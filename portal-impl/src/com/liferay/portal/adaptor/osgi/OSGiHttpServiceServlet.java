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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
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
public class OSGiHttpServiceServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		BundleContext bundleContext = getBundleContext();

		if (bundleContext == null) {
			_log.warn("No framework provided!");

			return;
		}

		registerServletConfig(servletConfig);

		_serviceReference = bundleContext.getServiceReference(
			_HTTP_SERVICE_SERVLET_WRAPPER);

		if (_serviceReference == null) {
			_log.warn("No HttpService available!");

			return;
		}

		HttpServlet httpServlet = (HttpServlet)bundleContext.getService(
			_serviceReference);

		// Make sure that the implementation doesn't have to be initialised
		// all the time, but since the service might come and go at any time
		// we always have to assume that we need to re-initialise it here.

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
				new IllegalStateException("No framework provided!"),
				request, response);

			return;
		}

		String servletPath = request.getServletPath();

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
				String servletPathAttribute = (String)request.getAttribute(
					_INCLUDE_SERVLET_PATH_ATTRIBUTE);

				if (servletPathAttribute.contains(servletPath)) {
					servletPathAttribute = servletPathAttribute.substring(
						servletPath.length());
				}

				if (isExtensionMapping(servletPathAttribute)) {
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
				new IllegalStateException("No HttpService available!"),
				request, response);

			return;
		}

		HttpServlet httpServlet = (HttpServlet)bundleContext.getService(
			_serviceReference);

		// Make sure that the implementation doesn't have to be initialised
		// all the time, but since the service might come and go at any time
		// we always have to assume that we need to re-initialise it here.

		httpServlet.init(getServletConfig());

		httpServlet.service(request, response);
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

	protected void registerServletConfig(ServletConfig servletConfig) {
		Hashtable<String,Object> properties = new Hashtable<String, Object>();

		properties.put(Constants.SERVICE_VENDOR, ReleaseInfo.getVendor());

		properties.put(
			OSGiConstants.PORTAL_SERVICE_BEAN_NAME,
			ServletConfig.class.getName());

		_bundleContext.registerService(
			new String[] {ServletConfig.class.getName()}, servletConfig,
			properties);
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

		public IncludedExtensionMappingRequest(
			HttpServletRequest request, String servletPath) {

			super(request);

			_servletPath = servletPath;
		}

		@Override
		public Object getAttribute(String attributeName) {
			if (attributeName.equals(_INCLUDE_SERVLET_PATH_ATTRIBUTE)) {
				return StringPool.BLANK;
			}
			else if (attributeName.equals(_INCLUDE_PATH_INFO_ATTRIBUTE)) {
				String servletPathAttribute = (String)super.getAttribute(
					_INCLUDE_SERVLET_PATH_ATTRIBUTE);

				if (servletPathAttribute.contains(_servletPath)) {
					servletPathAttribute = servletPathAttribute.substring(
						_servletPath.length());
				}

				return servletPathAttribute;
			}

			return super.getAttribute(attributeName);
		}

		private String _servletPath;

	}

	private static final String _HTTP_SERVICE_SERVLET_WRAPPER =
		"com.liferay.osgi.http.HttpServiceServletWrapper";
	private static final String _INCLUDE_REQUEST_URI_ATTRIBUTE =
		"javax.servlet.include.request_uri";
	private static final String _INCLUDE_SERVLET_PATH_ATTRIBUTE =
		"javax.servlet.include.servlet_path";
	private static final String _INCLUDE_PATH_INFO_ATTRIBUTE =
		"javax.servlet.include.path_info";

	private static Log _log = LogFactoryUtil.getLog(
		OSGiHttpServiceServlet.class);

	private BundleContext _bundleContext;
	private ServiceReference _serviceReference;

}