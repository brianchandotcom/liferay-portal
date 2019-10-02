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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portlet.internal.PortletContextImpl;
import com.liferay.portlet.internal.PortletRequestDispatcherImpl;
import com.liferay.portlet.internal.RenderRequestImpl;
import com.liferay.portlet.internal.RenderResponseImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.Collections;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

/**
 * @author William Newbury
 */
public class PortletRequestDispatcherImplTest {

	@BeforeClass
	public static void setUpClass() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testInclude() throws Exception {
		PortletRequestDispatcherImpl portletRequestDispatcherImpl =
			new PortletRequestDispatcherImpl(
				new TestRequestDispatcher(null, null, "/testPath", "/testPath"),
				true, _portletContext, "/testPath");

		portletRequestDispatcherImpl.include(_portletRequest, _portletResponse);
	}

	@Test
	public void testIncludeAlternateContextPath() throws Exception {
		PortletRequestDispatcherImpl portletRequestDispatcherImpl =
			new PortletRequestDispatcherImpl(
				new TestRequestDispatcher(
					null, null, "/test/testPath", "/testPath"),
				true, _portletContext, "/testPath");

		portletRequestDispatcherImpl.include(
			new TestPortletRequest("/test", _portlet), _portletResponse);
	}

	@Test
	public void testIncludeNoPath() throws Exception {
		PortletRequestDispatcherImpl portletRequestDispatcherImpl =
			new PortletRequestDispatcherImpl(
				new TestRequestDispatcher(null, null, "", ""), true,
				_portletContext);

		portletRequestDispatcherImpl.include(_portletRequest, _portletResponse);
	}

	@Test
	public void testIncludeWithQueryString() throws Exception {
		PortletRequestDispatcherImpl portletRequestDispatcherImpl =
			new PortletRequestDispatcherImpl(
				new TestRequestDispatcher(
					"/moreTestPath",
					"testName=&testname=testvalue&testname=testvalue2",
					"/testPath/moreTestPath", "/testPath"),
				true, _portletContext,
				"/testPath/moreTestPath?testName=&testname=testvalue&" +
					"testname=testvalue2");

		portletRequestDispatcherImpl.include(_portletRequest, _portletResponse);
	}

	@Test
	public void testIncludeWithUnmatchedPath() throws Exception {
		PortletRequestDispatcherImpl portletRequestDispatcherImpl =
			new PortletRequestDispatcherImpl(
				new TestRequestDispatcher(
					null, null, "/unmatchedPath", "/unmatchedPath"),
				true, _portletContext, "/unmatchedPath");

		portletRequestDispatcherImpl.include(_portletRequest, _portletResponse);
	}

	@Test
	public void testIncludeWithUnrecognizedSeparator() throws Exception {
		PortletRequestDispatcherImpl portletRequestDispatcherImpl =
			new PortletRequestDispatcherImpl(
				new TestRequestDispatcher(
					null, null, "/testPath|", "/testPath|"),
				true, _portletContext, "/testPath|");

		portletRequestDispatcherImpl.include(_portletRequest, _portletResponse);
	}

	static {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	private static final Portlet _portlet = new PortletImpl() {

		@Override
		public PortletApp getPortletApp() {
			return new PortletAppImpl(StringPool.BLANK) {

				@Override
				public Set<String> getServletURLPatterns() {
					return Collections.singleton("/testPath/*");
				}

			};
		}

		@Override
		public String getPortletName() {
			return RandomTestUtil.randomString();
		}

		@Override
		public URLEncoder getURLEncoderInstance() {
			return null;
		}

	};

	private static final PortletContext _portletContext =
		new PortletContextImpl(_portlet, new MockServletContext());
	private static final PortletRequest _portletRequest =
		new TestPortletRequest(StringPool.SLASH, _portlet);

	private static final PortletResponse _portletResponse =
		new RenderResponseImpl() {

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

		};

	private static class TestPortletRequest extends RenderRequestImpl {

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

		private TestPortletRequest(String contextPath, Portlet portlet) {
			_contextPath = contextPath;
			_portlet = portlet;

			ReflectionTestUtil.setFieldValue(
				this, "_httpServletRequest", new MockHttpServletRequest());
		}

		private final String _contextPath;
		private final Portlet _portlet;

	}

	private static class TestRequestDispatcher implements RequestDispatcher {

		public void assertPropogatedInformation(
			HttpServletRequest httpServletRequest) {

			Assert.assertEquals(_pathInfo, httpServletRequest.getPathInfo());
			Assert.assertEquals(
				_queryString, httpServletRequest.getQueryString());
			Assert.assertEquals(
				_requestURI, httpServletRequest.getRequestURI());
			Assert.assertEquals(
				_servletPath, httpServletRequest.getServletPath());
		}

		@Override
		public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse) {

			assertPropogatedInformation((HttpServletRequest)servletRequest);
		}

		@Override
		public void include(
			ServletRequest servletRequest, ServletResponse servletResponse) {

			assertPropogatedInformation((HttpServletRequest)servletRequest);
		}

		private TestRequestDispatcher(
			String pathInfo, String queryString, String requestURI,
			String servletPath) {

			_pathInfo = pathInfo;
			_queryString = queryString;
			_requestURI = requestURI;
			_servletPath = servletPath;
		}

		private final String _pathInfo;
		private final String _queryString;
		private final String _requestURI;
		private final String _servletPath;

	}

}