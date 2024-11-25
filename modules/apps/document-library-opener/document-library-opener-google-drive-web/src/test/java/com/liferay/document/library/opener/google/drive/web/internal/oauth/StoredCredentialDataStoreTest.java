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
		for (int i = 0; i < _COMPANIES_COUNT; i++) {
			_storedCredentialDataStores[i] = new StoredCredentialDataStore(
				RandomTestUtil.randomLong(), null,
				RandomTestUtil.randomString());

			for (int j = 0; j < _KEYS_COUNT; j++) {
				_storedCredentials[i][j] = _addStoredCredential();
			}
		}

		for (int i = 0; i < _KEYS_COUNT; i++) {
			_keys[i] = String.valueOf(RandomTestUtil.randomLong());
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
		_forEachKey(StoredCredentialDataStore::set);

		_storedCredentialDataStores[0].clear();

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) -> {
				if (Objects.equals(
						storedCredentialDataStore.getId(),
						_storedCredentialDataStores[0].getId())) {

					Assert.assertNull(storedCredentialDataStore.get(key));
				}
				else {
					Assert.assertEquals(
						storedCredential, storedCredentialDataStore.get(key));
				}
			});
	}

	@Test
	public void testContainsKey() throws Exception {
		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) ->
				Assert.assertFalse(storedCredentialDataStore.containsKey(key)));

		_forEachKey(StoredCredentialDataStore::set);

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) ->
				Assert.assertTrue(storedCredentialDataStore.containsKey(key)));
	}

	@Test
	public void testContainsValue() throws Exception {
		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) ->
				Assert.assertFalse(
					storedCredentialDataStore.containsValue(storedCredential)));

		_forEachKey(StoredCredentialDataStore::set);

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) ->
				Assert.assertTrue(
					storedCredentialDataStore.containsValue(storedCredential)));
	}

	@Test
	public void testDelete() throws Exception {
		_forEachKey(StoredCredentialDataStore::set);

		_storedCredentialDataStores[0].delete(_keys[0]);

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) -> {
				if (Objects.equals(
						storedCredentialDataStore.getId(),
						_storedCredentialDataStores[0].getId()) &&
					Objects.equals(key, _keys[0])) {

					Assert.assertNull(storedCredentialDataStore.get(key));
				}
				else {
					Assert.assertEquals(
						storedCredential, storedCredentialDataStore.get(key));
				}
			});
	}

	@Test
	public void testGet() throws Exception {
		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertNull(
				storedCredentialDataStore.get(
					String.valueOf(RandomTestUtil.randomLong()))));
	}

	@Test
	public void testIsEmpty() throws Exception {
		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertTrue(
				storedCredentialDataStore.isEmpty()));

		_forEachKey(StoredCredentialDataStore::set);

		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertFalse(
				storedCredentialDataStore.isEmpty()));
	}

	@Test
	public void testKeySet() throws Exception {
		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> {
				Set<String> keySet = storedCredentialDataStore.keySet();

				Assert.assertTrue(keySet.isEmpty());
			});

		_forEachKey(StoredCredentialDataStore::set);

		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertEquals(
				_getCompanyKeySet(storedCredentialDataStore.getId()),
				storedCredentialDataStore.keySet()));
	}

	@Test
	public void testSet() throws Exception {
		_forEachKey(StoredCredentialDataStore::set);

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) ->
				Assert.assertEquals(
					storedCredential, storedCredentialDataStore.get(key)));
	}

	@Test
	public void testSize() throws Exception {
		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertEquals(
				0, storedCredentialDataStore.size()));

		_forEachKey(StoredCredentialDataStore::set);

		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertEquals(
				_KEYS_COUNT, storedCredentialDataStore.size()));
	}

	@Test
	public void testValues() throws Exception {
		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> {
				Collection<StoredCredential> values =
					storedCredentialDataStore.values();

				Assert.assertTrue(values.isEmpty());
			});

		_forEachKey(StoredCredentialDataStore::set);

		_forEachStoredCredentialDataStore(
			storedCredentialDataStore -> Assert.assertEquals(
				_getCompanyValues(storedCredentialDataStore.getId()),
				new HashSet<StoredCredential>(
					storedCredentialDataStore.values())));
	}

	private StoredCredential _addStoredCredential() {
		StoredCredential storedCredential = new StoredCredential();

		storedCredential.setAccessToken(RandomTestUtil.randomString());
		storedCredential.setExpirationTimeMilliseconds(
			RandomTestUtil.randomLong());
		storedCredential.setRefreshToken(RandomTestUtil.randomString());

		return storedCredential;
	}

	private void _forEachKey(
			UnsafeTriConsumer
				<StoredCredentialDataStore, String, StoredCredential, Exception>
					unsafeTriConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANIES_COUNT; i++) {
			for (int j = 0; j < _KEYS_COUNT; j++) {
				unsafeTriConsumer.accept(
					_storedCredentialDataStores[i], _keys[j],
					_storedCredentials[i][j]);
			}
		}
	}

	private void _forEachStoredCredentialDataStore(
			UnsafeConsumer<StoredCredentialDataStore, Exception> unsafeConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANIES_COUNT; i++) {
			unsafeConsumer.accept(_storedCredentialDataStores[i]);
		}
	}

	private Set<String> _getCompanyKeySet(String id) throws Exception {
		Set<String> keySet = new HashSet<>();

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) -> {
				if (Objects.equals(storedCredentialDataStore.getId(), id)) {
					keySet.add(key);
				}
			});

		return keySet;
	}

	private Set<StoredCredential> _getCompanyValues(String id)
		throws Exception {

		Set<StoredCredential> values = new HashSet<>();

		_forEachKey(
			(storedCredentialDataStore, key, storedCredential) -> {
				if (Objects.equals(storedCredentialDataStore.getId(), id)) {
					values.add(storedCredential);
				}
			});

		return values;
	}

	private static final int _COMPANIES_COUNT = 3;

	private static final int _KEYS_COUNT = 3;

	private static final MockedStatic<ClusterExecutorUtil>
		_clusterExecutorUtilMockedStatic = Mockito.mockStatic(
			ClusterExecutorUtil.class);

	private final String[] _keys = new String[_KEYS_COUNT];
	private final StoredCredentialDataStore[] _storedCredentialDataStores =
		new StoredCredentialDataStore[_COMPANIES_COUNT];
	private final StoredCredential[][] _storedCredentials =
		new StoredCredential[_COMPANIES_COUNT][_KEYS_COUNT];

}