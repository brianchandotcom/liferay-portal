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
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.exception.SecretException;
import com.liferay.portal.security.key.spi.ProviderStatus;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.security.key.spi.secret.SecretProvider;
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

		ReflectionTestUtil.setFieldValue(
			_secretManagerImpl, "_keyManagerProfileRegistry",
			_keyManagerProfileRegistry);
		ReflectionTestUtil.setFieldValue(
			_secretManagerImpl, "_serviceTrackerMap", _serviceTrackerMap);
	}

	@Test
	public void testDeleteSecretThrowsWhenNoProviderRegistered() {
		String secretProviderId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(secretProviderId)
		).thenReturn(
			null
		);

		Assert.assertThrows(
			SecretException.class,
			() -> _secretManagerImpl.deleteSecret(
				RandomTestUtil.randomLong(),
				_secretReference(secretProviderId)));
	}

	@Test
	public void testGetKeyReferencesResolvesWildcardThroughActiveProfile()
		throws Exception {

		String secretProviderId = RandomTestUtil.randomString();

		Mockito.when(
			_keyManagerProfile.getCompanySecretProviderId()
		).thenReturn(
			secretProviderId
		);

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			_keyManagerProfile
		);

		String identifier = RandomTestUtil.randomString();

		Mockito.when(
			_secretProvider.getSecretIdentifiers(Mockito.anyLong())
		).thenReturn(
			Collections.singletonList(identifier)
		);

		Mockito.when(
			_secretProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		Mockito.when(
			_serviceTrackerMap.getService(secretProviderId)
		).thenReturn(
			Collections.singletonList(_secretProvider)
		);

		List<KeyReference> keyReferences = _secretManagerImpl.getKeyReferences(
			RandomTestUtil.randomLong(), StringPool.STAR);

		Assert.assertEquals(keyReferences.toString(), 1, keyReferences.size());

		KeyReference keyReference = keyReferences.get(0);

		Assert.assertEquals(KeyReference.Type.SECRET, keyReference.getType());
		Assert.assertEquals(identifier, keyReference.getIdentifier());
		Assert.assertEquals(secretProviderId, keyReference.getProviderId());
	}

	@Test
	public void testGetSecretDelegatesToProvider() throws Exception {
		long companyId = RandomTestUtil.randomLong();
		String identifier = RandomTestUtil.randomString();
		String secretProviderId = RandomTestUtil.randomString();

		Mockito.when(
			_secretProvider.getSecret(companyId, identifier)
		).thenReturn(
			new Secret(
				RandomTestUtil.randomBytes(),
				_secretReference(secretProviderId, identifier))
		);

		Mockito.when(
			_secretProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		Mockito.when(
			_serviceTrackerMap.getService(secretProviderId)
		).thenReturn(
			Collections.singletonList(_secretProvider)
		);

		_secretManagerImpl.getSecret(
			companyId, _secretReference(secretProviderId, identifier));

		Mockito.verify(
			_secretProvider
		).getSecret(
			companyId, identifier
		);
	}

	@Test
	public void testGetSecretThrowsWhenProviderInErrorState() {
		Mockito.when(
			_secretProvider.getProviderStatus()
		).thenReturn(
			ProviderStatus.ERROR
		);

		Mockito.when(
			_secretProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		String secretProviderId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(secretProviderId)
		).thenReturn(
			Collections.singletonList(_secretProvider)
		);

		Assert.assertThrows(
			SecretException.class,
			() -> _secretManagerImpl.getSecret(
				RandomTestUtil.randomLong(),
				_secretReference(secretProviderId)));
	}

	@Test
	public void testPutSecretResolvesProviderWildcard() throws Exception {
		_testPutSecretResolvesProviderWildcard(CompanyConstants.SYSTEM);
		_testPutSecretResolvesProviderWildcard(RandomTestUtil.randomLong());
	}

	@Test
	public void testPutSecretRoutesToProvider() throws Exception {
		Mockito.when(
			_secretProvider.isAllowedCompany(Mockito.anyLong())
		).thenReturn(
			true
		);

		String secretProviderId = RandomTestUtil.randomString();

		Mockito.when(
			_serviceTrackerMap.getService(secretProviderId)
		).thenReturn(
			Collections.singletonList(_secretProvider)
		);

		long companyId = RandomTestUtil.randomLong();
		String identifier = RandomTestUtil.randomString();

		try (Secret secret = new Secret(
				_secretReference(secretProviderId, identifier),
				RandomTestUtil.randomString())) {

			_secretManagerImpl.putSecret(companyId, secret);
		}

		Mockito.verify(
			_secretProvider
		).putSecret(
			Mockito.eq(companyId), Mockito.any(Secret.class)
		);
	}

	private KeyReference _secretReference(String secretProviderId) {
		return _secretReference(
			secretProviderId, RandomTestUtil.randomString());
	}

	private KeyReference _secretReference(
		String secretProviderId, String identifier) {

		return new KeyReference(
			identifier, secretProviderId, KeyReference.Type.SECRET);
	}

	private void _testPutSecretResolvesProviderWildcard(long companyId)
		throws Exception {

		String secretProviderId = RandomTestUtil.randomString();

		if (companyId == CompanyConstants.SYSTEM) {
			Mockito.when(
				_keyManagerProfile.getSystemSecretProviderId()
			).thenReturn(
				secretProviderId
			);
		}
		else {
			Mockito.when(
				_keyManagerProfile.getCompanySecretProviderId()
			).thenReturn(
				secretProviderId
			);
		}

		Mockito.when(
			_keyManagerProfileRegistry.getActiveKeyManagerProfile()
		).thenReturn(
			_keyManagerProfile
		);

		Mockito.when(
			_secretProvider.isAllowedCompany(companyId)
		).thenReturn(
			true
		);

		Mockito.when(
			_serviceTrackerMap.getService(secretProviderId)
		).thenReturn(
			Collections.singletonList(_secretProvider)
		);

		try (Secret secret = new Secret(
				_secretReference(
					StringPool.STAR, RandomTestUtil.randomString()),
				RandomTestUtil.randomString())) {

			_secretManagerImpl.putSecret(companyId, secret);
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

	private final SecretManagerImpl _secretManagerImpl =
		new SecretManagerImpl();

	@Mock
	private SecretProvider _secretProvider;

	@Mock
	private ServiceTrackerMap<String, List<SecretProvider>> _serviceTrackerMap;

}