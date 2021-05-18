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

package com.liferay.data.guard.connector.client;

import com.liferay.data.guard.connector.DataGuardResult;
import com.liferay.data.guard.connector.command.DataGuardCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matthew Tambara
 */
public class DataGuardClient {

	public void close() throws IOException {
		_objectInputStream.close();

		_objectInputStream = null;

		_objectOutputStream.close();

		_objectOutputStream = null;

		_socket.close();

		_socket = null;
	}

	public void connect() throws Exception {
		Integer port = Integer.getInteger("liferay.data.guard.port");

		if (port == null) {
			port = _PORT;
		}

		int retries = 0;

		while (true) {
			try {
				_socket = new Socket(_inetAddress, port);

				break;
			}
			catch (ConnectException connectException) {
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

					throw connectException;
				}
			}
		}

		_objectOutputStream = new ObjectOutputStream(_socket.getOutputStream());

		_objectInputStream = new ObjectInputStream(_socket.getInputStream());

		String passcode = System.getProperty("liferay.data.guard.passcode");

		if (passcode == null) {
			passcode = "";
		}

		_objectOutputStream.writeUTF(passcode);

		_objectOutputStream.flush();
	}

	public long endCapture(long id, String testClassName) throws Throwable {
		_objectOutputStream.writeObject(
			DataGuardCommand.endCapture(id, testClassName));

		_objectOutputStream.flush();

		return _getResult();
	}

	public long startCapture() throws Throwable {
		_objectOutputStream.writeObject(DataGuardCommand.startCapture());

		_objectOutputStream.flush();

		return _getResult();
	}

	private long _getResult() throws Throwable {
		DataGuardResult dataGuardResult =
			(DataGuardResult)_objectInputStream.readObject();

		return dataGuardResult.get();
	}

	private static final int _MAX_RETRY_ATTEMPTS = 5;

	private static final int _PORT = 42763;

	private static final int _RETRY_INTERVAL = 10;

	private static final Logger _logger = Logger.getLogger(
		DataGuardClient.class.getName());

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private ObjectInputStream _objectInputStream;
	private ObjectOutputStream _objectOutputStream;
	private Socket _socket;

}