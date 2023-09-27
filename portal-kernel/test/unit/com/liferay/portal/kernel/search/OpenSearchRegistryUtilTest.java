/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 * @author Peter Fellwock
 */
public class OpenSearchRegistryUtilTest {

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_openSearch = (OpenSearch)ProxyUtil.newProxyInstance(
			OpenSearch.class.getClassLoader(),
			new Class<?>[] {OpenSearch.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "getClassName")) {
					return _CLASS_NAME;
				}

				return null;
			});

		_serviceRegistration = bundleContext.registerService(
			OpenSearch.class, _openSearch, null);
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