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

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
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

import org.jgroups.JChannel;
import org.jgroups.View;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class ClusterLinkImplTest extends NewClassLoaderTestCase {

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

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				assertEquals(2, jChannels.size());

				JChannel jChannel = jChannels.get(0);

				assertTrue(jChannel.isOpen());

				jChannel = jChannels.get(1);

				assertTrue(jChannel.isOpen());

				clusterLinkImpl.destroy();

				jChannels = _getJChannels(clusterLinkImpl);

				assertEquals(2, jChannels.size());

				jChannel = jChannels.get(0);

				assertFalse(jChannel.isOpen());

				jChannel = jChannels.get(1);

				assertFalse(jChannel.isOpen());

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

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				assertEquals(2, jChannels.size());

				JChannel jChannel = jChannels.get(0);

				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL1));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL2));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL3));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL4));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL5));

				jChannel = jChannels.get(1);

				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL6));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL7));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL8));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL9));
				assertSame(
					jChannel, clusterLinkImpl.getChannel(Priority.LEVEL10));

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

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				assertSame(
					_getJGroupsAddress(jChannels, 0),
					_getRealAddress(addresses, 0));
				assertSame(
					_getJGroupsAddress(jChannels, 1),
					_getRealAddress(addresses, 1));

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

				List<JChannel> jChannels1 = _getJChannels(clusterLinkImpl1);

				assertEquals(2, jChannels1.size());

				List<JChannel> jChannels2 = _getJChannels(clusterLinkImpl2);

				assertEquals(2, jChannels2.size());

				List<Address> addresses1 =
					clusterLinkImpl1.getTransportAddresses(Priority.LEVEL1);

				assertEquals(2, addresses1.size());

				List<Address> addresses2 =
					clusterLinkImpl1.getTransportAddresses(Priority.LEVEL6);

				assertEquals(2, addresses2.size());

				assertEquals(
					_getJGroupsAddress(jChannels1, 0),
					_getRealAddress(addresses1, 0));
				assertEquals(
					_getJGroupsAddress(jChannels1, 1),
					_getRealAddress(addresses2, 0));
				assertEquals(
					_getJGroupsAddress(jChannels2, 0),
					_getRealAddress(addresses1, 1));
				assertEquals(
					_getJGroupsAddress(jChannels2, 1),
					_getRealAddress(addresses2, 1));

				clusterLinkImpl1.destroy();
				clusterLinkImpl2.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testInitChannel1() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				try {
					_getClusterLinkImpl(
						ClusterLinkImpl.MAX_CHANNEL_COUNT + 1, true);

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

				List<JChannel> jChannels1 = _getJChannels(clusterLinkImpl1);
				List<JChannel> jChannels2 = _getJChannels(clusterLinkImpl2);
				List<JChannel> jChannels3 = _getJChannels(clusterLinkImpl3);

				TestReceiver testReceiver1 = _getTestReceiver(jChannels1, 0);
				TestReceiver testReceiver2 = _getTestReceiver(jChannels2, 0);
				TestReceiver testReceiver3 = _getTestReceiver(jChannels3, 0);

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

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				JChannel jChannel = jChannels.get(0);

				jChannel.close();

				clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

				_assertLogger(
					captureHandler,
					"Unable to send multicast message " + message,
					Exception.class);

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage4() throws Exception {
		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ClusterLinkImpl clusterLinkImpl = _getClusterLinkImpl(1, true);

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				JChannel jChannel = jChannels.get(0);

				jChannel.disconnect();

				clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

				_assertLogger(
					captureHandler,
					"Unable to send multicast message " + message,
					Exception.class);

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

				List<JChannel> jChannels1 = _getJChannels(clusterLinkImpl1);
				List<JChannel> jChannels2 = _getJChannels(clusterLinkImpl2);

				TestReceiver testReceiver1 = _getTestReceiver(jChannels1, 0);
				TestReceiver testReceiver2 = _getTestReceiver(jChannels2, 0);

				Message message = _createMessage();

				clusterLinkImpl1.sendUnicastMessage(
					new AddressImpl(jChannels2.get(0).getAddress()), message,
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

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				JChannel jChannel = jChannels.get(0);

				jChannel.close();

				clusterLinkImpl.sendUnicastMessage(
					new AddressImpl(new MockAddress()), message,
					Priority.LEVEL1);

				_assertLogger(
					captureHandler, "Unable to send unicast message " + message,
					Exception.class);

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

				List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				JChannel jChannel = jChannels.get(0);

				jChannel.disconnect();

				clusterLinkImpl.sendUnicastMessage(
					new AddressImpl(new MockAddress()), message,
					Priority.LEVEL1);

				_assertLogger(
					captureHandler, "Unable to send unicast message " + message,
					Exception.class);

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
			assertNotNull(logRecord.getThrown());
		}

		captureHandler.flush();
	}

	private Message _createMessage() {
		Message message = new Message();

		message.setPayload(PwdGenerator.getPassword());

		return message;
	}

	private ClusterLinkImpl _getClusterLinkImpl(
			int channelsCount, boolean enableClusterLink)
		throws Exception {

		_handlePropsValues(channelsCount, enableClusterLink);

		_handleLog(ClusterBase.class, Level.FINE);

		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterForwardMessageListener(
			new ClusterForwardMessageListener());

		clusterLinkImpl.afterPropertiesSet();

		List<JChannel> jChannels = _getJChannels(clusterLinkImpl);

		if (jChannels != null) {
			for (JChannel channel : jChannels) {
				channel.setReceiver(
					new TestReceiver(new AddressImpl(channel.getAddress())));
			}
		}

		return clusterLinkImpl;
	}

	private List<JChannel> _getJChannels(ClusterLinkImpl clusterLinkImpl)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			ClusterLinkImpl.class, "_transportChannels");

		return (List<JChannel>)field.get(clusterLinkImpl);
	}

	private org.jgroups.Address _getJGroupsAddress(
		List<JChannel> jChannels, int index) {

		JChannel jChannel = jChannels.get(index);

		return jChannel.getAddress();
	}

	private Object _getRealAddress(List<Address> addresses, int index) {
		Address address = addresses.get(index);

		return address.getRealAddress();
	}

	private TestReceiver _getTestReceiver(List<JChannel> jChannels, int index) {
		JChannel jChannel = jChannels.get(index);

		return (TestReceiver)jChannel.getReceiver();
	}

	private CaptureHandler _handleLog(Class<?> clazz, Level level)
		throws Exception {

		Logger logger = Logger.getLogger(clazz.getName());

		for (Handler handler : logger.getHandlers()) {
			logger.removeHandler(handler);
		}

		logger.setLevel(level);
		logger.setUseParentHandlers(false);

		CaptureHandler captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		return captureHandler;
	}

	private void _handlePropsValues(
			int channelsCount, boolean enableClusterLink)
		throws Exception {

		if (channelsCount == 0) {
			Field instanceField = ReflectionUtil.getDeclaredField(
				PropsUtil.class, "_instance");

			Object instance = instanceField.get(null);

			Field configurationField = ReflectionUtil.getDeclaredField(
				PropsUtil.class, "_configuration");

			configurationField.set(
				instance,
				new MockConfigurationImpl(
					PropsUtil.class.getClassLoader(), PropsFiles.PORTAL));
		}
		else {
			Properties properties = PropsUtil.getProperties(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT, true);

			for (int i = properties.size(); i < channelsCount; i++) {
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

		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		public boolean isMulticastAddress() {
			return false;
		}

		public void readExternal(ObjectInput objectInput) {
		}

		public void readFrom(DataInput dataInput) throws Exception {
		}

		public void readFrom(DataInputStream dataInputStream) {
		}

		public int size() {
			return 0;
		}

		public void writeExternal(ObjectOutput objectOutput) {
		}

		public void writeTo(DataOutput dataOutput) throws Exception {
		}

		public void writeTo(DataOutputStream dataOutputStream) {
		}

	}

	private class MockConfigurationImpl extends ConfigurationImpl {

		public MockConfigurationImpl(ClassLoader classLoader, String name) {
			super(classLoader, name);
		}

		@Override
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
				fail();
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