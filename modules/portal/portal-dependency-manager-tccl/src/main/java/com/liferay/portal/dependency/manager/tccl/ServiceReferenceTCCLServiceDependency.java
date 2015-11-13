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

package com.liferay.portal.dependency.manager.tccl;

import org.apache.felix.dm.DependencyService;
import org.apache.felix.dm.impl.Logger;
import org.apache.felix.dm.impl.dependencies.ServiceDependencyImpl;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceReferenceTCCLServiceDependency
	extends ServiceDependencyImpl {

	public ServiceReferenceTCCLServiceDependency(
		BundleContext bundleContext, Logger logger) {

		super(bundleContext, logger);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void invoke(
		Object[] callbackInstances, DependencyService dependencyService,
		ServiceReference serviceReference, Object service, String name) {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Bundle bundle = serviceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		currentThread.setContextClassLoader(bundleWiring.getClassLoader());

		try {
			super.invoke(
				callbackInstances, dependencyService, serviceReference, service,
				name);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void invokeSwappedCallback(
		Object[] callbackInstances, DependencyService component,
		ServiceReference previousServiceReference, Object previousService,
		ServiceReference currentServiceReference, Object currentService,
		String swapCallback) {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Bundle bundle = currentServiceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		currentThread.setContextClassLoader(bundleWiring.getClassLoader());

		try {
			super.invokeSwappedCallback(
				callbackInstances, component, previousServiceReference,
				previousService, currentServiceReference, currentService,
				swapCallback);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

}