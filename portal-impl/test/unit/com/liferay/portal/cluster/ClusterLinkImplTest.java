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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.jgroups.Channel.State;
import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.jgroups.util.UUID;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class ClusterLinkImplTest extends BaseClusterTestCase {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testDestroy() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		try {
			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Assert.assertEquals(2, jChannels.size());
			Assert.assertTrue(isOpen(jChannels.get(0)));
			Assert.assertTrue(isOpen(jChannels.get(1)));

			clusterLinkImpl.destroy();

			Assert.assertFalse(isOpen(jChannels.get(0)));
			Assert.assertFalse(isOpen(jChannels.get(1)));
		}
		finally {
			clusterLinkImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testGetChannel() {
		TransportationConfigurationAdvice.setChannelCount(2);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.FINE);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		try {
			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Assert.assertEquals(2, jChannels.size());
			Assert.assertSame(
				jChannels.get(0), clusterLinkImpl.getChannel(Priority.LEVEL1));
			Assert.assertSame(
				jChannels.get(0), clusterLinkImpl.getChannel(Priority.LEVEL2));
			Assert.assertSame(
				jChannels.get(0), clusterLinkImpl.getChannel(Priority.LEVEL3));
			Assert.assertSame(
				jChannels.get(0), clusterLinkImpl.getChannel(Priority.LEVEL4));
			Assert.assertSame(
				jChannels.get(0), clusterLinkImpl.getChannel(Priority.LEVEL5));
			Assert.assertSame(
				jChannels.get(1), clusterLinkImpl.getChannel(Priority.LEVEL6));
			Assert.assertSame(
				jChannels.get(1), clusterLinkImpl.getChannel(Priority.LEVEL7));
			Assert.assertSame(
				jChannels.get(1), clusterLinkImpl.getChannel(Priority.LEVEL8));
			Assert.assertSame(
				jChannels.get(1), clusterLinkImpl.getChannel(Priority.LEVEL9));
			Assert.assertSame(
				jChannels.get(1), clusterLinkImpl.getChannel(Priority.LEVEL10));
		}
		finally {
			captureHandler.close();

			clusterLinkImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testGetLocalTransportAddresses() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		try {
			List<Address> addresses =
				clusterLinkImpl.getLocalTransportAddresses();

			Assert.assertEquals(2, addresses.size());

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			assertAddresses(addresses, jChannels.get(0), jChannels.get(1));
		}
		finally {
			clusterLinkImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testGetTransportAddressesByPriority() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl1 = getClusterLinkImpl();
		ClusterLinkImpl clusterLinkImpl2 = getClusterLinkImpl();

		try {
			List<JChannel> jChannels1 = getJChannels(clusterLinkImpl1);
			List<JChannel> jChannels2 = getJChannels(clusterLinkImpl2);

			Assert.assertEquals(2, jChannels1.size());
			Assert.assertEquals(2, jChannels2.size());

			List<Address> addresses1 = clusterLinkImpl1.getTransportAddresses(
				Priority.LEVEL1);
			List<Address> addresses2 = clusterLinkImpl1.getTransportAddresses(
				Priority.LEVEL6);

			Assert.assertEquals(2, addresses1.size());
			Assert.assertEquals(2, addresses2.size());

			assertAddresses(addresses1, jChannels1.get(0), jChannels2.get(0));
			assertAddresses(addresses2, jChannels1.get(1), jChannels2.get(1));
		}
		finally {
			clusterLinkImpl1.destroy();
			clusterLinkImpl2.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testInitChannel() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.OFF);

		try {

			// Test 1, create ClusterLinkImpl#MAX_CHANNEL_COUNT channels

			TransportationConfigurationAdvice.setChannelCount(
				ClusterLinkImpl.MAX_CHANNEL_COUNT + 1);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			try {
				getClusterLinkImpl();

				Assert.fail();
			}
			catch (IllegalStateException ise) {
				Assert.assertEquals(0, logRecords.size());
				Assert.assertEquals(
					"java.lang.IllegalArgumentException: Channel count must " +
						"be between 1 and " + ClusterLinkImpl.MAX_CHANNEL_COUNT,
					ise.getMessage());
			}

			// Test 2, create 0 channels

			TransportationConfigurationAdvice.setChannelCount(0);

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			try {
				getClusterLinkImpl();

				Assert.fail();
			}
			catch (IllegalStateException ise) {
				Assert.assertEquals(1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"Unable to initialize channels", logRecord.getMessage());
				Assert.assertEquals(
					"java.lang.IllegalArgumentException: Channel count must " +
						"be between 1 and " + ClusterLinkImpl.MAX_CHANNEL_COUNT,
					ise.getMessage());
			}
		}
		finally {
			captureHandler.close();
		}
	}

	@AdviseWith(
		adviceClasses = {
			BaseReceiverAdvice.class, DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testSendMulticastMessage() throws Exception {
		TransportationConfigurationAdvice.setChannelCount(1);

		BaseReceiverAdvice.reset(3);

		ClusterLinkImpl clusterLinkImpl1 = getClusterLinkImpl();
		ClusterLinkImpl clusterLinkImpl2 = getClusterLinkImpl();
		ClusterLinkImpl clusterLinkImpl3 = getClusterLinkImpl();

		try {
			List<JChannel> jChannels1 = getJChannels(clusterLinkImpl1);
			List<JChannel> jChannels2 = getJChannels(clusterLinkImpl2);
			List<JChannel> jChannels3 = getJChannels(clusterLinkImpl3);

			JChannel jChannel1 = jChannels1.get(0);
			JChannel jChannel2 = jChannels2.get(0);
			JChannel jChannel3 = jChannels3.get(0);

			Receiver receiver1 = jChannel1.getReceiver();
			Receiver receiver2 = jChannel2.getReceiver();
			Receiver receiver3 = jChannel3.getReceiver();

			Message message = createMessage();

			clusterLinkImpl1.sendMulticastMessage(message, Priority.LEVEL1);

			org.jgroups.Address sourceJAddress = jChannel1.getAddress();

			Message receivedMessage1 =
				(Message)BaseReceiverAdvice.getJGroupsMessagePayload(
					receiver1, sourceJAddress);
			Message receivedMessage2 =
				(Message)BaseReceiverAdvice.getJGroupsMessagePayload(
					receiver2, sourceJAddress);
			Message receivedMessage3 =
				(Message)BaseReceiverAdvice.getJGroupsMessagePayload(
					receiver3, sourceJAddress);

			Assert.assertEquals(
				message.getPayload(), receivedMessage1.getPayload());
			Assert.assertEquals(
				message.getPayload(), receivedMessage2.getPayload());
			Assert.assertEquals(
				message.getPayload(), receivedMessage3.getPayload());
		}
		finally {
			clusterLinkImpl1.destroy();
			clusterLinkImpl2.destroy();
			clusterLinkImpl3.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testSendMulticastMessageWithError() {
		TransportationConfigurationAdvice.setChannelCount(1);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.WARNING);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		try {

			// Test 1, send message when cluster link is destroyed

			clusterLinkImpl.destroy();

			Message message = createMessage();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send multicast message " + message,
				IllegalStateException.class);

			// Test 2, send message when cluster link is disconnected

			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			JChannel jChannel = jChannels.get(0);

			jChannel.disconnect();

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send multicast message " + message,
				IllegalStateException.class);
		}
		finally {
			captureHandler.close();

			clusterLinkImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			BaseReceiverAdvice.class, DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testSendUnicastMessage() throws Exception {
		TransportationConfigurationAdvice.setChannelCount(1);

		BaseReceiverAdvice.reset(1);

		ClusterLinkImpl clusterLinkImpl1 = getClusterLinkImpl();
		ClusterLinkImpl clusterLinkImpl2 = getClusterLinkImpl();

		try {
			List<JChannel> jChannels1 = getJChannels(clusterLinkImpl1);
			List<JChannel> jChannels2 = getJChannels(clusterLinkImpl2);

			JChannel jChannel1 = jChannels1.get(0);
			JChannel jChannel2 = jChannels2.get(0);

			Receiver receiver1 = jChannel1.getReceiver();
			Receiver receiver2 = jChannel2.getReceiver();

			Message message = createMessage();

			clusterLinkImpl1.sendUnicastMessage(
				new AddressImpl(jChannels2.get(0).getAddress()), message,
				Priority.LEVEL1);

			org.jgroups.Address sourceJAddress = jChannel1.getAddress();

			Message receivedMessage1 =
				(Message)BaseReceiverAdvice.getJGroupsMessagePayload(
					receiver1, sourceJAddress);
			Message receivedMessage2 =
				(Message)BaseReceiverAdvice.getJGroupsMessagePayload(
					receiver2, sourceJAddress);

			Assert.assertNull(receivedMessage1);
			Assert.assertEquals(
				message.getPayload(), receivedMessage2.getPayload());
		}
		finally {
			clusterLinkImpl1.destroy();
			clusterLinkImpl2.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testSendUnicastMessageWithError() {
		TransportationConfigurationAdvice.setChannelCount(1);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.WARNING);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		try {

			// Test 1, send message when cluster link is destroyed

			clusterLinkImpl.destroy();

			Message message = createMessage();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterLinkImpl.sendUnicastMessage(
				new AddressImpl(new MockAddress()), message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send unicast message " + message,
				IllegalStateException.class);

			// Test 2, send message when cluster link is disconnected

			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			JChannel jChannel = jChannels.get(0);

			jChannel.disconnect();

			logRecords = captureHandler.resetLogLevel(Level.WARNING);

			clusterLinkImpl.sendUnicastMessage(
				new AddressImpl(new MockAddress()), message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send unicast message " + message,
				IllegalStateException.class);
		}
		finally {
			captureHandler.close();

			clusterLinkImpl.destroy();
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testWithClusterDisabled() throws Exception {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		List<Address> addresses = clusterLinkImpl.getLocalTransportAddresses();

		Assert.assertSame(Collections.emptyList(), addresses);

		addresses = clusterLinkImpl.getTransportAddresses(Priority.LEVEL1);

		Assert.assertSame(Collections.emptyList(), addresses);

		clusterLinkImpl.sendMulticastMessage(createMessage(), Priority.LEVEL1);

		clusterLinkImpl.sendUnicastMessage(
			new AddressImpl(new MockAddress()), createMessage(),
			Priority.LEVEL1);

		clusterLinkImpl.destroy();
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		new AspectJNewEnvTestRule();

	@Aspect
	public static class TransportationConfigurationAdvice {

		public static void setChannelCount(int channelCount) {
			_CHANNEL_COUNT = channelCount;
		}

		@Around(
			"execution(* com.liferay.portal.util.PropsUtil.getProperties(" +
				"String, boolean))")
		public Object getTransportationConfigurationProperties(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Object[] arguments = proceedingJoinPoint.getArgs();

			if (PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT.equals(
					arguments[0]) &&
				Boolean.TRUE.equals(arguments[1])) {

				Properties properties = new Properties();

				for (int i = 0; i < _CHANNEL_COUNT; i++) {
					properties.put(
						PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT +
							CharPool.POUND + i, "udp.xml");
				}

				return properties;
			}

			return proceedingJoinPoint.proceed();
		}

		private static int _CHANNEL_COUNT = 0;

	}

	protected void assertAddresses(
		List<Address> addresses, JChannel... jChannels) {

		Assert.assertEquals(addresses.size(), jChannels.length);

		for (JChannel jChannel : jChannels) {
			Assert.assertTrue(
				addresses.contains(new AddressImpl(jChannel.getAddress())));
		}
	}

	protected Message createMessage() {
		Message message = new Message();

		UUID uuid = UUID.randomUUID();

		message.setPayload(uuid.toString());

		return message;
	}

	protected ClusterLinkImpl getClusterLinkImpl() {
		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.afterPropertiesSet();

		clusterLinkImpl.initialize();

		if (clusterLinkImpl.isEnabled()) {
			Assert.assertNotNull(clusterLinkImpl.getBindInetAddress());
		}

		return clusterLinkImpl;
	}

	protected List<JChannel> getJChannels(ClusterLinkImpl clusterLinkImpl) {
		return ReflectionTestUtil.getFieldValue(
			clusterLinkImpl, "_transportJChannels");
	}

	protected boolean isOpen(JChannel jChannel) {
		String state = jChannel.getState();

		return !state.equals(State.CLOSED.toString());
	}

}