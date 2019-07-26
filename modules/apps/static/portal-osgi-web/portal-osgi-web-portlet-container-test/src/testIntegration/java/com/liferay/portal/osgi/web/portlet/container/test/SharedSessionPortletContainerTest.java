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

package com.liferay.portal.osgi.web.portlet.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicReference;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class SharedSessionPortletContainerTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPrivateFalsePortalToPortlet() throws Exception {
		final String attributeKey = "TEST_ATTRIBUTE";
		final String attributeValue = "TEST_VALUE";
		final AtomicReference<Object> sessionValue = new AtomicReference<>();

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletSession portletSession =
					renderRequest.getPortletSession();

				Object value = portletSession.getAttribute(
					attributeKey, PortletSession.APPLICATION_SCOPE);

				sessionValue.set(value);

				super.render(renderRequest, renderResponse);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"com.liferay.portlet.private-session-attributes", Boolean.FALSE);

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		LifecycleAction lifecycleAction = new LifecycleAction() {

			@Override
			public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
				throws ActionException {

				HttpServletRequest httpServletRequest =
					lifecycleEvent.getRequest();

				HttpSession session = httpServletRequest.getSession(true);

				session.setAttribute(attributeKey, attributeValue);
			}

		};

		properties = new HashMapDictionary<>();

		properties.put("key", "servlet.service.events.pre");

		registerService(LifecycleAction.class, lifecycleAction, properties);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertEquals(attributeValue, sessionValue.get());
	}

	@Test
	public void testPrivateFalsePortletToPortal() throws Exception {
		final String attributeKey = "TEST_ATTRIBUTE";
		final String attributeValue = "TEST_VALUE";
		final AtomicReference<Object> sessionValue = new AtomicReference<>();

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletSession portletSession =
					renderRequest.getPortletSession();

				portletSession.setAttribute(
					attributeKey, attributeValue,
					PortletSession.APPLICATION_SCOPE);

				super.render(renderRequest, renderResponse);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"com.liferay.portlet.private-session-attributes", Boolean.FALSE);

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID);

		LifecycleAction lifecycleAction = new LifecycleAction() {

			@Override
			public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
				throws ActionException {

				HttpServletRequest httpServletRequest =
					lifecycleEvent.getRequest();

				HttpSession session = httpServletRequest.getSession(true);

				Object value = session.getAttribute(attributeKey);

				sessionValue.set(value);
			}

		};

		properties = new HashMapDictionary<>();

		properties.put("key", "servlet.service.events.post");

		registerService(LifecycleAction.class, lifecycleAction, properties);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertEquals(attributeValue, sessionValue.get());
	}

	@Test
	public void testPrivateTruePortalToPortlet() throws Exception {
		final String attributeKey = "TEST_ATTRIBUTE";
		final String attributeValue = "TEST_VALUE";
		final AtomicReference<Object> sessionValue = new AtomicReference<>();

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletSession portletSession =
					renderRequest.getPortletSession();

				Object value = portletSession.getAttribute(
					attributeKey, PortletSession.APPLICATION_SCOPE);

				sessionValue.set(value);

				super.render(renderRequest, renderResponse);
			}

		};

		setUpPortlet(
			testPortlet, new HashMapDictionary<String, Object>(),
			TEST_PORTLET_ID);

		LifecycleAction lifecycleAction = new LifecycleAction() {

			@Override
			public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
				throws ActionException {

				HttpServletRequest httpServletRequest =
					lifecycleEvent.getRequest();

				HttpSession session = httpServletRequest.getSession(true);

				session.setAttribute(attributeKey, attributeValue);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("key", "servlet.service.events.pre");

		registerService(LifecycleAction.class, lifecycleAction, properties);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertNotEquals(attributeValue, sessionValue.get());
	}

	@Test
	public void testPrivateTruePortalToPortletSharedAttribute()
		throws Exception {

		final String attributeKey = "LIFERAY_SHARED_TEST_ATTRIBUTE";
		final String attributeValue = "TEST_VALUE";
		final AtomicReference<Object> sessionValue = new AtomicReference<>();

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletSession portletSession =
					renderRequest.getPortletSession();

				Object value = portletSession.getAttribute(
					attributeKey, PortletSession.APPLICATION_SCOPE);

				sessionValue.set(value);

				super.render(renderRequest, renderResponse);
			}

		};

		setUpPortlet(
			testPortlet, new HashMapDictionary<String, Object>(),
			TEST_PORTLET_ID);

		LifecycleAction lifecycleAction = new LifecycleAction() {

			@Override
			public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
				throws ActionException {

				HttpServletRequest httpServletRequest =
					lifecycleEvent.getRequest();

				HttpSession session = httpServletRequest.getSession(true);

				session.setAttribute(attributeKey, attributeValue);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("key", "servlet.service.events.pre");

		registerService(LifecycleAction.class, lifecycleAction, properties);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertEquals(attributeValue, sessionValue.get());
	}

	@Test
	public void testPrivateTruePortletToPortal() throws Exception {
		final String attributeKey = "TEST_ATTRIBUTE";
		final String attributeValue = "TEST_VALUE";
		final AtomicReference<Object> sessionValue = new AtomicReference<>();

		testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				PortletSession portletSession =
					renderRequest.getPortletSession();

				portletSession.setAttribute(
					attributeKey, attributeValue,
					PortletSession.APPLICATION_SCOPE);

				super.render(renderRequest, renderResponse);
			}

		};

		setUpPortlet(
			testPortlet, new HashMapDictionary<String, Object>(),
			TEST_PORTLET_ID);

		LifecycleAction lifecycleAction = new LifecycleAction() {

			@Override
			public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
				throws ActionException {

				HttpServletRequest httpServletRequest =
					lifecycleEvent.getRequest();

				HttpSession session = httpServletRequest.getSession(true);

				Object value = session.getAttribute(attributeKey);

				sessionValue.set(value);
			}

		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("key", "servlet.service.events.post");

		registerService(LifecycleAction.class, lifecycleAction, properties);

		HttpServletRequest httpServletRequest =
			PortletContainerTestUtil.getHttpServletRequest(group, layout);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, TEST_PORTLET_ID, layout.getPlid(),
			PortletRequest.RENDER_PHASE);

		PortletContainerTestUtil.Response response =
			PortletContainerTestUtil.request(portletURL.toString());

		Assert.assertEquals(200, response.getCode());

		Assert.assertTrue(testPortlet.isCalledRender());
		Assert.assertNotEquals(attributeValue, sessionValue.get());
	}

}