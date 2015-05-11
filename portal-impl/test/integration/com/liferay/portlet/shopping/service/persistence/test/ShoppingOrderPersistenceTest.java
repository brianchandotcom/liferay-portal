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

import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderUtil;

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
public class ShoppingOrderPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ShoppingOrderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingOrder> iterator = _shoppingOrders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingOrder shoppingOrder = _persistence.create(pk);

		Assert.assertNotNull(shoppingOrder);

		Assert.assertEquals(shoppingOrder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		_persistence.remove(newShoppingOrder);

		ShoppingOrder existingShoppingOrder = _persistence.fetchByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertNull(existingShoppingOrder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingOrder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingOrder newShoppingOrder = _persistence.create(pk);

		newShoppingOrder.setGroupId(RandomTestUtil.nextLong());

		newShoppingOrder.setCompanyId(RandomTestUtil.nextLong());

		newShoppingOrder.setUserId(RandomTestUtil.nextLong());

		newShoppingOrder.setUserName(RandomTestUtil.randomString());

		newShoppingOrder.setCreateDate(RandomTestUtil.nextDate());

		newShoppingOrder.setModifiedDate(RandomTestUtil.nextDate());

		newShoppingOrder.setNumber(RandomTestUtil.randomString());

		newShoppingOrder.setTax(RandomTestUtil.nextDouble());

		newShoppingOrder.setShipping(RandomTestUtil.nextDouble());

		newShoppingOrder.setAltShipping(RandomTestUtil.randomString());

		newShoppingOrder.setRequiresShipping(RandomTestUtil.randomBoolean());

		newShoppingOrder.setInsure(RandomTestUtil.randomBoolean());

		newShoppingOrder.setInsurance(RandomTestUtil.nextDouble());

		newShoppingOrder.setCouponCodes(RandomTestUtil.randomString());

		newShoppingOrder.setCouponDiscount(RandomTestUtil.nextDouble());

		newShoppingOrder.setBillingFirstName(RandomTestUtil.randomString());

		newShoppingOrder.setBillingLastName(RandomTestUtil.randomString());

		newShoppingOrder.setBillingEmailAddress(RandomTestUtil.randomString());

		newShoppingOrder.setBillingCompany(RandomTestUtil.randomString());

		newShoppingOrder.setBillingStreet(RandomTestUtil.randomString());

		newShoppingOrder.setBillingCity(RandomTestUtil.randomString());

		newShoppingOrder.setBillingState(RandomTestUtil.randomString());

		newShoppingOrder.setBillingZip(RandomTestUtil.randomString());

		newShoppingOrder.setBillingCountry(RandomTestUtil.randomString());

		newShoppingOrder.setBillingPhone(RandomTestUtil.randomString());

		newShoppingOrder.setShipToBilling(RandomTestUtil.randomBoolean());

		newShoppingOrder.setShippingFirstName(RandomTestUtil.randomString());

		newShoppingOrder.setShippingLastName(RandomTestUtil.randomString());

		newShoppingOrder.setShippingEmailAddress(RandomTestUtil.randomString());

		newShoppingOrder.setShippingCompany(RandomTestUtil.randomString());

		newShoppingOrder.setShippingStreet(RandomTestUtil.randomString());

		newShoppingOrder.setShippingCity(RandomTestUtil.randomString());

		newShoppingOrder.setShippingState(RandomTestUtil.randomString());

		newShoppingOrder.setShippingZip(RandomTestUtil.randomString());

		newShoppingOrder.setShippingCountry(RandomTestUtil.randomString());

		newShoppingOrder.setShippingPhone(RandomTestUtil.randomString());

		newShoppingOrder.setCcName(RandomTestUtil.randomString());

		newShoppingOrder.setCcType(RandomTestUtil.randomString());

		newShoppingOrder.setCcNumber(RandomTestUtil.randomString());

		newShoppingOrder.setCcExpMonth(RandomTestUtil.nextInt());

		newShoppingOrder.setCcExpYear(RandomTestUtil.nextInt());

		newShoppingOrder.setCcVerNumber(RandomTestUtil.randomString());

		newShoppingOrder.setComments(RandomTestUtil.randomString());

		newShoppingOrder.setPpTxnId(RandomTestUtil.randomString());

		newShoppingOrder.setPpPaymentStatus(RandomTestUtil.randomString());

		newShoppingOrder.setPpPaymentGross(RandomTestUtil.nextDouble());

		newShoppingOrder.setPpReceiverEmail(RandomTestUtil.randomString());

		newShoppingOrder.setPpPayerEmail(RandomTestUtil.randomString());

		newShoppingOrder.setSendOrderEmail(RandomTestUtil.randomBoolean());

		newShoppingOrder.setSendShippingEmail(RandomTestUtil.randomBoolean());

		_shoppingOrders.add(_persistence.update(newShoppingOrder));

		ShoppingOrder existingShoppingOrder = _persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrder.getOrderId(),
			newShoppingOrder.getOrderId());
		Assert.assertEquals(existingShoppingOrder.getGroupId(),
			newShoppingOrder.getGroupId());
		Assert.assertEquals(existingShoppingOrder.getCompanyId(),
			newShoppingOrder.getCompanyId());
		Assert.assertEquals(existingShoppingOrder.getUserId(),
			newShoppingOrder.getUserId());
		Assert.assertEquals(existingShoppingOrder.getUserName(),
			newShoppingOrder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingOrder.getCreateDate()),
			Time.getShortTimestamp(newShoppingOrder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingOrder.getModifiedDate()),
			Time.getShortTimestamp(newShoppingOrder.getModifiedDate()));
		Assert.assertEquals(existingShoppingOrder.getNumber(),
			newShoppingOrder.getNumber());
		AssertUtils.assertEquals(existingShoppingOrder.getTax(),
			newShoppingOrder.getTax());
		AssertUtils.assertEquals(existingShoppingOrder.getShipping(),
			newShoppingOrder.getShipping());
		Assert.assertEquals(existingShoppingOrder.getAltShipping(),
			newShoppingOrder.getAltShipping());
		Assert.assertEquals(existingShoppingOrder.getRequiresShipping(),
			newShoppingOrder.getRequiresShipping());
		Assert.assertEquals(existingShoppingOrder.getInsure(),
			newShoppingOrder.getInsure());
		AssertUtils.assertEquals(existingShoppingOrder.getInsurance(),
			newShoppingOrder.getInsurance());
		Assert.assertEquals(existingShoppingOrder.getCouponCodes(),
			newShoppingOrder.getCouponCodes());
		AssertUtils.assertEquals(existingShoppingOrder.getCouponDiscount(),
			newShoppingOrder.getCouponDiscount());
		Assert.assertEquals(existingShoppingOrder.getBillingFirstName(),
			newShoppingOrder.getBillingFirstName());
		Assert.assertEquals(existingShoppingOrder.getBillingLastName(),
			newShoppingOrder.getBillingLastName());
		Assert.assertEquals(existingShoppingOrder.getBillingEmailAddress(),
			newShoppingOrder.getBillingEmailAddress());
		Assert.assertEquals(existingShoppingOrder.getBillingCompany(),
			newShoppingOrder.getBillingCompany());
		Assert.assertEquals(existingShoppingOrder.getBillingStreet(),
			newShoppingOrder.getBillingStreet());
		Assert.assertEquals(existingShoppingOrder.getBillingCity(),
			newShoppingOrder.getBillingCity());
		Assert.assertEquals(existingShoppingOrder.getBillingState(),
			newShoppingOrder.getBillingState());
		Assert.assertEquals(existingShoppingOrder.getBillingZip(),
			newShoppingOrder.getBillingZip());
		Assert.assertEquals(existingShoppingOrder.getBillingCountry(),
			newShoppingOrder.getBillingCountry());
		Assert.assertEquals(existingShoppingOrder.getBillingPhone(),
			newShoppingOrder.getBillingPhone());
		Assert.assertEquals(existingShoppingOrder.getShipToBilling(),
			newShoppingOrder.getShipToBilling());
		Assert.assertEquals(existingShoppingOrder.getShippingFirstName(),
			newShoppingOrder.getShippingFirstName());
		Assert.assertEquals(existingShoppingOrder.getShippingLastName(),
			newShoppingOrder.getShippingLastName());
		Assert.assertEquals(existingShoppingOrder.getShippingEmailAddress(),
			newShoppingOrder.getShippingEmailAddress());
		Assert.assertEquals(existingShoppingOrder.getShippingCompany(),
			newShoppingOrder.getShippingCompany());
		Assert.assertEquals(existingShoppingOrder.getShippingStreet(),
			newShoppingOrder.getShippingStreet());
		Assert.assertEquals(existingShoppingOrder.getShippingCity(),
			newShoppingOrder.getShippingCity());
		Assert.assertEquals(existingShoppingOrder.getShippingState(),
			newShoppingOrder.getShippingState());
		Assert.assertEquals(existingShoppingOrder.getShippingZip(),
			newShoppingOrder.getShippingZip());
		Assert.assertEquals(existingShoppingOrder.getShippingCountry(),
			newShoppingOrder.getShippingCountry());
		Assert.assertEquals(existingShoppingOrder.getShippingPhone(),
			newShoppingOrder.getShippingPhone());
		Assert.assertEquals(existingShoppingOrder.getCcName(),
			newShoppingOrder.getCcName());
		Assert.assertEquals(existingShoppingOrder.getCcType(),
			newShoppingOrder.getCcType());
		Assert.assertEquals(existingShoppingOrder.getCcNumber(),
			newShoppingOrder.getCcNumber());
		Assert.assertEquals(existingShoppingOrder.getCcExpMonth(),
			newShoppingOrder.getCcExpMonth());
		Assert.assertEquals(existingShoppingOrder.getCcExpYear(),
			newShoppingOrder.getCcExpYear());
		Assert.assertEquals(existingShoppingOrder.getCcVerNumber(),
			newShoppingOrder.getCcVerNumber());
		Assert.assertEquals(existingShoppingOrder.getComments(),
			newShoppingOrder.getComments());
		Assert.assertEquals(existingShoppingOrder.getPpTxnId(),
			newShoppingOrder.getPpTxnId());
		Assert.assertEquals(existingShoppingOrder.getPpPaymentStatus(),
			newShoppingOrder.getPpPaymentStatus());
		AssertUtils.assertEquals(existingShoppingOrder.getPpPaymentGross(),
			newShoppingOrder.getPpPaymentGross());
		Assert.assertEquals(existingShoppingOrder.getPpReceiverEmail(),
			newShoppingOrder.getPpReceiverEmail());
		Assert.assertEquals(existingShoppingOrder.getPpPayerEmail(),
			newShoppingOrder.getPpPayerEmail());
		Assert.assertEquals(existingShoppingOrder.getSendOrderEmail(),
			newShoppingOrder.getSendOrderEmail());
		Assert.assertEquals(existingShoppingOrder.getSendShippingEmail(),
			newShoppingOrder.getSendShippingEmail());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByNumber() throws Exception {
		_persistence.countByNumber(StringPool.BLANK);

		_persistence.countByNumber(StringPool.NULL);

		_persistence.countByNumber((String)null);
	}

	@Test
	public void testCountByPPTxnId() throws Exception {
		_persistence.countByPPTxnId(StringPool.BLANK);

		_persistence.countByPPTxnId(StringPool.NULL);

		_persistence.countByPPTxnId((String)null);
	}

	@Test
	public void testCountByG_U_PPPS() throws Exception {
		_persistence.countByG_U_PPPS(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_U_PPPS(0L, 0L, StringPool.NULL);

		_persistence.countByG_U_PPPS(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		ShoppingOrder existingShoppingOrder = _persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrder, newShoppingOrder);
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ShoppingOrder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingOrder", "orderId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"number", true, "tax", true, "shipping", true, "altShipping", true,
			"requiresShipping", true, "insure", true, "insurance", true,
			"couponCodes", true, "couponDiscount", true, "billingFirstName",
			true, "billingLastName", true, "billingEmailAddress", true,
			"billingCompany", true, "billingStreet", true, "billingCity", true,
			"billingState", true, "billingZip", true, "billingCountry", true,
			"billingPhone", true, "shipToBilling", true, "shippingFirstName",
			true, "shippingLastName", true, "shippingEmailAddress", true,
			"shippingCompany", true, "shippingStreet", true, "shippingCity",
			true, "shippingState", true, "shippingZip", true,
			"shippingCountry", true, "shippingPhone", true, "ccName", true,
			"ccType", true, "ccNumber", true, "ccExpMonth", true, "ccExpYear",
			true, "ccVerNumber", true, "comments", true, "ppTxnId", true,
			"ppPaymentStatus", true, "ppPaymentGross", true, "ppReceiverEmail",
			true, "ppPayerEmail", true, "sendOrderEmail", true,
			"sendShippingEmail", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		ShoppingOrder existingShoppingOrder = _persistence.fetchByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrder, newShoppingOrder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingOrder missingShoppingOrder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingOrder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingOrder newShoppingOrder1 = addShoppingOrder();
		ShoppingOrder newShoppingOrder2 = addShoppingOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingOrder1.getPrimaryKey());
		primaryKeys.add(newShoppingOrder2.getPrimaryKey());

		Map<Serializable, ShoppingOrder> shoppingOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingOrders.size());
		Assert.assertEquals(newShoppingOrder1,
			shoppingOrders.get(newShoppingOrder1.getPrimaryKey()));
		Assert.assertEquals(newShoppingOrder2,
			shoppingOrders.get(newShoppingOrder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingOrder> shoppingOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingOrder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingOrder> shoppingOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingOrders.size());
		Assert.assertEquals(newShoppingOrder,
			shoppingOrders.get(newShoppingOrder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingOrder> shoppingOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingOrder.getPrimaryKey());

		Map<Serializable, ShoppingOrder> shoppingOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingOrders.size());
		Assert.assertEquals(newShoppingOrder,
			shoppingOrders.get(newShoppingOrder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingOrderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingOrder shoppingOrder = (ShoppingOrder)object;

					Assert.assertNotNull(shoppingOrder);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderId",
				newShoppingOrder.getOrderId()));

		List<ShoppingOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingOrder existingShoppingOrder = result.get(0);

		Assert.assertEquals(existingShoppingOrder, newShoppingOrder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderId",
				RandomTestUtil.nextLong()));

		List<ShoppingOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderId"));

		Object newOrderId = newShoppingOrder.getOrderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderId",
				new Object[] { newOrderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOrderId = result.get(0);

		Assert.assertEquals(existingOrderId, newOrderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		_persistence.clearCache();

		ShoppingOrder existingShoppingOrder = _persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingShoppingOrder.getNumber(),
				ReflectionTestUtil.invoke(existingShoppingOrder,
					"getOriginalNumber", new Class<?>[0])));

		Assert.assertTrue(Validator.equals(existingShoppingOrder.getPpTxnId(),
				ReflectionTestUtil.invoke(existingShoppingOrder,
					"getOriginalPpTxnId", new Class<?>[0])));
	}

	protected ShoppingOrder addShoppingOrder() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingOrder shoppingOrder = _persistence.create(pk);

		shoppingOrder.setGroupId(RandomTestUtil.nextLong());

		shoppingOrder.setCompanyId(RandomTestUtil.nextLong());

		shoppingOrder.setUserId(RandomTestUtil.nextLong());

		shoppingOrder.setUserName(RandomTestUtil.randomString());

		shoppingOrder.setCreateDate(RandomTestUtil.nextDate());

		shoppingOrder.setModifiedDate(RandomTestUtil.nextDate());

		shoppingOrder.setNumber(RandomTestUtil.randomString());

		shoppingOrder.setTax(RandomTestUtil.nextDouble());

		shoppingOrder.setShipping(RandomTestUtil.nextDouble());

		shoppingOrder.setAltShipping(RandomTestUtil.randomString());

		shoppingOrder.setRequiresShipping(RandomTestUtil.randomBoolean());

		shoppingOrder.setInsure(RandomTestUtil.randomBoolean());

		shoppingOrder.setInsurance(RandomTestUtil.nextDouble());

		shoppingOrder.setCouponCodes(RandomTestUtil.randomString());

		shoppingOrder.setCouponDiscount(RandomTestUtil.nextDouble());

		shoppingOrder.setBillingFirstName(RandomTestUtil.randomString());

		shoppingOrder.setBillingLastName(RandomTestUtil.randomString());

		shoppingOrder.setBillingEmailAddress(RandomTestUtil.randomString());

		shoppingOrder.setBillingCompany(RandomTestUtil.randomString());

		shoppingOrder.setBillingStreet(RandomTestUtil.randomString());

		shoppingOrder.setBillingCity(RandomTestUtil.randomString());

		shoppingOrder.setBillingState(RandomTestUtil.randomString());

		shoppingOrder.setBillingZip(RandomTestUtil.randomString());

		shoppingOrder.setBillingCountry(RandomTestUtil.randomString());

		shoppingOrder.setBillingPhone(RandomTestUtil.randomString());

		shoppingOrder.setShipToBilling(RandomTestUtil.randomBoolean());

		shoppingOrder.setShippingFirstName(RandomTestUtil.randomString());

		shoppingOrder.setShippingLastName(RandomTestUtil.randomString());

		shoppingOrder.setShippingEmailAddress(RandomTestUtil.randomString());

		shoppingOrder.setShippingCompany(RandomTestUtil.randomString());

		shoppingOrder.setShippingStreet(RandomTestUtil.randomString());

		shoppingOrder.setShippingCity(RandomTestUtil.randomString());

		shoppingOrder.setShippingState(RandomTestUtil.randomString());

		shoppingOrder.setShippingZip(RandomTestUtil.randomString());

		shoppingOrder.setShippingCountry(RandomTestUtil.randomString());

		shoppingOrder.setShippingPhone(RandomTestUtil.randomString());

		shoppingOrder.setCcName(RandomTestUtil.randomString());

		shoppingOrder.setCcType(RandomTestUtil.randomString());

		shoppingOrder.setCcNumber(RandomTestUtil.randomString());

		shoppingOrder.setCcExpMonth(RandomTestUtil.nextInt());

		shoppingOrder.setCcExpYear(RandomTestUtil.nextInt());

		shoppingOrder.setCcVerNumber(RandomTestUtil.randomString());

		shoppingOrder.setComments(RandomTestUtil.randomString());

		shoppingOrder.setPpTxnId(RandomTestUtil.randomString());

		shoppingOrder.setPpPaymentStatus(RandomTestUtil.randomString());

		shoppingOrder.setPpPaymentGross(RandomTestUtil.nextDouble());

		shoppingOrder.setPpReceiverEmail(RandomTestUtil.randomString());

		shoppingOrder.setPpPayerEmail(RandomTestUtil.randomString());

		shoppingOrder.setSendOrderEmail(RandomTestUtil.randomBoolean());

		shoppingOrder.setSendShippingEmail(RandomTestUtil.randomBoolean());

		_shoppingOrders.add(_persistence.update(shoppingOrder));

		return shoppingOrder;
	}

	private List<ShoppingOrder> _shoppingOrders = new ArrayList<ShoppingOrder>();
	private ShoppingOrderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}