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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;

import java.net.ServerSocket;

import java.nio.channels.ServerSocketChannel;

import java.util.Map;
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
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Before
	public void setUp() {
		_properties = HashMapBuilder.<String, Object>put(
			PropsKeys.INTRABAND_WELDER_SOCKET_BUFFER_SIZE, String.valueOf(8192)
		).put(
			PropsKeys.INTRABAND_WELDER_SOCKET_KEEP_ALIVE,
			Boolean.toString(false)
		).put(
			PropsKeys.INTRABAND_WELDER_SOCKET_REUSE_ADDRESS,
			Boolean.toString(false)
		).put(
			PropsKeys.INTRABAND_WELDER_SOCKET_SERVER_START_PORT,
			String.valueOf(3414)
		).put(
			PropsKeys.INTRABAND_WELDER_SOCKET_SO_LINGER, String.valueOf(0)
		).put(
			PropsKeys.INTRABAND_WELDER_SOCKET_SO_TIMEOUT, String.valueOf(0)
		).put(
			PropsKeys.INTRABAND_WELDER_SOCKET_TCP_NO_DELAY,
			Boolean.toString(false)
		).build();

		PropsTestUtil.setProps(_properties);
	}

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

	@Test
	public void testWeldSolingerOff() throws Exception {
		_properties.put(
			PropsKeys.INTRABAND_WELDER_SOCKET_SO_LINGER, String.valueOf(10));

		testWeldSolingerOn();
	}

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

	private Map<String, Object> _properties;

}