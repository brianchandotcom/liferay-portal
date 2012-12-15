/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;
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
public class SocketWelder implements Welder {

	public SocketWelder() throws IOException {
		serverSocketChannel = SocketUtil.createServerSocketChannel(
			InetAddressUtil.getLoopbackInetAddress(), serverStartPort,
			new SocketWelderServerSocketConfigurator());
		serverPort = serverSocketChannel.socket().getLocalPort();
	}

	public RegistrationReference weldClient(IntraBand intraBand)
		throws IOException {

		SocketChannel socketChannel = SocketChannel.open();

		_configureSocket(socketChannel.socket());

		socketChannel.connect(
			new InetSocketAddress(
				InetAddressUtil.getLoopbackInetAddress(), serverPort));

		return intraBand.registerChannel(socketChannel);
	}

	public RegistrationReference weldServer(IntraBand intraBand)
		throws IOException {

		SocketChannel socketChannel = serverSocketChannel.accept();

		serverSocketChannel.close();

		_configureSocket(socketChannel.socket());

		return intraBand.registerChannel(socketChannel);
	}

	/**
	 * Technically the following properties are constants, which means they
	 * suppose to be static. But making them static will cause
	 * PropsUtil/PropsKeys class loading on class initialization, which is not
	 * allowed on Portal Resiliency bootstrap.
	 *
	 * To relieve this classloading dependence, they are kept as instance
	 * fields, so the serialization mechanism will copy the server side
	 * configuration to client side. On server side the values are loaded from
	 * properties which requires PropsUtil/PropsKeys, but on client side
	 * deserialization logic will directly assgin the values without bothering
	 * to load the properties.
	 */
	protected final int bufferSize = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_BUFFER_SIZE));

	protected final boolean keepAlive = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_KEEP_ALIVE));

	protected final boolean reuseAddress = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_REUSE_ADDRESS));

	protected final int serverPort;

	protected final transient ServerSocketChannel serverSocketChannel;

	protected final int serverStartPort = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SERVER_START_PORT));

	protected final int solinger = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SOLINGER));

	protected final int sotimeout = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SOTIMEOUT));

	protected final boolean tcpNodeplay = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_TCP_NODELAY));

	protected class SocketWelderServerSocketConfigurator
		implements ServerSocketConfigurator {

		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setReceiveBufferSize(bufferSize);
			serverSocket.setReuseAddress(reuseAddress);
			serverSocket.setSoTimeout(sotimeout);
		}

	}

	private void _configureSocket(Socket socket) throws SocketException {
		socket.setKeepAlive(keepAlive);
		socket.setReceiveBufferSize(bufferSize);
		socket.setReuseAddress(reuseAddress);
		socket.setSendBufferSize(bufferSize);
		if (solinger <= 0) {
			socket.setSoLinger(false, solinger);
		}
		else {
			socket.setSoLinger(true, solinger);
		}

		socket.setSoTimeout(sotimeout);
		socket.setTcpNoDelay(tcpNodeplay);
	}

}