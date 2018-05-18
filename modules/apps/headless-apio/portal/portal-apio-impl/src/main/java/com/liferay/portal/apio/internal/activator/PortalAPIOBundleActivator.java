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

package com.liferay.portal.apio.internal.activator;

import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Filter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * Adds basic auth filter to APIO Architect application.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PortalAPIOBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME +
				"=context.forapio-application)");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME,
			AuthVerifierFilter.class.getName());
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN, "/*");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_INIT_PARAM_PREFIX +
				"auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes",
			"*");

		_serviceRegistration = bundleContext.registerService(
			Filter.class, new AuthVerifierFilter(), properties);
	}

	@Override
	public void stop(BundleContext context) {
		_serviceRegistration.unregister();
	}

	private ServiceRegistration<Filter> _serviceRegistration;

}