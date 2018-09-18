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

package com.liferay.portal.osgi.debug.spring.extender.internal;

import static java.lang.Thread.sleep;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.osgi.debug.spring.extender.internal.configuration.UnavailableComponentScannerConfiguration;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Matthew Tambara
 */
@Component(
	configurationPid = "com.liferay.portal.osgi.debug.spring.extender.internal.configuration.UnavailableComponentScannerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class UnavailableComponentScanner {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		UnavailableComponentScannerConfiguration
			unavailableComponentScannerConfiguration =
				ConfigurableUtil.createConfigurable(
					UnavailableComponentScannerConfiguration.class, properties);

		long scanningInterval =
			unavailableComponentScannerConfiguration.
				unavailableComponentScanningInterval();

		if (scanningInterval > 0) {
			_unavailableComponentScanningThread =
				new UnavailableComponentScanningThread(
					scanningInterval * Time.SECOND);

			_unavailableComponentScanningThread.start();
		}
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext)
		throws InterruptedException {

		if (_unavailableComponentScanningThread != null) {
			_unavailableComponentScanningThread.interrupt();

			_unavailableComponentScanningThread.join();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnavailableComponentScanner.class);

	private Thread _unavailableComponentScanningThread;

	private static class UnavailableComponentScanningThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					sleep(_scanningInterval);

					String scanResult =
						UnavailableComponentUtil.scanUnavailableComponents();

					if (scanResult.isEmpty()) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"All Spring extender dependency manager " +
									"components are registered");
						}
					}
					else {
						if (_log.isWarnEnabled()) {
							_log.warn(scanResult);
						}
					}
				}
			}
			catch (InterruptedException ie) {
				if (_log.isInfoEnabled()) {
					_log.info("Stopped scanning for unavailable components");
				}
			}
		}

		private UnavailableComponentScanningThread(long scanningInterval) {
			_scanningInterval = scanningInterval;

			setDaemon(true);
			setName("Spring Extender Unavailable Component Scanner");
		}

		private final long _scanningInterval;

	}

}