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

import com.liferay.portal.kernel.nio.intraband.IntraBand;
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

		// These assignments have to stay in the constructor because we need to
		// differentiate between a constructor created SocketWelder and a
		// deserialization created SocketWelder. Only the constructor created
		// welder needs an initialization from Configuration. The
		// deserialization created welder has the configuration from the
		// original SocketWelder.

		bufferSize = Configuration.bufferSize;
		keepAlive = Configuration.keepAlive;
		reuseAddress = Configuration.reuseAddress;
		solinger = Configuration.solinger;
		sotimeout = Configuration.sotimeout;
		tcpNodeplay = Configuration.tcpNodeplay;

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
	protected RegistrationReference weldClient(IntraBand intraBand)
		throws IOException {

		socketChannel = SocketChannel.open();

		_configureSocket(socketChannel.socket());

		socketChannel.connect(
			new InetSocketAddress(
				InetAddressUtil.getLoopbackInetAddress(), serverPort));

		return intraBand.registerChannel(socketChannel);
	}

	@Override
	protected RegistrationReference weldServer(IntraBand intraBand)
		throws IOException {

		socketChannel = serverSocketChannel.accept();

		serverSocketChannel.close();

		_configureSocket(socketChannel.socket());

		return intraBand.registerChannel(socketChannel);
	}

	protected final int bufferSize;
	protected final boolean keepAlive;
	protected final boolean reuseAddress;
	protected final int serverPort;
	protected final transient ServerSocketChannel serverSocketChannel;
	protected transient SocketChannel socketChannel;
	protected final int solinger;
	protected final int sotimeout;
	protected final boolean tcpNodeplay;

	/**
	 * This inner static class is used for lazy properties loading.
	 *
	 * On MPI side, this class loads exactly once to fetch up all required
	 * properties, so all following created SockerWelders won't have to reload
	 * them.
	 *
	 * On SPI side, this class never load (unless this SPI launches its own SPI,
	 * which turns itself to MPI). The properties values are directly injected
	 * into the SockerWelders by deserilaization. This is crucial, because
	 * deserilaization of SockerWelders happens before Portal initialization, if
	 * these configuration properties are held as SockerWelder's static field,
	 * the invocation to PropsUtil will fail the deserilaization.
	 */
	protected static class Configuration {

		protected static final int bufferSize = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_BUFFER_SIZE));

		protected static final boolean keepAlive = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_KEEP_ALIVE));

		protected static final boolean reuseAddress = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_REUSE_ADDRESS));

		protected static final int serverStartPort = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SERVER_START_PORT));

		protected static final int solinger = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SOLINGER));

		protected static final int sotimeout = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_SOTIMEOUT));

		protected static final boolean tcpNodeplay = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.INTRABAND_WELDER_SOCKET_TCP_NODELAY));

	}

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