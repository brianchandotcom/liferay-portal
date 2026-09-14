/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.company;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.encryptor.CompanyKeyUtil;
import com.liferay.portal.kernel.exception.CompanyKeyResolutionException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.ServiceIndicator;
import com.liferay.portal.security.key.crypto.CryptoManager;
import com.liferay.portal.security.key.crypto.CryptoServiceResult;
import com.liferay.portal.security.key.crypto.exception.CryptoException;
import com.liferay.portal.security.key.internal.profile.configuration.KeyManagerConfiguration;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.security.Key;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * @author Christopher Kian
 */
public class CompanyKeyResolverImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testActivate() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		CompanyKeyCacheEntry companyKeyCacheEntry = _cacheCompanyKey(
			companyKeyResolverImpl);

		Assert.assertNotNull(companyKeyCacheEntry);

		companyKeyResolverImpl.activate(
			HashMapBuilder.<String, Object>put(
				"companyKEKIdentifier", _KEK_IDENTIFIER
			).put(
				"companyKeyCacheTTL", 1
			).build());

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		Assert.assertNull(companyKeyCacheEntry.getKeyBytes());
		Assert.assertTrue(companyKeyCacheEntries.isEmpty());

		KeyManagerConfiguration keyManagerConfiguration =
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_keyManagerConfiguration");

		Assert.assertEquals(
			_KEK_IDENTIFIER, keyManagerConfiguration.companyKEKIdentifier());
		Assert.assertEquals(1, keyManagerConfiguration.companyKeyCacheTTL());

		ScheduledExecutorService scheduledExecutorService =
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_scheduledExecutorService");

		Assert.assertFalse(scheduledExecutorService.isShutdown());

		ScheduledFuture<?> scheduledFuture = ReflectionTestUtil.getFieldValue(
			companyKeyResolverImpl, "_scheduledFuture");

		Assert.assertNotNull(scheduledFuture);

		companyKeyResolverImpl.activate(
			HashMapBuilder.<String, Object>put(
				"companyKEKIdentifier", _KEK_IDENTIFIER
			).put(
				"companyKeyCacheTTL", 1
			).build());

		Assert.assertNotSame(
			scheduledFuture,
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_scheduledFuture"));
		Assert.assertSame(
			scheduledExecutorService,
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_scheduledExecutorService"));
		Assert.assertTrue(scheduledFuture.isCancelled());

		companyKeyResolverImpl.activate(
			HashMapBuilder.<String, Object>put(
				"companyKEKIdentifier", _KEK_IDENTIFIER
			).put(
				"companyKeyCacheTTL", 0
			).build());

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_scheduledFuture"));

		companyKeyResolverImpl.deactivate();
	}

	@Test
	public void testDeactivate() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		CompanyKeyCacheEntry companyKeyCacheEntry = _cacheCompanyKey(
			companyKeyResolverImpl);

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		ScheduledExecutorService scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();

		ReflectionTestUtil.setFieldValue(
			companyKeyResolverImpl, "_scheduledExecutorService",
			scheduledExecutorService);
		ReflectionTestUtil.setFieldValue(
			companyKeyResolverImpl, "_scheduledFuture",
			scheduledExecutorService.scheduleWithFixedDelay(
				() -> {
				},
				1, 1, TimeUnit.HOURS));

		companyKeyResolverImpl.deactivate();

		Assert.assertNull(companyKeyCacheEntry.getKeyBytes());
		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_scheduledExecutorService"));
		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				companyKeyResolverImpl, "_scheduledFuture"));
		Assert.assertTrue(companyKeyCacheEntries.isEmpty());
		Assert.assertTrue(scheduledExecutorService.isShutdown());
	}

	@Test
	public void testDeserializeKey() throws Exception {
		_testDeserializeKey();
		_testDeserializeKeyWithChangedSerializedKey();
		_testDeserializeKeyWithDecryptFailure();
		_testDeserializeKeyWithExpiredCacheEntry();
		_testDeserializeKeyWithExpiredCacheEntryForOtherCompany();
		_testDeserializeKeyWithMalformedSerializedKey();
		_testDeserializeKeyWithMultipleCompanies();
		_testDeserializeKeyWithoutCache();
		_testDeserializeKeyWithUnsupportedVersion();
	}

	@Test
	public void testIsEnabled() throws Exception {
		_testIsEnabled();
		_testIsEnabledInFIPSMode();
	}

	@Test
	public void testSerializeKey() throws Exception {
		_testSerializeKey();
		_testSerializeKeyWithEncryptFailure();
		_testSerializeKeyWithoutKEKIdentifier();
		_testSerializeKeyWithoutKEKProvider();
		_testSerializeKeyWithUnregisteredKEKProvider();
		_testSerializeKeyWithWildcardKEKProvider();
	}

	private void _assertDeserializeKeyFails(
		CompanyKeyResolverImpl companyKeyResolverImpl, String serializedKey) {

		try {
			companyKeyResolverImpl.deserializeKey(_COMPANY_ID_1, serializedKey);

			Assert.fail();
		}
		catch (CompanyKeyResolutionException companyKeyResolutionException) {
		}
	}

	private void _assertSerializeKeyFails(
		CompanyKeyResolverImpl companyKeyResolverImpl) {

		try {
			companyKeyResolverImpl.serializeKey(_COMPANY_ID_1, _key1);

			Assert.fail();
		}
		catch (CompanyKeyResolutionException companyKeyResolutionException) {
		}
	}

	private CompanyKeyCacheEntry _cacheCompanyKey(
			CompanyKeyResolverImpl companyKeyResolverImpl)
		throws Exception {

		_mockDecrypt(_COMPANY_ID_1, _CIPHERTEXT_1, _KEY_BYTES_1);

		companyKeyResolverImpl.deserializeKey(
			_COMPANY_ID_1, _serialize(_CIPHERTEXT_1));

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		return companyKeyCacheEntries.get(_COMPANY_ID_1);
	}

	private CompanyKeyResolverImpl _createCompanyKeyResolverImpl(
			int companyKeyCacheTTL)
		throws Exception {

		CompanyKeyResolverImpl companyKeyResolverImpl =
			new CompanyKeyResolverImpl();

		_keyManagerConfiguration = Mockito.mock(KeyManagerConfiguration.class);

		Mockito.when(
			_keyManagerConfiguration.companyKEKIdentifier()
		).thenReturn(
			_KEK_IDENTIFIER
		);

		Mockito.when(
			_keyManagerConfiguration.companyKeyCacheTTL()
		).thenReturn(
			companyKeyCacheTTL
		);

		_keyManagerProfile = Mockito.mock(KeyManagerProfile.class);

		_mockCompanyKEKProviderId(_KEK_PROVIDER_ID);

		_keyManagerProfileRegistry = Mockito.mock(
			KeyManagerProfileRegistry.class);

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			_keyManagerProfile
		);

		_cryptoManager = Mockito.mock(CryptoManager.class);

		Mockito.when(
			_cryptoManager.getCryptoProviderIds(ArgumentMatchers.anyLong())
		).thenReturn(
			Collections.singletonList(_KEK_PROVIDER_ID)
		);

		ReflectionTestUtil.setFieldValue(
			companyKeyResolverImpl, "_cryptoManager", _cryptoManager);

		ReflectionTestUtil.setFieldValue(
			companyKeyResolverImpl, "_keyAlgorithm", _KEY_ALGORITHM);
		ReflectionTestUtil.setFieldValue(
			companyKeyResolverImpl, "_keyManagerConfiguration",
			_keyManagerConfiguration);
		ReflectionTestUtil.setFieldValue(
			companyKeyResolverImpl, "_keyManagerProfileRegistry",
			_keyManagerProfileRegistry);

		return companyKeyResolverImpl;
	}

	private Map<Long, CompanyKeyCacheEntry> _getCompanyKeyCacheEntries(
		CompanyKeyResolverImpl companyKeyResolverImpl) {

		return ReflectionTestUtil.getFieldValue(
			companyKeyResolverImpl, "_companyKeyCacheEntries");
	}

	private void _mockCompanyKEKIdentifier(String companyKEKIdentifier) {
		Mockito.when(
			_keyManagerConfiguration.companyKEKIdentifier()
		).thenReturn(
			companyKEKIdentifier
		);
	}

	private void _mockCompanyKEKProviderId(String companyKEKProviderId) {
		Mockito.when(
			_keyManagerProfile.getCompanyKEKProviderId()
		).thenReturn(
			companyKEKProviderId
		);
	}

	private byte[] _mockDecrypt(
			long companyId, byte[] ciphertext, byte[] keyBytes)
		throws Exception {

		byte[] providerKeyBytes = keyBytes.clone();

		Mockito.when(
			_cryptoManager.decrypt(
				AdditionalMatchers.aryEq(ciphertext),
				ArgumentMatchers.eq(companyId),
				ArgumentMatchers.eq(
					new KeyReference(
						_KEK_IDENTIFIER, _KEK_PROVIDER_ID,
						KeyReference.Type.CRYPTO)))
		).thenReturn(
			new CryptoServiceResult<>(_serviceIndicator, providerKeyBytes)
		);

		return providerKeyBytes;
	}

	private void _mockDecryptFailure() throws Exception {
		Mockito.when(
			_cryptoManager.decrypt(
				ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
				ArgumentMatchers.any())
		).thenThrow(
			new CryptoException(RandomTestUtil.randomString())
		);
	}

	private String _serialize(byte[] ciphertext) {
		WrappedCompanyKey wrappedCompanyKey = new WrappedCompanyKey(
			ciphertext, _KEK_IDENTIFIER, _KEK_PROVIDER_ID);

		return wrappedCompanyKey.serialize();
	}

	private void _testDeserializeKey() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		String serializedKey = _serialize(_CIPHERTEXT_1);

		byte[] providerKeyBytes = _mockDecrypt(
			_COMPANY_ID_1, _CIPHERTEXT_1, _KEY_BYTES_1);

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey));
		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey));

		Mockito.verify(
			_cryptoManager, Mockito.times(1)
		).decrypt(
			ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
			ArgumentMatchers.any()
		);

		Assert.assertArrayEquals(_KEY_BYTES_1, providerKeyBytes);
	}

	private void _testDeserializeKeyWithChangedSerializedKey()
		throws Exception {

		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		_mockDecrypt(_COMPANY_ID_1, _CIPHERTEXT_1, _KEY_BYTES_1);

		companyKeyResolverImpl.deserializeKey(
			_COMPANY_ID_1, _serialize(_CIPHERTEXT_1));

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		CompanyKeyCacheEntry companyKeyCacheEntry = companyKeyCacheEntries.get(
			_COMPANY_ID_1);

		byte[] changedCiphertext = _CIPHERTEXT_1.clone();

		changedCiphertext[0] = (byte)(changedCiphertext[0] + 1);

		_mockDecrypt(_COMPANY_ID_1, changedCiphertext, _KEY_BYTES_1);

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, _serialize(changedCiphertext)));

		Mockito.verify(
			_cryptoManager, Mockito.times(2)
		).decrypt(
			ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
			ArgumentMatchers.any()
		);

		Assert.assertNull(companyKeyCacheEntry.getKeyBytes());
	}

	private void _testDeserializeKeyWithDecryptFailure() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		_cacheCompanyKey(companyKeyResolverImpl);

		String serializedKey = _serialize(_CIPHERTEXT_1);

		_mockDecryptFailure();

		// A cached key still resolves while the KEK is unreachable, which is
		// the last known good behavior LPD-88890 asks for. Eviction on key
		// revocation is deferred to LPD-88888, so this is not a guarantee that
		// a revoked KEK stops resolving within the cache TTL

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey));

		companyKeyResolverImpl = _createCompanyKeyResolverImpl(
			RandomTestUtil.randomInt(1, 1000));

		_mockDecryptFailure();

		_assertDeserializeKeyFails(companyKeyResolverImpl, serializedKey);
	}

	private void _testDeserializeKeyWithExpiredCacheEntry() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		String serializedKey = _serialize(_CIPHERTEXT_1);

		_mockDecrypt(_COMPANY_ID_1, _CIPHERTEXT_1, _KEY_BYTES_1);

		companyKeyResolverImpl.deserializeKey(_COMPANY_ID_1, serializedKey);

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		CompanyKeyCacheEntry expiredCompanyKeyCacheEntry =
			new CompanyKeyCacheEntry(
				System.currentTimeMillis() - 1, _KEY_BYTES_1, serializedKey);

		companyKeyCacheEntries.put(_COMPANY_ID_1, expiredCompanyKeyCacheEntry);

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey));

		Mockito.verify(
			_cryptoManager, Mockito.times(2)
		).decrypt(
			ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
			ArgumentMatchers.any()
		);

		Assert.assertNull(expiredCompanyKeyCacheEntry.getKeyBytes());
	}

	private void _testDeserializeKeyWithExpiredCacheEntryForOtherCompany()
		throws Exception {

		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		CompanyKeyCacheEntry expiredCompanyKeyCacheEntry =
			new CompanyKeyCacheEntry(
				System.currentTimeMillis() - 1, _KEY_BYTES_1,
				_serialize(_CIPHERTEXT_1));

		companyKeyCacheEntries.put(_COMPANY_ID_1, expiredCompanyKeyCacheEntry);

		_mockDecrypt(_COMPANY_ID_2, _CIPHERTEXT_2, _KEY_BYTES_2);

		Assert.assertEquals(
			_key2,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_2, _serialize(_CIPHERTEXT_2)));

		Assert.assertFalse(companyKeyCacheEntries.containsKey(_COMPANY_ID_1));
		Assert.assertNull(expiredCompanyKeyCacheEntry.getKeyBytes());
	}

	private void _testDeserializeKeyWithMalformedSerializedKey()
		throws Exception {

		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		_assertDeserializeKeyFails(
			companyKeyResolverImpl, RandomTestUtil.randomString());

		String body = StringBundler.concat(
			CompanyKeyUtil.WRAPPED_KEY_PREFIX,
			CompanyKeyUtil.WRAPPED_KEY_VERSION, ":", _KEK_PROVIDER_ID, ":",
			_KEK_IDENTIFIER);

		_assertDeserializeKeyFails(companyKeyResolverImpl, body + "|}");
		_assertDeserializeKeyFails(companyKeyResolverImpl, body + "|=}");
		_assertDeserializeKeyFails(
			companyKeyResolverImpl, body + "|not valid base64}");
	}

	private void _testDeserializeKeyWithMultipleCompanies() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		String serializedKey1 = _serialize(_CIPHERTEXT_1);
		String serializedKey2 = _serialize(_CIPHERTEXT_2);

		_mockDecrypt(_COMPANY_ID_1, _CIPHERTEXT_1, _KEY_BYTES_1);
		_mockDecrypt(_COMPANY_ID_2, _CIPHERTEXT_2, _KEY_BYTES_2);

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey1));
		Assert.assertEquals(
			_key2,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_2, serializedKey2));

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey1));
		Assert.assertEquals(
			_key2,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_2, serializedKey2));

		Mockito.verify(
			_cryptoManager, Mockito.times(2)
		).decrypt(
			ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
			ArgumentMatchers.any()
		);
	}

	private void _testDeserializeKeyWithoutCache() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(0);

		String serializedKey = _serialize(_CIPHERTEXT_1);

		_mockDecrypt(_COMPANY_ID_1, _CIPHERTEXT_1, _KEY_BYTES_1);

		companyKeyResolverImpl.deserializeKey(_COMPANY_ID_1, serializedKey);
		companyKeyResolverImpl.deserializeKey(_COMPANY_ID_1, serializedKey);

		Mockito.verify(
			_cryptoManager, Mockito.times(2)
		).decrypt(
			ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
			ArgumentMatchers.any()
		);

		Map<Long, CompanyKeyCacheEntry> companyKeyCacheEntries =
			_getCompanyKeyCacheEntries(companyKeyResolverImpl);

		Assert.assertTrue(companyKeyCacheEntries.isEmpty());
	}

	private void _testDeserializeKeyWithUnsupportedVersion() throws Exception {
		WrappedCompanyKey wrappedCompanyKey = new WrappedCompanyKey(
			_CIPHERTEXT_1, _KEK_IDENTIFIER, _KEK_PROVIDER_ID);

		String serializedKey = StringUtil.replaceFirst(
			wrappedCompanyKey.serialize(),
			CompanyKeyUtil.WRAPPED_KEY_PREFIX +
				CompanyKeyUtil.WRAPPED_KEY_VERSION,
			CompanyKeyUtil.WRAPPED_KEY_PREFIX.concat("v2"));

		Assert.assertTrue(CompanyKeyUtil.isWrappedKey(serializedKey));

		_assertDeserializeKeyFails(
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000)),
			serializedKey);
	}

	private void _testIsEnabled() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		boolean fipsEnabled = PropsValues.FIPS_ENABLED;

		try {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "FIPS_ENABLED", false);

			Assert.assertTrue(companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));

			_mockCompanyKEKIdentifier(null);

			Assert.assertFalse(companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));

			_mockCompanyKEKIdentifier("");

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					CompanyKeyResolverImpl.class.getName(),
					LoggerTestUtil.WARN)) {

				Assert.assertFalse(
					companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 0, logEntries.size());
			}

			_mockCompanyKEKIdentifier("   ");

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					CompanyKeyResolverImpl.class.getName(),
					LoggerTestUtil.WARN)) {

				Assert.assertFalse(
					companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertEquals(
					StringBundler.concat(
						"Company key wrapping is inactive because the KEK ",
						"identifier is blank for company ", _COMPANY_ID_1),
					logEntry.getMessage());
			}

			ReflectionTestUtil.setFieldValue(
				companyKeyResolverImpl, "_keyManagerConfiguration", null);

			Assert.assertFalse(companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "FIPS_ENABLED", fipsEnabled);
		}
	}

	private void _testIsEnabledInFIPSMode() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		boolean fipsEnabled = PropsValues.FIPS_ENABLED;

		try {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "FIPS_ENABLED", true);

			Assert.assertTrue(companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));

			_mockCompanyKEKIdentifier(null);

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					CompanyKeyResolverImpl.class.getName(),
					LoggerTestUtil.WARN)) {

				Assert.assertFalse(
					companyKeyResolverImpl.isEnabled(_COMPANY_ID_1));

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertEquals(
					StringBundler.concat(
						"The company key is stored in plaintext in FIPS mode ",
						"because the KEK identifier is not configured for ",
						"company ", _COMPANY_ID_1),
					logEntry.getMessage());
			}
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "FIPS_ENABLED", fipsEnabled);
		}
	}

	private void _testSerializeKey() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		byte[][] plaintexts = new byte[1][];

		Mockito.when(
			_cryptoManager.encrypt(
				ArgumentMatchers.eq(_COMPANY_ID_1),
				ArgumentMatchers.eq(
					new KeyReference(
						_KEK_IDENTIFIER, _KEK_PROVIDER_ID,
						KeyReference.Type.CRYPTO)),
				ArgumentMatchers.any(byte[].class))
		).thenAnswer(
			invocationOnMock -> {
				plaintexts[0] = invocationOnMock.getArgument(2);

				return new CryptoServiceResult<>(
					_serviceIndicator, _CIPHERTEXT_1.clone());
			}
		);

		String serializedKey = companyKeyResolverImpl.serializeKey(
			_COMPANY_ID_1, _key1);

		WrappedCompanyKey wrappedCompanyKey = WrappedCompanyKey.parse(
			_COMPANY_ID_1, serializedKey);

		Assert.assertArrayEquals(
			_CIPHERTEXT_1, wrappedCompanyKey.getCiphertext());
		Assert.assertEquals(_KEK_IDENTIFIER, wrappedCompanyKey.getIdentifier());
		Assert.assertEquals(
			_KEK_PROVIDER_ID, wrappedCompanyKey.getProviderId());

		Assert.assertArrayEquals(_KEY_BYTES_1, _key1.getEncoded());
		Assert.assertArrayEquals(_KEY_BYTES_1, plaintexts[0]);

		Assert.assertEquals(
			_key1,
			companyKeyResolverImpl.deserializeKey(
				_COMPANY_ID_1, serializedKey));
		Assert.assertTrue(CompanyKeyUtil.isWrappedKey(serializedKey));

		Mockito.verify(
			_cryptoManager, Mockito.never()
		).decrypt(
			ArgumentMatchers.any(), ArgumentMatchers.anyLong(),
			ArgumentMatchers.any()
		);
	}

	private void _testSerializeKeyWithEncryptFailure() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		Mockito.when(
			_cryptoManager.encrypt(
				ArgumentMatchers.anyLong(), ArgumentMatchers.any(),
				ArgumentMatchers.any())
		).thenThrow(
			new CryptoException(RandomTestUtil.randomString())
		);

		_assertSerializeKeyFails(companyKeyResolverImpl);
	}

	private void _testSerializeKeyWithoutKEKIdentifier() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		_mockCompanyKEKIdentifier("");

		_assertSerializeKeyFails(companyKeyResolverImpl);
	}

	private void _testSerializeKeyWithoutKEKProvider() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			null
		);

		_assertSerializeKeyFails(companyKeyResolverImpl);
	}

	private void _testSerializeKeyWithUnregisteredKEKProvider()
		throws Exception {

		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		Mockito.when(
			_cryptoManager.getCryptoProviderIds(ArgumentMatchers.anyLong())
		).thenReturn(
			Collections.singletonList(RandomTestUtil.randomString())
		);

		_assertSerializeKeyFails(companyKeyResolverImpl);

		Mockito.verify(
			_cryptoManager, Mockito.never()
		).encrypt(
			ArgumentMatchers.anyLong(), ArgumentMatchers.any(),
			ArgumentMatchers.any()
		);
	}

	private void _testSerializeKeyWithWildcardKEKProvider() throws Exception {
		CompanyKeyResolverImpl companyKeyResolverImpl =
			_createCompanyKeyResolverImpl(RandomTestUtil.randomInt(1, 1000));

		_mockCompanyKEKProviderId("*");

		_assertSerializeKeyFails(companyKeyResolverImpl);
	}

	private static final byte[] _CIPHERTEXT_1 = RandomTestUtil.randomBytes();

	private static final byte[] _CIPHERTEXT_2 = RandomTestUtil.randomBytes();

	private static final long _COMPANY_ID_1 = RandomTestUtil.randomLong();

	private static final long _COMPANY_ID_2 = RandomTestUtil.randomLong();

	private static final String _KEK_IDENTIFIER = RandomTestUtil.randomString();

	private static final String _KEK_PROVIDER_ID =
		RandomTestUtil.randomString();

	private static final String _KEY_ALGORITHM = "AES";

	private static final byte[] _KEY_BYTES_1 = RandomTestUtil.randomBytes();

	private static final byte[] _KEY_BYTES_2 = RandomTestUtil.randomBytes();

	private CryptoManager _cryptoManager;
	private final Key _key1 = new SecretKeySpec(_KEY_BYTES_1, _KEY_ALGORITHM);
	private final Key _key2 = new SecretKeySpec(_KEY_BYTES_2, _KEY_ALGORITHM);
	private KeyManagerConfiguration _keyManagerConfiguration;
	private KeyManagerProfile _keyManagerProfile;
	private KeyManagerProfileRegistry _keyManagerProfileRegistry;
	private final ServiceIndicator _serviceIndicator = new ServiceIndicator(
		true, RandomTestUtil.randomString());

}