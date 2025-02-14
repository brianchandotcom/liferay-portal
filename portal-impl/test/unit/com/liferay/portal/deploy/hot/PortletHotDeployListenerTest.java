/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.hot;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 */
public class PortletHotDeployListenerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() {
		ServletContext servletContext = Mockito.mock(ServletContext.class);

		Mockito.when(
			servletContext.getResourcePaths(Mockito.anyString())
		).thenReturn(
			Set.of(
				_RESOURCE_PATH + "FakePortlet_Fake_Resource_Name.bin",
				_RESOURCE_PATH + "FakePortlet_en_US.properties",
				_RESOURCE_PATH + "TestPortlet_en_US.properties")
		);

		PortletApp portletApp = Mockito.mock(PortletApp.class);

		Mockito.when(
			portletApp.getServletContext()
		).thenReturn(
			servletContext
		);

		Portlet portlet = Mockito.mock(Portlet.class);

		Mockito.when(
			portlet.getContextName()
		).thenReturn(
			_CONTEXT_NAME
		);

		Mockito.when(
			portlet.getPortletApp()
		).thenReturn(
			portletApp
		);

		Mockito.when(
			portlet.getPortletId()
		).thenReturn(
			_PORTLET_ID
		);

		Mockito.when(
			portlet.getResourceBundle()
		).thenReturn(
			_RESOURCE_BUNDLE
		);

		PortletHotDeployListener portletHotDeployListener =
			new PortletHotDeployListener();

		Class<?> clazz = getClass();

		portletHotDeployListener.checkResourceBundles(
			clazz.getClassLoader(), portlet);

		Map<String, ServiceRegistration<ResourceBundleLoader>>
			resourceBundleLoaderServiceRegistrations =
				ReflectionTestUtil.getFieldValue(
					portletHotDeployListener,
					"_resourceBundleLoaderServiceRegistrations");

		Assert.assertNotNull(
			resourceBundleLoaderServiceRegistrations.get(_PORTLET_ID));

		Map<String, Set<ServiceRegistration<ResourceBundle>>>
			resourceBundleServiceRegistrations =
				ReflectionTestUtil.getFieldValue(
					portletHotDeployListener,
					"_resourceBundleServiceRegistrations");

		Set<ServiceRegistration<ResourceBundle>> serviceRegistrations =
			resourceBundleServiceRegistrations.get(_CONTEXT_NAME);

		Assert.assertNotNull(serviceRegistrations);
		Assert.assertEquals(
			serviceRegistrations.toString(), 1, serviceRegistrations.size());
	}

	private static final String _CONTEXT_NAME =
		PortletHotDeployListenerTest.class.getName();

	private static final String _PORTLET_ID = "TestPortlet";

	private static final String _RESOURCE_BUNDLE =
		PortletHotDeployListenerTest.class.getPackageName() + ".dependencies." +
			_PORTLET_ID;

	private static final String _RESOURCE_PATH =
		"/WEB-INF/classes/" +
			StringUtil.replace(
				PortletHotDeployListenerTest.class.getPackageName(),
				CharPool.PERIOD, CharPool.SLASH) + "/dependencies/";

}