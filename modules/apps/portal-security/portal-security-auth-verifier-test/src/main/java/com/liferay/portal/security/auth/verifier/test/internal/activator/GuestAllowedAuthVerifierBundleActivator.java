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

import java.util.Dictionary;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Marta Medio
 */
public class GuestAllowedAuthVerifierBundleActivator
	extends BaseAuthVerifierBundleActivator {

	@Override
	public void doStart(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("auth.verifier.guest.allowed", false);
		properties.put(
			JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-no-allowed");

		registerServletContextHelper(
			"auth-verifier-guest-allowed-false-test", properties);

		properties = new HashMapDictionary<>();

		properties.put("auth.verifier.guest.allowed", true);
		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-allowed");

		registerServletContextHelper(
			"auth-verifier-guest-allowed-true-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "guest-default");

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

	@Override
	protected void registerServletContextHelper(
		String servletContextName, Dictionary<String, Object> properties) {

		properties.put(
			"auth-verifier-guest-allowed-test-servlet-context-helper", true);

		super.registerServletContextHelper(servletContextName, properties);
	}

}