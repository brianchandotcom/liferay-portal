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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;

import java.io.IOException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.logging.LogRecord;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class IntrabandTestUtil {

	public static void assertMessageStartWith(
		LogRecord logRecord, String messagePrefix) {

		String message = logRecord.getMessage();

		Assert.assertTrue(message.startsWith(messagePrefix));
	}

	public static <T> T createProxy(Class<?>... interfaces) {
		return (T)ProxyUtil.newProxyInstance(
			IntrabandTestUtil.class.getClassLoader(), interfaces,
			new InvocationHandler() {

				@Override
				public Object invoke(
					Object proxy, Method method, Object[] args) {

					throw new UnsupportedOperationException();
				}

			});
	}

	public static SocketChannel[] createSocketChannelPeers()
		throws IOException {

		SocketChannel clientPeerSocketChannel = null;
		SocketChannel serverPeerSocketChannel = null;

		try (ServerSocketChannel serverSocketChannel =
				SocketUtil.createServerSocketChannel(
					InetAddress.getLocalHost(), 15238,
					_serverSocketConfigurator)) {

			ServerSocket serverSocket = serverSocketChannel.socket();

			clientPeerSocketChannel = SocketChannel.open(
				new InetSocketAddress(
					InetAddress.getLocalHost(), serverSocket.getLocalPort()));

			serverPeerSocketChannel = serverSocketChannel.accept();
		}

		SocketChannel[] socketChannels = new SocketChannel[2];

		socketChannels[0] = serverPeerSocketChannel;
		socketChannels[1] = clientPeerSocketChannel;

		return socketChannels;
	}

	public static Datagram readDatagramFully(
			ScatteringByteChannel scatteringByteChannel)
		throws IOException {

		Datagram datagram = DatagramHelper.createReceiveDatagram();

		while (!DatagramHelper.readFrom(datagram, scatteringByteChannel));

		return datagram;
	}

	private static final ServerSocketConfigurator _serverSocketConfigurator =
		new ServerSocketConfigurator() {

		@Override
		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setReuseAddress(true);
		}

	};

}