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

package com.liferay.address.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AddressLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAddress() throws Exception {
		String phoneNumber = "1234567890";

		Address address = _addAddress(phoneNumber);

		Assert.assertEquals(phoneNumber, address.getPhoneNumber());

		List<Phone> phones = _phoneLocalService.getPhones(
			address.getCompanyId(), Address.class.getName(),
			address.getAddressId());

		Phone phone = phones.get(0);

		Assert.assertEquals(phoneNumber, phone.getNumber());

		_addressLocalService.deleteAddress(address);

		Assert.assertTrue(
			ListUtil.isEmpty(
				_phoneLocalService.getPhones(
					address.getCompanyId(), address.getClassName(),
					address.getAddressId())));
	}

	@Test
	public void testSearchAddresses() throws Exception {
		String keywords = RandomTestUtil.randomString();

		Address address1 = _addAddress(null);
		Address address2 = _addAddress(
			keywords + RandomTestUtil.randomString(), -1, null);
		Address address3 = _addAddress(
			keywords + RandomTestUtil.randomString(), -1, null);

		_assertSearchAddress(
			Arrays.asList(address1, address2, address3), null, null);
		_assertSearchAddress(Arrays.asList(address2, address3), keywords, null);
	}

	@Test
	public void testSearchAddressesPagination() throws Exception {
		String keywords = RandomTestUtil.randomString();

		List<Address> expectedAddresses = Arrays.asList(
			_addAddress(keywords + RandomTestUtil.randomString(), -1, null),
			_addAddress(keywords + RandomTestUtil.randomString(), -1, null),
			_addAddress(keywords + RandomTestUtil.randomString(), -1, null),
			_addAddress(keywords + RandomTestUtil.randomString(), -1, null),
			_addAddress(keywords + RandomTestUtil.randomString(), -1, null));

		Comparator<Address> comparator = Comparator.comparing(
			Address::getName, String.CASE_INSENSITIVE_ORDER);

		_assertSearchAddressesPaginationSort(
			ListUtil.sort(expectedAddresses, comparator), keywords,
			SortFactoryUtil.create("name", false));
		_assertSearchAddressesPaginationSort(
			ListUtil.sort(expectedAddresses, comparator.reversed()), keywords,
			SortFactoryUtil.create("name", true));
	}

	@Test
	public void testSearchAddressesWithInvalidTypeName() throws Exception {
		ListType businessType = _listTypeLocalService.getListType(
			"business", ListTypeConstants.CONTACT_ADDRESS);

		Address address = _addAddress(
			RandomTestUtil.randomString(), businessType.getListTypeId(), null);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_LOG_NAME, Level.DEBUG)) {

			String typeName = RandomTestUtil.randomString();

			_assertSearchAddress(
				Collections.emptyList(), null,
				_getLinkedHashMap("typeNames", new String[] {typeName}));
			_assertSearchAddress(
				Arrays.asList(address), null,
				_getLinkedHashMap(
					"typeNames",
					new String[] {businessType.getName(), typeName}));

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"No list type found for ",
					ListTypeConstants.CONTACT_ADDRESS, " with the name: ",
					typeName),
				loggingEvent.getMessage());
		}
	}

	@Test
	public void testSearchAddressesWithParam() throws Exception {
		ListType businessType = _listTypeLocalService.getListType(
			"business", ListTypeConstants.CONTACT_ADDRESS);

		Address businessAddress = _addAddress(
			RandomTestUtil.randomString(), businessType.getListTypeId(), null);

		ListType personalType = _listTypeLocalService.getListType(
			"personal", ListTypeConstants.CONTACT_ADDRESS);

		Address personalAddress = _addAddress(
			RandomTestUtil.randomString(), personalType.getListTypeId(), null);

		_assertSearchAddress(
			Arrays.asList(businessAddress), null,
			_getLinkedHashMap(
				"typeIds", new long[] {businessType.getListTypeId()}));
		_assertSearchAddress(
			Arrays.asList(businessAddress, personalAddress), null,
			_getLinkedHashMap(
				"typeIds",
				new long[] {
					businessType.getListTypeId(), personalType.getListTypeId()
				}));
		_assertSearchAddress(
			Arrays.asList(businessAddress), null,
			_getLinkedHashMap(
				"typeNames", new String[] {businessType.getName()}));
		_assertSearchAddress(
			Arrays.asList(businessAddress, personalAddress), null,
			_getLinkedHashMap(
				"typeNames",
				new String[] {businessType.getName(), personalType.getName()}));
	}

	@Test
	public void testUpdateAddress() throws Exception {
		Address address = _addAddress("1234567890");

		Address updatedAddress = _addressLocalService.updateAddress(
			address.getAddressId(), address.getName(), address.getDescription(),
			address.getStreet1(), address.getStreet2(), address.getStreet3(),
			address.getCity(), address.getZip(), address.getRegionId(),
			address.getCountryId(), address.getTypeId(), address.isMailing(),
			address.isPrimary(), "1112223333");

		List<Phone> phones = _phoneLocalService.getPhones(
			address.getCompanyId(), Address.class.getName(),
			address.getAddressId());

		Assert.assertEquals(phones.toString(), 1, phones.size());

		Phone phone = phones.get(0);

		Assert.assertEquals(updatedAddress.getPhoneNumber(), phone.getNumber());
	}

	private Address _addAddress(String phoneNumber) throws Exception {
		return _addAddress(RandomTestUtil.randomString(), -1, phoneNumber);
	}

	private Address _addAddress(String name, long typeId, String phoneNumber)
		throws Exception {

		User user = TestPropsValues.getUser();

		if (typeId < 0) {
			ListType listType = _listTypeLocalService.getListType(
				"personal", ListTypeConstants.CONTACT_ADDRESS);

			typeId = listType.getListTypeId();
		}

		return _addressLocalService.addAddress(
			null, user.getUserId(), Contact.class.getName(),
			user.getContactId(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString(), null, 0, 0, typeId, false, false,
			phoneNumber, ServiceContextTestUtil.getServiceContext());
	}

	private void _assertSearchAddress(
			List<Address> expectedAddresses, String keyword,
			LinkedHashMap<String, Object> params)
		throws Exception {

		User user = TestPropsValues.getUser();

		BaseModelSearchResult<Address> baseModelSearchResult =
			_addressLocalService.searchAddresses(
				user.getCompanyId(), Contact.class.getName(),
				user.getContactId(), keyword, params, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			expectedAddresses.size(), baseModelSearchResult.getLength());
		Assert.assertTrue(
			expectedAddresses.containsAll(
				baseModelSearchResult.getBaseModels()));
	}

	private void _assertSearchAddressesPaginationSort(
			List<Address> expectedAddresses, String keywords, Sort sort)
		throws Exception {

		int end = 3;
		int start = 1;

		User user = TestPropsValues.getUser();

		BaseModelSearchResult<Address> baseModelSearchResult =
			_addressLocalService.searchAddresses(
				user.getCompanyId(), Contact.class.getName(),
				user.getContactId(), keywords, null, start, end, sort);

		List<Address> actualAddresses = baseModelSearchResult.getBaseModels();

		Assert.assertEquals(
			actualAddresses.toString(), end - start, actualAddresses.size());

		for (int i = 0; i < (end - start); i++) {
			Assert.assertEquals(
				expectedAddresses.get(start + i), actualAddresses.get(i));
		}
	}

	private LinkedHashMap<String, Object> _getLinkedHashMap(
		String key, Object value) {

		return LinkedHashMapBuilder.<String, Object>put(
			key, value
		).build();
	}

	private static final String _LOG_NAME =
		"com.liferay.address.internal.search.spi.model.query.contributor." +
			"AddressModelPreFilterContributor";

	@Inject
	private static AddressLocalService _addressLocalService;

	@Inject
	private static CountryService _countryService;

	@Inject
	private static ListTypeLocalService _listTypeLocalService;

	@Inject
	private static PhoneLocalService _phoneLocalService;

	@Inject
	private static RegionService _regionService;

}