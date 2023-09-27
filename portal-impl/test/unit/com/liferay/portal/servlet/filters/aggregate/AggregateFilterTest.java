/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Test
	public void testAggregateWithImports() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		portalUtil.setPortal(portal);

		Mockito.when(
			portal.getPathModule()
		).thenReturn(
			StringPool.BLANK
		);

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			PortalExecutorManager.class,
			ProxyFactory.newDummyInstance(PortalExecutorManager.class), null);

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