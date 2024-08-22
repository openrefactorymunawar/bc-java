package org.bouncycastle.pqc.jcajce.provider.test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.spec.DilithiumParameterSpec;

/**
 * KeyFactory/KeyPairGenerator tests for Dilithium with BC provider.
 */
public class DilithiumKeyPairGeneratorTest
    extends KeyPairGeneratorTest
{
    protected void setUp()
    {
        super.setUp();
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
        {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public void testKeyFactory()
        throws Exception
    {
        kf = KeyFactory.getInstance("Dilithium", "BC");
    }

    public void testKeyPairGeneratorNames()
        throws Exception
    {
        ASN1ObjectIdentifier[] oids = new ASN1ObjectIdentifier[] {
            NISTObjectIdentifiers.id_ml_dsa_44,
            NISTObjectIdentifiers.id_ml_dsa_65,
            NISTObjectIdentifiers.id_ml_dsa_87
        };

        String[] algs = new String[]{
            "ML-DSA-44",
            "ML-DSA-65",
            "ML-DSA-87"
        };

        for (int i = 0; i != oids.length; i++)
        {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance(oids[i].getId(), "BC");

            KeyPair kp = kpGen.generateKeyPair();

            assertEquals(algs[i], kp.getPrivate().getAlgorithm());
            assertEquals(algs[i], kp.getPublic().getAlgorithm());
        }
    }

    public void testKeyPairEncoding()
        throws Exception
    {
        DilithiumParameterSpec[] specs =
            new DilithiumParameterSpec[]
                {
                        DilithiumParameterSpec.dilithium2,
                        DilithiumParameterSpec.dilithium3,
                        DilithiumParameterSpec.dilithium5,
                };
        kf = KeyFactory.getInstance("Dilithium", "BC");

        kpg = KeyPairGenerator.getInstance("Dilithium", "BC");

        for (int i = 0; i != specs.length; i++)
        {
            kpg.initialize(specs[i], new SecureRandom());
            performKeyPairEncodingTest(specs[i].getName(), kpg.generateKeyPair());
        }
    }

}
