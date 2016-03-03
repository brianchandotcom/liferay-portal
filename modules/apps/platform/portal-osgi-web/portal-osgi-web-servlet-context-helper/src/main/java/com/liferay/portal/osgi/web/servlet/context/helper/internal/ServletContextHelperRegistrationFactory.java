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

package com.liferay.portal.osgi.web.servlet.context.helper.internal;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContextListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Juan Gonzalez
 * @author Raymond Augé
 */
public class ServletContextHelperRegistrationFactory
	implements ServiceFactory<ServletContextHelperRegistration> {

	@Override
	public ServletContextHelperRegistration getService(
		Bundle bundle,
		ServiceRegistration<ServletContextHelperRegistration>
			registration) {

		Dictionary<String, Object> properties = new Hashtable<>();

		String contextPath = getContextPath(bundle);
		String servletContextName = getServletContextName(bundle, contextPath);

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
			servletContextName);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, contextPath);
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
			"(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" +
				servletContextName + ")");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_LISTENER,
			Boolean.TRUE.toString());
		properties.put("rtl.required", Boolean.TRUE.toString());

		BundleContext bundleContext = bundle.getBundleContext();

		CustomServletContextHelper customServletContextHelper =
			new CustomServletContextHelper(bundle);

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				new String[] {
					ServletContextHelper.class.getName(),
					ServletContextListener.class.getName()
				},
				customServletContextHelper, properties);

		return new ServletContextHelperRegistrationImpl(
			(ServiceRegistration<ServletContextHelper>)serviceRegistration,
			customServletContextHelper);
	}

	@Override
	public void ungetService(
		Bundle bundle,
		ServiceRegistration<ServletContextHelperRegistration> registration,
		ServletContextHelperRegistration service) {
	}

	protected String getServletContextName(Bundle bundle, String contextPath) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String header = headers.get("Web-ContextName");

		if (Validator.isNotNull(header)) {
			return header;
		}

		contextPath = contextPath.substring(1);

		return contextPath.replaceAll("[^a-zA-Z0-9\\-]", "");
	}

	private String getContextPath(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders();

		String contextPath = headers.get("Web-ContextPath");

		if (Validator.isNotNull(contextPath)) {
			return contextPath;
		}

		String symbolicName = bundle.getSymbolicName();

		return '/' + symbolicName.replaceAll("[^a-zA-Z0-9]", "");
	}

}