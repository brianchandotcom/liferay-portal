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
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.shipment.client.serdes.v1_0.ShippingAddressSerDes;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Alberti
 */
@RunWith(Arquillian.class)
public class ShippingAddressResourceTest
	extends BaseShippingAddressResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		BigDecimal value = BigDecimal.valueOf(RandomTestUtil.nextDouble());

		_commerceOrder = CommerceTestUtil.createCommerceOrderForShipping(
			_user.getUserId(), _commerceChannel.getGroupId(),
			_commerceCurrency.getCommerceCurrencyId(), value);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceCountry = CommerceInventoryTestUtil.addCommerceCountry(
			_serviceContext);

		_commerceRegion = CommerceInventoryTestUtil.addCommerceRegion(
			_commerceCountry.getCommerceCountryId(), _serviceContext);

		_commerceShipment =
			CommerceShipmentLocalServiceUtil.addCommerceShipment(
				_commerceOrder.getCommerceOrderId(), _serviceContext);
	}

	@Test
	public void testGetShipmentShippingAddress() throws Exception {
		ShippingAddress shippingAddress = _toShippingAddress(
			_commerceShipment.fetchCommerceAddress());

		ShippingAddress getShippingAddress =
			shippingAddressResource.getShipmentShippingAddress(
				_commerceShipment.getCommerceShipmentId());

		assertEquals(shippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Test
	public void testGraphQLGetShipmentShippingAddress() throws Exception {
		ShippingAddress shippingAddress = _toShippingAddress(
			_commerceShipment.fetchCommerceAddress());

		Assert.assertTrue(
			equals(
				shippingAddress,
				ShippingAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"shipmentShippingAddress",
								HashMapBuilder.<String, Object>put(
									"shipmentId",
									_commerceShipment.getCommerceShipmentId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data", "Object/shipmentShippingAddress"))));
	}

	@Test
	public void testPatchShipmentShippingAddress() throws Exception {
		ShippingAddress postShippingAddress = _toShippingAddress(
			_commerceShipment.fetchCommerceAddress());

		ShippingAddress randomPatchShippingAddress =
			randomPatchShippingAddress();

		shippingAddressResource.patchShipmentShippingAddress(
			_commerceShipment.getCommerceShipmentId(),
			randomPatchShippingAddress);

		ShippingAddress expectedPatchShippingAddress =
			postShippingAddress.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchShippingAddress, randomPatchShippingAddress);

		ShippingAddress getShippingAddress =
			shippingAddressResource.getShipmentShippingAddress(
				_commerceShipment.getCommerceShipmentId());

		assertEquals(expectedPatchShippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"city", "countryISOCode", "name", "street1"};
	}

	@Override
	protected ShippingAddress randomShippingAddress() throws Exception {
		return new ShippingAddress() {
			{
				city = StringUtil.toLowerCase(RandomTestUtil.randomString());
				countryISOCode = _commerceCountry.getTwoLettersISOCode();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				latitude = RandomTestUtil.randomDouble();
				longitude = RandomTestUtil.randomDouble();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				phoneNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				regionISOCode = _commerceRegion.getCode();
				street1 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street2 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street3 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				zip = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private String _getRegionISOCode(CommerceAddress commerceAddress)
		throws Exception {

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion == null) {
			return StringPool.BLANK;
		}

		return commerceRegion.getCode();
	}

	private ShippingAddress _toShippingAddress(CommerceAddress commerceAddress)
		throws Exception {

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		return new ShippingAddress() {
			{
				city = commerceAddress.getCity();
				countryISOCode = commerceCountry.getTwoLettersISOCode();
				description = commerceAddress.getDescription();
				externalReferenceCode =
					commerceAddress.getExternalReferenceCode();
				id = commerceAddress.getCommerceAddressId();
				latitude = commerceAddress.getLatitude();
				longitude = commerceAddress.getLongitude();
				name = commerceAddress.getName();
				phoneNumber = commerceAddress.getPhoneNumber();
				regionISOCode = _getRegionISOCode(commerceAddress);
				street1 = commerceAddress.getStreet1();
				street2 = commerceAddress.getStreet2();
				street3 = commerceAddress.getStreet3();
				zip = commerceAddress.getZip();
			}
		};
	}

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCountry _commerceCountry;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceRegion _commerceRegion;

	@DeleteAfterTestRun
	private CommerceShipment _commerceShipment;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}