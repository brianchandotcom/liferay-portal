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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.ShippingAddressSerDes;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class ShippingAddressResourceTest
	extends BaseShippingAddressResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, serviceContext);

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getCommerceAccount(
				_accountEntry.getAccountEntryId());

		_commerceCurrency = _commerceCurrencyLocalService.addCommerceCurrency(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), BigDecimal.ONE,
			RandomTestUtil.randomLocaleStringMap(), 2, 2, "HALF_EVEN", false,
			RandomTestUtil.nextDouble(), true);

		_commerceChannel = _commerceChannelLocalService.addCommerceChannel(
			RandomTestUtil.randomString(), testGroup.getGroupId(),
			RandomTestUtil.randomString(),
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), serviceContext);

		_commerceCPInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_country = _countryLocalService.addCountry(
			"XY", "XYZ", true, true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), true, true, false, serviceContext);

		_region = _regionLocalService.addRegion(
			_country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), RandomTestUtil.randomString(),
			serviceContext);

		_address = _addressLocalService.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), serviceContext);

		_commerceOrder = _commerceOrderLocalService.addCommerceOrder(
			_user.getUserId(), testGroup.getGroupId(), _address.getAddressId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(),
			CommerceOrderConstants.TYPE_PK_FULFILLMENT, 0,
			_address.getAddressId(), RandomTestUtil.randomString(), 1, 1, 2022,
			0, 0, CommerceOrderConstants.ORDER_STATUS_COMPLETED,
			CommercePaymentConstants.COMMERCE_PAYMENT_METHOD_TYPE_OFFLINE,
			RandomTestUtil.randomString(), BigDecimal.ONE,
			RandomTestUtil.randomString(), BigDecimal.ONE, BigDecimal.ONE,
			BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN,
			serviceContext);

		_commerceOrderItem =
			_commerceOrderItemLocalService.addCommerceOrderItem(
				_commerceOrder.getCommerceOrderId(),
				_commerceCPInstance.getCPInstanceId(), null, 1, 1,
				new TestCommerceContext(
					_commerceCurrency, _commerceChannel, _user, testGroup,
					commerceAccount, _commerceOrder),
				serviceContext);

		_commerceOrderItem.setShippingAddressId(_address.getAddressId());

		_commerceOrderItemLocalService.updateCommerceOrderItem(
			_commerceOrderItem);
	}

	@Override
	@Test
	public void testGetOrderByExternalReferenceCodeShippingAddress()
		throws Exception {

		ShippingAddress postShippingAddress = _toShippingAddress(_address);

		ShippingAddress getShippingAddress =
			shippingAddressResource.
				getOrderByExternalReferenceCodeShippingAddress(
					_commerceOrder.getExternalReferenceCode());

		assertEquals(postShippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Override
	@Test
	public void testGetOrderIdShippingAddress() throws Exception {
		ShippingAddress postShippingAddress = _toShippingAddress(_address);

		ShippingAddress getShippingAddress =
			shippingAddressResource.getOrderIdShippingAddress(
				_commerceOrder.getCommerceOrderId());

		assertEquals(postShippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Override
	@Test
	public void testGetOrderItemShippingAddress() throws Exception {
		ShippingAddress postShippingAddress = _toShippingAddress(_address);

		ShippingAddress getShippingAddress =
			shippingAddressResource.getOrderItemShippingAddress(
				_commerceOrderItem.getCommerceOrderItemId());

		assertEquals(postShippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Override
	@Test
	public void testGraphQLGetOrderByExternalReferenceCodeShippingAddress()
		throws Exception {

		ShippingAddress shippingAddress = _toShippingAddress(_address);

		String externalReferenceCode =
			"\"" + _commerceOrder.getExternalReferenceCode() + "\"";

		Assert.assertTrue(
			equals(
				shippingAddress,
				ShippingAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderByExternalReferenceCodeShippingAddress",
								HashMapBuilder.<String, Object>put(
									"externalReferenceCode",
									externalReferenceCode
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderByExternalReferenceCodeShippingAddress"))));
	}

	@Override
	@Test
	public void testGraphQLGetOrderIdShippingAddress() throws Exception {
		ShippingAddress shippingAddress = _toShippingAddress(_address);

		Assert.assertTrue(
			equals(
				shippingAddress,
				ShippingAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderIdShippingAddress",
								HashMapBuilder.<String, Object>put(
									"id", _commerceOrder.getCommerceOrderId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data", "Object/orderIdShippingAddress"))));
	}

	@Override
	@Test
	public void testGraphQLGetOrderItemShippingAddress() throws Exception {
		ShippingAddress shippingAddress = _toShippingAddress(_address);

		Assert.assertTrue(
			equals(
				shippingAddress,
				ShippingAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderItemShippingAddress",
								HashMapBuilder.<String, Object>put(
									"id",
									_commerceOrderItem.getCommerceOrderItemId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderItemShippingAddress"))));
	}

	@Override
	@Test
	public void testPatchOrderByExternalReferenceCodeShippingAddress()
		throws Exception {

		ShippingAddress postShippingAddress = _toShippingAddress(_address);

		ShippingAddress randomPatchShippingAddress =
			randomPatchShippingAddress();

		shippingAddressResource.
			patchOrderByExternalReferenceCodeShippingAddress(
				_commerceOrder.getExternalReferenceCode(),
				randomPatchShippingAddress);

		ShippingAddress expectedShippingAddress = postShippingAddress.clone();

		BeanTestUtil.copyProperties(
			randomPatchShippingAddress, expectedShippingAddress);

		ShippingAddress getShippingAddress = _toShippingAddress(
			AddressLocalServiceUtil.getAddressByExternalReferenceCode(
				postShippingAddress.getExternalReferenceCode(),
				testGroup.getCompanyId()));

		assertEquals(expectedShippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Override
	@Test
	public void testPatchOrderIdShippingAddress() throws Exception {
		ShippingAddress postShippingAddress = _toShippingAddress(_address);

		ShippingAddress randomPatchShippingAddress =
			randomPatchShippingAddress();

		shippingAddressResource.patchOrderIdShippingAddress(
			_commerceOrder.getCommerceOrderId(), randomPatchShippingAddress);

		ShippingAddress expectedShippingAddress = postShippingAddress.clone();

		BeanTestUtil.copyProperties(
			randomPatchShippingAddress, expectedShippingAddress);

		ShippingAddress getShippingAddress = _toShippingAddress(
			AddressLocalServiceUtil.getAddress(postShippingAddress.getId()));

		assertEquals(expectedShippingAddress, getShippingAddress);
		assertValid(getShippingAddress);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"city", "description", "name", "phoneNumber", "street1", "street2",
			"street3", "zip"
		};
	}

	@Override
	protected ShippingAddress randomShippingAddress() throws Exception {
		return new ShippingAddress() {
			{
				city = StringUtil.toLowerCase(RandomTestUtil.randomString());
				countryISOCode = _country.getA2();
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
				regionISOCode = _region.getRegionCode();
				street1 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street2 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street3 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				zip = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private ShippingAddress _toShippingAddress(Address address) {
		return new ShippingAddress() {
			{
				city = address.getCity();
				countryISOCode = _country.getA2();
				description = address.getDescription();
				externalReferenceCode = address.getExternalReferenceCode();
				id = address.getAddressId();
				latitude = address.getLatitude();
				longitude = address.getLongitude();
				name = address.getName();
				phoneNumber = address.getPhoneNumber();
				regionISOCode = _region.getRegionCode();
				street1 = address.getStreet1();
				street2 = address.getStreet2();
				street3 = address.getStreet3();
				zip = address.getZip();
			}
		};
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AddressLocalService _addressLocalService;

	@Inject
	private static CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private static CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private static CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Inject
	private static CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private static CountryLocalService _countryLocalService;

	@Inject
	private static RegionLocalService _regionLocalService;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private Address _address;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CPInstance _commerceCPInstance;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private CommerceOrderItem _commerceOrderItem;

	@DeleteAfterTestRun
	private Country _country;

	@DeleteAfterTestRun
	private Region _region;

	@DeleteAfterTestRun
	private User _user;

}