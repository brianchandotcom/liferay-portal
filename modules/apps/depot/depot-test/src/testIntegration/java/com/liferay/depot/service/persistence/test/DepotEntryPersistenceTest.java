/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.depot.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.exception.NoSuchEntryException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.depot.service.persistence.DepotEntryPersistence;
import com.liferay.depot.service.persistence.DepotEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import java.util.Objects;
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
public class DepotEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.depot.service"));

	@Before
	public void setUp() {
		_persistence = DepotEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DepotEntry> iterator = _depotEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntry depotEntry = _persistence.create(pk);

		Assert.assertNotNull(depotEntry);

		Assert.assertEquals(depotEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		_persistence.remove(newDepotEntry);

		DepotEntry existingDepotEntry = _persistence.fetchByPrimaryKey(
			newDepotEntry.getPrimaryKey());

		Assert.assertNull(existingDepotEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDepotEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntry newDepotEntry = _persistence.create(pk);

		newDepotEntry.setMvccVersion(RandomTestUtil.nextLong());

		newDepotEntry.setUuid(RandomTestUtil.randomString());

		newDepotEntry.setGroupId(RandomTestUtil.nextLong());

		newDepotEntry.setCompanyId(RandomTestUtil.nextLong());

		newDepotEntry.setUserId(RandomTestUtil.nextLong());

		newDepotEntry.setCreateDate(RandomTestUtil.nextDate());

		newDepotEntry.setModifiedDate(RandomTestUtil.nextDate());

		_depotEntries.add(_persistence.update(newDepotEntry));

		DepotEntry existingDepotEntry = _persistence.findByPrimaryKey(
			newDepotEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDepotEntry.getMvccVersion(),
			newDepotEntry.getMvccVersion());
		Assert.assertEquals(
			existingDepotEntry.getUuid(), newDepotEntry.getUuid());
		Assert.assertEquals(
			existingDepotEntry.getDepotEntryId(),
			newDepotEntry.getDepotEntryId());
		Assert.assertEquals(
			existingDepotEntry.getGroupId(), newDepotEntry.getGroupId());
		Assert.assertEquals(
			existingDepotEntry.getCompanyId(), newDepotEntry.getCompanyId());
		Assert.assertEquals(
			existingDepotEntry.getUserId(), newDepotEntry.getUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingDepotEntry.getCreateDate()),
			Time.getShortTimestamp(newDepotEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingDepotEntry.getModifiedDate()),
			Time.getShortTimestamp(newDepotEntry.getModifiedDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		DepotEntry existingDepotEntry = _persistence.findByPrimaryKey(
			newDepotEntry.getPrimaryKey());

		Assert.assertEquals(existingDepotEntry, newDepotEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DepotEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DepotEntry", "mvccVersion", true, "uuid", true, "depotEntryId",
			true, "groupId", true, "companyId", true, "userId", true,
			"createDate", true, "modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		DepotEntry existingDepotEntry = _persistence.fetchByPrimaryKey(
			newDepotEntry.getPrimaryKey());

		Assert.assertEquals(existingDepotEntry, newDepotEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntry missingDepotEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDepotEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DepotEntry newDepotEntry1 = addDepotEntry();
		DepotEntry newDepotEntry2 = addDepotEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDepotEntry1.getPrimaryKey());
		primaryKeys.add(newDepotEntry2.getPrimaryKey());

		Map<Serializable, DepotEntry> depotEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, depotEntries.size());
		Assert.assertEquals(
			newDepotEntry1, depotEntries.get(newDepotEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newDepotEntry2, depotEntries.get(newDepotEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DepotEntry> depotEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(depotEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DepotEntry newDepotEntry = addDepotEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDepotEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DepotEntry> depotEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, depotEntries.size());
		Assert.assertEquals(
			newDepotEntry, depotEntries.get(newDepotEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DepotEntry> depotEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(depotEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDepotEntry.getPrimaryKey());

		Map<Serializable, DepotEntry> depotEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, depotEntries.size());
		Assert.assertEquals(
			newDepotEntry, depotEntries.get(newDepotEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DepotEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DepotEntry>() {

				@Override
				public void performAction(DepotEntry depotEntry) {
					Assert.assertNotNull(depotEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"depotEntryId", newDepotEntry.getDepotEntryId()));

		List<DepotEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DepotEntry existingDepotEntry = result.get(0);

		Assert.assertEquals(existingDepotEntry, newDepotEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"depotEntryId", RandomTestUtil.nextLong()));

		List<DepotEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("depotEntryId"));

		Object newDepotEntryId = newDepotEntry.getDepotEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"depotEntryId", new Object[] {newDepotEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDepotEntryId = result.get(0);

		Assert.assertEquals(existingDepotEntryId, newDepotEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("depotEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"depotEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DepotEntry newDepotEntry = addDepotEntry();

		_persistence.clearCache();

		DepotEntry existingDepotEntry = _persistence.findByPrimaryKey(
			newDepotEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingDepotEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingDepotEntry, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingDepotEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingDepotEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingDepotEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingDepotEntry, "getOriginalGroupId", new Class<?>[0]));
	}

	protected DepotEntry addDepotEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntry depotEntry = _persistence.create(pk);

		depotEntry.setMvccVersion(RandomTestUtil.nextLong());

		depotEntry.setUuid(RandomTestUtil.randomString());

		depotEntry.setGroupId(RandomTestUtil.nextLong());

		depotEntry.setCompanyId(RandomTestUtil.nextLong());

		depotEntry.setUserId(RandomTestUtil.nextLong());

		depotEntry.setCreateDate(RandomTestUtil.nextDate());

		depotEntry.setModifiedDate(RandomTestUtil.nextDate());

		_depotEntries.add(_persistence.update(depotEntry));

		return depotEntry;
	}

	private List<DepotEntry> _depotEntries = new ArrayList<DepotEntry>();
	private DepotEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}