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
import com.liferay.portal.kernel.cluster.messaging.ClusterForwardMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.jgroups.Channel.State;
import org.jgroups.JChannel;
import org.jgroups.View;
import org.jgroups.util.UUID;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class ClusterLinkImplTest extends BaseClusterTestCase {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testDestroy1() throws Exception {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		clusterLinkImpl.destroy();
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testDestroy2() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Assert.assertEquals(2, jChannels.size());

			JChannel jChannel = jChannels.get(0);

			Assert.assertTrue(isOpen(jChannel));

			jChannel = jChannels.get(1);

			Assert.assertTrue(isOpen(jChannel));

			clusterLinkImpl.destroy();

			jChannels = getJChannels(clusterLinkImpl);

			Assert.assertEquals(2, jChannels.size());

			jChannel = jChannels.get(0);

			Assert.assertFalse(isOpen(jChannel));

			jChannel = jChannels.get(1);

			Assert.assertFalse(isOpen(jChannel));
		}
		finally {
			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
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

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Assert.assertEquals(2, jChannels.size());

			JChannel jChannel = jChannels.get(0);

			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL1));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL2));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL3));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL4));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL5));

			jChannel = jChannels.get(1);

			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL6));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL7));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL8));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL9));
			Assert.assertSame(
				jChannel, clusterLinkImpl.getChannel(Priority.LEVEL10));
		}
		finally {
			captureHandler.close();

			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testGetLocalTransportAddresses1() throws Exception {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		List<Address> addresses = clusterLinkImpl.getLocalTransportAddresses();

		Assert.assertSame(Collections.emptyList(), addresses);

		clusterLinkImpl.destroy();
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testGetLocalTransportAddresses2() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			List<Address> addresses =
				clusterLinkImpl.getLocalTransportAddresses();

			Assert.assertEquals(2, addresses.size());

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Assert.assertSame(
				getJGroupsAddress(jChannels, 0), getRealAddress(addresses, 0));
			Assert.assertSame(
				getJGroupsAddress(jChannels, 1), getRealAddress(addresses, 1));
		}
		finally {
			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testGetTransportAddressesByPriority1() throws Exception {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		List<Address> addresses = clusterLinkImpl.getTransportAddresses(
			Priority.LEVEL1);

		Assert.assertSame(Collections.emptyList(), addresses);

		clusterLinkImpl.destroy();
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testGetTransportAddressesByPriority2() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl1 = null;
		ClusterLinkImpl clusterLinkImpl2 = null;

		try {
			clusterLinkImpl1 = getClusterLinkImpl();
			clusterLinkImpl2 = getClusterLinkImpl();

			List<JChannel> jChannels1 = getJChannels(clusterLinkImpl1);

			Assert.assertEquals(2, jChannels1.size());

			List<JChannel> jChannels2 = getJChannels(clusterLinkImpl2);

			Assert.assertEquals(2, jChannels2.size());

			List<Address> addresses1 = clusterLinkImpl1.getTransportAddresses(
				Priority.LEVEL1);

			Assert.assertEquals(2, addresses1.size());

			List<Address> addresses2 = clusterLinkImpl1.getTransportAddresses(
				Priority.LEVEL6);

			Assert.assertEquals(2, addresses2.size());

			Assert.assertEquals(
				getJGroupsAddress(jChannels1, 0),
				getRealAddress(addresses1, 0));
			Assert.assertEquals(
				getJGroupsAddress(jChannels1, 1),
				getRealAddress(addresses2, 0));
			Assert.assertEquals(
				getJGroupsAddress(jChannels2, 0),
				getRealAddress(addresses1, 1));
			Assert.assertEquals(
				getJGroupsAddress(jChannels2, 1),
				getRealAddress(addresses2, 1));
		}
		finally {
			if (clusterLinkImpl1 != null) {
				clusterLinkImpl1.destroy();
			}

			if (clusterLinkImpl2 != null) {
				clusterLinkImpl2.destroy();
			}
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
	public void testInitChannel1() throws Exception {
		TransportationConfigurationAdvice.setChannelCount(
			ClusterLinkImpl.MAX_CHANNEL_COUNT + 1);

		try {
			getClusterLinkImpl();

			Assert.fail();
		}
		catch (IllegalStateException ise) {
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
	public void testInitChannel2() throws Exception {
		try {
			getClusterLinkImpl();

			Assert.fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testSendMulticastMessage1() throws Exception {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		Message message = createMessage();

		clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

		clusterLinkImpl.destroy();
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testSendMulticastMessage2() throws Exception {
		TransportationConfigurationAdvice.setChannelCount(1);

		ClusterLinkImpl clusterLinkImpl1 = null;
		ClusterLinkImpl clusterLinkImpl2 = null;
		ClusterLinkImpl clusterLinkImpl3 = null;

		try {
			clusterLinkImpl1 = getClusterLinkImpl();
			clusterLinkImpl2 = getClusterLinkImpl();
			clusterLinkImpl3 = getClusterLinkImpl();

			List<JChannel> jChannels1 = getJChannels(clusterLinkImpl1);
			List<JChannel> jChannels2 = getJChannels(clusterLinkImpl2);
			List<JChannel> jChannels3 = getJChannels(clusterLinkImpl3);

			TestReceiver testReceiver1 = getTestReceiver(jChannels1, 0);
			TestReceiver testReceiver2 = getTestReceiver(jChannels2, 0);
			TestReceiver testReceiver3 = getTestReceiver(jChannels3, 0);

			Message message = createMessage();

			clusterLinkImpl1.sendMulticastMessage(message, Priority.LEVEL1);

			String localMessage1 = testReceiver1.waitLocalMessage();
			String remoteMessage1 = testReceiver1.waitRemoteMessage();

			String localMessage2 = testReceiver2.waitLocalMessage();
			String remoteMessage2 = testReceiver2.waitRemoteMessage();

			String localMessage3 = testReceiver3.waitLocalMessage();
			String remoteMessage3 = testReceiver3.waitRemoteMessage();

			String messageKey = (String)message.getPayload();

			Assert.assertEquals(messageKey, localMessage1);
			Assert.assertNull(remoteMessage1);
			Assert.assertNull(localMessage2);
			Assert.assertEquals(messageKey, remoteMessage2);
			Assert.assertNull(localMessage3);
			Assert.assertEquals(messageKey, remoteMessage3);
		}
		finally {
			if (clusterLinkImpl1 != null) {
				clusterLinkImpl1.destroy();
			}

			if (clusterLinkImpl2 != null) {
				clusterLinkImpl2.destroy();
			}

			if (clusterLinkImpl3 != null) {
				clusterLinkImpl3.destroy();
			}
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
	public void testSendMulticastMessage3() {
		TransportationConfigurationAdvice.setChannelCount(1);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.WARNING);

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			Message message = createMessage();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			JChannel jChannel = jChannels.get(0);

			jChannel.close();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send multicast message " + message,
				IllegalStateException.class);
		}
		finally {
			captureHandler.close();

			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
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
	public void testSendMulticastMessage4() {
		TransportationConfigurationAdvice.setChannelCount(1);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.WARNING);

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Message message = createMessage();

			JChannel jChannel = jChannels.get(0);

			jChannel.disconnect();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send multicast message " + message,
				IllegalStateException.class);
		}
		finally {
			captureHandler.close();

			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testSendUnicastMessage1() throws Exception {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		Message message = createMessage();

		clusterLinkImpl.sendUnicastMessage(
			new AddressImpl(new MockAddress()), message, Priority.LEVEL1);

		clusterLinkImpl.destroy();
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}

	)
	@Test
	public void testSendUnicastMessage2() throws Exception {
		TransportationConfigurationAdvice.setChannelCount(1);

		ClusterLinkImpl clusterLinkImpl1 = null;
		ClusterLinkImpl clusterLinkImpl2 = null;

		try {
			clusterLinkImpl1 = getClusterLinkImpl();
			clusterLinkImpl2 = getClusterLinkImpl();

			List<JChannel> jChannels1 = getJChannels(clusterLinkImpl1);
			List<JChannel> jChannels2 = getJChannels(clusterLinkImpl2);

			TestReceiver testReceiver1 = getTestReceiver(jChannels1, 0);
			TestReceiver testReceiver2 = getTestReceiver(jChannels2, 0);

			Message message = createMessage();

			clusterLinkImpl1.sendUnicastMessage(
				new AddressImpl(jChannels2.get(0).getAddress()), message,
				Priority.LEVEL1);

			String localMessage1 = testReceiver1.waitLocalMessage();
			String remoteMessage1 = testReceiver1.waitRemoteMessage();
			String localMessage2 = testReceiver2.waitLocalMessage();
			String remoteMessage2 = testReceiver2.waitRemoteMessage();

			String messageKey = (String)message.getPayload();

			Assert.assertNull(localMessage1);
			Assert.assertNull(remoteMessage1);
			Assert.assertNull(localMessage2);
			Assert.assertEquals(messageKey, remoteMessage2);
		}
		finally {
			if (clusterLinkImpl1 != null) {
				clusterLinkImpl1.destroy();
			}

			if (clusterLinkImpl2 != null) {
				clusterLinkImpl2.destroy();
			}
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
	public void testSendUnicastMessage3() {
		TransportationConfigurationAdvice.setChannelCount(1);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.WARNING);

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Message message = createMessage();

			JChannel jChannel = jChannels.get(0);

			jChannel.close();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterLinkImpl.sendUnicastMessage(
				new AddressImpl(new MockAddress()), message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send unicast message " + message,
				IllegalStateException.class);
		}
		finally {
			captureHandler.close();

			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
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
	public void testSendUnicastMessage4() {
		TransportationConfigurationAdvice.setChannelCount(1);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterLinkImpl.class.getName(), Level.WARNING);

		ClusterLinkImpl clusterLinkImpl = null;

		try {
			clusterLinkImpl = getClusterLinkImpl();

			List<JChannel> jChannels = getJChannels(clusterLinkImpl);

			Message message = createMessage();

			JChannel jChannel = jChannels.get(0);

			jChannel.disconnect();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterLinkImpl.sendUnicastMessage(
				new AddressImpl(new MockAddress()), message, Priority.LEVEL1);

			assertLogger(
				logRecords, "Unable to send unicast message " + message,
				IllegalStateException.class);
		}
		finally {
			captureHandler.close();

			if (clusterLinkImpl != null) {
				clusterLinkImpl.destroy();
			}
		}
	}

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

	protected Message createMessage() {
		Message message = new Message();

		message.setPayload(UUID.randomUUID().toString());

		return message;
	}

	protected ClusterLinkImpl getClusterLinkImpl() {
		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterForwardMessageListener(
			new ClusterForwardMessageListener());

		clusterLinkImpl.afterPropertiesSet();

		if (clusterLinkImpl.isEnabled()) {
			Assert.assertNotNull(clusterLinkImpl.getBindInetAddress());
		}

		List<JChannel> jChannels = getJChannels(clusterLinkImpl);

		if (jChannels != null) {
			for (JChannel channel : jChannels) {
				channel.setReceiver(
					new TestReceiver(new AddressImpl(channel.getAddress())));
			}
		}

		return clusterLinkImpl;
	}

	protected List<JChannel> getJChannels(ClusterLinkImpl clusterLinkImpl) {
		return ReflectionTestUtil.getFieldValue(
			clusterLinkImpl, "_transportJChannels");
	}

	protected org.jgroups.Address getJGroupsAddress(
		List<JChannel> jChannels, int index) {

		JChannel jChannel = jChannels.get(index);

		return jChannel.getAddress();
	}

	protected Object getRealAddress(List<Address> addresses, int index) {
		Address address = addresses.get(index);

		return address.getRealAddress();
	}

	protected TestReceiver getTestReceiver(
		List<JChannel> jChannels, int index) {

		JChannel jChannel = jChannels.get(index);

		return (TestReceiver)jChannel.getReceiver();
	}

	protected boolean isOpen(JChannel jChannel) {
		String state = jChannel.getState();

		return !state.equals(State.CLOSED.toString());
	}

	private class TestReceiver extends BaseReceiver {

		public TestReceiver(Address address) {
			_localAddress = address;
		}

		@Override
		public void receive(org.jgroups.Message message) {
			org.jgroups.Address sourceJGroupsAddress = message.getSrc();

			Message content = (Message)message.getObject();

			String messageKey = (String)content.getPayload();

			try {
				if (sourceJGroupsAddress.equals(
						_localAddress.getRealAddress())) {

					_localMessageExchanger.exchange(messageKey);
				}
				else {
					_remoteMessageExchanger.exchange(messageKey);
				}
			}
			catch (InterruptedException ie) {
				Assert.fail();
			}
		}

		@Override
		public void viewAccepted(View view) {
			super.view = view;
		}

		public String waitLocalMessage() throws Exception {
			try {
				return _localMessageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		public String waitRemoteMessage() throws Exception {
			try {
				return _remoteMessageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		private Address _localAddress;
		private Exchanger<String> _localMessageExchanger =
			new Exchanger<String>();
		private Exchanger<String> _remoteMessageExchanger =
			new Exchanger<String>();

	}

}