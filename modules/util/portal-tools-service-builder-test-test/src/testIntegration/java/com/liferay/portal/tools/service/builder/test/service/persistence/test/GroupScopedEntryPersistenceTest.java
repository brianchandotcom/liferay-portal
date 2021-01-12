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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchGroupScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry;
import com.liferay.portal.tools.service.builder.test.service.GroupScopedEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.GroupScopedEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.GroupScopedEntryUtil;

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
public class GroupScopedEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = GroupScopedEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<GroupScopedEntry> iterator = _groupScopedEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupScopedEntry groupScopedEntry = _persistence.create(pk);

		Assert.assertNotNull(groupScopedEntry);

		Assert.assertEquals(groupScopedEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		_persistence.remove(newGroupScopedEntry);

		GroupScopedEntry existingGroupScopedEntry =
			_persistence.fetchByPrimaryKey(newGroupScopedEntry.getPrimaryKey());

		Assert.assertNull(existingGroupScopedEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addGroupScopedEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupScopedEntry newGroupScopedEntry = _persistence.create(pk);

		newGroupScopedEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newGroupScopedEntry.setCompanyId(RandomTestUtil.nextLong());

		newGroupScopedEntry.setGroupId(RandomTestUtil.nextLong());

		_groupScopedEntries.add(_persistence.update(newGroupScopedEntry));

		GroupScopedEntry existingGroupScopedEntry =
			_persistence.findByPrimaryKey(newGroupScopedEntry.getPrimaryKey());

		Assert.assertEquals(
			existingGroupScopedEntry.getExternalReferenceCode(),
			newGroupScopedEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingGroupScopedEntry.getGroupScopedEntryId(),
			newGroupScopedEntry.getGroupScopedEntryId());
		Assert.assertEquals(
			existingGroupScopedEntry.getCompanyId(),
			newGroupScopedEntry.getCompanyId());
		Assert.assertEquals(
			existingGroupScopedEntry.getGroupId(),
			newGroupScopedEntry.getGroupId());
	}

	@Test
	public void testCountByG_ERC() throws Exception {
		_persistence.countByG_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByG_ERC(0L, "null");

		_persistence.countByG_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		GroupScopedEntry existingGroupScopedEntry =
			_persistence.findByPrimaryKey(newGroupScopedEntry.getPrimaryKey());

		Assert.assertEquals(existingGroupScopedEntry, newGroupScopedEntry);
	}

	@Test(expected = NoSuchGroupScopedEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<GroupScopedEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"GroupScopedEntry", "externalReferenceCode", true,
			"GroupScopedEntryId", true, "companyId", true, "groupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		GroupScopedEntry existingGroupScopedEntry =
			_persistence.fetchByPrimaryKey(newGroupScopedEntry.getPrimaryKey());

		Assert.assertEquals(existingGroupScopedEntry, newGroupScopedEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupScopedEntry missingGroupScopedEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingGroupScopedEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		GroupScopedEntry newGroupScopedEntry1 = addGroupScopedEntry();
		GroupScopedEntry newGroupScopedEntry2 = addGroupScopedEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroupScopedEntry1.getPrimaryKey());
		primaryKeys.add(newGroupScopedEntry2.getPrimaryKey());

		Map<Serializable, GroupScopedEntry> groupScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, groupScopedEntries.size());
		Assert.assertEquals(
			newGroupScopedEntry1,
			groupScopedEntries.get(newGroupScopedEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newGroupScopedEntry2,
			groupScopedEntries.get(newGroupScopedEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, GroupScopedEntry> groupScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(groupScopedEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroupScopedEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, GroupScopedEntry> groupScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, groupScopedEntries.size());
		Assert.assertEquals(
			newGroupScopedEntry,
			groupScopedEntries.get(newGroupScopedEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, GroupScopedEntry> groupScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(groupScopedEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroupScopedEntry.getPrimaryKey());

		Map<Serializable, GroupScopedEntry> groupScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, groupScopedEntries.size());
		Assert.assertEquals(
			newGroupScopedEntry,
			groupScopedEntries.get(newGroupScopedEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			GroupScopedEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<GroupScopedEntry>() {

				@Override
				public void performAction(GroupScopedEntry groupScopedEntry) {
					Assert.assertNotNull(groupScopedEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GroupScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"GroupScopedEntryId",
				newGroupScopedEntry.getGroupScopedEntryId()));

		List<GroupScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		GroupScopedEntry existingGroupScopedEntry = result.get(0);

		Assert.assertEquals(existingGroupScopedEntry, newGroupScopedEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GroupScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"GroupScopedEntryId", RandomTestUtil.nextLong()));

		List<GroupScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GroupScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("GroupScopedEntryId"));

		Object newGroupScopedEntryId =
			newGroupScopedEntry.getGroupScopedEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"GroupScopedEntryId", new Object[] {newGroupScopedEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingGroupScopedEntryId = result.get(0);

		Assert.assertEquals(existingGroupScopedEntryId, newGroupScopedEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GroupScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("GroupScopedEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"GroupScopedEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newGroupScopedEntry.getPrimaryKey()));
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

		GroupScopedEntry newGroupScopedEntry = addGroupScopedEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			GroupScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"GroupScopedEntryId",
				newGroupScopedEntry.getGroupScopedEntryId()));

		List<GroupScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(GroupScopedEntry groupScopedEntry) {
		Assert.assertEquals(
			Long.valueOf(groupScopedEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				groupScopedEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			groupScopedEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				groupScopedEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected GroupScopedEntry addGroupScopedEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupScopedEntry groupScopedEntry = _persistence.create(pk);

		groupScopedEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		groupScopedEntry.setCompanyId(RandomTestUtil.nextLong());

		groupScopedEntry.setGroupId(RandomTestUtil.nextLong());

		_groupScopedEntries.add(_persistence.update(groupScopedEntry));

		return groupScopedEntry;
	}

	private List<GroupScopedEntry> _groupScopedEntries =
		new ArrayList<GroupScopedEntry>();
	private GroupScopedEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}