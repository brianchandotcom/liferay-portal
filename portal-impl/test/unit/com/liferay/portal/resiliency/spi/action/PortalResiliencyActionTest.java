/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.resiliency.spi.action;

import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.resiliency.spi.agent.SPIAgentRequest;
import com.liferay.portal.resiliency.spi.agent.SPIAgentResponse;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.EventImpl;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class PortalResiliencyActionTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		PropsUtil.setProps(new PropsImpl());

		PortletContainerUtil portletContainerUtil = new PortletContainerUtil();

		_mockPortletContainer = new MockPortletContainer();

		portletContainerUtil.setPortletContainer(_mockPortletContainer);

		_request = new MockHttpServletRequest();

		_request.setAttribute(
			WebKeys.SPI_AGENT_REQUEST,
			new SPIAgentRequest(new MockHttpServletRequest()));
		_request.setAttribute(
			WebKeys.SPI_AGENT_RESPONSE, new SPIAgentResponse());

		_portlet = new PortletImpl();

		_request.setAttribute(WebKeys.SPI_AGENT_PORTLET, _portlet);

		_layout = new LayoutImpl();

		_layout.setTypeSettings(_DEFAULT_LAYOUT_TYPE_SETTINGS);

		_request.setAttribute(WebKeys.LAYOUT, _layout);

		HttpSession session = _request.getSession();

		session.setAttribute(WebKeys.USER_PASSWORD, "password");

		_response = new MockHttpServletResponse();
	}

	@After
	public void tearDown() {
		Assert.assertTrue(_mockPortletContainer.prepared);
		Assert.assertEquals("password", PrincipalThreadLocal.getPassword());
		Assert.assertSame(
			Boolean.TRUE,
			_request.getAttribute(WebKeys.PORTAL_RESILIENCY_ACTION));

		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	@Test
	public void testActionPhase() throws Exception {

		// No change to layout type settings

		_request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.ACTION);

		_portalResiliencyAction.execute(null, null, _request, _response);

		Assert.assertSame(_request, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(
			_mockPortletContainer.actionResult,
			_request.getAttribute(WebKeys.SPI_AGENT_ACTION_RESULT));
		Assert.assertNull(
			_request.getAttribute(WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));

		// Update layout type settings

		_mockPortletContainer.modifyLayoutTypeSettings = true;

		_portalResiliencyAction.execute(null, null, _request, _response);

		Assert.assertSame(_request, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(
			_mockPortletContainer.actionResult,
			_request.getAttribute(WebKeys.SPI_AGENT_ACTION_RESULT));
		Assert.assertEquals(
			MockPortletContainer.MOCK_LAYOUT_TYPE_SETTINGS,
			_request.getAttribute(WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));
	}

	@Test
	public void testEventPhase() throws Exception {

		// No change to layout type settings

		_request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.EVENT);

		Event event = new EventImpl(null, null, null);

		_request.setAttribute(WebKeys.SPI_AGENT_EVENT, event);

		Layout layout = new LayoutImpl();

		_request.setAttribute(WebKeys.SPI_AGENT_LAYOUT, layout);

		_portalResiliencyAction.execute(null, null, _request, _response);

		Assert.assertSame(_request, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(layout, _mockPortletContainer.layout);
		Assert.assertSame(event, _mockPortletContainer.event);
		Assert.assertSame(
			_mockPortletContainer.events,
			_request.getAttribute(WebKeys.SPI_AGENT_EVENT_RESULT));
		Assert.assertNull(
			_request.getAttribute(WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));

		// Update layout type settings

		_mockPortletContainer.modifyLayoutTypeSettings = true;

		_portalResiliencyAction.execute(null, null, _request, _response);

		Assert.assertSame(_request, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(layout, _mockPortletContainer.layout);
		Assert.assertSame(event, _mockPortletContainer.event);
		Assert.assertSame(
			_mockPortletContainer.events,
			_request.getAttribute(WebKeys.SPI_AGENT_EVENT_RESULT));
		Assert.assertEquals(
			MockPortletContainer.MOCK_LAYOUT_TYPE_SETTINGS,
			_request.getAttribute(WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));
	}

	@Test
	public void testRenderPhase() throws Exception {
		_request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RENDER);

		_portalResiliencyAction.execute(null, null, _request, _response);

		Assert.assertSame(_request, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
	}

	@Test
	public void testResourcePhase() throws Exception {
		_request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RESOURCE);

		_portalResiliencyAction.execute(null, null, _request, _response);

		Assert.assertSame(_request, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
	}

	@AdviseWith(adviceClasses = {LifecycleAdvice.class})
	@Test
	public void testUnknownPhase() throws Exception {
		_request.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.values()[4]);

		try {
			_portalResiliencyAction.execute(null, null, _request, _response);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Unkown lifecycle UNKNOWN", iae.getMessage());
		}
	}

	@Aspect
	public static class LifecycleAdvice {

		@Around(
			"execution(* com.liferay.portal.kernel.resiliency.spi.agent." +
				"SPIAgent$Lifecycle.values())")
		public SPIAgent.Lifecycle[] values(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			SPIAgent.Lifecycle[] lifecycles =
				(SPIAgent.Lifecycle[])proceedingJoinPoint.proceed();

			SPIAgent.Lifecycle[] newLifecycles =
				new SPIAgent.Lifecycle[lifecycles.length + 1];

			System.arraycopy(
				lifecycles, 0, newLifecycles, 0, lifecycles.length);

			int ordinal = newLifecycles.length - 1;

			newLifecycles[ordinal] = ReflectionUtil.newEnumElement(
				SPIAgent.Lifecycle.class, new Class<?>[] {String.class},
					"UNKNOWN", ordinal, "UNKNOWN");

			return newLifecycles;
		}

	}

	private static final String _DEFAULT_LAYOUT_TYPE_SETTINGS =
		"_DEFAULT_LAYOUT_TYPE_SETTINGS";

	private Layout _layout;
	private MockPortletContainer _mockPortletContainer;
	private PortalResiliencyAction _portalResiliencyAction =
		new PortalResiliencyAction();
	private Portlet _portlet;
	private HttpServletRequest _request;
	private HttpServletResponse _response;

}