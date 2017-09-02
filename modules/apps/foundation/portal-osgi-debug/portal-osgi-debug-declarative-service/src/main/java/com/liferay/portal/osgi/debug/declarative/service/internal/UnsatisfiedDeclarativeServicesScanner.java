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

package com.liferay.portal.osgi.debug.declarative.service.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.debug.declarative.service.internal.configuration.UnsatisfiedDeclarativeServicesScannerConfiguration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.osgi.debug.declarative.service.internal.configuration.UnsatisfiedDeclarativeServicesScannerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true
)
public class UnsatisfiedDeclarativeServicesScanner {

	@Activate
	protected void activate(ComponentContext componentContext) {
		if (!_log.isInfoEnabled()) {
			return;
		}

		UnsatisfiedDeclarativeServicesScannerConfiguration
			unsatisfiedDeclarativeServicesScannerConfiguration =
				ConfigurableUtil.createConfigurable(
					UnsatisfiedDeclarativeServicesScannerConfiguration.class,
					componentContext.getProperties());

		final BundleContext bundleContext = componentContext.getBundleContext();

		_scheduledExecutorService = Executors.newScheduledThreadPool(1);

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				_log.info(
					DeclarativeServiceUtil.listUnsatisfiedDeclarativeServices(
						_serviceComponentRuntime, bundleContext.getBundles()));
			}

		};

		_scheduledExecutorService.scheduleAtFixedRate(
			runnable, 0,
			unsatisfiedDeclarativeServicesScannerConfiguration.interval(),
			TimeUnit.SECONDS);
	}

	@Deactivate
	protected void deactivate() {
		if (_scheduledExecutorService != null) {
			_scheduledExecutorService.shutdown();

			_scheduledExecutorService = null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnsatisfiedDeclarativeServicesScanner.class);

	private ScheduledExecutorService _scheduledExecutorService;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}