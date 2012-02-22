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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.shopping.NoSuchOrderItemException;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderItemPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingOrderItemPersistence)PortalBeanLocatorUtil.locate(ShoppingOrderItemPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingOrderItem shoppingOrderItem = _persistence.create(pk);

		assertNotNull(shoppingOrderItem);

		assertEquals(shoppingOrderItem.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		_persistence.remove(newShoppingOrderItem);

		ShoppingOrderItem existingShoppingOrderItem = _persistence.fetchByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		assertNull(existingShoppingOrderItem);
	}

	public void testUpdateNew() throws Exception {
		addShoppingOrderItem();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingOrderItem newShoppingOrderItem = _persistence.create(pk);

		newShoppingOrderItem.setOrderId(nextLong());

		newShoppingOrderItem.setItemId(randomString());

		newShoppingOrderItem.setSku(randomString());

		newShoppingOrderItem.setName(randomString());

		newShoppingOrderItem.setDescription(randomString());

		newShoppingOrderItem.setProperties(randomString());

		newShoppingOrderItem.setPrice(nextDouble());

		newShoppingOrderItem.setQuantity(nextInt());

		newShoppingOrderItem.setShippedDate(nextDate());

		_persistence.update(newShoppingOrderItem, false);

		ShoppingOrderItem existingShoppingOrderItem = _persistence.findByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		assertEquals(existingShoppingOrderItem.getOrderItemId(),
			newShoppingOrderItem.getOrderItemId());
		assertEquals(existingShoppingOrderItem.getOrderId(),
			newShoppingOrderItem.getOrderId());
		assertEquals(existingShoppingOrderItem.getItemId(),
			newShoppingOrderItem.getItemId());
		assertEquals(existingShoppingOrderItem.getSku(),
			newShoppingOrderItem.getSku());
		assertEquals(existingShoppingOrderItem.getName(),
			newShoppingOrderItem.getName());
		assertEquals(existingShoppingOrderItem.getDescription(),
			newShoppingOrderItem.getDescription());
		assertEquals(existingShoppingOrderItem.getProperties(),
			newShoppingOrderItem.getProperties());
		assertEquals(existingShoppingOrderItem.getPrice(),
			newShoppingOrderItem.getPrice());
		assertEquals(existingShoppingOrderItem.getQuantity(),
			newShoppingOrderItem.getQuantity());
		assertEquals(Time.getShortTimestamp(
				existingShoppingOrderItem.getShippedDate()),
			Time.getShortTimestamp(newShoppingOrderItem.getShippedDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		ShoppingOrderItem existingShoppingOrderItem = _persistence.findByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		assertEquals(existingShoppingOrderItem, newShoppingOrderItem);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchOrderItemException");
		}
		catch (NoSuchOrderItemException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		ShoppingOrderItem existingShoppingOrderItem = _persistence.fetchByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		assertEquals(existingShoppingOrderItem, newShoppingOrderItem);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingOrderItem missingShoppingOrderItem = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingOrderItem);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderItemId",
				newShoppingOrderItem.getOrderItemId()));

		List<ShoppingOrderItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingOrderItem existingShoppingOrderItem = result.get(0);

		assertEquals(existingShoppingOrderItem, newShoppingOrderItem);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderItemId", nextLong()));

		List<ShoppingOrderItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderItemId"));

		Object newOrderItemId = newShoppingOrderItem.getOrderItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderItemId",
				new Object[] { newOrderItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingOrderItemId = result.get(0);

		assertEquals(existingOrderItemId, newOrderItemId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderItemId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ShoppingOrderItem addShoppingOrderItem()
		throws Exception {
		long pk = nextLong();

		ShoppingOrderItem shoppingOrderItem = _persistence.create(pk);

		shoppingOrderItem.setOrderId(nextLong());

		shoppingOrderItem.setItemId(randomString());

		shoppingOrderItem.setSku(randomString());

		shoppingOrderItem.setName(randomString());

		shoppingOrderItem.setDescription(randomString());

		shoppingOrderItem.setProperties(randomString());

		shoppingOrderItem.setPrice(nextDouble());

		shoppingOrderItem.setQuantity(nextInt());

		shoppingOrderItem.setShippedDate(nextDate());

		_persistence.update(shoppingOrderItem, false);

		return shoppingOrderItem;
	}

	private ShoppingOrderItemPersistence _persistence;
}