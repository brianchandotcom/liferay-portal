/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.welder.socket;

import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.BaseWelder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.SocketUtil;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Shuyang Zhou
 */
public class SocketWelder extends BaseWelder {

	public SocketWelder() throws IOException {

		// Assignments have to stay in the constructor because we need to
		// differentiate between a constructor created object and a
		// deserialization created object. Only the constructor created object
		// needs to assign values. The deserialization created object gets its
		// values from the original object.

		bufferSize = Configuration.bufferSize;
		keepAlive = Configuration.keepAlive;
		reuseAddress = Configuration.reuseAddress;
		soLinger = Configuration.soLinger;
		soTimeout = Configuration.soTimeout;
		tcpNoDelay = Configuration.tcpNoDelay;

		serverSocketChannel = SocketUtil.createServerSocketChannel(
			InetAddressUtil.getLoopbackInetAddress(),
			Configuration.serverStartPort,
			new SocketWelderServerSocketConfigurator());

		ServerSocket serverSocket = serverSocketChannel.socket();

		serverPort = serverSocket.getLocalPort();
	}

	@Override
	protected void doDestroy() throws IOException {
		socketChannel.close();
	}

	@Override
	protected RegistrationReference weldClient(Intraband intraband)
		throws IOException {

		socketChannel = SocketChannel.open();

		_configureSocket(socketChannel.socket());

		socketChannel.connect(
			new InetSocketAddress(
				InetAddressUtil.getLoopbackInetAddress(), serverPort));

		return intraband.registerChannel(socketChannel);
	}

	@Override
	protected RegistrationReference weldServer(Intraband intraband)
		throws IOException {

		socketChannel = serverSocketChannel.accept();

		serverSocketChannel.close();

		_configureSocket(socketChannel.socket());

		return intraband.registerChannel(socketChannel);
	}

	protected final int bufferSize;
	protected final boolean keepAlive;
	protected final boolean reuseAddress;
	protected final int serverPort;
	protected final transient ServerSocketChannel serverSocketChannel;
	protected transient SocketChannel socketChannel;
	protected final int soLinger;
	protected final int soTimeout;
	protected final boolean tcpNoDelay;

	protected static class Configuration {

		protected static final int bufferSize = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_BUFFER_SIZE));

		protected static final boolean keepAlive = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_KEEP_ALIVE));

		protected static final boolean reuseAddress = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_REUSE_ADDRESS));

		protected static final int serverStartPort = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SERVER_START_PORT));

		protected static final int soLinger = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SO_LINGER));

		protected static final int soTimeout = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SO_TIMEOUT));

		protected static final boolean tcpNoDelay = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_TCP_NO_DELAY));

	}

	protected class SocketWelderServerSocketConfigurator
		implements ServerSocketConfigurator {

		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setReceiveBufferSize(bufferSize);
			serverSocket.setReuseAddress(reuseAddress);
			serverSocket.setSoTimeout(soTimeout);
		}

	}

	private void _configureSocket(Socket socket) throws SocketException {
		socket.setKeepAlive(keepAlive);
		socket.setReceiveBufferSize(bufferSize);
		socket.setReuseAddress(reuseAddress);
		socket.setSendBufferSize(bufferSize);

		if (soLinger <= 0) {
			socket.setSoLinger(false, soLinger);
		}
		else {
			socket.setSoLinger(true, soLinger);
		}

		socket.setSoTimeout(soTimeout);
		socket.setTcpNoDelay(tcpNoDelay);
	}

}