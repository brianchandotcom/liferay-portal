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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.exception.NoSuchTirePriceEntryException;
import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.CommerceTirePriceEntryLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceTirePriceEntryPersistence;
import com.liferay.commerce.service.persistence.CommerceTirePriceEntryUtil;

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
import com.liferay.portal.kernel.util.StringPool;
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
public class CommerceTirePriceEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceTirePriceEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceTirePriceEntry> iterator = _commerceTirePriceEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTirePriceEntry commerceTirePriceEntry = _persistence.create(pk);

		Assert.assertNotNull(commerceTirePriceEntry);

		Assert.assertEquals(commerceTirePriceEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		_persistence.remove(newCommerceTirePriceEntry);

		CommerceTirePriceEntry existingCommerceTirePriceEntry = _persistence.fetchByPrimaryKey(newCommerceTirePriceEntry.getPrimaryKey());

		Assert.assertNull(existingCommerceTirePriceEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceTirePriceEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTirePriceEntry newCommerceTirePriceEntry = _persistence.create(pk);

		newCommerceTirePriceEntry.setUuid(RandomTestUtil.randomString());

		newCommerceTirePriceEntry.setGroupId(RandomTestUtil.nextLong());

		newCommerceTirePriceEntry.setCompanyId(RandomTestUtil.nextLong());

		newCommerceTirePriceEntry.setUserId(RandomTestUtil.nextLong());

		newCommerceTirePriceEntry.setUserName(RandomTestUtil.randomString());

		newCommerceTirePriceEntry.setCreateDate(RandomTestUtil.nextDate());

		newCommerceTirePriceEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceTirePriceEntry.setCommercePriceEntryId(RandomTestUtil.nextLong());

		newCommerceTirePriceEntry.setPrice(RandomTestUtil.nextDouble());

		newCommerceTirePriceEntry.setMinQuantity(RandomTestUtil.nextInt());

		newCommerceTirePriceEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceTirePriceEntries.add(_persistence.update(
				newCommerceTirePriceEntry));

		CommerceTirePriceEntry existingCommerceTirePriceEntry = _persistence.findByPrimaryKey(newCommerceTirePriceEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceTirePriceEntry.getUuid(),
			newCommerceTirePriceEntry.getUuid());
		Assert.assertEquals(existingCommerceTirePriceEntry.getCommerceTirePriceEntryId(),
			newCommerceTirePriceEntry.getCommerceTirePriceEntryId());
		Assert.assertEquals(existingCommerceTirePriceEntry.getGroupId(),
			newCommerceTirePriceEntry.getGroupId());
		Assert.assertEquals(existingCommerceTirePriceEntry.getCompanyId(),
			newCommerceTirePriceEntry.getCompanyId());
		Assert.assertEquals(existingCommerceTirePriceEntry.getUserId(),
			newCommerceTirePriceEntry.getUserId());
		Assert.assertEquals(existingCommerceTirePriceEntry.getUserName(),
			newCommerceTirePriceEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTirePriceEntry.getCreateDate()),
			Time.getShortTimestamp(newCommerceTirePriceEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTirePriceEntry.getModifiedDate()),
			Time.getShortTimestamp(newCommerceTirePriceEntry.getModifiedDate()));
		Assert.assertEquals(existingCommerceTirePriceEntry.getCommercePriceEntryId(),
			newCommerceTirePriceEntry.getCommercePriceEntryId());
		AssertUtils.assertEquals(existingCommerceTirePriceEntry.getPrice(),
			newCommerceTirePriceEntry.getPrice());
		Assert.assertEquals(existingCommerceTirePriceEntry.getMinQuantity(),
			newCommerceTirePriceEntry.getMinQuantity());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTirePriceEntry.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommerceTirePriceEntry.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCommercePriceEntryId() throws Exception {
		_persistence.countByCommercePriceEntryId(RandomTestUtil.nextLong());

		_persistence.countByCommercePriceEntryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		CommerceTirePriceEntry existingCommerceTirePriceEntry = _persistence.findByPrimaryKey(newCommerceTirePriceEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceTirePriceEntry,
			newCommerceTirePriceEntry);
	}

	@Test(expected = NoSuchTirePriceEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceTirePriceEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceTirePriceEntry",
			"uuid", true, "CommerceTirePriceEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commercePriceEntryId", true, "price",
			true, "minQuantity", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		CommerceTirePriceEntry existingCommerceTirePriceEntry = _persistence.fetchByPrimaryKey(newCommerceTirePriceEntry.getPrimaryKey());

		Assert.assertEquals(existingCommerceTirePriceEntry,
			newCommerceTirePriceEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTirePriceEntry missingCommerceTirePriceEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceTirePriceEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry1 = addCommerceTirePriceEntry();
		CommerceTirePriceEntry newCommerceTirePriceEntry2 = addCommerceTirePriceEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTirePriceEntry1.getPrimaryKey());
		primaryKeys.add(newCommerceTirePriceEntry2.getPrimaryKey());

		Map<Serializable, CommerceTirePriceEntry> commerceTirePriceEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceTirePriceEntries.size());
		Assert.assertEquals(newCommerceTirePriceEntry1,
			commerceTirePriceEntries.get(
				newCommerceTirePriceEntry1.getPrimaryKey()));
		Assert.assertEquals(newCommerceTirePriceEntry2,
			commerceTirePriceEntries.get(
				newCommerceTirePriceEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceTirePriceEntry> commerceTirePriceEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTirePriceEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTirePriceEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceTirePriceEntry> commerceTirePriceEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTirePriceEntries.size());
		Assert.assertEquals(newCommerceTirePriceEntry,
			commerceTirePriceEntries.get(
				newCommerceTirePriceEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceTirePriceEntry> commerceTirePriceEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTirePriceEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTirePriceEntry.getPrimaryKey());

		Map<Serializable, CommerceTirePriceEntry> commerceTirePriceEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTirePriceEntries.size());
		Assert.assertEquals(newCommerceTirePriceEntry,
			commerceTirePriceEntries.get(
				newCommerceTirePriceEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceTirePriceEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceTirePriceEntry>() {
				@Override
				public void performAction(
					CommerceTirePriceEntry commerceTirePriceEntry) {
					Assert.assertNotNull(commerceTirePriceEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTirePriceEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CommerceTirePriceEntryId",
				newCommerceTirePriceEntry.getCommerceTirePriceEntryId()));

		List<CommerceTirePriceEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceTirePriceEntry existingCommerceTirePriceEntry = result.get(0);

		Assert.assertEquals(existingCommerceTirePriceEntry,
			newCommerceTirePriceEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTirePriceEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CommerceTirePriceEntryId", RandomTestUtil.nextLong()));

		List<CommerceTirePriceEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTirePriceEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CommerceTirePriceEntryId"));

		Object newCommerceTirePriceEntryId = newCommerceTirePriceEntry.getCommerceTirePriceEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CommerceTirePriceEntryId",
				new Object[] { newCommerceTirePriceEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceTirePriceEntryId = result.get(0);

		Assert.assertEquals(existingCommerceTirePriceEntryId,
			newCommerceTirePriceEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTirePriceEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CommerceTirePriceEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CommerceTirePriceEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceTirePriceEntry newCommerceTirePriceEntry = addCommerceTirePriceEntry();

		_persistence.clearCache();

		CommerceTirePriceEntry existingCommerceTirePriceEntry = _persistence.findByPrimaryKey(newCommerceTirePriceEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommerceTirePriceEntry.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceTirePriceEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommerceTirePriceEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceTirePriceEntry,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceTirePriceEntry addCommerceTirePriceEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTirePriceEntry commerceTirePriceEntry = _persistence.create(pk);

		commerceTirePriceEntry.setUuid(RandomTestUtil.randomString());

		commerceTirePriceEntry.setGroupId(RandomTestUtil.nextLong());

		commerceTirePriceEntry.setCompanyId(RandomTestUtil.nextLong());

		commerceTirePriceEntry.setUserId(RandomTestUtil.nextLong());

		commerceTirePriceEntry.setUserName(RandomTestUtil.randomString());

		commerceTirePriceEntry.setCreateDate(RandomTestUtil.nextDate());

		commerceTirePriceEntry.setModifiedDate(RandomTestUtil.nextDate());

		commerceTirePriceEntry.setCommercePriceEntryId(RandomTestUtil.nextLong());

		commerceTirePriceEntry.setPrice(RandomTestUtil.nextDouble());

		commerceTirePriceEntry.setMinQuantity(RandomTestUtil.nextInt());

		commerceTirePriceEntry.setLastPublishDate(RandomTestUtil.nextDate());

		_commerceTirePriceEntries.add(_persistence.update(
				commerceTirePriceEntry));

		return commerceTirePriceEntry;
	}

	private List<CommerceTirePriceEntry> _commerceTirePriceEntries = new ArrayList<CommerceTirePriceEntry>();
	private CommerceTirePriceEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}