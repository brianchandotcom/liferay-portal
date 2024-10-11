/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.BaseCustomAttributesDisplay;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.petra.function.transform.TransformUtil;
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
import java.util.Objects;

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
		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		try {
			PropsTestUtil.setProps(
				"feature.flag.LPD-ENABLED", Boolean.TRUE.toString());

			serviceRegistrations.add(
				_bundleContext.registerService(
					Portlet.class, new TestPortlet(),
					MapUtil.singletonDictionary(
						"javax.portlet.name", _TEST_PORTLET_NAME)));

			TestCustomAttributesDisplay
				testCustomAttributesDisplayWithEnabledFeatureFlag =
					new TestCustomAttributesDisplay("LPD-ENABLED");

			serviceRegistrations.add(
				_bundleContext.registerService(
					CustomAttributesDisplay.class,
					testCustomAttributesDisplayWithEnabledFeatureFlag,
					MapUtil.singletonDictionary(
						"javax.portlet.name", _TEST_PORTLET_NAME)));

			TestCustomAttributesDisplay
				testCustomAttributesDisplayWithNullFeatureFlag =
					new TestCustomAttributesDisplay(null);

			serviceRegistrations.add(
				_bundleContext.registerService(
					CustomAttributesDisplay.class,
					testCustomAttributesDisplayWithNullFeatureFlag,
					MapUtil.singletonDictionary(
						"javax.portlet.name", _TEST_PORTLET_NAME)));

			TestCustomAttributesDisplay
				testCustomAttributesDisplayWithDisabledFeatureFlag =
					new TestCustomAttributesDisplay("LPD-DISABLED");

			serviceRegistrations.add(
				_bundleContext.registerService(
					CustomAttributesDisplay.class,
					testCustomAttributesDisplayWithDisabledFeatureFlag,
					MapUtil.singletonDictionary(
						"javax.portlet.name", _TEST_PORTLET_NAME)));

			List<CustomAttributesDisplay> customAttributesDisplays =
				TransformUtil.transform(
					_portletLocalService.getCustomAttributesDisplays(),
					customAttributesDisplay -> {
						if (Objects.equals(
								TestCustomAttributesDisplay.class.getName(),
								customAttributesDisplay.getClassName())) {

							return customAttributesDisplay;
						}

						return null;
					});

			Assert.assertTrue(
				customAttributesDisplays.toString(),
				customAttributesDisplays.contains(
					testCustomAttributesDisplayWithEnabledFeatureFlag));
			Assert.assertTrue(
				customAttributesDisplays.toString(),
				customAttributesDisplays.contains(
					testCustomAttributesDisplayWithNullFeatureFlag));
			Assert.assertFalse(
				customAttributesDisplays.toString(),
				customAttributesDisplays.contains(
					testCustomAttributesDisplayWithDisabledFeatureFlag));
			Assert.assertEquals(
				customAttributesDisplays.toString(), 2,
				customAttributesDisplays.size());
		}
		finally {
			PropsUtil.setProps(_props);

			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	private static final String _TEST_PORTLET_NAME = "TEST_PORTLET_NAME";

	private BundleContext _bundleContext;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private Props _props;

	private class TestCustomAttributesDisplay
		extends BaseCustomAttributesDisplay {

		@Override
		public String getClassName() {
			return TestCustomAttributesDisplay.class.getName();
		}

		@Override
		public String getFeatureFlagKey() {
			return _featureFlagKey;
		}

		private TestCustomAttributesDisplay(String featureFlagKey) {
			_featureFlagKey = featureFlagKey;
		}

		private final String _featureFlagKey;

	}

	private class TestPortlet extends MVCPortlet {
	}

}