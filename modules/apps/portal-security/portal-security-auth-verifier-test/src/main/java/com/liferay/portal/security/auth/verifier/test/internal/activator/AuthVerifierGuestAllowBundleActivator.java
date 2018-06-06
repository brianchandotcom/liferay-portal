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

package com.liferay.portal.security.auth.verifier.test.internal.activator;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Marta Medio
 */
public class AuthVerifierGuestAllowBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("auth.verifier.guest.allowed", false);
		properties.put("com.liferay.auth.verifier.filter.enabled", true);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/auth-verifier-no-allow-guest-test");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			"auth-verifier-no-allow-guest-test");
		properties.put("test-servlet-context-helper", true);

		_serviceRegistrations.add(
			bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(bundleContext.getBundle()) {
				},
				properties));

		properties = new HashMapDictionary<>();

		properties.put("com.liferay.auth.verifier.filter.enabled", true);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/auth-verifier-allow-guest-test");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			"auth-verifier-allow-guest-test");
		properties.put("test-servlet-context-helper", true);

		_serviceRegistrations.add(
			bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(bundleContext.getBundle()) {
				},
				properties));

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(test-servlet-context-helper=true)");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/getAccess");

		_serviceRegistrations.add(
			bundleContext.registerService(
				Servlet.class,
				new PrototypeServiceFactory<Servlet>() {

					@Override
					public Servlet getService(
						Bundle bundle,
						ServiceRegistration<Servlet> serviceRegistration) {

						return new GuestAllowHttpServlet();
					}

					@Override
					public void ungetService(
						Bundle bundle,
						ServiceRegistration<Servlet> serviceRegistration,
						Servlet servlet) {
					}

				},
				properties));
	}

	@Override
	public void stop(BundleContext bundleContext) {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				continue;
			}
		}
	}

	public class GuestAllowHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest request, HttpServletResponse response)
			throws IOException {

			PrintWriter writer = response.getWriter();

			writer.write("true");
		}

	}

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}