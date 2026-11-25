/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.encryptor.CompanyKeyResolver;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.exception.CompanyKeyException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.fips.FIPSModeValidationException.ValidationError;
import com.liferay.portal.kernel.security.fips.FIPSModeValidationException.ValidationErrorType;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProvider;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.test.util.FIPSModeTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.security.Key;
import java.security.Provider;
import java.security.Security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Caio Farias
 */
public class FIPSModeValidatorTest {

	@Before
	public void setUp() {
		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		SecureXMLFactoryProvider secureXMLFactoryProvider = Mockito.mock(
			SecureXMLFactoryProvider.class);

		Mockito.when(
			secureXMLFactoryProvider.newDocumentBuilderFactory()
		).thenAnswer(
			input -> DocumentBuilderFactory.newInstance()
		);

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			secureXMLFactoryProvider);
	}

	@Test
	public void testGetAllowedTLSCipherSuites() {
		Assert.assertArrayEquals(
			new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"},
			FIPSModeValidator.getAllowedTLSCipherSuites(
				new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"}));

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			Assert.assertArrayEquals(
				new String[] {"TLS_AES_128_GCM_SHA256"},
				FIPSModeValidator.getAllowedTLSCipherSuites(
					new String[] {
						"TLS_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA"
					}));
		}
	}

	@Test
	public void testGetAllowedTLSProtocols() {
		Assert.assertArrayEquals(
			new String[] {"TLSv1"},
			FIPSModeValidator.getAllowedTLSProtocols(new String[] {"TLSv1"}));

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			Assert.assertArrayEquals(
				new String[] {"TLSv1.3"},
				FIPSModeValidator.getAllowedTLSProtocols(
					new String[] {"TLSv1.3", "TLSv1"}));
		}
	}

	@Test
	public void testGetKeyEncryptionKeyValidationErrors() throws Exception {
		long companyId1 = RandomTestUtil.randomLong();
		long companyId2 = RandomTestUtil.randomLong();
		Key key = new SecretKeySpec(
			RandomTestUtil.randomBytes(), RandomTestUtil.randomString());

		try (AutoCloseable autoCloseable1 = _setCompanyLocalService(
				companyId1, companyId2);
			AutoCloseable autoCloseable2 = _setEncryptor(key)) {

			try (AutoCloseable autoCloseable3 = _setCompanyKeyResolver(null);
				LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					FIPSModeValidator.class.getName(), LoggerTestUtil.WARN)) {

				Assert.assertEquals(
					Collections.emptyList(), _getKeyEncryptionKeyViolations());

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertEquals(
					"Unable to check the key encryption keys because no " +
						"company key resolver is available",
					logEntry.getMessage());
			}

			CompanyKeyResolver companyKeyResolver = Mockito.mock(
				CompanyKeyResolver.class);

			try (AutoCloseable autoCloseable3 = _setCompanyKeyResolver(
					companyKeyResolver)) {

				Assert.assertEquals(
					Collections.emptyList(), _getKeyEncryptionKeyViolations());

				Mockito.when(
					companyKeyResolver.isEnabled(Mockito.anyLong())
				).thenReturn(
					true
				);

				String keyString1 = RandomTestUtil.randomString();

				Mockito.when(
					companyKeyResolver.wrapKey(companyId1, key)
				).thenReturn(
					keyString1
				);

				Mockito.when(
					companyKeyResolver.unwrapKey(companyId1, keyString1)
				).thenReturn(
					key
				);

				String keyString2 = RandomTestUtil.randomString();

				Mockito.when(
					companyKeyResolver.wrapKey(companyId2, key)
				).thenReturn(
					keyString2
				);

				Mockito.when(
					companyKeyResolver.unwrapKey(companyId2, keyString2)
				).thenReturn(
					key
				);

				Assert.assertEquals(
					Collections.emptyList(), _getKeyEncryptionKeyViolations());

				Mockito.when(
					companyKeyResolver.unwrapKey(companyId1, keyString1)
				).thenReturn(
					new SecretKeySpec(
						RandomTestUtil.randomBytes(),
						RandomTestUtil.randomString())
				);

				String message = RandomTestUtil.randomString();

				Mockito.when(
					companyKeyResolver.wrapKey(companyId2, key)
				).thenThrow(
					new CompanyKeyException(message)
				);

				Assert.assertEquals(
					Arrays.asList(
						_getViolation(
							"Key Encryption Key",
							"The key encryption key of company " + companyId1 +
								" did not return the key it wrapped"),
						_getViolation(
							"Key Encryption Key",
							StringBundler.concat(
								"Unable to reach the key encryption key of ",
								"company ", companyId2, ": ", message))),
					_getKeyEncryptionKeyViolations());
			}
		}
	}

	@Test
	public void testGetPlaintextSecretProperties() {
		String obfuscatedKey1 = RandomTestUtil.randomString();
		String obfuscatedKey2 = RandomTestUtil.randomString();
		String obfuscatedKey3 = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"ADMIN_OBFUSCATED_PROPERTIES",
					new String[] {
						obfuscatedKey1, obfuscatedKey2, obfuscatedKey3
					})) {

			Properties properties = new Properties();

			properties.setProperty(
				obfuscatedKey1, RandomTestUtil.randomString());
			properties.setProperty(
				obfuscatedKey2, "${" + RandomTestUtil.randomString() + "}");
			properties.setProperty(obfuscatedKey3, StringPool.BLANK);
			properties.setProperty(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

			List<String> plaintextSecretProperties = ReflectionTestUtil.invoke(
				FIPSModeValidator.class, "_getPlaintextSecretProperties",
				new Class<?>[] {Properties.class}, properties);

			Assert.assertEquals(
				plaintextSecretProperties.toString(), 1,
				plaintextSecretProperties.size());

			Assert.assertTrue(
				plaintextSecretProperties.contains(obfuscatedKey1));
		}
	}

	@Test
	public void testGetPortalPropertiesValidationErrors() {
		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"COMPANY_ENCRYPTION_ALGORITHM", "AES", false);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DEFAULT_ADMIN_PASSWORD", RandomTestUtil.randomString(14));
			SafeCloseable safeCloseable3 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 14);
			SafeCloseable safeCloseable4 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_ENCRYPTION_ALGORITHM",
					"PBKDF2WithHmacSHA256/256/1300000", false);
			SafeCloseable safeCloseable5 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNEL_UTIL_VERIFY_SSL_HOSTNAME", "true", false);
			SafeCloseable safeCloseable6 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_ENCRYPTION_ALGORITHM", "AES")) {

			Assert.assertEquals(
				Collections.emptyList(), _getPortalPropertiesViolations());

			try (SafeCloseable safeCloseable7 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"COMPANY_ENCRYPTION_ALGORITHM", "DES", false);
				SafeCloseable safeCloseable8 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 13)) {

				Assert.assertEquals(
					Arrays.asList(
						_getViolation(
							"Encryption Algorithms",
							"Algorithm \"DES\" is not allowed in FIPS mode"),
						_getViolation(
							"Passwords",
							"FIPS mode requires the property \"" +
								PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH +
									"\" to be at least 14")),
					_getPortalPropertiesViolations());
			}

			try (SafeCloseable safeCloseable7 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"DEFAULT_ADMIN_PASSWORD",
						RandomTestUtil.randomString(13))) {

				Assert.assertEquals(
					Collections.singletonList(
						_getViolation(
							"Passwords",
							"FIPS mode requires the property \"" +
								PropsKeys.DEFAULT_ADMIN_PASSWORD +
									"\" to be at least 14 bytes")),
					_getPortalPropertiesViolations());
			}

			try (SafeCloseable safeCloseable7 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"TUNNEL_UTIL_VERIFY_SSL_HOSTNAME", StringPool.BLANK,
						false)) {

				Assert.assertEquals(
					Collections.singletonList(
						_getViolation(
							"TLS and Certificate Revocation",
							"FIPS mode requires the property \"" +
								PropsKeys.TUNNEL_UTIL_VERIFY_SSL_HOSTNAME +
									"\" to be enabled")),
					_getPortalPropertiesViolations());
			}

			try (SafeCloseable safeCloseable7 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"TUNNEL_UTIL_VERIFY_SSL_HOSTNAME", "false", false)) {

				Assert.assertEquals(
					Collections.singletonList(
						_getViolation(
							"TLS and Certificate Revocation",
							"FIPS mode requires the property \"" +
								PropsKeys.TUNNEL_UTIL_VERIFY_SSL_HOSTNAME +
									"\" to be enabled")),
					_getPortalPropertiesViolations());
			}
		}
	}

	@Test
	public void testIsNotAllowedPassword() {
		String password = RandomTestUtil.randomString(13);

		Assert.assertFalse(FIPSModeValidator.isNotAllowedPassword(password));

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			Assert.assertFalse(
				FIPSModeValidator.isNotAllowedPassword(
					RandomTestUtil.randomString(14)));
			Assert.assertTrue(FIPSModeValidator.isNotAllowedPassword(password));
		}
	}

	@Test
	public void testReadChannelPropertiesDocument() throws Exception {
		Document document = ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_readChannelPropertiesDocument",
			new Class<?>[] {String.class},
			String.valueOf(
				DependenciesTestUtil.getDependencyAsFile(
					FIPSModeValidatorTest.class,
					"cluster-link-channel-properties.xml")));

		NodeList nodeList = document.getElementsByTagName("AUTH");

		Assert.assertEquals(1, nodeList.getLength());

		Element authElement = (Element)nodeList.item(0);

		Assert.assertEquals(
			"org.jgroups.auth.X509Token",
			authElement.getAttribute("auth_class"));

		_assertSecurityException(
			"The cluster link channel properties " +
				"\"jgroups/secure/sym_encrypt/udp_control.xml\" must be a " +
					"file in FIPS mode",
			"_readChannelPropertiesDocument", new Class<?>[] {String.class},
			"jgroups/secure/sym_encrypt/udp_control.xml");
		_assertSecurityException(
			"Unable to parse the cluster link channel properties",
			"_readChannelPropertiesDocument", new Class<?>[] {String.class},
			String.valueOf(
				DependenciesTestUtil.getDependencyAsFile(
					FIPSModeValidatorTest.class,
					"cluster-link-channel-properties-malformed.xml")));
		_assertSecurityException(
			"must be a file in FIPS mode", "_readChannelPropertiesDocument",
			new Class<?>[] {String.class}, "\u0000");
		_assertSecurityException(
			"must be a file in FIPS mode", "_readChannelPropertiesDocument",
			new Class<?>[] {String.class},
			SystemProperties.get(SystemProperties.TMP_DIR));
	}

	@Test
	public void testValidate() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 13)) {

			SecurityException securityException = Assert.assertThrows(
				SecurityException.class, FIPSModeValidator::validate);

			String message = securityException.getMessage();

			Assert.assertTrue(message.contains("\n\nPasswords\n- "));
			Assert.assertTrue(message.contains("\n\nSecurity Providers\n- "));
			Assert.assertTrue(
				message.contains(
					"\n- FIPS mode requires the property \"" +
						PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH +
							"\" to be at least 14"));
			Assert.assertTrue(
				message.startsWith(
					"The configuration is not allowed in FIPS mode.\n\n"));

			Provider[] providers = Security.getProviders();

			Assert.assertTrue(
				message.contains(
					"\n- The first security provider \"" +
						providers[0].getName() +
							"\" is not allowed in FIPS mode"));
		}
	}

	@Test
	public void testValidateAlgorithm() {
		FIPSModeValidator.validateAlgorithm("MD5");
		FIPSModeValidator.validateAlgorithm(null);

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			FIPSModeValidator.validateAlgorithm("AES");
			FIPSModeValidator.validateAlgorithm("SHA-256");

			FIPSModeTestUtil.assertSecurityException(
				"is not allowed in FIPS mode",
				() -> FIPSModeValidator.validateAlgorithm("MD5"));
			FIPSModeTestUtil.assertSecurityException(
				"is not allowed in FIPS mode",
				() -> FIPSModeValidator.validateAlgorithm("SHA-2"));
			FIPSModeTestUtil.assertSecurityException(
				"is not allowed in FIPS mode",
				() -> FIPSModeValidator.validateAlgorithm(null));
		}
	}

	@Test
	public void testValidateAllowedPropertyValues() {
		Assert.assertEquals(
			Collections.emptyList(),
			_getAllowedPropertyValuesViolations("TLSv1.2"));
		Assert.assertEquals(
			Collections.emptyList(),
			_getAllowedPropertyValuesViolations("TLSv1.2,"));
		Assert.assertEquals(
			Collections.emptyList(),
			_getAllowedPropertyValuesViolations("TLSv1.2,TLSv1.3"));
		Assert.assertEquals(
			Collections.emptyList(),
			_getAllowedPropertyValuesViolations("TLSv1.3"));

		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations(""));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations(",TLSv1.2"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("SSLv3,TLSv1.3"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("TLSv1"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("TLSv1.1"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("TLSv1.1,TLSv1.2"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("TLSv1.11"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("TLSv1.2,SSLv2Hello"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations("tlsv1.2"));
		_assertViolation(
			"FIPS mode requires the property \"jdk.tls.client.protocols\"",
			"TLS and Certificate Revocation",
			_getAllowedPropertyValuesViolations(null));
	}

	@Test
	public void testValidateClusterLinkChannelAuthElement() throws Exception {
		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateClusterLinkChannelAuthElement",
			new Class<?>[] {Element.class, String.class},
			_getElement("cluster-link-channel-properties.xml", "AUTH"),
			RandomTestUtil.randomString());

		_assertSecurityException(
			"must authenticate cluster members with " +
				"\"org.jgroups.auth.X509Token\"",
			"_validateClusterLinkChannelAuthElement",
			new Class<?>[] {Element.class, String.class},
			_getElement(
				"cluster-link-channel-properties-auth-class-missing.xml",
				"AUTH"),
			RandomTestUtil.randomString());
		_assertSecurityException(
			"must authenticate cluster members with " +
				"\"org.jgroups.auth.X509Token\"",
			"_validateClusterLinkChannelAuthElement",
			new Class<?>[] {Element.class, String.class},
			_getElement(
				"cluster-link-channel-properties-auth-class-md5-token.xml",
				"AUTH"),
			RandomTestUtil.randomString());
	}

	@Test
	public void testValidateClusterLinkChannelConfiguration() throws Exception {
		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateClusterLinkChannelConfiguration",
			new Class<?>[] {String.class},
			String.valueOf(
				DependenciesTestUtil.getDependencyAsFile(
					FIPSModeValidatorTest.class,
					"cluster-link-channel-properties.xml")));

		_assertSecurityException(
			"must encrypt intracluster traffic with \"SYM_ENCRYPT\" in FIPS " +
				"mode",
			"_validateClusterLinkChannelConfiguration",
			new Class<?>[] {String.class},
			String.valueOf(
				DependenciesTestUtil.getDependencyAsFile(
					FIPSModeValidatorTest.class,
					"cluster-link-channel-properties-asym-encrypt.xml")));
		_assertSecurityException(
			"must encrypt intracluster traffic with \"SYM_ENCRYPT\" in FIPS " +
				"mode",
			"_validateClusterLinkChannelConfiguration",
			new Class<?>[] {String.class},
			String.valueOf(
				DependenciesTestUtil.getDependencyAsFile(
					FIPSModeValidatorTest.class,
					"cluster-link-channel-properties-future-encrypt.xml")));
	}

	@Test
	public void testValidateClusterLinkChannelSymEncryptElement()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties.xml", "SYM_ENCRYPT"));
			ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-provider-bcfips.xml",
					"SYM_ENCRYPT"));

			_assertSecurityException(
				"Initialization vector size 0 is not allowed in FIPS mode",
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-sym-iv-length-missing.xml",
					"SYM_ENCRYPT"));
			_assertSecurityException(
				"Initialization vector size 12 is not allowed in FIPS mode",
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-sym-iv-length-12.xml",
					"SYM_ENCRYPT"));
			_assertSecurityException(
				"Key size 64 is not allowed in FIPS mode",
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-sym-keylength-64.xml",
					"SYM_ENCRYPT"));
			_assertSecurityException(
				"Security provider \"SunJCE\" is not allowed in FIPS mode",
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-provider-sun-jce.xml",
					"SYM_ENCRYPT"));
			_assertSecurityException(
				"Transformation \"\" is not allowed",
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-sym-algorithm-missing.xml",
					"SYM_ENCRYPT"));
			_assertSecurityException(
				"Transformation \"AES/ECB/NoPadding\" is not allowed",
				"_validateClusterLinkChannelSymEncryptElement",
				new Class<?>[] {Element.class},
				_getElement(
					"cluster-link-channel-properties-sym-algorithm-ecb.xml",
					"SYM_ENCRYPT"));
		}
	}

	@Test
	public void testValidateClusterLinkConfiguration() throws Exception {
		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"CLUSTER_LINK_AUTH_KEYSTORE_TYPE", "BCFKS", false);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL",
					String.valueOf(
						DependenciesTestUtil.getDependencyAsFile(
							FIPSModeValidatorTest.class,
							"cluster-link-channel-properties.xml")),
					false);
			SafeCloseable safeCloseable3 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT",
					String.valueOf(
						DependenciesTestUtil.getDependencyAsFile(
							FIPSModeValidatorTest.class,
							"cluster-link-channel-properties.xml")),
					false);
			SafeCloseable safeCloseable4 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"CLUSTER_LINK_ENABLED", true);
			SafeCloseable safeCloseable5 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			Assert.assertEquals(
				Collections.emptyList(),
				_getClusterLinkConfigurationViolations());

			_assertClusterLinkConfigurationViolation(
				"Key size 64 is not allowed in FIPS mode",
				"cluster-link-channel-properties-sym-keylength-64.xml",
				"CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT");
			_assertClusterLinkConfigurationViolation(
				"must authenticate cluster members with " +
					"\"org.jgroups.auth.X509Token\"",
				"cluster-link-channel-properties-auth-class-md5-token.xml",
				"CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL");
			_assertClusterLinkConfigurationViolation(
				"must encrypt intracluster traffic with \"SYM_ENCRYPT\" in " +
					"FIPS mode",
				"cluster-link-channel-properties-asym-encrypt.xml",
				"CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT");

			try (SafeCloseable safeCloseable6 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"CLUSTER_LINK_AUTH_KEYSTORE_TYPE", "bcfks", false)) {

				Assert.assertEquals(
					Collections.emptyList(),
					_getClusterLinkConfigurationViolations());
			}

			try (SafeCloseable safeCloseable6 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"CLUSTER_LINK_AUTH_KEYSTORE_TYPE", "PKCS12", false)) {

				_assertViolation(
					"\"" + PropsKeys.CLUSTER_LINK_AUTH_KEYSTORE_TYPE +
						"\" to be set to only [BCFKS]",
					"Cluster Link", _getClusterLinkConfigurationViolations());
			}

			try (SafeCloseable safeCloseable6 =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"CLUSTER_LINK_AUTH_KEYSTORE_TYPE",
						RandomTestUtil.randomString(), false)) {

				_assertViolation(
					"\"" + PropsKeys.CLUSTER_LINK_AUTH_KEYSTORE_TYPE +
						"\" to be set to only [BCFKS]",
					"Cluster Link", _getClusterLinkConfigurationViolations());
			}
		}
	}

	@Test
	public void testValidateDefaultAdminPassword() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DEFAULT_ADMIN_PASSWORD",
					RandomTestUtil.randomString(14))) {

			ReflectionTestUtil.invoke(
				FIPSModeValidator.class, "_validateDefaultAdminPassword",
				new Class<?>[0]);
		}

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DEFAULT_ADMIN_PASSWORD", StringPool.BLANK)) {

			ReflectionTestUtil.invoke(
				FIPSModeValidator.class, "_validateDefaultAdminPassword",
				new Class<?>[0]);
		}

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DEFAULT_ADMIN_PASSWORD",
					RandomTestUtil.randomString(13))) {

			_assertSecurityException(
				"FIPS mode requires the property \"" +
					PropsKeys.DEFAULT_ADMIN_PASSWORD + "\" to be at least",
				"_validateDefaultAdminPassword", new Class<?>[0]);
		}
	}

	@Test
	public void testValidateFIPSProvider() {
		_assertSecurityException(
			"The first security provider \"AmazonCorrettoCryptoProvider\" is " +
				"not allowed in FIPS mode",
			"_validateFIPSProvider", new Class<?>[] {Provider[].class},
			(Object)new Provider[] {
				_createProvider("AmazonCorrettoCryptoProvider")
			});

		_assertSecurityException(
			"FIPS provider integrity failed:", "_validateFIPSProvider",
			new Class<?>[] {Provider[].class},
			(Object)new Provider[] {_createProvider("BCFIPS")});
		_assertSecurityException(
			"The first security provider \"null\" is not allowed in FIPS mode",
			"_validateFIPSProvider", new Class<?>[] {Provider[].class},
			(Object)new Provider[] {_createProvider(null)});
		_assertSecurityException(
			"There are no security providers", "_validateFIPSProvider",
			new Class<?>[] {Provider[].class}, (Object)new Provider[0]);
		_assertSecurityException(
			"is not allowed in FIPS mode", "_validateFIPSProvider",
			new Class<?>[] {Provider[].class},
			(Object)new Provider[] {
				_createProvider(RandomTestUtil.randomString())
			});
	}

	@Test
	public void testValidateJWSAlgorithm() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", false)) {

			FIPSModeValidator.validateJWSAlgorithm("HS256");
			FIPSModeValidator.validateJWSAlgorithm(null);
		}

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			FIPSModeValidator.validateJWSAlgorithm("ES256");
			FIPSModeValidator.validateJWSAlgorithm("ES384");
			FIPSModeValidator.validateJWSAlgorithm("ES512");
			FIPSModeValidator.validateJWSAlgorithm("EdDSA");
			FIPSModeValidator.validateJWSAlgorithm("PS256");
			FIPSModeValidator.validateJWSAlgorithm("PS384");
			FIPSModeValidator.validateJWSAlgorithm("PS512");
			FIPSModeValidator.validateJWSAlgorithm("RS256");
			FIPSModeValidator.validateJWSAlgorithm("RS384");
			FIPSModeValidator.validateJWSAlgorithm("RS512");

			_assertJWSAlgorithmNotAllowed(StringPool.BLANK);
			_assertJWSAlgorithmNotAllowed("ES256K");
			_assertJWSAlgorithmNotAllowed("HS1");
			_assertJWSAlgorithmNotAllowed("HS256");
			_assertJWSAlgorithmNotAllowed("HS384");
			_assertJWSAlgorithmNotAllowed("HS512");
			_assertJWSAlgorithmNotAllowed("RS1");
			_assertJWSAlgorithmNotAllowed("RSA1_5");
			_assertJWSAlgorithmNotAllowed("garbage");
			_assertJWSAlgorithmNotAllowed("none");
			_assertJWSAlgorithmNotAllowed("rs256");
			_assertJWSAlgorithmNotAllowed(null);
		}
	}

	@Test
	public void testValidateKey() {
		FIPSModeValidator.validateKey("DES", 64);

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			FIPSModeValidator.validateKey("AES", 0);
			FIPSModeValidator.validateKey("AES", 128);
			FIPSModeValidator.validateKey("AES", 192);
			FIPSModeValidator.validateKey("AES", 256);

			FIPSModeTestUtil.assertSecurityException(
				"Key size 64 is not allowed in FIPS mode",
				() -> FIPSModeValidator.validateKey("AES", 64));
			FIPSModeTestUtil.assertSecurityException(
				"is not allowed in FIPS mode",
				() -> FIPSModeValidator.validateKey("DES", 128));
		}
	}

	@Test
	public void testValidateKeyEncryptionKeys() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		CompanyKeyResolver companyKeyResolver = Mockito.mock(
			CompanyKeyResolver.class);
		Key key = new SecretKeySpec(
			RandomTestUtil.randomBytes(), RandomTestUtil.randomString());

		try (AutoCloseable autoCloseable1 = _setCompanyKeyResolver(
				companyKeyResolver);
			AutoCloseable autoCloseable2 = _setCompanyLocalService(companyId);
			AutoCloseable autoCloseable3 = _setEncryptor(key)) {

			FIPSModeValidator.validateKeyEncryptionKeys();

			Mockito.when(
				companyKeyResolver.isEnabled(companyId)
			).thenReturn(
				true
			);

			CompanyKeyException companyKeyException = new CompanyKeyException(
				RandomTestUtil.randomString());

			Mockito.when(
				companyKeyResolver.wrapKey(companyId, key)
			).thenThrow(
				companyKeyException
			);

			SecurityException securityException = Assert.assertThrows(
				SecurityException.class,
				FIPSModeValidator::validateKeyEncryptionKeys);

			String violation = _getViolation(
				"Key Encryption Key",
				StringBundler.concat(
					"Unable to reach the key encryption key of company ",
					companyId, ": ", companyKeyException.getMessage()));

			Assert.assertEquals(
				"The configuration is not allowed in FIPS mode.\n\n" +
					violation,
				securityException.getMessage());

			Throwable[] suppressedThrowables =
				securityException.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(suppressedThrowables), 1,
				suppressedThrowables.length);

			Throwable suppressedThrowable = suppressedThrowables[0];

			Assert.assertSame(
				companyKeyException, suppressedThrowable.getCause());
		}
	}

	@Test
	public void testValidatePassword() {
		String password = RandomTestUtil.randomString(13);

		FIPSModeValidator.validatePassword(password);

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			FIPSModeValidator.validatePassword(RandomTestUtil.randomString(14));

			FIPSModeTestUtil.assertSecurityException(
				"Password must be at least 14 bytes in FIPS mode",
				() -> FIPSModeValidator.validatePassword(password));
		}
	}

	@Test
	public void testValidatePasswordsDefaultPolicyMinLength() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 14)) {

			ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validatePasswordsDefaultPolicyMinLength", new Class<?>[0]);
		}

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 13)) {

			_assertSecurityException(
				"FIPS mode requires the property \"" +
					PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH +
						"\" to be at least",
				"_validatePasswordsDefaultPolicyMinLength", new Class<?>[0]);
		}
	}

	@Test
	public void testValidatePasswordsEncryptionAlgorithm() {
		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validatePasswordsEncryptionAlgorithm",
			new Class<?>[] {String.class}, "PBKDF2WithHmacSHA256/256/1300000");

		_assertSecurityException(
			"is not allowed in FIPS mode",
			"_validatePasswordsEncryptionAlgorithm",
			new Class<?>[] {String.class}, "PBKDF2WithHmacSHA1/160/1300000");
		_assertSecurityException(
			"is not allowed in FIPS mode",
			"_validatePasswordsEncryptionAlgorithm",
			new Class<?>[] {String.class}, "bcrypt/10");
		_assertSecurityException(
			"iteration count", "_validatePasswordsEncryptionAlgorithm",
			new Class<?>[] {String.class}, "PBKDF2WithHmacSHA256/256/600000");
		_assertSecurityException(
			"output length", "_validatePasswordsEncryptionAlgorithm",
			new Class<?>[] {String.class}, "PBKDF2WithHmacSHA256/64/1300000");
	}

	@Test
	public void testValidatePortalConfiguration() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		CompanyKeyResolver companyKeyResolver = Mockito.mock(
			CompanyKeyResolver.class);
		Key key = new SecretKeySpec(
			RandomTestUtil.randomBytes(), RandomTestUtil.randomString());

		try (AutoCloseable autoCloseable1 = _setCompanyKeyResolver(
				companyKeyResolver);
			AutoCloseable autoCloseable2 = _setCompanyLocalService(companyId);
			AutoCloseable autoCloseable3 = _setEncryptor(key);
			SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"PASSWORDS_DEFAULT_POLICY_MIN_LENGTH", 13)) {

			Mockito.when(
				companyKeyResolver.isEnabled(companyId)
			).thenReturn(
				true
			);

			CompanyKeyException companyKeyException = new CompanyKeyException(
				RandomTestUtil.randomString());

			Mockito.when(
				companyKeyResolver.wrapKey(companyId, key)
			).thenThrow(
				companyKeyException
			);

			SecurityException securityException = Assert.assertThrows(
				SecurityException.class,
				FIPSModeValidator::validatePortalConfiguration);

			String message = securityException.getMessage();

			Assert.assertTrue(
				message.contains(
					"\n- FIPS mode requires the property \"" +
						PropsKeys.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH +
							"\" to be at least 14"));
			Assert.assertTrue(
				message.contains(
					_getViolation(
						"Key Encryption Key",
						StringBundler.concat(
							"Unable to reach the key encryption key of ",
							"company ", companyId, ": ",
							companyKeyException.getMessage()))));
			Assert.assertTrue(
				message.startsWith(
					"The configuration is not allowed in FIPS mode.\n\n"));
		}
	}

	@Test
	public void testValidateProviders() {
		Map<String, List<String>> allowedProviderNames =
			ReflectionTestUtil.getFieldValue(
				FIPSModeValidator.class, "_allowedProviderNames");

		for (String allowedProviderName : allowedProviderNames.keySet()) {
			_assertSecurityException(
				"are not allowed in FIPS mode for", "_validateProviders",
				new Class<?>[] {Provider[].class},
				(Object)new Provider[] {
					_createProvider(allowedProviderName),
					_createProvider(RandomTestUtil.randomString())
				});
		}

		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateProviders",
			new Class<?>[] {Provider[].class}, (Object)new Provider[0]);
		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateProviders",
			new Class<?>[] {Provider[].class},
			(Object)new Provider[] {
				_createProvider(RandomTestUtil.randomString()),
				_createProvider(RandomTestUtil.randomString())
			});
	}

	@Test
	public void testValidateRequiredPropertyValues() {
		String key = RandomTestUtil.randomString();

		Assert.assertEquals(
			Collections.emptyList(),
			_getRequiredPropertyValuesViolations(
				curKey -> "PKIX", Map.of(key, new String[] {"PKIX"})));
		Assert.assertEquals(
			Collections.emptyList(),
			_getRequiredPropertyValuesViolations(
				curKey -> "SSLv3, TLSv1", Map.of(key, new String[] {"TLSv1"})));
		Assert.assertEquals(
			Collections.emptyList(),
			_getRequiredPropertyValuesViolations(
				curKey -> "TLSv1.2,TLSv1.3",
				Map.of(key, new String[] {"TLSv1.2"})));
		Assert.assertEquals(
			Collections.emptyList(),
			_getRequiredPropertyValuesViolations(
				curKey -> "TRUE", Map.of(key, new String[] {"true"})));

		_assertViolation(
			"FIPS mode requires the property \"" + key +
				"\" to include \"TLSv1.3\"",
			"TLS and Certificate Revocation",
			_getRequiredPropertyValuesViolations(
				curKey -> "TLSv1.2",
				Map.of(key, new String[] {"TLSv1.2", "TLSv1.3"})));
		_assertViolation(
			"FIPS mode requires the property \"" + key + "\"",
			"TLS and Certificate Revocation",
			_getRequiredPropertyValuesViolations(
				curKey -> "SunPKIXFoo", Map.of(key, new String[] {"PKIX"})));
		_assertViolation(
			"FIPS mode requires the property \"" + key + "\"",
			"TLS and Certificate Revocation",
			_getRequiredPropertyValuesViolations(
				curKey -> "TLSv1.1", Map.of(key, new String[] {"TLSv1"})));
		_assertViolation(
			"FIPS mode requires the property \"" + key + "\"",
			"TLS and Certificate Revocation",
			_getRequiredPropertyValuesViolations(
				curKey -> "untrue", Map.of(key, new String[] {"true"})));
		_assertViolation(
			"FIPS mode requires the property \"" + key + "\"",
			"TLS and Certificate Revocation",
			_getRequiredPropertyValuesViolations(
				curKey -> null, Map.of(key, new String[] {"true"})));

		Assert.assertEquals(
			Arrays.asList(
				_getViolation(
					"TLS and Certificate Revocation",
					"FIPS mode requires the property \"" + key +
						"1\" to include \"true\""),
				_getViolation(
					"TLS and Certificate Revocation",
					"FIPS mode requires the property \"" + key +
						"2\" to include \"true\"")),
			_getRequiredPropertyValuesViolations(
				curKey -> null,
				TreeMapBuilder.put(
					key + "1", new String[] {"true"}
				).put(
					key + "2", new String[] {"true"}
				).build()));

		System.setProperty(key, "true");

		try {
			Assert.assertEquals(
				Collections.emptyList(),
				_getRequiredPropertyValuesViolations(
					System::getProperty, Map.of(key, new String[] {"true"})));

			_assertViolation(
				"FIPS mode requires the property \"" + key + "\"",
				"TLS and Certificate Revocation",
				_getRequiredPropertyValuesViolations(
					Security::getProperty, Map.of(key, new String[] {"true"})));
		}
		finally {
			System.clearProperty(key);
		}
	}

	@Test
	public void testValidateSecureRandomProvider() {
		Object originalSecureRandom = ReflectionTestUtil.getFieldValue(
			SecureRandomUtil.class, "_random");

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			ReflectionTestUtil.setFieldValue(
				SecureRandomUtil.class, "_random",
				_createSecureRandom("BCFIPS"));

			ReflectionTestUtil.invoke(
				FIPSModeValidator.class, "_validateSecureRandomProvider",
				new Class<?>[0]);

			String providerName = RandomTestUtil.randomString();

			ReflectionTestUtil.setFieldValue(
				SecureRandomUtil.class, "_random",
				_createSecureRandom(providerName));

			_assertSecurityException(
				"The secure random provider \"" + providerName +
					"\" is not allowed in FIPS mode",
				"_validateSecureRandomProvider", new Class<?>[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				SecureRandomUtil.class, "_random", originalSecureRandom);
		}
	}

	@Test
	public void testValidateSessionTimeout() {
		FIPSModeValidator.validateSessionTimeout(RandomTestUtil.randomInt());

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			FIPSModeValidator.validateSessionTimeout(720);

			FIPSModeTestUtil.assertSecurityException(
				"Session timeout must not be greater than 12 hours",
				() -> FIPSModeValidator.validateSessionTimeout(721));
		}
	}

	@Test
	public void testValidateURL() {
		FIPSModeValidator.validateURL(
			"ldap://" + RandomTestUtil.randomString());
		FIPSModeValidator.validateURL(
			"ldaps://" + RandomTestUtil.randomString());
		FIPSModeValidator.validateURL(null);

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			FIPSModeValidator.validateURL(
				"ldaps://" + RandomTestUtil.randomString());

			FIPSModeTestUtil.assertSecurityException(
				"protocol scheme", () -> FIPSModeValidator.validateURL(""));
			FIPSModeTestUtil.assertSecurityException(
				"protocol scheme",
				() -> FIPSModeValidator.validateURL(
					"ldap://" + RandomTestUtil.randomString()));
			FIPSModeTestUtil.assertSecurityException(
				"protocol scheme", () -> FIPSModeValidator.validateURL(null));
		}
	}

	private void _assertClusterLinkConfigurationViolation(
			String expectedMessage, String fileName, String propsKeysFieldName)
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					propsKeysFieldName,
					String.valueOf(
						DependenciesTestUtil.getDependencyAsFile(
							FIPSModeValidatorTest.class, fileName)),
					false)) {

			_assertViolation(
				expectedMessage, "Cluster Link",
				_getClusterLinkConfigurationViolations());
		}
	}

	private void _assertJWSAlgorithmNotAllowed(String algorithm) {
		FIPSModeTestUtil.assertSecurityException(
			"is not allowed in FIPS mode",
			() -> FIPSModeValidator.validateJWSAlgorithm(algorithm));
	}

	private void _assertSecurityException(
		String expectedMessage, String methodName, Class<?>[] parameterTypes,
		Object... parameterValues) {

		FIPSModeTestUtil.assertSecurityException(
			expectedMessage,
			() -> ReflectionTestUtil.invoke(
				FIPSModeValidator.class, methodName, parameterTypes,
				parameterValues));
	}

	private void _assertViolation(
		String expectedMessage, String label, List<String> violations) {

		Assert.assertEquals(violations.toString(), 1, violations.size());

		String violation = violations.get(0);

		Assert.assertTrue(violation.contains(expectedMessage));
		Assert.assertTrue(violation.startsWith(label + "\n- "));
	}

	private Provider _createProvider(String name) {
		return new Provider(
			name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString()) {
		};
	}

	private Random _createSecureRandom(String providerName) {
		Provider provider = _createProvider(providerName);

		return new Random() {

			public Provider getProvider() {
				return provider;
			}

		};
	}

	private List<String> _getAllowedPropertyValuesViolations(String value) {
		List<ValidationError> validationErrors = new ArrayList<>();

		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateAllowedPropertyValues",
			new Class<?>[] {Function.class, List.class},
			(Function<String, String>)key -> value, validationErrors);

		return _getViolations(validationErrors);
	}

	private List<String> _getClusterLinkConfigurationViolations() {
		List<ValidationError> validationErrors = new ArrayList<>();

		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateClusterLinkConfiguration",
			new Class<?>[] {List.class}, validationErrors);

		return _getViolations(validationErrors);
	}

	private Element _getElement(String fileName, String tagName)
		throws Exception {

		Document document = ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_readChannelPropertiesDocument",
			new Class<?>[] {String.class},
			String.valueOf(
				DependenciesTestUtil.getDependencyAsFile(
					FIPSModeValidatorTest.class, fileName)));

		NodeList nodeList = document.getElementsByTagName(tagName);

		return (Element)nodeList.item(0);
	}

	private List<String> _getKeyEncryptionKeyViolations() {
		return _getViolations(
			ReflectionTestUtil.invoke(
				FIPSModeValidator.class, "_getKeyEncryptionKeyValidationErrors",
				new Class<?>[0]));
	}

	private List<String> _getPortalPropertiesViolations() {
		return _getViolations(
			ReflectionTestUtil.invoke(
				FIPSModeValidator.class, "_getPortalPropertiesValidationErrors",
				new Class<?>[0]));
	}

	private List<String> _getRequiredPropertyValuesViolations(
		Function<String, String> function,
		Map<String, String[]> propertiesMap) {

		List<ValidationError> validationErrors = new ArrayList<>();

		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validateRequiredPropertyValues",
			new Class<?>[] {Function.class, Map.class, List.class}, function,
			propertiesMap, validationErrors);

		return _getViolations(validationErrors);
	}

	private String _getViolation(String label, String message) {
		return label + "\n- " + message;
	}

	private List<String> _getViolations(
		List<ValidationError> validationErrors) {

		return TransformUtil.transform(
			validationErrors,
			validationError -> {
				ValidationErrorType validationErrorType =
					validationError.getValidationErrorType();

				return _getViolation(
					validationErrorType.getLabel(),
					validationError.getErrorMessage());
			});
	}

	private AutoCloseable _setCompanyKeyResolver(
		CompanyKeyResolver companyKeyResolver) {

		Snapshot<CompanyKeyResolver> snapshot = Mockito.mock(Snapshot.class);

		Mockito.when(
			snapshot.get()
		).thenReturn(
			companyKeyResolver
		);

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			FIPSModeValidator.class, "_companyKeyResolverSnapshot", snapshot);
	}

	private AutoCloseable _setCompanyLocalService(long... companyIds) {
		CompanyLocalService companyLocalService = Mockito.mock(
			CompanyLocalService.class);

		Mockito.doAnswer(
			invocation -> {
				UnsafeConsumer<Long, Exception> unsafeConsumer =
					invocation.getArgument(0);

				for (long curCompanyId : companyIds) {
					unsafeConsumer.accept(curCompanyId);
				}

				return null;
			}
		).when(
			companyLocalService
		).forEachCompanyId(
			Mockito.any()
		);

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			CompanyLocalServiceUtil.class, "_service", companyLocalService);
	}

	private AutoCloseable _setEncryptor(Key key) throws Exception {
		Snapshot<Encryptor> snapshot = Mockito.mock(Snapshot.class);

		Encryptor encryptor = Mockito.mock(Encryptor.class);

		Mockito.when(
			encryptor.generateKey()
		).thenReturn(
			key
		);

		Mockito.when(
			snapshot.get()
		).thenReturn(
			encryptor
		);

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			EncryptorUtil.class, "_encryptorSnapshot", snapshot);
	}

}