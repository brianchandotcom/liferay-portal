/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpServletRequestWrapperImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpServletResponseWrapperImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.ResponseStateHandler;
import org.eclipse.equinox.http.servlet.internal.util.Params;

/**
 * @author Dante Wang
 */
public class LiferayDispatchTargets extends DispatchTargets {

	public LiferayDispatchTargets(
		LiferayContextController liferayContextController,
		EndpointRegistration<?> endpointRegistration,
		List<FilterRegistration> matchingFilterRegistrations,
		String servletName, String requestURI, String servletPath,
		String pathInfo, String queryString) {

		super(
			liferayContextController, endpointRegistration,
			matchingFilterRegistrations, servletName, requestURI, servletPath,
			pathInfo, queryString);

		_liferayContextController = liferayContextController;
		_endpointRegistration = endpointRegistration;
		_matchingFilterRegistrations = matchingFilterRegistrations;
		_servletName = servletName;
		_requestURI = requestURI;
		_servletPath = GetterUtil.getString(servletPath);
		_pathInfo = pathInfo;
		_queryString = queryString;

		_specialOverides = new ConcurrentHashMap();
	}

	public LiferayDispatchTargets(
		LiferayContextController liferayContextController,
		EndpointRegistration<?> endpointRegistration, String servletName,
		String requestURI, String servletPath, String pathInfo,
		String queryString) {

		this(
			liferayContextController, endpointRegistration,
			Collections.emptyList(), servletName, requestURI, servletPath,
			pathInfo, queryString);
	}

	@Override
	public void addRequestParameters(HttpServletRequest httpServletRequest) {
		if (_queryString == null) {
			_parameterMap = httpServletRequest.getParameterMap();
			_queryString = httpServletRequest.getQueryString();

			return;
		}

		Map<String, String[]> parsedParameterMap = _parseParameterMap(
			_queryString);

		Map<String, String[]> requestParameterMap =
			httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry :
				requestParameterMap.entrySet()) {

			parsedParameterMap.put(
				entry.getKey(),
				Params.append(
					parsedParameterMap.get(entry.getKey()), entry.getValue()));
		}

		_parameterMap = Collections.unmodifiableMap(parsedParameterMap);
	}

	@Override
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
				JavaConstants.JAKARTA_SERVLET_INCLUDE_CONTEXT_PATH,
				_liferayContextController.getFullContextPath());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_PATH_INFO, getPathInfo());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_QUERY_STRING,
				getQueryString());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_REQUEST_URI,
				getRequestURI());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_SERVLET_PATH,
				getServletPath());
		}
		else if (_dispatcherType == DispatcherType.FORWARD) {
			if (!originalHttpServletRequest.isAsyncStarted() &&
				!httpServletResponse.isCommitted()) {

				httpServletResponse.resetBuffer();
			}

			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_CONTEXT_PATH,
				originalHttpServletRequest.getContextPath());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_PATH_INFO,
				originalHttpServletRequest.getPathInfo());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_QUERY_STRING,
				originalHttpServletRequest.getQueryString());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_REQUEST_URI,
				originalHttpServletRequest.getRequestURI());
			requestAttributeSetter.setAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_SERVLET_PATH,
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
		}
		finally {
			httpServletRequestWrapperImpl.pop();

			requestAttributeSetter.close();
		}

		return true;
	}

	@Override
	public ContextController getContextController() {
		return _liferayContextController;
	}

	@Override
	public DispatcherType getDispatcherType() {
		return _dispatcherType;
	}

	@Override
	public List<FilterRegistration> getMatchingFilterRegistrations() {
		return _matchingFilterRegistrations;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public String getPathInfo() {
		return _pathInfo;
	}

	@Override
	public String getQueryString() {
		return _queryString;
	}

	@Override
	public String getRequestURI() {
		if (_requestURI == null) {
			return null;
		}

		return _liferayContextController.getFullContextPath() + _requestURI;
	}

	@Override
	public String getServletName() {
		return _servletName;
	}

	@Override
	public String getServletPath() {
		return _servletPath;
	}

	@Override
	public EndpointRegistration<?> getServletRegistration() {
		return _endpointRegistration;
	}

	@Override
	public Map<String, Object> getSpecialOverides() {
		return _specialOverides;
	}

	@Override
	public void setDispatcherType(DispatcherType dispatcherType) {
		_dispatcherType = dispatcherType;
	}

	@Override
	public String toString() {
		String value = _string;

		if (value == null) {
			String queryString = StringPool.BLANK;

			if (_queryString != null) {
				queryString = StringPool.QUESTION + _queryString;
			}

			value = StringBundler.concat(
				_SIMPLE_NAME, CharPool.OPEN_BRACKET,
				_liferayContextController.getFullContextPath(), _requestURI,
				queryString, CharPool.COMMA, CharPool.SPACE,
				_endpointRegistration, CharPool.CLOSE_BRACKET);

			_string = value;
		}

		return value;
	}

	private Map<String, String[]> _parseParameterMap(String queryString) {
		if ((queryString == null) || (queryString.length() == 0)) {
			return new HashMap<>();
		}

		try {
			Map<String, String[]> parameterMap = new LinkedHashMap<>();

			String[] parameters = StringUtil.split(
				queryString, CharPool.AMPERSAND);

			for (String parameter : parameters) {
				int index = parameter.indexOf(CharPool.EQUAL);

				String name = parameter;

				if (index > 0) {
					name = URLDecoder.decode(
						parameter.substring(0, index), StringPool.UTF8);
				}

				String[] values = parameterMap.get(name);

				if (values == null) {
					values = new String[0];
				}

				String value = null;

				if ((index > 0) && (parameter.length() > (index + 1))) {
					value = URLDecoder.decode(
						parameter.substring(index + 1), StringPool.UTF8);
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

	private static final String _SIMPLE_NAME =
		LiferayDispatchTargets.class.getSimpleName();

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayDispatchTargets.class);

	private DispatcherType _dispatcherType;
	private final EndpointRegistration<?> _endpointRegistration;
	private final LiferayContextController _liferayContextController;
	private final List<FilterRegistration> _matchingFilterRegistrations;
	private Map<String, String[]> _parameterMap;
	private final String _pathInfo;
	private String _queryString;
	private final String _requestURI;
	private final String _servletName;
	private final String _servletPath;
	private final Map<String, Object> _specialOverides;
	private String _string;

	private static class RequestAttributeSetter implements Closeable {

		public RequestAttributeSetter(ServletRequest servletRequest) {
			_servletRequest = servletRequest;
		}

		public void close() {
			for (Map.Entry<String, Object> oldValue : _oldValues.entrySet()) {
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
			_oldValues.put(name, _servletRequest.getAttribute(name));

			_servletRequest.setAttribute(name, value);
		}

		private final Map<String, Object> _oldValues = new HashMap<>();
		private final ServletRequest _servletRequest;

	}

}