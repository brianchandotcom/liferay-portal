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

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderUtil;

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
public class CommerceOrderPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceOrderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceOrder> iterator = _commerceOrders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder commerceOrder = _persistence.create(pk);

		Assert.assertNotNull(commerceOrder);

		Assert.assertEquals(commerceOrder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		_persistence.remove(newCommerceOrder);

		CommerceOrder existingCommerceOrder = _persistence.fetchByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertNull(existingCommerceOrder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceOrder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder newCommerceOrder = _persistence.create(pk);

		newCommerceOrder.setUuid(RandomTestUtil.randomString());

		newCommerceOrder.setGroupId(RandomTestUtil.nextLong());

		newCommerceOrder.setCompanyId(RandomTestUtil.nextLong());

		newCommerceOrder.setUserId(RandomTestUtil.nextLong());

		newCommerceOrder.setUserName(RandomTestUtil.randomString());

		newCommerceOrder.setCreateDate(RandomTestUtil.nextDate());

		newCommerceOrder.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceOrder.setSiteGroupId(RandomTestUtil.nextLong());

		newCommerceOrder.setOrderOrganizationId(RandomTestUtil.nextLong());

		newCommerceOrder.setOrderUserId(RandomTestUtil.nextLong());

		newCommerceOrder.setCommerceCurrencyId(RandomTestUtil.nextLong());

		newCommerceOrder.setBillingAddressId(RandomTestUtil.nextLong());

		newCommerceOrder.setShippingAddressId(RandomTestUtil.nextLong());

		newCommerceOrder.setCommercePaymentMethodId(RandomTestUtil.nextLong());

		newCommerceOrder.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		newCommerceOrder.setShippingOptionName(RandomTestUtil.randomString());

		newCommerceOrder.setPurchaseOrderNumber(RandomTestUtil.randomString());

		newCommerceOrder.setSubtotal(RandomTestUtil.nextDouble());

		newCommerceOrder.setShippingPrice(RandomTestUtil.nextDouble());

		newCommerceOrder.setTotal(RandomTestUtil.nextDouble());

		newCommerceOrder.setAdvanceStatus(RandomTestUtil.randomString());

		newCommerceOrder.setPaymentStatus(RandomTestUtil.nextInt());

		newCommerceOrder.setShippingStatus(RandomTestUtil.nextInt());

		newCommerceOrder.setOrderStatus(RandomTestUtil.nextInt());

		newCommerceOrder.setStatus(RandomTestUtil.nextInt());

		newCommerceOrder.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceOrder.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceOrder.setStatusDate(RandomTestUtil.nextDate());

		_commerceOrders.add(_persistence.update(newCommerceOrder));

		CommerceOrder existingCommerceOrder = _persistence.findByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrder.getUuid(),
			newCommerceOrder.getUuid());
		Assert.assertEquals(existingCommerceOrder.getCommerceOrderId(),
			newCommerceOrder.getCommerceOrderId());
		Assert.assertEquals(existingCommerceOrder.getGroupId(),
			newCommerceOrder.getGroupId());
		Assert.assertEquals(existingCommerceOrder.getCompanyId(),
			newCommerceOrder.getCompanyId());
		Assert.assertEquals(existingCommerceOrder.getUserId(),
			newCommerceOrder.getUserId());
		Assert.assertEquals(existingCommerceOrder.getUserName(),
			newCommerceOrder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceOrder.getCreateDate()),
			Time.getShortTimestamp(newCommerceOrder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceOrder.getModifiedDate()),
			Time.getShortTimestamp(newCommerceOrder.getModifiedDate()));
		Assert.assertEquals(existingCommerceOrder.getSiteGroupId(),
			newCommerceOrder.getSiteGroupId());
		Assert.assertEquals(existingCommerceOrder.getOrderOrganizationId(),
			newCommerceOrder.getOrderOrganizationId());
		Assert.assertEquals(existingCommerceOrder.getOrderUserId(),
			newCommerceOrder.getOrderUserId());
		Assert.assertEquals(existingCommerceOrder.getCommerceCurrencyId(),
			newCommerceOrder.getCommerceCurrencyId());
		Assert.assertEquals(existingCommerceOrder.getBillingAddressId(),
			newCommerceOrder.getBillingAddressId());
		Assert.assertEquals(existingCommerceOrder.getShippingAddressId(),
			newCommerceOrder.getShippingAddressId());
		Assert.assertEquals(existingCommerceOrder.getCommercePaymentMethodId(),
			newCommerceOrder.getCommercePaymentMethodId());
		Assert.assertEquals(existingCommerceOrder.getCommerceShippingMethodId(),
			newCommerceOrder.getCommerceShippingMethodId());
		Assert.assertEquals(existingCommerceOrder.getShippingOptionName(),
			newCommerceOrder.getShippingOptionName());
		Assert.assertEquals(existingCommerceOrder.getPurchaseOrderNumber(),
			newCommerceOrder.getPurchaseOrderNumber());
		AssertUtils.assertEquals(existingCommerceOrder.getSubtotal(),
			newCommerceOrder.getSubtotal());
		AssertUtils.assertEquals(existingCommerceOrder.getShippingPrice(),
			newCommerceOrder.getShippingPrice());
		AssertUtils.assertEquals(existingCommerceOrder.getTotal(),
			newCommerceOrder.getTotal());
		Assert.assertEquals(existingCommerceOrder.getAdvanceStatus(),
			newCommerceOrder.getAdvanceStatus());
		Assert.assertEquals(existingCommerceOrder.getPaymentStatus(),
			newCommerceOrder.getPaymentStatus());
		Assert.assertEquals(existingCommerceOrder.getShippingStatus(),
			newCommerceOrder.getShippingStatus());
		Assert.assertEquals(existingCommerceOrder.getOrderStatus(),
			newCommerceOrder.getOrderStatus());
		Assert.assertEquals(existingCommerceOrder.getStatus(),
			newCommerceOrder.getStatus());
		Assert.assertEquals(existingCommerceOrder.getStatusByUserId(),
			newCommerceOrder.getStatusByUserId());
		Assert.assertEquals(existingCommerceOrder.getStatusByUserName(),
			newCommerceOrder.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceOrder.getStatusDate()),
			Time.getShortTimestamp(newCommerceOrder.getStatusDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByBillingAddressId() throws Exception {
		_persistence.countByBillingAddressId(RandomTestUtil.nextLong());

		_persistence.countByBillingAddressId(0L);
	}

	@Test
	public void testCountByShippingAddressId() throws Exception {
		_persistence.countByShippingAddressId(RandomTestUtil.nextLong());

		_persistence.countByShippingAddressId(0L);
	}

	@Test
	public void testCountByG_O() throws Exception {
		_persistence.countByG_O(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_O(0L, 0L);
	}

	@Test
	public void testCountByG_U_O() throws Exception {
		_persistence.countByG_U_O(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_U_O(0L, 0L, 0);
	}

	@Test
	public void testCountByU_LtC_O() throws Exception {
		_persistence.countByU_LtC_O(RandomTestUtil.nextLong(),
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByU_LtC_O(0L, RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		CommerceOrder existingCommerceOrder = _persistence.findByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrder, newCommerceOrder);
	}

	@Test(expected = NoSuchOrderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceOrder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceOrder", "uuid",
			true, "commerceOrderId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "siteGroupId", true, "orderOrganizationId",
			true, "orderUserId", true, "commerceCurrencyId", true,
			"billingAddressId", true, "shippingAddressId", true,
			"commercePaymentMethodId", true, "commerceShippingMethodId", true,
			"shippingOptionName", true, "purchaseOrderNumber", true,
			"subtotal", true, "shippingPrice", true, "total", true,
			"advanceStatus", true, "paymentStatus", true, "shippingStatus",
			true, "orderStatus", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		CommerceOrder existingCommerceOrder = _persistence.fetchByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrder, newCommerceOrder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder missingCommerceOrder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceOrder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceOrder newCommerceOrder1 = addCommerceOrder();
		CommerceOrder newCommerceOrder2 = addCommerceOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrder1.getPrimaryKey());
		primaryKeys.add(newCommerceOrder2.getPrimaryKey());

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceOrders.size());
		Assert.assertEquals(newCommerceOrder1,
			commerceOrders.get(newCommerceOrder1.getPrimaryKey()));
		Assert.assertEquals(newCommerceOrder2,
			commerceOrders.get(newCommerceOrder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrders.size());
		Assert.assertEquals(newCommerceOrder,
			commerceOrders.get(newCommerceOrder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrder.getPrimaryKey());

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrders.size());
		Assert.assertEquals(newCommerceOrder,
			commerceOrders.get(newCommerceOrder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceOrderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceOrder>() {
				@Override
				public void performAction(CommerceOrder commerceOrder) {
					Assert.assertNotNull(commerceOrder);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceOrderId",
				newCommerceOrder.getCommerceOrderId()));

		List<CommerceOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceOrder existingCommerceOrder = result.get(0);

		Assert.assertEquals(existingCommerceOrder, newCommerceOrder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceOrderId",
				RandomTestUtil.nextLong()));

		List<CommerceOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceOrderId"));

		Object newCommerceOrderId = newCommerceOrder.getCommerceOrderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceOrderId",
				new Object[] { newCommerceOrderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceOrderId = result.get(0);

		Assert.assertEquals(existingCommerceOrderId, newCommerceOrderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceOrderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceOrderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		_persistence.clearCache();

		CommerceOrder existingCommerceOrder = _persistence.findByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCommerceOrder.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceOrder,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCommerceOrder.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceOrder,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceOrder addCommerceOrder() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder commerceOrder = _persistence.create(pk);

		commerceOrder.setUuid(RandomTestUtil.randomString());

		commerceOrder.setGroupId(RandomTestUtil.nextLong());

		commerceOrder.setCompanyId(RandomTestUtil.nextLong());

		commerceOrder.setUserId(RandomTestUtil.nextLong());

		commerceOrder.setUserName(RandomTestUtil.randomString());

		commerceOrder.setCreateDate(RandomTestUtil.nextDate());

		commerceOrder.setModifiedDate(RandomTestUtil.nextDate());

		commerceOrder.setSiteGroupId(RandomTestUtil.nextLong());

		commerceOrder.setOrderOrganizationId(RandomTestUtil.nextLong());

		commerceOrder.setOrderUserId(RandomTestUtil.nextLong());

		commerceOrder.setCommerceCurrencyId(RandomTestUtil.nextLong());

		commerceOrder.setBillingAddressId(RandomTestUtil.nextLong());

		commerceOrder.setShippingAddressId(RandomTestUtil.nextLong());

		commerceOrder.setCommercePaymentMethodId(RandomTestUtil.nextLong());

		commerceOrder.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		commerceOrder.setShippingOptionName(RandomTestUtil.randomString());

		commerceOrder.setPurchaseOrderNumber(RandomTestUtil.randomString());

		commerceOrder.setSubtotal(RandomTestUtil.nextDouble());

		commerceOrder.setShippingPrice(RandomTestUtil.nextDouble());

		commerceOrder.setTotal(RandomTestUtil.nextDouble());

		commerceOrder.setAdvanceStatus(RandomTestUtil.randomString());

		commerceOrder.setPaymentStatus(RandomTestUtil.nextInt());

		commerceOrder.setShippingStatus(RandomTestUtil.nextInt());

		commerceOrder.setOrderStatus(RandomTestUtil.nextInt());

		commerceOrder.setStatus(RandomTestUtil.nextInt());

		commerceOrder.setStatusByUserId(RandomTestUtil.nextLong());

		commerceOrder.setStatusByUserName(RandomTestUtil.randomString());

		commerceOrder.setStatusDate(RandomTestUtil.nextDate());

		_commerceOrders.add(_persistence.update(commerceOrder));

		return commerceOrder;
	}

	private List<CommerceOrder> _commerceOrders = new ArrayList<CommerceOrder>();
	private CommerceOrderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}