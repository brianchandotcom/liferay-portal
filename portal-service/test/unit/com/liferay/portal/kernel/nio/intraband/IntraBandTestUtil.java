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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.SocketUtil;

import java.io.IOException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Shuyang Zhou
 */
public class IntraBandTestUtil {

	public static <T> T createProxy(Class<?>... interfaces) {
		return (T)ProxyUtil.newProxyInstance(
			IntraBandTestUtil.class.getClassLoader(), interfaces,
			new InvocationHandler() {

				public Object invoke(
					Object proxy, Method method, Object[] args) {

					throw new UnsupportedOperationException();
				}
			});
	}

	public static SocketChannel[] createSocketChannelPeers()
		throws IOException {

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 15238, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		SocketChannel clientPeerSocketChannel = SocketChannel.open(
			new InetSocketAddress(
				InetAddress.getLocalHost(), serverSocket.getLocalPort()));

		SocketChannel serverPeerSocketChannel = serverSocketChannel.accept();

		serverSocketChannel.close();

		SocketChannel[] socketChannels = new SocketChannel[2];

		socketChannels[0] = serverPeerSocketChannel;
		socketChannels[1] = clientPeerSocketChannel;

		return socketChannels;
	}

	private static ServerSocketConfigurator _serverSocketConfigurator =
		new ServerSocketConfigurator() {

		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setReuseAddress(true);
		}

	};

}