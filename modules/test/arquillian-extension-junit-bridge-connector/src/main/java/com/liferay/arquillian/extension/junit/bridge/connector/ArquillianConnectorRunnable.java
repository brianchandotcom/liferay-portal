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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ServerSocketChannel;

import org.osgi.framework.BundleContext;

/**
 * @author Matthew Tambara
 */
public class ArquillianConnectorRunnable implements Runnable {

	public ArquillianConnectorRunnable(BundleContext bundleContext, int port) {
		_bundleContext = bundleContext;
		_port = port;
	}

	@Override
	public void run() {
		try {
			ServerSocketChannel serverSocketChannel =
				ServerSocketChannel.open();

			ServerSocket serverSocket = serverSocketChannel.socket();

			serverSocket.bind(new InetSocketAddress(_inetAddress, _port));

			while (true) {
				try (Socket socket = serverSocket.accept();
					ObjectOutputStream objectOutputStream =
						new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream objectInputStream = new ObjectInputStream(
						socket.getInputStream())) {

					while (true) {
						FrameworkCommand frameworkCommand =
							(FrameworkCommand)objectInputStream.readObject();

						long result = frameworkCommand.execute(_bundleContext);

						if (result > 0) {
							objectOutputStream.writeLong(result);

							objectOutputStream.flush();
						}
					}
				}
				catch (EOFException eofe) {
				}
			}
		}
		catch (ClosedByInterruptException cbie) {
		}
		catch (Exception e) {
			_log.fatal(
				"Encountered a problem while using " +
					_inetAddress.getHostAddress() + ":" + _port +
						". Shutting down now.",
				e);

			System.exit(_port);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ArquillianConnectorRunnable.class);

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private final BundleContext _bundleContext;
	private final int _port;

}