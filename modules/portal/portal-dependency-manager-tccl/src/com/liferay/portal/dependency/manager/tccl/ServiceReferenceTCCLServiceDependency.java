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
		BundleContext context, Logger logger) {

		super(context, logger);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void invoke(
		Object[] callbackInstances, DependencyService dependencyService,
		ServiceReference reference, Object service, String name) {

		Bundle bundle = reference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader bundleClassLoader = bundleWiring.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(bundleClassLoader);

		try {
			super.invoke(
				callbackInstances, dependencyService, reference, service, name);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void invokeSwappedCallback(
		Object[] callbackInstances, DependencyService component,
		ServiceReference previousReference, Object previous,
		ServiceReference currentServiceReference, Object current,
		String swapCallback) {

		Bundle bundle = currentServiceReference.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader bundleClassLoader = bundleWiring.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(bundleClassLoader);

		try {
			super.invokeSwappedCallback(
				callbackInstances, component, previousReference, previous,
				currentServiceReference, current, swapCallback);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

}