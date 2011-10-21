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

package com.liferay.portal.osgi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.service.OSGiServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;

/**
 * @author Raymond Augé
 */
public class OSGiServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		BundleContext bundleContext = getBundleContext();

		if (bundleContext == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No framework available");
			}

			return;
		}

		registerServletConfig(servletConfig);

		_serviceReference = bundleContext.getServiceReference(
			_HTTP_SERVICE_SERVLET_WRAPPER);

		if (_serviceReference == null) {
			_log.warn("No HTTP service available");

			return;
		}

		HttpServlet httpServlet = (HttpServlet)bundleContext.getService(
			_serviceReference);

		httpServlet.init(servletConfig);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		BundleContext bundleContext = getBundleContext();

		if (bundleContext == null) {
			PortalUtil.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				new IllegalStateException("No framework available"),
				request, response);

			return;
		}

		String pathInfo = request.getPathInfo();

		if (Validator.isNull(pathInfo) || pathInfo.equals(StringPool.SLASH)) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				new IllegalArgumentException("No path was available"), request,
				response);

			return;
		}

		if (isExtensionMapping(pathInfo)) {
			request = new ExtensionMappingRequest(request);
		}

		if (request.getAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI) != null) {

			String includePathInfo = (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO);

			if (Validator.isNull(includePathInfo)) {
				String includeServletPath = (String)request.getAttribute(
					JavaConstants.JAVAX_SERVLET_INCLUDE_SERVLET_PATH);

				String servletPath = request.getServletPath();

				if (includeServletPath.contains(servletPath)) {
					includeServletPath = includeServletPath.substring(
						servletPath.length());
				}

				if (isExtensionMapping(includeServletPath)) {
					request = new IncludedExtensionMappingRequest(
						request, servletPath);
				}
			}
		}

		_serviceReference = bundleContext.getServiceReference(
			_HTTP_SERVICE_SERVLET_WRAPPER);

		if (_serviceReference == null) {
			PortalUtil.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				new IllegalStateException("No HTTP service available"),
				request, response);

			return;
		}

		HttpServlet httpServlet = (HttpServlet)bundleContext.getService(
			_serviceReference);

		httpServlet.init(getServletConfig());

		httpServlet.service(request, response);
	}

	protected BundleContext getBundleContext() {
		if (_bundleContext != null) {
			return _bundleContext;
		}

		Framework framework = OSGiServiceUtil.getFramework();

		if (framework != null) {
			_bundleContext = framework.getBundleContext();
		}

		return _bundleContext;
	}

	protected boolean isExtensionMapping(String servletPath) {
		int pos = servletPath.lastIndexOf(StringPool.SLASH);

		if (pos != -1) {
			servletPath = servletPath.substring(pos + 1);
		}

		return servletPath.indexOf(StringPool.PERIOD) != -1;
	}

	protected void registerServletConfig(ServletConfig servletConfig) {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(
			OSGiConstants.PORTAL_SERVICE_BEAN_NAME,
			ServletConfig.class.getName());
		properties.put(Constants.SERVICE_VENDOR, ReleaseInfo.getVendor());

		_bundleContext.registerService(
			new String[] {ServletConfig.class.getName()}, servletConfig,
			properties);
	}

	private static final String _HTTP_SERVICE_SERVLET_WRAPPER =
		"com.liferay.osgi.http.HttpServiceServletWrapper";

	private static Log _log = LogFactoryUtil.getLog(OSGiServlet.class);

	private BundleContext _bundleContext;
	private ServiceReference<?> _serviceReference;

	private class ExtensionMappingRequest extends HttpServletRequestWrapper {

		public ExtensionMappingRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getServletPath() {
			return StringPool.BLANK;
		}

	}

	private class IncludedExtensionMappingRequest
		extends ExtensionMappingRequest {

		private String _servletPath;

		public IncludedExtensionMappingRequest(
			HttpServletRequest request, String servletPath) {

			super(request);

			_servletPath = servletPath;
		}

		@Override
		public Object getAttribute(String attributeName) {
			if (attributeName.equals(
					JavaConstants.JAVAX_SERVLET_INCLUDE_SERVLET_PATH)) {

				return StringPool.BLANK;
			}
			else if (attributeName.equals(
						JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO)) {

				String includeServletPath = (String)super.getAttribute(
					JavaConstants.JAVAX_SERVLET_INCLUDE_SERVLET_PATH);

				if (includeServletPath.contains(_servletPath)) {
					includeServletPath = includeServletPath.substring(
						_servletPath.length());
				}

				return includeServletPath;
			}

			return super.getAttribute(attributeName);
		}

	}

}