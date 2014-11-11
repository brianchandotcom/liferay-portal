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

package com.liferay.portal.kernel.nio.intraband.welder.socket;

import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.test.WelderTestUtil;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;

import java.net.ServerSocket;

import java.nio.channels.ServerSocketChannel;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class SocketWelderTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

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
			PropsKeys.INTRABAND_WELDER_SOCKET_SO_LINGER, Integer.toString(0));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_SO_TIMEOUT, Integer.toString(0));
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_TCP_NO_DELAY,
			Boolean.toString(false));
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testConfiguration() {
		Assert.assertEquals(8192, SocketWelder.Configuration.bufferSize);
		Assert.assertFalse(SocketWelder.Configuration.keepAlive);
		Assert.assertFalse(SocketWelder.Configuration.reuseAddress);
		Assert.assertEquals(3414, SocketWelder.Configuration.serverStartPort);
		Assert.assertEquals(0, SocketWelder.Configuration.soLinger);
		Assert.assertEquals(0, SocketWelder.Configuration.soTimeout);
		Assert.assertFalse(SocketWelder.Configuration.tcpNoDelay);
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testConstructor() throws Exception {
		SocketWelder socketWelder = new SocketWelder();

		new SocketWelder.Configuration();

		int serverPort = socketWelder.serverPort;

		Assert.assertTrue(
			serverPort >= SocketWelder.Configuration.serverStartPort);

		ServerSocketChannel serverSocketChannel =
			socketWelder.serverSocketChannel;

		Assert.assertNotNull(serverSocketChannel);

		ServerSocket serverSocket = serverSocketChannel.socket();

		Assert.assertEquals(
			socketWelder.bufferSize, serverSocket.getReceiveBufferSize());
		Assert.assertEquals(
			socketWelder.reuseAddress, serverSocket.getReuseAddress());
		Assert.assertEquals(
			socketWelder.soTimeout, serverSocket.getSoTimeout());
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testWeldSolingerOff() throws Exception {
		PropsUtilAdvice.setProps(
			PropsKeys.INTRABAND_WELDER_SOCKET_SO_LINGER, Integer.toString(10));

		testWeldSolingerOn();
	}

	@AdviseWith(adviceClasses = {PropsUtilAdvice.class})
	@Test
	public void testWeldSolingerOn() throws Exception {
		final SocketWelder serverSocketWelder = new SocketWelder();
		final SocketWelder clientSocketWelder = WelderTestUtil.transform(
			serverSocketWelder);

		FutureTask<MockRegistrationReference> serverWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					@Override
					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)
							serverSocketWelder.weld(new MockIntraband());
					}
				});

		Thread serverWeldingThread = new Thread(serverWeldingTask);

		serverWeldingThread.start();

		FutureTask<MockRegistrationReference> clientWeldingTask =
			new FutureTask<MockRegistrationReference>(
				new Callable<MockRegistrationReference>() {

					@Override
					public MockRegistrationReference call() throws Exception {
						return (MockRegistrationReference)
							clientSocketWelder.weld(new MockIntraband());
					}
				});

		Thread clientWeldingThread = new Thread(clientWeldingTask);

		clientWeldingThread.start();

		MockRegistrationReference serverMockRegistrationReference =
			serverWeldingTask.get();

		MockRegistrationReference clientMockRegistrationReference =
			clientWeldingTask.get();

		WelderTestUtil.assertConnectted(
			serverMockRegistrationReference.getScatteringByteChannel(),
			clientMockRegistrationReference.getGatheringByteChannel());
		WelderTestUtil.assertConnectted(
			clientMockRegistrationReference.getScatteringByteChannel(),
			serverMockRegistrationReference.getGatheringByteChannel());

		serverSocketWelder.destroy();
		clientSocketWelder.destroy();

		try {
			serverSocketWelder.weld(new MockIntraband());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Unable to weld a welder with state DESTROYED",
				ise.getMessage());
		}

		try {
			clientSocketWelder.weld(new MockIntraband());

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Unable to weld a welder with state DESTROYED",
				ise.getMessage());
		}
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

}