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

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.EndpointRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.FilterRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.HttpServletRequestWrapperImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.HttpServletResponseWrapperImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.ResponseStateHandler;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Params;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.net.URLDecoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class DispatchTargets {

	public DispatchTargets(
		ContextController contextController,
		EndpointRegistration<?> endpointRegistration,
		List<FilterRegistration> matchingFilterRegistrations,
		String servletName, String requestURI, String servletPath,
		String pathInfo, String queryString) {

		_contextController = contextController;
		_endpointRegistration = endpointRegistration;
		_matchingFilterRegistrations = matchingFilterRegistrations;
		_servletName = servletName;
		_requestURI = requestURI;
		_servletPath = (servletPath == null) ? Const.BLANK : servletPath;
		_pathInfo = pathInfo;
		_queryString = queryString;
	}

	public DispatchTargets(
		ContextController contextController,
		EndpointRegistration<?> endpointRegistration, String servletName,
		String requestURI, String servletPath, String pathInfo,
		String queryString) {

		this(
			contextController, endpointRegistration, Collections.emptyList(),
			servletName, requestURI, servletPath, pathInfo, queryString);
	}

	public void addRequestParameters(HttpServletRequest httpServletRequest) {
		if (_queryString == null) {
			_parameterMap = httpServletRequest.getParameterMap();
			_queryString = httpServletRequest.getQueryString();

			return;
		}

		Map<String, String[]> parameterMapCopy = _queryStringToParameterMap(
			_queryString);

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] values = Params.append(
				parameterMapCopy.get(entry.getKey()), entry.getValue());

			parameterMapCopy.put(entry.getKey(), values);
		}

		_parameterMap = Collections.unmodifiableMap(parameterMapCopy);
	}

	public boolean doDispatch(
			HttpServletRequest originalHttpServletRequest,
			HttpServletResponse httpServletResponse, String path,
			DispatcherType requestedDispatcherType)
		throws IOException, ServletException {

		setDispatcherType(requestedDispatcherType);

		RequestAttributeSetter requestAttributeSetter =
			new RequestAttributeSetter(originalHttpServletRequest);

		if (_dispatcherType == DispatcherType.INCLUDE) {
			requestAttributeSetter.setAttribute(
				RequestDispatcher.INCLUDE_CONTEXT_PATH,
				_contextController.getFullContextPath());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.INCLUDE_PATH_INFO, getPathInfo());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.INCLUDE_QUERY_STRING, getQueryString());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.INCLUDE_REQUEST_URI, getRequestURI());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH, getServletPath());
		}
		else if (_dispatcherType == DispatcherType.FORWARD) {
			if (!originalHttpServletRequest.isAsyncStarted() &&
				!httpServletResponse.isCommitted()) {

				httpServletResponse.resetBuffer();
			}

			requestAttributeSetter.setAttribute(
				RequestDispatcher.FORWARD_CONTEXT_PATH,
				originalHttpServletRequest.getContextPath());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.FORWARD_PATH_INFO,
				originalHttpServletRequest.getPathInfo());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.FORWARD_QUERY_STRING,
				originalHttpServletRequest.getQueryString());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.FORWARD_REQUEST_URI,
				originalHttpServletRequest.getRequestURI());
			requestAttributeSetter.setAttribute(
				RequestDispatcher.FORWARD_SERVLET_PATH,
				originalHttpServletRequest.getServletPath());
		}

		HttpServletRequest httpServletRequest = originalHttpServletRequest;

		HttpServletRequestWrapperImpl httpServletRequestWrapperImpl =
			HttpServletRequestWrapperImpl.findHttpRuntimeRequest(
				originalHttpServletRequest);

		try {
			if (httpServletRequestWrapperImpl == null) {
				httpServletRequestWrapperImpl =
					new HttpServletRequestWrapperImpl(
						originalHttpServletRequest);

				httpServletRequest = httpServletRequestWrapperImpl;

				httpServletResponse = new HttpServletResponseWrapperImpl(
					httpServletResponse);
			}

			httpServletRequestWrapperImpl.push(this);

			ResponseStateHandler responseStateHandler =
				new ResponseStateHandler(
					httpServletRequest, httpServletResponse, this);

			responseStateHandler.processRequest();

			if ((_dispatcherType == DispatcherType.FORWARD) &&
				!httpServletResponse.isCommitted() &&
				!httpServletRequest.isAsyncStarted()) {

				try {
					httpServletResponse.flushBuffer();

					Writer writer = httpServletResponse.getWriter();

					writer.close();
				}
				catch (IllegalStateException illegalStateException) {
					if (_log.isDebugEnabled()) {
						_log.debug(illegalStateException);
					}

					try {
						ServletOutputStream servletOutputStream =
							httpServletResponse.getOutputStream();

						servletOutputStream.close();
					}
					catch (IllegalStateException | IOException exception) {
						if (_log.isDebugEnabled()) {
							_log.debug(exception);
						}
					}
				}
			}

			return true;
		}
		finally {
			httpServletRequestWrapperImpl.pop();

			requestAttributeSetter.close();
		}
	}

	public ContextController getContextController() {
		return _contextController;
	}

	public DispatcherType getDispatcherType() {
		return _dispatcherType;
	}

	public List<FilterRegistration> getMatchingFilterRegistrations() {
		return _matchingFilterRegistrations;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public String getPathInfo() {
		return _pathInfo;
	}

	public String getQueryString() {
		return _queryString;
	}

	public String getRequestURI() {
		if (_requestURI == null) {
			return null;
		}

		return getContextController().getFullContextPath() + _requestURI;
	}

	public String getServletName() {
		return _servletName;
	}

	public String getServletPath() {
		return _servletPath;
	}

	public EndpointRegistration<?> getServletRegistration() {
		return _endpointRegistration;
	}

	public Map<String, Object> getSpecialOverides() {
		return _specialOveridesMap;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		_dispatcherType = dispatcherType;
	}

	@Override
	public String toString() {
		String value = _string;

		if (value == null) {
			value = StringBundler.concat(
				DispatchTargets.class.getSimpleName(), '[',
				_contextController.getFullContextPath(), _requestURI,
				(_queryString != null) ? '?' + _queryString : "", ", ",
				_endpointRegistration, ']');

			_string = value;
		}

		return value;
	}

	private Map<String, String[]> _queryStringToParameterMap(
		String queryString) {

		if ((queryString == null) || (queryString.length() == 0)) {
			return new HashMap<>();
		}

		try {
			Map<String, String[]> parameterMap = new LinkedHashMap<>();

			for (String parameter : queryString.split(Const.AMP)) {
				int index = parameter.indexOf('=');

				String name = null;

				if (index > 0) {
					name = URLDecoder.decode(
						parameter.substring(0, index), Const.UTF8);
				}

				String[] values = parameterMap.get(name);

				if (values == null) {
					values = new String[0];
				}

				String value = null;

				if ((index > 0) && (parameter.length() > (index + 1))) {
					value = URLDecoder.decode(
						parameter.substring(index + 1), Const.UTF8);
				}

				values = Params.append(values, value);

				parameterMap.put(name, values);
			}

			return parameterMap;
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTargets.class.getName());

	private final ContextController _contextController;
	private DispatcherType _dispatcherType;
	private final EndpointRegistration<?> _endpointRegistration;
	private final List<FilterRegistration> _matchingFilterRegistrations;
	private Map<String, String[]> _parameterMap;
	private final String _pathInfo;
	private String _queryString;
	private final String _requestURI;
	private final String _servletName;
	private final String _servletPath;
	private final Map<String, Object> _specialOveridesMap =
		new ConcurrentHashMap<>();
	private String _string;

	private static class RequestAttributeSetter implements Closeable {

		public RequestAttributeSetter(ServletRequest servletRequest) {
			_servletRequest = servletRequest;
		}

		@Override
		public void close() {
			for (Map.Entry<String, Object> oldValue :
					_oldValuesMap.entrySet()) {

				if (oldValue.getValue() == null) {
					_servletRequest.removeAttribute(oldValue.getKey());
				}
				else {
					_servletRequest.setAttribute(
						oldValue.getKey(), oldValue.getValue());
				}
			}
		}

		public void setAttribute(String name, Object value) {
			_oldValuesMap.put(name, _servletRequest.getAttribute(name));

			_servletRequest.setAttribute(name, value);
		}

		private final Map<String, Object> _oldValuesMap = new HashMap<>();
		private final ServletRequest _servletRequest;

	}

}