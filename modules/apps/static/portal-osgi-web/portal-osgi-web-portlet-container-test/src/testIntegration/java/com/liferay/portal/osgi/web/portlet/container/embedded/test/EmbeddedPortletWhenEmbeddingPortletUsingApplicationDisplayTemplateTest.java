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

package com.liferay.portal.osgi.web.portlet.container.embedded.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.osgi.web.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.osgi.web.portlet.container.test.TestPortlet;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Dictionary;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class
	EmbeddedPortletWhenEmbeddingPortletUsingApplicationDisplayTemplateTest
		extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		_layoutStaticPortletsAll = PropsValues.LAYOUT_STATIC_PORTLETS_ALL;
	}

	@Test
	public void testShouldRenderApplicationDisplayTemplateAndRuntimePortlets()
		throws Exception {

		TestPortlet testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				super.render(renderRequest, renderResponse);

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/view.jsp");

				portletRequestDispatcher.include(renderRequest, renderResponse);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"com.liferay.portlet.instanceable", Boolean.FALSE.toString());

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		properties.put("javax.portlet.name", TEST_PORTLET_ID);

		registerService(
			TemplateHandler.class,
			new TestEmbeddedPortletDisplayTemplateHandler(), properties);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		TestRuntimePortlet testRuntimePortlet = new TestRuntimePortlet();
		String testRuntimePortletId = "testRuntimePortletId";

		setUpPortlet(
			testRuntimePortlet, properties, testRuntimePortletId, false);

		portletURL.setParameter("testRuntimePortletId", testRuntimePortletId);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertTrue(testRuntimePortlet.isCalledRuntime());
	}

	private static String[] _layoutStaticPortletsAll;
	private static LayoutTypePortlet _layoutTypePortlet;

}