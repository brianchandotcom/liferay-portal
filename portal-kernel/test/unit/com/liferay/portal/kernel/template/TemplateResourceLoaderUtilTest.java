/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.Objects;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 * @author Philip Jones
 */
public class TemplateResourceLoaderUtilTest {

	@BeforeClass
	public static void setUpClass() {
		_templateResourceLoader =
			(TemplateResourceLoader)ProxyUtil.newProxyInstance(
				TemplateResourceLoader.class.getClassLoader(),
				new Class<?>[] {TemplateResourceLoader.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getName")) {
						return _TEST_TEMPLATE_RESOURCE_LOADER_NAME;
					}

					if (Objects.equals(
							method.getName(), "getTemplateResource") &&
						_TEST_TEMPLATE_RESOURCE_TEMPLATE_ID.equals(args[0])) {

						return _TEMPLATE_RESOURCE;
					}

					if (Objects.equals(
							method.getName(), "hasTemplateResource")) {

						return _TEST_TEMPLATE_RESOURCE_TEMPLATE_ID.equals(
							args[0]);
					}

					return null;
				});

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			TemplateResourceLoader.class, _templateResourceLoader, null);
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetTemplateResource() throws TemplateException {
		Assert.assertSame(
			_TEMPLATE_RESOURCE,
			TemplateResourceLoaderUtil.getTemplateResource(
				_TEST_TEMPLATE_RESOURCE_LOADER_NAME,
				_TEST_TEMPLATE_RESOURCE_TEMPLATE_ID));
	}

	@Test
	public void testGetTemplateResourceLoader() throws TemplateException {
		Assert.assertSame(
			_templateResourceLoader,
			TemplateResourceLoaderUtil.getTemplateResourceLoader(
				_TEST_TEMPLATE_RESOURCE_LOADER_NAME));
	}

	@Test
	public void testGetTemplateResourceLoaderNames() {
		Set<String> templateResourceLoaderNames =
			TemplateResourceLoaderUtil.getTemplateResourceLoaderNames();

		Assert.assertTrue(
			templateResourceLoaderNames.toString(),
			templateResourceLoaderNames.contains(
				_TEST_TEMPLATE_RESOURCE_LOADER_NAME));
	}

	@Test
	public void testHasTemplateResource() throws TemplateException {
		Assert.assertTrue(
			_TEST_TEMPLATE_RESOURCE_TEMPLATE_ID +
				" not loaded by template resource loader " +
					_TEST_TEMPLATE_RESOURCE_LOADER_NAME,
			TemplateResourceLoaderUtil.hasTemplateResource(
				_TEST_TEMPLATE_RESOURCE_LOADER_NAME,
				_TEST_TEMPLATE_RESOURCE_TEMPLATE_ID));
	}

	@Test
	public void testHasTemplateResourceLoader() {
		Assert.assertTrue(
			_TEST_TEMPLATE_RESOURCE_LOADER_NAME + " not found",
			TemplateResourceLoaderUtil.hasTemplateResourceLoader(
				_TEST_TEMPLATE_RESOURCE_LOADER_NAME));
	}

	private static final TemplateResource _TEMPLATE_RESOURCE =
		ProxyFactory.newDummyInstance(TemplateResource.class);

	private static final String _TEST_TEMPLATE_RESOURCE_LOADER_NAME =
		"TEST_TEMPLATE_RESOURCE_LOADER_NAME";

	private static final String _TEST_TEMPLATE_RESOURCE_TEMPLATE_ID =
		"TEST_TEMPLATE_RESOURCE_TEMPLATE_ID";

	private static ServiceRegistration<TemplateResourceLoader>
		_serviceRegistration;
	private static TemplateResourceLoader _templateResourceLoader;

}