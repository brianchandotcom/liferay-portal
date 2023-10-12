/*******************************************************************************
 * Copyright (c) 2014, 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.context;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import javax.servlet.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.*;
import org.eclipse.equinox.http.servlet.internal.util.Const;
import org.eclipse.equinox.http.servlet.internal.util.Params;

/**
 * @author Raymond Augé
 */
public class DispatchTargets {

	public DispatchTargets(
		ContextController contextController,
		EndpointRegistration<?> endpointRegistration, String servletName,
		String requestURI, String servletPath, String pathInfo, String queryString) {

		this(
			contextController, endpointRegistration,
			Collections.<FilterRegistration>emptyList(), servletName, requestURI,
			servletPath, pathInfo, queryString);
	}

	public DispatchTargets(
		ContextController contextController,
		EndpointRegistration<?> endpointRegistration,
		List<FilterRegistration> matchingFilterRegistrations, String servletName,
		String requestURI, String servletPath, String pathInfo, String queryString) {

		this.contextController = contextController;
		this.endpointRegistration = endpointRegistration;
		this.matchingFilterRegistrations = matchingFilterRegistrations;
		this.servletName = servletName;
		this.requestURI = requestURI;
		this.servletPath = (servletPath == null) ? Const.BLANK : servletPath;
		this.pathInfo = pathInfo;
		this.queryString = queryString;
	}

	public void addRequestParameters(HttpServletRequest request) {
		if (queryString == null) {
			parameterMap = request.getParameterMap();
			queryString = request.getQueryString();

			return;
		}

		Map<String, String[]> parameterMapCopy = queryStringToParameterMap(queryString);

		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String[] values = parameterMapCopy.get(entry.getKey());
			values = Params.append(values, entry.getValue());
			parameterMapCopy.put(entry.getKey(), values);
		}

		parameterMap = Collections.unmodifiableMap(parameterMapCopy);
	}

	public boolean doDispatch(
			HttpServletRequest originalRequest, HttpServletResponse response,
			String path, DispatcherType requestedDispatcherType)
		throws ServletException, IOException {

		setDispatcherType(requestedDispatcherType);

		RequestAttributeSetter setter = new RequestAttributeSetter(originalRequest);

		if (dispatcherType == DispatcherType.INCLUDE) {
			setter.setAttribute(RequestDispatcher.INCLUDE_CONTEXT_PATH, contextController.getFullContextPath());
			setter.setAttribute(RequestDispatcher.INCLUDE_PATH_INFO, getPathInfo());
			setter.setAttribute(RequestDispatcher.INCLUDE_QUERY_STRING, getQueryString());
			setter.setAttribute(RequestDispatcher.INCLUDE_REQUEST_URI, getRequestURI());
			setter.setAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH, getServletPath());
		}
		else if (dispatcherType == DispatcherType.FORWARD) {
			if (!originalRequest.isAsyncStarted() && !response.isCommitted()) {
				response.resetBuffer();
			}

			setter.setAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH, originalRequest.getContextPath());
			setter.setAttribute(RequestDispatcher.FORWARD_PATH_INFO, originalRequest.getPathInfo());
			setter.setAttribute(RequestDispatcher.FORWARD_QUERY_STRING, originalRequest.getQueryString());
			setter.setAttribute(RequestDispatcher.FORWARD_REQUEST_URI, originalRequest.getRequestURI());
			setter.setAttribute(RequestDispatcher.FORWARD_SERVLET_PATH, originalRequest.getServletPath());
		}

		HttpServletRequest request = originalRequest;
		HttpServletRequestWrapperImpl httpRuntimeRequest = HttpServletRequestWrapperImpl.findHttpRuntimeRequest(originalRequest);

		try {
			if (httpRuntimeRequest == null) {
				httpRuntimeRequest = new HttpServletRequestWrapperImpl(originalRequest);
				request = httpRuntimeRequest;
				response = new HttpServletResponseWrapperImpl(response);
			}

			httpRuntimeRequest.push(this);

			ResponseStateHandler responseStateHandler = new ResponseStateHandler(request, response, this);

			responseStateHandler.processRequest();

			if ((dispatcherType == DispatcherType.FORWARD) &&
				!response.isCommitted() && !request.isAsyncStarted()) {

				try {
					response.flushBuffer();
					response.getWriter().close();
				}
				catch (IllegalStateException ise1) {
					try {
						ServletOutputStream outputStream = response.getOutputStream();
						outputStream.close();
					}
					catch (IllegalStateException ise2) {
						// ignore
					}
					catch (IOException ioe) {
						// ignore
					}
				}
			}

			return true;
		}
		finally {
			httpRuntimeRequest.pop();

			setter.close();
		}
	}

	public ContextController getContextController() {
		return contextController;
	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public List<FilterRegistration> getMatchingFilterRegistrations() {
		return matchingFilterRegistrations;
	}

	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public String getQueryString() {
		return queryString;
	}

	public String getRequestURI() {
		if (requestURI == null) {
			return null;
		}
		return getContextController().getFullContextPath() + requestURI;
	}

	public String getServletName() {
		return servletName;
	}

	public String getServletPath() {
		return servletPath;
	}

	public EndpointRegistration<?> getServletRegistration() {
		return endpointRegistration;
	}

	public Map<String, Object> getSpecialOverides() {
		return specialOverides;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	@Override
	public String toString() {
		String value = string;

		if (value == null) {
			value = SIMPLE_NAME + '[' + contextController.getFullContextPath() + requestURI + (queryString != null ? '?' + queryString : "") + ", " + endpointRegistration.toString() + ']'; //$NON-NLS-1$

			string = value;
		}

		return value;
	}

	private static Map<String, String[]> queryStringToParameterMap(String queryString) {
		if ((queryString == null) || (queryString.length() == 0)) {
			return new HashMap<String, String[]>();
		}

		try {
			Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
			String[] parameters = queryString.split(Const.AMP);
			for (String parameter : parameters) {
				int index = parameter.indexOf('=');
				String name = (index > 0) ? URLDecoder.decode(parameter.substring(0, index), Const.UTF8) : parameter;
				String[] values = parameterMap.get(name);
				if (values == null) {
					values = new String[0];
				}
				String value = ((index > 0) && (parameter.length() > index + 1)) ? URLDecoder.decode(parameter.substring(index + 1), Const.UTF8) : null;
				values = Params.append(values, value);
				parameterMap.put(name, values);
			}
			return parameterMap;
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private static class RequestAttributeSetter implements Closeable {

		private final ServletRequest servletRequest;
		private final Map<String, Object> oldValues = new HashMap<String, Object>();

		public RequestAttributeSetter(ServletRequest servletRequest) {
			this.servletRequest = servletRequest;
		}

		public void setAttribute(String name, Object value) {
			oldValues.put(name, servletRequest.getAttribute(name));

			servletRequest.setAttribute(name, value);
		}

		public void close() {
			for (Map.Entry<String, Object> oldValue : oldValues.entrySet()) {
				if (oldValue.getValue() == null) {
					servletRequest.removeAttribute(oldValue.getKey());
				}
				else {
					servletRequest.setAttribute(oldValue.getKey(), oldValue.getValue());
				}
			}
		}
	}

	private static final String SIMPLE_NAME =
		DispatchTargets.class.getSimpleName();

	private final ContextController contextController;
	private DispatcherType dispatcherType;
	private final EndpointRegistration<?> endpointRegistration;
	private final List<FilterRegistration> matchingFilterRegistrations;
	private final String pathInfo;
	private Map<String, String[]> parameterMap;
	private String queryString;
	private final String requestURI;
	private final String servletPath;
	private final String servletName;
	private final Map<String, Object> specialOverides = new ConcurrentHashMap<String, Object>();
	private String string;

}