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

package com.liferay.portal.servlet.filters.virtualhost.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.virtualhost.VirtualHostFilter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Zsolt Oláh
 */
@RunWith(Arquillian.class)
public class VirtualHostFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws PortalException {
		_layoutSet = _layoutSetLocalService.getLayoutSet(
			TestPropsValues.getGroupId(), false);
	}

	@Before
	public void setUp() {
		_mockHttpServletRequest.setAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET, _layoutSet);

		_portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String getPathContext() {
					return GetterUtil.getString(_pathContext);
				}

				@Override
				public String getPathProxy() {
					return GetterUtil.getString(_pathProxy);
				}

			});
	}

	@After
	public void tearDown() {
		_portalUtil.setPortal(_portal);
	}

	@Test
	public void testProcessFilter1() {
		_pathContext = _PATH_PROXY + _PATH_CONTEXT;
		_pathProxy = _PATH_PROXY;

		_mockHttpServletRequest.setRequestURI(_PATH_CONTEXT + _LAST_PATH);

		Assert.assertEquals(
			_LAST_PATH,
			_getLastPath(
				_mockHttpServletRequest, _mockHttpServletResponse,
				_mockFilterChain));
	}

	@Test
	public void testProcessFilter2() {
		_pathContext = _PATH_PROXY;
		_pathProxy = _PATH_PROXY;

		_mockHttpServletRequest.setRequestURI(_LAST_PATH);

		Assert.assertEquals(
			_LAST_PATH,
			_getLastPath(
				_mockHttpServletRequest, _mockHttpServletResponse,
				_mockFilterChain));
	}

	@Test
	public void testProcessFilter3() {
		_pathContext = _PATH_PROXY;
		_pathProxy = StringPool.BLANK;

		_mockHttpServletRequest.setRequestURI(_LAST_PATH);

		Assert.assertEquals(
			_LAST_PATH,
			_getLastPath(
				_mockHttpServletRequest, _mockHttpServletResponse,
				_mockFilterChain));
	}

	private String _getLastPath(
		MockHttpServletRequest request, MockHttpServletResponse response,
		MockFilterChain filterChain) {

		_virtualHostFilter.init(_mockFilterConfig);

		ReflectionTestUtil.invoke(
			_virtualHostFilter, "processFilter",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class,
				FilterChain.class
			},
			request, response, filterChain);

		LastPath lastPath = (LastPath)request.getAttribute(WebKeys.LAST_PATH);

		if (lastPath != null) {
			return lastPath.getPath();
		}

		return StringPool.BLANK;
	}

	private static final String _LAST_PATH =
		VirtualHostFilterTest._PATH_PROXY + "_last_path";

	private static final String _PATH_CONTEXT = "/context";

	private static final String _PATH_PROXY = "/proxy";

	private static LayoutSet _layoutSet;

	@Inject
	private static LayoutSetLocalService _layoutSetLocalService;

	private final MockFilterChain _mockFilterChain = new MockFilterChain();
	private final MockFilterConfig _mockFilterConfig = new MockFilterConfig();
	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private final MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();
	private String _pathContext;
	private String _pathProxy;

	@Inject
	private Portal _portal;

	@Inject
	private PortalUtil _portalUtil;

	private final VirtualHostFilter _virtualHostFilter =
		new VirtualHostFilter();

}