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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ModuleAbsolutePortalURLBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class ModuleAbsolutePortalURLBuilderTest
	extends AbsolutePortalURLBuilderTestBase {

	public static String[] results = {
		"/o/bundle/path/to/resource",
		"http://cdn-host/o/bundle/path/to/resource",
		"/context/o/bundle/path/to/resource",
		"/proxy/context/o/bundle/path/to/resource",
		"/proxy/o/bundle/path/to/resource"
	};
	public static String[] resultsIgnoringCDN = {
		"/o/bundle/path/to/resource", "/o/bundle/path/to/resource",
		"/context/o/bundle/path/to/resource",
		"/proxy/context/o/bundle/path/to/resource",
		"/proxy/o/bundle/path/to/resource"
	};
	public static String[] resultsIgnoringCDNAndProxy = {
		"/o/bundle/path/to/resource", "/o/bundle/path/to/resource",
		"/context/o/bundle/path/to/resource",
		"/context/o/bundle/path/to/resource", "/o/bundle/path/to/resource"
	};
	public static String[] resultsIgnoringProxy = {
		"/o/bundle/path/to/resource",
		"http://cdn-host/o/bundle/path/to/resource",
		"/context/o/bundle/path/to/resource",
		"/context/o/bundle/path/to/resource", "/o/bundle/path/to/resource"
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

		Bundle bundle = Mockito.mock(Bundle.class);

		Dictionary<String, String> headers = new Hashtable<>();

		headers.put("Web-ContextPath", "/bundle");

		Mockito.when(
			bundle.getHeaders(StringPool.BLANK)
		).thenReturn(
			headers
		);

		_moduleAbsolutePortalURLBuilder = _absolutePortalURLBuilder.forModule(
			bundle, "path/to/resource");
	}

	@Test
	public void test() {
		String url = _moduleAbsolutePortalURLBuilder.build();

		Assert.assertEquals(results[index], url);
	}

	@Test
	public void testIgnoringCDN() {
		_absolutePortalURLBuilder.ignoreCDNHost();

		String url = _moduleAbsolutePortalURLBuilder.build();

		Assert.assertEquals(resultsIgnoringCDN[index], url);
	}

	@Test
	public void testIgnoringCDNAndProxy() {
		_absolutePortalURLBuilder.ignoreCDNHost();
		_absolutePortalURLBuilder.ignorePathProxy();

		String url = _moduleAbsolutePortalURLBuilder.build();

		Assert.assertEquals(resultsIgnoringCDNAndProxy[index], url);
	}

	@Test
	public void testIgnoringProxy() {
		_absolutePortalURLBuilder.ignorePathProxy();

		String url = _moduleAbsolutePortalURLBuilder.build();

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
	private ModuleAbsolutePortalURLBuilder _moduleAbsolutePortalURLBuilder;

}