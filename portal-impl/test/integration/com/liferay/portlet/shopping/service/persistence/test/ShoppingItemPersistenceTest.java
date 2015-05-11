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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.ShoppingItemLocalServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemUtil;

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
public class ShoppingItemPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ShoppingItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingItem> iterator = _shoppingItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItem shoppingItem = _persistence.create(pk);

		Assert.assertNotNull(shoppingItem);

		Assert.assertEquals(shoppingItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		_persistence.remove(newShoppingItem);

		ShoppingItem existingShoppingItem = _persistence.fetchByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertNull(existingShoppingItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItem newShoppingItem = _persistence.create(pk);

		newShoppingItem.setGroupId(RandomTestUtil.nextLong());

		newShoppingItem.setCompanyId(RandomTestUtil.nextLong());

		newShoppingItem.setUserId(RandomTestUtil.nextLong());

		newShoppingItem.setUserName(RandomTestUtil.randomString());

		newShoppingItem.setCreateDate(RandomTestUtil.nextDate());

		newShoppingItem.setModifiedDate(RandomTestUtil.nextDate());

		newShoppingItem.setCategoryId(RandomTestUtil.nextLong());

		newShoppingItem.setSku(RandomTestUtil.randomString());

		newShoppingItem.setName(RandomTestUtil.randomString());

		newShoppingItem.setDescription(RandomTestUtil.randomString());

		newShoppingItem.setProperties(RandomTestUtil.randomString());

		newShoppingItem.setFields(RandomTestUtil.randomBoolean());

		newShoppingItem.setFieldsQuantities(RandomTestUtil.randomString());

		newShoppingItem.setMinQuantity(RandomTestUtil.nextInt());

		newShoppingItem.setMaxQuantity(RandomTestUtil.nextInt());

		newShoppingItem.setPrice(RandomTestUtil.nextDouble());

		newShoppingItem.setDiscount(RandomTestUtil.nextDouble());

		newShoppingItem.setTaxable(RandomTestUtil.randomBoolean());

		newShoppingItem.setShipping(RandomTestUtil.nextDouble());

		newShoppingItem.setUseShippingFormula(RandomTestUtil.randomBoolean());

		newShoppingItem.setRequiresShipping(RandomTestUtil.randomBoolean());

		newShoppingItem.setStockQuantity(RandomTestUtil.nextInt());

		newShoppingItem.setFeatured(RandomTestUtil.randomBoolean());

		newShoppingItem.setSale(RandomTestUtil.randomBoolean());

		newShoppingItem.setSmallImage(RandomTestUtil.randomBoolean());

		newShoppingItem.setSmallImageId(RandomTestUtil.nextLong());

		newShoppingItem.setSmallImageURL(RandomTestUtil.randomString());

		newShoppingItem.setMediumImage(RandomTestUtil.randomBoolean());

		newShoppingItem.setMediumImageId(RandomTestUtil.nextLong());

		newShoppingItem.setMediumImageURL(RandomTestUtil.randomString());

		newShoppingItem.setLargeImage(RandomTestUtil.randomBoolean());

		newShoppingItem.setLargeImageId(RandomTestUtil.nextLong());

		newShoppingItem.setLargeImageURL(RandomTestUtil.randomString());

		_shoppingItems.add(_persistence.update(newShoppingItem));

		ShoppingItem existingShoppingItem = _persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingItem.getItemId(),
			newShoppingItem.getItemId());
		Assert.assertEquals(existingShoppingItem.getGroupId(),
			newShoppingItem.getGroupId());
		Assert.assertEquals(existingShoppingItem.getCompanyId(),
			newShoppingItem.getCompanyId());
		Assert.assertEquals(existingShoppingItem.getUserId(),
			newShoppingItem.getUserId());
		Assert.assertEquals(existingShoppingItem.getUserName(),
			newShoppingItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingItem.getCreateDate()),
			Time.getShortTimestamp(newShoppingItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingItem.getModifiedDate()),
			Time.getShortTimestamp(newShoppingItem.getModifiedDate()));
		Assert.assertEquals(existingShoppingItem.getCategoryId(),
			newShoppingItem.getCategoryId());
		Assert.assertEquals(existingShoppingItem.getSku(),
			newShoppingItem.getSku());
		Assert.assertEquals(existingShoppingItem.getName(),
			newShoppingItem.getName());
		Assert.assertEquals(existingShoppingItem.getDescription(),
			newShoppingItem.getDescription());
		Assert.assertEquals(existingShoppingItem.getProperties(),
			newShoppingItem.getProperties());
		Assert.assertEquals(existingShoppingItem.getFields(),
			newShoppingItem.getFields());
		Assert.assertEquals(existingShoppingItem.getFieldsQuantities(),
			newShoppingItem.getFieldsQuantities());
		Assert.assertEquals(existingShoppingItem.getMinQuantity(),
			newShoppingItem.getMinQuantity());
		Assert.assertEquals(existingShoppingItem.getMaxQuantity(),
			newShoppingItem.getMaxQuantity());
		AssertUtils.assertEquals(existingShoppingItem.getPrice(),
			newShoppingItem.getPrice());
		AssertUtils.assertEquals(existingShoppingItem.getDiscount(),
			newShoppingItem.getDiscount());
		Assert.assertEquals(existingShoppingItem.getTaxable(),
			newShoppingItem.getTaxable());
		AssertUtils.assertEquals(existingShoppingItem.getShipping(),
			newShoppingItem.getShipping());
		Assert.assertEquals(existingShoppingItem.getUseShippingFormula(),
			newShoppingItem.getUseShippingFormula());
		Assert.assertEquals(existingShoppingItem.getRequiresShipping(),
			newShoppingItem.getRequiresShipping());
		Assert.assertEquals(existingShoppingItem.getStockQuantity(),
			newShoppingItem.getStockQuantity());
		Assert.assertEquals(existingShoppingItem.getFeatured(),
			newShoppingItem.getFeatured());
		Assert.assertEquals(existingShoppingItem.getSale(),
			newShoppingItem.getSale());
		Assert.assertEquals(existingShoppingItem.getSmallImage(),
			newShoppingItem.getSmallImage());
		Assert.assertEquals(existingShoppingItem.getSmallImageId(),
			newShoppingItem.getSmallImageId());
		Assert.assertEquals(existingShoppingItem.getSmallImageURL(),
			newShoppingItem.getSmallImageURL());
		Assert.assertEquals(existingShoppingItem.getMediumImage(),
			newShoppingItem.getMediumImage());
		Assert.assertEquals(existingShoppingItem.getMediumImageId(),
			newShoppingItem.getMediumImageId());
		Assert.assertEquals(existingShoppingItem.getMediumImageURL(),
			newShoppingItem.getMediumImageURL());
		Assert.assertEquals(existingShoppingItem.getLargeImage(),
			newShoppingItem.getLargeImage());
		Assert.assertEquals(existingShoppingItem.getLargeImageId(),
			newShoppingItem.getLargeImageId());
		Assert.assertEquals(existingShoppingItem.getLargeImageURL(),
			newShoppingItem.getLargeImageURL());
	}

	@Test
	public void testCountBySmallImageId() throws Exception {
		_persistence.countBySmallImageId(RandomTestUtil.nextLong());

		_persistence.countBySmallImageId(0L);
	}

	@Test
	public void testCountByMediumImageId() throws Exception {
		_persistence.countByMediumImageId(RandomTestUtil.nextLong());

		_persistence.countByMediumImageId(0L);
	}

	@Test
	public void testCountByLargeImageId() throws Exception {
		_persistence.countByLargeImageId(RandomTestUtil.nextLong());

		_persistence.countByLargeImageId(0L);
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_S(0L, StringPool.NULL);

		_persistence.countByC_S(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		ShoppingItem existingShoppingItem = _persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingItem, newShoppingItem);
	}

	@Test(expected = NoSuchItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ShoppingItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingItem", "itemId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"categoryId", true, "sku", true, "name", true, "description", true,
			"properties", true, "fields", true, "fieldsQuantities", true,
			"minQuantity", true, "maxQuantity", true, "price", true,
			"discount", true, "taxable", true, "shipping", true,
			"useShippingFormula", true, "requiresShipping", true,
			"stockQuantity", true, "featured", true, "sale", true,
			"smallImage", true, "smallImageId", true, "smallImageURL", true,
			"mediumImage", true, "mediumImageId", true, "mediumImageURL", true,
			"largeImage", true, "largeImageId", true, "largeImageURL", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		ShoppingItem existingShoppingItem = _persistence.fetchByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingItem, newShoppingItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItem missingShoppingItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingItem newShoppingItem1 = addShoppingItem();
		ShoppingItem newShoppingItem2 = addShoppingItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItem1.getPrimaryKey());
		primaryKeys.add(newShoppingItem2.getPrimaryKey());

		Map<Serializable, ShoppingItem> shoppingItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingItems.size());
		Assert.assertEquals(newShoppingItem1,
			shoppingItems.get(newShoppingItem1.getPrimaryKey()));
		Assert.assertEquals(newShoppingItem2,
			shoppingItems.get(newShoppingItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingItem> shoppingItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingItem> shoppingItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItems.size());
		Assert.assertEquals(newShoppingItem,
			shoppingItems.get(newShoppingItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingItem> shoppingItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingItem.getPrimaryKey());

		Map<Serializable, ShoppingItem> shoppingItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingItems.size());
		Assert.assertEquals(newShoppingItem,
			shoppingItems.get(newShoppingItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingItem shoppingItem = (ShoppingItem)object;

					Assert.assertNotNull(shoppingItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemId",
				newShoppingItem.getItemId()));

		List<ShoppingItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingItem existingShoppingItem = result.get(0);

		Assert.assertEquals(existingShoppingItem, newShoppingItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemId",
				RandomTestUtil.nextLong()));

		List<ShoppingItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemId"));

		Object newItemId = newShoppingItem.getItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemId",
				new Object[] { newItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingItemId = result.get(0);

		Assert.assertEquals(existingItemId, newItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		_persistence.clearCache();

		ShoppingItem existingShoppingItem = _persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingItem.getSmallImageId(),
			ReflectionTestUtil.invoke(existingShoppingItem,
				"getOriginalSmallImageId", new Class<?>[0]));

		Assert.assertEquals(existingShoppingItem.getMediumImageId(),
			ReflectionTestUtil.invoke(existingShoppingItem,
				"getOriginalMediumImageId", new Class<?>[0]));

		Assert.assertEquals(existingShoppingItem.getLargeImageId(),
			ReflectionTestUtil.invoke(existingShoppingItem,
				"getOriginalLargeImageId", new Class<?>[0]));

		Assert.assertEquals(existingShoppingItem.getCompanyId(),
			ReflectionTestUtil.invoke(existingShoppingItem,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingShoppingItem.getSku(),
				ReflectionTestUtil.invoke(existingShoppingItem,
					"getOriginalSku", new Class<?>[0])));
	}

	protected ShoppingItem addShoppingItem() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingItem shoppingItem = _persistence.create(pk);

		shoppingItem.setGroupId(RandomTestUtil.nextLong());

		shoppingItem.setCompanyId(RandomTestUtil.nextLong());

		shoppingItem.setUserId(RandomTestUtil.nextLong());

		shoppingItem.setUserName(RandomTestUtil.randomString());

		shoppingItem.setCreateDate(RandomTestUtil.nextDate());

		shoppingItem.setModifiedDate(RandomTestUtil.nextDate());

		shoppingItem.setCategoryId(RandomTestUtil.nextLong());

		shoppingItem.setSku(RandomTestUtil.randomString());

		shoppingItem.setName(RandomTestUtil.randomString());

		shoppingItem.setDescription(RandomTestUtil.randomString());

		shoppingItem.setProperties(RandomTestUtil.randomString());

		shoppingItem.setFields(RandomTestUtil.randomBoolean());

		shoppingItem.setFieldsQuantities(RandomTestUtil.randomString());

		shoppingItem.setMinQuantity(RandomTestUtil.nextInt());

		shoppingItem.setMaxQuantity(RandomTestUtil.nextInt());

		shoppingItem.setPrice(RandomTestUtil.nextDouble());

		shoppingItem.setDiscount(RandomTestUtil.nextDouble());

		shoppingItem.setTaxable(RandomTestUtil.randomBoolean());

		shoppingItem.setShipping(RandomTestUtil.nextDouble());

		shoppingItem.setUseShippingFormula(RandomTestUtil.randomBoolean());

		shoppingItem.setRequiresShipping(RandomTestUtil.randomBoolean());

		shoppingItem.setStockQuantity(RandomTestUtil.nextInt());

		shoppingItem.setFeatured(RandomTestUtil.randomBoolean());

		shoppingItem.setSale(RandomTestUtil.randomBoolean());

		shoppingItem.setSmallImage(RandomTestUtil.randomBoolean());

		shoppingItem.setSmallImageId(RandomTestUtil.nextLong());

		shoppingItem.setSmallImageURL(RandomTestUtil.randomString());

		shoppingItem.setMediumImage(RandomTestUtil.randomBoolean());

		shoppingItem.setMediumImageId(RandomTestUtil.nextLong());

		shoppingItem.setMediumImageURL(RandomTestUtil.randomString());

		shoppingItem.setLargeImage(RandomTestUtil.randomBoolean());

		shoppingItem.setLargeImageId(RandomTestUtil.nextLong());

		shoppingItem.setLargeImageURL(RandomTestUtil.randomString());

		_shoppingItems.add(_persistence.update(shoppingItem));

		return shoppingItem;
	}

	private List<ShoppingItem> _shoppingItems = new ArrayList<ShoppingItem>();
	private ShoppingItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}