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

package com.liferay.portlet.shopping.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.shopping.NoSuchItemPriceException;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.service.ShoppingItemPriceLocalServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPriceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class ShoppingItemPricePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ShoppingItemPriceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingItemPrice> iterator = _shoppingItemPrices.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemPrice shoppingItemPrice = _persistence.create(pk);

		Assert.assertNotNull(shoppingItemPrice);

		Assert.assertEquals(shoppingItemPrice.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		_persistence.remove(newShoppingItemPrice);

		ShoppingItemPrice existingShoppingItemPrice = _persistence.fetchByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		Assert.assertNull(existingShoppingItemPrice);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingItemPrice();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemPrice newShoppingItemPrice = _persistence.create(pk);

		newShoppingItemPrice.setItemId(RandomTestUtil.nextLong());

		newShoppingItemPrice.setMinQuantity(RandomTestUtil.nextInt());

		newShoppingItemPrice.setMaxQuantity(RandomTestUtil.nextInt());

		newShoppingItemPrice.setPrice(RandomTestUtil.nextDouble());

		newShoppingItemPrice.setDiscount(RandomTestUtil.nextDouble());

		newShoppingItemPrice.setTaxable(RandomTestUtil.randomBoolean());

		newShoppingItemPrice.setShipping(RandomTestUtil.nextDouble());

		newShoppingItemPrice.setUseShippingFormula(RandomTestUtil.randomBoolean());

		newShoppingItemPrice.setStatus(RandomTestUtil.nextInt());

		_shoppingItemPrices.add(_persistence.update(newShoppingItemPrice));

		ShoppingItemPrice existingShoppingItemPrice = _persistence.findByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemPrice.getItemPriceId(),
			newShoppingItemPrice.getItemPriceId());
		Assert.assertEquals(existingShoppingItemPrice.getItemId(),
			newShoppingItemPrice.getItemId());
		Assert.assertEquals(existingShoppingItemPrice.getMinQuantity(),
			newShoppingItemPrice.getMinQuantity());
		Assert.assertEquals(existingShoppingItemPrice.getMaxQuantity(),
			newShoppingItemPrice.getMaxQuantity());
		AssertUtils.assertEquals(existingShoppingItemPrice.getPrice(),
			newShoppingItemPrice.getPrice());
		AssertUtils.assertEquals(existingShoppingItemPrice.getDiscount(),
			newShoppingItemPrice.getDiscount());
		Assert.assertEquals(existingShoppingItemPrice.getTaxable(),
			newShoppingItemPrice.getTaxable());
		AssertUtils.assertEquals(existingShoppingItemPrice.getShipping(),
			newShoppingItemPrice.getShipping());
		Assert.assertEquals(existingShoppingItemPrice.getUseShippingFormula(),
			newShoppingItemPrice.getUseShippingFormula());
		Assert.assertEquals(existingShoppingItemPrice.getStatus(),
			newShoppingItemPrice.getStatus());
	}

	@Test
	public void testCountByItemId() throws Exception {
		_persistence.countByItemId(RandomTestUtil.nextLong());

		_persistence.countByItemId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		ShoppingItemPrice existingShoppingItemPrice = _persistence.findByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemPrice, newShoppingItemPrice);
	}

	@Test(expected = NoSuchItemPriceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ShoppingItemPrice> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingItemPrice",
			"itemPriceId", true, "itemId", true, "minQuantity", true,
			"maxQuantity", true, "price", true, "discount", true, "taxable",
			true, "shipping", true, "useShippingFormula", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		ShoppingItemPrice existingShoppingItemPrice = _persistence.fetchByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemPrice, newShoppingItemPrice);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemPrice missingShoppingItemPrice = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingItemPrice);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingItemPrice newShoppingItemPrice1 = addShoppingItemPrice();
		ShoppingItemPrice newShoppingItemPrice2 = addShoppingItemPrice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemPrice1.getPrimaryKey());
		primaryKeys.add(newShoppingItemPrice2.getPrimaryKey());

		Map<Serializable, ShoppingItemPrice> shoppingItemPrices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingItemPrices.size());
		Assert.assertEquals(newShoppingItemPrice1,
			shoppingItemPrices.get(newShoppingItemPrice1.getPrimaryKey()));
		Assert.assertEquals(newShoppingItemPrice2,
			shoppingItemPrices.get(newShoppingItemPrice2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingItemPrice> shoppingItemPrices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItemPrices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemPrice.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingItemPrice> shoppingItemPrices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItemPrices.size());
		Assert.assertEquals(newShoppingItemPrice,
			shoppingItemPrices.get(newShoppingItemPrice.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingItemPrice> shoppingItemPrices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItemPrices.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItemPrice.getPrimaryKey());

		Map<Serializable, ShoppingItemPrice> shoppingItemPrices = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItemPrices.size());
		Assert.assertEquals(newShoppingItemPrice,
			shoppingItemPrices.get(newShoppingItemPrice.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingItemPriceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)object;

					Assert.assertNotNull(shoppingItemPrice);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemPriceId",
				newShoppingItemPrice.getItemPriceId()));

		List<ShoppingItemPrice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingItemPrice existingShoppingItemPrice = result.get(0);

		Assert.assertEquals(existingShoppingItemPrice, newShoppingItemPrice);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemPriceId",
				RandomTestUtil.nextLong()));

		List<ShoppingItemPrice> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemPriceId"));

		Object newItemPriceId = newShoppingItemPrice.getItemPriceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemPriceId",
				new Object[] { newItemPriceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingItemPriceId = result.get(0);

		Assert.assertEquals(existingItemPriceId, newItemPriceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemPriceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemPriceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ShoppingItemPrice addShoppingItemPrice()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItemPrice shoppingItemPrice = _persistence.create(pk);

		shoppingItemPrice.setItemId(RandomTestUtil.nextLong());

		shoppingItemPrice.setMinQuantity(RandomTestUtil.nextInt());

		shoppingItemPrice.setMaxQuantity(RandomTestUtil.nextInt());

		shoppingItemPrice.setPrice(RandomTestUtil.nextDouble());

		shoppingItemPrice.setDiscount(RandomTestUtil.nextDouble());

		shoppingItemPrice.setTaxable(RandomTestUtil.randomBoolean());

		shoppingItemPrice.setShipping(RandomTestUtil.nextDouble());

		shoppingItemPrice.setUseShippingFormula(RandomTestUtil.randomBoolean());

		shoppingItemPrice.setStatus(RandomTestUtil.nextInt());

		_shoppingItemPrices.add(_persistence.update(shoppingItemPrice));

		return shoppingItemPrice;
	}

	private List<ShoppingItemPrice> _shoppingItemPrices = new ArrayList<ShoppingItemPrice>();
	private ShoppingItemPricePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}