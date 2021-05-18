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
@Component(
	configurationPid = "com.liferay.data.guard.connector.DataGuardConnectorConfiguration",
	immediate = true, service = {}
)
public class DataGuardConnector {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, String> properties) {

		int port = _DEFAULT_PORT;

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

	private static final int _DEFAULT_PORT = 42763;

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private DataGuardConnectorThread _dataGuardConnectorThread;

	@Reference
	private LoggerFactory _loggerFactory;

}