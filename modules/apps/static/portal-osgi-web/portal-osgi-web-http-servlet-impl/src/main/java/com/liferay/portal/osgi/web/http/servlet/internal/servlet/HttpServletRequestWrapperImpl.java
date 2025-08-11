/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.kernel.util.ArrayUtil;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.registration.ServletRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.RequestDispatcherAdaptor;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

/**
 * @author Dante Wang
 */
public class HttpServletRequestWrapperImpl extends HttpServletRequestWrapper {

	public static HttpServletRequestWrapperImpl findHttpRuntimeRequest(
		HttpServletRequest httpServletRequest) {

		while (httpServletRequest instanceof
					HttpServletRequestWrapper httpServletRequestWrapper) {

			if (httpServletRequestWrapper instanceof
					HttpServletRequestWrapperImpl
						httpServletRequestWrapperImpl) {

				return httpServletRequestWrapperImpl;
			}

			httpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		return null;
	}

	public static String getDispatchPathInfo(
		HttpServletRequest httpServletRequest) {

		if (httpServletRequest.getDispatcherType() == DispatcherType.INCLUDE) {
			return (String)httpServletRequest.getAttribute(
				"jakarta.servlet.include.path_info");
		}

		return httpServletRequest.getPathInfo();
	}

	public HttpServletRequestWrapperImpl(
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);

		_httpServletRequest = httpServletRequest;
	}

	@Override
	public Object getAttribute(String attributeName) {
		DispatchTargets currentDispatchTargets = _dispatchTargetsDeque.peek();

		DispatcherType dispatcherType =
			currentDispatchTargets.getDispatcherType();

		if ((dispatcherType != DispatcherType.ASYNC) &&
			(dispatcherType != DispatcherType.REQUEST) &&
			attributeName.startsWith("jakarta.servlet.")) {

			Map<String, Object> specialOverrides =
				currentDispatchTargets.getSpecialOverides();

			boolean hasServletName = false;

			if (currentDispatchTargets.getServletName() != null) {
				hasServletName = true;
			}

			boolean dispatcherAttribute = _dispatcherAttributes.contains(
				attributeName);

			if (dispatcherType == DispatcherType.ERROR) {
				if (dispatcherAttribute &&
					!attributeName.startsWith("javax.servlet.error.")) {

					return null;
				}
			}
			else if (dispatcherType == DispatcherType.INCLUDE) {
				if (hasServletName &&
					attributeName.startsWith("javax.servlet.include")) {

					return null;
				}

				if (dispatcherAttribute) {
					Object specialOveride = specialOverrides.get(attributeName);

					if (specialOveride == _NULL_PLACEHOLDER) {
						return null;
					}

					Object attributeValue = super.getAttribute(attributeName);

					if (attributeValue != null) {
						return attributeValue;
					}
				}

				if (attributeName.equals(
						"jakarta.servlet.include.context_path")) {

					ContextController contextController =
						currentDispatchTargets.getContextController();

					return contextController.getContextPath();
				}

				if (attributeName.equals("jakarta.servlet.include.path_info")) {
					return currentDispatchTargets.getPathInfo();
				}

				if (attributeName.equals(
						"jakarta.servlet.include.query_string")) {

					return currentDispatchTargets.getQueryString();
				}

				if (attributeName.equals(
						"jakarta.servlet.include.request_uri")) {

					return currentDispatchTargets.getRequestURI();
				}

				if (attributeName.equals(
						"jakarta.servlet.include.servlet_path")) {

					return currentDispatchTargets.getServletPath();
				}

				if (dispatcherAttribute) {
					return null;
				}
			}
			else if (dispatcherType == DispatcherType.FORWARD) {
				if (hasServletName &&
					attributeName.startsWith("javax.servlet.forward")) {

					return null;
				}

				if (dispatcherAttribute) {
					Object specialOveride = specialOverrides.get(attributeName);

					if (specialOveride == _NULL_PLACEHOLDER) {
						return null;
					}
				}

				if (attributeName.equals(
						"jakarta.servlet.forward.context_path")) {

					ContextController contextController =
						currentDispatchTargets.getContextController();

					return contextController.getContextPath();
				}

				DispatchTargets originalDispatchTargets =
					(DispatchTargets)_dispatchTargetsDeque.getLast();

				if (attributeName.equals("jakarta.servlet.forward.path_info")) {
					return originalDispatchTargets.getPathInfo();
				}

				if (attributeName.equals(
						"jakarta.servlet.forward.query_string")) {

					return originalDispatchTargets.getQueryString();
				}

				if (attributeName.equals(
						"jakarta.servlet.forward.request_uri")) {

					return originalDispatchTargets.getRequestURI();
				}

				if (attributeName.equals(
						"jakarta.servlet.forward.servlet_path")) {

					return originalDispatchTargets.getServletPath();
				}

				if (dispatcherAttribute) {
					return null;
				}
			}

			return _httpServletRequest.getAttribute(attributeName);
		}

		return _httpServletRequest.getAttribute(attributeName);
	}

	@Override
	public String getAuthType() {
		String authType = (String)getAttribute(
			"org.osgi.service.http.authentication.type");

		if (authType != null) {
			return authType;
		}

		return _httpServletRequest.getAuthType();
	}

	@Override
	public String getContextPath() {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		ContextController contextController =
			dispatchTargets.getContextController();

		return contextController.getFullContextPath();
	}

	@Override
	public DispatcherType getDispatcherType() {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		return dispatchTargets.getDispatcherType();
	}

	@Override
	public String getParameter(String name) {
		String[] values = getParameterValues(name);

		if (ArrayUtil.isEmpty(values)) {
			return null;
		}

		return values[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		return dispatchTargets.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Map<String, String[]> parameterMap = getParameterMap();

		return Collections.enumeration(parameterMap.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String[]> parameterMap = getParameterMap();

		return parameterMap.get(name);
	}

	@Override
	public String getPathInfo() {
		DispatchTargets currentDispatchTargets = _dispatchTargetsDeque.peek();

		if ((currentDispatchTargets.getServletName() == null) &&
			(currentDispatchTargets.getDispatcherType() !=
				DispatcherType.INCLUDE)) {

			return currentDispatchTargets.getPathInfo();
		}

		DispatchTargets originalDispatchTargets =
			_dispatchTargetsDeque.getLast();

		return originalDispatchTargets.getPathInfo();
	}

	@Override
	public String getQueryString() {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		if ((dispatchTargets.getServletName() == null) &&
			(dispatchTargets.getDispatcherType() != DispatcherType.INCLUDE)) {

			return dispatchTargets.getQueryString();
		}

		return _httpServletRequest.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		String remoteUser = (String)getAttribute(
			"org.osgi.service.http.authentication.remote.user");

		if (remoteUser != null) {
			return remoteUser;
		}

		return _httpServletRequest.getRemoteUser();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		ContextController contextController =
			dispatchTargets.getContextController();

		if (!path.startsWith("/")) {
			path = dispatchTargets.getServletPath() + "/" + path;
		}
		else {
			String fullContextPath = contextController.getFullContextPath();

			if (path.startsWith(fullContextPath)) {
				path = path.substring(fullContextPath.length());
			}
		}

		DispatchTargets requestedDispatchTargets =
			contextController.getDispatchTargets(path);

		if (requestedDispatchTargets == null) {
			return null;
		}

		return new RequestDispatcherAdaptor(requestedDispatchTargets, path);
	}

	@Override
	public String getRequestURI() {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		if ((dispatchTargets.getServletName() == null) &&
			(dispatchTargets.getDispatcherType() != DispatcherType.INCLUDE)) {

			return dispatchTargets.getRequestURI();
		}

		return _httpServletRequest.getRequestURI();
	}

	@Override
	public ServletContext getServletContext() {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		ServletRegistration servletRegistration =
			(ServletRegistration)dispatchTargets.getServletRegistration();

		return servletRegistration.getServletContext();
	}

	@Override
	public String getServletPath() {
		DispatchTargets currentDispatchTargets = _dispatchTargetsDeque.peek();

		if ((currentDispatchTargets.getServletName() == null) &&
			(currentDispatchTargets.getDispatcherType() !=
				DispatcherType.INCLUDE)) {

			String servletPath = currentDispatchTargets.getServletPath();

			if (servletPath.equals("/")) {
				return "";
			}

			return servletPath;
		}

		DispatchTargets originalDispatchTargets =
			_dispatchTargetsDeque.getLast();

		return originalDispatchTargets.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession httpSession = _httpServletRequest.getSession(create);

		if (httpSession != null) {
			DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

			ContextController contextController =
				dispatchTargets.getContextController();

			ServletRegistration servletRegistration =
				(ServletRegistration)dispatchTargets.getServletRegistration();

			Servlet servlet = servletRegistration.getT();

			ServletConfig servletConfig = servlet.getServletConfig();

			return contextController.getSessionAdaptor(
				httpSession, servletConfig.getServletContext());
		}

		return null;
	}

	public synchronized void pop() {
		if (_dispatchTargetsDeque.size() > 1) {
			_dispatchTargetsDeque.pop();
		}
	}

	public synchronized void push(DispatchTargets dispatchTargets) {
		dispatchTargets.addRequestParameters(_httpServletRequest);

		_dispatchTargetsDeque.push(dispatchTargets);
	}

	@Override
	public void removeAttribute(String name) {
		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		if (_dispatcherAttributes.contains(name)) {
			Map<String, Object> specialOverrides =
				dispatchTargets.getSpecialOverides();

			specialOverrides.remove(name);
		}

		_httpServletRequest.removeAttribute(name);

		ContextController contextController =
			dispatchTargets.getContextController();

		EventListeners eventListeners = contextController.getEventListeners();

		List<ServletRequestAttributeListener> servletRequestAttributeListeners =
			eventListeners.get(ServletRequestAttributeListener.class);

		if (!servletRequestAttributeListeners.isEmpty()) {
			ServletRegistration servletRegistration =
				(ServletRegistration)dispatchTargets.getServletRegistration();

			ServletRequestAttributeEvent servletRequestAttributeEvent =
				new ServletRequestAttributeEvent(
					servletRegistration.getServletContext(), this, name, null);

			for (ServletRequestAttributeListener
					servletRequestAttributeListener :
						servletRequestAttributeListeners) {

				servletRequestAttributeListener.attributeRemoved(
					servletRequestAttributeEvent);
			}
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		boolean added = false;

		if (_httpServletRequest.getAttribute(name) == null) {
			added = true;
		}

		DispatchTargets dispatchTargets = _dispatchTargetsDeque.peek();

		if ((value == null) && _dispatcherAttributes.contains(name)) {
			Map<String, Object> specialOverrides =
				dispatchTargets.getSpecialOverides();

			specialOverrides.put(name, _NULL_PLACEHOLDER);
		}

		_httpServletRequest.setAttribute(name, value);

		ContextController contextController =
			dispatchTargets.getContextController();

		EventListeners eventListeners = contextController.getEventListeners();

		List<ServletRequestAttributeListener> servletRequestAttributeListeners =
			eventListeners.get(ServletRequestAttributeListener.class);

		if (!servletRequestAttributeListeners.isEmpty()) {
			ServletRegistration servletRegistration =
				(ServletRegistration)dispatchTargets.getServletRegistration();

			ServletRequestAttributeEvent servletRequestAttributeEvent =
				new ServletRequestAttributeEvent(
					servletRegistration.getServletContext(), this, name, value);

			for (ServletRequestAttributeListener
					servletRequestAttributeListener :
						servletRequestAttributeListeners) {

				if (added) {
					servletRequestAttributeListener.attributeAdded(
						servletRequestAttributeEvent);
				}
				else {
					servletRequestAttributeListener.attributeReplaced(
						servletRequestAttributeEvent);
				}
			}
		}
	}

	private static final Object _NULL_PLACEHOLDER = new Object();

	private static final Set<String> _dispatcherAttributes = Set.of(
		"jakarta.servlet.error.exception",
		"jakarta.servlet.error.exception_type", "jakarta.servlet.error.message",
		"jakarta.servlet.error.request_uri",
		"jakarta.servlet.error.servlet_name",
		"jakarta.servlet.error.status_code",
		"jakarta.servlet.forward.context_path",
		"jakarta.servlet.forward.path_info",
		"jakarta.servlet.forward.query_string",
		"jakarta.servlet.forward.request_uri",
		"jakarta.servlet.forward.servlet_path",
		"jakarta.servlet.include.context_path",
		"jakarta.servlet.include.path_info",
		"jakarta.servlet.include.query_string",
		"jakarta.servlet.include.request_uri",
		"jakarta.servlet.include.servlet_path");

	private final Deque<DispatchTargets> _dispatchTargetsDeque =
		new LinkedList<>();
	private final HttpServletRequest _httpServletRequest;

}