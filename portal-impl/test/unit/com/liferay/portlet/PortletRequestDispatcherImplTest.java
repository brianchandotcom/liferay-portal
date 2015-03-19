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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import org.testng.Assert;

/**
 * @author William Newbury
 */
public class PortletRequestDispatcherImplTest {

	@BeforeClass
	public static void setUpClass() {
		final PortletApp portletApp = (PortletApp)ProxyUtil.newProxyInstance(
			PortletApp.class.getClassLoader(),
			new Class<?>[] {PortletApp.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					String methodName = method.getName();

					if (methodName.equals("getServletURLPatterns")) {
						Set<String> servletUrlPatterns = new HashSet<>();

						servletUrlPatterns.add("/testPath/*");

						return servletUrlPatterns;
					}

					return null;
				}

			});

		_portlet = (Portlet)ProxyUtil.newProxyInstance(
			Portlet.class.getClassLoader(), new Class<?>[] {Portlet.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					String methodName = method.getName();

					if (methodName.equals("getPortletApp")) {
						return portletApp;
					}

					return null;
				}

			});

		_portletContext = (LiferayPortletContext)ProxyUtil.newProxyInstance(
			LiferayPortletContext.class.getClassLoader(),
			new Class<?>[] {LiferayPortletContext.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					String methodName = method.getName();

					if (methodName.equals("getPortlet")) {
						return _portlet;
					}

					return null;
				}

			});

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testInclude() throws IOException, PortletException {
		TestRequestDispatcher requestDispatcher = new TestRequestDispatcher(
			"/testPath", null, "/testPath", "");

		PortletRequestDispatcherImpl portletRequestDispatcher =
			new PortletRequestDispatcherImpl(
				requestDispatcher, true, _portletContext, "/testPath");

		TestPortletRequest request = new TestPortletRequest(_portlet);
		TestPortletResponse response = new TestPortletResponse();

		portletRequestDispatcher.include(request, response);
	}

	@Test
	public void testIncludeAlternateContextPath()
		throws IOException, PortletException {

		TestRequestDispatcher requestDispatcher = new TestRequestDispatcher(
			"/testPath", null, "/test/testPath", "");

		PortletRequestDispatcherImpl portletRequestDispatcher =
			new PortletRequestDispatcherImpl(
				requestDispatcher, true, _portletContext, "/testPath");

		TestPortletRequest request = new TestPortletRequest("/test", _portlet);
		TestPortletResponse response = new TestPortletResponse();

		portletRequestDispatcher.include(request, response);
	}

	@Test
	public void testIncludeNoPath() throws IOException, PortletException {
		TestRequestDispatcher requestDispatcher = new TestRequestDispatcher(
			null, null, "", "");

		PortletRequestDispatcherImpl portletRequestDispatcher =
			new PortletRequestDispatcherImpl(
				requestDispatcher, true, _portletContext);

		TestPortletRequest request = new TestPortletRequest(_portlet);
		TestPortletResponse response = new TestPortletResponse();

		portletRequestDispatcher.include(request, response);
	}

	@Test
	public void testIncludeWithQueryParams()
		throws IOException, PortletException {

		TestRequestDispatcher requestDispatcher = new TestRequestDispatcher(
			"/moreTestPath", "testName=&testname=testvalue&testname=testvalue2",
			"/testPath/moreTestPath", "/testPath");

		PortletRequestDispatcherImpl portletRequestDispatcher =
			new PortletRequestDispatcherImpl(
				requestDispatcher, true, _portletContext,
				"/testPath/moreTestPath?testName=&testname=testvalue&" +
					"testname=testvalue2");

		TestPortletRequest request = new TestPortletRequest(_portlet);
		TestPortletResponse response = new TestPortletResponse();

		portletRequestDispatcher.include(request, response);
	}

	@Test
	public void testIncludeWithUnmatchedPath()
		throws IOException, PortletException {

		TestRequestDispatcher requestDispatcher = new TestRequestDispatcher(
			"/unmatchedPath", null, "/unmatchedPath", "");

		PortletRequestDispatcherImpl portletRequestDispatcher =
			new PortletRequestDispatcherImpl(
				requestDispatcher, true, _portletContext, "/unmatchedPath");

		TestPortletRequest request = new TestPortletRequest(_portlet);
		TestPortletResponse response = new TestPortletResponse();

		portletRequestDispatcher.include(request, response);
	}

	@Test
	public void testIncludeWithUnrecognizedSeparator()
		throws IOException, PortletException {

		TestRequestDispatcher requestDispatcher = new TestRequestDispatcher(
			"/testPath|", null, "/testPath|", "");

		PortletRequestDispatcherImpl portletRequestDispatcher =
			new PortletRequestDispatcherImpl(
				requestDispatcher, true, _portletContext, "/testPath|");

		TestPortletRequest request = new TestPortletRequest(_portlet);
		TestPortletResponse response = new TestPortletResponse();

		portletRequestDispatcher.include(request, response);
	}

	private static Portlet _portlet;
	private static PortletContext _portletContext;

	private class TestPortletRequest extends RenderRequestImpl {

		public TestPortletRequest(Portlet portlet) {
			_contextPath = StringPool.SLASH;
			_portlet = portlet;
		}

		public TestPortletRequest(String contextPath, Portlet portlet) {
			_contextPath = contextPath;
			_portlet = portlet;
		}

		@Override
		public String getContextPath() {
			return _contextPath;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

		@Override
		public Portlet getPortlet() {
			return _portlet;
		}

		@Override
		public boolean isPrivateRequestAttributes() {
			return false;
		}

		private final String _contextPath;
		private final Portlet _portlet;

	}

	private class TestPortletResponse extends RenderResponseImpl {

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return new MockHttpServletResponse();
		}

		@Override
		public boolean isCalledFlushBuffer() {
			return false;
		}

		@Override
		public void setURLEncoder(URLEncoder urlEncoder) {
		}

	}

	private class TestRequestDispatcher implements RequestDispatcher {

		public TestRequestDispatcher(
			String expectedPathInfo, String expectedQueryString,
			String expectedRequestURI, String expectedServletPath) {

			pathInfo = expectedPathInfo;
			queryString = expectedQueryString;
			requestURI = expectedRequestURI;
			servletPath = expectedServletPath;
		}

		public void assertPropogatedInformation(
			PortletServletRequest portletServletRequest,
			PortletServletResponse portletServletResponse) {

			Assert.assertEquals(portletServletRequest.getPathInfo(), pathInfo);
			Assert.assertEquals(
				portletServletRequest.getQueryString(), queryString);
			Assert.assertEquals(
				portletServletRequest.getRequestURI(), requestURI);
			Assert.assertEquals(
				portletServletRequest.getServletPath(), servletPath);
		}

		@Override
		public void forward(ServletRequest request, ServletResponse response) {
			assertPropogatedInformation(
				(PortletServletRequest)request,
				(PortletServletResponse)response);
		}

		@Override
		public void include(ServletRequest request, ServletResponse response) {
			assertPropogatedInformation(
				(PortletServletRequest)request,
				(PortletServletResponse)response);
		}

		private final String pathInfo;
		private final String queryString;
		private final String requestURI;
		private final String servletPath;

	}

}