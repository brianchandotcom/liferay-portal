/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.channels.ServerSocketChannel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matthew Tambara
 */
public class SocketState {

	public void close() throws IOException {
		_objectInputStream.close();

		_objectInputStream = null;

		_objectOutputStream.close();

		_objectOutputStream = null;

		_socket.close();

		_socket = null;

		_serverSocket.close();

		_serverSocket = null;
	}

	public void connect(long passCode) throws IOException {
		while (true) {
			_socket = _serverSocket.accept();

			_objectOutputStream = new ObjectOutputStream(
				_socket.getOutputStream());

			_objectInputStream = new ObjectInputStream(
				_socket.getInputStream());

			if (passCode == _objectInputStream.readLong()) {
				break;
			}

			_logger.log(
				Level.WARNING,
				"Pass code mismatch, dropped connection from " +
					_socket.getRemoteSocketAddress());

			_objectInputStream.close();

			_objectOutputStream.close();

			_socket.close();
		}
	}

	public ServerSocket getServerSocket() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		int port = _START_PORT;

		while (true) {
			try {
				ServerSocket serverSocket = serverSocketChannel.socket();

				serverSocket.bind(new InetSocketAddress(_inetAddress, port));

				_serverSocket = serverSocket;

				return serverSocket;
			}
			catch (IOException ioException) {
				port++;
			}
		}
	}

	public long readLong() throws IOException {
		return _objectInputStream.readLong();
	}

	public Object readObject() throws Exception {
		try {
			return _objectInputStream.readObject();
		}
		catch (WriteAbortedException writeAbortedException) {
			return _objectInputStream.readObject();
		}
	}

	public void writeObject(Object object) throws IOException {
		_objectOutputStream.writeObject(object);

		_objectOutputStream.flush();
	}

	public void writeUTF(String string) throws IOException {
		_objectOutputStream.writeUTF(string);

		_objectOutputStream.flush();
	}

	private static final int _START_PORT = 32764;

	private static final Logger _logger = Logger.getLogger(
		SocketState.class.getName());

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private ObjectInputStream _objectInputStream;
	private ObjectOutputStream _objectOutputStream;
	private ServerSocket _serverSocket;
	private Socket _socket;

}