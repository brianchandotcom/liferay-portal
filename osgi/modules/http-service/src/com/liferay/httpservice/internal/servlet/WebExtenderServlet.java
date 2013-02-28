/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.httpservice.internal.http.ExtendedHttpService;
import com.liferay.httpservice.internal.http.FilterTracker;
import com.liferay.httpservice.internal.http.HttpServiceFactory;
import com.liferay.httpservice.internal.http.HttpSupport;
import com.liferay.httpservice.internal.http.ServletTracker;
import com.liferay.osgi.bootstrap.ServicePropsKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.Filter;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebExtenderServlet extends PortletServlet implements StrutsAction {

	public static final String NAME = "Web Extender Servlet";

	public WebExtenderServlet(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void destroy() {
		_httpServletRegistration.unregister();
		_httpServiceRegistration.unregister();
		_filterTracker.close();
		_servletTracker.close();

		super.destroy();
	}

	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		service(request, response);

		return null;
	}

	public String execute(
			StrutsAction originalStrutsAction, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		service(request, response);

		return null;
	}

	public BundleContext getBundleContext() {
		return _bundleContext;
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(ServicePropsKeys.BEAN_ID, HttpServlet.class.getName());
		properties.put(ServicePropsKeys.ORIGINAL_BEAN, Boolean.TRUE);
		properties.put(ServicePropsKeys.VENDOR, ReleaseInfo.getVendor());

		_httpServletRegistration = _bundleContext.registerService(
			HttpServlet.class, this, properties);

		HttpSupport httpSupport = new HttpSupport(_bundleContext, this);

		HttpServiceFactory httpServiceFactory = new HttpServiceFactory(
			httpSupport);

		properties.put(ServicePropsKeys.BEAN_ID, HttpService.class.getName());

		_httpServiceRegistration = _bundleContext.registerService(
			new String[] {
				HttpService.class.getName(), ExtendedHttpService.class.getName()
			}, httpServiceFactory, properties);

		_filterTracker = new ServiceTracker<Filter, Filter>(
			_bundleContext, Filter.class, new FilterTracker(httpSupport));

		_filterTracker.open();

		_servletTracker = new ServiceTracker<Servlet, Servlet>(
			_bundleContext, Servlet.class, new ServletTracker(httpSupport));

		_servletTracker.open();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		_printDebugHeaderInfo(request);

		String portletId = (String)request.getAttribute(WebKeys.PORTLET_ID);
		String requestURI = request.getRequestURI();

		Portlet portlet = null;

		if (Validator.isNotNull(portletId)) {
			try {
				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				portlet = PortletLocalServiceUtil.getPortletById(rootPortletId);
			}
			catch (Exception e) {
				_log.debug(e, e);
			}
		}

		String servletContextName = null;

		if (portlet != null) {
			servletContextName =
				portlet.getPortletApp().getServletContextName();
		}
		else {
			if (requestURI != null) {
				String pathContext = PortalUtil.getPathContext();
				String requestURITemp = requestURI;

				if (Validator.isNotNull(pathContext) &&
					requestURITemp.startsWith(pathContext)) {

					requestURITemp = requestURITemp.substring(
						pathContext.length());
				}

				if (requestURITemp.startsWith(Portal.PATH_MODULE)) {
					requestURITemp = requestURITemp.substring(
						Portal.PATH_MODULE.length());
				}

				servletContextName = requestURITemp;

				if (servletContextName.startsWith(StringPool.SLASH)) {
					servletContextName = servletContextName.substring(1);
				}

				int index = servletContextName.indexOf(StringPool.SLASH);

				if (index != -1) {
					requestURITemp = servletContextName.substring(
						index, servletContextName.length());

					servletContextName = servletContextName.substring(0, index);
				}
			}
		}

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				new IllegalArgumentException(
					"No application mapped to this path"), request, response);

			return;
		}

		service(request, response, servletContext, portletId, requestURI);
	}

	protected void service(
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, String portletId, String requestURI)
		throws IOException, ServletException {

		BundleServletContext bundleServletContext =
			(BundleServletContext)servletContext;

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = bundleServletContext.getClassLoader();
		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(classLoader);

			RequestDispatcher requestDispatcher =
				bundleServletContext.getRequestDispatcher(requestURI);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);

				return;
			}

			if (requestURI.endsWith("/invoke") &&
				Validator.isNotNull(portletId)) {

				super.service(request, response);

				return;
			}

			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				new IllegalArgumentException(
					"No servlet or resource mapped to the path: " + requestURI),
					request, response);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private void _printDebugHeaderInfo(HttpServletRequest request) {
		if (!_log.isDebugEnabled()) {
			return;
		}

		Enumeration<String> enu = request.getHeaderNames();

		while (enu.hasMoreElements()) {
			Object name = enu.nextElement();

			_log.debug(name + " = " + request.getHeader(String.valueOf(name)));
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WebExtenderServlet.class);

	private BundleContext _bundleContext;
	private ServiceTracker<Filter, Filter> _filterTracker;
	private ServiceRegistration<?> _httpServiceRegistration;
	private ServiceRegistration<HttpServlet> _httpServletRegistration;
	private ServiceTracker<Servlet, Servlet> _servletTracker;

}