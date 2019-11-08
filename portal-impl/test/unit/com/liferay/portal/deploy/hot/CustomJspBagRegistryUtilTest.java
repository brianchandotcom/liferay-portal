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

package com.liferay.portal.deploy.hot;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.url.URLContainer;
import com.liferay.portal.kernel.util.CustomJspRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.util.CustomJspRegistryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class CustomJspBagRegistryUtilTest {

	@BeforeClass
	public static void setUpClass() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		CustomJspRegistryUtil customJspRegistryUtil =
			new CustomJspRegistryUtil();

		customJspRegistryUtil.setCustomJspRegistry(new CustomJspRegistryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testGetCustomJspBags() {
		TestCustomJspBag testCustomJspBag = new TestCustomJspBag(false);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<CustomJspBag> serviceRegistration =
			registry.registerService(
				CustomJspBag.class, testCustomJspBag,
				HashMapBuilder.<String, Object>put(
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

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<CustomJspBag> serviceRegistration =
			registry.registerService(
				CustomJspBag.class, testCustomJspBag,
				HashMapBuilder.<String, Object>put(
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