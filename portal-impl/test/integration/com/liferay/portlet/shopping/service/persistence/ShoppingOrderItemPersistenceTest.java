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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

import com.liferay.portlet.shopping.NoSuchOrderItemException;
import com.liferay.portlet.shopping.model.ShoppingOrderItem;

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
public class ShoppingOrderItemPersistenceTest {
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

		ShoppingOrderItem shoppingOrderItem = _persistence.create(pk);

		Assert.assertNotNull(shoppingOrderItem);

		Assert.assertEquals(shoppingOrderItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		_persistence.remove(newShoppingOrderItem);

		ShoppingOrderItem existingShoppingOrderItem = _persistence.fetchByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		Assert.assertNull(existingShoppingOrderItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingOrderItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingOrderItem newShoppingOrderItem = _persistence.create(pk);

		newShoppingOrderItem.setOrderId(ServiceTestUtil.nextLong());

		newShoppingOrderItem.setItemId(ServiceTestUtil.randomString());

		newShoppingOrderItem.setSku(ServiceTestUtil.randomString());

		newShoppingOrderItem.setName(ServiceTestUtil.randomString());

		newShoppingOrderItem.setDescription(ServiceTestUtil.randomString());

		newShoppingOrderItem.setProperties(ServiceTestUtil.randomString());

		newShoppingOrderItem.setPrice(ServiceTestUtil.nextDouble());

		newShoppingOrderItem.setQuantity(ServiceTestUtil.nextInt());

		newShoppingOrderItem.setShippedDate(ServiceTestUtil.nextDate());

		_persistence.update(newShoppingOrderItem);

		ShoppingOrderItem existingShoppingOrderItem = _persistence.findByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrderItem.getOrderItemId(),
			newShoppingOrderItem.getOrderItemId());
		Assert.assertEquals(existingShoppingOrderItem.getOrderId(),
			newShoppingOrderItem.getOrderId());
		Assert.assertEquals(existingShoppingOrderItem.getItemId(),
			newShoppingOrderItem.getItemId());
		Assert.assertEquals(existingShoppingOrderItem.getSku(),
			newShoppingOrderItem.getSku());
		Assert.assertEquals(existingShoppingOrderItem.getName(),
			newShoppingOrderItem.getName());
		Assert.assertEquals(existingShoppingOrderItem.getDescription(),
			newShoppingOrderItem.getDescription());
		Assert.assertEquals(existingShoppingOrderItem.getProperties(),
			newShoppingOrderItem.getProperties());
		AssertUtils.assertEquals(existingShoppingOrderItem.getPrice(),
			newShoppingOrderItem.getPrice());
		Assert.assertEquals(existingShoppingOrderItem.getQuantity(),
			newShoppingOrderItem.getQuantity());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingOrderItem.getShippedDate()),
			Time.getShortTimestamp(newShoppingOrderItem.getShippedDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		ShoppingOrderItem existingShoppingOrderItem = _persistence.findByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrderItem, newShoppingOrderItem);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchOrderItemException");
		}
		catch (NoSuchOrderItemException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ShoppingOrderItem",
			"orderItemId", true, "orderId", true, "itemId", true, "sku", true,
			"name", true, "description", true, "properties", true, "price",
			true, "quantity", true, "shippedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		ShoppingOrderItem existingShoppingOrderItem = _persistence.fetchByPrimaryKey(newShoppingOrderItem.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrderItem, newShoppingOrderItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingOrderItem missingShoppingOrderItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingOrderItem);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ShoppingOrderItemActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ShoppingOrderItem shoppingOrderItem = (ShoppingOrderItem)object;

					Assert.assertNotNull(shoppingOrderItem);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingOrderItem newShoppingOrderItem = addShoppingOrderItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderItemId",
				newShoppingOrderItem.getOrderItemId()));

		List<ShoppingOrderItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingOrderItem existingShoppingOrderItem = result.get(0);

		Assert.assertEquals(existingShoppingOrderItem, newShoppingOrderItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderItemId",
				ServiceTestUtil.nextLong()));

		List<ShoppingOrderItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingOrderItemId = result.get(0);

		Assert.assertEquals(existingOrderItemId, newOrderItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrderItem.class,
				ShoppingOrderItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderItemId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ShoppingOrderItem addShoppingOrderItem()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingOrderItem shoppingOrderItem = _persistence.create(pk);

		shoppingOrderItem.setOrderId(ServiceTestUtil.nextLong());

		shoppingOrderItem.setItemId(ServiceTestUtil.randomString());

		shoppingOrderItem.setSku(ServiceTestUtil.randomString());

		shoppingOrderItem.setName(ServiceTestUtil.randomString());

		shoppingOrderItem.setDescription(ServiceTestUtil.randomString());

		shoppingOrderItem.setProperties(ServiceTestUtil.randomString());

		shoppingOrderItem.setPrice(ServiceTestUtil.nextDouble());

		shoppingOrderItem.setQuantity(ServiceTestUtil.nextInt());

		shoppingOrderItem.setShippedDate(ServiceTestUtil.nextDate());

		_persistence.update(shoppingOrderItem);

		return shoppingOrderItem;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderItemPersistenceTest.class);
	private ShoppingOrderItemPersistence _persistence = (ShoppingOrderItemPersistence)PortalBeanLocatorUtil.locate(ShoppingOrderItemPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}