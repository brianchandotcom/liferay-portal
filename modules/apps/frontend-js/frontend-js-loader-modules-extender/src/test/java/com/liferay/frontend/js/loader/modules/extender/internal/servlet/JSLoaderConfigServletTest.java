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

package com.liferay.frontend.js.loader.modules.extender.internal.servlet;

import aQute.lib.converter.Converter;

import com.liferay.frontend.js.loader.modules.extender.internal.Details;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.portal.url.builder.ServletAbsolutePortalURLBuilder;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

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
public class JSLoaderConfigServletTest extends PowerMockito {

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
		JSLoaderConfigServlet jsLoaderConfigServlet =
			_buildJSLoaderModulesServlet();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		jsLoaderConfigServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		String content = mockHttpServletResponse.getContentAsString();

		Assert.assertNotNull(content);
		Assert.assertTrue(
			content.contains(
				"Liferay.RESOLVE_PATH = \"/o/js_resolve_modules\""));

		Assert.assertEquals(
			ContentTypes.TEXT_JAVASCRIPT_UTF8,
			mockHttpServletResponse.getContentType());
	}

	private JSLoaderConfigServlet _buildJSLoaderModulesServlet()
		throws Exception {

		return _buildJSLoaderModulesServlet(Collections.emptyMap());
	}

	private JSLoaderConfigServlet _buildJSLoaderModulesServlet(
			Map<String, Object> properties)
		throws Exception {

		JSLoaderConfigServlet jsLoaderConfigServlet =
			new JSLoaderConfigServlet();

		jsLoaderConfigServlet.activate(
			mock(ComponentContext.class), properties);

		AbsolutePortalURLBuilderFactory absolutePortalURLBuilderFactory = mock(
			AbsolutePortalURLBuilderFactory.class);

		AbsolutePortalURLBuilder absolutePortalURLBuilder = mock(
			AbsolutePortalURLBuilder.class);

		ServletAbsolutePortalURLBuilder servletAbsolutePortalURLBuilder = mock(
			ServletAbsolutePortalURLBuilder.class);

		when(
			servletAbsolutePortalURLBuilder.build()
		).thenReturn(
			"/o/js_resolve_modules"
		);

		when(
			absolutePortalURLBuilder.forServlet(Mockito.any())
		).thenReturn(
			servletAbsolutePortalURLBuilder
		);

		when(
			absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				Mockito.any())
		).thenReturn(
			absolutePortalURLBuilder
		);

		jsLoaderConfigServlet.setAbsolutePortalURLBuilderFactory(
			absolutePortalURLBuilderFactory);

		MockServletContext mockServletContext = new MockServletContext();

		mockServletContext.setContextPath("/loader");

		jsLoaderConfigServlet.init(new MockServletConfig(mockServletContext));

		jsLoaderConfigServlet.setDetails(
			Converter.cnv(Details.class, properties));

		return jsLoaderConfigServlet;
	}

	private Portal _portal;
	private final PortalUtil _portalUtil = new PortalUtil();

}