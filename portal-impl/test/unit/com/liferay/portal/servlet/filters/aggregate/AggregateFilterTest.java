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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.framework.BundleContext;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Cleydyr de Albuquerque
 */
@PrepareForTest(PortalUtil.class)
@RunWith(PowerMockRunner.class)
public class AggregateFilterTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

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
		ServletPaths servletPaths = mock(ServletPaths.class);

		when(
			servletPaths.down(Mockito.anyString())
		).thenReturn(
			servletPaths
		);

		ServletPaths cssServletPaths = mock(ServletPaths.class);

		when(
			cssServletPaths.getContent()
		).thenReturn(
			css
		);

		when(
			cssServletPaths.getResourcePath()
		).thenReturn(
			StringPool.BLANK
		);

		when(
			servletPaths.down(StringPool.QUOTE + fileName + StringPool.QUOTE)
		).thenReturn(
			cssServletPaths
		);

		when(
			servletPaths.getContent()
		).thenReturn(
			null
		);

		when(
			servletPaths.getResourcePath()
		).thenReturn(
			StringPool.BLANK
		);

		return servletPaths;
	}

	private void _setUpPortalUtil() {
		mockStatic(PortalUtil.class);

		PowerMockito.when(
			PortalUtil.getPathModule()
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