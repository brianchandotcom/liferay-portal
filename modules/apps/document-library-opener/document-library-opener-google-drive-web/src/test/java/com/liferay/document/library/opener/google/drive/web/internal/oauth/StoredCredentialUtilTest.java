/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.google.api.client.auth.oauth2.StoredCredential;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
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
public class StoredCredentialUtilTest {

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
			_companyIds[i] = RandomTestUtil.randomLong();

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
	public void testAdd() throws Exception {
		_forEachKey(StoredCredentialUtil::add);

		_forEachKey(
			(companyId, key, storedCredential) -> Assert.assertEquals(
				storedCredential, StoredCredentialUtil.get(companyId, key)));
	}

	@Test
	public void testClear() throws Exception {
		_forEachKey(StoredCredentialUtil::add);

		StoredCredentialUtil.clear();

		_forEachKey(
			(companyId, key, storedCredential) -> Assert.assertNull(
				StoredCredentialUtil.get(companyId, key)));
	}

	@Test
	public void testClearCompany() throws Exception {
		_forEachKey(StoredCredentialUtil::add);

		StoredCredentialUtil.clear(_companyIds[0]);

		_forEachKey(
			(companyId, key, storedCredential) -> {
				if (companyId == _companyIds[0]) {
					Assert.assertNull(StoredCredentialUtil.get(companyId, key));
				}
				else {
					Assert.assertEquals(
						storedCredential,
						StoredCredentialUtil.get(companyId, key));
				}
			});
	}

	@Test
	public void testContainsKey() throws Exception {
		_forEachKey(
			(companyId, key, storedCredential) -> Assert.assertFalse(
				StoredCredentialUtil.containsKey(companyId, key)));

		_forEachKey(StoredCredentialUtil::add);

		_forEachKey(
			(companyId, key, storedCredential) -> Assert.assertTrue(
				StoredCredentialUtil.containsKey(companyId, key)));
	}

	@Test
	public void testContainsValue() throws Exception {
		_forEachKey(
			(companyId, key, storedCredential) -> Assert.assertFalse(
				StoredCredentialUtil.containsValue(
					companyId, storedCredential)));

		_forEachKey(StoredCredentialUtil::add);

		_forEachKey(
			(companyId, key, storedCredential) -> Assert.assertTrue(
				StoredCredentialUtil.containsValue(
					companyId, storedCredential)));
	}

	@Test
	public void testDelete() throws Exception {
		_forEachKey(StoredCredentialUtil::add);

		StoredCredentialUtil.delete(_companyIds[0], _keys[0]);

		_forEachKey(
			(companyId, key, storedCredential) -> {
				if ((companyId == _companyIds[0]) &&
					Objects.equals(key, _keys[0])) {

					Assert.assertNull(StoredCredentialUtil.get(companyId, key));
				}
				else {
					Assert.assertEquals(
						storedCredential,
						StoredCredentialUtil.get(companyId, key));
				}
			});
	}

	@Test
	public void testGet() {
		Assert.assertNull(
			StoredCredentialUtil.get(
				RandomTestUtil.randomLong(),
				String.valueOf(RandomTestUtil.randomLong())));
	}

	@Test
	public void testIsEmpty() throws Exception {
		_forEachCompany(
			companyId -> Assert.assertTrue(
				StoredCredentialUtil.isEmpty(companyId)));

		_forEachKey(StoredCredentialUtil::add);

		_forEachCompany(
			companyId -> Assert.assertFalse(
				StoredCredentialUtil.isEmpty(companyId)));
	}

	@Test
	public void testKeySet() throws Exception {
		_forEachCompany(
			companyId -> {
				Set<String> keys = StoredCredentialUtil.keySet(companyId);

				Assert.assertTrue(keys.isEmpty());
			});

		_forEachKey(StoredCredentialUtil::add);

		_forEachCompany(
			companyId -> Assert.assertEquals(
				_getCompanyKeySet(companyId),
				StoredCredentialUtil.keySet(companyId)));
	}

	@Test
	public void testSize() throws Exception {
		_forEachCompany(
			companyId -> Assert.assertEquals(
				0, StoredCredentialUtil.size(companyId)));

		_forEachKey(StoredCredentialUtil::add);

		_forEachCompany(
			companyId -> Assert.assertEquals(
				_KEYS_COUNT, StoredCredentialUtil.size(companyId)));
	}

	@Test
	public void testStoredCredentialIsSerializable()
		throws ClassNotFoundException {

		StoredCredential storedCredential = _addStoredCredential();

		Serializer serializer = new Serializer();

		serializer.writeObject(storedCredential);

		Deserializer deserializer = new Deserializer(serializer.toByteBuffer());

		StoredCredential deserializedStoredCredential =
			deserializer.readObject();

		Assert.assertEquals(storedCredential, deserializedStoredCredential);
	}

	@Test
	public void testValues() throws Exception {
		_forEachCompany(
			companyId -> {
				Collection<StoredCredential> storedCredentials =
					StoredCredentialUtil.values(companyId);

				Assert.assertTrue(storedCredentials.isEmpty());
			});

		_forEachKey(StoredCredentialUtil::add);

		_forEachCompany(
			companyId -> Assert.assertEquals(
				_getCompanyValues(companyId),
				new HashSet<StoredCredential>(
					StoredCredentialUtil.values(companyId))));
	}

	private StoredCredential _addStoredCredential() {
		StoredCredential storedCredential = new StoredCredential();

		storedCredential.setAccessToken(RandomTestUtil.randomString());
		storedCredential.setExpirationTimeMilliseconds(
			RandomTestUtil.randomLong());
		storedCredential.setRefreshToken(RandomTestUtil.randomString());

		return storedCredential;
	}

	private void _forEachCompany(UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANIES_COUNT; i++) {
			unsafeConsumer.accept(_companyIds[i]);
		}
	}

	private void _forEachKey(
			UnsafeTriConsumer<Long, String, StoredCredential, Exception>
				unsafeTriConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANIES_COUNT; i++) {
			for (int j = 0; j < _KEYS_COUNT; j++) {
				unsafeTriConsumer.accept(
					_companyIds[i], _keys[j], _storedCredentials[i][j]);
			}
		}
	}

	private Set<String> _getCompanyKeySet(long companyId1) throws Exception {
		Set<String> keys = new HashSet<>();

		_forEachKey(
			(companyId2, key, storedCredential) -> {
				if (companyId1 == companyId2) {
					keys.add(key);
				}
			});

		return keys;
	}

	private Set<StoredCredential> _getCompanyValues(long companyId1)
		throws Exception {

		Set<StoredCredential> storedCredentials = new HashSet<>();

		_forEachKey(
			(companyId2, key, storedCredential) -> {
				if (companyId1 == companyId2) {
					storedCredentials.add(storedCredential);
				}
			});

		return storedCredentials;
	}

	private static final int _COMPANIES_COUNT = 3;

	private static final int _KEYS_COUNT = 3;

	private static final MockedStatic<ClusterExecutorUtil>
		_clusterExecutorUtilMockedStatic = Mockito.mockStatic(
			ClusterExecutorUtil.class);

	private final long[] _companyIds = new long[_COMPANIES_COUNT];
	private final String[] _keys = new String[_KEYS_COUNT];
	private final StoredCredential[][] _storedCredentials =
		new StoredCredential[_COMPANIES_COUNT][_KEYS_COUNT];

}