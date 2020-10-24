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
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AddressLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAddressWithPhoneNumber() throws Exception {
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
	public void testUpdateAddressWithPhoneNumber() throws Exception {
		Address address = _addAddress("123456789");

		Address updatedAddress = _addressLocalService.updateAddress(
			address.getAddressId(), address.getName(), address.getDescription(),
			address.getStreet1(), address.getStreet2(), address.getStreet3(),
			address.getCity(), address.getZip(), address.getRegionId(),
			address.getCountryId(), "1112223333", address.getTypeId(),
			address.isMailing(), address.isPrimary());

		List<Phone> phones = _phoneLocalService.getPhones(
			address.getCompanyId(), Address.class.getName(),
			address.getAddressId());

		Assert.assertEquals(phones.toString(), 1, phones.size());

		Phone phone = phones.get(0);

		Assert.assertEquals(updatedAddress.getPhoneNumber(), phone.getNumber());
	}

	private Address _addAddress(String phoneNumber) throws Exception {
		User user = TestPropsValues.getUser();

		ListType listType = _listTypeLocalService.getListType(
			"personal", ListTypeConstants.CONTACT_ADDRESS);

		return _addressLocalService.addAddress(
			user.getUserId(), Contact.class.getName(), user.getContactId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString(), null, 0, 0, phoneNumber,
			listType.getListTypeId(), false, false,
			ServiceContextTestUtil.getServiceContext());
	}

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