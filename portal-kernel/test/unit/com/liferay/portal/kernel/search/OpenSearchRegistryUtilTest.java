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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Dante Wang
 * @author Peter Fellwock
 */
public class OpenSearchRegistryUtilTest {

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		_openSearch = (OpenSearch)ProxyUtil.newProxyInstance(
			OpenSearch.class.getClassLoader(),
			new Class<?>[] {OpenSearch.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "getClassName")) {
					return _CLASS_NAME;
				}

				return null;
			});

		_serviceRegistration = registry.registerService(
			OpenSearch.class, _openSearch);
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetOpenSearch() {
		Assert.assertSame(
			_openSearch, OpenSearchRegistryUtil.getOpenSearch(_CLASS_NAME));
	}

	@Test
	public void testGetOpenSearchInstances() {
		List<OpenSearch> openSearches = new ArrayList<>(
			OpenSearchRegistryUtil.getOpenSearchInstances());

		Assert.assertTrue(
			_CLASS_NAME + " not found in " + openSearches,
			openSearches.removeIf(openSearch -> openSearch == _openSearch));
	}

	private static final String _CLASS_NAME = "TestOpenSearch";

	private static OpenSearch _openSearch;
	private static ServiceRegistration<OpenSearch> _serviceRegistration;

}