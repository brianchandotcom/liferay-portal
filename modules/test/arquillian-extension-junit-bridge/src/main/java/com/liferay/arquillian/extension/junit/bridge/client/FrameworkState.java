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

import java.net.InetAddress;
import java.net.Socket;

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

	public void connect() throws IOException {
		Integer port = Integer.getInteger("liferay.arquillian.port");

		if (port == null) {
			port = _PORT;
		}

		_socket = new Socket(_inetAddress, port);

		_objectOutputStream = new ObjectOutputStream(_socket.getOutputStream());

		_objectInputStream = new ObjectInputStream(_socket.getInputStream());

		String passcode = System.getProperty("liferay.arquillian.passcode");

		if (passcode == null) {
			passcode = "";
		}

		_objectOutputStream.writeUTF(passcode);

		_objectOutputStream.flush();
	}

	public long installBundle(String location, byte[] bytes) throws Exception {
		_objectOutputStream.writeObject(
			FrameworkCommand.installBundle(location, bytes));

		_objectOutputStream.flush();

		return _getResult();
	}

	public void startBundle(long bundleId) throws Exception {
		_objectOutputStream.writeObject(FrameworkCommand.startBundle(bundleId));

		_objectOutputStream.flush();

		_getResult();
	}

	public void uninstallBundle(long bundleId) throws Exception {
		_objectOutputStream.writeObject(
			FrameworkCommand.uninstallBundle(bundleId));

		_objectOutputStream.flush();

		_getResult();
	}

	private long _getResult() throws Exception {
		FrameworkResult frameworkResult =
			(FrameworkResult)_objectInputStream.readObject();

		Exception exception = frameworkResult.getException();

		if (exception != null) {
			throw exception;
		}

		return frameworkResult.getBundleId();
	}

	private static final int _PORT = 32763;

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();

	private ObjectInputStream _objectInputStream;
	private ObjectOutputStream _objectOutputStream;
	private Socket _socket;

}