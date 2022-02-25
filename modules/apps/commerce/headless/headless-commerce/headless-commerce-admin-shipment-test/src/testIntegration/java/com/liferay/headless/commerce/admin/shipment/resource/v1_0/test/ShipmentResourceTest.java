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

package com.liferay.headless.commerce.admin.shipment.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.Shipment;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class ShipmentResourceTest extends BaseShipmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		BigDecimal value = BigDecimal.valueOf(RandomTestUtil.nextDouble());

		_commerceOrder = CommerceTestUtil.createCommerceOrderForShipping(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId(), value);
	}

	@Override
	@Test
	public void testDeleteShipmentByExternalReferenceCode() throws Exception {
	}

	@Override
	@Test
	public void testGetShipmentByExternalReferenceCode() throws Exception {
	}

	@Override
	@Test
	public void testPatchShipmentByExternalReferenceCode() throws Exception {
	}

	@Override
	@Test
	public void testPutShipmentByExternalReferenceCode() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"accountId", "shippingAddressId", "shippingMethodId",
			"shippingOptionName"
		};
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected Shipment randomShipment() throws Exception {
		return new Shipment() {
			{
				accountId = _commerceOrder.getCommerceAccountId();
				carrier = StringUtil.toLowerCase(RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				expectedDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				modifiedDate = RandomTestUtil.nextDate();
				orderId = _commerceOrder.getCommerceOrderId();
				shippingAddressId = _commerceOrder.getShippingAddressId();
				shippingDate = RandomTestUtil.nextDate();
				shippingMethodId = _commerceOrder.getCommerceShippingMethodId();
				shippingOptionName = _commerceOrder.getShippingOptionName();
				trackingNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userName = _commerceOrder.getUserName();
			}
		};
	}

	@Override
	protected Shipment testDeleteShipment_addShipment() throws Exception {
		return _addShipment(null);
	}

	@Override
	protected Shipment testDeleteShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		return _addShipment(null);
	}

	@Override
	protected Shipment testGetShipment_addShipment() throws Exception {
		return _addShipment(null);
	}

	@Override
	protected Shipment testGetShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		return _addShipment(null);
	}

	@Override
	protected Shipment testGetShipmentsPage_addShipment(Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment testGraphQLShipment_addShipment() throws Exception {
		return _addShipment(null);
	}

	@Override
	protected Shipment testPatchShipment_addShipment() throws Exception {
		return _addShipment(null);
	}

	@Override
	protected Shipment testPatchShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		return _addShipment(null);
	}

	@Override
	protected Shipment testPostShipment_addShipment(Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment
			testPostShipmentByExternalReferenceCodeStatusDelivered_addShipment(
				Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment
			testPostShipmentByExternalReferenceCodeStatusFinishProcessing_addShipment(
				Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment
			testPostShipmentByExternalReferenceCodeStatusShipped_addShipment(
				Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment testPostShipmentStatusDelivered_addShipment(
			Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment testPostShipmentStatusFinishProcessing_addShipment(
			Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment testPostShipmentStatusShipped_addShipment(
			Shipment shipment)
		throws Exception {

		return _addShipment(shipment.getExternalReferenceCode());
	}

	@Override
	protected Shipment testPutShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		return _addShipment(null);
	}

	private Shipment _addShipment(String externalReferenceCode)
		throws Exception {

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				externalReferenceCode, _commerceOrder.getGroupId(),
				_commerceOrder.getCommerceAccountId(),
				_commerceOrder.getShippingAddressId(),
				_commerceOrder.getCommerceShippingMethodId(),
				_commerceOrder.getShippingOptionName(), _serviceContext);

		_commerceShipments.add(_commerceShipment);

		return _toShipment(_commerceShipment);
	}

	private Shipment _toShipment(CommerceShipment commerceShipment)
		throws Exception {

		return new Shipment() {
			{
				accountId = commerceShipment.getCommerceAccountId();
				carrier = commerceShipment.getCarrier();
				createDate = commerceShipment.getCreateDate();
				expectedDate = commerceShipment.getExpectedDate();
				externalReferenceCode =
					commerceShipment.getExternalReferenceCode();
				id = commerceShipment.getCommerceShipmentId();
				modifiedDate = commerceShipment.getModifiedDate();
				shippingAddressId = commerceShipment.getCommerceAddressId();
				shippingDate = commerceShipment.getShippingDate();
				shippingMethodId =
					commerceShipment.getCommerceShippingMethodId();
				shippingOptionName = commerceShipment.getShippingOptionName();
				trackingNumber = commerceShipment.getTrackingNumber();
				userName = commerceShipment.getUserName();
			}
		};
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment;

	@DeleteAfterTestRun
	private final List<CommerceShipment> _commerceShipments = new ArrayList<>();

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}