/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.hot;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.url.URLContainer;
import com.liferay.portal.kernel.util.CustomJspRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.CustomJspRegistryImpl;
import com.liferay.portal.util.PortalImpl;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class CustomJspBagRegistryUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		CustomJspRegistryUtil customJspRegistryUtil =
			new CustomJspRegistryUtil();

		customJspRegistryUtil.setCustomJspRegistry(new CustomJspRegistryImpl());

		_servletContext = Mockito.mock(ServletContext.class);

		Mockito.when(
			_servletContext.getRealPath(Mockito.anyString())
		).thenReturn(
			StringPool.BLANK
		);

		ServletContextPool.put(
			PortalContextLoaderListener.getPortalServletContextName(),
			_servletContext);
	}

	@Test
	public void testGetCustomJspBags() {
		TestCustomJspBag testCustomJspBag = new TestCustomJspBag(false);

		ServiceRegistration<CustomJspBag> serviceRegistration =
			_bundleContext.registerService(
				CustomJspBag.class, testCustomJspBag,
				HashMapDictionaryBuilder.<String, Object>put(
					"context.id", _TEST_CUSTOM_JSP_BAG
				).put(
					"context.name", "Test Custom JSP Bag"
				).build());

		try {
			Assert.assertSame(
				testCustomJspBag, _getCustomJspBag(_TEST_CUSTOM_JSP_BAG));

			Set<String> servletContextNames =
				CustomJspRegistryUtil.getServletContextNames();

			Assert.assertTrue(
				_TEST_CUSTOM_JSP_BAG + " not found in " +
					servletContextNames.toString(),
				servletContextNames.contains(_TEST_CUSTOM_JSP_BAG));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetGlobalCustomJspBags() {
		TestCustomJspBag testCustomJspBag = new TestCustomJspBag(true);

		ServiceRegistration<CustomJspBag> serviceRegistration =
			_bundleContext.registerService(
				CustomJspBag.class, testCustomJspBag,
				HashMapDictionaryBuilder.<String, Object>put(
					"context.id", _TEST_GLOBAL_CUSTOM_JSP_BAG
				).put(
					"context.name", "Test Global Custom JSP Bag"
				).build());

		try {
			Assert.assertSame(
				testCustomJspBag,
				_getCustomJspBag(_TEST_GLOBAL_CUSTOM_JSP_BAG));

			Set<String> servletContextNames =
				CustomJspRegistryUtil.getServletContextNames();

			Assert.assertFalse(
				_TEST_GLOBAL_CUSTOM_JSP_BAG + " should not be found in " +
					servletContextNames.toString(),
				servletContextNames.contains(_TEST_GLOBAL_CUSTOM_JSP_BAG));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private CustomJspBag _getCustomJspBag(String targetContextId) {
		Map<ServiceReference<CustomJspBag>, CustomJspBag> customJspBags =
			CustomJspBagRegistryUtil.getCustomJspBags();

		for (Map.Entry<ServiceReference<CustomJspBag>, CustomJspBag> entry :
				customJspBags.entrySet()) {

			ServiceReference<CustomJspBag> serviceReference = entry.getKey();

			String contextId = GetterUtil.getString(
				serviceReference.getProperty("context.id"));

			if (contextId.equals(targetContextId)) {
				return entry.getValue();
			}
		}

		return null;
	}

	private static final String _TEST_CUSTOM_JSP_BAG = "TEST_CUSTOM_JSP_BAG";

	private static final String _TEST_GLOBAL_CUSTOM_JSP_BAG =
		"TEST_GLOBAL_CUSTOM_JSP_BAG";

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static ServletContext _servletContext;

	private static class TestCustomJspBag implements CustomJspBag {

		@Override
		public String getCustomJspDir() {
			return StringPool.SLASH;
		}

		@Override
		public List<String> getCustomJsps() {
			return _customJsps;
		}

		@Override
		public URLContainer getURLContainer() {
			return new URLContainer() {

				@Override
				public URL getResource(String name) {
					Class<?> clazz = getClass();

					return clazz.getResource("dependencies/bottom-ext.jsp");
				}

				@Override
				public Set<String> getResources(String path) {
					return Collections.singleton(
						"/html/common/themes/bottom-ext.jsp");
				}

			};
		}

		@Override
		public boolean isCustomJspGlobal() {
			return _customJspGlobal;
		}

		private TestCustomJspBag(boolean customJspGlobal) {
			_customJspGlobal = customJspGlobal;
		}

		private final boolean _customJspGlobal;
		private final List<String> _customJsps = new ArrayList<>();

	}

}