/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class FrontendJsWebUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "{0}: cdnHost={1}, contextPath={2}, proxyPath={3}"
	)
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK},
				{1, StringPool.BLANK, StringPool.BLANK, "/proxy"},
				{2, StringPool.BLANK, "/context", StringPool.BLANK},
				{3, StringPool.BLANK, "/context", "/proxy"},
				{4, "http://cdn.com", StringPool.BLANK, StringPool.BLANK},
				{5, "http://cdn.com", StringPool.BLANK, "/proxy"},
				{6, "http://cdn.com", "/context", StringPool.BLANK},
				{7, "http://cdn.com", "/context", "/proxy"}
			});
	}

	@Test
	public void testGetBaseURL() throws Exception {
		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			_RESULTS_GET_BASE_URL[index],
			FrontendJsWebUtil.getBaseURL(
				Mockito.mock(HttpServletRequest.class),
				_mockPortal(cdnHost, contextPath, proxyPath)));
	}

	@Test
	public void testGetPortalContextPath() throws Exception {
		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			_RESULTS_GET_PORTAL_CONTEXT_PATH[index],
			FrontendJsWebUtil.getPortalContextPath(
				_mockPortal(cdnHost, contextPath, proxyPath)));
	}

	@Test
	public void testGetServletContextNameFromServletContextPath()
		throws Exception {

		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			"frontend-js-web",
			FrontendJsWebUtil.getServletContextNameFromServletContextPath(
				_mockPortal(cdnHost, contextPath, proxyPath),
				contextPath + "/o/frontend-js-web"));
	}

	@Test
	public void testGetServletContextPathFromFileURI() throws Exception {
		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			contextPath + "/o/frontend-js-web",
			FrontendJsWebUtil.getServletContextPathFromFileURI(
				contextPath + "/o/frontend-js-web/__liferay__/index.js",
				_mockPortal(cdnHost, contextPath, proxyPath)));
	}

	@Test
	public void testGetServletContextPathFromServletContextName()
		throws Exception {

		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			_RESULTS_GET_SERVLET_CONTEXT_PATH_FROM_SERVLET_CONTEXT_NAME[index],
			FrontendJsWebUtil.getServletContextPathFromServletContextName(
				_mockPortal(cdnHost, contextPath, proxyPath),
				"frontend-js-web"));
	}

	@Test
	public void testGetServletContextResourcePathFromFileURI()
		throws Exception {

		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			"/__liferay__/index.js",
			FrontendJsWebUtil.getServletContextResourcePathFromFileURI(
				contextPath + "/o/frontend-js-web/__liferay__/index.js",
				_mockPortal(cdnHost, contextPath, proxyPath)));
	}

	@Test
	public void testGetWebContextPathFromFileURI() throws Exception {
		FrontendJsWebUtil.clearCache();

		Assert.assertEquals(
			"/frontend-js-web",
			FrontendJsWebUtil.getWebContextPathFromFileURI(
				_mockPortal(cdnHost, contextPath, proxyPath),
				contextPath + "/o/frontend-js-web/__liferay__/index.js"));
	}

	@Parameterized.Parameter(1)
	public String cdnHost;

	@Parameterized.Parameter(2)
	public String contextPath;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(3)
	public String proxyPath;

	private Portal _mockPortal(
			String cdnHost, String contextPath, String proxyPath)
		throws Exception {

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getCDNHost(Mockito.any())
		).thenReturn(
			cdnHost
		);

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			proxyPath + contextPath
		);

		Mockito.when(
			portal.getPathProxy()
		).thenReturn(
			proxyPath
		);

		return portal;
	}

	private static final String[] _RESULTS_GET_BASE_URL = {
		StringPool.BLANK, "/proxy", StringPool.BLANK, "/proxy",
		"http://cdn.com", "http://cdn.com/proxy", "http://cdn.com",
		"http://cdn.com/proxy"
	};

	private static final String[] _RESULTS_GET_PORTAL_CONTEXT_PATH = {
		StringPool.BLANK, StringPool.BLANK, "/context", "/context",
		StringPool.BLANK, StringPool.BLANK, "/context", "/context"
	};

	private static final String[]
		_RESULTS_GET_SERVLET_CONTEXT_PATH_FROM_SERVLET_CONTEXT_NAME = {
			"/o/frontend-js-web", "/o/frontend-js-web",
			"/context/o/frontend-js-web", "/context/o/frontend-js-web",
			"/o/frontend-js-web", "/o/frontend-js-web",
			"/context/o/frontend-js-web", "/context/o/frontend-js-web"
		};

}