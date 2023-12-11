/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Miguel Pastor
 */
public class JSONWebServiceTrackerCustomizer
	implements ServiceTrackerCustomizer<Object, Object> {

	public JSONWebServiceTrackerCustomizer(
		JSONWebServiceActionsManager jsonWebServiceActionsManager,
		BundleContext bundleContext) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
		_bundleContext = bundleContext;
	}

	@Override
	public Object addingService(ServiceReference<Object> serviceReference) {
		String contextName = (String)serviceReference.getProperty(
			"json.web.service.context.name");
		String contextPath = (String)serviceReference.getProperty(
			"json.web.service.context.path");
		Object service = _bundleContext.getService(serviceReference);

		Bundle bundle = serviceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				bundleWiring.getClassLoader())) {

			_jsonWebServiceActionsManager.registerService(
				contextName, contextPath, service);
		}

		return service;
	}

	@Override
	public void modifiedService(
		ServiceReference<Object> serviceReference, Object service) {

		removedService(serviceReference, service);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Object> serviceReference, Object service) {

		_jsonWebServiceActionsManager.unregisterJSONWebServiceActions(service);

		_bundleContext.ungetService(serviceReference);
	}

	private final BundleContext _bundleContext;
	private final JSONWebServiceActionsManager _jsonWebServiceActionsManager;

}