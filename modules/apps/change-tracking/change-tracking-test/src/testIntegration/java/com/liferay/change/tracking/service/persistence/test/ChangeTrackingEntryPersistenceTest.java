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

package com.liferay.change.tracking.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.ChangeTrackingEntry;
import com.liferay.change.tracking.service.ChangeTrackingEntryLocalServiceUtil;
import com.liferay.change.tracking.service.persistence.ChangeTrackingEntryPersistence;
import com.liferay.change.tracking.service.persistence.ChangeTrackingEntryUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

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
@RunWith(Arquillian.class)
public class ChangeTrackingEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.service"));

	@Before
	public void setUp() {
		_persistence = ChangeTrackingEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChangeTrackingEntry> iterator = _changeTrackingEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingEntry changeTrackingEntry = _persistence.create(pk);

		Assert.assertNotNull(changeTrackingEntry);

		Assert.assertEquals(changeTrackingEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		_persistence.remove(newChangeTrackingEntry);

		ChangeTrackingEntry existingChangeTrackingEntry = _persistence.fetchByPrimaryKey(newChangeTrackingEntry.getPrimaryKey());

		Assert.assertNull(existingChangeTrackingEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangeTrackingEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingEntry newChangeTrackingEntry = _persistence.create(pk);

		newChangeTrackingEntry.setCompanyId(RandomTestUtil.nextLong());

		newChangeTrackingEntry.setUserId(RandomTestUtil.nextLong());

		newChangeTrackingEntry.setUserName(RandomTestUtil.randomString());

		newChangeTrackingEntry.setCreateDate(RandomTestUtil.nextDate());

		newChangeTrackingEntry.setModifiedDate(RandomTestUtil.nextDate());

		newChangeTrackingEntry.setClassNameId(RandomTestUtil.nextLong());

		newChangeTrackingEntry.setClassPK(RandomTestUtil.nextLong());

		newChangeTrackingEntry.setResourcePrimKey(RandomTestUtil.nextLong());

		_changeTrackingEntries.add(_persistence.update(newChangeTrackingEntry));

		ChangeTrackingEntry existingChangeTrackingEntry = _persistence.findByPrimaryKey(newChangeTrackingEntry.getPrimaryKey());

		Assert.assertEquals(existingChangeTrackingEntry.getChangeTrackingEntryId(),
			newChangeTrackingEntry.getChangeTrackingEntryId());
		Assert.assertEquals(existingChangeTrackingEntry.getCompanyId(),
			newChangeTrackingEntry.getCompanyId());
		Assert.assertEquals(existingChangeTrackingEntry.getUserId(),
			newChangeTrackingEntry.getUserId());
		Assert.assertEquals(existingChangeTrackingEntry.getUserName(),
			newChangeTrackingEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeTrackingEntry.getCreateDate()),
			Time.getShortTimestamp(newChangeTrackingEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeTrackingEntry.getModifiedDate()),
			Time.getShortTimestamp(newChangeTrackingEntry.getModifiedDate()));
		Assert.assertEquals(existingChangeTrackingEntry.getClassNameId(),
			newChangeTrackingEntry.getClassNameId());
		Assert.assertEquals(existingChangeTrackingEntry.getClassPK(),
			newChangeTrackingEntry.getClassPK());
		Assert.assertEquals(existingChangeTrackingEntry.getResourcePrimKey(),
			newChangeTrackingEntry.getResourcePrimKey());
	}

	@Test
	public void testCountByResourcePrimKey() throws Exception {
		_persistence.countByResourcePrimKey(RandomTestUtil.nextLong());

		_persistence.countByResourcePrimKey(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		ChangeTrackingEntry existingChangeTrackingEntry = _persistence.findByPrimaryKey(newChangeTrackingEntry.getPrimaryKey());

		Assert.assertEquals(existingChangeTrackingEntry, newChangeTrackingEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ChangeTrackingEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ChangeTrackingEntry",
			"changeTrackingEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "resourcePrimKey", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		ChangeTrackingEntry existingChangeTrackingEntry = _persistence.fetchByPrimaryKey(newChangeTrackingEntry.getPrimaryKey());

		Assert.assertEquals(existingChangeTrackingEntry, newChangeTrackingEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingEntry missingChangeTrackingEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangeTrackingEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry1 = addChangeTrackingEntry();
		ChangeTrackingEntry newChangeTrackingEntry2 = addChangeTrackingEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeTrackingEntry1.getPrimaryKey());
		primaryKeys.add(newChangeTrackingEntry2.getPrimaryKey());

		Map<Serializable, ChangeTrackingEntry> changeTrackingEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changeTrackingEntries.size());
		Assert.assertEquals(newChangeTrackingEntry1,
			changeTrackingEntries.get(newChangeTrackingEntry1.getPrimaryKey()));
		Assert.assertEquals(newChangeTrackingEntry2,
			changeTrackingEntries.get(newChangeTrackingEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChangeTrackingEntry> changeTrackingEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeTrackingEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeTrackingEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChangeTrackingEntry> changeTrackingEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeTrackingEntries.size());
		Assert.assertEquals(newChangeTrackingEntry,
			changeTrackingEntries.get(newChangeTrackingEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChangeTrackingEntry> changeTrackingEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeTrackingEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeTrackingEntry.getPrimaryKey());

		Map<Serializable, ChangeTrackingEntry> changeTrackingEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeTrackingEntries.size());
		Assert.assertEquals(newChangeTrackingEntry,
			changeTrackingEntries.get(newChangeTrackingEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangeTrackingEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ChangeTrackingEntry>() {
				@Override
				public void performAction(
					ChangeTrackingEntry changeTrackingEntry) {
					Assert.assertNotNull(changeTrackingEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changeTrackingEntryId",
				newChangeTrackingEntry.getChangeTrackingEntryId()));

		List<ChangeTrackingEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChangeTrackingEntry existingChangeTrackingEntry = result.get(0);

		Assert.assertEquals(existingChangeTrackingEntry, newChangeTrackingEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changeTrackingEntryId",
				RandomTestUtil.nextLong()));

		List<ChangeTrackingEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ChangeTrackingEntry newChangeTrackingEntry = addChangeTrackingEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeTrackingEntryId"));

		Object newChangeTrackingEntryId = newChangeTrackingEntry.getChangeTrackingEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("changeTrackingEntryId",
				new Object[] { newChangeTrackingEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangeTrackingEntryId = result.get(0);

		Assert.assertEquals(existingChangeTrackingEntryId,
			newChangeTrackingEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeTrackingEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeTrackingEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("changeTrackingEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ChangeTrackingEntry addChangeTrackingEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeTrackingEntry changeTrackingEntry = _persistence.create(pk);

		changeTrackingEntry.setCompanyId(RandomTestUtil.nextLong());

		changeTrackingEntry.setUserId(RandomTestUtil.nextLong());

		changeTrackingEntry.setUserName(RandomTestUtil.randomString());

		changeTrackingEntry.setCreateDate(RandomTestUtil.nextDate());

		changeTrackingEntry.setModifiedDate(RandomTestUtil.nextDate());

		changeTrackingEntry.setClassNameId(RandomTestUtil.nextLong());

		changeTrackingEntry.setClassPK(RandomTestUtil.nextLong());

		changeTrackingEntry.setResourcePrimKey(RandomTestUtil.nextLong());

		_changeTrackingEntries.add(_persistence.update(changeTrackingEntry));

		return changeTrackingEntry;
	}

	private List<ChangeTrackingEntry> _changeTrackingEntries = new ArrayList<ChangeTrackingEntry>();
	private ChangeTrackingEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}