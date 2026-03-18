/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.launch.exception.DuplicateLaunchSetExternalReferenceCodeException;
import com.liferay.launch.exception.NoSuchLaunchSetException;
import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.service.LaunchSetLocalServiceUtil;
import com.liferay.launch.service.persistence.LaunchSetPersistence;
import com.liferay.launch.service.persistence.LaunchSetUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class LaunchSetPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.launch.service"));

	@Before
	public void setUp() {
		_persistence = LaunchSetUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LaunchSet> iterator = _launchSets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchSet launchSet = _persistence.create(pk);

		Assert.assertNotNull(launchSet);

		Assert.assertEquals(launchSet.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		_persistence.remove(newLaunchSet);

		LaunchSet existingLaunchSet = _persistence.fetchByPrimaryKey(
			newLaunchSet.getPrimaryKey());

		Assert.assertNull(existingLaunchSet);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLaunchSet();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchSet newLaunchSet = _persistence.create(pk);

		newLaunchSet.setMvccVersion(RandomTestUtil.nextLong());

		newLaunchSet.setUuid(RandomTestUtil.randomString());

		newLaunchSet.setExternalReferenceCode(RandomTestUtil.randomString());

		newLaunchSet.setCompanyId(RandomTestUtil.nextLong());

		newLaunchSet.setUserId(RandomTestUtil.nextLong());

		newLaunchSet.setCreateDate(RandomTestUtil.nextDate());

		newLaunchSet.setModifiedDate(RandomTestUtil.nextDate());

		newLaunchSet.setDescription(RandomTestUtil.randomString());

		newLaunchSet.setName(RandomTestUtil.randomString());

		newLaunchSet.setStatus(RandomTestUtil.nextInt());

		newLaunchSet.setStatusByUserId(RandomTestUtil.nextLong());

		newLaunchSet.setStatusDate(RandomTestUtil.nextDate());

		_launchSets.add(_persistence.update(newLaunchSet));

		LaunchSet existingLaunchSet = _persistence.findByPrimaryKey(
			newLaunchSet.getPrimaryKey());

		Assert.assertEquals(
			existingLaunchSet.getMvccVersion(), newLaunchSet.getMvccVersion());
		Assert.assertEquals(
			existingLaunchSet.getUuid(), newLaunchSet.getUuid());
		Assert.assertEquals(
			existingLaunchSet.getExternalReferenceCode(),
			newLaunchSet.getExternalReferenceCode());
		Assert.assertEquals(
			existingLaunchSet.getLaunchSetId(), newLaunchSet.getLaunchSetId());
		Assert.assertEquals(
			existingLaunchSet.getCompanyId(), newLaunchSet.getCompanyId());
		Assert.assertEquals(
			existingLaunchSet.getUserId(), newLaunchSet.getUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLaunchSet.getCreateDate()),
			Time.getShortTimestamp(newLaunchSet.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLaunchSet.getModifiedDate()),
			Time.getShortTimestamp(newLaunchSet.getModifiedDate()));
		Assert.assertEquals(
			existingLaunchSet.getDescription(), newLaunchSet.getDescription());
		Assert.assertEquals(
			existingLaunchSet.getName(), newLaunchSet.getName());
		Assert.assertEquals(
			existingLaunchSet.getStatus(), newLaunchSet.getStatus());
		Assert.assertEquals(
			existingLaunchSet.getStatusByUserId(),
			newLaunchSet.getStatusByUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLaunchSet.getStatusDate()),
			Time.getShortTimestamp(newLaunchSet.getStatusDate()));
	}

	@Test(expected = DuplicateLaunchSetExternalReferenceCodeException.class)
	public void testUpdateWithExistingExternalReferenceCode() throws Exception {
		LaunchSet launchSet = addLaunchSet();

		LaunchSet newLaunchSet = addLaunchSet();

		newLaunchSet.setCompanyId(launchSet.getCompanyId());

		newLaunchSet = _persistence.update(newLaunchSet);

		Session session = _persistence.getCurrentSession();

		session.evict(newLaunchSet);

		newLaunchSet.setExternalReferenceCode(
			launchSet.getExternalReferenceCode());

		_persistence.update(newLaunchSet);
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_U() throws Exception {
		_persistence.countByC_U(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_U(0L, 0L);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_S(0L, 0);
	}

	@Test
	public void testCountByC_SArrayable() throws Exception {
		_persistence.countByC_S(
			RandomTestUtil.nextLong(), new int[] {RandomTestUtil.nextInt(), 0});
	}

	@Test
	public void testCountByERC_C() throws Exception {
		_persistence.countByERC_C("", RandomTestUtil.nextLong());

		_persistence.countByERC_C("null", 0L);

		_persistence.countByERC_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		LaunchSet existingLaunchSet = _persistence.findByPrimaryKey(
			newLaunchSet.getPrimaryKey());

		Assert.assertEquals(existingLaunchSet, newLaunchSet);
	}

	@Test(expected = NoSuchLaunchSetException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LaunchSet> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LaunchSet", "mvccVersion", true, "uuid", true,
			"externalReferenceCode", true, "launchSetId", true, "companyId",
			true, "userId", true, "createDate", true, "modifiedDate", true,
			"description", true, "name", true, "status", true, "statusByUserId",
			true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		LaunchSet existingLaunchSet = _persistence.fetchByPrimaryKey(
			newLaunchSet.getPrimaryKey());

		Assert.assertEquals(existingLaunchSet, newLaunchSet);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchSet missingLaunchSet = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLaunchSet);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LaunchSet newLaunchSet1 = addLaunchSet();
		LaunchSet newLaunchSet2 = addLaunchSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLaunchSet1.getPrimaryKey());
		primaryKeys.add(newLaunchSet2.getPrimaryKey());

		Map<Serializable, LaunchSet> launchSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, launchSets.size());
		Assert.assertEquals(
			newLaunchSet1, launchSets.get(newLaunchSet1.getPrimaryKey()));
		Assert.assertEquals(
			newLaunchSet2, launchSets.get(newLaunchSet2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LaunchSet> launchSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(launchSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LaunchSet newLaunchSet = addLaunchSet();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLaunchSet.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LaunchSet> launchSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, launchSets.size());
		Assert.assertEquals(
			newLaunchSet, launchSets.get(newLaunchSet.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LaunchSet> launchSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(launchSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLaunchSet.getPrimaryKey());

		Map<Serializable, LaunchSet> launchSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, launchSets.size());
		Assert.assertEquals(
			newLaunchSet, launchSets.get(newLaunchSet.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LaunchSetLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LaunchSet>() {

				@Override
				public void performAction(LaunchSet launchSet) {
					Assert.assertNotNull(launchSet);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"launchSetId", newLaunchSet.getLaunchSetId()));

		List<LaunchSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LaunchSet existingLaunchSet = result.get(0);

		Assert.assertEquals(existingLaunchSet, newLaunchSet);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"launchSetId", RandomTestUtil.nextLong()));

		List<LaunchSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchSet.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("launchSetId"));

		Object newLaunchSetId = newLaunchSet.getLaunchSetId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"launchSetId", new Object[] {newLaunchSetId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLaunchSetId = result.get(0);

		Assert.assertEquals(existingLaunchSetId, newLaunchSetId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchSet.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("launchSetId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"launchSetId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LaunchSet newLaunchSet = addLaunchSet();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newLaunchSet.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		LaunchSet newLaunchSet = addLaunchSet();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"launchSetId", newLaunchSet.getLaunchSetId()));

		List<LaunchSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(LaunchSet launchSet) {
		Assert.assertEquals(
			launchSet.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				launchSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
		Assert.assertEquals(
			Long.valueOf(launchSet.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				launchSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
	}

	protected LaunchSet addLaunchSet() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchSet launchSet = _persistence.create(pk);

		launchSet.setMvccVersion(RandomTestUtil.nextLong());

		launchSet.setUuid(RandomTestUtil.randomString());

		launchSet.setExternalReferenceCode(RandomTestUtil.randomString());

		launchSet.setCompanyId(RandomTestUtil.nextLong());

		launchSet.setUserId(RandomTestUtil.nextLong());

		launchSet.setCreateDate(RandomTestUtil.nextDate());

		launchSet.setModifiedDate(RandomTestUtil.nextDate());

		launchSet.setDescription(RandomTestUtil.randomString());

		launchSet.setName(RandomTestUtil.randomString());

		launchSet.setStatus(RandomTestUtil.nextInt());

		launchSet.setStatusByUserId(RandomTestUtil.nextLong());

		launchSet.setStatusDate(RandomTestUtil.nextDate());

		_launchSets.add(_persistence.update(launchSet));

		return launchSet;
	}

	private List<LaunchSet> _launchSets = new ArrayList<LaunchSet>();
	private LaunchSetPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1567254830