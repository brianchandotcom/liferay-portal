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
import com.liferay.portal.osgi.debug.declarative.service.internal.configuration.UnsatisfiedComponentScannerConfiguration;

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
	configurationPid = "com.liferay.portal.osgi.debug.declarative.service.internal.configuration.UnsatisfiedComponentScannerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true
)
public class UnsatisfiedComponentScanner {

	@Activate
	protected void activate(ComponentContext componentContext) {
		UnsatisfiedComponentScannerConfiguration
			unsatisfiedComponentScannerConfiguration =
				ConfigurableUtil.createConfigurable(
					UnsatisfiedComponentScannerConfiguration.class,
					componentContext.getProperties());

		long scanningInterval =
			unsatisfiedComponentScannerConfiguration.
				unsatisfiedComponentScanningInterval();

		if (scanningInterval > 0) {
			final BundleContext bundleContext =
				componentContext.getBundleContext();

			_scheduledExecutorService = Executors.newScheduledThreadPool(1);

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					if (_log.isInfoEnabled()) {
						_log.info(
							UnsatisfiedComponentUtil.listUnsatisfiedComponents(
								_serviceComponentRuntime,
								bundleContext.getBundles()));
					}
				}

			};

			_scheduledExecutorService.scheduleAtFixedRate(
				runnable, 0, scanningInterval, TimeUnit.SECONDS);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_scheduledExecutorService != null) {
			_scheduledExecutorService.shutdown();

			_scheduledExecutorService = null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnsatisfiedComponentScanner.class);

	private ScheduledExecutorService _scheduledExecutorService;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}