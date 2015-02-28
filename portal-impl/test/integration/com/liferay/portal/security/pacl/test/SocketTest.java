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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.PACLTestRule;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class SocketTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testAccept1() throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(4316)) {
			serverSocket.setReuseAddress(true);
			serverSocket.setSoTimeout(50);

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try (Socket socket = new Socket()) {
						socket.setReuseAddress(true);
						socket.setSoLinger(false, 0);

						socket.bind(new InetSocketAddress("localhost", 4320));

						socket.connect(
							new InetSocketAddress("localhost", 4316), 10);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			};

			Thread thread = new Thread(runnable);

			thread.start();

			Socket socket = serverSocket.accept();

			socket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testAccept2() throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(4316)) {
			serverSocket.setReuseAddress(true);
			serverSocket.setSoTimeout(50);

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try (Socket socket = new Socket()) {
						socket.setReuseAddress(true);
						socket.setSoLinger(false, 0);

						socket.bind(new InetSocketAddress("localhost", 4321));

						socket.connect(
							new InetSocketAddress("localhost", 4316), 10);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			};

			Thread thread = new Thread(runnable);

			thread.start();

			Socket socket = serverSocket.accept();

			socket.close();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testConnect1() throws IOException {
		try {
			HttpUtil.URLtoString("http://www.cbs.com");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
		catch (SocketException se) {
		}
	}

	@Test
	public void testConnect2() throws IOException {
		try {
			HttpUtil.URLtoString("http://www.cnn.com");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testConnect3() throws IOException {
		try (Socket socket = new Socket()) {
			socket.setReuseAddress(true);
			socket.setSoLinger(false, 0);

			socket.connect(new InetSocketAddress("www.google.com", 443));
		}
		catch (SecurityException se) {
			Assert.fail();
		}
		catch (SocketException se) {
		}
	}

	@Test
	public void testConnect4() throws IOException {
		try (Socket socket = new Socket()) {
			socket.setReuseAddress(true);
			socket.setSoLinger(false, 0);

			socket.connect(new InetSocketAddress("www.google.com", 80));
		}
		catch (SecurityException se) {
			Assert.fail();
		}
		catch (SocketException se) {
		}
	}

	@Test
	public void testConnect5() throws IOException {
		try (Socket socket = new Socket()) {
			socket.setReuseAddress(true);
			socket.setSoLinger(false, 0);

			socket.connect(new InetSocketAddress("www.msn.com", 80));

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testConnect6() throws IOException {
		try (Socket socket = new Socket()) {
			socket.setReuseAddress(true);
			socket.setSoLinger(false, 0);

			socket.connect(new InetSocketAddress("www.yahoo.com", 443));
		}
		catch (SecurityException se) {
			Assert.fail();
		}
		catch (SocketException se) {
		}
	}

	@Test
	public void testConnect7() throws IOException {
		try (Socket socket = new Socket()) {
			socket.setReuseAddress(true);
			socket.setSoLinger(false, 0);

			socket.connect(new InetSocketAddress("www.yahoo.com", 80));

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testListen1() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4315)) {
			serverSocket.setReuseAddress(true);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testListen2() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4316)) {
			serverSocket.setReuseAddress(true);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testListen3() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4317)) {
			serverSocket.setReuseAddress(true);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testListen4() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4318)) {
			serverSocket.setReuseAddress(true);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testListen5() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4319)) {
			serverSocket.setReuseAddress(true);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testListen6() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4320)) {
			serverSocket.setReuseAddress(true);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void testListen7() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(4321)) {
			serverSocket.setReuseAddress(true);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}