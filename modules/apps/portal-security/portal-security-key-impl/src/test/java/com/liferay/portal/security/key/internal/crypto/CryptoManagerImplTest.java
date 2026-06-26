/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.crypto;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.ServiceIndicator;
import com.liferay.portal.security.key.crypto.CryptoManagerException;
import com.liferay.portal.security.key.crypto.CryptoServiceResult;
import com.liferay.portal.security.key.spi.ModuleStatus;
import com.liferay.portal.security.key.spi.crypto.CryptoVaultProvider;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.security.Key;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Christopher Kian
 */
public class CryptoManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		_cryptoManagerImpl = new CryptoManagerImpl();

		ReflectionTestUtil.setFieldValue(
			_cryptoManagerImpl, "_keyManagerProfileRegistry",
			_keyManagerProfileRegistry);
		ReflectionTestUtil.setFieldValue(
			_cryptoManagerImpl, "_serviceTrackerMap", _serviceTrackerMap);
	}

	@Test
	public void testEncryptResolvesProviderWildcard() throws Exception {
		_testEncryptResolvesProviderWildcard(CompanyConstants.SYSTEM);
		_testEncryptResolvesProviderWildcard(RandomTestUtil.randomLong());
	}

	@Test
	public void testEncryptThrowsWhenNoProfileActive() {
		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			null
		);

		Assert.assertThrows(
			CryptoManagerException.class,
			() -> _cryptoManagerImpl.encrypt(
				RandomTestUtil.randomLong(), _keyReference(StringPool.STAR),
				RandomTestUtil.randomBytes()));
	}

	@Test
	public void testEncryptThrowsWhenNoProviderRegistered() {
		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			null
		);

		Assert.assertThrows(
			CryptoManagerException.class,
			() -> _cryptoManagerImpl.encrypt(
				RandomTestUtil.randomLong(), _keyReference(providerId),
				RandomTestUtil.randomBytes()));
	}

	@Test
	public void testEncryptThrowsWhenProviderInErrorState() {
		Mockito.when(
			_cryptoVaultProvider.getModuleStatus()
		).thenReturn(
			ModuleStatus.ERROR
		);

		Mockito.when(
			_cryptoVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_cryptoVaultProvider)
		);

		Assert.assertThrows(
			CryptoManagerException.class,
			() -> _cryptoManagerImpl.encrypt(
				RandomTestUtil.randomLong(), _keyReference(providerId),
				RandomTestUtil.randomBytes()));
	}

	@Test
	public void testExportKey() throws Exception {
		Key key = Mockito.mock(Key.class);

		Mockito.when(
			_cryptoVaultProvider.exportKey(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_cryptoServiceResult(key)
		);

		Mockito.when(
			_cryptoVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_cryptoVaultProvider)
		);

		CryptoServiceResult<Key> cryptoServiceResult =
			_cryptoManagerImpl.exportKey(
				RandomTestUtil.randomLong(), _keyReference(providerId));

		Assert.assertSame(key, cryptoServiceResult.getValue());
	}

	@Test
	public void testImportSecretKeyZerosRawKeyMaterialEvenOnFailure()
		throws Exception {

		Mockito.when(
			_cryptoVaultProvider.importSecretKey(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.any(byte[].class))
		).thenThrow(
			new CryptoManagerException()
		);

		Mockito.when(
			_cryptoVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_cryptoVaultProvider)
		);

		byte[] rawKeyMaterial = RandomTestUtil.randomBytes();

		Assert.assertThrows(
			CryptoManagerException.class,
			() -> _cryptoManagerImpl.importSecretKey(
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomString(), providerId, rawKeyMaterial));

		for (byte b : rawKeyMaterial) {
			Assert.assertEquals(0, b);
		}
	}

	@Test
	public void testUnwrap() throws Exception {
		Mockito.when(
			_cryptoVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		String unwrappedIdentifier = RandomTestUtil.randomString();

		Mockito.when(
			_cryptoVaultProvider.unwrap(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(byte[].class),
				Mockito.anyInt())
		).thenReturn(
			_cryptoServiceResult(unwrappedIdentifier)
		);

		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_cryptoVaultProvider)
		);

		CryptoServiceResult<KeyReference> cryptoServiceResult =
			_cryptoManagerImpl.unwrap(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				_keyReference(providerId), RandomTestUtil.randomString(),
				RandomTestUtil.randomBytes(), RandomTestUtil.randomInt());

		KeyReference keyReference = cryptoServiceResult.getValue();

		Assert.assertEquals(providerId, keyReference.getProviderId());
		Assert.assertEquals(unwrappedIdentifier, keyReference.getIdentifier());
	}

	@Test
	public void testWrap() throws Exception {
		Mockito.when(
			_cryptoVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		byte[] wrappedKeyBytes = RandomTestUtil.randomBytes();

		Mockito.when(
			_cryptoVaultProvider.wrap(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			_cryptoServiceResult(wrappedKeyBytes)
		);

		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_cryptoVaultProvider)
		);

		CryptoServiceResult<byte[]> cryptoServiceResult =
			_cryptoManagerImpl.wrap(
				RandomTestUtil.randomLong(), _keyReference(providerId),
				_keyReference(providerId));

		Assert.assertSame(wrappedKeyBytes, cryptoServiceResult.getValue());
	}

	@Test
	public void testWrapThrowsWhenProvidersDiffer() {
		Assert.assertThrows(
			CryptoManagerException.class,
			() -> _cryptoManagerImpl.wrap(
				RandomTestUtil.randomLong(),
				_keyReference(RandomTestUtil.randomString()),
				_keyReference(RandomTestUtil.randomString())));
	}

	private <T> CryptoServiceResult<T> _cryptoServiceResult(T value) {
		return new CryptoServiceResult<>(
			new ServiceIndicator(true, RandomTestUtil.randomString()), value);
	}

	private KeyReference _keyReference(String providerId) {
		return _keyReference(providerId, RandomTestUtil.randomString());
	}

	private KeyReference _keyReference(String providerId, String identifier) {
		return new KeyReference(
			identifier, providerId, KeyReference.Type.CRYPTO);
	}

	private void _testEncryptResolvesProviderWildcard(long companyId)
		throws Exception {

		String identifier = RandomTestUtil.randomString();

		Mockito.when(
			_cryptoVaultProvider.encrypt(
				Mockito.eq(companyId), Mockito.eq(identifier),
				Mockito.any(byte[].class))
		).thenReturn(
			_cryptoServiceResult(new byte[0])
		);

		Mockito.when(
			_cryptoVaultProvider.isAllowedCompany(companyId)
		).thenReturn(
			true
		);

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			_keyManagerProfile
		);

		String providerId = RandomTestUtil.randomString();

		if (companyId == CompanyConstants.SYSTEM) {
			Mockito.when(
				_keyManagerProfile.getSystemDEKProviderId()
			).thenReturn(
				providerId
			);
		}
		else {
			Mockito.when(
				_keyManagerProfile.getCompanyDEKProviderId()
			).thenReturn(
				providerId
			);
		}

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_cryptoVaultProvider)
		);

		_cryptoManagerImpl.encrypt(
			companyId, _keyReference(StringPool.STAR, identifier),
			RandomTestUtil.randomBytes());

		if (companyId == CompanyConstants.SYSTEM) {
			Mockito.verify(
				_keyManagerProfile, Mockito.atLeastOnce()
			).getSystemDEKProviderId();
		}
		else {
			Mockito.verify(
				_keyManagerProfile, Mockito.atLeastOnce()
			).getCompanyDEKProviderId();
		}
	}

	private CryptoManagerImpl _cryptoManagerImpl;

	@Mock
	private CryptoVaultProvider _cryptoVaultProvider;

	@Mock
	private KeyManagerProfile _keyManagerProfile;

	@Mock
	private KeyManagerProfileRegistry _keyManagerProfileRegistry;

	@Mock
	private ServiceTrackerMap<String, List<CryptoVaultProvider>>
		_serviceTrackerMap;

}