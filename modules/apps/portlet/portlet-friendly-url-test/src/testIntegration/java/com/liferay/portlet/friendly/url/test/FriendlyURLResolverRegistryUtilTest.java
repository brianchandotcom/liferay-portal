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

package com.liferay.portlet.friendly.url.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class FriendlyURLResolverRegistryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			FriendlyURLResolverRegistryUtilTest.class);

		_bundleContext = bundle.getBundleContext();

		_friendlyURLResolver = _createFriendlyURLResolver();

		_serviceRegistration = _bundleContext.registerService(
			FriendlyURLResolver.class, _friendlyURLResolver,
			new HashMapDictionary());
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetFriendlyURLResolver() {
		Collection<FriendlyURLResolver> friendlyURLResolvers =
			FriendlyURLResolverRegistryUtil.
				getFriendlyURLResolversAsCollection();

		Assert.assertFalse(
			friendlyURLResolvers.toString(), friendlyURLResolvers.isEmpty());

		Assert.assertSame(
			_friendlyURLResolver,
			FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(_SEPARATOR));
	}

	@Test
	public void testOverride() {
		Bundle bundle = FrameworkUtil.getBundle(
			FriendlyURLResolverRegistryUtilTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		FriendlyURLResolver friendlyURLResolver = _createFriendlyURLResolver();

		ServiceRegistration<FriendlyURLResolver> serviceRegistration1 =
			bundleContext.registerService(
				FriendlyURLResolver.class, friendlyURLResolver,
				MapUtil.singletonDictionary("service.ranking", 25));

		ServiceRegistration<FriendlyURLResolver> serviceRegistration2 = null;

		try {
			Collection<FriendlyURLResolver> friendlyURLResolvers =
				FriendlyURLResolverRegistryUtil.
					getFriendlyURLResolversAsCollection();

			Assert.assertFalse(
				friendlyURLResolvers.toString(),
				friendlyURLResolvers.isEmpty());

			Assert.assertSame(
				friendlyURLResolver,
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					_SEPARATOR));

			serviceRegistration2 = bundleContext.registerService(
				FriendlyURLResolver.class, _createFriendlyURLResolver(),
				MapUtil.singletonDictionary("service.ranking", 12));

			Assert.assertSame(
				friendlyURLResolver,
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					_SEPARATOR));
		}
		finally {
			serviceRegistration1.unregister();

			if (serviceRegistration2 != null) {
				serviceRegistration2.unregister();
			}

			Assert.assertSame(
				_friendlyURLResolver,
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					_SEPARATOR));
		}
	}

	private static FriendlyURLResolver _createFriendlyURLResolver() {
		return (FriendlyURLResolver)ProxyUtil.newProxyInstance(
			FriendlyURLResolver.class.getClassLoader(),
			new Class<?>[] {FriendlyURLResolver.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "getURLSeparator")) {
					return _SEPARATOR;
				}

				return null;
			});
	}

	private static final String _SEPARATOR = "/-foo-";

	private static BundleContext _bundleContext;
	private static FriendlyURLResolver _friendlyURLResolver;
	private static ServiceRegistration<FriendlyURLResolver>
		_serviceRegistration;

}