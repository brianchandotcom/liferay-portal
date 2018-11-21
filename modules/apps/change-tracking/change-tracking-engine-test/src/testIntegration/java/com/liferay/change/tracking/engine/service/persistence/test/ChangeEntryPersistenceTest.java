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

package com.liferay.change.tracking.engine.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException;
import com.liferay.change.tracking.engine.model.ChangeEntry;
import com.liferay.change.tracking.engine.service.ChangeEntryLocalServiceUtil;
import com.liferay.change.tracking.engine.service.persistence.ChangeEntryPersistence;
import com.liferay.change.tracking.engine.service.persistence.ChangeEntryUtil;

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
public class ChangeEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.engine.service"));

	@Before
	public void setUp() {
		_persistence = ChangeEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChangeEntry> iterator = _changeEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeEntry changeEntry = _persistence.create(pk);

		Assert.assertNotNull(changeEntry);

		Assert.assertEquals(changeEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		_persistence.remove(newChangeEntry);

		ChangeEntry existingChangeEntry = _persistence.fetchByPrimaryKey(newChangeEntry.getPrimaryKey());

		Assert.assertNull(existingChangeEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangeEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeEntry newChangeEntry = _persistence.create(pk);

		newChangeEntry.setCompanyId(RandomTestUtil.nextLong());

		newChangeEntry.setUserId(RandomTestUtil.nextLong());

		newChangeEntry.setUserName(RandomTestUtil.randomString());

		newChangeEntry.setCreateDate(RandomTestUtil.nextDate());

		newChangeEntry.setModifiedDate(RandomTestUtil.nextDate());

		newChangeEntry.setClassNameId(RandomTestUtil.nextLong());

		newChangeEntry.setClassPK(RandomTestUtil.nextLong());

		newChangeEntry.setResourcePrimKey(RandomTestUtil.nextLong());

		_changeEntries.add(_persistence.update(newChangeEntry));

		ChangeEntry existingChangeEntry = _persistence.findByPrimaryKey(newChangeEntry.getPrimaryKey());

		Assert.assertEquals(existingChangeEntry.getChangeEntryId(),
			newChangeEntry.getChangeEntryId());
		Assert.assertEquals(existingChangeEntry.getCompanyId(),
			newChangeEntry.getCompanyId());
		Assert.assertEquals(existingChangeEntry.getUserId(),
			newChangeEntry.getUserId());
		Assert.assertEquals(existingChangeEntry.getUserName(),
			newChangeEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeEntry.getCreateDate()),
			Time.getShortTimestamp(newChangeEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeEntry.getModifiedDate()),
			Time.getShortTimestamp(newChangeEntry.getModifiedDate()));
		Assert.assertEquals(existingChangeEntry.getClassNameId(),
			newChangeEntry.getClassNameId());
		Assert.assertEquals(existingChangeEntry.getClassPK(),
			newChangeEntry.getClassPK());
		Assert.assertEquals(existingChangeEntry.getResourcePrimKey(),
			newChangeEntry.getResourcePrimKey());
	}

	@Test
	public void testCountByResourcePrimKey() throws Exception {
		_persistence.countByResourcePrimKey(RandomTestUtil.nextLong());

		_persistence.countByResourcePrimKey(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		ChangeEntry existingChangeEntry = _persistence.findByPrimaryKey(newChangeEntry.getPrimaryKey());

		Assert.assertEquals(existingChangeEntry, newChangeEntry);
	}

	@Test(expected = NoSuchChangeEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ChangeEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ChangeEntry",
			"changeEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "resourcePrimKey", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		ChangeEntry existingChangeEntry = _persistence.fetchByPrimaryKey(newChangeEntry.getPrimaryKey());

		Assert.assertEquals(existingChangeEntry, newChangeEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeEntry missingChangeEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangeEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ChangeEntry newChangeEntry1 = addChangeEntry();
		ChangeEntry newChangeEntry2 = addChangeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeEntry1.getPrimaryKey());
		primaryKeys.add(newChangeEntry2.getPrimaryKey());

		Map<Serializable, ChangeEntry> changeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changeEntries.size());
		Assert.assertEquals(newChangeEntry1,
			changeEntries.get(newChangeEntry1.getPrimaryKey()));
		Assert.assertEquals(newChangeEntry2,
			changeEntries.get(newChangeEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChangeEntry> changeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChangeEntry> changeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeEntries.size());
		Assert.assertEquals(newChangeEntry,
			changeEntries.get(newChangeEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChangeEntry> changeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeEntry.getPrimaryKey());

		Map<Serializable, ChangeEntry> changeEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changeEntries.size());
		Assert.assertEquals(newChangeEntry,
			changeEntries.get(newChangeEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangeEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ChangeEntry>() {
				@Override
				public void performAction(ChangeEntry changeEntry) {
					Assert.assertNotNull(changeEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changeEntryId",
				newChangeEntry.getChangeEntryId()));

		List<ChangeEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChangeEntry existingChangeEntry = result.get(0);

		Assert.assertEquals(existingChangeEntry, newChangeEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changeEntryId",
				RandomTestUtil.nextLong()));

		List<ChangeEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ChangeEntry newChangeEntry = addChangeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeEntryId"));

		Object newChangeEntryId = newChangeEntry.getChangeEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("changeEntryId",
				new Object[] { newChangeEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangeEntryId = result.get(0);

		Assert.assertEquals(existingChangeEntryId, newChangeEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangeEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changeEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("changeEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ChangeEntry addChangeEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangeEntry changeEntry = _persistence.create(pk);

		changeEntry.setCompanyId(RandomTestUtil.nextLong());

		changeEntry.setUserId(RandomTestUtil.nextLong());

		changeEntry.setUserName(RandomTestUtil.randomString());

		changeEntry.setCreateDate(RandomTestUtil.nextDate());

		changeEntry.setModifiedDate(RandomTestUtil.nextDate());

		changeEntry.setClassNameId(RandomTestUtil.nextLong());

		changeEntry.setClassPK(RandomTestUtil.nextLong());

		changeEntry.setResourcePrimKey(RandomTestUtil.nextLong());

		_changeEntries.add(_persistence.update(changeEntry));

		return changeEntry;
	}

	private List<ChangeEntry> _changeEntries = new ArrayList<ChangeEntry>();
	private ChangeEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}