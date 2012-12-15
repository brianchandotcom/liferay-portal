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

import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.net.ConnectException;
import java.net.ServerSocket;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.ServerSocketChannel;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class SocketWelderTest {

	@Before
	public void setUp() {
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_BUFFER_SIZE,
			Integer.toString(8192));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_KEEP_ALIVE,
			Boolean.toString(false));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_REUSE_ADDRESS,
			Boolean.toString(false));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_SERVER_START_PORT,
			Integer.toString(3414));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_SOLINGER, Integer.toString(0));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_SOTIMEOUT, Integer.toString(0));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_TCP_NODELAY,
			Boolean.toString(false));
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testConstructor() throws Exception {
		SocketWelder socketWelder = new SocketWelder();

		int serverPort = socketWelder.serverPort;

		Assert.assertTrue(serverPort >= socketWelder.serverStartPort);

		ServerSocketChannel serverSocketChannel =
			socketWelder.serverSocketChannel;

		Assert.assertNotNull(serverSocketChannel);

		ServerSocket serverSocket = serverSocketChannel.socket();

		Assert.assertEquals(
			socketWelder.bufferSize, serverSocket.getReceiveBufferSize());
		Assert.assertEquals(
			socketWelder.reuseAddress, serverSocket.getReuseAddress());
		Assert.assertEquals(
			socketWelder.sotimeout, serverSocket.getSoTimeout());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testWeldingSolingerOff() throws Exception {

		// Satisfy code coverage

		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_SOLINGER, Integer.toString(10));

		testWeldingSolingerOn();
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testWeldingSolingerOn() throws Exception {

		// 1) Normal welding

		final SocketWelder socketWelder = new SocketWelder();

		FutureTask<MockRegistrationReference> serverWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)
							socketWelder.weldServer(new MockIntraBand());
					}
				});

		FutureTask<MockRegistrationReference> clientWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)
							socketWelder.weldClient(new MockIntraBand());
					}
				});

		new Thread(serverWeldingTask).start();
		new Thread(clientWeldingTask).start();

		MockRegistrationReference serverMockRegistrationReference =
			serverWeldingTask.get();
		MockRegistrationReference clientMockRegistrationReference =
			clientWeldingTask.get();

		_ensureConnectted(
			serverMockRegistrationReference.getScatteringByteChannel(),
			clientMockRegistrationReference.getGatheringByteChannel());
		_ensureConnectted(
			clientMockRegistrationReference.getScatteringByteChannel(),
			serverMockRegistrationReference.getGatheringByteChannel());

		serverMockRegistrationReference.closeChannels();
		clientMockRegistrationReference.closeChannels();

		// 2) Welding on used Welder

		try {
			socketWelder.weldServer(new MockIntraBand());

			Assert.fail();
		}
		catch (ClosedChannelException cce) {
		}
		finally {
			socketWelder.serverSocketChannel.close();
		}

		try {
			socketWelder.weldClient(new MockIntraBand());

			Assert.fail();
		}
		catch (ConnectException ce) {
		}
		finally {
			socketWelder.serverSocketChannel.close();
		}
	}

	private static void _ensureConnectted(
			final ScatteringByteChannel scatteringByteChannel,
			final GatheringByteChannel gatheringByteChannel)
		throws Exception {

		Random random = new Random();

		final byte[] data = new byte[1024 * 1024];

		random.nextBytes(data);

		FutureTask<Void> writeFutureTask = new FutureTask<Void>(
			new Callable<Void>() {

				public Void call() throws Exception {
					ByteBuffer byteBuffer = ByteBuffer.wrap(data);

					while (byteBuffer.hasRemaining()) {
						gatheringByteChannel.write(byteBuffer);
					}

					return null;
				}
			});

		FutureTask<byte[]> readFutureTask = new FutureTask<byte[]>(
			new Callable<byte[]>() {

				public byte[] call() throws Exception {
					ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);

					while (byteBuffer.hasRemaining()) {
						scatteringByteChannel.read(byteBuffer);
					}

					return byteBuffer.array();
				}
			});

		new Thread(writeFutureTask).start();
		new Thread(readFutureTask).start();

		writeFutureTask.get();

		byte[] readData = readFutureTask.get();

		Assert.assertArrayEquals(data, readData);
	}

}