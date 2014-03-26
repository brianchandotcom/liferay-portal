/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.spring.extender.classloader;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Enumeration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class BundleResolverClassLoaderTest extends PowerMockito {

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyClassLoaders() {
		BundleResolverClassLoader bundleResolverClassLoader =
			new BundleResolverClassLoader();

		Assert.fail("This code should not be executed");
	}

	@Test
	public void testGetResourceDefinedInFirstClassLoader()
		throws ClassNotFoundException, MalformedURLException {

		ClassLoader bundleResolverClassLoader =
			_buildBundleResolverClassLoader();

		_mockGetResource(_bundle1, _RESOURCE, _url);

		bundleResolverClassLoader.getResource(_RESOURCE);

		_verifyGetResource(_bundle1, _RESOURCE);
	}

	@Test
	public void testGetResourceDefinedInSecondClassLoader()
		throws ClassNotFoundException, MalformedURLException {

		ClassLoader bundleResolverClassLoader =
			_buildBundleResolverClassLoader();

		_mockGetResource(_bundle1, _RESOURCE, null);
		_mockGetResource(_bundle2, _RESOURCE, _url);

		bundleResolverClassLoader.getResource(_RESOURCE);

		_verifyGetResource(_bundle1, _RESOURCE);
		_verifyGetResource(_bundle2, _RESOURCE);
	}

	@Test
	public void testGetResourcesDefinedInFirstClassLoader()
		throws ClassNotFoundException, IOException {

		ClassLoader bundleResolverClassLoader =
			_buildBundleResolverClassLoader();

		_mockGetResources(_bundle1, _RESOURCE, _enumeration);

		bundleResolverClassLoader.getResources(_RESOURCE);

		_verifyGetResources(_bundle1, _RESOURCE);
	}

	@Test
	public void testGetResourcesDefinedInSecondClassLoader()
		throws ClassNotFoundException, IOException {

		ClassLoader bundleResolverClassLoader =
			_buildBundleResolverClassLoader();

		_mockGetResources(_bundle1, _RESOURCE, null);
		_mockGetResources(_bundle2, _RESOURCE, _enumeration);

		bundleResolverClassLoader.getResources(_RESOURCE);

		_verifyGetResources(_bundle1, _RESOURCE);
		_verifyGetResources(_bundle2, _RESOURCE);
	}

	private ClassLoader _buildBundleResolverClassLoader() {
		return new BundleResolverClassLoader(_bundle1, _bundle2);
	}

	private void _mockGetResource(Bundle bundle, String resource, URL url)
		throws ClassNotFoundException, MalformedURLException {

		when(
			bundle.getResource(resource)
		).thenReturn(
			url
		);
	}

	private void _mockGetResources(
			Bundle bundle, String resource, Enumeration enumeration)
		throws ClassNotFoundException, IOException {

		when(
			bundle.getResources(resource)
		).thenReturn(
			enumeration
		);
	}

	private void _verifyGetResource(Bundle mockBundle, String resource) {
		Bundle bundle = Mockito.verify(mockBundle);

		bundle.getResource(resource);
	}

	private void _verifyGetResources(Bundle mockBundle, String resource)
		throws IOException {

		Bundle bundle = Mockito.verify(mockBundle);

		bundle.getResources(resource);
	}

	private static final String _RESOURCE = "resource";

	@Mock
	private Bundle _bundle1;

	@Mock
	private Bundle _bundle2;
	@Mock
	Enumeration _enumeration;
	@Mock
	URL _url;

}