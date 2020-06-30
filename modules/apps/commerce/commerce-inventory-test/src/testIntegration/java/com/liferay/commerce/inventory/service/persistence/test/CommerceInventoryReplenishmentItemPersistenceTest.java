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

package com.liferay.commerce.inventory.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.exception.NoSuchInventoryReplenishmentItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemLocalServiceUtil;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryReplenishmentItemPersistence;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryReplenishmentItemUtil;
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
public class CommerceInventoryReplenishmentItemPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.inventory.service"));

	@Before
	public void setUp() {
		_persistence = CommerceInventoryReplenishmentItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceInventoryReplenishmentItem> iterator =
			_commerceInventoryReplenishmentItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_persistence.create(pk);

		Assert.assertNotNull(commerceInventoryReplenishmentItem);

		Assert.assertEquals(
			commerceInventoryReplenishmentItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		_persistence.remove(newCommerceInventoryReplenishmentItem);

		CommerceInventoryReplenishmentItem
			existingCommerceInventoryReplenishmentItem =
				_persistence.fetchByPrimaryKey(
					newCommerceInventoryReplenishmentItem.getPrimaryKey());

		Assert.assertNull(existingCommerceInventoryReplenishmentItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceInventoryReplenishmentItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem = _persistence.create(pk);

		newCommerceInventoryReplenishmentItem.setMvccVersion(
			RandomTestUtil.nextLong());

		newCommerceInventoryReplenishmentItem.setCompanyId(
			RandomTestUtil.nextLong());

		newCommerceInventoryReplenishmentItem.setUserId(
			RandomTestUtil.nextLong());

		newCommerceInventoryReplenishmentItem.setUserName(
			RandomTestUtil.randomString());

		newCommerceInventoryReplenishmentItem.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceInventoryReplenishmentItem.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceInventoryReplenishmentItem.setCommerceInventoryWarehouseId(
			RandomTestUtil.nextLong());

		newCommerceInventoryReplenishmentItem.setSku(
			RandomTestUtil.randomString());

		newCommerceInventoryReplenishmentItem.setAvailabilityDate(
			RandomTestUtil.nextDate());

		newCommerceInventoryReplenishmentItem.setQuantity(
			RandomTestUtil.nextInt());

		_commerceInventoryReplenishmentItems.add(
			_persistence.update(newCommerceInventoryReplenishmentItem));

		CommerceInventoryReplenishmentItem
			existingCommerceInventoryReplenishmentItem =
				_persistence.findByPrimaryKey(
					newCommerceInventoryReplenishmentItem.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.getMvccVersion(),
			newCommerceInventoryReplenishmentItem.getMvccVersion());
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.
				getCommerceInventoryReplenishmentItemId(),
			newCommerceInventoryReplenishmentItem.
				getCommerceInventoryReplenishmentItemId());
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.getCompanyId(),
			newCommerceInventoryReplenishmentItem.getCompanyId());
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.getUserId(),
			newCommerceInventoryReplenishmentItem.getUserId());
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.getUserName(),
			newCommerceInventoryReplenishmentItem.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceInventoryReplenishmentItem.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceInventoryReplenishmentItem.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceInventoryReplenishmentItem.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceInventoryReplenishmentItem.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.
				getCommerceInventoryWarehouseId(),
			newCommerceInventoryReplenishmentItem.
				getCommerceInventoryWarehouseId());
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.getSku(),
			newCommerceInventoryReplenishmentItem.getSku());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceInventoryReplenishmentItem.
					getAvailabilityDate()),
			Time.getShortTimestamp(
				newCommerceInventoryReplenishmentItem.getAvailabilityDate()));
		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem.getQuantity(),
			newCommerceInventoryReplenishmentItem.getQuantity());
	}

	@Test
	public void testCountByCommerceInventoryWarehouseId() throws Exception {
		_persistence.countByCommerceInventoryWarehouseId(
			RandomTestUtil.nextLong());

		_persistence.countByCommerceInventoryWarehouseId(0L);
	}

	@Test
	public void testCountBySku() throws Exception {
		_persistence.countBySku("");

		_persistence.countBySku("null");

		_persistence.countBySku((String)null);
	}

	@Test
	public void testCountByAvailabilityDate() throws Exception {
		_persistence.countByAvailabilityDate(RandomTestUtil.nextDate());

		_persistence.countByAvailabilityDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(), "");

		_persistence.countByC_S(0L, "null");

		_persistence.countByC_S(0L, (String)null);
	}

	@Test
	public void testCountByS_AD() throws Exception {
		_persistence.countByS_AD("", RandomTestUtil.nextDate());

		_persistence.countByS_AD("null", RandomTestUtil.nextDate());

		_persistence.countByS_AD((String)null, RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		CommerceInventoryReplenishmentItem
			existingCommerceInventoryReplenishmentItem =
				_persistence.findByPrimaryKey(
					newCommerceInventoryReplenishmentItem.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem,
			newCommerceInventoryReplenishmentItem);
	}

	@Test(expected = NoSuchInventoryReplenishmentItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceInventoryReplenishmentItem>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CIReplenishmentItem", "mvccVersion", true,
			"commerceInventoryReplenishmentItemId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceInventoryWarehouseId", true, "sku",
			true, "availabilityDate", true, "quantity", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		CommerceInventoryReplenishmentItem
			existingCommerceInventoryReplenishmentItem =
				_persistence.fetchByPrimaryKey(
					newCommerceInventoryReplenishmentItem.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem,
			newCommerceInventoryReplenishmentItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventoryReplenishmentItem
			missingCommerceInventoryReplenishmentItem =
				_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceInventoryReplenishmentItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem1 =
				addCommerceInventoryReplenishmentItem();
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem2 =
				addCommerceInventoryReplenishmentItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventoryReplenishmentItem1.getPrimaryKey());
		primaryKeys.add(newCommerceInventoryReplenishmentItem2.getPrimaryKey());

		Map<Serializable, CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceInventoryReplenishmentItems.size());
		Assert.assertEquals(
			newCommerceInventoryReplenishmentItem1,
			commerceInventoryReplenishmentItems.get(
				newCommerceInventoryReplenishmentItem1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceInventoryReplenishmentItem2,
			commerceInventoryReplenishmentItems.get(
				newCommerceInventoryReplenishmentItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceInventoryReplenishmentItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventoryReplenishmentItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceInventoryReplenishmentItems.size());
		Assert.assertEquals(
			newCommerceInventoryReplenishmentItem,
			commerceInventoryReplenishmentItems.get(
				newCommerceInventoryReplenishmentItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceInventoryReplenishmentItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventoryReplenishmentItem.getPrimaryKey());

		Map<Serializable, CommerceInventoryReplenishmentItem>
			commerceInventoryReplenishmentItems =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceInventoryReplenishmentItems.size());
		Assert.assertEquals(
			newCommerceInventoryReplenishmentItem,
			commerceInventoryReplenishmentItems.get(
				newCommerceInventoryReplenishmentItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceInventoryReplenishmentItemLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceInventoryReplenishmentItem>() {

				@Override
				public void performAction(
					CommerceInventoryReplenishmentItem
						commerceInventoryReplenishmentItem) {

					Assert.assertNotNull(commerceInventoryReplenishmentItem);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryReplenishmentItem.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceInventoryReplenishmentItemId",
				newCommerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId()));

		List<CommerceInventoryReplenishmentItem> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceInventoryReplenishmentItem
			existingCommerceInventoryReplenishmentItem = result.get(0);

		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItem,
			newCommerceInventoryReplenishmentItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryReplenishmentItem.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceInventoryReplenishmentItemId",
				RandomTestUtil.nextLong()));

		List<CommerceInventoryReplenishmentItem> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceInventoryReplenishmentItem
			newCommerceInventoryReplenishmentItem =
				addCommerceInventoryReplenishmentItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryReplenishmentItem.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"commerceInventoryReplenishmentItemId"));

		Object newCommerceInventoryReplenishmentItemId =
			newCommerceInventoryReplenishmentItem.
				getCommerceInventoryReplenishmentItemId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceInventoryReplenishmentItemId",
				new Object[] {newCommerceInventoryReplenishmentItemId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceInventoryReplenishmentItemId = result.get(0);

		Assert.assertEquals(
			existingCommerceInventoryReplenishmentItemId,
			newCommerceInventoryReplenishmentItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryReplenishmentItem.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"commerceInventoryReplenishmentItemId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceInventoryReplenishmentItemId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceInventoryReplenishmentItem
			addCommerceInventoryReplenishmentItem()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_persistence.create(pk);

		commerceInventoryReplenishmentItem.setMvccVersion(
			RandomTestUtil.nextLong());

		commerceInventoryReplenishmentItem.setCompanyId(
			RandomTestUtil.nextLong());

		commerceInventoryReplenishmentItem.setUserId(RandomTestUtil.nextLong());

		commerceInventoryReplenishmentItem.setUserName(
			RandomTestUtil.randomString());

		commerceInventoryReplenishmentItem.setCreateDate(
			RandomTestUtil.nextDate());

		commerceInventoryReplenishmentItem.setModifiedDate(
			RandomTestUtil.nextDate());

		commerceInventoryReplenishmentItem.setCommerceInventoryWarehouseId(
			RandomTestUtil.nextLong());

		commerceInventoryReplenishmentItem.setSku(
			RandomTestUtil.randomString());

		commerceInventoryReplenishmentItem.setAvailabilityDate(
			RandomTestUtil.nextDate());

		commerceInventoryReplenishmentItem.setQuantity(
			RandomTestUtil.nextInt());

		_commerceInventoryReplenishmentItems.add(
			_persistence.update(commerceInventoryReplenishmentItem));

		return commerceInventoryReplenishmentItem;
	}

	private List<CommerceInventoryReplenishmentItem>
		_commerceInventoryReplenishmentItems =
			new ArrayList<CommerceInventoryReplenishmentItem>();
	private CommerceInventoryReplenishmentItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}