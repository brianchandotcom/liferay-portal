/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.google.api.client.auth.oauth2.StoredCredential;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Marco Galluzzi
 */
public class StoredCredentialDataStoreTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		Mockito.when(
			ClusterExecutorUtil.isEnabled()
		).thenReturn(
			false
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_clusterExecutorUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		for (int i = 0; i < _COMPANY_COUNT; i++) {
			_storedCredentialDataStores[i] = new StoredCredentialDataStore(
				RandomTestUtil.randomLong(), null,
				RandomTestUtil.randomString());
		}

		for (int i = 0; i < _USER_COUNT; i++) {
			_userIds[i] = String.valueOf(RandomTestUtil.randomLong());
		}

		for (int i = 0; i < _COMPANY_COUNT; i++) {
			for (int j = 0; j < _USER_COUNT; j++) {
				_storedCredentials[i][j] = _addStoredCredential();
			}
		}
	}

	@After
	public void tearDown() {
		Map<Long, Map<Long, StoredCredential>> storedCredentials =
			ReflectionTestUtil.getFieldValue(
				StoredCredentialUtil.class, "_storedCredentials");

		storedCredentials.clear();
	}

	@Test
	public void testClear() throws Exception {
		_forEachUser(StoredCredentialDataStore::set);

		_storedCredentialDataStores[0].clear();

		_forEachUser(
			(dataStore, userId, storedCredential) -> {
				if (Objects.equals(
						dataStore.getId(),
						_storedCredentialDataStores[0].getId())) {

					Assert.assertNull(dataStore.get(userId));
				}
				else {
					Assert.assertEquals(
						storedCredential, dataStore.get(userId));
				}
			});
	}

	@Test
	public void testContainsKey() throws Exception {
		_forEachUser(
			(dataStore, userId, storedCredential) -> Assert.assertFalse(
				dataStore.containsKey(userId)));

		_forEachUser(StoredCredentialDataStore::set);

		_forEachUser(
			(dataStore, userId, storedCredential) -> Assert.assertTrue(
				dataStore.containsKey(userId)));
	}

	@Test
	public void testContainsValue() throws Exception {
		_forEachUser(
			(dataStore, userId, storedCredential) -> Assert.assertFalse(
				dataStore.containsValue(storedCredential)));

		_forEachUser(StoredCredentialDataStore::set);

		_forEachUser(
			(dataStore, userId, storedCredential) -> Assert.assertTrue(
				dataStore.containsValue(storedCredential)));
	}

	@Test
	public void testDelete() throws Exception {
		_forEachUser(StoredCredentialDataStore::set);

		_storedCredentialDataStores[0].delete(_userIds[0]);

		_forEachUser(
			(dataStore, userId, storedCredential) -> {
				if (Objects.equals(
						dataStore.getId(),
						_storedCredentialDataStores[0].getId()) &&
					Objects.equals(userId, _userIds[0])) {

					Assert.assertNull(dataStore.get(userId));
				}
				else {
					Assert.assertEquals(
						storedCredential, dataStore.get(userId));
				}
			});
	}

	@Test
	public void testGetNonexistentStoredCredential() throws Exception {
		_forEachDataStore(
			dataStore -> Assert.assertNull(
				dataStore.get(String.valueOf(RandomTestUtil.randomLong()))));
	}

	@Test
	public void testIsEmpty() throws Exception {
		_forEachDataStore(dataStore -> Assert.assertTrue(dataStore.isEmpty()));

		_forEachUser(StoredCredentialDataStore::set);

		_forEachDataStore(dataStore -> Assert.assertFalse(dataStore.isEmpty()));
	}

	@Test
	public void testKeySet() throws Exception {
		_forEachDataStore(
			dataStore -> {
				Set<String> keySet = dataStore.keySet();

				Assert.assertTrue(keySet.isEmpty());
			});

		_forEachUser(StoredCredentialDataStore::set);

		_forEachDataStore(
			dataStore -> Assert.assertEquals(
				_getCompanyKeySet(dataStore.getId()), dataStore.keySet()));
	}

	@Test
	public void testSet() throws Exception {
		_forEachUser(StoredCredentialDataStore::set);

		_forEachUser(
			(dataStore, userId, storedCredential) -> Assert.assertEquals(
				storedCredential, dataStore.get(userId)));
	}

	@Test
	public void testSize() throws Exception {
		_forEachDataStore(
			dataStore -> Assert.assertEquals(0, dataStore.size()));

		_forEachUser(StoredCredentialDataStore::set);

		_forEachDataStore(
			dataStore -> Assert.assertEquals(_USER_COUNT, dataStore.size()));
	}

	@Test
	public void testValues() throws Exception {
		_forEachDataStore(
			dataStore -> {
				Collection<StoredCredential> values = dataStore.values();

				Assert.assertTrue(values.isEmpty());
			});

		_forEachUser(StoredCredentialDataStore::set);

		_forEachDataStore(
			dataStore -> Assert.assertEquals(
				_getCompanyValues(dataStore.getId()),
				new HashSet<StoredCredential>(dataStore.values())));
	}

	private StoredCredential _addStoredCredential() {
		StoredCredential storedCredential = new StoredCredential();

		storedCredential.setAccessToken(RandomTestUtil.randomString());
		storedCredential.setExpirationTimeMilliseconds(
			RandomTestUtil.randomLong());
		storedCredential.setRefreshToken(RandomTestUtil.randomString());

		return storedCredential;
	}

	private void _forEachDataStore(
			UnsafeConsumer<StoredCredentialDataStore, Exception> unsafeConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANY_COUNT; i++) {
			unsafeConsumer.accept(_storedCredentialDataStores[i]);
		}
	}

	private void _forEachUser(
			UnsafeTriConsumer
				<StoredCredentialDataStore, String, StoredCredential, Exception>
					unsafeTriConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANY_COUNT; i++) {
			for (int j = 0; j < _USER_COUNT; j++) {
				unsafeTriConsumer.accept(
					_storedCredentialDataStores[i], _userIds[j],
					_storedCredentials[i][j]);
			}
		}
	}

	private Set<String> _getCompanyKeySet(String id) throws Exception {
		Set<String> keySet = new HashSet<>();

		_forEachUser(
			(dataStore, userId, storedCredential) -> {
				if (Objects.equals(dataStore.getId(), id)) {
					keySet.add(userId);
				}
			});

		return keySet;
	}

	private Set<StoredCredential> _getCompanyValues(String id)
		throws Exception {

		Set<StoredCredential> values = new HashSet<>();

		_forEachUser(
			(dataStore, userId, storedCredential) -> {
				if (Objects.equals(dataStore.getId(), id)) {
					values.add(storedCredential);
				}
			});

		return values;
	}

	private static final int _COMPANY_COUNT = 3;

	private static final int _USER_COUNT = 3;

	private static final MockedStatic<ClusterExecutorUtil>
		_clusterExecutorUtilMockedStatic = Mockito.mockStatic(
			ClusterExecutorUtil.class);

	private final StoredCredentialDataStore[] _storedCredentialDataStores =
		new StoredCredentialDataStore[_COMPANY_COUNT];
	private final StoredCredential[][] _storedCredentials =
		new StoredCredential[_COMPANY_COUNT][_USER_COUNT];
	private final String[] _userIds = new String[_USER_COUNT];

}