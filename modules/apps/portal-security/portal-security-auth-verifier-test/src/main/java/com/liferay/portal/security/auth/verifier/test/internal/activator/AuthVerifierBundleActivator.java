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

import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.function.Supplier;

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
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Tomas Polesovsky
 */
public class AuthVerifierBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		doStart(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				continue;
			}
		}
	}

	public class GuestAllowedHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest request, HttpServletResponse response)
			throws IOException {

			PrintWriter printWriter = response.getWriter();

			printWriter.write("guest-allowed");
		}

	}

	public class RemoteAccessHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest request, HttpServletResponse response)
			throws IOException {

			PrintWriter printWriter = response.getWriter();

			printWriter.write(
				String.valueOf(AccessControlThreadLocal.isRemoteAccess()));
		}

	}

	public class RemoteUserHttpServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest request, HttpServletResponse response)
			throws IOException {

			PrintWriter printWriter = response.getWriter();

			String remoteUser = request.getRemoteUser();

			if (Validator.isNull(remoteUser)) {
				printWriter.write("no-remote-user");
			}
			else {
				printWriter.write("remote-user-set");
			}
		}

	}

	protected void doStart(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-no-allowed");
		properties.put("auth.verifier.guest.allowed", false);
		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-guest-allowed-false-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-allowed");
		properties.put("auth.verifier.guest.allowed", true);
		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-guest-allowed-true-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-default");
		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-guest-allowed-default-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/guestAllowed");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(auth-verifier-guest-allowed-test-servlet-context-helper=true)");

		registerServlet(properties, GuestAllowedHttpServlet::new);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled");
		properties.put("auth.verifier.guest.allowed", true);
		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-filter-tracker-enabled-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-filter-tracker-disabled-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-filter-tracker-default-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(auth-verifier-tracker-test-servlet-context-helper=true)");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/remoteUser");

		registerServlet(properties, RemoteUserHttpServlet::new);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled");
		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		registerServletContextHelper(
			"auth-verifier-filter-tracker-remote-access-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/remoteAccess");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"auth-verifier-filter-tracker-remote-access-test");

		registerServlet(properties, RemoteAccessHttpServlet::new);
	}

	protected void registerServlet(
		Dictionary<String, Object> properties, Supplier<Servlet> supplier) {

		serviceRegistrations.add(
			_bundleContext.registerService(
				Servlet.class,
				new PrototypeServiceFactory<Servlet>() {

					@Override
					public Servlet getService(
						Bundle bundle,
						ServiceRegistration<Servlet> serviceRegistration) {

						return supplier.get();
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

	protected void registerServletContextHelper(
		String servletContextName, Dictionary<String, Object> properties) {

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/" + servletContextName);

		serviceRegistrations.add(
			_bundleContext.registerService(
				ServletContextHelper.class,
				new ServletContextHelper(_bundleContext.getBundle()) {
				},
				properties));
	}

	protected final List<ServiceRegistration<?>> serviceRegistrations =
		new ArrayList<>();

	private BundleContext _bundleContext;

}