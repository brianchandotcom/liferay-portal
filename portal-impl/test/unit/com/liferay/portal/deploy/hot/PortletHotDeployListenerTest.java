/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.hot;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 */
public class PortletHotDeployListenerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testResourceBundle() {
		Class<?> clazz = getClass();

		String resourceBundle =
			clazz.getPackageName() + ".dependencies.TestPortlet";

		String path = StringUtil.replace(
			"/WEB-INF/classes/".concat(resourceBundle), CharPool.PERIOD,
			CharPool.SLASH);

		String resourcePath = path.substring(
			0, path.lastIndexOf(StringPool.SLASH));

		ServletContext servletContext = Mockito.mock(ServletContext.class);

		Mockito.when(
			servletContext.getResourcePaths(Mockito.anyString())
		).thenReturn(
			Set.of(
				resourcePath + "/FakePortlet_Fake_Resource_Name.bin",
				resourcePath + "/FakePortlet_zh_CN.properties",
				resourcePath + "/TestPortlet_en_US.properties")
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
			clazz.getName()
		);

		Mockito.when(
			portlet.getPortletApp()
		).thenReturn(
			portletApp
		);

		Mockito.when(
			portlet.getPortletId()
		).thenReturn(
			"TestPortlet"
		);

		Mockito.when(
			portlet.getResourceBundle()
		).thenReturn(
			resourceBundle
		);

		PortletHotDeployListener portletHotDeployListener =
			new PortletHotDeployListener();

		portletHotDeployListener.checkResourceBundles(
			clazz.getClassLoader(), portlet);

		Map<String, ServiceRegistration<ResourceBundleLoader>>
			resourceBundleLoaderServiceRegistrations =
				ReflectionTestUtil.getFieldValue(
					portletHotDeployListener,
					"_resourceBundleLoaderServiceRegistrations");

		Assert.assertNotNull(
			resourceBundleLoaderServiceRegistrations.get("TestPortlet"));
		Assert.assertNull(
			resourceBundleLoaderServiceRegistrations.get("FakePortlet"));

		Map<String, Set<ServiceRegistration<ResourceBundle>>>
			resourceBundleServiceRegistrations =
				ReflectionTestUtil.getFieldValue(
					portletHotDeployListener,
					"_resourceBundleServiceRegistrations");

		Set<ServiceRegistration<ResourceBundle>> serviceRegistrations =
			resourceBundleServiceRegistrations.get(clazz.getName());

		Assert.assertNotNull(serviceRegistrations);
		Assert.assertEquals(
			serviceRegistrations.toString(), 1, serviceRegistrations.size());

		ServiceRegistration<?>[] serviceRegistrationArray =
			serviceRegistrations.toArray(new ServiceRegistration<?>[0]);

		ServiceRegistration<?> serviceRegistration =
			serviceRegistrationArray[0];

		ServiceReference<?> serviceReference =
			serviceRegistration.getReference();

		Assert.assertEquals(
			"en_US", serviceReference.getProperty("language.id"));
	}

}