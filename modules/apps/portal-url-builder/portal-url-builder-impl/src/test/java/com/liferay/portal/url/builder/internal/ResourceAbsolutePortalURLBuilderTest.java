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

package com.liferay.portal.url.builder.internal;

import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ResourceAbsolutePortalURLBuilder;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class ResourceAbsolutePortalURLBuilderTest
	extends AbsolutePortalURLBuilderTestBase {

	public static String[] results = {
		"/path/to/resource", "http://cdn-host/path/to/resource",
		"/context/path/to/resource", "/proxy/context/path/to/resource",
		"/proxy/path/to/resource"
	};
	public static String[] resultsIgnoringCDN = {
		"/path/to/resource", "/path/to/resource", "/context/path/to/resource",
		"/proxy/context/path/to/resource", "/proxy/path/to/resource"
	};
	public static String[] resultsIgnoringCDNAndProxy = {
		"/path/to/resource", "/path/to/resource", "/context/path/to/resource",
		"/context/path/to/resource", "/path/to/resource"
	};
	public static String[] resultsIgnoringProxy = {
		"/path/to/resource", "http://cdn-host/path/to/resource",
		"/context/path/to/resource", "/context/path/to/resource",
		"/path/to/resource"
	};

	@Parameterized.Parameters(name = "{0}: context={1}, proxy={2}, cdnHost={3}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, false, false}, {1, false, false, true},
				{2, true, false, false}, {3, true, true, false},
				{4, false, true, false}
			});
	}

	@Before
	public void setUp() throws Exception {
		_absolutePortalURLBuilder = new AbsolutePortalURLBuilderImpl(
			mockPortal(context, proxy, cdnHost),
			Mockito.mock(HttpServletRequest.class));

		_resourceAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forResource("path/to/resource");
	}

	@Test
	public void test() {
		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(results[index], url);
	}

	@Test
	public void testIgnoringCDN() {
		_absolutePortalURLBuilder.ignoreCDNHost();

		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(resultsIgnoringCDN[index], url);
	}

	@Test
	public void testIgnoringCDNAndProxy() {
		_absolutePortalURLBuilder.ignoreCDNHost();
		_absolutePortalURLBuilder.ignorePathProxy();

		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(resultsIgnoringCDNAndProxy[index], url);
	}

	@Test
	public void testIgnoringProxy() {
		_absolutePortalURLBuilder.ignorePathProxy();

		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(resultsIgnoringProxy[index], url);
	}

	@Parameterized.Parameter(3)
	public boolean cdnHost;

	@Parameterized.Parameter(1)
	public boolean context;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(2)
	public boolean proxy;

	private AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private ResourceAbsolutePortalURLBuilder _resourceAbsolutePortalURLBuilder;

}