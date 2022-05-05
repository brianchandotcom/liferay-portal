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
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ComboRequestAbsolutePortalURLBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class ComboRequestAbsolutePortalURLBuilderTest
	extends BaseAbsolutePortalURLBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
			mockCacheHelper(), mockPortal(context, proxy, cdnHost),
			mockHttpServletRequest());

		Bundle bundle = Mockito.mock(Bundle.class);

		Dictionary<String, String> headers = new Hashtable<>();

		headers.put("Web-ContextPath", "/bundle");

		Mockito.when(
			bundle.getHeaders(StringPool.BLANK)
		).thenReturn(
			headers
		);

		_comboRequestAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forComboRequest();

		_comboRequestAbsolutePortalURLBuilder.addFile("/file.js");
		_comboRequestAbsolutePortalURLBuilder.addFile("/path/to/file2.js");
	}

	@Test
	public void test() {
		Assert.assertEquals(
			_RESULTS[index], _comboRequestAbsolutePortalURLBuilder.build());
	}

	@Test
	public void testTimestamp() {
		_comboRequestAbsolutePortalURLBuilder.setTimestamp(13);

		Assert.assertEquals(
			_RESULTS_TIMESTAMP[index],
			_comboRequestAbsolutePortalURLBuilder.build());
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
		"/combo?minifierType=js&t=0&/path/to/file2.js&/file.js",
		"/combo?minifierType=js&t=0&/path/to/file2.js&/file.js",
		"/context/combo?minifierType=js&t=0&/path/to/file2.js&/file.js",
		"/proxy/context/combo?minifierType=js&t=0&/path/to/file2.js&/file.js",
		"/proxy/combo?minifierType=js&t=0&/path/to/file2.js&/file.js"
	};

	private static final String[] _RESULTS_TIMESTAMP = {
		"/combo?minifierType=js&t=13&/path/to/file2.js&/file.js",
		"/combo?minifierType=js&t=13&/path/to/file2.js&/file.js",
		"/context/combo?minifierType=js&t=13&/path/to/file2.js&/file.js",
		"/proxy/context/combo?minifierType=js&t=13&/path/to/file2.js&/file.js",
		"/proxy/combo?minifierType=js&t=13&/path/to/file2.js&/file.js"
	};

	private AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private ComboRequestAbsolutePortalURLBuilder
		_comboRequestAbsolutePortalURLBuilder;

}