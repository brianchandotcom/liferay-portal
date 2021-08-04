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

package com.liferay.portal.instances.on.demand.admin.internal.helper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.instances.on.demand.admin.test.util.OnDemandAdminTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OnDemandAdminHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			"com.liferay.portal.instances.on.demand.admin.internal.helper." +
				"OnDemandAdminHelper");

		_serviceTracker.open();
	}

	@Test
	public void testCleanUpOnDemandAdminUsers() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		List<User> users = Arrays.asList(
			OnDemandAdminTestUtil.addOnDemandAdminUser(
				company, TestPropsValues.getUserId()),
			OnDemandAdminTestUtil.addOnDemandAdminUser(
				company, TestPropsValues.getUserId()));

		Object onDemandAdminHelper = _serviceTracker.getService();

		for (User user : users) {
			Assert.assertNotNull(_userLocalService.fetchUser(user.getUserId()));
			Assert.assertTrue(
				ReflectionTestUtil.invoke(
					onDemandAdminHelper, "isOnDemandAdminUser",
					new Class<?>[] {User.class}, user));
		}

		ReflectionTestUtil.invoke(
			onDemandAdminHelper, "cleanUpOnDemandAdminUsers",
			new Class<?>[] {Date.class}, new Date(System.currentTimeMillis()));

		for (User user : users) {
			Assert.assertNull(_userLocalService.fetchUser(user.getUserId()));
		}
	}

	private static ServiceTracker<Object, Object> _serviceTracker;

	@Inject
	private UserLocalService _userLocalService;

}