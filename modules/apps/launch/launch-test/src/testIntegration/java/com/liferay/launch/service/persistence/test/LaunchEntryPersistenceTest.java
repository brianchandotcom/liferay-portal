/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.launch.exception.DuplicateLaunchEntryExternalReferenceCodeException;
import com.liferay.launch.exception.NoSuchLaunchEntryException;
import com.liferay.launch.model.LaunchEntry;
import com.liferay.launch.service.LaunchEntryLocalServiceUtil;
import com.liferay.launch.service.persistence.LaunchEntryPersistence;
import com.liferay.launch.service.persistence.LaunchEntryUtil;
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
public class LaunchEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.launch.service"));

	@Before
	public void setUp() {
		_persistence = LaunchEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LaunchEntry> iterator = _launchEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchEntry launchEntry = _persistence.create(pk);

		Assert.assertNotNull(launchEntry);

		Assert.assertEquals(launchEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		_persistence.remove(newLaunchEntry);

		LaunchEntry existingLaunchEntry = _persistence.fetchByPrimaryKey(
			newLaunchEntry.getPrimaryKey());

		Assert.assertNull(existingLaunchEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLaunchEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchEntry newLaunchEntry = _persistence.create(pk);

		newLaunchEntry.setMvccVersion(RandomTestUtil.nextLong());

		newLaunchEntry.setUuid(RandomTestUtil.randomString());

		newLaunchEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		newLaunchEntry.setCompanyId(RandomTestUtil.nextLong());

		newLaunchEntry.setUserId(RandomTestUtil.nextLong());

		newLaunchEntry.setCreateDate(RandomTestUtil.nextDate());

		newLaunchEntry.setModifiedDate(RandomTestUtil.nextDate());

		newLaunchEntry.setLaunchSetId(RandomTestUtil.nextLong());

		newLaunchEntry.setClassNameId(RandomTestUtil.nextLong());

		newLaunchEntry.setClassPK(RandomTestUtil.nextLong());

		newLaunchEntry.setClassVersion(RandomTestUtil.randomString());

		_launchEntries.add(_persistence.update(newLaunchEntry));

		LaunchEntry existingLaunchEntry = _persistence.findByPrimaryKey(
			newLaunchEntry.getPrimaryKey());

		Assert.assertEquals(
			existingLaunchEntry.getMvccVersion(),
			newLaunchEntry.getMvccVersion());
		Assert.assertEquals(
			existingLaunchEntry.getUuid(), newLaunchEntry.getUuid());
		Assert.assertEquals(
			existingLaunchEntry.getExternalReferenceCode(),
			newLaunchEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingLaunchEntry.getLaunchEntryId(),
			newLaunchEntry.getLaunchEntryId());
		Assert.assertEquals(
			existingLaunchEntry.getCompanyId(), newLaunchEntry.getCompanyId());
		Assert.assertEquals(
			existingLaunchEntry.getUserId(), newLaunchEntry.getUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLaunchEntry.getCreateDate()),
			Time.getShortTimestamp(newLaunchEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLaunchEntry.getModifiedDate()),
			Time.getShortTimestamp(newLaunchEntry.getModifiedDate()));
		Assert.assertEquals(
			existingLaunchEntry.getLaunchSetId(),
			newLaunchEntry.getLaunchSetId());
		Assert.assertEquals(
			existingLaunchEntry.getClassNameId(),
			newLaunchEntry.getClassNameId());
		Assert.assertEquals(
			existingLaunchEntry.getClassPK(), newLaunchEntry.getClassPK());
		Assert.assertEquals(
			existingLaunchEntry.getClassVersion(),
			newLaunchEntry.getClassVersion());
	}

	@Test(expected = DuplicateLaunchEntryExternalReferenceCodeException.class)
	public void testUpdateWithExistingExternalReferenceCode() throws Exception {
		LaunchEntry launchEntry = addLaunchEntry();

		LaunchEntry newLaunchEntry = addLaunchEntry();

		newLaunchEntry.setCompanyId(launchEntry.getCompanyId());

		newLaunchEntry = _persistence.update(newLaunchEntry);

		Session session = _persistence.getCurrentSession();

		session.evict(newLaunchEntry);

		newLaunchEntry.setExternalReferenceCode(
			launchEntry.getExternalReferenceCode());

		_persistence.update(newLaunchEntry);
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
	public void testCountByLaunchSetId() throws Exception {
		_persistence.countByLaunchSetId(RandomTestUtil.nextLong());

		_persistence.countByLaunchSetId(0L);
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByC_C_C(0L, 0L, "null");

		_persistence.countByC_C_C(0L, 0L, (String)null);
	}

	@Test
	public void testCountByERC_C() throws Exception {
		_persistence.countByERC_C("", RandomTestUtil.nextLong());

		_persistence.countByERC_C("null", 0L);

		_persistence.countByERC_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		LaunchEntry existingLaunchEntry = _persistence.findByPrimaryKey(
			newLaunchEntry.getPrimaryKey());

		Assert.assertEquals(existingLaunchEntry, newLaunchEntry);
	}

	@Test(expected = NoSuchLaunchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LaunchEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LaunchEntry", "mvccVersion", true, "uuid", true,
			"externalReferenceCode", true, "launchEntryId", true, "companyId",
			true, "userId", true, "createDate", true, "modifiedDate", true,
			"launchSetId", true, "classNameId", true, "classPK", true,
			"classVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		LaunchEntry existingLaunchEntry = _persistence.fetchByPrimaryKey(
			newLaunchEntry.getPrimaryKey());

		Assert.assertEquals(existingLaunchEntry, newLaunchEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchEntry missingLaunchEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLaunchEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LaunchEntry newLaunchEntry1 = addLaunchEntry();
		LaunchEntry newLaunchEntry2 = addLaunchEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLaunchEntry1.getPrimaryKey());
		primaryKeys.add(newLaunchEntry2.getPrimaryKey());

		Map<Serializable, LaunchEntry> launchEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, launchEntries.size());
		Assert.assertEquals(
			newLaunchEntry1,
			launchEntries.get(newLaunchEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newLaunchEntry2,
			launchEntries.get(newLaunchEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LaunchEntry> launchEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(launchEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LaunchEntry newLaunchEntry = addLaunchEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLaunchEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LaunchEntry> launchEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, launchEntries.size());
		Assert.assertEquals(
			newLaunchEntry, launchEntries.get(newLaunchEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LaunchEntry> launchEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(launchEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLaunchEntry.getPrimaryKey());

		Map<Serializable, LaunchEntry> launchEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, launchEntries.size());
		Assert.assertEquals(
			newLaunchEntry, launchEntries.get(newLaunchEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LaunchEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LaunchEntry>() {

				@Override
				public void performAction(LaunchEntry launchEntry) {
					Assert.assertNotNull(launchEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"launchEntryId", newLaunchEntry.getLaunchEntryId()));

		List<LaunchEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LaunchEntry existingLaunchEntry = result.get(0);

		Assert.assertEquals(existingLaunchEntry, newLaunchEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"launchEntryId", RandomTestUtil.nextLong()));

		List<LaunchEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("launchEntryId"));

		Object newLaunchEntryId = newLaunchEntry.getLaunchEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"launchEntryId", new Object[] {newLaunchEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLaunchEntryId = result.get(0);

		Assert.assertEquals(existingLaunchEntryId, newLaunchEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("launchEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"launchEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LaunchEntry newLaunchEntry = addLaunchEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newLaunchEntry.getPrimaryKey()));
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

		LaunchEntry newLaunchEntry = addLaunchEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LaunchEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"launchEntryId", newLaunchEntry.getLaunchEntryId()));

		List<LaunchEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(LaunchEntry launchEntry) {
		Assert.assertEquals(
			Long.valueOf(launchEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				launchEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(launchEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				launchEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
		Assert.assertEquals(
			launchEntry.getClassVersion(),
			ReflectionTestUtil.invoke(
				launchEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classVersion"));

		Assert.assertEquals(
			launchEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				launchEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
		Assert.assertEquals(
			Long.valueOf(launchEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				launchEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
	}

	protected LaunchEntry addLaunchEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LaunchEntry launchEntry = _persistence.create(pk);

		launchEntry.setMvccVersion(RandomTestUtil.nextLong());

		launchEntry.setUuid(RandomTestUtil.randomString());

		launchEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		launchEntry.setCompanyId(RandomTestUtil.nextLong());

		launchEntry.setUserId(RandomTestUtil.nextLong());

		launchEntry.setCreateDate(RandomTestUtil.nextDate());

		launchEntry.setModifiedDate(RandomTestUtil.nextDate());

		launchEntry.setLaunchSetId(RandomTestUtil.nextLong());

		launchEntry.setClassNameId(RandomTestUtil.nextLong());

		launchEntry.setClassPK(RandomTestUtil.nextLong());

		launchEntry.setClassVersion(RandomTestUtil.randomString());

		_launchEntries.add(_persistence.update(launchEntry));

		return launchEntry;
	}

	private List<LaunchEntry> _launchEntries = new ArrayList<LaunchEntry>();
	private LaunchEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// SB-Hash:-379107591