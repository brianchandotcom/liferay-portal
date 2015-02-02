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

package com.liferay.portlet.trash.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.trash.NoSuchEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.impl.TrashEntryModelImpl;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class TrashEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<TrashEntry> iterator = _trashEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashEntry trashEntry = _persistence.create(pk);

		Assert.assertNotNull(trashEntry);

		Assert.assertEquals(trashEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		_persistence.remove(newTrashEntry);

		TrashEntry existingTrashEntry = _persistence.fetchByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertNull(existingTrashEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTrashEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashEntry newTrashEntry = _persistence.create(pk);

		newTrashEntry.setGroupId(RandomTestUtil.nextLong());

		newTrashEntry.setCompanyId(RandomTestUtil.nextLong());

		newTrashEntry.setUserId(RandomTestUtil.nextLong());

		newTrashEntry.setUserName(RandomTestUtil.randomString());

		newTrashEntry.setCreateDate(RandomTestUtil.nextDate());

		newTrashEntry.setClassNameId(RandomTestUtil.nextLong());

		newTrashEntry.setClassPK(RandomTestUtil.nextLong());

		newTrashEntry.setSystemEventSetKey(RandomTestUtil.nextLong());

		newTrashEntry.setTypeSettings(RandomTestUtil.randomString());

		newTrashEntry.setStatus(RandomTestUtil.nextInt());

		_trashEntries.add(_persistence.update(newTrashEntry));

		TrashEntry existingTrashEntry = _persistence.findByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntry.getEntryId(),
			newTrashEntry.getEntryId());
		Assert.assertEquals(existingTrashEntry.getGroupId(),
			newTrashEntry.getGroupId());
		Assert.assertEquals(existingTrashEntry.getCompanyId(),
			newTrashEntry.getCompanyId());
		Assert.assertEquals(existingTrashEntry.getUserId(),
			newTrashEntry.getUserId());
		Assert.assertEquals(existingTrashEntry.getUserName(),
			newTrashEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTrashEntry.getCreateDate()),
			Time.getShortTimestamp(newTrashEntry.getCreateDate()));
		Assert.assertEquals(existingTrashEntry.getClassNameId(),
			newTrashEntry.getClassNameId());
		Assert.assertEquals(existingTrashEntry.getClassPK(),
			newTrashEntry.getClassPK());
		Assert.assertEquals(existingTrashEntry.getSystemEventSetKey(),
			newTrashEntry.getSystemEventSetKey());
		Assert.assertEquals(existingTrashEntry.getTypeSettings(),
			newTrashEntry.getTypeSettings());
		Assert.assertEquals(existingTrashEntry.getStatus(),
			newTrashEntry.getStatus());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LtCD() {
		try {
			_persistence.countByG_LtCD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate());

			_persistence.countByG_LtCD(0L, RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C() {
		try {
			_persistence.countByG_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		TrashEntry existingTrashEntry = _persistence.findByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntry, newTrashEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<TrashEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("TrashEntry", "entryId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "classNameId", true,
			"classPK", true, "systemEventSetKey", true, "typeSettings", true,
			"status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		TrashEntry existingTrashEntry = _persistence.fetchByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntry, newTrashEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashEntry missingTrashEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTrashEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		TrashEntry newTrashEntry1 = addTrashEntry();
		TrashEntry newTrashEntry2 = addTrashEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTrashEntry1.getPrimaryKey());
		primaryKeys.add(newTrashEntry2.getPrimaryKey());

		Map<Serializable, TrashEntry> trashEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, trashEntries.size());
		Assert.assertEquals(newTrashEntry1,
			trashEntries.get(newTrashEntry1.getPrimaryKey()));
		Assert.assertEquals(newTrashEntry2,
			trashEntries.get(newTrashEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, TrashEntry> trashEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(trashEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTrashEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, TrashEntry> trashEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, trashEntries.size());
		Assert.assertEquals(newTrashEntry,
			trashEntries.get(newTrashEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, TrashEntry> trashEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(trashEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTrashEntry.getPrimaryKey());

		Map<Serializable, TrashEntry> trashEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, trashEntries.size());
		Assert.assertEquals(newTrashEntry,
			trashEntries.get(newTrashEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = TrashEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					TrashEntry trashEntry = (TrashEntry)object;

					Assert.assertNotNull(trashEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newTrashEntry.getEntryId()));

		List<TrashEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		TrashEntry existingTrashEntry = result.get(0);

		Assert.assertEquals(existingTrashEntry, newTrashEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<TrashEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newTrashEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		TrashEntry newTrashEntry = addTrashEntry();

		_persistence.clearCache();

		TrashEntryModelImpl existingTrashEntryModelImpl = (TrashEntryModelImpl)_persistence.findByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntryModelImpl.getClassNameId(),
			existingTrashEntryModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingTrashEntryModelImpl.getClassPK(),
			existingTrashEntryModelImpl.getOriginalClassPK());
	}

	protected TrashEntry addTrashEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashEntry trashEntry = _persistence.create(pk);

		trashEntry.setGroupId(RandomTestUtil.nextLong());

		trashEntry.setCompanyId(RandomTestUtil.nextLong());

		trashEntry.setUserId(RandomTestUtil.nextLong());

		trashEntry.setUserName(RandomTestUtil.randomString());

		trashEntry.setCreateDate(RandomTestUtil.nextDate());

		trashEntry.setClassNameId(RandomTestUtil.nextLong());

		trashEntry.setClassPK(RandomTestUtil.nextLong());

		trashEntry.setSystemEventSetKey(RandomTestUtil.nextLong());

		trashEntry.setTypeSettings(RandomTestUtil.randomString());

		trashEntry.setStatus(RandomTestUtil.nextInt());

		_trashEntries.add(_persistence.update(trashEntry));

		return trashEntry;
	}

	private List<TrashEntry> _trashEntries = new ArrayList<TrashEntry>();
	private TrashEntryPersistence _persistence = TrashEntryUtil.getPersistence();
}