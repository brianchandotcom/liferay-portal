/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

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

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_LOG_NAME, LoggerTestUtil.DEBUG)) {

			String typeName = RandomTestUtil.randomString();

			_assertSearchAddress(
				Collections.emptyList(), null,
				_getLinkedHashMap("typeNames", new String[] {typeName}));
			_assertSearchAddress(
				Arrays.asList(address), null,
				_getLinkedHashMap(
					"typeNames",
					new String[] {businessType.getName(), typeName}));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"No list type found for ",
					ListTypeConstants.CONTACT_ADDRESS, " with the name ",
					typeName),
				logEntry.getMessage());
		}
	}

	@Test
	public void testSearchAddressesWithKeywords() throws Exception {
		Address address = _addAddress("1234567890");

		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String street1 = RandomTestUtil.randomString();
		String city = RandomTestUtil.randomString();
		String zip = RandomTestUtil.randomString();

		Country country = _countryLocalService.fetchCountryByA2(
			TestPropsValues.getCompanyId(), "US");

		Region region = _regionLocalService.addRegion(
			country.getCountryId(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString(), RandomTestUtil.randomDouble(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		_addressLocalService.updateAddress(
			address.getAddressId(), name, description, street1, null, null,
			city, zip, region.getRegionId(), country.getCountryId(),
			address.getListTypeId(), address.isMailing(), address.isPrimary(),
			address.getPhoneNumber());

		List<Address> expectedAddresses = Arrays.asList(address);

		_assertSearchAddress(expectedAddresses, name, null);
		_assertSearchAddress(expectedAddresses, description, null);
		_assertSearchAddress(expectedAddresses, street1, null);
		_assertSearchAddress(expectedAddresses, city, null);
		_assertSearchAddress(expectedAddresses, zip, null);
		_assertSearchAddress(expectedAddresses, region.getName(), null);
		_assertSearchAddress(expectedAddresses, country.getName(), null);
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
				"listTypeIds", new long[] {businessType.getListTypeId()}));
		_assertSearchAddress(
			Arrays.asList(businessAddress, personalAddress), null,
			_getLinkedHashMap(
				"listTypeIds",
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
		Address address = _addAddress(RandomTestUtil.randomString());

		String phoneNumber = RandomTestUtil.randomString();

		Address updatedAddress = _addressLocalService.updateAddress(
			address.getAddressId(), address.getName(), address.getDescription(),
			address.getStreet1(), address.getStreet2(), address.getStreet3(),
			address.getCity(), address.getZip(), address.getRegionId(),
			address.getCountryId(), address.getListTypeId(),
			address.isMailing(), address.isPrimary(), phoneNumber);

		List<Phone> phones = _phoneLocalService.getPhones(
			address.getCompanyId(), Address.class.getName(),
			address.getAddressId());

		Assert.assertEquals(phones.toString(), 1, phones.size());

		Assert.assertEquals(updatedAddress.getPhoneNumber(), phoneNumber);
	}

	private Address _addAddress(String phoneNumber) throws Exception {
		return _addAddress(RandomTestUtil.randomString(), -1, phoneNumber);
	}

	private Address _addAddress(
			String name, long listTypeId, String phoneNumber)
		throws Exception {

		User user = TestPropsValues.getUser();

		if (listTypeId < 0) {
			ListType listType = _listTypeLocalService.getListType(
				"personal", ListTypeConstants.CONTACT_ADDRESS);

			listTypeId = listType.getListTypeId();
		}

		return _addressLocalService.addAddress(
			null, user.getUserId(), Contact.class.getName(),
			user.getContactId(), name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString(), null, 0, 0, listTypeId, false, false,
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
	private static CountryLocalService _countryLocalService;

	@Inject
	private static ListTypeLocalService _listTypeLocalService;

	@Inject
	private static PhoneLocalService _phoneLocalService;

	@Inject
	private static RegionLocalService _regionLocalService;

}