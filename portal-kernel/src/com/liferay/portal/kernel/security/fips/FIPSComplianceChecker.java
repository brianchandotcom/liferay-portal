/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Caio Farias
 */
public class FIPSComplianceChecker {

	public static void check() {
		_check(Security.getProviders());
	}

	private static void _check(Provider[] providers) {
		_checkSecurityProvidersCompliance(providers);

		_checkFIPSProviderIntegrity(providers[0]);

		_check(
			"DES/CBC/PKCS5Padding",
			() -> _generateCipher(
				"DES/CBC/PKCS5Padding", new SecretKeySpec(new byte[8], "DES"),
				new IvParameterSpec(new byte[8])));
		_check(
			"DESede",
			() -> _generateCipher(
				"DESede/CBC/PKCS5Padding",
				new SecretKeySpec(new byte[24], "DESede"),
				new IvParameterSpec(new byte[8])));
		_check(
			"RC4",
			() -> _generateCipher(
				"RC4", new SecretKeySpec(new byte[16], "RC4"), null));
		_check(
			"PBEWithMD5AndDES",
			() -> {
				SecretKey key = SecretKeyFactory.getInstance(
					"PBEWithMD5AndDES"
				).generateSecret(
					new PBEKeySpec("password".toCharArray())
				);

				return _generateCipher(
					"PBEWithMD5AndDES", key,
					new PBEParameterSpec(new byte[8], 1000));
			});
		_check(
			"HmacMD5",
			() -> {
				Mac mac = Mac.getInstance("HmacMD5");

				mac.init(new SecretKeySpec(new byte[16], "HmacMD5"));

				mac.doFinal(new byte[0]);

				return mac.getProvider();
			});
		_check("MD5", () -> _messageDigest("MD5"));
		_check(
			"EC/secp192r1",
			() -> _generateKey("EC", new ECGenParameterSpec("secp192r1")));
		_check(
			"RSA/512",
			() -> _generateKey(
				"RSA",
				new RSAKeyGenParameterSpec(512, RSAKeyGenParameterSpec.F4)));
		_check(
			"RSA/1024",
			() -> _generateKey(
				"RSA",
				new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4)));
		_check(
			"SHA1withECDSA",
			() -> _generateSignature(
				"SHA1withECDSA", "EC", new ECGenParameterSpec("secp256r1")));
		_check(
			"SHA1withRSA",
			() -> _generateSignature(
				"SHA1withRSA", "RSA",
				new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4)));

		if (_log.isInfoEnabled()) {
			_log.info("FIPS checks passed");
		}
	}

	private static void _check(
		String algorithmName,
		UnsafeSupplier<Provider, Exception> unsafeSupplier) {

		Provider provider;

		try {
			provider = unsafeSupplier.get();
		}
		catch (Error | InvalidAlgorithmParameterException |
			   InvalidKeyException | InvalidKeySpecException |
			   NoSuchAlgorithmException | NoSuchPaddingException |
			   SecurityException exception) {

			return;
		}
		catch (Exception exception) {
			throw new SecurityException(
				"FIPS check failed unexpectedly: " + exception.getMessage(),
				exception);
		}

		if ((provider != null) &&
			_fipsSecurityProvidersMap.containsKey(provider.getName())) {

			return;
		}

		String providerInfo = "";

		if (provider != null) {
			providerInfo = " (provided by " + provider.getName() + ")";
		}

		throw new SecurityException(
			StringBundler.concat(
				"FIPS check failed: ", algorithmName,
				" should not be accessible in FIPS mode", providerInfo));
	}

	private static void _checkFIPSProviderIntegrity(Provider fipsProvider) {
		String name = fipsProvider.getName();

		if (name.equals("BCFIPS")) {
			_executeFIPSProviderIntegrityCheck(
				() -> {
					Class<?> fipsStatus = Class.forName(
						"org.bouncycastle.crypto.fips.FipsStatus");

					boolean ready = (Boolean)ReflectionUtil.getDeclaredMethod(
						fipsStatus, "isReady"
					).invoke(
						null
					);

					if (!ready) {
						String msg = (String)ReflectionUtil.getDeclaredMethod(
							fipsStatus, "getStatusMessage"
						).invoke(
							null
						);

						throw new SecurityException(
							"BCFIPS integrity failure: " + msg);
					}

					Class<?> cryptoServicesRegistrar = Class.forName(
						"org.bouncycastle.crypto.CryptoServicesRegistrar");

					boolean approved =
						(Boolean)ReflectionUtil.getDeclaredMethod(
							cryptoServicesRegistrar, "isInApprovedOnlyMode"
						).invoke(
							null
						);

					if (!approved) {
						throw new SecurityException(
							"BCFIPS not in approved-only mode");
					}
				});
		}
		else if (name.equals("AmazonCorrettoCryptoProvider")) {
			_executeFIPSProviderIntegrityCheck(
				() -> {
					Class<?> amazonCorrettoCryptoProvider = Class.forName(
						"com.amazon.corretto.crypto.provider." +
							"AmazonCorrettoCryptoProvider");

					Object instance = ReflectionUtil.getDeclaredField(
						amazonCorrettoCryptoProvider, "INSTANCE"
					).get(
						null
					);

					Throwable loadingErrorThrowable =
						(Throwable)ReflectionUtil.getDeclaredMethod(
							amazonCorrettoCryptoProvider, "getLoadingError"
						).invoke(
							instance
						);

					if (loadingErrorThrowable != null) {
						throw new SecurityException(
							"AmazonCorrettoCryptoProvider integrity failure: " +
								loadingErrorThrowable.getMessage(),
							loadingErrorThrowable);
					}

					boolean fipsVersion =
						(Boolean)ReflectionUtil.getDeclaredMethod(
							amazonCorrettoCryptoProvider, "isFips"
						).invoke(
							instance
						);

					if (!fipsVersion) {
						throw new SecurityException(
							"\"AmazonCorrettoCryptoProvider\" is not a FIPS " +
								"build");
					}

					Object status = ReflectionUtil.getDeclaredMethod(
						amazonCorrettoCryptoProvider, "runSelfTests"
					).invoke(
						instance
					);

					if (!Objects.equals(String.valueOf(status), "PASSED")) {
						throw new SecurityException(
							"AmazonCorrettoCryptoProvider integrity failure: " +
								status);
					}
				});
		}
		else {
			throw new SecurityException(
				"No integrity check implemented for: " + name);
		}
	}

	private static void _checkSecurityProvidersCompliance(
		Provider[] providers) {

		if (providers.length == 0) {
			throw new SecurityException("No security providers are registered");
		}

		String fipsProviderName = providers[0].getName();

		if (!_fipsSecurityProvidersMap.containsKey(fipsProviderName)) {
			throw new SecurityException("FIPS provider is not supported");
		}

		List<String> unapprovedProviderNames = new ArrayList<>();

		List<String> approvedProviders = _fipsSecurityProvidersMap.get(
			fipsProviderName);

		for (Provider provider : providers) {
			String providerName = provider.getName();

			if (!approvedProviders.contains(providerName)) {
				unapprovedProviderNames.add(providerName);
			}
		}

		if (unapprovedProviderNames.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"All registered security providers are approved for FIPS " +
						"mode");
			}

			return;
		}

		throw new SecurityException(
			"Unapproved security providers registered in FIPS mode: " +
				unapprovedProviderNames);
	}

	private static void _executeFIPSProviderIntegrityCheck(
		UnsafeRunnable<Throwable> unsafeRunnable) {

		try {
			unsafeRunnable.run();
		}
		catch (SecurityException securityException) {
			throw securityException;
		}
		catch (Throwable throwable) {
			Throwable causeThrowable = throwable.getCause();

			if (causeThrowable == null) {
				causeThrowable = throwable;
			}

			throw new SecurityException(
				"FIPS provider integrity failure: " +
					causeThrowable.getMessage(),
				causeThrowable);
		}
	}

	private static Provider _generateCipher(
			String transformation, SecretKey key, AlgorithmParameterSpec spec)
		throws Exception {

		Cipher cipher = Cipher.getInstance(transformation);

		cipher.init(Cipher.ENCRYPT_MODE, key, spec);

		cipher.doFinal(new byte[0]);

		return cipher.getProvider();
	}

	private static Provider _generateKey(
			String algorithm, AlgorithmParameterSpec spec)
		throws Exception {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
			algorithm);

		keyPairGenerator.initialize(spec);

		keyPairGenerator.generateKeyPair();

		return keyPairGenerator.getProvider();
	}

	private static Provider _generateSignature(
			String signatureAlgorithm, String keyAlgorithm,
			AlgorithmParameterSpec keySpec)
		throws Exception {

		Signature signature = Signature.getInstance(signatureAlgorithm);

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
			keyAlgorithm);

		keyPairGenerator.initialize(keySpec);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		signature.initSign(keyPair.getPrivate());

		signature.sign();

		return signature.getProvider();
	}

	private static Provider _messageDigest(String algorithm) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

		messageDigest.digest(new byte[0]);

		return messageDigest.getProvider();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FIPSComplianceChecker.class);

	private static final Map<String, List<String>> _fipsSecurityProvidersMap =
		Map.of(
			"BCFIPS",
			List.of(
				"BCFIPS", "BCJSSE", "SUN", "SunJCE", "XMLDSig", "SunJGSS",
				"SunSASL", "JdkLDAP", "JdkSASL"),
			"AmazonCorrettoCryptoProvider",
			List.of(
				"AmazonCorrettoCryptoProvider", "SUN", "SunRsaSign", "SunEC",
				"SunJSSE", "SunJCE", "SunJGSS", "SunSASL", "XMLDSig", "JdkLDAP",
				"JdkSASL"));

}