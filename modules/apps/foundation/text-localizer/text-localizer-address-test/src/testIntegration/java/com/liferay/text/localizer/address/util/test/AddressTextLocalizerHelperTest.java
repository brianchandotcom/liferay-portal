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

package com.liferay.text.localizer.address.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.text.localizer.address.AddressTextLocalizer;
import com.liferay.text.localizer.address.USAddressTextLocalizer;
import com.liferay.text.localizer.address.util.AddressTextLocalizerHelper;

import java.util.Dictionary;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AddressTextLocalizerHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_address = _addAddress(_user);

		_serviceRegistration = null;
	}

	@After
	public void tearDown() {
		Optional.ofNullable(_serviceRegistration).ifPresent(
			ServiceRegistration::unregister);
	}

	@Test
	public void testFormatWithTheCorrectLocalizer() {
		AddressTextLocalizer localizer =
			AddressTextLocalizerHelper.getAddressTextLocalizer(_address);

		Assert.assertEquals(
			localizer.format(_address),
			AddressTextLocalizerHelper.format(_address));
	}

	@Test
	public void testGetAddressTextLocalizerFromAddress() {
		Assert.assertTrue(
			AddressTextLocalizerHelper.getAddressTextLocalizer(
				_address) instanceof USAddressTextLocalizer);
	}

	@Test
	public void testGetAddressTextLocalizerFromCountryA2String() {
		Assert.assertTrue(
			AddressTextLocalizerHelper.getAddressTextLocalizer(
				"US") instanceof USAddressTextLocalizer);

		String testCountryA2 = RandomTestUtil.randomString();
		String testFormattedString = RandomTestUtil.randomString();

		AddressTextLocalizer testAddressTextLocalizer =
			address -> testFormattedString;

		_serviceRegistration = _registerAddressTextLocalizer(
			testAddressTextLocalizer, testCountryA2);

		AddressTextLocalizer retrievedAddressTextLocalizer =
			AddressTextLocalizerHelper.getAddressTextLocalizer(testCountryA2);

		Assert.assertEquals(
			testAddressTextLocalizer, retrievedAddressTextLocalizer);

		Assert.assertEquals(
			testFormattedString,
			retrievedAddressTextLocalizer.format(_address));
	}

	private Address _addAddress(User user) throws Exception {
		return AddressLocalServiceUtil.addAddress(
			user.getUserId(), user.getModelClassName(), user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(), false,
			true, new ServiceContext());
	}

	private ServiceRegistration<?> _registerAddressTextLocalizer(
		AddressTextLocalizer addressTextLocalizer, String countryA2) {

		Dictionary<String, String> properties = new HashMapDictionary<>();

		properties.put("country", countryA2);

		return _bundleContext.registerService(
			AddressTextLocalizer.class.getName(), addressTextLocalizer,
			properties);
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AddressTextLocalizerHelperTest.class);

		if (bundle == null) {
			_bundleContext = null;
		}
		else {
			_bundleContext = bundle.getBundleContext();
		}
	}

	@DeleteAfterTestRun
	private Address _address;

	private ServiceRegistration _serviceRegistration;

	@DeleteAfterTestRun
	private User _user;

}