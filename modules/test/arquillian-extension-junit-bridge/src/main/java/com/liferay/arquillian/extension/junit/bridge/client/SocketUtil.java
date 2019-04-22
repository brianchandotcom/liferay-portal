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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import java.nio.channels.ServerSocketChannel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matthew Tambara
 */
public class SocketUtil {

	public static void close() throws IOException {
		_objectInputStream.close();

		_objectInputStream = null;

		_objectOutputStream.close();

		_objectOutputStream = null;

		_socket.close();

		_socket = null;

		_connected = false;
	}

	public static void connect(long passCode) throws IOException {
		if (!_connected) {
			while (true) {
				_socket = _serverSocket.accept();

				_objectOutputStream = new ObjectOutputStream(
					_socket.getOutputStream());

				_objectInputStream = new ObjectInputStream(
					_socket.getInputStream());

				if (passCode != readLong()) {
					_logger.log(
						Level.WARNING,
						"Pass code mismatch, dropped connection from " +
							getRemoteSocketAddress());

					close();

					continue;
				}

				_connected = true;

				return;
			}
		}
	}

	public static SocketAddress getRemoteSocketAddress() {
		return _socket.getRemoteSocketAddress();
	}

	public static ServerSocket getServerSocket() throws IOException {
		if (_serverSocket != null) {
			return _serverSocket;
		}

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		int port = _START_PORT;

		while (true) {
			try {
				ServerSocket serverSocket = serverSocketChannel.socket();

				serverSocket.bind(new InetSocketAddress(_inetAddress, port));

				_serverSocket = serverSocket;

				return serverSocket;
			}
			catch (IOException ioe) {
				port++;
			}
		}
	}

	public static long readLong() throws IOException {
		return _objectInputStream.readLong();
	}

	public static Object readObject() throws Exception {
		return _objectInputStream.readObject();
	}

	public static void writeUTF(String string) throws IOException {
		_objectOutputStream.writeUTF(string);

		_objectOutputStream.flush();
	}

	private static final int _START_PORT = 32764;

	private static final Logger _logger = Logger.getLogger(
		SocketUtil.class.getName());

	private static boolean _connected;
	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();
	private static ObjectInputStream _objectInputStream;
	private static ObjectOutputStream _objectOutputStream;
	private static ServerSocket _serverSocket;
	private static Socket _socket;

}