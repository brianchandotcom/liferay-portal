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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.HttpMethods;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mika Koivisto
 */
@RunWith(PowerMockRunner.class)
public class FilterMappingTest extends PowerMockito {

	@Before
	public void setUp() {
		_dispatchers = new ArrayList<>();

		for (Dispatcher dispatcher : Dispatcher.values()) {
			_dispatchers.add(dispatcher.name());
		}

		_filter = mock(Filter.class);

		_filterConfig = mock(FilterConfig.class);

		when(
			_filterConfig.getInitParameter("url-regex-pattern")
		).thenReturn(
			StringPool.BLANK
		);

		when(
			_filterConfig.getInitParameter("url-regex-ignore-pattern")
		).thenReturn(
			StringPool.BLANK
		);
	}

	@Test
	public void testIsMatchURLPattern() {
		List<String> urlPatterns = new ArrayList<>();

		urlPatterns.add("/c/portal/login");

		FilterMapping filterMapping = new FilterMapping(
			StringPool.BLANK, _filter, _filterConfig, urlPatterns,
			_dispatchers);

		String uri = "/c/portal/login";

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(HttpMethods.GET, uri);

		Assert.assertEquals(
			true,
			filterMapping.isMatch(
				mockHttpServletRequest, Dispatcher.REQUEST, uri));
	}

	private List<String> _dispatchers;
	private Filter _filter;
	private FilterConfig _filterConfig;

}