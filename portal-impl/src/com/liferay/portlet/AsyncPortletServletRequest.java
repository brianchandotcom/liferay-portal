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

import java.util.Collection;
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
		HttpServletRequest httpServletRequest) {

		while (httpServletRequest instanceof HttpServletRequestWrapper) {
			if (httpServletRequest instanceof AsyncPortletServletRequest) {
				return (AsyncPortletServletRequest)httpServletRequest;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)httpServletRequest;

			httpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		return null;
	}

	public AsyncPortletServletRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);

		_contextPath = httpServletRequest.getContextPath();
		_pathInfo = httpServletRequest.getPathInfo();
		_queryString = httpServletRequest.getQueryString();
		_requestURI = httpServletRequest.getRequestURI();
		_servletPath = httpServletRequest.getServletPath();
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
			if (!contextPath.isEmpty() && path.startsWith(contextPath)) {
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

	private static final Set<String> _portalServletURLPatterns =
		new HashSet<String>() {
			{
				ServletContext servletContext = ServletContextPool.get(
					PortalContextLoaderListener.getPortalServletContextName());

				if (servletContext == null) {
					throw new ExceptionInInitializerError(
						"Portal servlet context is not initialized");
				}

				Map<String, ? extends ServletRegistration>
					servletRegistrations =
						servletContext.getServletRegistrations();

				for (ServletRegistration servletRegistration :
						servletRegistrations.values()) {

					Collection<String> mappings =
						servletRegistration.getMappings();

					// LPS-86502

					if (mappings != null) {
						addAll(mappings);
					}
				}
			}
		};

	private String _contextPath;
	private String _pathInfo;
	private String _queryString;
	private String _requestURI;
	private String _servletPath;

}