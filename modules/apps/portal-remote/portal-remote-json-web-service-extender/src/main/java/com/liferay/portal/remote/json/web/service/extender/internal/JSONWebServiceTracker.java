/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.extender.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
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
		return _registerService(serviceReference);
	}

	@Override
	public void modifiedService(
		ServiceReference<Object> serviceReference, Object service) {

		_unregisterService(service);

		_registerService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Object> serviceReference, Object service) {

		_unregisterService(service);
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		_serviceTracker = ServiceTrackerFactory.open(
			componentContext.getBundleContext(),
			StringBundler.concat(
				"(&(json.web.service.context.name=*)(json.web.service.context.",
				"path=*)(!(objectClass=", AopService.class.getName(), ")))"),
			this);
	}

	@Deactivate
	protected void deactivate() {
		_componentContext = null;

		_serviceTracker.close();

		_serviceTracker = null;
	}

	private ClassLoader _getBundleClassLoader(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		return bundleWiring.getClassLoader();
	}

	private Object _getService(ServiceReference<Object> serviceReference) {
		BundleContext bundleContext = _componentContext.getBundleContext();

		return bundleContext.getService(serviceReference);
	}

	private Object _registerService(ServiceReference<Object> serviceReference) {
		String contextName = (String)serviceReference.getProperty(
			"json.web.service.context.name");
		String contextPath = (String)serviceReference.getProperty(
			"json.web.service.context.path");
		Object service = _getService(serviceReference);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader classLoader = _getBundleClassLoader(
			serviceReference.getBundle());

		currentThread.setContextClassLoader(classLoader);

		try {
			_jsonWebServiceActionsManager.registerService(
				contextName, contextPath, service);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return service;
	}

	private void _unregisterService(Object service) {
		_jsonWebServiceActionsManager.unregisterJSONWebServiceActions(service);
	}

	private ComponentContext _componentContext;

	@Reference
	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;

	private ServiceTracker<Object, Object> _serviceTracker;

}