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

package com.liferay.productivitycenter.util;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.net.URL;

import java.util.Dictionary;

import javax.servlet.Servlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Adolfo Pérez
 */
public class BundlerServletUtil {

	public static ServiceRegistration<ServletContextHelper> createContext(
		String servletContextName, Bundle bundle) {

		ServletContextHelper servletContextHelper =
			new ServletContextHelper(bundle) {

				@Override
				public URL getResource(String name) {
					return super.getResource("/META-INF/resources" + name);
				}

			};

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/" + servletContextName);

		return bundleContext.registerService(
			ServletContextHelper.class, servletContextHelper, properties);
	}

	public static ServiceRegistration<Servlet> createJspServlet(
		String servletContextName, BundleContext bundleContext) {

		Servlet servlet = null;

		try {
			Class<?> clazz = Class.forName(
				"com.liferay.portal.servlet.jsp.compiler.JspServlet");

			servlet = (Servlet)clazz.newInstance();
		}
		catch (Exception e) {
			return null;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME, "jsp");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "*.jsp");

		return bundleContext.registerService(
			Servlet.class, servlet, properties);
	}

}