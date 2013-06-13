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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl;

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
public class ShoppingOrderPersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		ShoppingOrder newShoppingOrder = _persistence.create(pk);

		newShoppingOrder.setGroupId(ServiceTestUtil.nextLong());

		newShoppingOrder.setCompanyId(ServiceTestUtil.nextLong());

		newShoppingOrder.setUserId(ServiceTestUtil.nextLong());

		newShoppingOrder.setUserName(ServiceTestUtil.randomString());

		newShoppingOrder.setCreateDate(ServiceTestUtil.nextDate());

		newShoppingOrder.setModifiedDate(ServiceTestUtil.nextDate());

		newShoppingOrder.setNumber(ServiceTestUtil.randomString());

		newShoppingOrder.setTax(ServiceTestUtil.nextDouble());

		newShoppingOrder.setShipping(ServiceTestUtil.nextDouble());

		newShoppingOrder.setAltShipping(ServiceTestUtil.randomString());

		newShoppingOrder.setRequiresShipping(ServiceTestUtil.randomBoolean());

		newShoppingOrder.setInsure(ServiceTestUtil.randomBoolean());

		newShoppingOrder.setInsurance(ServiceTestUtil.nextDouble());

		newShoppingOrder.setCouponCodes(ServiceTestUtil.randomString());

		newShoppingOrder.setCouponDiscount(ServiceTestUtil.nextDouble());

		newShoppingOrder.setBillingFirstName(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingLastName(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingEmailAddress(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingCompany(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingStreet(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingCity(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingState(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingZip(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingCountry(ServiceTestUtil.randomString());

		newShoppingOrder.setBillingPhone(ServiceTestUtil.randomString());

		newShoppingOrder.setShipToBilling(ServiceTestUtil.randomBoolean());

		newShoppingOrder.setShippingFirstName(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingLastName(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingEmailAddress(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingCompany(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingStreet(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingCity(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingState(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingZip(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingCountry(ServiceTestUtil.randomString());

		newShoppingOrder.setShippingPhone(ServiceTestUtil.randomString());

		newShoppingOrder.setCcName(ServiceTestUtil.randomString());

		newShoppingOrder.setCcType(ServiceTestUtil.randomString());

		newShoppingOrder.setCcNumber(ServiceTestUtil.randomString());

		newShoppingOrder.setCcExpMonth(ServiceTestUtil.nextInt());

		newShoppingOrder.setCcExpYear(ServiceTestUtil.nextInt());

		newShoppingOrder.setCcVerNumber(ServiceTestUtil.randomString());

		newShoppingOrder.setComments(ServiceTestUtil.randomString());

		newShoppingOrder.setPpTxnId(ServiceTestUtil.randomString());

		newShoppingOrder.setPpPaymentStatus(ServiceTestUtil.randomString());

		newShoppingOrder.setPpPaymentGross(ServiceTestUtil.nextDouble());

		newShoppingOrder.setPpReceiverEmail(ServiceTestUtil.randomString());

		newShoppingOrder.setPpPayerEmail(ServiceTestUtil.randomString());

		newShoppingOrder.setSendOrderEmail(ServiceTestUtil.randomBoolean());

		newShoppingOrder.setSendShippingEmail(ServiceTestUtil.randomBoolean());

		_persistence.update(newShoppingOrder);

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
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		ShoppingOrder existingShoppingOrder = _persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertEquals(existingShoppingOrder, newShoppingOrder);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchOrderException");
		}
		catch (NoSuchOrderException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
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
		long pk = ServiceTestUtil.nextLong();

		ShoppingOrder missingShoppingOrder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingOrder);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ShoppingOrderActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ShoppingOrder shoppingOrder = (ShoppingOrder)object;

					Assert.assertNotNull(shoppingOrder);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				ShoppingOrder.class.getClassLoader());

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
				ShoppingOrder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderId",
				ServiceTestUtil.nextLong()));

		List<ShoppingOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				ShoppingOrder.class.getClassLoader());

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
				ShoppingOrder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingOrder newShoppingOrder = addShoppingOrder();

		_persistence.clearCache();

		ShoppingOrderModelImpl existingShoppingOrderModelImpl = (ShoppingOrderModelImpl)_persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingShoppingOrderModelImpl.getNumber(),
				existingShoppingOrderModelImpl.getOriginalNumber()));

		Assert.assertTrue(Validator.equals(
				existingShoppingOrderModelImpl.getPpTxnId(),
				existingShoppingOrderModelImpl.getOriginalPpTxnId()));
	}

	protected ShoppingOrder addShoppingOrder() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingOrder shoppingOrder = _persistence.create(pk);

		shoppingOrder.setGroupId(ServiceTestUtil.nextLong());

		shoppingOrder.setCompanyId(ServiceTestUtil.nextLong());

		shoppingOrder.setUserId(ServiceTestUtil.nextLong());

		shoppingOrder.setUserName(ServiceTestUtil.randomString());

		shoppingOrder.setCreateDate(ServiceTestUtil.nextDate());

		shoppingOrder.setModifiedDate(ServiceTestUtil.nextDate());

		shoppingOrder.setNumber(ServiceTestUtil.randomString());

		shoppingOrder.setTax(ServiceTestUtil.nextDouble());

		shoppingOrder.setShipping(ServiceTestUtil.nextDouble());

		shoppingOrder.setAltShipping(ServiceTestUtil.randomString());

		shoppingOrder.setRequiresShipping(ServiceTestUtil.randomBoolean());

		shoppingOrder.setInsure(ServiceTestUtil.randomBoolean());

		shoppingOrder.setInsurance(ServiceTestUtil.nextDouble());

		shoppingOrder.setCouponCodes(ServiceTestUtil.randomString());

		shoppingOrder.setCouponDiscount(ServiceTestUtil.nextDouble());

		shoppingOrder.setBillingFirstName(ServiceTestUtil.randomString());

		shoppingOrder.setBillingLastName(ServiceTestUtil.randomString());

		shoppingOrder.setBillingEmailAddress(ServiceTestUtil.randomString());

		shoppingOrder.setBillingCompany(ServiceTestUtil.randomString());

		shoppingOrder.setBillingStreet(ServiceTestUtil.randomString());

		shoppingOrder.setBillingCity(ServiceTestUtil.randomString());

		shoppingOrder.setBillingState(ServiceTestUtil.randomString());

		shoppingOrder.setBillingZip(ServiceTestUtil.randomString());

		shoppingOrder.setBillingCountry(ServiceTestUtil.randomString());

		shoppingOrder.setBillingPhone(ServiceTestUtil.randomString());

		shoppingOrder.setShipToBilling(ServiceTestUtil.randomBoolean());

		shoppingOrder.setShippingFirstName(ServiceTestUtil.randomString());

		shoppingOrder.setShippingLastName(ServiceTestUtil.randomString());

		shoppingOrder.setShippingEmailAddress(ServiceTestUtil.randomString());

		shoppingOrder.setShippingCompany(ServiceTestUtil.randomString());

		shoppingOrder.setShippingStreet(ServiceTestUtil.randomString());

		shoppingOrder.setShippingCity(ServiceTestUtil.randomString());

		shoppingOrder.setShippingState(ServiceTestUtil.randomString());

		shoppingOrder.setShippingZip(ServiceTestUtil.randomString());

		shoppingOrder.setShippingCountry(ServiceTestUtil.randomString());

		shoppingOrder.setShippingPhone(ServiceTestUtil.randomString());

		shoppingOrder.setCcName(ServiceTestUtil.randomString());

		shoppingOrder.setCcType(ServiceTestUtil.randomString());

		shoppingOrder.setCcNumber(ServiceTestUtil.randomString());

		shoppingOrder.setCcExpMonth(ServiceTestUtil.nextInt());

		shoppingOrder.setCcExpYear(ServiceTestUtil.nextInt());

		shoppingOrder.setCcVerNumber(ServiceTestUtil.randomString());

		shoppingOrder.setComments(ServiceTestUtil.randomString());

		shoppingOrder.setPpTxnId(ServiceTestUtil.randomString());

		shoppingOrder.setPpPaymentStatus(ServiceTestUtil.randomString());

		shoppingOrder.setPpPaymentGross(ServiceTestUtil.nextDouble());

		shoppingOrder.setPpReceiverEmail(ServiceTestUtil.randomString());

		shoppingOrder.setPpPayerEmail(ServiceTestUtil.randomString());

		shoppingOrder.setSendOrderEmail(ServiceTestUtil.randomBoolean());

		shoppingOrder.setSendShippingEmail(ServiceTestUtil.randomBoolean());

		_persistence.update(shoppingOrder);

		return shoppingOrder;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderPersistenceTest.class);
	private ShoppingOrderPersistence _persistence = (ShoppingOrderPersistence)PortalBeanLocatorUtil.locate(ShoppingOrderPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}