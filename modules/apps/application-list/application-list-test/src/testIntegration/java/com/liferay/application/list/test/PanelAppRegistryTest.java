/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.test;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class PanelAppRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(PanelAppRegistryTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() throws Exception {
		_panelApp1 = _registerPanelApp(CompanyConstants.SYSTEM, null);
		_panelApp2 = _registerPanelApp(CompanyConstants.SYSTEM, "LPD-TEST");
		_panelApp3 = _registerPanelApp(TestPropsValues.getCompanyId(), null);
		_panelApp4 = _registerPanelApp(
			TestPropsValues.getCompanyId(), "LPD-TEST");

		String randomFeatureFlag = StringUtil.toUpperCase(
			RandomTestUtil.randomString());

		_registerPanelApp(TestPropsValues.getCompanyId(), randomFeatureFlag);
		_registerPanelApp(CompanyConstants.SYSTEM, randomFeatureFlag);

		long randomCompanyId = RandomTestUtil.randomLong();

		_registerPanelApp(randomCompanyId, null);
		_registerPanelApp(randomCompanyId, "LPD-TEST");
		_registerPanelApp(randomCompanyId, randomFeatureFlag);
	}

	@After
	public void tearDown() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	@Test
	public void testGetPanelApps() {
		AssertUtils.assertEquals(
			Arrays.asList(_panelApp1, _panelApp3),
			_panelAppRegistry.getPanelApps(_PARENT_PANEL_CATEGORY_KEY));
	}

	@FeatureFlags("LPD-TEST")
	@Test
	public void testGetPanelAppsWithFeatureFlag() throws Exception {
		AssertUtils.assertEquals(
			Arrays.asList(_panelApp1, _panelApp2, _panelApp3, _panelApp4),
			_panelAppRegistry.getPanelApps(_PARENT_PANEL_CATEGORY_KEY));
	}

	private PanelApp _registerPanelApp(long companyId, String featureFlagKey) {
		PanelApp panelApp = new PanelAppImpl(companyId);

		_serviceRegistrations.add(
			_bundleContext.registerService(
				PanelApp.class, panelApp,
				HashMapDictionaryBuilder.put(
					"featureFlagKey", () -> featureFlagKey
				).put(
					"panel.category.key", _PARENT_PANEL_CATEGORY_KEY
				).build()));

		return panelApp;
	}

	private static final String _PARENT_PANEL_CATEGORY_KEY =
		RandomTestUtil.randomString();

	private static BundleContext _bundleContext;

	private PanelApp _panelApp1;
	private PanelApp _panelApp2;
	private PanelApp _panelApp3;
	private PanelApp _panelApp4;

	@Inject
	private PanelAppRegistry _panelAppRegistry;

	private final List<ServiceRegistration<PanelApp>> _serviceRegistrations =
		new ArrayList<>();

	private class PanelAppImpl implements PanelApp {

		public PanelAppImpl(long companyId) {
			_companyId = companyId;

			_portletId = RandomTestUtil.randomString();
		}

		@Override
		public String getKey() {
			return RandomTestUtil.randomString();
		}

		@Override
		public String getLabel(Locale locale) {
			return RandomTestUtil.randomString();
		}

		@Override
		public int getNotificationsCount(User user) {
			return 0;
		}

		@Override
		public Portlet getPortlet() {
			return new PortletImpl(_companyId, _portletId);
		}

		@Override
		public String getPortletId() {
			return _portletId;
		}

		@Override
		public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
			throws PortalException {

			return null;
		}

		@Override
		public boolean include(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException {

			return true;
		}

		@Override
		public boolean isShow(PermissionChecker permissionChecker, Group group)
			throws PortalException {

			return true;
		}

		@Override
		public void setGroupProvider(GroupProvider groupProvider) {
		}

		private final long _companyId;
		private final String _portletId;

	}

}