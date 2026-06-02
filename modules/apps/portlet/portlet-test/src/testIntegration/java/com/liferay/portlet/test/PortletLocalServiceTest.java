/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.BaseCustomAttributesDisplay;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.Portlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
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

	@After
	public void tearDown() throws Exception {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Test
	public void testFetchPortletById() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		String portletName = RandomTestUtil.randomString();

		_serviceRegistrations.add(
			bundleContext.registerService(
				Portlet.class, new TestPortlet(),
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.portlet.instanceable", "true"
				).put(
					"jakarta.portlet.name", portletName
				).build()));

		_assertFetchPortletById(portletName, null);
		_assertFetchPortletById(portletName, RandomTestUtil.randomString());
	}

	@Test
	public void testGetCustomAttributesDisplaysWithCustomAttributesDisplayDisabled()
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		TestCustomAttributesDisplay disabledFFCustomAttributesDisplay =
			new TestCustomAttributesDisplay(RandomTestUtil.randomString());
		String portletName = RandomTestUtil.randomString();

		_serviceRegistrations.add(
			bundleContext.registerService(
				CustomAttributesDisplay.class,
				disabledFFCustomAttributesDisplay,
				MapUtil.singletonDictionary(
					"jakarta.portlet.name", portletName)));

		String enabledFFKey = RandomTestUtil.randomString();

		PropsUtil.set("feature.flag." + enabledFFKey, "true");

		TestCustomAttributesDisplay enabledFFCustomAttributesDisplay =
			new TestCustomAttributesDisplay(enabledFFKey);

		_serviceRegistrations.add(
			bundleContext.registerService(
				CustomAttributesDisplay.class, enabledFFCustomAttributesDisplay,
				MapUtil.singletonDictionary(
					"jakarta.portlet.name", portletName)));

		TestCustomAttributesDisplay nullFFCustomAttributesDisplay =
			new TestCustomAttributesDisplay(null);

		_serviceRegistrations.add(
			bundleContext.registerService(
				CustomAttributesDisplay.class, nullFFCustomAttributesDisplay,
				MapUtil.singletonDictionary(
					"jakarta.portlet.name", portletName)));

		_serviceRegistrations.add(
			bundleContext.registerService(
				Portlet.class, new TestPortlet(),
				MapUtil.singletonDictionary(
					"jakarta.portlet.name", portletName)));

		Thread.sleep(200);

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

		Assert.assertFalse(
			customAttributesDisplays.contains(
				disabledFFCustomAttributesDisplay));
		Assert.assertTrue(
			customAttributesDisplays.contains(
				enabledFFCustomAttributesDisplay));
		Assert.assertTrue(
			customAttributesDisplays.contains(nullFFCustomAttributesDisplay));
		Assert.assertEquals(
			customAttributesDisplays.toString(), 2,
			customAttributesDisplays.size());
	}

	private void _assertFetchPortletById(String portletName, String instanceId)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String portletId = PortletIdCodec.encode(portletName, instanceId);

		com.liferay.portal.kernel.model.Portlet portlet =
			_portletLocalService.fetchPortletById(companyId, portletId);

		Assert.assertEquals(instanceId, portlet.getInstanceId());
		Assert.assertEquals(portletId, portlet.getPortletId());
		Assert.assertEquals(portletName, portlet.getPortletName());
		Assert.assertTrue(portlet.isInstanceable());
		Assert.assertFalse(portlet.isStatic());
		Assert.assertFalse(portlet.isStaticStart());

		portlet.setStatic(true);
		portlet.setStaticStart(true);

		Assert.assertTrue(portlet.isStatic());
		Assert.assertTrue(portlet.isStaticStart());

		portlet = _portletLocalService.fetchPortletById(companyId, portletId);

		Assert.assertFalse(portlet.isStatic());
		Assert.assertFalse(portlet.isStaticStart());
	}

	@Inject
	private PortletLocalService _portletLocalService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

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