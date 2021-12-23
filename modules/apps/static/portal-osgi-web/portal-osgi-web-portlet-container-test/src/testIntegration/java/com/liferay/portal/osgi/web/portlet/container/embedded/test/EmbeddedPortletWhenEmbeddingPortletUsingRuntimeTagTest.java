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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.web.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.osgi.web.portlet.container.test.TestPortlet;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.portletext.RuntimeTag;

import java.io.IOException;

import java.util.Dictionary;
import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
public class EmbeddedPortletWhenEmbeddingPortletUsingRuntimeTagTest
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
	public void testRenderInfiniteRecursionEmbeddedAndRuntimePortlets()
		throws Exception {

		TestPortlet testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				super.render(renderRequest, renderResponse);

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				renderRequest.setAttribute(
					"testRuntimePortletId",
					PortletIdCodec.encode(
						TEST_PORTLET_ID, StringUtil.randomString(8)));

				portletRequestDispatcher.include(renderRequest, renderResponse);
			}

		};

		setUpPortlet(
			testPortlet,
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.instanceable", Boolean.TRUE.toString()
			).build(),
			TEST_PORTLET_ID);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				RuntimeTag.class.getName(), LoggerTestUtil.WARN)) {

			PortletContainerTestUtil.Response response =
				PortletContainerTestUtil.request(
					PortletURLBuilder.create(
						_portletURLFactory.create(
							PortletContainerTestUtil.getHttpServletRequest(
								group, layout),
							TEST_PORTLET_ID, layout.getPlid(),
							PortletRequest.RENDER_PHASE)
					).buildString());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"The application cannot include itself: " + TEST_PORTLET_ID,
				logEntry.getMessage());

			User defaultUser = _userLocalService.getDefaultUser(
				group.getCompanyId());

			String errorMessage = _language.get(
				defaultUser.getLocale(),
				"the-application-cannot-include-itself");

			String body = response.getBody();

			Assert.assertTrue(
				"Page body should contain error message : " + errorMessage,
				body.contains(errorMessage));

			Assert.assertEquals(200, response.getCode());

			Assert.assertTrue(testPortlet.isCalledRender());
		}
	}

	@Test
	public void testShouldNotCreatePortletPreferencesEmbeddedAndRuntimePortlets()
		throws Exception {

		TestPortlet testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				super.render(renderRequest, renderResponse);

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				portletRequestDispatcher.include(renderRequest, renderResponse);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		TestRuntimePortlet testRuntimePortlet = new TestRuntimePortlet();
		String testRuntimePortletId = "testRuntimePortletId";

		setUpPortlet(
			testRuntimePortlet, properties, testRuntimePortletId, false);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(
				PortletURLBuilder.create(
					_portletURLFactory.create(
						PortletContainerTestUtil.getHttpServletRequest(
							group, layout),
						TEST_PORTLET_ID, layout.getPlid(),
						PortletRequest.RENDER_PHASE)
				).setParameters(
					HashMapBuilder.put(
						"persistSettings",
						new String[] {Boolean.FALSE.toString()}
					).put(
						"testRuntimePortletId",
						new String[] {testRuntimePortletId}
					).build()
				).buildString());

		long count = _portletPreferencesLocalService.getPortletPreferencesCount(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			testRuntimePortletId);

		Assert.assertTrue("portletPreferences count should be 0", count == 0);

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertTrue(testRuntimePortlet.isCalledRender());
	}

	@Test
	public void testShouldRenderEmbeddedAndRuntimePortlets() throws Exception {
		TestPortlet testPortlet = new TestPortlet() {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException, PortletException {

				super.serveResource(resourceRequest, resourceResponse);

				PortletContext portletContext = getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/runtime_portlet.jsp");

				portletRequestDispatcher.include(
					resourceRequest, resourceResponse);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID, false);

		_portletPreferencesLocalService.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			TEST_PORTLET_ID, null, null);

		TestRuntimePortlet testRuntimePortlet = new TestRuntimePortlet();
		String testRuntimePortletId = "testRuntimePortletId";

		setUpPortlet(
			testRuntimePortlet, properties, testRuntimePortletId, false);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(
				PortletURLBuilder.create(
					_portletURLFactory.create(
						PortletContainerTestUtil.getHttpServletRequest(
							group, layout),
						TEST_PORTLET_ID, layout.getPlid(),
						PortletRequest.RESOURCE_PHASE)
				).setParameter(
					"testRuntimePortletId", testRuntimePortletId
				).buildString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledServeResource());
		Assert.assertTrue(testRuntimePortlet.isCalledRuntime());
	}

	private static String[] _layoutStaticPortletsAll;
	private static LayoutTypePortlet _layoutTypePortlet;

	@Inject
	private Language _language;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private PortletURLFactory _portletURLFactory;

	@Inject
	private UserLocalService _userLocalService;

}