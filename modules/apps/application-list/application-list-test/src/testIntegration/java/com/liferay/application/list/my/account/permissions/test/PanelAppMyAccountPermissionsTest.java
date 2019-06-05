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

package com.liferay.application.list.my.account.permissions.test;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.WebAppPool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;

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
public class PanelAppMyAccountPermissionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_testPortletId = "TEST_PORTLET_" + RandomTestUtil.randomString();
	}

	@After
	public void tearDown() throws Exception {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, LayoutConstants.DEFAULT_PLID,
			_testPortletId);
	}

	@Test
	public void testPermissionsAddedForAllCompaniesFromNewPanelApp()
		throws Exception {

		_testCompany = addCompany();

		_registerTestPortlet(_testPortletId);

		long defaultCompanyId = TestPropsValues.getCompanyId();

		Assert.assertFalse(
			_hasMyAccountPermission(defaultCompanyId, _testPortletId));

		long testCompanyId = _testCompany.getCompanyId();

		Assert.assertFalse(
			_hasMyAccountPermission(testCompanyId, _testPortletId));

		_registerTestPanelApp(_testPortletId);

		Assert.assertTrue(
			_hasMyAccountPermission(defaultCompanyId, _testPortletId));
		Assert.assertTrue(
			_hasMyAccountPermission(testCompanyId, _testPortletId));
	}

	@Test
	public void testPermissionsAddedForPanelAppFromNewCompany()
		throws Exception {

		_registerTestPortlet(_testPortletId);

		_registerTestPanelApp(_testPortletId);

		_testCompany = addCompany();

		Assert.assertTrue(
			_hasMyAccountPermission(
				_testCompany.getCompanyId(), _testPortletId));
	}

	protected Company addCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		WebAppPool.put(
			company.getCompanyId(), WebKeys.PORTLET_CATEGORY,
			new PortletCategory());

		return company;
	}

	private boolean _hasMyAccountPermission(long companyId, String portletId)
		throws Exception {

		Role userRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.USER);

		return ResourcePermissionLocalServiceUtil.hasResourcePermission(
			companyId, portletId, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId), userRole.getRoleId(),
			ActionKeys.ACCESS_IN_CONTROL_PANEL);
	}

	private void _registerTestPanelApp(String portletId) {
		_serviceRegistrations.add(
			_bundleContext.registerService(
				PanelApp.class, new TestPanelApp(portletId),
				new HashMapDictionary<String, String>() {
					{
						put(
							"panel.category.key",
							PanelCategoryKeys.USER_MY_ACCOUNT);
					}
				}));
	}

	private void _registerTestPortlet(final String portletId) throws Exception {
		_serviceRegistrations.add(
			_bundleContext.registerService(
				Portlet.class, new TestPortlet(),
				new HashMapDictionary<String, String>() {
					{
						put("javax.portlet.name", portletId);
					}
				}));
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PanelAppMyAccountPermissionsTest.class);

		if (bundle == null) {
			_bundleContext = null;
		}
		else {
			_bundleContext = bundle.getBundleContext();
		}
	}

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();

	@DeleteAfterTestRun
	private Company _testCompany;

	private String _testPortletId;

	private class TestPanelApp extends BasePanelApp {

		public TestPanelApp(String portletId) {
			_portletId = portletId;
		}

		public String getPortletId() {
			return _portletId;
		}

		private final String _portletId;

	}

	private class TestPortlet extends GenericPortlet {
	}

}