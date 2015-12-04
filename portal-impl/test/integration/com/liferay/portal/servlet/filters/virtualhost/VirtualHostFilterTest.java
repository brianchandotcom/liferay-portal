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

package com.liferay.portal.servlet.filters.virtualhost;

import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Zsolt Oláh
 */
public class VirtualHostFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String getPathContext() {
					return _portalPathContext;
				}

				@Override
				public String getPathProxy() {
					return _portalPathProxy;
				}

			}
		);

		_mockFilterChain = new MockFilterChain();
		_mockHttpServletRequest = new MockHttpServletRequest();
		_mockHttpServletResponse = new MockHttpServletResponse();
		_mockFilterConfig = new MockFilterConfig();

		_virtualHostFilter = new VirtualHostFilter();
		_virtualHostFilter.init(_mockFilterConfig);
	}

	@Test
	public void testFilterProxyContext() throws Exception {
		_mockHttpServletRequest.setRequestURI(
			_CONTEXT + _STARTING_WITH_PROXY_FRIENDLY_URL);
		_portalPathContext = _PROXY + _CONTEXT;
		_portalPathProxy = _PROXY;
		String filteredFriendlyUrl = getFilteredFriendlyUrl(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);
		Assert.assertEquals(
			_STARTING_WITH_PROXY_FRIENDLY_URL, filteredFriendlyUrl);
	}

	@Test
	public void testFilterProxyFriendlyUrlBeginsWithProxy() throws Exception {
		_mockHttpServletRequest.setRequestURI(
			_STARTING_WITH_PROXY_FRIENDLY_URL);
		_portalPathContext = _PROXY;
		_portalPathProxy = _PROXY;
		String filteredFriendlyUrl = getFilteredFriendlyUrl(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);
		Assert.assertEquals(
			_STARTING_WITH_PROXY_FRIENDLY_URL, filteredFriendlyUrl);
	}

	@Test
	public void testFilterProxyFriendlyUrlBeginsWithProxyEmptyPathProxy()
		throws Exception {

		_mockHttpServletRequest.setRequestURI(
			_STARTING_WITH_PROXY_FRIENDLY_URL);
		_portalPathContext = _PROXY;
		_portalPathProxy = "";
		String filteredFriendlyUrl = getFilteredFriendlyUrl(
			_mockHttpServletRequest, _mockHttpServletResponse,
			_mockFilterChain);
		Assert.assertEquals(
			_STARTING_WITH_PROXY_FRIENDLY_URL, filteredFriendlyUrl);
	}

	protected String getFilteredFriendlyUrl(
			MockHttpServletRequest request, MockHttpServletResponse response,
			MockFilterChain filterChain)
		throws Exception {

		_virtualHostFilter.processFilter(request, response, filterChain);

		LastPath lastPath = (LastPath)request.getAttribute(WebKeys.LAST_PATH);

		if (lastPath != null) {
			return lastPath.getPath();
		}
		else {
			return "";
		}
	}

	private static final String _CONTEXT = "/pathcontext";

	private static final String _PROXY = "/pathproxy";

	private static final String _STARTING_WITH_PROXY_FRIENDLY_URL =
		_PROXY + "_sitename";

	private MockFilterChain _mockFilterChain;
	private MockFilterConfig _mockFilterConfig;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private String _portalPathContext;
	private String _portalPathProxy;
	private VirtualHostFilter _virtualHostFilter;

}