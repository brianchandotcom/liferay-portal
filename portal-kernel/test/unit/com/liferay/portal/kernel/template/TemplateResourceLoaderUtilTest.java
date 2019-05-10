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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Objects;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			TemplateResourceLoader.class, _templateResourceLoader);
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