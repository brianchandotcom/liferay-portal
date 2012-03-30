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

package com.liferay.portal.cluster;

import com.liferay.portal.configuration.ConfigurationImpl;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.cluster.messaging.ClusterForwardMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.NewClassLoaderTestCase;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsFiles;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.PwdGenerator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.View;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class ClusterLinkImplTest extends  NewClassLoaderTestCase {

	public void testDestroy1() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, false);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testDestroy2() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(2, true);

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				assertEquals(2, channelList.size());

				assertTrue(channelList.get(0).isOpen());
				assertTrue(channelList.get(1).isOpen());

				clusterLinkImpl.destroy();

				channelList = _getChannelList(clusterLinkImpl);

				assertEquals(2, channelList.size());

				assertFalse(channelList.get(0).isOpen());
				assertFalse(channelList.get(1).isOpen());

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetChannel() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(2, true);

				_handleLog(ClusterLinkImpl.class, Level.FINE);

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				assertEquals(2, channelList.size());

				JChannel channel1 = channelList.get(0);
				JChannel channel2 = channelList.get(1);

				assertSame(
					channel1, clusterLinkImpl.getChannel(Priority.LEVEL1));
				assertSame(
					channel1, clusterLinkImpl.getChannel(Priority.LEVEL2));
				assertSame(
					channel1, clusterLinkImpl.getChannel(Priority.LEVEL3));
				assertSame(
					channel1, clusterLinkImpl.getChannel(Priority.LEVEL4));
				assertSame(
					channel1, clusterLinkImpl.getChannel(Priority.LEVEL5));

				assertSame(
					channel2, clusterLinkImpl.getChannel(Priority.LEVEL6));
				assertSame(
					channel2, clusterLinkImpl.getChannel(Priority.LEVEL7));
				assertSame(
					channel2, clusterLinkImpl.getChannel(Priority.LEVEL8));
				assertSame(
					channel2, clusterLinkImpl.getChannel(Priority.LEVEL9));
				assertSame(
					channel2, clusterLinkImpl.getChannel(Priority.LEVEL10));

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetLocalTransportAddresses1() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, false);

				List<Address> addresses =
					clusterLinkImpl.getLocalTransportAddresses();

				assertTrue(addresses.isEmpty());

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetLocalTransportAddresses2() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(2, true);

				List<Address> addresses =
					clusterLinkImpl.getLocalTransportAddresses();

				assertEquals(2, addresses.size());

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				assertEquals(2, addresses.size());

				org.jgroups.Address expectedAddress1 = channelList.get(
					0).getAddress();
				org.jgroups.Address expectedAddress2 = channelList.get(
					1).getAddress();

				assertEquals(2, addresses.size());

				assertSame(expectedAddress1, addresses.get(0).getRealAddress());
				assertSame(expectedAddress2, addresses.get(1).getRealAddress());

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetTransportAddressesByPriority1() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, false);

				List<Address> addresses = clusterLinkImpl.getTransportAddresses(
					Priority.LEVEL1);

				assertTrue(addresses.isEmpty());

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetTransportAddressesByPriority2() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl1 = _getClusterLinkImpl(2, true);
				ClusterLinkImpl clusterLinkImpl2 = _getClusterLinkImpl(2, true);

				List<JChannel> channelList1 = _getChannelList(clusterLinkImpl1);
				List<JChannel> channelList2 = _getChannelList(clusterLinkImpl2);

				assertEquals(2, channelList1.size());
				assertEquals(2, channelList2.size());

				List<Address> addresses1 =
					clusterLinkImpl1.getTransportAddresses(Priority.LEVEL1);
				List<Address> addresses2 =
					clusterLinkImpl1.getTransportAddresses(Priority.LEVEL6);

				assertEquals(2, addresses1.size());
				assertEquals(2, addresses2.size());

				org.jgroups.Address exceptedAddress1 = channelList1.get(
					0).getAddress();
				org.jgroups.Address exceptedAddress2 = channelList1.get(
					1).getAddress();
				org.jgroups.Address exceptedAddress3 = channelList2.get(
					0).getAddress();
				org.jgroups.Address exceptedAddress4 = channelList2.get(
					1).getAddress();

				assertEquals(
					exceptedAddress1, addresses1.get(0).getRealAddress());
				assertEquals(
					exceptedAddress3, addresses1.get(1).getRealAddress());

				assertEquals(
					exceptedAddress2, addresses2.get(0).getRealAddress());
				assertEquals(
					exceptedAddress4, addresses2.get(1).getRealAddress());

				clusterLinkImpl1.destroy();
				clusterLinkImpl2.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testInitChannel1() throws Exception {
		//channel number > _MAX_CHANNEL_COUNT

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				try {
					_getClusterLinkImpl(11, true);

					fail();
				}
				catch (IllegalStateException ise) {
				}

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testInitChannel2() throws Exception {
		//channel number = 0

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				try {
					_getClusterLinkImpl(0, true);

					fail();
				}
				catch (IllegalStateException ise) {
				}

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage1() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, false);

				Message message = _createMessage();

				clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage2() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl1 = _getClusterLinkImpl(1, true);
				ClusterLinkImpl clusterLinkImpl2 = _getClusterLinkImpl(1, true);
				ClusterLinkImpl clusterLinkImpl3 = _getClusterLinkImpl(1, true);

				List<JChannel> channelList1 = _getChannelList(clusterLinkImpl1);
				List<JChannel> channelList2 = _getChannelList(clusterLinkImpl2);
				List<JChannel> channelList3 = _getChannelList(clusterLinkImpl3);

				TestReceiver testReceiver1 =
					(TestReceiver)channelList1.get(0).getReceiver();
				TestReceiver testReceiver2 =
					(TestReceiver)channelList2.get(0).getReceiver();
				TestReceiver testReceiver3 =
					(TestReceiver)channelList3.get(0).getReceiver();

				Message message = _createMessage();

				clusterLinkImpl1.sendMulticastMessage(message, Priority.LEVEL1);

				String localMessage1 = testReceiver1.waitLocalMessage();
				String remoteMessage1 = testReceiver1.waitRemoteMessage();

				String localMessage2 = testReceiver2.waitLocalMessage();
				String remoteMessage2 = testReceiver2.waitRemoteMessage();

				String localMessage3 = testReceiver3.waitLocalMessage();
				String remoteMessage3 = testReceiver3.waitRemoteMessage();

				String messageKey = (String)message.getPayload();

				assertEquals(messageKey, localMessage1);
				assertNull(remoteMessage1);
				assertNull(localMessage2);
				assertEquals(messageKey, remoteMessage2);
				assertNull(localMessage3);
				assertEquals(messageKey, remoteMessage3);

				clusterLinkImpl1.destroy();
				clusterLinkImpl2.destroy();
				clusterLinkImpl3.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage3() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, true);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				channelList.get(0).close();

				clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

				_assertLogger(
					captureHandler,
					"Unable to send multicast message " + message,
					ChannelClosedException.class);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage4() throws Exception {
		//Channel is disconnected

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, true);

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				channelList.get(0).disconnect();

				clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

				_assertLogger(
					captureHandler,
					"Unable to send multicast message " + message,
					ChannelNotConnectedException.class);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage1() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, false);

				Message message = _createMessage();

				clusterLinkImpl.sendUnicastMessage(
					new AddressImpl(new MockAddress()), message,
					Priority.LEVEL1);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage2() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl1 = _getClusterLinkImpl(1, true);
				ClusterLinkImpl clusterLinkImpl2 = _getClusterLinkImpl(1, true);

				List<JChannel> channelList1 = _getChannelList(clusterLinkImpl1);
				List<JChannel> channelList2 = _getChannelList(clusterLinkImpl2);

				Message message = _createMessage();

				TestReceiver testReceiver1 =
					(TestReceiver)channelList1.get(0).getReceiver();
				TestReceiver testReceiver2 =
					(TestReceiver)channelList2.get(0).getReceiver();

				clusterLinkImpl1.sendUnicastMessage(
					new AddressImpl(channelList2.get(0).getAddress()), message,
					Priority.LEVEL1);

				String localMessage1 = testReceiver1.waitLocalMessage();
				String remoteMessage1 = testReceiver1.waitRemoteMessage();
				String localMessage2 = testReceiver2.waitLocalMessage();
				String remoteMessage2 = testReceiver2.waitRemoteMessage();

				String messageKey = (String)message.getPayload();

				assertNull(localMessage1);
				assertNull(remoteMessage1);
				assertNull(localMessage2);
				assertEquals(messageKey, remoteMessage2);

				clusterLinkImpl1.destroy();
				clusterLinkImpl2.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage3() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, true);

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				channelList.get(0).close();

				clusterLinkImpl.sendUnicastMessage(
					new AddressImpl(new MockAddress()), message,
					Priority.LEVEL1);

				_assertLogger(
					captureHandler, "Unable to send unicast message:" + message,
					ChannelClosedException.class);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage4() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, true);

				List<JChannel> channelList = _getChannelList(clusterLinkImpl);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				channelList.get(0).disconnect();

				clusterLinkImpl.sendUnicastMessage(
					new AddressImpl(new MockAddress()), message,
					Priority.LEVEL1);

				_assertLogger(
					captureHandler, "Unable to send unicast message:" + message,
					ChannelNotConnectedException.class);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	private void _assertLogger(
		CaptureHandler captureHandler, String message,
		Class<?> exceptionClass) {

		List<LogRecord> logRecords = captureHandler.getLogRecords();

		if (message == null) {
			assertEquals(0, logRecords.size());

			return;
		}

		assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		assertEquals(message, logRecord.getMessage());

		if (exceptionClass == null) {
			assertNull(logRecord.getThrown());
		}
		else {
			assertEquals(exceptionClass, logRecord.getThrown().getClass());
		}

		captureHandler.flush();
	}

	private Message _createMessage() {
		Message message = new Message();

		message.setPayload(PwdGenerator.getPassword());

		return message;
	}

	private List<JChannel> _getChannelList(ClusterLinkImpl clusterLinkImpl)
		throws Exception {

		Field channelField = ReflectionUtil.getDeclaredField(
			ClusterLinkImpl.class, "_transportChannels");

		return (List<JChannel>)channelField.get(clusterLinkImpl);
	}

	private ClusterLinkImpl _getClusterLinkImpl(
			int channelCount, boolean enableClusterLink)
		throws Exception {

		_handlePropsValues(channelCount, enableClusterLink);

		_handleLog(ClusterBase.class, Level.FINE);

		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterForwardMessageListener(
			new ClusterForwardMessageListener());

		clusterLinkImpl.afterPropertiesSet();

		List<JChannel> channelList = _getChannelList(clusterLinkImpl);

		if (channelList != null) {
			for (JChannel channel : channelList) {
				channel.setReceiver(
					new TestReceiver(new AddressImpl(channel.getAddress())));
			}
		}

		return clusterLinkImpl;
	}

	private CaptureHandler _handleLog(Class<?> clazz, Level level)
		throws Exception {

		Logger logger = Logger.getLogger(clazz.getName());

		logger.setUseParentHandlers(false);
		logger.setLevel(level);

		for (Handler handler : logger.getHandlers()) {
			logger.removeHandler(handler);
		}

		CaptureHandler captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		return captureHandler;
	}

	private void _handlePropsValues(int channelCount, boolean enableClusterLink)
		throws Exception {

		if (channelCount == 0) {
			Field propsUtilInstance = ReflectionUtil.getDeclaredField(
				PropsUtil.class, "_instance");

			Object propsUtil = propsUtilInstance.get(null);

			Field configuration = ReflectionUtil.getDeclaredField(
				PropsUtil.class, "_configuration");

			configuration.set(
				propsUtil,
				new MockConfigurationImpl(
					PropsUtil.class.getClassLoader(), PropsFiles.PORTAL));
		}
		else {
			Properties transportProperties = PropsUtil.getProperties(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT, true);

			for (int i = transportProperties.size(); i < channelCount; i++) {
				PropsUtil.set(
					PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT +
						CharPool.POUND + i,
					"udp.xml");
			}
		}

		PropsUtil.set(
			PropsKeys.CLUSTER_LINK_ENABLED, String.valueOf(enableClusterLink));
	}

	private class CaptureHandler extends Handler {

		@Override
		public void close() throws SecurityException {
			_logRecords.clear();
		}

		@Override
		public void flush() {
			_logRecords.clear();
		}

		public List<LogRecord> getLogRecords() {
			return _logRecords;
		}

		@Override
		public boolean isLoggable(LogRecord logRecord) {
			return false;
		}

		@Override
		public void publish(LogRecord logRecord) {
			_logRecords.add(logRecord);
		}

		private List<LogRecord> _logRecords =
			new CopyOnWriteArrayList<LogRecord>();

	}

	private class MockAddress implements org.jgroups.Address {

		public boolean isMulticastAddress() {
			return false;
		}

		public int size() {
			return 0;
		}

		public void writeExternal(ObjectOutput out) throws IOException {
		}

		public void readExternal(ObjectInput in)
			throws IOException, ClassNotFoundException {
		}

		public void writeTo(DataOutputStream stream) throws IOException {
		}

		public void readFrom(DataInputStream stream)
			throws IOException, IllegalAccessException, InstantiationException {
		}

		public int compareTo(org.jgroups.Address o) {
			return 0;
		}

	}

	private class MockConfigurationImpl extends ConfigurationImpl {

		public MockConfigurationImpl(ClassLoader classLoader, String name) {
			super(classLoader, name);
		}

		public Properties getProperties(String prefix, boolean removePrefix) {
			if (prefix.equals(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT)) {

				return new Properties();
			}

			return super.getProperties(prefix, removePrefix);
		}
	}

	private class TestReceiver extends BaseReceiver {

		public TestReceiver(Address address) {
			_localAddress = address;
			_localMessageExchanger = new Exchanger<String>();
			_remoteMessageExchanger = new Exchanger<String>();
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

		@Override
		public void receive(org.jgroups.Message message) {
			org.jgroups.Address sourceAddress = message.getSrc();

			Message content = (Message)message.getObject();

			String messageKey = (String)content.getPayload();

			try {
				if (_localAddress.getRealAddress().equals(sourceAddress)) {
					_localMessageExchanger.exchange(messageKey);
				}
				else {
					_remoteMessageExchanger.exchange(messageKey);
				}
			}
			catch (InterruptedException ie) {
				fail();
			}
		}

		public void viewAccepted(View view) {
			super.view = view;
		}

		private final Address _localAddress;
		private final Exchanger<String> _localMessageExchanger;
		private final Exchanger<String> _remoteMessageExchanger;

	}

}