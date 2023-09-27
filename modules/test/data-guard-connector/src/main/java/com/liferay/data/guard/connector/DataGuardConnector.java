/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.guard.connector;

import java.io.IOException;

import java.net.InetAddress;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

/**
 * @author Matthew Tambara
 */
@Component(service = {})
public class DataGuardConnector {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, String> properties) {

		int port = _PORT;

		String portString = properties.get("port");

		if (portString != null) {
			port = Integer.valueOf(portString);
		}

		Logger logger = _loggerFactory.getLogger(DataGuardConnector.class);

		logger.info("Listening on port {}", port);

		try {
			_dataGuardConnectorThread = new DataGuardConnectorThread(
				bundleContext, _inetAddress, port, properties.get("passcode"),
				logger);
		}
		catch (IOException ioException) {
			logger.error(
				"Encountered a problem while using {}:{}. Shutting down now.",
				_inetAddress.getHostAddress(), port, ioException);

			System.exit(-10);
		}

		_dataGuardConnectorThread.start();
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_dataGuardConnectorThread.close();

		_dataGuardConnectorThread.join();
	}

	private static final int _PORT = 42763;

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private DataGuardConnectorThread _dataGuardConnectorThread;

	@Reference
	private LoggerFactory _loggerFactory;

}