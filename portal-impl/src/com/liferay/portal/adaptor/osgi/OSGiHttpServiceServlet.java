/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.adaptor.osgi;

import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;

/**
 * @author Raymond Augé
 */
public class OSGiHttpServiceServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String pathInfo = request.getPathInfo();

		if (Validator.isNull(pathInfo) || pathInfo.equals(StringPool.SLASH)) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				new IllegalArgumentException("No path was provided!"), request,
				response);

			return;
		}

		if (isExtensionMapping(pathInfo)) {
			request = new ExtensionMappingRequest(request);
		}

		if (request.getAttribute(_INCLUDE_REQUEST_URI_ATTRIBUTE) != null) {
			String includePathInfo = (String)request.getAttribute(
				_INCLUDE_PATH_INFO_ATTRIBUTE);

			if (Validator.isNull(includePathInfo)) {
				String servletPath = (String)request.getAttribute(
					_INCLUDE_SERVLET_PATH_ATTRIBUTE);

				if (servletPath.contains(_OSGI_PATH)) {
					servletPath = servletPath.substring(_OSGI_PATH.length());
				}

				if (isExtensionMapping(servletPath)) {
					request = new IncludedExtensionMappingRequest(request);
				}
			}
		}

		try {
			BundleContext bundleContext = getBundleContext();

			if (bundleContext == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_SERVICE_UNAVAILABLE,
					new IllegalStateException("No framework provided!"),
					request, response);

				return;
			}

			_serviceReference = bundleContext.getServiceReference(
				_HTTP_SERVICE_SERVLET_WRAPPER);

			if (_serviceReference == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_SERVICE_UNAVAILABLE,
					new IllegalStateException("No HttpService available!"),
					request, response);

				return;
			}

			HttpServlet httpServlet = (HttpServlet)bundleContext.getService(
				_serviceReference);

			// Make sure that the implementation doesn't have to be initialized
			// all the time, but since the service might come and go at any time
			// we always have to assume that we need to re-initlaize it here.

			httpServlet.init(getServletConfig());

			httpServlet.service(request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, e, request, response);
		}
	}

	protected BundleContext getBundleContext() {
		if (_bundleContext == null) {
			try {
				Adaptor adaptor = AdaptorUtil.getAdaptor();

				if ((adaptor != null) && (adaptor instanceof OSGiAdaptor)) {
					OSGiAdaptor osgiAdaptor = (OSGiAdaptor)adaptor;

					Framework framework = osgiAdaptor.getFramework();

					_bundleContext = framework.getBundleContext();
				}
			}
			catch (Exception e) {
			}
		}

		return _bundleContext;
	}

	protected boolean isExtensionMapping(String servletPath) {
		String lastSegment = servletPath;

		int lastSlash = servletPath.lastIndexOf(StringPool.SLASH);

		if (lastSlash != -1) {
			lastSegment = servletPath.substring(lastSlash + 1);
		}

		return lastSegment.indexOf(StringPool.PERIOD) != -1;
	}

	static class ExtensionMappingRequest extends HttpServletRequestWrapper {

		public ExtensionMappingRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getServletPath() {
			return StringPool.BLANK;
		}

	}

	static class IncludedExtensionMappingRequest
		extends ExtensionMappingRequest {

		public IncludedExtensionMappingRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Object getAttribute(String attributeName) {
			if (attributeName.equals(_INCLUDE_SERVLET_PATH_ATTRIBUTE)) {
				return StringPool.BLANK;
			}
			else if (attributeName.equals(_INCLUDE_PATH_INFO_ATTRIBUTE)) {
				String servletPath = (String)super.getAttribute(
					_INCLUDE_SERVLET_PATH_ATTRIBUTE);

				if (servletPath.contains(_OSGI_PATH)) {
					servletPath = servletPath.substring(_OSGI_PATH.length());
				}

				return servletPath;
			}

			return super.getAttribute(attributeName);
		}

	}

	private static final String _HTTP_SERVICE_SERVLET_WRAPPER =
		"com.liferay.osgi.http.HttpServiceServletWrapper";
	private static final String _INCLUDE_REQUEST_URI_ATTRIBUTE =
		"javax.servlet.include.request_uri";
	private static final String _INCLUDE_SERVLET_PATH_ATTRIBUTE =
		"javax.servlet.include.servlet_path";
	private static final String _INCLUDE_PATH_INFO_ATTRIBUTE =
		"javax.servlet.include.path_info";
	private static final String _OSGI_PATH = "/osgi";

	private BundleContext _bundleContext;
	private ServiceReference _serviceReference;

}