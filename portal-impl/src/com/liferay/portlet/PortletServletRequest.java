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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletInputStreamAdapter;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.internal.PortletRequestDispatcherImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Constructor;

import java.security.Principal;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ClientDataRequest;
import javax.portlet.EventRequest;
import javax.portlet.PortletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class PortletServletRequest extends HttpServletRequestWrapper {

	public PortletServletRequest(
		HttpServletRequest request, PortletRequest portletRequest,
		String pathInfo, String queryString, String requestURI,
		String servletPath, boolean named, boolean include) {

		super(request);

		_request = request;

		_portletRequest = portletRequest;

		_liferayPortletRequest = LiferayPortletUtil.getLiferayPortletRequest(
			_portletRequest);

		_pathInfo = pathInfo;
		_queryString = queryString;
		_requestURI = GetterUtil.getString(requestURI);
		_servletPath = GetterUtil.getString(servletPath);
		_named = named;
		_include = include;

		_lifecycle = _liferayPortletRequest.getLifecycle();

		if (Validator.isNotNull(_queryString)) {
			_liferayPortletRequest.setPortletRequestDispatcherRequest(request);
		}
	}

	@Override
	public Object getAttribute(String name) {
		if (_include || (name == null)) {
			return _request.getAttribute(name);
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_CONTEXT_PATH)) {
			if (_named) {
				return null;
			}

			return _portletRequest.getContextPath();
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_PATH_INFO)) {
			if (_named) {
				return null;
			}

			return _pathInfo;
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING)) {
			if (_named) {
				return null;
			}

			return _queryString;
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI)) {
			if (_named) {
				return null;
			}

			return _requestURI;
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_SERVLET_PATH)) {
			if (_named) {
				return null;
			}

			return _servletPath;
		}

		return _request.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return _portletRequest.getAttributeNames();
	}

	@Override
	public String getAuthType() {
		return _request.getAuthType();
	}

	@Override
	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getCharacterEncoding();
		}

		return null;
	}

	@Override
	public int getContentLength() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getContentLength();
		}

		return 0;
	}

	@Override
	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getContentType();
		}

		return null;
	}

	@Override
	public String getContextPath() {
		return _portletRequest.getContextPath();
	}

	@Override
	public Cookie[] getCookies() {
		return _request.getCookies();
	}

	@Override
	public long getDateHeader(String name) {
		String header = getHeader(name);

		if (header == null) {
			return -1;
		}

		return GetterUtil.getLongStrict(getHeader(name));
	}

	@Override
	public String getHeader(String name) {
		return _request.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return _request.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		return _request.getHeaders(name);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			ClientDataRequest clientDataRequest = _getClientDataRequest();

			InputStream portletInputStream =
				clientDataRequest.getPortletInputStream();

			ServletInputStream servletInputStream =
				new ServletInputStreamAdapter(portletInputStream);

			return servletInputStream;
		}

		return null;
	}

	@Override
	public int getIntHeader(String name) {
		String header = getHeader(name);

		if (header == null) {
			return -1;
		}

		return GetterUtil.getIntegerStrict(header);
	}

	@Override
	public String getLocalAddr() {
		return null;
	}

	@Override
	public Locale getLocale() {
		return _portletRequest.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return _portletRequest.getLocales();
	}

	@Override
	public String getLocalName() {
		return null;
	}

	@Override
	public int getLocalPort() {
		return 0;
	}

	@Override
	public String getMethod() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			ClientDataRequest clientDataRequest = _getClientDataRequest();

			return clientDataRequest.getMethod();
		}

		if (_lifecycle.equals(PortletRequest.HEADER_PHASE) ||
			_lifecycle.equals(PortletRequest.RENDER_PHASE)) {

			return HttpMethods.GET;
		}

		EventRequest eventRequest = _getEventRequest();

		return eventRequest.getMethod();
	}

	@Override
	public String getParameter(String name) {
		return _portletRequest.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _portletRequest.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return _portletRequest.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		return _portletRequest.getParameterValues(name);
	}

	@Override
	public String getPathInfo() {
		return _pathInfo;
	}

	@Override
	public String getPathTranslated() {
		ServletContext servletContext = _request.getServletContext();

		if ((_pathInfo != null) && (servletContext != null)) {
			return servletContext.getRealPath(_pathInfo);
		}

		return null;
	}

	@Override
	public String getProtocol() {
		return "HTTP/1.1";
	}

	@Override
	public String getQueryString() {
		return _queryString;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			ClientDataRequest clientDataRequest = _getClientDataRequest();

			return clientDataRequest.getReader();
		}

		return null;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String getRealPath(String path) {
		return null;
	}

	@Override
	public String getRemoteAddr() {
		return null;
	}

	@Override
	public String getRemoteHost() {
		return null;
	}

	@Override
	public int getRemotePort() {
		return 0;
	}

	@Override
	public String getRemoteUser() {
		return _portletRequest.getRemoteUser();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		RequestDispatcher requestDispatcher = _request.getRequestDispatcher(
			path);

		if (requestDispatcher != null) {
			requestDispatcher = new PortletRequestDispatcherImpl(
				requestDispatcher, path);
		}

		return requestDispatcher;
	}

	@Override
	public String getRequestedSessionId() {
		return _portletRequest.getRequestedSessionId();
	}

	@Override
	public String getRequestURI() {
		return _requestURI;
	}

	@Override
	public StringBuffer getRequestURL() {
		return null;
	}

	@Override
	public String getScheme() {
		return _portletRequest.getScheme();
	}

	@Override
	public String getServerName() {
		return _portletRequest.getServerName();
	}

	@Override
	public int getServerPort() {
		return _portletRequest.getServerPort();
	}

	@Override
	public String getServletPath() {
		return _servletPath;
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession session = _request.getSession(create);

		if (session == null) {
			return null;
		}

		session = new PortletServletSession(session, _liferayPortletRequest);

		if (ServerDetector.isJetty()) {
			try {
				session = wrapJettySession(session);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return session;
	}

	@Override
	public Principal getUserPrincipal() {
		return _portletRequest.getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return _request.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return _request.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return _portletRequest.isRequestedSessionIdValid();
	}

	@Override
	public boolean isSecure() {
		return _portletRequest.isSecure();
	}

	@Override
	public boolean isUserInRole(String role) {
		return _portletRequest.isUserInRole(role);
	}

	@Override
	public void removeAttribute(String name) {
		_portletRequest.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object obj) {
		_portletRequest.setAttribute(name, obj);
	}

	@Override
	public void setCharacterEncoding(String encoding)
		throws UnsupportedEncodingException {

		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			ClientDataRequest clientDataRequest = _getClientDataRequest();

			clientDataRequest.setCharacterEncoding(encoding);
		}
	}

	protected HttpSession wrapJettySession(HttpSession session)
		throws Exception {

		// This must be called through reflection because Resin tries to load
		// org/mortbay/jetty/servlet/AbstractSessionManager$SessionIf

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		Class<?> jettyHttpSessionWrapperClass = classLoader.loadClass(
			"com.liferay.portal.servlet.JettyHttpSessionWrapper");

		Constructor<?> constructor =
			jettyHttpSessionWrapperClass.getConstructor(
				new Class<?>[] {HttpSession.class});

		return (HttpSession)constructor.newInstance(new Object[] {session});
	}

	private ClientDataRequest _getClientDataRequest() {
		return (ClientDataRequest)_portletRequest;
	}

	private EventRequest _getEventRequest() {
		return (EventRequest)_portletRequest;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletServletRequest.class);

	private final boolean _include;
	private final String _lifecycle;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final boolean _named;
	private final String _pathInfo;
	private final PortletRequest _portletRequest;
	private final String _queryString;
	private final HttpServletRequest _request;
	private final String _requestURI;
	private final String _servletPath;

}