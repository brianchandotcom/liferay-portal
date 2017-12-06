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

import com.liferay.commerce.exception.NoSuchWarehouseItemException;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.CommerceWarehouseItemLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemPersistence;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemUtil;

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
public class CommerceWarehouseItemPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceWarehouseItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceWarehouseItem> iterator = _commerceWarehouseItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouseItem commerceWarehouseItem = _persistence.create(pk);

		Assert.assertNotNull(commerceWarehouseItem);

		Assert.assertEquals(commerceWarehouseItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		_persistence.remove(newCommerceWarehouseItem);

		CommerceWarehouseItem existingCommerceWarehouseItem = _persistence.fetchByPrimaryKey(newCommerceWarehouseItem.getPrimaryKey());

		Assert.assertNull(existingCommerceWarehouseItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceWarehouseItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouseItem newCommerceWarehouseItem = _persistence.create(pk);

		newCommerceWarehouseItem.setGroupId(RandomTestUtil.nextLong());

		newCommerceWarehouseItem.setCompanyId(RandomTestUtil.nextLong());

		newCommerceWarehouseItem.setUserId(RandomTestUtil.nextLong());

		newCommerceWarehouseItem.setUserName(RandomTestUtil.randomString());

		newCommerceWarehouseItem.setCreateDate(RandomTestUtil.nextDate());

		newCommerceWarehouseItem.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceWarehouseItem.setCommerceWarehouseId(RandomTestUtil.nextLong());

		newCommerceWarehouseItem.setCPInstanceId(RandomTestUtil.nextLong());

		newCommerceWarehouseItem.setQuantity(RandomTestUtil.nextInt());

		_commerceWarehouseItems.add(_persistence.update(
				newCommerceWarehouseItem));

		CommerceWarehouseItem existingCommerceWarehouseItem = _persistence.findByPrimaryKey(newCommerceWarehouseItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceWarehouseItem.getCommerceWarehouseItemId(),
			newCommerceWarehouseItem.getCommerceWarehouseItemId());
		Assert.assertEquals(existingCommerceWarehouseItem.getGroupId(),
			newCommerceWarehouseItem.getGroupId());
		Assert.assertEquals(existingCommerceWarehouseItem.getCompanyId(),
			newCommerceWarehouseItem.getCompanyId());
		Assert.assertEquals(existingCommerceWarehouseItem.getUserId(),
			newCommerceWarehouseItem.getUserId());
		Assert.assertEquals(existingCommerceWarehouseItem.getUserName(),
			newCommerceWarehouseItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceWarehouseItem.getCreateDate()),
			Time.getShortTimestamp(newCommerceWarehouseItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceWarehouseItem.getModifiedDate()),
			Time.getShortTimestamp(newCommerceWarehouseItem.getModifiedDate()));
		Assert.assertEquals(existingCommerceWarehouseItem.getCommerceWarehouseId(),
			newCommerceWarehouseItem.getCommerceWarehouseId());
		Assert.assertEquals(existingCommerceWarehouseItem.getCPInstanceId(),
			newCommerceWarehouseItem.getCPInstanceId());
		Assert.assertEquals(existingCommerceWarehouseItem.getQuantity(),
			newCommerceWarehouseItem.getQuantity());
	}

	@Test
	public void testCountByCommerceWarehouseId() throws Exception {
		_persistence.countByCommerceWarehouseId(RandomTestUtil.nextLong());

		_persistence.countByCommerceWarehouseId(0L);
	}

	@Test
	public void testCountByCPInstanceId() throws Exception {
		_persistence.countByCPInstanceId(RandomTestUtil.nextLong());

		_persistence.countByCPInstanceId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		CommerceWarehouseItem existingCommerceWarehouseItem = _persistence.findByPrimaryKey(newCommerceWarehouseItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceWarehouseItem,
			newCommerceWarehouseItem);
	}

	@Test(expected = NoSuchWarehouseItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceWarehouseItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceWarehouseItem",
			"commerceWarehouseItemId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceWarehouseId", true, "CPInstanceId",
			true, "quantity", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		CommerceWarehouseItem existingCommerceWarehouseItem = _persistence.fetchByPrimaryKey(newCommerceWarehouseItem.getPrimaryKey());

		Assert.assertEquals(existingCommerceWarehouseItem,
			newCommerceWarehouseItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouseItem missingCommerceWarehouseItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceWarehouseItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem1 = addCommerceWarehouseItem();
		CommerceWarehouseItem newCommerceWarehouseItem2 = addCommerceWarehouseItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWarehouseItem1.getPrimaryKey());
		primaryKeys.add(newCommerceWarehouseItem2.getPrimaryKey());

		Map<Serializable, CommerceWarehouseItem> commerceWarehouseItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceWarehouseItems.size());
		Assert.assertEquals(newCommerceWarehouseItem1,
			commerceWarehouseItems.get(
				newCommerceWarehouseItem1.getPrimaryKey()));
		Assert.assertEquals(newCommerceWarehouseItem2,
			commerceWarehouseItems.get(
				newCommerceWarehouseItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceWarehouseItem> commerceWarehouseItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceWarehouseItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWarehouseItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceWarehouseItem> commerceWarehouseItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceWarehouseItems.size());
		Assert.assertEquals(newCommerceWarehouseItem,
			commerceWarehouseItems.get(newCommerceWarehouseItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceWarehouseItem> commerceWarehouseItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceWarehouseItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceWarehouseItem.getPrimaryKey());

		Map<Serializable, CommerceWarehouseItem> commerceWarehouseItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceWarehouseItems.size());
		Assert.assertEquals(newCommerceWarehouseItem,
			commerceWarehouseItems.get(newCommerceWarehouseItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceWarehouseItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceWarehouseItem>() {
				@Override
				public void performAction(
					CommerceWarehouseItem commerceWarehouseItem) {
					Assert.assertNotNull(commerceWarehouseItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouseItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceWarehouseItemId",
				newCommerceWarehouseItem.getCommerceWarehouseItemId()));

		List<CommerceWarehouseItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceWarehouseItem existingCommerceWarehouseItem = result.get(0);

		Assert.assertEquals(existingCommerceWarehouseItem,
			newCommerceWarehouseItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouseItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceWarehouseItemId",
				RandomTestUtil.nextLong()));

		List<CommerceWarehouseItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouseItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceWarehouseItemId"));

		Object newCommerceWarehouseItemId = newCommerceWarehouseItem.getCommerceWarehouseItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceWarehouseItemId",
				new Object[] { newCommerceWarehouseItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceWarehouseItemId = result.get(0);

		Assert.assertEquals(existingCommerceWarehouseItemId,
			newCommerceWarehouseItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceWarehouseItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceWarehouseItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceWarehouseItemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceWarehouseItem newCommerceWarehouseItem = addCommerceWarehouseItem();

		_persistence.clearCache();

		CommerceWarehouseItem existingCommerceWarehouseItem = _persistence.findByPrimaryKey(newCommerceWarehouseItem.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceWarehouseItem.getCommerceWarehouseId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceWarehouseItem,
				"getOriginalCommerceWarehouseId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceWarehouseItem.getCPInstanceId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceWarehouseItem,
				"getOriginalCPInstanceId", new Class<?>[0]));
	}

	protected CommerceWarehouseItem addCommerceWarehouseItem()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceWarehouseItem commerceWarehouseItem = _persistence.create(pk);

		commerceWarehouseItem.setGroupId(RandomTestUtil.nextLong());

		commerceWarehouseItem.setCompanyId(RandomTestUtil.nextLong());

		commerceWarehouseItem.setUserId(RandomTestUtil.nextLong());

		commerceWarehouseItem.setUserName(RandomTestUtil.randomString());

		commerceWarehouseItem.setCreateDate(RandomTestUtil.nextDate());

		commerceWarehouseItem.setModifiedDate(RandomTestUtil.nextDate());

		commerceWarehouseItem.setCommerceWarehouseId(RandomTestUtil.nextLong());

		commerceWarehouseItem.setCPInstanceId(RandomTestUtil.nextLong());

		commerceWarehouseItem.setQuantity(RandomTestUtil.nextInt());

		_commerceWarehouseItems.add(_persistence.update(commerceWarehouseItem));

		return commerceWarehouseItem;
	}

	private List<CommerceWarehouseItem> _commerceWarehouseItems = new ArrayList<CommerceWarehouseItem>();
	private CommerceWarehouseItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}