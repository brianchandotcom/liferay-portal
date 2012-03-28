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
import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.NewClassLoaderTestCase;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ObjectValuePair;
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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
 */
public class ClusterLinkImplTest extends  NewClassLoaderTestCase {

	public void testDestroy0() throws Exception {
		//Disable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, false);

				try {
					clusterPlatform.getKey().destroy();
				}
				catch (Exception e) {
					fail();
				}

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testDestroy1() throws Exception {
		//Enable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(2, true);

				try {
					clusterPlatform.getKey().destroy();
				}
				catch (Exception e) {
					fail();
				}

				assertEquals(false, clusterPlatform.getValue()[0].isOpen());
				assertEquals(false, clusterPlatform.getValue()[1].isOpen());

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetChannel() throws Exception {
		//Enable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(2, true);

				_handleLog(ClusterLinkImpl.class, Level.FINE);

				JChannel[] actualChannel1 = new JChannel[5];
				JChannel[] actualChannel2 = new JChannel[5];

				try {
					actualChannel1[0] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL1);
					actualChannel1[1] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL2);
					actualChannel1[2] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL3);
					actualChannel1[3] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL4);
					actualChannel1[4] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL5);

					actualChannel2[0] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL6);
					actualChannel2[1] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL7);
					actualChannel2[2] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL8);
					actualChannel2[3] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL9);
					actualChannel2[4] =
						clusterPlatform.getKey().getChannel(Priority.LEVEL10);
				}
				catch (Exception e) {
					fail();
				}

				JChannel expectedChannel1 = clusterPlatform.getValue()[0];
				JChannel expectedChannel2 = clusterPlatform.getValue()[1];

				for (int i = 0; i < 5; i++) {
					assertEquals(expectedChannel1, actualChannel1[i]);
					assertEquals(expectedChannel2, actualChannel2[i]);
				}

				clusterPlatform.getKey().destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetLocalTransportAddresses0() throws Exception {
		//Disable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, false);

				List<Address> addresses = null;

				try {
					addresses =
						clusterPlatform.getKey().getLocalTransportAddresses();
				}
				catch (Exception e) {
					fail();
				}

				assertEquals(true, addresses.isEmpty());

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetLocalTransportAddresses1() throws Exception {
		//Enable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(2, true);

				ClusterLinkImpl clusterLinkImpl = clusterPlatform.getKey();

				List<Address> addresses = null;

				try {
					addresses = clusterLinkImpl.getLocalTransportAddresses();
				}
				catch (Exception e) {
					fail();
				}

				org.jgroups.Address expectedAddress1 = 
					clusterPlatform.getValue()[0].getAddress();
				org.jgroups.Address expectedAddress2 = 
					clusterPlatform.getValue()[1].getAddress();

				assertEquals(2, addresses.size());
				assertEquals(
					expectedAddress1, addresses.get(0).getRealAddress());
				assertEquals(
					expectedAddress2, addresses.get(1).getRealAddress());

				clusterLinkImpl.destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetTransportAddressesByPriority0() throws Exception {
		//Disable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, false);

				List<Address> addresses = null;

				try {
					addresses =
						clusterPlatform.getKey().getTransportAddresses(
							Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				assertEquals(true, addresses.isEmpty());

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testGetTransportAddressesByPriority1() throws Exception {
		//Enable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform1 =
					_getClusterLinkPlatform(2, true);
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform2 =
					_getClusterLinkPlatform(2, true);

				List<Address> addresses1 = null;
				List<Address> addresses2 = null;

				try {
					addresses1 =
						clusterPlatform1.getKey().getTransportAddresses(
							Priority.LEVEL1);
					addresses2 =
						clusterPlatform1.getKey().getTransportAddresses(
							Priority.LEVEL6);
				}
				catch (Exception e) {
					e.printStackTrace();

					fail();
				}

				org.jgroups.Address exceptedAddress1 =
					clusterPlatform1.getValue()[0].getAddress();
				org.jgroups.Address exceptedAddress2 =
					clusterPlatform1.getValue()[1].getAddress();
				org.jgroups.Address exceptedAddress3 =
					clusterPlatform2.getValue()[0].getAddress();
				org.jgroups.Address exceptedAddress4 =
					clusterPlatform2.getValue()[1].getAddress();

				assertEquals(2, addresses1.size());
				assertEquals(
					exceptedAddress1, addresses1.get(0).getRealAddress());
				assertEquals(
					exceptedAddress3, addresses1.get(1).getRealAddress());

				assertEquals(2, addresses2.size());
				assertEquals(
					exceptedAddress2, addresses2.get(0).getRealAddress());
				assertEquals(
					exceptedAddress4, addresses2.get(1).getRealAddress());

				clusterPlatform1.getKey().destroy();
				clusterPlatform2.getKey().destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testInitChannel0() throws Exception {
		//channel number > _MAX_CHANNEL_COUNT

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				try {
					_getClusterLinkPlatform(11, true);
				}
				catch (IllegalStateException e) {
					return null;
				}
				catch (Exception e) {
					fail();
				}

				fail();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testInitChannel1() throws Exception {
		//channel number = 0

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				try {
					_getClusterLinkPlatform(0, true);
				}
				catch (IllegalStateException e) {
					return null;
				}
				catch (Exception e) {
					fail();
				}

				fail();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage0() throws Exception {
		//Disable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, false);

				Message message = _createMessage();

				try {
					clusterPlatform.getKey().sendMulticastMessage(
						message, Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage1() throws Exception {
		//Enable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform1 =
					_getClusterLinkPlatform(1, true);
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform2 =
					_getClusterLinkPlatform(1, true);
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform3 =
					_getClusterLinkPlatform(1, true);

				TestReceiver testReceiver1 =
					(TestReceiver)clusterPlatform1.getValue()[0].getReceiver();
				TestReceiver testReceiver2 =
					(TestReceiver)clusterPlatform2.getValue()[0].getReceiver();
				TestReceiver testReceiver3 =
					(TestReceiver)clusterPlatform3.getValue()[0].getReceiver();

				testReceiver1.setLocalMessageCount(1);
				testReceiver2.setRemoteMessageCount(1);
				testReceiver3.setRemoteMessageCount(1);

				Message message = _createMessage();

				try {
					clusterPlatform1.getKey().sendMulticastMessage(
						message, Priority.LEVEL1);

					testReceiver1.waitLocalMessage();
					testReceiver2.waitRemoteMessage();
					testReceiver3.waitRemoteMessage();
				}
				catch (Exception e) {
					fail();
				}

				Set<String> localMessages1 =
					testReceiver1.getReceivedLocalMessages();
				Set<String> remoteMessages1 =
					testReceiver1.getReceivedRemoteMessages();
				Set<String> localMessages2 =
					testReceiver2.getReceivedLocalMessages();
				Set<String> remoteMessages2 =
					testReceiver2.getReceivedRemoteMessages();
				Set<String> localMessages3 =
					testReceiver3.getReceivedLocalMessages();
				Set<String> remoteMessages3 =
					testReceiver3.getReceivedRemoteMessages();

				assertEquals(1, localMessages1.size());
				assertEquals(0, remoteMessages1.size());
				assertEquals(0, localMessages2.size());
				assertEquals(1, remoteMessages2.size());
				assertEquals(0, localMessages3.size());
				assertEquals(1, remoteMessages3.size());

				String messageKey = (String)message.getPayload();

				assertTrue(localMessages1.contains(messageKey));
				assertTrue(remoteMessages2.contains(messageKey));
				assertTrue(remoteMessages3.contains(messageKey));

				clusterPlatform1.getKey().destroy();
				clusterPlatform2.getKey().destroy();
				clusterPlatform3.getKey().destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage2() throws Exception {
		//Channel is closed

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, true);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				clusterPlatform.getValue()[0].close();

				try {
					clusterPlatform.getKey().sendMulticastMessage(
						message, Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				_assertLogger(
					captureHandler,
					"Unable to send multicast message " + message,
					ChannelClosedException.class);

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendMulticastMessage3() throws Exception {
		//Channel is disconnected

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, true);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				clusterPlatform.getValue()[0].disconnect();

				try {
					clusterPlatform.getKey().sendMulticastMessage(
						message, Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				_assertLogger(
					captureHandler, 
					"Unable to send multicast message " + message,
					ChannelNotConnectedException.class);

				clusterPlatform.getKey().destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage0() throws Exception {
		//Disable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, false);

				Message message = _createMessage();

				try {
					clusterPlatform.getKey().sendUnicastMessage(
						new AddressImpl(new MockAddress()), 
						message, Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage1() throws Exception {
		//Enable cluster link

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform1 =
					_getClusterLinkPlatform(1, true);
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform2 =
					_getClusterLinkPlatform(1, true);

				Message message = _createMessage();

				TestReceiver testReceiver1 =
					(TestReceiver)clusterPlatform1.getValue()[0].getReceiver();
				TestReceiver testReceiver2 =
					(TestReceiver)clusterPlatform2.getValue()[0].getReceiver();

				testReceiver2.setRemoteMessageCount(1);

				try {
					clusterPlatform1.getKey().sendUnicastMessage(
						new AddressImpl(
							clusterPlatform2.getValue()[0].getAddress()),
						message, Priority.LEVEL1);

					testReceiver2.waitRemoteMessage();
				}
				catch (Exception e) {
					fail();
				}

				Set<String> localMessages1 =
					testReceiver1.getReceivedLocalMessages();
				Set<String> remoteMessages1 =
					testReceiver1.getReceivedRemoteMessages();
				Set<String> localMessages2 =
					testReceiver2.getReceivedLocalMessages();
				Set<String> remoteMessages2 =
					testReceiver2.getReceivedRemoteMessages();

				assertEquals(0, localMessages1.size());
				assertEquals(0, remoteMessages1.size());
				assertEquals(0, localMessages2.size());
				assertEquals(1, remoteMessages2.size());

				assertTrue(
					remoteMessages2.contains((String)message.getPayload()));

				clusterPlatform1.getKey().destroy();
				clusterPlatform2.getKey().destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage2() throws Exception {
		//Channel is closed

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, true);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				clusterPlatform.getValue()[0].close();

				try {
					clusterPlatform.getKey().sendUnicastMessage(
						new AddressImpl(new MockAddress()), message,
						Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				_assertLogger(
					captureHandler,
					"Unable to send unicast message:" + message,
					ChannelClosedException.class);

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	public void testSendUnicastMessage3() throws Exception {
		//Channel is disconnected

		Callable<Void> callable = new Callable<Void>() {

			public Void call() throws Exception {
				ObjectValuePair<ClusterLinkImpl, JChannel[]> clusterPlatform =
					_getClusterLinkPlatform(1, true);

				Message message = _createMessage();

				CaptureHandler captureHandler = _handleLog(
					ClusterLinkImpl.class, Level.WARNING);

				clusterPlatform.getValue()[0].disconnect();

				try {
					clusterPlatform.getKey().sendUnicastMessage(
						new AddressImpl(new MockAddress()), message,
						Priority.LEVEL1);
				}
				catch (Exception e) {
					fail();
				}

				_assertLogger(
					captureHandler,
					"Unable to send unicast message:" + message,
					ChannelNotConnectedException.class);

				clusterPlatform.getKey().destroy();

				return null;
			}

		};

		runInNewClassLoader(
			(Class<? extends Callable<Void>>)callable.getClass());
	}

	private void _assertLogger(
		CaptureHandler captureHandler, String message, Class exceptionClass) {

		List<LogRecord> logRecords = captureHandler.getLogRecords();

		if (message == null) {
			assertEquals(0, logRecords.size());

			return;
		}

		assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		assertEquals(message, logRecord.getMessage());

		if (exceptionClass == null) {
			assertEquals(null, logRecord.getThrown());
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

	private ObjectValuePair<ClusterLinkImpl, JChannel[]>
			_getClusterLinkPlatform(int channelCount, boolean enableClusterLink)
		throws Exception {

		_handlePropsValues(channelCount, enableClusterLink);

		_handleLog(ClusterBase.class, Level.FINE);

		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterForwardMessageListener(
			new ClusterForwardMessageListener());

		clusterLinkImpl.afterPropertiesSet();

		Field channelField = ReflectionUtil.getDeclaredField(
			ClusterLinkImpl.class, "_transportChannels");

		Object channelFieldValue = channelField.get(clusterLinkImpl);

		JChannel[] channelArray = null;

		if (channelFieldValue != null) {
			List<JChannel> channelList = (List<JChannel>)channelFieldValue;

			if (!channelList.isEmpty()) {
				channelArray = new JChannel[channelList.size()];
				for (int i = 0; i < channelList.size(); i++) {
					JChannel channel = channelList.get(i);

					channel.setReceiver(
						new TestReceiver(
							new AddressImpl(channel.getAddress())));

					channelArray[i] = channel;
				}
			}
		}

		return new ObjectValuePair<ClusterLinkImpl, JChannel[]>(
			clusterLinkImpl, channelArray);
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

		Log newLog = new LogWrapper(new Jdk14LogImpl(logger));

		Field logField = ReflectionUtil.getDeclaredField(clazz, "_log");

		logField.set(null, newLog);

		return captureHandler;
	}

	private void _handlePropsValues(
			int channelCount, boolean enableClusterLink)
		throws Exception {

		Properties transportProperties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT, true);

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

	private class TestReceiver extends BaseReceiver {

		public TestReceiver(Address address) {
			_localAddress = address;
		}

		public void setLocalMessageCount(int messagecount) {
			_localMessageCountDownLatch = new CountDownLatch(messagecount);
		}

		public void setRemoteMessageCount(int messagecount) {
			_remoteMessageCountDownLatch = new CountDownLatch(messagecount);
		}

		public void waitLocalMessage() throws Exception {
			_localMessageCountDownLatch.await(1000, TimeUnit.MILLISECONDS);
		}

		public void waitRemoteMessage() throws Exception {
			_remoteMessageCountDownLatch.await(1000, TimeUnit.MILLISECONDS);
		}

		public Set<String> getReceivedLocalMessages() {
			return _receivedLocalMessages.keySet();
		}

		public Set<String> getReceivedRemoteMessages() {
			return _receivedRemoteMessages.keySet();
		}

		@Override
		public void receive(org.jgroups.Message message) {
			org.jgroups.Address sourceAddress = message.getSrc();

			Message content = (Message)message.getObject();

			String messageKey = (String)content.getPayload();

			if (_localAddress.getRealAddress().equals(sourceAddress)) {
				if (_localMessageCountDownLatch != null) {
					_localMessageCountDownLatch.countDown();
				}

				_receivedLocalMessages.put(messageKey, new Date());
			}
			else {
				if (_remoteMessageCountDownLatch != null) {
					_remoteMessageCountDownLatch.countDown();
				}

				_receivedRemoteMessages.put(messageKey, new Date());
			}
		}

		public void viewAccepted(View view) {
			super.view = view;
		}

		private Address _localAddress;
		private volatile CountDownLatch _localMessageCountDownLatch;
		private Map<String, Date> _receivedLocalMessages =
			new ConcurrentHashMap<String, Date>();
		private Map<String, Date> _receivedRemoteMessages =
			new ConcurrentHashMap<String, Date>();
		private volatile CountDownLatch _remoteMessageCountDownLatch;

	}

}