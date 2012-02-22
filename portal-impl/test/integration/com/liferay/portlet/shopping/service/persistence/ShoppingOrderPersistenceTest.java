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

import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingOrderPersistence)PortalBeanLocatorUtil.locate(ShoppingOrderPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingOrder shoppingOrder = _persistence.create(pk);

		assertNotNull(shoppingOrder);

		assertEquals(shoppingOrder.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		_persistence.remove(newShoppingOrder);

		ShoppingOrder existingShoppingOrder = _persistence.fetchByPrimaryKey(newShoppingOrder.getPrimaryKey());

		assertNull(existingShoppingOrder);
	}

	public void testUpdateNew() throws Exception {
		addShoppingOrder();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingOrder newShoppingOrder = _persistence.create(pk);

		newShoppingOrder.setGroupId(nextLong());

		newShoppingOrder.setCompanyId(nextLong());

		newShoppingOrder.setUserId(nextLong());

		newShoppingOrder.setUserName(randomString());

		newShoppingOrder.setCreateDate(nextDate());

		newShoppingOrder.setModifiedDate(nextDate());

		newShoppingOrder.setNumber(randomString());

		newShoppingOrder.setTax(nextDouble());

		newShoppingOrder.setShipping(nextDouble());

		newShoppingOrder.setAltShipping(randomString());

		newShoppingOrder.setRequiresShipping(randomBoolean());

		newShoppingOrder.setInsure(randomBoolean());

		newShoppingOrder.setInsurance(nextDouble());

		newShoppingOrder.setCouponCodes(randomString());

		newShoppingOrder.setCouponDiscount(nextDouble());

		newShoppingOrder.setBillingFirstName(randomString());

		newShoppingOrder.setBillingLastName(randomString());

		newShoppingOrder.setBillingEmailAddress(randomString());

		newShoppingOrder.setBillingCompany(randomString());

		newShoppingOrder.setBillingStreet(randomString());

		newShoppingOrder.setBillingCity(randomString());

		newShoppingOrder.setBillingState(randomString());

		newShoppingOrder.setBillingZip(randomString());

		newShoppingOrder.setBillingCountry(randomString());

		newShoppingOrder.setBillingPhone(randomString());

		newShoppingOrder.setShipToBilling(randomBoolean());

		newShoppingOrder.setShippingFirstName(randomString());

		newShoppingOrder.setShippingLastName(randomString());

		newShoppingOrder.setShippingEmailAddress(randomString());

		newShoppingOrder.setShippingCompany(randomString());

		newShoppingOrder.setShippingStreet(randomString());

		newShoppingOrder.setShippingCity(randomString());

		newShoppingOrder.setShippingState(randomString());

		newShoppingOrder.setShippingZip(randomString());

		newShoppingOrder.setShippingCountry(randomString());

		newShoppingOrder.setShippingPhone(randomString());

		newShoppingOrder.setCcName(randomString());

		newShoppingOrder.setCcType(randomString());

		newShoppingOrder.setCcNumber(randomString());

		newShoppingOrder.setCcExpMonth(nextInt());

		newShoppingOrder.setCcExpYear(nextInt());

		newShoppingOrder.setCcVerNumber(randomString());

		newShoppingOrder.setComments(randomString());

		newShoppingOrder.setPpTxnId(randomString());

		newShoppingOrder.setPpPaymentStatus(randomString());

		newShoppingOrder.setPpPaymentGross(nextDouble());

		newShoppingOrder.setPpReceiverEmail(randomString());

		newShoppingOrder.setPpPayerEmail(randomString());

		newShoppingOrder.setSendOrderEmail(randomBoolean());

		newShoppingOrder.setSendShippingEmail(randomBoolean());

		_persistence.update(newShoppingOrder, false);

		ShoppingOrder existingShoppingOrder = _persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		assertEquals(existingShoppingOrder.getOrderId(),
			newShoppingOrder.getOrderId());
		assertEquals(existingShoppingOrder.getGroupId(),
			newShoppingOrder.getGroupId());
		assertEquals(existingShoppingOrder.getCompanyId(),
			newShoppingOrder.getCompanyId());
		assertEquals(existingShoppingOrder.getUserId(),
			newShoppingOrder.getUserId());
		assertEquals(existingShoppingOrder.getUserName(),
			newShoppingOrder.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingShoppingOrder.getCreateDate()),
			Time.getShortTimestamp(newShoppingOrder.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingShoppingOrder.getModifiedDate()),
			Time.getShortTimestamp(newShoppingOrder.getModifiedDate()));
		assertEquals(existingShoppingOrder.getNumber(),
			newShoppingOrder.getNumber());
		assertEquals(existingShoppingOrder.getTax(), newShoppingOrder.getTax());
		assertEquals(existingShoppingOrder.getShipping(),
			newShoppingOrder.getShipping());
		assertEquals(existingShoppingOrder.getAltShipping(),
			newShoppingOrder.getAltShipping());
		assertEquals(existingShoppingOrder.getRequiresShipping(),
			newShoppingOrder.getRequiresShipping());
		assertEquals(existingShoppingOrder.getInsure(),
			newShoppingOrder.getInsure());
		assertEquals(existingShoppingOrder.getInsurance(),
			newShoppingOrder.getInsurance());
		assertEquals(existingShoppingOrder.getCouponCodes(),
			newShoppingOrder.getCouponCodes());
		assertEquals(existingShoppingOrder.getCouponDiscount(),
			newShoppingOrder.getCouponDiscount());
		assertEquals(existingShoppingOrder.getBillingFirstName(),
			newShoppingOrder.getBillingFirstName());
		assertEquals(existingShoppingOrder.getBillingLastName(),
			newShoppingOrder.getBillingLastName());
		assertEquals(existingShoppingOrder.getBillingEmailAddress(),
			newShoppingOrder.getBillingEmailAddress());
		assertEquals(existingShoppingOrder.getBillingCompany(),
			newShoppingOrder.getBillingCompany());
		assertEquals(existingShoppingOrder.getBillingStreet(),
			newShoppingOrder.getBillingStreet());
		assertEquals(existingShoppingOrder.getBillingCity(),
			newShoppingOrder.getBillingCity());
		assertEquals(existingShoppingOrder.getBillingState(),
			newShoppingOrder.getBillingState());
		assertEquals(existingShoppingOrder.getBillingZip(),
			newShoppingOrder.getBillingZip());
		assertEquals(existingShoppingOrder.getBillingCountry(),
			newShoppingOrder.getBillingCountry());
		assertEquals(existingShoppingOrder.getBillingPhone(),
			newShoppingOrder.getBillingPhone());
		assertEquals(existingShoppingOrder.getShipToBilling(),
			newShoppingOrder.getShipToBilling());
		assertEquals(existingShoppingOrder.getShippingFirstName(),
			newShoppingOrder.getShippingFirstName());
		assertEquals(existingShoppingOrder.getShippingLastName(),
			newShoppingOrder.getShippingLastName());
		assertEquals(existingShoppingOrder.getShippingEmailAddress(),
			newShoppingOrder.getShippingEmailAddress());
		assertEquals(existingShoppingOrder.getShippingCompany(),
			newShoppingOrder.getShippingCompany());
		assertEquals(existingShoppingOrder.getShippingStreet(),
			newShoppingOrder.getShippingStreet());
		assertEquals(existingShoppingOrder.getShippingCity(),
			newShoppingOrder.getShippingCity());
		assertEquals(existingShoppingOrder.getShippingState(),
			newShoppingOrder.getShippingState());
		assertEquals(existingShoppingOrder.getShippingZip(),
			newShoppingOrder.getShippingZip());
		assertEquals(existingShoppingOrder.getShippingCountry(),
			newShoppingOrder.getShippingCountry());
		assertEquals(existingShoppingOrder.getShippingPhone(),
			newShoppingOrder.getShippingPhone());
		assertEquals(existingShoppingOrder.getCcName(),
			newShoppingOrder.getCcName());
		assertEquals(existingShoppingOrder.getCcType(),
			newShoppingOrder.getCcType());
		assertEquals(existingShoppingOrder.getCcNumber(),
			newShoppingOrder.getCcNumber());
		assertEquals(existingShoppingOrder.getCcExpMonth(),
			newShoppingOrder.getCcExpMonth());
		assertEquals(existingShoppingOrder.getCcExpYear(),
			newShoppingOrder.getCcExpYear());
		assertEquals(existingShoppingOrder.getCcVerNumber(),
			newShoppingOrder.getCcVerNumber());
		assertEquals(existingShoppingOrder.getComments(),
			newShoppingOrder.getComments());
		assertEquals(existingShoppingOrder.getPpTxnId(),
			newShoppingOrder.getPpTxnId());
		assertEquals(existingShoppingOrder.getPpPaymentStatus(),
			newShoppingOrder.getPpPaymentStatus());
		assertEquals(existingShoppingOrder.getPpPaymentGross(),
			newShoppingOrder.getPpPaymentGross());
		assertEquals(existingShoppingOrder.getPpReceiverEmail(),
			newShoppingOrder.getPpReceiverEmail());
		assertEquals(existingShoppingOrder.getPpPayerEmail(),
			newShoppingOrder.getPpPayerEmail());
		assertEquals(existingShoppingOrder.getSendOrderEmail(),
			newShoppingOrder.getSendOrderEmail());
		assertEquals(existingShoppingOrder.getSendShippingEmail(),
			newShoppingOrder.getSendShippingEmail());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		ShoppingOrder existingShoppingOrder = _persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		assertEquals(existingShoppingOrder, newShoppingOrder);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchOrderException");
		}
		catch (NoSuchOrderException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		ShoppingOrder existingShoppingOrder = _persistence.fetchByPrimaryKey(newShoppingOrder.getPrimaryKey());

		assertEquals(existingShoppingOrder, newShoppingOrder);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingOrder missingShoppingOrder = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingOrder);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingOrder newShoppingOrder = addShoppingOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				ShoppingOrder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderId",
				newShoppingOrder.getOrderId()));

		List<ShoppingOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingOrder existingShoppingOrder = result.get(0);

		assertEquals(existingShoppingOrder, newShoppingOrder);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				ShoppingOrder.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orderId", nextLong()));

		List<ShoppingOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingOrderId = result.get(0);

		assertEquals(existingOrderId, newOrderId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingOrder.class,
				ShoppingOrder.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orderId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingOrder newShoppingOrder = addShoppingOrder();

		_persistence.clearCache();

		ShoppingOrderModelImpl existingShoppingOrderModelImpl = (ShoppingOrderModelImpl)_persistence.findByPrimaryKey(newShoppingOrder.getPrimaryKey());

		assertTrue(Validator.equals(
				existingShoppingOrderModelImpl.getNumber(),
				existingShoppingOrderModelImpl.getOriginalNumber()));

		assertTrue(Validator.equals(
				existingShoppingOrderModelImpl.getPpTxnId(),
				existingShoppingOrderModelImpl.getOriginalPpTxnId()));
	}

	protected ShoppingOrder addShoppingOrder() throws Exception {
		long pk = nextLong();

		ShoppingOrder shoppingOrder = _persistence.create(pk);

		shoppingOrder.setGroupId(nextLong());

		shoppingOrder.setCompanyId(nextLong());

		shoppingOrder.setUserId(nextLong());

		shoppingOrder.setUserName(randomString());

		shoppingOrder.setCreateDate(nextDate());

		shoppingOrder.setModifiedDate(nextDate());

		shoppingOrder.setNumber(randomString());

		shoppingOrder.setTax(nextDouble());

		shoppingOrder.setShipping(nextDouble());

		shoppingOrder.setAltShipping(randomString());

		shoppingOrder.setRequiresShipping(randomBoolean());

		shoppingOrder.setInsure(randomBoolean());

		shoppingOrder.setInsurance(nextDouble());

		shoppingOrder.setCouponCodes(randomString());

		shoppingOrder.setCouponDiscount(nextDouble());

		shoppingOrder.setBillingFirstName(randomString());

		shoppingOrder.setBillingLastName(randomString());

		shoppingOrder.setBillingEmailAddress(randomString());

		shoppingOrder.setBillingCompany(randomString());

		shoppingOrder.setBillingStreet(randomString());

		shoppingOrder.setBillingCity(randomString());

		shoppingOrder.setBillingState(randomString());

		shoppingOrder.setBillingZip(randomString());

		shoppingOrder.setBillingCountry(randomString());

		shoppingOrder.setBillingPhone(randomString());

		shoppingOrder.setShipToBilling(randomBoolean());

		shoppingOrder.setShippingFirstName(randomString());

		shoppingOrder.setShippingLastName(randomString());

		shoppingOrder.setShippingEmailAddress(randomString());

		shoppingOrder.setShippingCompany(randomString());

		shoppingOrder.setShippingStreet(randomString());

		shoppingOrder.setShippingCity(randomString());

		shoppingOrder.setShippingState(randomString());

		shoppingOrder.setShippingZip(randomString());

		shoppingOrder.setShippingCountry(randomString());

		shoppingOrder.setShippingPhone(randomString());

		shoppingOrder.setCcName(randomString());

		shoppingOrder.setCcType(randomString());

		shoppingOrder.setCcNumber(randomString());

		shoppingOrder.setCcExpMonth(nextInt());

		shoppingOrder.setCcExpYear(nextInt());

		shoppingOrder.setCcVerNumber(randomString());

		shoppingOrder.setComments(randomString());

		shoppingOrder.setPpTxnId(randomString());

		shoppingOrder.setPpPaymentStatus(randomString());

		shoppingOrder.setPpPaymentGross(nextDouble());

		shoppingOrder.setPpReceiverEmail(randomString());

		shoppingOrder.setPpPayerEmail(randomString());

		shoppingOrder.setSendOrderEmail(randomBoolean());

		shoppingOrder.setSendShippingEmail(randomBoolean());

		_persistence.update(shoppingOrder, false);

		return shoppingOrder;
	}

	private ShoppingOrderPersistence _persistence;
}