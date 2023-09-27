/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.declarative.service.internal;

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
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
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
					Thread.sleep(_scanningInterval);

					_scanUnsatisfiedComponents(_serviceComponentRuntime);
				}
			}
			catch (InterruptedException interruptedException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Stopped scanning for unsatisfied declarative " +
							"service components",
						interruptedException);
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