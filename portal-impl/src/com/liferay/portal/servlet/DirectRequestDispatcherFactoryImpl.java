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

package com.liferay.portal.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactory;
import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class DirectRequestDispatcherFactoryImpl
	implements DirectRequestDispatcherFactory {

	@Override
	public RequestDispatcher getRequestDispatcher(
		ServletContext servletContext, String path) {

		RequestDispatcher requestDispatcher = doGetRequestDispatcher(
			servletContext, path);

		return new ClassLoaderRequestDispatcherWrapper(
			servletContext, requestDispatcher);
	}

	@Override
	public RequestDispatcher getRequestDispatcher(
		ServletRequest servletRequest, String path) {

		ServletContext servletContext =
			(ServletContext)servletRequest.getAttribute(WebKeys.CTX);

		if (servletContext == null) {
			return servletRequest.getRequestDispatcher(path);
		}

		return getRequestDispatcher(servletContext, path);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public RequestDispatcher getRequestDispatcher(
			ServletContext servletContext, RequestDispatcher requestDispatcher);

	}

	protected RequestDispatcher doGetRequestDispatcher(
		ServletContext servletContext, String path) {

		return new IndirectRequestDispatcher(
			_getetRequestDispatcher(servletContext, path));
	}

	private RequestDispatcher _getetRequestDispatcher(
		ServletContext servletContext, String path) {

		if (!PropsValues.DIRECT_SERVLET_CONTEXT_ENABLED) {
			return servletContext.getRequestDispatcher(path);
		}

		if ((path == null) || (path.length() == 0)) {
			return null;
		}

		if (path.charAt(0) != CharPool.SLASH) {
			throw new IllegalArgumentException(
				"Path " + path + " is not relative to context root");
		}

		String contextPath = servletContext.getContextPath();

		String fullPath = contextPath.concat(path);

		String queryString = null;

		int pos = fullPath.indexOf(CharPool.QUESTION);

		if (pos != -1) {
			queryString = fullPath.substring(pos + 1);

			fullPath = fullPath.substring(0, pos);
		}

		Servlet servlet = DirectServletRegistryUtil.getServlet(fullPath);

		if (servlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No servlet found for " + fullPath);
			}

			return new DirectServletPathRegisterDispatcher(
				path, servletContext.getRequestDispatcher(path));
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Servlet found for " + fullPath);
		}

		return new DirectRequestDispatcher(servlet, path, queryString);
	}

	private static final String _EQUINOX_REQUEST_CLASS_NAME =
		"org.eclipse.equinox.http.servlet.internal.servlet." +
			"HttpServletRequestWrapperImpl";

	private static final Log _log = LogFactoryUtil.getLog(
		DirectRequestDispatcherFactoryImpl.class);

	private static class DirectRequestDispatcherServletRequest
		extends HttpServletRequestWrapper {

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		private DirectRequestDispatcherServletRequest(
			ServletRequest servletRequest, ServletContext servletContext) {

			super((HttpServletRequest)servletRequest);

			_servletContext = servletContext;
		}

		private final ServletContext _servletContext;

	}

	/**
	 * See LPS-79937. We need to protect against redispatch from the module
	 * framework back to the portal, which means we have to unwrap the request.
	 */
	private static class IndirectRequestDispatcher
		implements RequestDispatcher {

		@Override
		public void forward(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {

			Class<?> clazz = servletRequest.getClass();

			if (_EQUINOX_REQUEST_CLASS_NAME.equals(clazz.getName())) {
				HttpServletRequestWrapper wrapper =
					(HttpServletRequestWrapper)servletRequest;

				servletRequest = new DirectRequestDispatcherServletRequest(
					wrapper.getRequest(), wrapper.getServletContext());
			}

			_requestDispatcher.forward(servletRequest, servletResponse);
		}

		@Override
		public void include(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {

			Class<?> clazz = servletRequest.getClass();

			if (_EQUINOX_REQUEST_CLASS_NAME.equals(clazz.getName())) {
				HttpServletRequestWrapper wrapper =
					(HttpServletRequestWrapper)servletRequest;

				servletRequest = new DirectRequestDispatcherServletRequest(
					wrapper.getRequest(), wrapper.getServletContext());
			}

			_requestDispatcher.include(servletRequest, servletResponse);
		}

		private IndirectRequestDispatcher(RequestDispatcher requestDispatcher) {
			_requestDispatcher = requestDispatcher;
		}

		private final RequestDispatcher _requestDispatcher;

	}

}