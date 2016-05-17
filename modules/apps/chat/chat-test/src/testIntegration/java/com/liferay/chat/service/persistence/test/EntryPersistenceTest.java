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

package com.liferay.chat.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.chat.exception.NoSuchEntryException;
import com.liferay.chat.model.Entry;
import com.liferay.chat.service.EntryLocalServiceUtil;
import com.liferay.chat.service.persistence.EntryPersistence;
import com.liferay.chat.service.persistence.EntryUtil;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class EntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = EntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Entry> iterator = _entries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Entry entry = _persistence.create(pk);

		Assert.assertNotNull(entry);

		Assert.assertEquals(entry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Entry newEntry = addEntry();

		_persistence.remove(newEntry);

		Entry existingEntry = _persistence.fetchByPrimaryKey(newEntry.getPrimaryKey());

		Assert.assertNull(existingEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Entry newEntry = _persistence.create(pk);

		newEntry.setCreateDate(RandomTestUtil.nextLong());

		newEntry.setFromUserId(RandomTestUtil.nextLong());

		newEntry.setToUserId(RandomTestUtil.nextLong());

		newEntry.setContent(RandomTestUtil.randomString());

		newEntry.setFlag(RandomTestUtil.nextInt());

		_entries.add(_persistence.update(newEntry));

		Entry existingEntry = _persistence.findByPrimaryKey(newEntry.getPrimaryKey());

		Assert.assertEquals(existingEntry.getEntryId(), newEntry.getEntryId());
		Assert.assertEquals(existingEntry.getCreateDate(),
			newEntry.getCreateDate());
		Assert.assertEquals(existingEntry.getFromUserId(),
			newEntry.getFromUserId());
		Assert.assertEquals(existingEntry.getToUserId(), newEntry.getToUserId());
		Assert.assertEquals(existingEntry.getContent(), newEntry.getContent());
		Assert.assertEquals(existingEntry.getFlag(), newEntry.getFlag());
	}

	@Test
	public void testCountByCreateDate() throws Exception {
		_persistence.countByCreateDate(RandomTestUtil.nextLong());

		_persistence.countByCreateDate(0L);
	}

	@Test
	public void testCountByFromUserId() throws Exception {
		_persistence.countByFromUserId(RandomTestUtil.nextLong());

		_persistence.countByFromUserId(0L);
	}

	@Test
	public void testCountByToUserId() throws Exception {
		_persistence.countByToUserId(RandomTestUtil.nextLong());

		_persistence.countByToUserId(0L);
	}

	@Test
	public void testCountByC_F() throws Exception {
		_persistence.countByC_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_F(0L, 0L);
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_T(0L, 0L);
	}

	@Test
	public void testCountByF_T() throws Exception {
		_persistence.countByF_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByF_T(0L, 0L);
	}

	@Test
	public void testCountByC_F_T() throws Exception {
		_persistence.countByC_F_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_F_T(0L, 0L, 0L);
	}

	@Test
	public void testCountByF_T_C() throws Exception {
		_persistence.countByF_T_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByF_T_C(0L, 0L, StringPool.NULL);

		_persistence.countByF_T_C(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Entry newEntry = addEntry();

		Entry existingEntry = _persistence.findByPrimaryKey(newEntry.getPrimaryKey());

		Assert.assertEquals(existingEntry, newEntry);
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

	protected OrderByComparator<Entry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Chat_Entry", "entryId",
			true, "createDate", true, "fromUserId", true, "toUserId", true,
			"content", true, "flag", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Entry newEntry = addEntry();

		Entry existingEntry = _persistence.fetchByPrimaryKey(newEntry.getPrimaryKey());

		Assert.assertEquals(existingEntry, newEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Entry missingEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Entry newEntry1 = addEntry();
		Entry newEntry2 = addEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEntry1.getPrimaryKey());
		primaryKeys.add(newEntry2.getPrimaryKey());

		Map<Serializable, Entry> entries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, entries.size());
		Assert.assertEquals(newEntry1, entries.get(newEntry1.getPrimaryKey()));
		Assert.assertEquals(newEntry2, entries.get(newEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Entry> entries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(entries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Entry newEntry = addEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Entry> entries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(newEntry, entries.get(newEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Entry> entries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(entries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Entry newEntry = addEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEntry.getPrimaryKey());

		Map<Serializable, Entry> entries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(newEntry, entries.get(newEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = EntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Entry>() {
				@Override
				public void performAction(Entry entry) {
					Assert.assertNotNull(entry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Entry newEntry = addEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Entry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newEntry.getEntryId()));

		List<Entry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Entry existingEntry = result.get(0);

		Assert.assertEquals(existingEntry, newEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Entry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<Entry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Entry newEntry = addEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Entry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Entry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Entry addEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Entry entry = _persistence.create(pk);

		entry.setCreateDate(RandomTestUtil.nextLong());

		entry.setFromUserId(RandomTestUtil.nextLong());

		entry.setToUserId(RandomTestUtil.nextLong());

		entry.setContent(RandomTestUtil.randomString());

		entry.setFlag(RandomTestUtil.nextInt());

		_entries.add(_persistence.update(entry));

		return entry;
	}

	private List<Entry> _entries = new ArrayList<Entry>();
	private EntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}