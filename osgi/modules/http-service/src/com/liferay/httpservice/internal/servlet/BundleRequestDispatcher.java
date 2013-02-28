/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class BundleRequestDispatcher implements RequestDispatcher {

	public static final String INCLUDE_CONTEXT_PATH =
		"javax.servlet.include.context_path";
	public static final String INCLUDE_PATH_INFO =
		"javax.servlet.include.path_info";
	public static final String INCLUDE_QUERY_STRING =
		"javax.servlet.include.query_string";
	public static final String INCLUDE_REQUEST_URI =
		"javax.servlet.include.request_uri";
	public static final String INCLUDE_SERVLET_PATH =
		"javax.servlet.include.servlet_path";

	public BundleRequestDispatcher(
		String servletMapping, boolean extensionMapping, String requestURI,
		BundleServletContext bundleServletContext,
		BundleFilterChain bundleFilterChain) {

		_servletMapping = servletMapping;
		_extensionMapping = extensionMapping;
		_bundleFilterChain = bundleFilterChain;
		_bundleServletContext = bundleServletContext;

		_requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		_contextPath = _bundleServletContext.getContextPath();

		_pathInfo = null;
		_queryString = null;
		_servletPath = null;

		if (!_extensionMapping) {
			_servletPath = _servletMapping;
		}
		else {
			_servletPath = _requestURI;
		}

		if ((_servletPath != null) &&
			_requestURI.startsWith(_servletPath) &&
			(_requestURI.length() > _servletPath.length())) {

			_pathInfo = _requestURI.substring(_servletPath.length());
		}
	}

	public void doDispatch(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader pluginClassLoader = _bundleServletContext.getClassLoader();

		PACLPolicy paclPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		try {
			PACLPolicy pluginPACLPolicy = PACLPolicyManager.getPACLPolicy(
				pluginClassLoader);

			PortalSecurityManagerThreadLocal.setPACLPolicy(pluginPACLPolicy);

			ClassLoaderUtil.setContextClassLoader(pluginClassLoader);

			for (ServletRequestListener servletRequestListener :
					_bundleServletContext.getServletRequestListeners()) {

				servletRequestListener.requestInitialized(
					new ServletRequestEvent(_bundleServletContext, request));
			}

			_bundleFilterChain.doFilter(request, response);

			for (ServletRequestListener servletRequestListener :
					_bundleServletContext.getServletRequestListeners()) {

				servletRequestListener.requestDestroyed(
					new ServletRequestEvent(_bundleServletContext, request));
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);

			PortalSecurityManagerThreadLocal.setPACLPolicy(paclPolicy);
		}
	}

	public void forward(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		BundleServletRequest bundleServletRequest = new BundleServletRequest(
			(HttpServletRequest)request);

		doDispatch(bundleServletRequest, response);
	}

	public void include(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		BundleServletRequest bundleServletRequest = new BundleServletRequest(
			(HttpServletRequest)request);

		if (_contextPath != null) {
			bundleServletRequest.setAttribute(
				INCLUDE_CONTEXT_PATH, _contextPath);
		}

		if (_pathInfo != null) {
			bundleServletRequest.setAttribute(INCLUDE_PATH_INFO, _pathInfo);
		}

		if (_queryString != null) {
			bundleServletRequest.setAttribute(
				INCLUDE_QUERY_STRING, _queryString);
		}

		if (_requestURI != null) {
			bundleServletRequest.setAttribute(
				INCLUDE_REQUEST_URI,
				_bundleServletContext.getContextPath().concat(_requestURI));
		}

		if (_servletPath != null) {
			bundleServletRequest.setAttribute(
				INCLUDE_SERVLET_PATH, _servletPath);
		}

		doDispatch(bundleServletRequest, response);
	}

	public class BundleServletRequest extends HttpServletRequestWrapper {

		public BundleServletRequest(HttpServletRequest request) {
			super(request);

			_session = new BundleSession(request.getSession());
		}

		@Override
		public Object getAttribute(String name) {
			if ((name.equals(INCLUDE_SERVLET_PATH) ||
				 name.equals(WebKeys.INVOKER_FILTER_URI)) &&
				_attributes.containsKey(WebKeys.SERVLET_PATH)) {

				return _attributes.get(WebKeys.SERVLET_PATH);
			}

			if (ArrayUtil.contains(_MASKED_ATTRIBUTES, name)) {
				return _attributes.get(name);
			}

			return super.getAttribute(name);
		}

		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Enumeration getAttributeNames() {
			List<String> attributeNames = new UniqueList<String>();

			Enumeration<String> enu = super.getAttributeNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				attributeNames.add(name);
			}

			attributeNames.addAll(_attributes.keySet());

			return Collections.enumeration(attributeNames);
		}

		@Override
		public String getContextPath() {
			return _bundleServletContext.getContextPath();
		}

		@Override
		public String getPathInfo() {
			return _pathInfo;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			RequestDispatcher requestDispatcher =
				_bundleServletContext.getRequestDispatcher(path);

			if (requestDispatcher != null) {
				return requestDispatcher;
			}

			return super.getRequestDispatcher(path);
		}

		@Override
		public String getRequestURI() {
			return getContextPath().concat(_requestURI);
		}

		@Override
		public String getServletPath() {
			return _servletPath;
		}

		@Override
		public HttpSession getSession() {
			return _session;
		}

		@Override
		public HttpSession getSession(boolean create) {
			return _session;
		}

		@Override
		public void removeAttribute(String name) {
			Object oldValue = null;

			if (ArrayUtil.contains(_MASKED_ATTRIBUTES, name)) {
				oldValue = _attributes.remove(name);
			}
			else {
				oldValue = super.getAttribute(name);

				super.removeAttribute(name);
			}

			List<ServletRequestAttributeListener> listeners =
				_bundleServletContext.getServletRequestAttributeListeners();

			for (ServletRequestAttributeListener listener : listeners) {
				listener.attributeReplaced(
					new ServletRequestAttributeEvent(
						_bundleServletContext, this, name, oldValue));
			}
		}

		@Override
		public void setAttribute(String name, Object value) {
			Object oldValue = null;

			if (ArrayUtil.contains(_MASKED_ATTRIBUTES, name)) {
				oldValue = _attributes.put(name, value);
			}
			else {
				oldValue = super.getAttribute(name);

				super.setAttribute(name, value);
			}

			List<ServletRequestAttributeListener> listeners =
				_bundleServletContext.getServletRequestAttributeListeners();

			for (ServletRequestAttributeListener listener : listeners) {

				if (oldValue != null) {
					listener.attributeReplaced(
						new ServletRequestAttributeEvent(
							_bundleServletContext, this, name, oldValue));
				}
				else {
					listener.attributeAdded(
						new ServletRequestAttributeEvent(
							_bundleServletContext, this, name, oldValue));
				}
			}
		}

		private Map<String, Object> _attributes = new HashMap<String, Object>();
		private HttpSession _session;

	}

	public class BundleSession extends HttpSessionWrapper {

		public BundleSession(HttpSession session) {
			super(session);
		}

		@Override
		public ServletContext getServletContext() {
			return _bundleServletContext;
		}

	}

	private static final String[] _MASKED_ATTRIBUTES = new String[] {
		INCLUDE_CONTEXT_PATH, INCLUDE_PATH_INFO, INCLUDE_QUERY_STRING,
		INCLUDE_REQUEST_URI, INCLUDE_SERVLET_PATH, WebKeys.INVOKER_FILTER_URI,
		WebKeys.SERVLET_PATH
	};

	private BundleFilterChain _bundleFilterChain;
	private BundleServletContext _bundleServletContext;
	private String _contextPath;
	private boolean _extensionMapping;
	private String _pathInfo;
	private String _queryString;
	private String _requestURI;
	private String _servletMapping;
	private String _servletPath;

}