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

package com.liferay.portal.servlet.filters.aggregate;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

/**
 * @author Cleydyr de Albuquerque
 */
public class AggregateFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpPortalUtil();

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			PortalExecutorManager.class,
			ProxyFactory.newDummyInstance(PortalExecutorManager.class), null);
	}

	@Test
	public void testAggregateWithImports() throws Exception {
		String fileName = "./my-styles.css";
		String css = "body {color: black;}";

		ServletPaths servletPaths = _createMockServletPaths(fileName, css);

		_testAggregateWithImports(
			servletPaths, css, _wrap(fileName, StringPool.QUOTE));

		String url = "https://example.com";

		_testAggregateWithImports(
			servletPaths, _wrap(url, StringPool.APOSTROPHE));
		_testAggregateWithImports(servletPaths, _wrap(url, StringPool.BLANK));
		_testAggregateWithImports(servletPaths, _wrap(url, StringPool.QUOTE));
	}

	private ServletPaths _createMockServletPaths(String fileName, String css) {
		ServletPaths servletPaths = Mockito.mock(ServletPaths.class);

		Mockito.when(
			servletPaths.down(Mockito.anyString())
		).thenReturn(
			servletPaths
		);

		ServletPaths cssServletPaths = Mockito.mock(ServletPaths.class);

		Mockito.when(
			cssServletPaths.getContent()
		).thenReturn(
			css
		);

		Mockito.when(
			cssServletPaths.getResourcePath()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			servletPaths.down(StringPool.QUOTE + fileName + StringPool.QUOTE)
		).thenReturn(
			cssServletPaths
		);

		Mockito.when(
			servletPaths.getContent()
		).thenReturn(
			null
		);

		Mockito.when(
			servletPaths.getResourcePath()
		).thenReturn(
			StringPool.BLANK
		);

		return servletPaths;
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		portalUtil.setPortal(portal);

		Mockito.when(
			portal.getPathModule()
		).thenReturn(
			StringPool.BLANK
		);
	}

	private void _testAggregateWithImports(
			ServletPaths servletPaths, String expected)
		throws Exception {

		_testAggregateWithImports(servletPaths, expected, expected);
	}

	private void _testAggregateWithImports(
			ServletPaths servletPaths, String expected, String content)
		throws Exception {

		Assert.assertEquals(
			expected, AggregateFilter.aggregateCss(servletPaths, content));
	}

	private String _wrap(String url, String delimiter) {
		return StringBundler.concat(
			"@import url(", delimiter, url, delimiter, ");");
	}

}