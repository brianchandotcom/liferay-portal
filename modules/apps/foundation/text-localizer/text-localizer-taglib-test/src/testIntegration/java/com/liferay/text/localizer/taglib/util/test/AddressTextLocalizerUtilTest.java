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

package com.liferay.text.localizer.taglib.util.test;

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
import com.liferay.text.localizer.address.util.AddressTextLocalizerUtil;

import java.util.Dictionary;

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
public class AddressTextLocalizerUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_address = _addAddress(_user);
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testFormat() {
		AddressTextLocalizer addressTextLocalizer =
			AddressTextLocalizerUtil.getAddressTextLocalizer(_address);

		Assert.assertEquals(
			addressTextLocalizer.format(_address),
			AddressTextLocalizerUtil.format(_address));
	}

	@Test
	public void testGetAddressTextLocalizerFromAddress() {
		AddressTextLocalizer addressTextLocalizer =
			AddressTextLocalizerUtil.getAddressTextLocalizer(_address);

		Assert.assertTrue(
			addressTextLocalizer instanceof USAddressTextLocalizer);
	}

	@Test
	public void testGetAddressTextLocalizerFromCountryA2() {
		AddressTextLocalizer defaultAddressTextLocalizer =
			AddressTextLocalizerUtil.getAddressTextLocalizer("US");

		Assert.assertTrue(
			defaultAddressTextLocalizer instanceof USAddressTextLocalizer);

		String addressText = RandomTestUtil.randomString();

		AddressTextLocalizer addressTextLocalizer = address -> addressText;

		String countryA2 = RandomTestUtil.randomString();

		_serviceRegistration = _registerAddressTextLocalizer(
			addressTextLocalizer, countryA2);

		AddressTextLocalizer registeredAddressTextLocalizer =
			AddressTextLocalizerUtil.getAddressTextLocalizer(countryA2);

		Assert.assertEquals(
			addressTextLocalizer, registeredAddressTextLocalizer);
		Assert.assertEquals(
			addressText, registeredAddressTextLocalizer.format(_address));
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

		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put("country", countryA2);

		return _bundleContext.registerService(
			AddressTextLocalizer.class.getName(), addressTextLocalizer,
			dictionary);
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AddressTextLocalizerUtilTest.class);

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