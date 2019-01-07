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

package com.liferay.frontend.js.loader.modules.extender.internal;

import aQute.lib.converter.Converter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.component.ComponentContext;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Raymond Augé
 */
@RunWith(PowerMockRunner.class)
public class JSLoaderModulesServletTest extends PowerMockito {

	@Before
	public void setUp() {
		_portal = PortalUtil.getPortal();

		_portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String getPathContext() {
					return StringPool.BLANK;
				}

			});
	}

	@After
	public void tearDown() {
		_portalUtil.setPortal(_portal);
	}

	@Test
	public void testBasicOutput() throws Exception {
		JSLoaderModulesServlet jsLoaderModulesServlet =
			buildJSLoaderModulesServlet();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		jsLoaderModulesServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertNotNull(mockHttpServletResponse.getContentAsString());
		Assert.assertEquals(
			Details.CONTENT_TYPE, mockHttpServletResponse.getContentType());
	}

	private JSLoaderModulesServlet buildJSLoaderModulesServlet()
		throws Exception {

		return buildJSLoaderModulesServlet(
			Collections.emptyMap());
	}

	private JSLoaderModulesServlet buildJSLoaderModulesServlet(
		Map<String, Object> properties)
		throws Exception {

		JSLoaderModulesServlet jsLoaderModulesServlet =
			new JSLoaderModulesServlet();

		ReflectionTestUtil.setFieldValue(
			jsLoaderModulesServlet, "_minifier",
			new Minifier() {

				@Override
				public String minify(String resourceName, String content) {
					return content;
				}

			});

		jsLoaderModulesServlet.activate(
			mock(ComponentContext.class), properties);

		MockServletContext mockServletContext = new MockServletContext();

		mockServletContext.setContextPath("/loader");

		jsLoaderModulesServlet.init(new MockServletConfig(mockServletContext));

		jsLoaderModulesServlet.setDetails(
			Converter.cnv(Details.class, properties));

		JSLoaderModulesTracker jsLoaderModulesTracker =
			new JSLoaderModulesTracker();

		jsLoaderModulesTracker.setDetails(
			Converter.cnv(Details.class, properties));

		jsLoaderModulesServlet.setJSLoaderModulesTracker(
			jsLoaderModulesTracker);

		return jsLoaderModulesServlet;
	}

	private Portal _portal;
	private final PortalUtil _portalUtil = new PortalUtil();

}