/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.secret;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.secret.SecretManagerException;
import com.liferay.portal.security.key.secret.SecureSecret;
import com.liferay.portal.security.key.spi.ModuleStatus;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.security.key.spi.secret.SecretVaultProvider;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
public class SecretManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		_secretManagerImpl = new SecretManagerImpl();

		ReflectionTestUtil.setFieldValue(
			_secretManagerImpl, "_keyManagerProfileRegistry",
			_keyManagerProfileRegistry);
		ReflectionTestUtil.setFieldValue(
			_secretManagerImpl, "_serviceTrackerMap", _serviceTrackerMap);
	}

	@Test
	public void testDeleteThrowsWhenNoProviderRegistered() {
		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			null
		);

		Assert.assertThrows(
			SecretManagerException.class,
			() -> _secretManagerImpl.deleteSecret(
				RandomTestUtil.randomLong(), _secretReference(providerId)));
	}

	@Test
	public void testGetKeyReferencesResolvesWildcardThroughActiveProfile()
		throws Exception {

		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_keyManagerProfile.getCompanySecretProviderId()
		).thenReturn(
			providerId
		);

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			_keyManagerProfile
		);

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_secretVaultProvider)
		);

		String identifier = RandomTestUtil.randomString();

		Mockito.when(
			_secretVaultProvider.getSecretIdentifiers(Mockito.anyLong())
		).thenReturn(
			Collections.singletonList(identifier)
		);

		Mockito.when(
			_secretVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		List<KeyReference> keyReferences = _secretManagerImpl.getKeyReferences(
			RandomTestUtil.randomLong(), StringPool.STAR);

		Assert.assertEquals(keyReferences.toString(), 1, keyReferences.size());

		KeyReference keyReference = keyReferences.get(0);

		Assert.assertEquals(identifier, keyReference.getIdentifier());
		Assert.assertEquals(providerId, keyReference.getProviderId());
		Assert.assertEquals(KeyReference.Type.SECRET, keyReference.getType());
	}

	@Test
	public void testGetSecretDelegatesToProvider() throws Exception {
		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_secretVaultProvider)
		);

		long companyId = RandomTestUtil.randomLong();
		String identifier = RandomTestUtil.randomString();

		Mockito.when(
			_secretVaultProvider.getSecret(companyId, identifier)
		).thenReturn(
			new SecureSecret(
				RandomTestUtil.randomBytes(),
				_secretReference(providerId, identifier))
		);

		Mockito.when(
			_secretVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		_secretManagerImpl.getSecret(
			companyId, _secretReference(providerId, identifier));

		Mockito.verify(
			_secretVaultProvider
		).getSecret(
			companyId, identifier
		);
	}

	@Test
	public void testGetSecretThrowsWhenProviderInErrorState() {
		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_secretVaultProvider)
		);

		Mockito.when(
			_secretVaultProvider.getModuleStatus()
		).thenReturn(
			ModuleStatus.ERROR
		);

		Mockito.when(
			_secretVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		Assert.assertThrows(
			SecretManagerException.class,
			() -> _secretManagerImpl.getSecret(
				RandomTestUtil.randomLong(), _secretReference(providerId)));
	}

	@Test
	public void testPutSecretResolvesProviderWildcard() throws Exception {
		_testPutSecretResolvesProviderWildcard(CompanyConstants.SYSTEM);
		_testPutSecretResolvesProviderWildcard(RandomTestUtil.randomLong());
	}

	@Test
	public void testPutSecretRoutesToProvider() throws Exception {
		String providerId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_secretVaultProvider)
		);

		Mockito.when(
			_secretVaultProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		long companyId = RandomTestUtil.randomLong();
		String identifier = RandomTestUtil.randomString();

		try (SecureSecret secureSecret = new SecureSecret(
				_secretReference(providerId, identifier),
				RandomTestUtil.randomString())) {

			_secretManagerImpl.putSecret(companyId, secureSecret);
		}

		Mockito.verify(
			_secretVaultProvider
		).putSecret(
			Mockito.eq(companyId), Mockito.any(SecureSecret.class)
		);
	}

	private KeyReference _secretReference(String providerId) {
		return _secretReference(providerId, RandomTestUtil.randomString());
	}

	private KeyReference _secretReference(
		String providerId, String identifier) {

		return new KeyReference(
			identifier, providerId, KeyReference.Type.SECRET);
	}

	private void _testPutSecretResolvesProviderWildcard(long companyId)
		throws Exception {

		String providerId = RandomTestUtil.randomString();

		if (companyId == CompanyConstants.SYSTEM) {
			Mockito.when(
				_keyManagerProfile.getSystemSecretProviderId()
			).thenReturn(
				providerId
			);
		}
		else {
			Mockito.when(
				_keyManagerProfile.getCompanySecretProviderId()
			).thenReturn(
				providerId
			);
		}

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			_keyManagerProfile
		);

		Mockito.when(
			_serviceTrackerMap.getService(providerId)
		).thenReturn(
			Collections.singletonList(_secretVaultProvider)
		);

		Mockito.when(
			_secretVaultProvider.isAllowedCompany(companyId)
		).thenReturn(
			true
		);

		try (SecureSecret secureSecret = new SecureSecret(
				_secretReference(
					StringPool.STAR, RandomTestUtil.randomString()),
				RandomTestUtil.randomString())) {

			_secretManagerImpl.putSecret(companyId, secureSecret);
		}

		if (companyId == CompanyConstants.SYSTEM) {
			Mockito.verify(
				_keyManagerProfile
			).getSystemSecretProviderId();
		}
		else {
			Mockito.verify(
				_keyManagerProfile
			).getCompanySecretProviderId();
		}
	}

	@Mock
	private KeyManagerProfile _keyManagerProfile;

	@Mock
	private KeyManagerProfileRegistry _keyManagerProfileRegistry;

	private SecretManagerImpl _secretManagerImpl;

	@Mock
	private SecretVaultProvider _secretVaultProvider;

	@Mock
	private ServiceTrackerMap<String, List<SecretVaultProvider>>
		_serviceTrackerMap;

}