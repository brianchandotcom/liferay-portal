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

package com.liferay.arquillian.extension.junit.bridge.connector;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.Logger;

/**
 * @author Matthew Tambara
 */
public class ArquillianConnectorThread extends Thread {

	public ArquillianConnectorThread(
			BundleContext bundleContext, InetAddress inetAddress, int port,
			String passcode, Logger logger)
		throws IOException {

		_bundleContext = bundleContext;
		_passcode = passcode;
		_logger = logger;

		setName("Arquillian-Connector-Thread");
		setDaemon(true);

		_serverSocket = new ServerSocket(port, 50, inetAddress);
	}

	public void close() throws IOException {
		interrupt();

		_serverSocket.close();
	}

	@Override
	public void run() {
		while (true) {
			try (Socket socket = _serverSocket.accept();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(
					socket.getInputStream())) {

				String passcode = objectInputStream.readUTF();

				if ((_passcode != null) && !_passcode.equals(passcode)) {
					_logger.warn(
						"Pass code mismatch, dropped connection from {}",
						socket.getRemoteSocketAddress());

					continue;
				}

				while (true) {
					FrameworkCommand<?> frameworkCommand =
						(FrameworkCommand)objectInputStream.readObject();

					try {
						objectOutputStream.writeObject(
							new FrameworkResult<>(
								frameworkCommand.execute(_bundleContext)));
					}
					catch (Exception e) {
						objectOutputStream.writeObject(
							new FrameworkResult<>(e));
					}

					objectOutputStream.flush();
				}
			}
			catch (EOFException eofe) {
			}
			catch (SocketException se) {
				break;
			}
			catch (Exception e) {
				_logger.error(
					"Dropped connection due to unrecoverable framework failure",
					e);
			}
		}
	}

	private final BundleContext _bundleContext;
	private final Logger _logger;
	private final String _passcode;
	private final ServerSocket _serverSocket;

}