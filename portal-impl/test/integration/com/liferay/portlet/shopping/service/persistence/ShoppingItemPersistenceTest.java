/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ShoppingItemPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		ShoppingItem newShoppingItem = _persistence.create(pk);

		newShoppingItem.setGroupId(ServiceTestUtil.nextLong());

		newShoppingItem.setCompanyId(ServiceTestUtil.nextLong());

		newShoppingItem.setUserId(ServiceTestUtil.nextLong());

		newShoppingItem.setUserName(ServiceTestUtil.randomString());

		newShoppingItem.setCreateDate(ServiceTestUtil.nextDate());

		newShoppingItem.setModifiedDate(ServiceTestUtil.nextDate());

		newShoppingItem.setCategoryId(ServiceTestUtil.nextLong());

		newShoppingItem.setSku(ServiceTestUtil.randomString());

		newShoppingItem.setName(ServiceTestUtil.randomString());

		newShoppingItem.setDescription(ServiceTestUtil.randomString());

		newShoppingItem.setProperties(ServiceTestUtil.randomString());

		newShoppingItem.setFields(ServiceTestUtil.randomBoolean());

		newShoppingItem.setFieldsQuantities(ServiceTestUtil.randomString());

		newShoppingItem.setMinQuantity(ServiceTestUtil.nextInt());

		newShoppingItem.setMaxQuantity(ServiceTestUtil.nextInt());

		newShoppingItem.setPrice(ServiceTestUtil.nextDouble());

		newShoppingItem.setDiscount(ServiceTestUtil.nextDouble());

		newShoppingItem.setTaxable(ServiceTestUtil.randomBoolean());

		newShoppingItem.setShipping(ServiceTestUtil.nextDouble());

		newShoppingItem.setUseShippingFormula(ServiceTestUtil.randomBoolean());

		newShoppingItem.setRequiresShipping(ServiceTestUtil.randomBoolean());

		newShoppingItem.setStockQuantity(ServiceTestUtil.nextInt());

		newShoppingItem.setFeatured(ServiceTestUtil.randomBoolean());

		newShoppingItem.setSale(ServiceTestUtil.randomBoolean());

		newShoppingItem.setSmallImage(ServiceTestUtil.randomBoolean());

		newShoppingItem.setSmallImageId(ServiceTestUtil.nextLong());

		newShoppingItem.setSmallImageURL(ServiceTestUtil.randomString());

		newShoppingItem.setMediumImage(ServiceTestUtil.randomBoolean());

		newShoppingItem.setMediumImageId(ServiceTestUtil.nextLong());

		newShoppingItem.setMediumImageURL(ServiceTestUtil.randomString());

		newShoppingItem.setLargeImage(ServiceTestUtil.randomBoolean());

		newShoppingItem.setLargeImageId(ServiceTestUtil.nextLong());

		newShoppingItem.setLargeImageURL(ServiceTestUtil.randomString());

		_persistence.update(newShoppingItem);

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
		AssertUtils.assertEquals(
			existingShoppingItem.getPrice(),
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		ShoppingItem existingShoppingItem = _persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingItem, newShoppingItem);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchItemException");
		}
		catch (NoSuchItemException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
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
		long pk = ServiceTestUtil.nextLong();

		ShoppingItem missingShoppingItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingItem);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ShoppingItemActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ShoppingItem shoppingItem = (ShoppingItem)object;

					Assert.assertNotNull(shoppingItem);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				ShoppingItem.class.getClassLoader());

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
				ShoppingItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemId",
				ServiceTestUtil.nextLong()));

		List<ShoppingItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				ShoppingItem.class.getClassLoader());

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
				ShoppingItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingItem newShoppingItem = addShoppingItem();

		_persistence.clearCache();

		ShoppingItemModelImpl existingShoppingItemModelImpl = (ShoppingItemModelImpl)_persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingItemModelImpl.getSmallImageId(),
			existingShoppingItemModelImpl.getOriginalSmallImageId());

		Assert.assertEquals(existingShoppingItemModelImpl.getMediumImageId(),
			existingShoppingItemModelImpl.getOriginalMediumImageId());

		Assert.assertEquals(existingShoppingItemModelImpl.getLargeImageId(),
			existingShoppingItemModelImpl.getOriginalLargeImageId());

		Assert.assertEquals(existingShoppingItemModelImpl.getCompanyId(),
			existingShoppingItemModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingShoppingItemModelImpl.getSku(),
				existingShoppingItemModelImpl.getOriginalSku()));
	}

	protected ShoppingItem addShoppingItem() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingItem shoppingItem = _persistence.create(pk);

		shoppingItem.setGroupId(ServiceTestUtil.nextLong());

		shoppingItem.setCompanyId(ServiceTestUtil.nextLong());

		shoppingItem.setUserId(ServiceTestUtil.nextLong());

		shoppingItem.setUserName(ServiceTestUtil.randomString());

		shoppingItem.setCreateDate(ServiceTestUtil.nextDate());

		shoppingItem.setModifiedDate(ServiceTestUtil.nextDate());

		shoppingItem.setCategoryId(ServiceTestUtil.nextLong());

		shoppingItem.setSku(ServiceTestUtil.randomString());

		shoppingItem.setName(ServiceTestUtil.randomString());

		shoppingItem.setDescription(ServiceTestUtil.randomString());

		shoppingItem.setProperties(ServiceTestUtil.randomString());

		shoppingItem.setFields(ServiceTestUtil.randomBoolean());

		shoppingItem.setFieldsQuantities(ServiceTestUtil.randomString());

		shoppingItem.setMinQuantity(ServiceTestUtil.nextInt());

		shoppingItem.setMaxQuantity(ServiceTestUtil.nextInt());

		shoppingItem.setPrice(ServiceTestUtil.nextDouble());

		shoppingItem.setDiscount(ServiceTestUtil.nextDouble());

		shoppingItem.setTaxable(ServiceTestUtil.randomBoolean());

		shoppingItem.setShipping(ServiceTestUtil.nextDouble());

		shoppingItem.setUseShippingFormula(ServiceTestUtil.randomBoolean());

		shoppingItem.setRequiresShipping(ServiceTestUtil.randomBoolean());

		shoppingItem.setStockQuantity(ServiceTestUtil.nextInt());

		shoppingItem.setFeatured(ServiceTestUtil.randomBoolean());

		shoppingItem.setSale(ServiceTestUtil.randomBoolean());

		shoppingItem.setSmallImage(ServiceTestUtil.randomBoolean());

		shoppingItem.setSmallImageId(ServiceTestUtil.nextLong());

		shoppingItem.setSmallImageURL(ServiceTestUtil.randomString());

		shoppingItem.setMediumImage(ServiceTestUtil.randomBoolean());

		shoppingItem.setMediumImageId(ServiceTestUtil.nextLong());

		shoppingItem.setMediumImageURL(ServiceTestUtil.randomString());

		shoppingItem.setLargeImage(ServiceTestUtil.randomBoolean());

		shoppingItem.setLargeImageId(ServiceTestUtil.nextLong());

		shoppingItem.setLargeImageURL(ServiceTestUtil.randomString());

		_persistence.update(shoppingItem);

		return shoppingItem;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingItemPersistenceTest.class);
	private ShoppingItemPersistence _persistence = (ShoppingItemPersistence)PortalBeanLocatorUtil.locate(ShoppingItemPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}