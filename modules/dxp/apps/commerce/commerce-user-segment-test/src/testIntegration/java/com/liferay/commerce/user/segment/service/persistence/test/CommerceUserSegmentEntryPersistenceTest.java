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

package com.liferay.commerce.user.segment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryPersistence;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CommerceUserSegmentEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.user.segment.service"));

	@Before
	public void setUp() {
		_persistence = CommerceUserSegmentEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceUserSegmentEntry> iterator = _commerceUserSegmentEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentEntry commerceUserSegmentEntry = _persistence.create(pk);

		Assert.assertNotNull(commerceUserSegmentEntry);

		Assert.assertEquals(commerceUserSegmentEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		_persistence.remove(newCommerceUserSegmentEntry);

		CommerceUserSegmentEntry existingCommerceUserSegmentEntry = _persistence.fetchByPrimaryKey(newCommerceUserSegmentEntry.getPrimaryKey());

		Assert.assertNull(existingCommerceUserSegmentEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceUserSegmentEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentEntry newCommerceUserSegmentEntry = _persistence.create(pk);

		newCommerceUserSegmentEntry.setGroupId(RandomTestUtil.nextLong());

		newCommerceUserSegmentEntry.setCompanyId(RandomTestUtil.nextLong());

		newCommerceUserSegmentEntry.setUserId(RandomTestUtil.nextLong());

		newCommerceUserSegmentEntry.setUserName(RandomTestUtil.randomString());

		newCommerceUserSegmentEntry.setCreateDate(RandomTestUtil.nextDate());

		newCommerceUserSegmentEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceUserSegmentEntry.setName(RandomTestUtil.randomString());

		newCommerceUserSegmentEntry.setKey(RandomTestUtil.randomString());

		newCommerceUserSegmentEntry.setActive(RandomTestUtil.randomBoolean());

		newCommerceUserSegmentEntry.setSystem(RandomTestUtil.randomBoolean());

		newCommerceUserSegmentEntry.setPriority(RandomTestUtil.nextDouble());

		_commerceUserSegmentEntries.add(_persistence.update(
				newCommerceUserSegmentEntry));

		CommerceUserSegmentEntry existingCommerceUserSegmentEntry = _persistence.findByPrimaryKey(newCommerceUserSegmentEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			newCommerceUserSegmentEntry.getCommerceUserSegmentEntryId());
		Assert.assertEquals(existingCommerceUserSegmentEntry.getGroupId(),
			newCommerceUserSegmentEntry.getGroupId());
		Assert.assertEquals(existingCommerceUserSegmentEntry.getCompanyId(),
			newCommerceUserSegmentEntry.getCompanyId());
		Assert.assertEquals(existingCommerceUserSegmentEntry.getUserId(),
			newCommerceUserSegmentEntry.getUserId());
		Assert.assertEquals(existingCommerceUserSegmentEntry.getUserName(),
			newCommerceUserSegmentEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceUserSegmentEntry.getCreateDate()),
			Time.getShortTimestamp(newCommerceUserSegmentEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceUserSegmentEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceUserSegmentEntry.getModifiedDate()));
		Assert.assertEquals(existingCommerceUserSegmentEntry.getName(),
			newCommerceUserSegmentEntry.getName());
		Assert.assertEquals(existingCommerceUserSegmentEntry.getKey(),
			newCommerceUserSegmentEntry.getKey());
		Assert.assertEquals(existingCommerceUserSegmentEntry.isActive(),
			newCommerceUserSegmentEntry.isActive());
		Assert.assertEquals(existingCommerceUserSegmentEntry.isSystem(),
			newCommerceUserSegmentEntry.isSystem());
		AssertUtils.assertEquals(existingCommerceUserSegmentEntry.getPriority(),
			newCommerceUserSegmentEntry.getPriority());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_K() throws Exception {
		_persistence.countByG_K(RandomTestUtil.nextLong(), "");

		_persistence.countByG_K(0L, "null");

		_persistence.countByG_K(0L, (String)null);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		CommerceUserSegmentEntry existingCommerceUserSegmentEntry = _persistence.findByPrimaryKey(newCommerceUserSegmentEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceUserSegmentEntry,
			newCommerceUserSegmentEntry);
	}

	@Test(expected = NoSuchUserSegmentEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceUserSegmentEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceUserSegmentEntry",
			"commerceUserSegmentEntryId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "key", true, "active", true,
			"system", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		CommerceUserSegmentEntry existingCommerceUserSegmentEntry = _persistence.fetchByPrimaryKey(newCommerceUserSegmentEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceUserSegmentEntry,
			newCommerceUserSegmentEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentEntry missingCommerceUserSegmentEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceUserSegmentEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry1 = addCommerceUserSegmentEntry();
		CommerceUserSegmentEntry newCommerceUserSegmentEntry2 = addCommerceUserSegmentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceUserSegmentEntry1.getPrimaryKey());
		primaryKeys.add(newCommerceUserSegmentEntry2.getPrimaryKey());

		Map<Serializable, CommerceUserSegmentEntry> commerceUserSegmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceUserSegmentEntries.size());
		Assert.assertEquals(newCommerceUserSegmentEntry1,
			commerceUserSegmentEntries.get(
				newCommerceUserSegmentEntry1.getPrimaryKey()));
		Assert.assertEquals(newCommerceUserSegmentEntry2,
			commerceUserSegmentEntries.get(
				newCommerceUserSegmentEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceUserSegmentEntry> commerceUserSegmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceUserSegmentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceUserSegmentEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceUserSegmentEntry> commerceUserSegmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceUserSegmentEntries.size());
		Assert.assertEquals(newCommerceUserSegmentEntry,
			commerceUserSegmentEntries.get(
				newCommerceUserSegmentEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceUserSegmentEntry> commerceUserSegmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceUserSegmentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceUserSegmentEntry.getPrimaryKey());

		Map<Serializable, CommerceUserSegmentEntry> commerceUserSegmentEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceUserSegmentEntries.size());
		Assert.assertEquals(newCommerceUserSegmentEntry,
			commerceUserSegmentEntries.get(
				newCommerceUserSegmentEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceUserSegmentEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceUserSegmentEntry>() {
				@Override
				public void performAction(
					CommerceUserSegmentEntry commerceUserSegmentEntry) {
					Assert.assertNotNull(commerceUserSegmentEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceUserSegmentEntryId",
				newCommerceUserSegmentEntry.getCommerceUserSegmentEntryId()));

		List<CommerceUserSegmentEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceUserSegmentEntry existingCommerceUserSegmentEntry = result.get(0);

		Assert.assertEquals(existingCommerceUserSegmentEntry,
			newCommerceUserSegmentEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceUserSegmentEntryId", RandomTestUtil.nextLong()));

		List<CommerceUserSegmentEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceUserSegmentEntryId"));

		Object newCommerceUserSegmentEntryId = newCommerceUserSegmentEntry.getCommerceUserSegmentEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceUserSegmentEntryId",
				new Object[] { newCommerceUserSegmentEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceUserSegmentEntryId = result.get(0);

		Assert.assertEquals(existingCommerceUserSegmentEntryId,
			newCommerceUserSegmentEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceUserSegmentEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceUserSegmentEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceUserSegmentEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceUserSegmentEntry newCommerceUserSegmentEntry = addCommerceUserSegmentEntry();

		_persistence.clearCache();

		CommerceUserSegmentEntry existingCommerceUserSegmentEntry = _persistence.findByPrimaryKey(newCommerceUserSegmentEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceUserSegmentEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceUserSegmentEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommerceUserSegmentEntry.getKey(),
				ReflectionTestUtil.invoke(existingCommerceUserSegmentEntry,
					"getOriginalKey", new Class<?>[0])));
	}

	protected CommerceUserSegmentEntry addCommerceUserSegmentEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceUserSegmentEntry commerceUserSegmentEntry = _persistence.create(pk);

		commerceUserSegmentEntry.setGroupId(RandomTestUtil.nextLong());

		commerceUserSegmentEntry.setCompanyId(RandomTestUtil.nextLong());

		commerceUserSegmentEntry.setUserId(RandomTestUtil.nextLong());

		commerceUserSegmentEntry.setUserName(RandomTestUtil.randomString());

		commerceUserSegmentEntry.setCreateDate(RandomTestUtil.nextDate());

		commerceUserSegmentEntry.setModifiedDate(RandomTestUtil.nextDate());

		commerceUserSegmentEntry.setName(RandomTestUtil.randomString());

		commerceUserSegmentEntry.setKey(RandomTestUtil.randomString());

		commerceUserSegmentEntry.setActive(RandomTestUtil.randomBoolean());

		commerceUserSegmentEntry.setSystem(RandomTestUtil.randomBoolean());

		commerceUserSegmentEntry.setPriority(RandomTestUtil.nextDouble());

		_commerceUserSegmentEntries.add(_persistence.update(
				commerceUserSegmentEntry));

		return commerceUserSegmentEntry;
	}

	private List<CommerceUserSegmentEntry> _commerceUserSegmentEntries = new ArrayList<CommerceUserSegmentEntry>();
	private CommerceUserSegmentEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}