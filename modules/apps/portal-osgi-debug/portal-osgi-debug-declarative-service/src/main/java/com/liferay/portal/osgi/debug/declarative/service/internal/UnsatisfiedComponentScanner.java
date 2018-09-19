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

import static java.lang.Thread.sleep;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.osgi.debug.declarative.service.internal.configuration.UnsatisfiedComponentScannerConfiguration;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
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
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
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
			_unsatisfiedComponentScanningThread =
				new UnsatisfiedComponentScanningThread(
					scanningInterval * Time.SECOND, _serviceComponentRuntime);

			_unsatisfiedComponentScanningThread.start();
		}
	}

	@Deactivate
	protected void deactivate() throws InterruptedException {
		if (_unsatisfiedComponentScanningThread != null) {
			_unsatisfiedComponentScanningThread.interrupt();

			_unsatisfiedComponentScanningThread.join();
		}
	}

	private static void _scanUnsatisfiedComponents(
		ServiceComponentRuntime serviceComponentRuntime) {

		if (_log.isInfoEnabled()) {
			Bundle bundle = FrameworkUtil.getBundle(
				UnsatisfiedComponentScanner.class);

			BundleContext bundleContext = bundle.getBundleContext();

			String message = UnsatisfiedComponentUtil.listUnsatisfiedComponents(
				serviceComponentRuntime, bundleContext.getBundles());

			if (message.isEmpty()) {
				_log.info("All declarative service components are satisfied");
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnsatisfiedComponentScanner.class);

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

	private Thread _unsatisfiedComponentScanningThread;

	private static class UnsatisfiedComponentScanningThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					sleep(_scanningInterval);

					_scanUnsatisfiedComponents(_serviceComponentRuntime);
				}
			}
			catch (InterruptedException ie) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Stopped scanning for unsatisfied declarative " +
							"service components");
				}
			}
		}

		private UnsatisfiedComponentScanningThread(
			long scanningInterval,
			ServiceComponentRuntime serviceComponentRuntime) {

			_scanningInterval = scanningInterval;
			_serviceComponentRuntime = serviceComponentRuntime;

			setDaemon(true);
			setName("Declarative Service Unsatisfied Component Scanner");
		}

		private final long _scanningInterval;
		private final ServiceComponentRuntime _serviceComponentRuntime;

	}

}