/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.google.api.client.auth.oauth2.StoredCredential;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
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
		for (int i = 0; i < _COMPANY_COUNT; i++) {
			_companyIds[i] = RandomTestUtil.randomLong();
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
	public void testAdd() throws Exception {
		_forEachUser(StoredCredentialUtil::add);

		_forEachUser(
			(companyId, userId, storedCredential) -> Assert.assertEquals(
				storedCredential, StoredCredentialUtil.get(companyId, userId)));
	}

	@Test
	public void testClear() throws Exception {
		_forEachUser(StoredCredentialUtil::add);

		StoredCredentialUtil.clear();

		_forEachUser(
			(companyId, userId, storedCredential) -> Assert.assertNull(
				StoredCredentialUtil.get(companyId, userId)));
	}

	@Test
	public void testClearCompany() throws Exception {
		_forEachUser(StoredCredentialUtil::add);

		StoredCredentialUtil.clear(_companyIds[0]);

		_forEachUser(
			(companyId, userId, storedCredential) -> {
				if (companyId == _companyIds[0]) {
					Assert.assertNull(
						StoredCredentialUtil.get(companyId, userId));
				}
				else {
					Assert.assertEquals(
						storedCredential,
						StoredCredentialUtil.get(companyId, userId));
				}
			});
	}

	@Test
	public void testContainsKey() throws Exception {
		_forEachUser(
			(companyId, userId, storedCredential) -> Assert.assertFalse(
				StoredCredentialUtil.containsKey(companyId, userId)));

		_forEachUser(StoredCredentialUtil::add);

		_forEachUser(
			(companyId, userId, storedCredential) -> Assert.assertTrue(
				StoredCredentialUtil.containsKey(companyId, userId)));
	}

	@Test
	public void testContainsValue() throws Exception {
		_forEachUser(
			(companyId, userId, storedCredential) -> Assert.assertFalse(
				StoredCredentialUtil.containsValue(
					companyId, storedCredential)));

		_forEachUser(StoredCredentialUtil::add);

		_forEachUser(
			(companyId, userId, storedCredential) -> Assert.assertTrue(
				StoredCredentialUtil.containsValue(
					companyId, storedCredential)));
	}

	@Test
	public void testDelete() throws Exception {
		_forEachUser(StoredCredentialUtil::add);

		StoredCredentialUtil.delete(_companyIds[0], _userIds[0]);

		_forEachUser(
			(companyId, userId, storedCredential) -> {
				if ((companyId == _companyIds[0]) &&
					Objects.equals(userId, _userIds[0])) {

					Assert.assertNull(
						StoredCredentialUtil.get(companyId, userId));
				}
				else {
					Assert.assertEquals(
						storedCredential,
						StoredCredentialUtil.get(companyId, userId));
				}
			});
	}

	@Test
	public void testGetNonexistentStoredCredential() {
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

		_forEachUser(StoredCredentialUtil::add);

		_forEachCompany(
			companyId -> Assert.assertFalse(
				StoredCredentialUtil.isEmpty(companyId)));
	}

	@Test
	public void testKeySet() throws Exception {
		_forEachCompany(
			companyId -> {
				Set<String> keySet = StoredCredentialUtil.keySet(companyId);

				Assert.assertTrue(keySet.isEmpty());
			});

		_forEachUser(StoredCredentialUtil::add);

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

		_forEachUser(StoredCredentialUtil::add);

		_forEachCompany(
			companyId -> Assert.assertEquals(
				_USER_COUNT, StoredCredentialUtil.size(companyId)));
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
				Collection<StoredCredential> values =
					StoredCredentialUtil.values(companyId);

				Assert.assertTrue(values.isEmpty());
			});

		_forEachUser(StoredCredentialUtil::add);

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

		for (int i = 0; i < _COMPANY_COUNT; i++) {
			unsafeConsumer.accept(_companyIds[i]);
		}
	}

	private void _forEachUser(
			UnsafeTriConsumer<Long, String, StoredCredential, Exception>
				unsafeTriConsumer)
		throws Exception {

		for (int i = 0; i < _COMPANY_COUNT; i++) {
			for (int j = 0; j < _USER_COUNT; j++) {
				unsafeTriConsumer.accept(
					_companyIds[i], _userIds[j], _storedCredentials[i][j]);
			}
		}
	}

	private Set<String> _getCompanyKeySet(long companyId1) throws Exception {
		Set<String> keySet = new HashSet<>();

		_forEachUser(
			(companyId2, userId, storedCredential) -> {
				if (companyId1 == companyId2) {
					keySet.add(userId);
				}
			});

		return keySet;
	}

	private Set<StoredCredential> _getCompanyValues(long companyId1)
		throws Exception {

		Set<StoredCredential> values = new HashSet<>();

		_forEachUser(
			(companyId2, userId, storedCredential) -> {
				if (companyId1 == companyId2) {
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

	private final long[] _companyIds = new long[_COMPANY_COUNT];
	private final StoredCredential[][] _storedCredentials =
		new StoredCredential[_COMPANY_COUNT][_USER_COUNT];
	private final String[] _userIds = new String[_USER_COUNT];

}