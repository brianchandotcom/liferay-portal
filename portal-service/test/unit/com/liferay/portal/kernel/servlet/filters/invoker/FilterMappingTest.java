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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.StringPool;

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
		_dispatchers = new ArrayList<String>();

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
	public void testIsMatchWithJSessionId() {
		List<String> urlPatterns = new ArrayList<String>();

		urlPatterns.add("/c/portal/login");

		FilterMapping filterMapping = new FilterMapping(
			_filter, _filterConfig, urlPatterns, _dispatchers);

		String uri = "/c/portal/login;jsessionid=ACD311312312323BF.worker1";

		MockHttpServletRequest request = new MockHttpServletRequest(
			HttpMethods.GET, uri);

		boolean isMatch = filterMapping.isMatch(
			request, Dispatcher.REQUEST, uri);

		Assert.assertEquals(true, isMatch);
	}


	@Test
	public void testIsMatchWithoutJSessionId() {
		List<String> urlPatterns = new ArrayList<String>();

		urlPatterns.add("/c/portal/login");

		FilterMapping filterMapping = new FilterMapping(
			_filter, _filterConfig, urlPatterns, _dispatchers);

		String uri = "/c/portal/login";

		MockHttpServletRequest request = new MockHttpServletRequest(
			HttpMethods.GET, uri);

		boolean isMatch = filterMapping.isMatch(
			request, Dispatcher.REQUEST, uri);

		Assert.assertEquals(true, isMatch);
	}

	private List<String> _dispatchers;
	private Filter _filter;
	private FilterConfig _filterConfig;

}
