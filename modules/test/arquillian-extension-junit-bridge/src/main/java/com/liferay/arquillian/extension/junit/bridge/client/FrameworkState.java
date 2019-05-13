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

package com.liferay.arquillian.extension.junit.bridge.client;

import com.liferay.arquillian.extension.junit.bridge.connector.FrameworkCommand;
import com.liferay.arquillian.extension.junit.bridge.connector.FrameworkResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matthew Tambara
 */
public class FrameworkState {

	public void close() throws IOException {
		_objectInputStream.close();

		_objectInputStream = null;

		_objectOutputStream.close();

		_objectOutputStream = null;

		_socket.close();

		_socket = null;
	}

	public void connect() throws Exception {
		Integer port = Integer.getInteger("liferay.arquillian.port");

		if (port == null) {
			port = _PORT;
		}

		int retries = 0;

		while (true) {
			try {
				_socket = new Socket(_inetAddress, port);

				break;
			}
			catch (ConnectException ce) {
				if (retries++ < _MAX_RETRY_ATTEMPTS) {
					_logger.log(
						Level.INFO,
						"Unable to connect at " +
							_inetAddress.getHostAddress() + ":" + port +
								". retrying in " + _RETRY_INTERVAL + "s.");

					Thread.sleep(_RETRY_INTERVAL * 1000);
				}
				else {
					_logger.log(
						Level.SEVERE,
						"Unable to connect after " + _MAX_RETRY_ATTEMPTS +
							" attempts");

					throw ce;
				}
			}
		}

		_objectOutputStream = new ObjectOutputStream(_socket.getOutputStream());

		_objectInputStream = new ObjectInputStream(_socket.getInputStream());

		String passcode = System.getProperty("liferay.arquillian.passcode");

		if (passcode == null) {
			passcode = "";
		}

		_objectOutputStream.writeUTF(passcode);

		_objectOutputStream.flush();
	}

	public long installBundle(String location, byte[] bytes) throws Throwable {
		_objectOutputStream.writeObject(
			FrameworkCommand.installBundle(location, bytes));

		_objectOutputStream.flush();

		return _getResult();
	}

	public void startBundle(long bundleId) throws Throwable {
		_objectOutputStream.writeObject(FrameworkCommand.startBundle(bundleId));

		_objectOutputStream.flush();

		_getResult();
	}

	public void uninstallBundle(long bundleId) throws Throwable {
		_objectOutputStream.writeObject(
			FrameworkCommand.uninstallBundle(bundleId));

		_objectOutputStream.flush();

		_getResult();
	}

	private <T extends Serializable> T _getResult() throws Throwable {
		FrameworkResult<T> frameworkResult =
			(FrameworkResult<T>)_objectInputStream.readObject();

		return frameworkResult.get();
	}

	private static final int _MAX_RETRY_ATTEMPTS = 5;

	private static final int _PORT = 32763;

	private static final int _RETRY_INTERVAL = 10;

	private static final Logger _logger = Logger.getLogger(
		FrameworkState.class.getName());

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private ObjectInputStream _objectInputStream;
	private ObjectOutputStream _objectOutputStream;
	private Socket _socket;

}