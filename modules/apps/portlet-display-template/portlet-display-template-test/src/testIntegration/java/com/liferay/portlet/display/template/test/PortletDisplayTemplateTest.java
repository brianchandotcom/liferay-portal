/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.display.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.display.template.BasePortletDisplayTemplateHandler;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
public class PortletDisplayTemplateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPortletDisplayTemplateHandlers() {
		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		try {
			Bundle bundle = FrameworkUtil.getBundle(getClass());

			BundleContext bundleContext = bundle.getBundleContext();

			String className1 = RandomTestUtil.randomString();

			TestTemplateHandler disabledTestTemplateHandler =
				new TestTemplateHandler(className1, false);

			serviceRegistrations.add(
				bundleContext.registerService(
					TemplateHandler.class, disabledTestTemplateHandler,
					MapUtil.singletonDictionary(
						"javax.portlet.name", RandomTestUtil.randomString())));

			Assert.assertFalse(
				_contains(
					className1,
					_portletDisplayTemplate.
						getPortletDisplayTemplateHandlers()));

			String className2 = RandomTestUtil.randomString();

			TestTemplateHandler enabledTestTemplateHandler =
				new TestTemplateHandler(className2, true);

			serviceRegistrations.add(
				bundleContext.registerService(
					TemplateHandler.class, enabledTestTemplateHandler,
					MapUtil.singletonDictionary(
						"javax.portlet.name", RandomTestUtil.randomString())));

			Assert.assertTrue(
				_contains(
					className2,
					_portletDisplayTemplate.
						getPortletDisplayTemplateHandlers()));
		}
		finally {
			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	private boolean _contains(
		String className, List<TemplateHandler> templateHandlers) {

		for (TemplateHandler templateHandler : templateHandlers) {
			if (Objects.equals(templateHandler.getClassName(), className)) {
				return true;
			}
		}

		return false;
	}

	@Inject
	private PortletDisplayTemplate _portletDisplayTemplate;

	private class TestTemplateHandler
		extends BasePortletDisplayTemplateHandler {

		@Override
		public String getClassName() {
			return _className;
		}

		@Override
		public String getName(Locale locale) {
			return StringPool.BLANK;
		}

		@Override
		public String getResourceName() {
			return StringPool.BLANK;
		}

		@Override
		public boolean isEnabled(long companyId) {
			return _enabled;
		}

		private TestTemplateHandler(String className, boolean enabled) {
			_className = className;
			_enabled = enabled;
		}

		private final String _className;
		private final boolean _enabled;

	}

}