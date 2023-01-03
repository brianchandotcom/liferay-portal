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
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.BillingAddressSerDes;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class BillingAddressResourceTest
	extends BaseBillingAddressResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_accountEntry = AccountEntryLocalServiceUtil.addAccountEntry(
			_user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, _serviceContext);

		_country = CountryLocalServiceUtil.addCountry(
			"XY", "XYZ", true, true, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), true, true, false, _serviceContext);

		_region = RegionLocalServiceUtil.addRegion(
			_country.getCountryId(), true, RandomTestUtil.randomString(),
			RandomTestUtil.nextDouble(), RandomTestUtil.randomString(),
			_serviceContext);

		_orderBillingAddress = _addBillingAddress();

		_commerceCurrency =
			CommerceCurrencyLocalServiceUtil.addCommerceCurrency(
				_user.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), BigDecimal.ONE,
				RandomTestUtil.randomLocaleStringMap(), 2, 2, "HALF_EVEN",
				false, RandomTestUtil.nextDouble(), true);

		_commerceOrder = CommerceOrderLocalServiceUtil.addCommerceOrder(
			_user.getUserId(), testGroup.getGroupId(),
			_orderBillingAddress.getId(), _accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId(),
			CommerceOrderConstants.TYPE_PK_FULFILLMENT, 0,
			_orderBillingAddress.getId(), RandomTestUtil.randomString(), 1, 1,
			2022, 0, 0, CommerceOrderConstants.ORDER_STATUS_COMPLETED,
			CommercePaymentConstants.COMMERCE_PAYMENT_METHOD_TYPE_OFFLINE,
			RandomTestUtil.randomString(), BigDecimal.ONE,
			RandomTestUtil.randomString(), BigDecimal.ONE, BigDecimal.ONE,
			BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN,
			_serviceContext);
	}

	@Override
	@Test
	public void testGetOrderByExternalReferenceCodeBillingAddress()
		throws Exception {

		BillingAddress postBillingAddress = _orderBillingAddress;

		BillingAddress getBillingAddress =
			billingAddressResource.
				getOrderByExternalReferenceCodeBillingAddress(
					_commerceOrder.getExternalReferenceCode());

		assertEquals(postBillingAddress, getBillingAddress);
		assertValid(getBillingAddress);
	}

	@Override
	@Test
	public void testGetOrderIdBillingAddress() throws Exception {
		BillingAddress postBillingAddress = _orderBillingAddress;

		BillingAddress getBillingAddress =
			billingAddressResource.getOrderIdBillingAddress(
				_commerceOrder.getCommerceOrderId());

		assertEquals(postBillingAddress, getBillingAddress);
		assertValid(getBillingAddress);
	}

	@Override
	@Test
	public void testGraphQLGetOrderByExternalReferenceCodeBillingAddress()
		throws Exception {

		BillingAddress billingAddress = _orderBillingAddress;

		String externalReferenceCode =
			"\"" + _commerceOrder.getExternalReferenceCode() + "\"";

		Assert.assertTrue(
			equals(
				billingAddress,
				BillingAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderByExternalReferenceCodeBillingAddress",
								HashMapBuilder.<String, Object>put(
									"externalReferenceCode",
									externalReferenceCode
								).build(),
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderByExternalReferenceCodeBillingAddress"))));
	}

	@Override
	@Test
	public void testGraphQLGetOrderIdBillingAddress() throws Exception {
		BillingAddress billingAddress = _orderBillingAddress;

		Assert.assertTrue(
			equals(
				billingAddress,
				BillingAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderIdBillingAddress",
								HashMapBuilder.<String, Object>put(
									"id", _commerceOrder.getCommerceOrderId()
								).build(),
								getGraphQLFields())),
						"JSONObject/data", "Object/orderIdBillingAddress"))));
	}

	@Override
	@Test
	public void testPatchOrderByExternalReferenceCodeBillingAddress()
		throws Exception {

		BillingAddress postBillingAddress = _orderBillingAddress;

		BillingAddress randomPatchBillingAddress = randomPatchBillingAddress();

		billingAddressResource.patchOrderByExternalReferenceCodeBillingAddress(
			_commerceOrder.getExternalReferenceCode(),
			randomPatchBillingAddress);

		BillingAddress expectedBillingAddress = postBillingAddress.clone();

		BeanTestUtil.copyProperties(
			randomPatchBillingAddress, expectedBillingAddress);

		BillingAddress getBillingAddress = _toBillingAddress(
			AddressLocalServiceUtil.getAddressByExternalReferenceCode(
				postBillingAddress.getExternalReferenceCode(),
				testGroup.getCompanyId()));

		assertEquals(expectedBillingAddress, getBillingAddress);
		assertValid(getBillingAddress);
	}

	@Override
	@Test
	public void testPatchOrderIdBillingAddress() throws Exception {
		BillingAddress postBillingAddress = _orderBillingAddress;

		BillingAddress randomPatchBillingAddress = randomPatchBillingAddress();

		billingAddressResource.patchOrderIdBillingAddress(
			_commerceOrder.getCommerceOrderId(), randomPatchBillingAddress);

		BillingAddress expectedBillingAddress = postBillingAddress.clone();

		BeanTestUtil.copyProperties(
			randomPatchBillingAddress, expectedBillingAddress);

		BillingAddress getBillingAddress = _toBillingAddress(
			AddressLocalServiceUtil.getAddress(postBillingAddress.getId()));

		assertEquals(expectedBillingAddress, getBillingAddress);
		assertValid(getBillingAddress);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"city", "description", "name", "phoneNumber", "street1", "street2",
			"street3", "zip"
		};
	}

	@Override
	protected BillingAddress randomBillingAddress() throws Exception {
		return new BillingAddress() {
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
				vatNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				zip = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected BillingAddress
			testGetOrderByExternalReferenceCodeBillingAddress_addBillingAddress()
		throws Exception {

		return _addBillingAddress();
	}

	@Override
	protected BillingAddress testGetOrderIdBillingAddress_addBillingAddress()
		throws Exception {

		return _addBillingAddress();
	}

	@Override
	protected BillingAddress testGraphQLBillingAddress_addBillingAddress()
		throws Exception {

		return _addBillingAddress();
	}

	private BillingAddress _addBillingAddress() throws Exception {
		Address address = AddressLocalServiceUtil.addAddress(
			RandomTestUtil.randomString(), _user.getUserId(),
			AccountEntry.class.getName(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _region.getRegionId(),
			_country.getCountryId(), 0, false, true,
			RandomTestUtil.randomString(), _serviceContext);

		_addresses.add(address);

		return _toBillingAddress(address);
	}

	private BillingAddress _toBillingAddress(Address address) {
		return new BillingAddress() {
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

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private final List<Address> _addresses = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@DeleteAfterTestRun
	private Country _country;

	private BillingAddress _orderBillingAddress;

	@DeleteAfterTestRun
	private Region _region;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}