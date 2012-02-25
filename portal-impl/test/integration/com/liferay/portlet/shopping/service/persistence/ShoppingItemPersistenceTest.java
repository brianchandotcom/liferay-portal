/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shopping.NoSuchItemException;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingItemPersistence)PortalBeanLocatorUtil.locate(ShoppingItemPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingItem shoppingItem = _persistence.create(pk);

		assertNotNull(shoppingItem);

		assertEquals(shoppingItem.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		_persistence.remove(newShoppingItem);

		ShoppingItem existingShoppingItem = _persistence.fetchByPrimaryKey(newShoppingItem.getPrimaryKey());

		assertNull(existingShoppingItem);
	}

	public void testUpdateNew() throws Exception {
		addShoppingItem();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingItem newShoppingItem = _persistence.create(pk);

		newShoppingItem.setGroupId(nextLong());

		newShoppingItem.setCompanyId(nextLong());

		newShoppingItem.setUserId(nextLong());

		newShoppingItem.setUserName(randomString());

		newShoppingItem.setCreateDate(nextDate());

		newShoppingItem.setModifiedDate(nextDate());

		newShoppingItem.setCategoryId(nextLong());

		newShoppingItem.setSku(randomString());

		newShoppingItem.setName(randomString());

		newShoppingItem.setDescription(randomString());

		newShoppingItem.setProperties(randomString());

		newShoppingItem.setFields(randomBoolean());

		newShoppingItem.setFieldsQuantities(randomString());

		newShoppingItem.setMinQuantity(nextInt());

		newShoppingItem.setMaxQuantity(nextInt());

		newShoppingItem.setPrice(nextDouble());

		newShoppingItem.setDiscount(nextDouble());

		newShoppingItem.setTaxable(randomBoolean());

		newShoppingItem.setShipping(nextDouble());

		newShoppingItem.setUseShippingFormula(randomBoolean());

		newShoppingItem.setRequiresShipping(randomBoolean());

		newShoppingItem.setStockQuantity(nextInt());

		newShoppingItem.setFeatured(randomBoolean());

		newShoppingItem.setSale(randomBoolean());

		newShoppingItem.setSmallImage(randomBoolean());

		newShoppingItem.setSmallImageId(nextLong());

		newShoppingItem.setSmallImageURL(randomString());

		newShoppingItem.setMediumImage(randomBoolean());

		newShoppingItem.setMediumImageId(nextLong());

		newShoppingItem.setMediumImageURL(randomString());

		newShoppingItem.setLargeImage(randomBoolean());

		newShoppingItem.setLargeImageId(nextLong());

		newShoppingItem.setLargeImageURL(randomString());

		_persistence.update(newShoppingItem, false);

		ShoppingItem existingShoppingItem = _persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		assertEquals(existingShoppingItem.getItemId(),
			newShoppingItem.getItemId());
		assertEquals(existingShoppingItem.getGroupId(),
			newShoppingItem.getGroupId());
		assertEquals(existingShoppingItem.getCompanyId(),
			newShoppingItem.getCompanyId());
		assertEquals(existingShoppingItem.getUserId(),
			newShoppingItem.getUserId());
		assertEquals(existingShoppingItem.getUserName(),
			newShoppingItem.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingShoppingItem.getCreateDate()),
			Time.getShortTimestamp(newShoppingItem.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingShoppingItem.getModifiedDate()),
			Time.getShortTimestamp(newShoppingItem.getModifiedDate()));
		assertEquals(existingShoppingItem.getCategoryId(),
			newShoppingItem.getCategoryId());
		assertEquals(existingShoppingItem.getSku(), newShoppingItem.getSku());
		assertEquals(existingShoppingItem.getName(), newShoppingItem.getName());
		assertEquals(existingShoppingItem.getDescription(),
			newShoppingItem.getDescription());
		assertEquals(existingShoppingItem.getProperties(),
			newShoppingItem.getProperties());
		assertEquals(existingShoppingItem.getFields(),
			newShoppingItem.getFields());
		assertEquals(existingShoppingItem.getFieldsQuantities(),
			newShoppingItem.getFieldsQuantities());
		assertEquals(existingShoppingItem.getMinQuantity(),
			newShoppingItem.getMinQuantity());
		assertEquals(existingShoppingItem.getMaxQuantity(),
			newShoppingItem.getMaxQuantity());
		assertEquals(existingShoppingItem.getPrice(), newShoppingItem.getPrice());
		assertEquals(existingShoppingItem.getDiscount(),
			newShoppingItem.getDiscount());
		assertEquals(existingShoppingItem.getTaxable(),
			newShoppingItem.getTaxable());
		assertEquals(existingShoppingItem.getShipping(),
			newShoppingItem.getShipping());
		assertEquals(existingShoppingItem.getUseShippingFormula(),
			newShoppingItem.getUseShippingFormula());
		assertEquals(existingShoppingItem.getRequiresShipping(),
			newShoppingItem.getRequiresShipping());
		assertEquals(existingShoppingItem.getStockQuantity(),
			newShoppingItem.getStockQuantity());
		assertEquals(existingShoppingItem.getFeatured(),
			newShoppingItem.getFeatured());
		assertEquals(existingShoppingItem.getSale(), newShoppingItem.getSale());
		assertEquals(existingShoppingItem.getSmallImage(),
			newShoppingItem.getSmallImage());
		assertEquals(existingShoppingItem.getSmallImageId(),
			newShoppingItem.getSmallImageId());
		assertEquals(existingShoppingItem.getSmallImageURL(),
			newShoppingItem.getSmallImageURL());
		assertEquals(existingShoppingItem.getMediumImage(),
			newShoppingItem.getMediumImage());
		assertEquals(existingShoppingItem.getMediumImageId(),
			newShoppingItem.getMediumImageId());
		assertEquals(existingShoppingItem.getMediumImageURL(),
			newShoppingItem.getMediumImageURL());
		assertEquals(existingShoppingItem.getLargeImage(),
			newShoppingItem.getLargeImage());
		assertEquals(existingShoppingItem.getLargeImageId(),
			newShoppingItem.getLargeImageId());
		assertEquals(existingShoppingItem.getLargeImageURL(),
			newShoppingItem.getLargeImageURL());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		ShoppingItem existingShoppingItem = _persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		assertEquals(existingShoppingItem, newShoppingItem);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchItemException");
		}
		catch (NoSuchItemException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		ShoppingItem existingShoppingItem = _persistence.fetchByPrimaryKey(newShoppingItem.getPrimaryKey());

		assertEquals(existingShoppingItem, newShoppingItem);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingItem missingShoppingItem = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingItem);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItem newShoppingItem = addShoppingItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				ShoppingItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemId",
				newShoppingItem.getItemId()));

		List<ShoppingItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingItem existingShoppingItem = result.get(0);

		assertEquals(existingShoppingItem, newShoppingItem);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				ShoppingItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemId", nextLong()));

		List<ShoppingItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingItemId = result.get(0);

		assertEquals(existingItemId, newItemId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItem.class,
				ShoppingItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("itemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("itemId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingItem newShoppingItem = addShoppingItem();

		_persistence.clearCache();

		ShoppingItemModelImpl existingShoppingItemModelImpl = (ShoppingItemModelImpl)_persistence.findByPrimaryKey(newShoppingItem.getPrimaryKey());

		assertEquals(existingShoppingItemModelImpl.getSmallImageId(),
			existingShoppingItemModelImpl.getOriginalSmallImageId());

		assertEquals(existingShoppingItemModelImpl.getMediumImageId(),
			existingShoppingItemModelImpl.getOriginalMediumImageId());

		assertEquals(existingShoppingItemModelImpl.getLargeImageId(),
			existingShoppingItemModelImpl.getOriginalLargeImageId());

		assertEquals(existingShoppingItemModelImpl.getCompanyId(),
			existingShoppingItemModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingShoppingItemModelImpl.getSku(),
				existingShoppingItemModelImpl.getOriginalSku()));
	}

	protected ShoppingItem addShoppingItem() throws Exception {
		long pk = nextLong();

		ShoppingItem shoppingItem = _persistence.create(pk);

		shoppingItem.setGroupId(nextLong());

		shoppingItem.setCompanyId(nextLong());

		shoppingItem.setUserId(nextLong());

		shoppingItem.setUserName(randomString());

		shoppingItem.setCreateDate(nextDate());

		shoppingItem.setModifiedDate(nextDate());

		shoppingItem.setCategoryId(nextLong());

		shoppingItem.setSku(randomString());

		shoppingItem.setName(randomString());

		shoppingItem.setDescription(randomString());

		shoppingItem.setProperties(randomString());

		shoppingItem.setFields(randomBoolean());

		shoppingItem.setFieldsQuantities(randomString());

		shoppingItem.setMinQuantity(nextInt());

		shoppingItem.setMaxQuantity(nextInt());

		shoppingItem.setPrice(nextDouble());

		shoppingItem.setDiscount(nextDouble());

		shoppingItem.setTaxable(randomBoolean());

		shoppingItem.setShipping(nextDouble());

		shoppingItem.setUseShippingFormula(randomBoolean());

		shoppingItem.setRequiresShipping(randomBoolean());

		shoppingItem.setStockQuantity(nextInt());

		shoppingItem.setFeatured(randomBoolean());

		shoppingItem.setSale(randomBoolean());

		shoppingItem.setSmallImage(randomBoolean());

		shoppingItem.setSmallImageId(nextLong());

		shoppingItem.setSmallImageURL(randomString());

		shoppingItem.setMediumImage(randomBoolean());

		shoppingItem.setMediumImageId(nextLong());

		shoppingItem.setMediumImageURL(randomString());

		shoppingItem.setLargeImage(randomBoolean());

		shoppingItem.setLargeImageId(nextLong());

		shoppingItem.setLargeImageURL(randomString());

		_persistence.update(shoppingItem, false);

		return shoppingItem;
	}

	private ShoppingItemPersistence _persistence;
}