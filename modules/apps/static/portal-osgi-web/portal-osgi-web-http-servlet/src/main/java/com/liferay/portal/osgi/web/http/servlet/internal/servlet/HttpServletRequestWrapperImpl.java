/*******************************************************************************
 * Copyright (c) 2005, 2016 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.context.DispatchTargets;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.EndpointRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;

import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.osgi.service.http.HttpContext;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class HttpServletRequestWrapperImpl extends HttpServletRequestWrapper {

	public static HttpServletRequestWrapperImpl findHttpRuntimeRequest(
		HttpServletRequest httpServletRequest) {

		while (httpServletRequest instanceof HttpServletRequestWrapper) {
			if (httpServletRequest instanceof HttpServletRequestWrapperImpl) {
				return (HttpServletRequestWrapperImpl)httpServletRequest;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)httpServletRequest;

			httpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		return null;
	}

	public static String getDispatchPathInfo(
		HttpServletRequest httpServletRequest) {

		if (httpServletRequest.getDispatcherType() == DispatcherType.INCLUDE) {
			return (String)httpServletRequest.getAttribute(
				RequestDispatcher.INCLUDE_PATH_INFO);
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
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		DispatcherType dispatcherType = dispatchTargets.getDispatcherType();

		if ((dispatcherType == DispatcherType.ASYNC) ||
			(dispatcherType == DispatcherType.REQUEST) ||
			!attributeName.startsWith("javax.servlet.")) {

			return _httpServletRequest.getAttribute(attributeName);
		}

		Map<String, Object> specialOverridesMap =
			dispatchTargets.getSpecialOverides();

		boolean hasServletName = false;

		if (dispatchTargets.getServletName() != null) {
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
				if (specialOverridesMap.get(attributeName) ==
						_NULL_PLACEHOLDER) {

					return null;
				}

				Object attributeValue = super.getAttribute(attributeName);

				if (attributeValue != null) {
					return attributeValue;
				}
			}

			if (attributeName.equals(RequestDispatcher.INCLUDE_CONTEXT_PATH)) {
				ContextController contextController =
					dispatchTargets.getContextController();

				return contextController.getContextPath();
			}
			else if (attributeName.equals(
						RequestDispatcher.INCLUDE_PATH_INFO)) {

				return dispatchTargets.getPathInfo();
			}
			else if (attributeName.equals(
						RequestDispatcher.INCLUDE_QUERY_STRING)) {

				return dispatchTargets.getQueryString();
			}
			else if (attributeName.equals(
						RequestDispatcher.INCLUDE_REQUEST_URI)) {

				return dispatchTargets.getRequestURI();
			}
			else if (attributeName.equals(
						RequestDispatcher.INCLUDE_SERVLET_PATH)) {

				return dispatchTargets.getServletPath();
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

			if (dispatcherAttribute &&
				(specialOverridesMap.get(attributeName) == _NULL_PLACEHOLDER)) {

				return null;
			}

			DispatchTargets lastDispatchTargets = _dispatchTargets.getLast();

			if (attributeName.equals(RequestDispatcher.FORWARD_CONTEXT_PATH)) {
				ContextController contextController =
					lastDispatchTargets.getContextController();

				return contextController.getContextPath();
			}
			else if (attributeName.equals(
						RequestDispatcher.FORWARD_PATH_INFO)) {

				return lastDispatchTargets.getPathInfo();
			}
			else if (attributeName.equals(
						RequestDispatcher.FORWARD_QUERY_STRING)) {

				return lastDispatchTargets.getQueryString();
			}
			else if (attributeName.equals(
						RequestDispatcher.FORWARD_REQUEST_URI)) {

				return lastDispatchTargets.getRequestURI();
			}
			else if (attributeName.equals(
						RequestDispatcher.FORWARD_SERVLET_PATH)) {

				return lastDispatchTargets.getServletPath();
			}

			if (dispatcherAttribute) {
				return null;
			}
		}

		return _httpServletRequest.getAttribute(attributeName);
	}

	@Override
	public String getAuthType() {
		String authType = (String)getAttribute(HttpContext.AUTHENTICATION_TYPE);

		if (authType != null) {
			return authType;
		}

		return _httpServletRequest.getAuthType();
	}

	@Override
	public String getContextPath() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		ContextController contextController =
			dispatchTargets.getContextController();

		return contextController.getFullContextPath();
	}

	@Override
	public DispatcherType getDispatcherType() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		return dispatchTargets.getDispatcherType();
	}

	@Override
	public String getParameter(String name) {
		String[] values = getParameterValues(name);

		if ((values == null) || (values.length == 0)) {
			return null;
		}

		return values[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		return dispatchTargets.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		return getParameterMap().get(name);
	}

	@Override
	public String getPathInfo() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		if ((dispatchTargets.getServletName() != null) ||
			(dispatchTargets.getDispatcherType() == DispatcherType.INCLUDE)) {

			DispatchTargets lastDispatchTargets = _dispatchTargets.getLast();

			return lastDispatchTargets.getPathInfo();
		}

		return dispatchTargets.getPathInfo();
	}

	@Override
	public String getQueryString() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		if ((dispatchTargets.getServletName() != null) ||
			(dispatchTargets.getDispatcherType() == DispatcherType.INCLUDE)) {

			return _httpServletRequest.getQueryString();
		}

		return dispatchTargets.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		String remoteUser = (String)getAttribute(HttpContext.REMOTE_USER);

		if (remoteUser != null) {
			return remoteUser;
		}

		return _httpServletRequest.getRemoteUser();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		ContextController contextController =
			dispatchTargets.getContextController();

		if (!path.startsWith(Const.SLASH)) {
			path = dispatchTargets.getServletPath() + Const.SLASH + path;
		}
		else if (path.startsWith(contextController.getFullContextPath())) {
			String fullContextPath = contextController.getFullContextPath();

			path = path.substring(fullContextPath.length());
		}

		DispatchTargets requestedDispatchTargets =
			contextController.getDispatchTargets(path, null);

		if (requestedDispatchTargets == null) {
			return null;
		}

		return new RequestDispatcherAdaptor(requestedDispatchTargets, path);
	}

	@Override
	public String getRequestURI() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		if ((dispatchTargets.getServletName() != null) ||
			(dispatchTargets.getDispatcherType() == DispatcherType.INCLUDE)) {

			return _httpServletRequest.getRequestURI();
		}

		return dispatchTargets.getRequestURI();
	}

	@Override
	public ServletContext getServletContext() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		EndpointRegistration<?> endpointRegistration =
			dispatchTargets.getServletRegistration();

		return endpointRegistration.getServletContext();
	}

	@Override
	public String getServletPath() {
		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		if ((dispatchTargets.getServletName() != null) ||
			(dispatchTargets.getDispatcherType() == DispatcherType.INCLUDE)) {

			DispatchTargets lastDispatchTargets = _dispatchTargets.getLast();

			return lastDispatchTargets.getServletPath();
		}

		if (Objects.equals(dispatchTargets.getServletPath(), Const.SLASH)) {
			return Const.BLANK;
		}

		return dispatchTargets.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		HttpSession httpSession = _httpServletRequest.getSession(create);

		if (httpSession != null) {
			DispatchTargets dispatchTargets = _dispatchTargets.peek();

			ContextController contextController =
				dispatchTargets.getContextController();

			EndpointRegistration<?> endpointRegistration =
				dispatchTargets.getServletRegistration();

			Servlet servlet = endpointRegistration.getT();

			ServletConfig servletConfig = servlet.getServletConfig();

			return contextController.getSessionAdaptor(
				httpSession, servletConfig.getServletContext());
		}

		return null;
	}

	public synchronized void pop() {
		if (_dispatchTargets.size() > 1) {
			_dispatchTargets.pop();
		}
	}

	public synchronized void push(DispatchTargets toPush) {
		toPush.addRequestParameters(_httpServletRequest);

		_dispatchTargets.push(toPush);
	}

	@Override
	public void removeAttribute(String name) {
		if (_dispatcherAttributes.contains(name)) {
			DispatchTargets dispatchTargets = _dispatchTargets.peek();

			Map<String, Object> specialOverridesMap =
				dispatchTargets.getSpecialOverides();

			specialOverridesMap.remove(name);
		}

		_httpServletRequest.removeAttribute(name);

		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		ContextController contextController =
			dispatchTargets.getContextController();

		EventListeners eventListeners = contextController.getEventListeners();

		List<ServletRequestAttributeListener> servletRequestAttributeListeners =
			eventListeners.get(ServletRequestAttributeListener.class);

		if (servletRequestAttributeListeners.isEmpty()) {
			return;
		}

		EndpointRegistration<?> endpointRegistration =
			dispatchTargets.getServletRegistration();

		ServletRequestAttributeEvent servletRequestAttributeEvent =
			new ServletRequestAttributeEvent(
				endpointRegistration.getServletContext(), this, name, null);

		for (ServletRequestAttributeListener servletRequestAttributeListener :
				servletRequestAttributeListeners) {

			servletRequestAttributeListener.attributeRemoved(
				servletRequestAttributeEvent);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		boolean added = false;

		if (_httpServletRequest.getAttribute(name) == null) {
			added = true;
		}

		if ((value == null) && _dispatcherAttributes.contains(name)) {
			DispatchTargets current = _dispatchTargets.peek();

			current.getSpecialOverides(
			).put(
				name, _NULL_PLACEHOLDER
			);
		}

		_httpServletRequest.setAttribute(name, value);

		DispatchTargets dispatchTargets = _dispatchTargets.peek();

		ContextController contextController =
			dispatchTargets.getContextController();

		EventListeners eventListeners = contextController.getEventListeners();

		List<ServletRequestAttributeListener> servletRequestAttributeListeners =
			eventListeners.get(ServletRequestAttributeListener.class);

		if (servletRequestAttributeListeners.isEmpty()) {
			return;
		}

		EndpointRegistration<?> endpointRegistration =
			dispatchTargets.getServletRegistration();

		ServletRequestAttributeEvent servletRequestAttributeEvent =
			new ServletRequestAttributeEvent(
				endpointRegistration.getServletContext(), this, name, value);

		for (ServletRequestAttributeListener servletRequestAttributeListener :
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

	private static final Object _NULL_PLACEHOLDER = new Object();

	private static final Set<String> _dispatcherAttributes =
		new HashSet<String>() {
			{
				add(RequestDispatcher.ERROR_EXCEPTION);
				add(RequestDispatcher.ERROR_EXCEPTION_TYPE);
				add(RequestDispatcher.ERROR_MESSAGE);
				add(RequestDispatcher.ERROR_REQUEST_URI);
				add(RequestDispatcher.ERROR_SERVLET_NAME);
				add(RequestDispatcher.ERROR_STATUS_CODE);
				add(RequestDispatcher.FORWARD_CONTEXT_PATH);
				add(RequestDispatcher.FORWARD_PATH_INFO);
				add(RequestDispatcher.FORWARD_QUERY_STRING);
				add(RequestDispatcher.FORWARD_REQUEST_URI);
				add(RequestDispatcher.FORWARD_SERVLET_PATH);
				add(RequestDispatcher.INCLUDE_CONTEXT_PATH);
				add(RequestDispatcher.INCLUDE_PATH_INFO);
				add(RequestDispatcher.INCLUDE_QUERY_STRING);
				add(RequestDispatcher.INCLUDE_REQUEST_URI);
				add(RequestDispatcher.INCLUDE_SERVLET_PATH);
			}
		};

	private final Deque<DispatchTargets> _dispatchTargets = new LinkedList<>();
	private final HttpServletRequest _httpServletRequest;

}