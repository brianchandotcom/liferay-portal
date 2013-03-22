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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class SocketTest {

	@Test
	public void accept1() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4316);

			serverSocket.setSoTimeout(50);

			try {
				Runnable runnable = new Runnable() {

					public void run() {
						Socket socket = new Socket();

						try {
							socket.setSoLinger(true, 0);

							socket.bind(
								new InetSocketAddress("localhost", 4320));

							socket.connect(
								new InetSocketAddress("localhost", 4316), 10);
						}
						catch (Exception e) {
							throw new RuntimeException(e);
						}
						finally {
							try {
								socket.close();
							}
							catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
					}

				};

				Thread thread = new Thread(runnable);

				thread.start();

				Socket socket = serverSocket.accept();

				socket.close();
			}
			finally {
				serverSocket.close();
			}
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void accept2() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4316);

			serverSocket.setSoTimeout(50);

			try {
				Runnable runnable = new Runnable() {

					public void run() {
						Socket socket = new Socket();

						try {
							socket.setSoLinger(true, 0);

							socket.bind(
								new InetSocketAddress("localhost", 4321));

							socket.connect(
								new InetSocketAddress("localhost", 4316), 10);
						}
						catch (Exception e) {
							throw new RuntimeException(e);
						}
						finally {
							try {
								socket.close();
							}
							catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
					}

				};

				Thread thread = new Thread(runnable);

				thread.start();

				Socket socket = serverSocket.accept();

				socket.close();
			}
			finally {
				serverSocket.close();
			}

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void connect1() throws Exception {
		try {
			HttpUtil.URLtoString("http://www.abc.com");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void connect2() throws Exception {
		try {
			HttpUtil.URLtoString("http://www.cbs.com");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void connect3() throws Exception {
		try {
			Socket socket = new Socket("www.google.com", 443);

			socket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void connect4() throws Exception {
		try {
			Socket socket = new Socket("www.google.com", 80);

			socket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void connect5() throws Exception {
		try {
			Socket socket = new Socket("www.msn.com", 80);

			socket.close();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void connect6() throws Exception {
		try {
			Socket socket = new Socket("www.yahoo.com", 443);

			socket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void connect7() throws Exception {
		try {
			Socket socket = new Socket("www.yahoo.com", 80);

			socket.close();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void listen1() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4315);

			serverSocket.close();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void listen2() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4316);

			serverSocket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void listen3() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4317);

			serverSocket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void listen4() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4318);

			serverSocket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void listen5() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4319);

			serverSocket.close();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void listen6() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4320);

			serverSocket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void listen7() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(4321);

			serverSocket.close();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

}