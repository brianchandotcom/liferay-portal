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

package com.liferay.portal.resiliency.spi.action;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.resiliency.spi.agent.SPIAgentRequest;
import com.liferay.portal.resiliency.spi.agent.SPIAgentResponse;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portlet.EventImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.io.IOException;

import javax.portlet.Event;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Before
	public void setUp() throws IOException {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		PropsUtil.setProps(new PropsImpl());

		PortletContainerUtil portletContainerUtil = new PortletContainerUtil();

		_mockPortletContainer = new MockPortletContainer();

		portletContainerUtil.setPortletContainer(_mockPortletContainer);

		_mockHttpServletRequest = new MockHttpServletRequest();

		_layout = new LayoutImpl();

		_layout.setTypeSettings(_DEFAULT_LAYOUT_TYPE_SETTINGS);

		_mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		_portlet = new PortletImpl() {

			@Override
			public String getContextName() {
				return _SERVLET_CONTEXT_NAME;
			}

		};

		_portlet.setPortletId(_PORTLET_ID);

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, _portlet);

		MockHttpServletRequest originalMockHttpServletRequest =
			new MockHttpServletRequest();

		originalMockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_PORTLET, _portlet);

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_REQUEST,
			new SPIAgentRequest(originalMockHttpServletRequest));

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_RESPONSE,
			new SPIAgentResponse(_SERVLET_CONTEXT_NAME));

		_response = new MockHttpServletResponse();

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(WebKeys.USER_PASSWORD, "password");
	}

	@After
	public void tearDown() {
		if (_mockHttpServletRequest.getParameter("p_p_id") == null) {
			Assert.assertFalse(_mockPortletContainer.prepared);
		}
		else {
			Assert.assertTrue(_mockPortletContainer.prepared);
		}

		Assert.assertEquals("password", PrincipalThreadLocal.getPassword());
		Assert.assertSame(
			Boolean.TRUE,
			_mockHttpServletRequest.getAttribute(
				WebKeys.PORTAL_RESILIENCY_ACTION));

		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	@Test
	public void testActionPhase() throws Exception {

		// No change to layout type settings

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.ACTION);

		_portalResiliencyAction.execute(
			null, null, _mockHttpServletRequest, _response);

		Assert.assertSame(
			_mockHttpServletRequest, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(
			_mockPortletContainer.actionResult,
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_ACTION_RESULT));
		Assert.assertNull(
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));

		// Update layout type settings

		_mockPortletContainer.modifyLayoutTypeSettings = true;

		_mockHttpServletRequest.setParameter("p_p_id", _PORTLET_ID);

		_portalResiliencyAction.execute(
			null, null, _mockHttpServletRequest, _response);

		Assert.assertSame(
			_mockHttpServletRequest, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(
			_mockPortletContainer.actionResult,
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_ACTION_RESULT));
		Assert.assertEquals(
			MockPortletContainer.MOCK_LAYOUT_TYPE_SETTINGS,
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));
	}

	@Test
	public void testEventPhase() throws Exception {

		// No change to layout type settings

		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.EVENT);

		Event event = new EventImpl(null, null, null);

		_mockHttpServletRequest.setAttribute(WebKeys.SPI_AGENT_EVENT, event);

		Layout layout = new LayoutImpl();

		_mockHttpServletRequest.setAttribute(WebKeys.SPI_AGENT_LAYOUT, layout);

		_portalResiliencyAction.execute(
			null, null, _mockHttpServletRequest, _response);

		Assert.assertSame(
			_mockHttpServletRequest, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(layout, _mockPortletContainer.layout);
		Assert.assertSame(event, _mockPortletContainer.event);
		Assert.assertSame(
			_mockPortletContainer.events,
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_EVENT_RESULT));
		Assert.assertNull(
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));

		// Update layout type settings

		_mockPortletContainer.modifyLayoutTypeSettings = true;

		_mockHttpServletRequest.setParameter("p_p_id", _PORTLET_ID);

		_portalResiliencyAction.execute(
			null, null, _mockHttpServletRequest, _response);

		Assert.assertSame(
			_mockHttpServletRequest, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
		Assert.assertSame(layout, _mockPortletContainer.layout);
		Assert.assertSame(event, _mockPortletContainer.event);
		Assert.assertSame(
			_mockPortletContainer.events,
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_EVENT_RESULT));
		Assert.assertEquals(
			MockPortletContainer.MOCK_LAYOUT_TYPE_SETTINGS,
			_mockHttpServletRequest.getAttribute(
				WebKeys.SPI_AGENT_LAYOUT_TYPE_SETTINGS));
	}

	@Test
	public void testRenderPhase() throws Exception {
		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RENDER);

		_portalResiliencyAction.execute(
			null, null, _mockHttpServletRequest, _response);

		Assert.assertSame(
			_mockHttpServletRequest, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
	}

	@Test
	public void testResourcePhase() throws Exception {
		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.RESOURCE);

		_portalResiliencyAction.execute(
			null, null, _mockHttpServletRequest, _response);

		Assert.assertSame(
			_mockHttpServletRequest, _mockPortletContainer.request);
		Assert.assertSame(_response, _mockPortletContainer.response);
		Assert.assertSame(_portlet, _mockPortletContainer.portlet);
	}

	@AdviseWith(adviceClasses = {LifecycleAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testUnknownPhase() throws Exception {
		_mockHttpServletRequest.setAttribute(
			WebKeys.SPI_AGENT_LIFECYCLE, SPIAgent.Lifecycle.values()[4]);

		try {
			_portalResiliencyAction.execute(
				null, null, _mockHttpServletRequest, _response);

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
				"SPIAgent$Lifecycle.values())"
		)
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

			newLifecycles[ordinal] = ReflectionTestUtil.newEnumElement(
				SPIAgent.Lifecycle.class, new Class<?>[] {String.class},
				"UNKNOWN", ordinal, "UNKNOWN");

			return newLifecycles;
		}

	}

	private static final String _DEFAULT_LAYOUT_TYPE_SETTINGS =
		"_DEFAULT_LAYOUT_TYPE_SETTINGS";

	private static final String _PORTLET_ID = "PORTLET_ID";

	private static final String _SERVLET_CONTEXT_NAME = "SERVLET_CONTEXT_NAME";

	private Layout _layout;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockPortletContainer _mockPortletContainer;
	private final PortalResiliencyAction _portalResiliencyAction =
		new PortalResiliencyAction();
	private Portlet _portlet;
	private HttpServletResponse _response;

}