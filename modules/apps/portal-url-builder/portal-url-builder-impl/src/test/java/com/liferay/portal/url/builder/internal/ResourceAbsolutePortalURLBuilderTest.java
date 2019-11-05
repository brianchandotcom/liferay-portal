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
	extends BaseAbsolutePortalURLBuilderTestCase {

	@Parameterized.Parameters(name = "{0}: context={1}, proxy={2}, cdnHost={3}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, false, false}, {1, false, false, true},
				{2, false, true, false}, {3, false, true, true},
				{4, true, false, false}, {5, true, false, true},
				{6, true, true, false}, {7, true, true, true}
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

		Assert.assertEquals(_RESULTS[index], url);
	}

	@Test
	public void testIgnoreCDN() {
		_absolutePortalURLBuilder.ignoreCDNHost();

		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(_RESULTS_IGNORE_CDN[index], url);
	}

	@Test
	public void testIgnoreCDNAndProxy() {
		_absolutePortalURLBuilder.ignoreCDNHost();
		_absolutePortalURLBuilder.ignorePathProxy();

		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(_RESULTS_IGNORE_CDN_AND_PROXY[index], url);
	}

	@Test
	public void testIgnoreProxy() {
		_absolutePortalURLBuilder.ignorePathProxy();

		String url = _resourceAbsolutePortalURLBuilder.build();

		Assert.assertEquals(_RESULTS_IGNORE_PROXY[index], url);
	}

	@Parameterized.Parameter(3)
	public boolean cdnHost;

	@Parameterized.Parameter(1)
	public boolean context;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(2)
	public boolean proxy;

	private static final String[] _RESULTS = {
		"/path/to/resource", "http://cdn-host/path/to/resource",
		"/proxy/path/to/resource", "http://cdn-host/proxy/path/to/resource",
		"/context/path/to/resource", "http://cdn-host/context/path/to/resource",
		"/proxy/context/path/to/resource",
		"http://cdn-host/proxy/context/path/to/resource"
	};

	private static final String[] _RESULTS_IGNORE_CDN = {
		"/path/to/resource", "/path/to/resource", "/proxy/path/to/resource",
		"/proxy/path/to/resource", "/context/path/to/resource",
		"/context/path/to/resource", "/proxy/context/path/to/resource",
		"/proxy/context/path/to/resource"
	};

	private static final String[] _RESULTS_IGNORE_CDN_AND_PROXY = {
		"/path/to/resource", "/path/to/resource", "/path/to/resource",
		"/path/to/resource", "/context/path/to/resource",
		"/context/path/to/resource", "/context/path/to/resource",
		"/context/path/to/resource"
	};

	private static final String[] _RESULTS_IGNORE_PROXY = {
		"/path/to/resource", "http://cdn-host/path/to/resource",
		"/path/to/resource", "http://cdn-host/path/to/resource",
		"/context/path/to/resource", "http://cdn-host/context/path/to/resource",
		"/context/path/to/resource", "http://cdn-host/context/path/to/resource"
	};

	private AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private ResourceAbsolutePortalURLBuilder _resourceAbsolutePortalURLBuilder;

}