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

package com.liferay.portal.equinox.log.bridge.internal;

import com.liferay.petra.string.StringBundler;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.equinox.log.ExtendedLogReaderService;
import org.eclipse.osgi.internal.hookregistry.ActivatorHookFactory;
import org.eclipse.osgi.internal.hookregistry.HookConfigurator;
import org.eclipse.osgi.internal.hookregistry.HookRegistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 * @author Kamesh Sampath
 */
public class PortalHookConfigurator
	implements ActivatorHookFactory, BundleActivator, HookConfigurator {

	public PortalHookConfigurator() {
		_bundleStartStopLogger = new BundleStartStopLogger(_portalStarted);
		_portalSynchronousLogListener = new PortalSynchronousLogListener();
	}

	@Override
	public void addHooks(HookRegistry hookRegistry) {
		hookRegistry.addActivatorHookFactory(this);
	}

	@Override
	public BundleActivator createActivator() {
		return this;
	}

	@Override
	public void start(BundleContext bundleContext)
		throws InvalidSyntaxException {

		ServiceReference<ExtendedLogReaderService> serviceReference =
			bundleContext.getServiceReference(ExtendedLogReaderService.class);

		if (serviceReference != null) {
			ExtendedLogReaderService extendedLogReaderService =
				bundleContext.getService(serviceReference);

			extendedLogReaderService.addLogListener(
				_portalSynchronousLogListener);
		}

		bundleContext.addBundleListener(_bundleStartStopLogger);

		_serviceTracker = new ServiceTracker<Object, Void>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=",
					"com.liferay.portal.kernel.module.framework.",
					"ModuleServiceLifecycle)",
					"(module.service.lifecycle=portal.initialized))")),
			null) {

			@Override
			public Void addingService(
				ServiceReference<Object> serviceReference) {

				_portalStarted.set(true);

				close();

				return null;
			}

		};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();

		bundleContext.removeBundleListener(_bundleStartStopLogger);

		ServiceReference<ExtendedLogReaderService> serviceReference =
			bundleContext.getServiceReference(ExtendedLogReaderService.class);

		ExtendedLogReaderService extendedLogReaderService =
			bundleContext.getService(serviceReference);

		extendedLogReaderService.removeLogListener(
			_portalSynchronousLogListener);
	}

	private final BundleStartStopLogger _bundleStartStopLogger;
	private final AtomicBoolean _portalStarted = new AtomicBoolean();
	private final PortalSynchronousLogListener _portalSynchronousLogListener;
	private ServiceTracker<Object, Void> _serviceTracker;

}