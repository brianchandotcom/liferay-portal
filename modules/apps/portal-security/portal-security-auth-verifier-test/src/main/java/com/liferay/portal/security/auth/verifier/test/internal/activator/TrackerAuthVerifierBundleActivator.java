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
import com.liferay.portal.kernel.util.Validator;

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
public class TrackerAuthVerifierBundleActivator
	extends BaseAuthVerifierBundleActivator {

	@Override
	public void doStart(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("auth.verifier.guest.allowed", true);
		properties.put(JaxrsWhiteboardConstants.JAX_RS_NAME, "filter-enabled");

		registerServletContextHelper(
			"auth-verifier-filter-tracker-enabled-test", properties);

		properties = new HashMapDictionary<>();

		registerServletContextHelper(
			"auth-verifier-filter-tracker-disabled-test", properties);

		properties = new HashMapDictionary<>();

		registerServletContextHelper(
			"auth-verifier-filter-tracker-default-test", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"cxf-servlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/remoteUser");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(auth-verifier-tracker-test-servlet-context-helper=true)");

		registerServlet(properties, RemoteUserHttpServlet::new);
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

	@Override
	protected void registerServletContextHelper(
		String servletContextName, Dictionary<String, Object> properties) {

		properties.put(
			"auth-verifier-tracker-test-servlet-context-helper", true);

		super.registerServletContextHelper(servletContextName, properties);
	}

}