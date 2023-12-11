/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.extender.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Miguel Pastor
 */
@Component(service = {})
public class JSONWebServiceTracker
	implements ServiceTrackerCustomizer<Object, Object> {

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			StringBundler.concat(
				"(&(json.web.service.context.name=*)(json.web.service.context.",
				"path=*)(!(objectClass=", AopService.class.getName(), ")))"),
			this);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private BundleContext _bundleContext;

	@Reference
	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;

	private ServiceTracker<Object, Object> _serviceTracker;

}