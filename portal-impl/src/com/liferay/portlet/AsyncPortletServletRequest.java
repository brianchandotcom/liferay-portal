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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.spring.context.PortalContextLoaderListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Dante Wang
 */
public class AsyncPortletServletRequest extends HttpServletRequestWrapper {

	public static AsyncPortletServletRequest getAsyncPortletServletRequest(
		HttpServletRequest request) {

		while (request instanceof HttpServletRequestWrapper) {
			if (request instanceof AsyncPortletServletRequest) {
				return (AsyncPortletServletRequest)request;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)request;

			request =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		return null;
	}

	public AsyncPortletServletRequest(HttpServletRequest request) {
		super(request);

		_contextPath = super.getContextPath();
		_queryString = super.getQueryString();
		_requestURI = super.getRequestURI();
		_servletPath = super.getServletPath();
		_pathInfo = super.getPathInfo();

		if (_portalServletURLPatterns == null) {
			ServletContext servletContext = ServletContextPool.get(
				PortalContextLoaderListener.getPortalServletContextName());

			Map<String, ServletRegistration> servletRegistrationMap =
				(Map<String, ServletRegistration>)
					servletContext.getServletRegistrations();

			Set<String> servletURLPatterns = new HashSet<>();

			for (ServletRegistration servletRegistration :
					servletRegistrationMap.values()) {

				servletURLPatterns.addAll(servletRegistration.getMappings());
			}

			_portalServletURLPatterns = Collections.unmodifiableSet(
				servletURLPatterns);
		}
	}

	@Override
	public String getContextPath() {
		return _contextPath;
	}

	@Override
	public DispatcherType getDispatcherType() {
		return DispatcherType.ASYNC;
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
		return _requestURI;
	}

	@Override
	public String getServletPath() {
		return _servletPath;
	}

	public void setContextPath(String contextPath) {
		_contextPath = contextPath;
	}

	public void setPathInfo(String pathInfo) {
		_pathInfo = pathInfo;
	}

	public void setQueryString(String queryString) {
		_queryString = queryString;

		setRequest(
			DynamicServletRequest.addQueryString(
				(HttpServletRequest)getRequest(), queryString, true));
	}

	public void setRequestURI(String requestUri) {
		_requestURI = requestUri;
	}

	public void setServletPath(String servletPath) {
		_servletPath = servletPath;
	}

	public void update(String contextPath, String path) {
		String pathInfo = null;
		String queryString = null;
		String requestURI = null;
		String servletPath = null;

		if (path != null) {
			if ((contextPath.length() > 0) && path.startsWith(contextPath)) {
				path = path.substring(contextPath.length());
			}

			String pathNoQueryString = path;

			int pos = path.indexOf(CharPool.QUESTION);

			if (pos != -1) {
				pathNoQueryString = path.substring(0, pos);
				queryString = path.substring(pos + 1);
			}

			for (String urlPattern : _portalServletURLPatterns) {
				if (urlPattern.endsWith("/*")) {
					int length = urlPattern.length() - 2;

					if ((pathNoQueryString.length() > length) &&
						pathNoQueryString.regionMatches(
							0, urlPattern, 0, length) &&
						(pathNoQueryString.charAt(length) == CharPool.SLASH)) {

						pathInfo = pathNoQueryString.substring(length);
						servletPath = urlPattern.substring(0, length);

						break;
					}
				}
			}

			if (servletPath == null) {
				servletPath = pathNoQueryString;
			}

			if (contextPath.equals(StringPool.SLASH)) {
				requestURI = pathNoQueryString;
			}
			else {
				requestURI = contextPath + pathNoQueryString;
			}
		}

		setContextPath(contextPath);
		setPathInfo(pathInfo);
		setQueryString(queryString);
		setRequestURI(requestURI);
		setServletPath(servletPath);
	}

	private static volatile Set<String> _portalServletURLPatterns;

	private String _contextPath;
	private String _pathInfo;
	private String _queryString;
	private String _requestURI;
	private String _servletPath;

}