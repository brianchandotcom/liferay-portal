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

package com.liferay.portal.instances.on.demand.admin.internal.generator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OnDemandAdminTokenGeneratorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			OnDemandAdminTokenGeneratorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Bundle onDemandAdminBundle = null;

		for (Bundle installedBundle : bundleContext.getBundles()) {
			if (Objects.equals(
					installedBundle.getSymbolicName(),
					"com.liferay.portal.instances.on.demand.admin")) {

				onDemandAdminBundle = installedBundle;

				break;
			}
		}

		if (onDemandAdminBundle == null) {
			throw new IllegalStateException(
				"com.liferay.portal.instances.on.demand.admin bundle not " +
					"found");
		}

		Class<?> clazz = onDemandAdminBundle.loadClass(
			"com.liferay.portal.instances.on.demand.admin.internal.constants." +
				"OnDemandAdminConstants");

		_screenNamePrefix = ReflectionTestUtil.getFieldValue(
			clazz, "ON_DEMAND_ADMIN_SCREEN_NAME_PREFIX");
		_ticketType = ReflectionTestUtil.getFieldValue(
			clazz, "TICKET_TYPE_ON_DEMAND_ADMIN_LOGIN");

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			"com.liferay.portal.instances.on.demand.admin.internal.generator." +
				"OnDemandAdminTokenGenerator");

		_serviceTracker.open();
	}

	@Test
	public void testGenerate() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		Object onDemandAdminTokenGenerator = _serviceTracker.getService();

		Ticket ticket = ReflectionTestUtil.invoke(
			onDemandAdminTokenGenerator, "generate",
			new Class<?>[] {Company.class, long.class}, company,
			TestPropsValues.getUserId());

		Assert.assertEquals(_ticketType, ticket.getType());

		User user = _userLocalService.getUser(ticket.getClassPK());

		Assert.assertEquals(company.getCompanyId(), user.getCompanyId());
		Assert.assertTrue(
			StringUtil.startsWith(user.getScreenName(), _screenNamePrefix));
		Assert.assertTrue(
			_userLocalService.hasRoleUser(
				company.getCompanyId(), RoleConstants.ADMINISTRATOR,
				user.getUserId(), false));
	}

	private static String _screenNamePrefix;
	private static ServiceTracker<Object, Object> _serviceTracker;
	private static int _ticketType;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserLocalService _userLocalService;

}