/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.BaseCustomAttributesDisplay;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;

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
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class PortletLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testGetCustomAttributesDisplaysWithCustomAttributesDisplayDisabled() {
		Props props = null;

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		try {
			serviceRegistrations.add(
				_bundleContext.registerService(
					Portlet.class, new TestPortlet(),
					MapUtil.singletonDictionary(
						"javax.portlet.name",
						"com_liferay_portal_kernel_service_TestPortlet")));

			TestCustomAttributesDisplay enabledTestCustomAttributesDisplay =
				new TestCustomAttributesDisplay("LPD-XXXXX");

			serviceRegistrations.add(
				_bundleContext.registerService(
					CustomAttributesDisplay.class,
					enabledTestCustomAttributesDisplay,
					MapUtil.singletonDictionary(
						"javax.portlet.name",
						"com_liferay_portal_kernel_service_TestPortlet")));

			TestCustomAttributesDisplay disabledTestCustomAttributesDisplay =
				new TestCustomAttributesDisplay(null);

			serviceRegistrations.add(
				_bundleContext.registerService(
					CustomAttributesDisplay.class,
					new TestCustomAttributesDisplay(null),
					MapUtil.singletonDictionary(
						"javax.portlet.name",
						"com_liferay_portal_kernel_service_TestPortlet")));

			props = PropsTestUtil.setProps(
				"feature.flag.LPD-XXXXX", Boolean.TRUE.toString());

			List<CustomAttributesDisplay> customAttributesDisplays =
				_portletLocalService.getCustomAttributesDisplays();

			Assert.assertTrue(
				customAttributesDisplays.toString(),
				customAttributesDisplays.contains(
					enabledTestCustomAttributesDisplay));
			Assert.assertFalse(
				customAttributesDisplays.toString(),
				customAttributesDisplays.contains(
					disabledTestCustomAttributesDisplay));
		}
		finally {
			props = null;

			PropsUtil.setProps(_props);

			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	private BundleContext _bundleContext;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private Props _props;

	private class TestCustomAttributesDisplay
		extends BaseCustomAttributesDisplay {

		public TestCustomAttributesDisplay(String featureFlagKey) {
			_featureFlagKey = featureFlagKey;
		}

		@Override
		public String getClassName() {
			return "test";
		}

		@Override
		public String getFeatureFlagKey() {
			return _featureFlagKey;
		}

		private final String _featureFlagKey;

	}

	private class TestPortlet extends MVCPortlet {
	}

}