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

package com.liferay.portal.jsonws.tracker.internal;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
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
@Component(immediate = true, service = JsonWSServiceTracker.class)
public class JsonWSServiceTracker
	implements ServiceTrackerCustomizer<Object, Object> {

	@Activate
	public void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		BundleContext bundleContext = componentContext.getBundleContext();

		try {
			_serviceTracker = new ServiceTracker<Object, Object>(
				bundleContext, bundleContext.createFilter(_JSONWS_PATH_FILTER),
				this);

			_serviceTracker.open();
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(
				"Unable to activate the JSON WS Tracker", ise);
		}
	}

	@Override
	public Object addingService(ServiceReference<Object> serviceReference) {
		return _registerJsonWsAction(serviceReference);
	}

	@Deactivate
	public void deactivate() {
		_componentContext = null;

		_serviceTracker.close();

		_serviceTracker = null;
	}

	@Override
	public void modifiedService(
		ServiceReference<Object> serviceReference, Object o) {

		_unregisterJsonWsAction(o);

		_registerJsonWsAction(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Object> serviceReference, Object o) {

		_unregisterJsonWsAction(o);
	}

	@Reference
	public void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	private Object _getService(ServiceReference<Object> serviceReference) {
		BundleContext bundleContext = _componentContext.getBundleContext();

		return bundleContext.getService(serviceReference);
	}

	private Object _registerJsonWsAction(
		ServiceReference<Object> serviceReference) {

		Object o = _getService(serviceReference);
		String path = (String)serviceReference.getProperty(_JSONWS_PATH);

		_jsonWebServiceActionsManager.registerService(path, o);

		return o;
	}

	private void _unregisterJsonWsAction(Object o) {
		_jsonWebServiceActionsManager.unregisterJSONWebServiceActions(o);
	}

	private static final String _JSONWS_PATH = "jsonws.path";

	private static final String _JSONWS_PATH_FILTER =
		"(" + _JSONWS_PATH + "=*)";

	private ComponentContext _componentContext;
	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;
	private ServiceTracker<Object, Object> _serviceTracker;

}