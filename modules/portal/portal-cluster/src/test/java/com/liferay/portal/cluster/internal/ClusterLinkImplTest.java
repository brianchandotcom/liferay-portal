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

package com.liferay.portal.cluster.internal;

import com.liferay.portal.cluster.ClusterChannel;
import com.liferay.portal.cluster.ClusterReceiver;
import com.liferay.portal.cluster.internal.constants.ClusterPropsKeys;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class ClusterLinkImplTest extends BaseClusterTestCase {

	@Test
	public void testDeactivate() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(true, 1);

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(1, clusterChannels.size());

		TestClusterChannel clusterChannel = clusterChannels.get(0);

		ExecutorService executorService = clusterLinkImpl.getExecutorService();

		Assert.assertFalse(clusterChannel.isClosed());
		Assert.assertFalse(executorService.isShutdown());

		clusterLinkImpl.deactivate();

		Assert.assertTrue(clusterChannel.isClosed());
		Assert.assertTrue(executorService.isShutdown());
	}

	@Test
	public void testDisabledClusterLink() {

		// Test 1, initialize

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(false, 1);

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertTrue(clusterChannels.isEmpty());
		Assert.assertNull(clusterLinkImpl.getExecutorService());

		// Test 2, send unicast message

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Message message = new Message();
		Address address = new TestAddress(-1);

		clusterLinkImpl.sendUnicastMessage(address, message, Priority.LEVEL1);

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		// Test 3, send multicast message

		clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		// Test 4, destroy

		clusterLinkImpl.deactivate();
	}

	@Test
	public void testGetChannel() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(true, 2);

		ClusterChannel clusterChannel1 = clusterLinkImpl.getChannel(
			Priority.LEVEL1);

		Assert.assertSame(
			clusterChannel1, clusterLinkImpl.getChannel(Priority.LEVEL2));
		Assert.assertSame(
			clusterChannel1, clusterLinkImpl.getChannel(Priority.LEVEL3));
		Assert.assertSame(
			clusterChannel1, clusterLinkImpl.getChannel(Priority.LEVEL4));
		Assert.assertSame(
			clusterChannel1, clusterLinkImpl.getChannel(Priority.LEVEL5));

		ClusterChannel clusterChannel2 = clusterLinkImpl.getChannel(
			Priority.LEVEL6);

		Assert.assertSame(
			clusterChannel2, clusterLinkImpl.getChannel(Priority.LEVEL7));
		Assert.assertSame(
			clusterChannel2, clusterLinkImpl.getChannel(Priority.LEVEL8));
		Assert.assertSame(
			clusterChannel2, clusterLinkImpl.getChannel(Priority.LEVEL9));
		Assert.assertSame(
			clusterChannel2, clusterLinkImpl.getChannel(Priority.LEVEL10));

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(2, clusterChannels.size());

		Assert.assertNotEquals(clusterChannel1, clusterChannel2);
		Assert.assertTrue(clusterChannels.contains(clusterChannel1));
		Assert.assertTrue(clusterChannels.contains(clusterChannel2));
	}

	@Test
	public void testInitChannels() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterLinkImpl.class.getName(), Level.OFF)) {

			// Test 1, create ClusterLinkImpl#MAX_CHANNEL_COUNT channels

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			try {
				getClusterLinkImpl(true, ClusterLinkImpl.MAX_CHANNEL_COUNT + 1);

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

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			try {
				getClusterLinkImpl(true, 0);

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
	}

	@Test
	public void testInitialize() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(true, 2);

		Assert.assertNotNull(clusterLinkImpl.getExecutorService());

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(2, clusterChannels.size());

		for (TestClusterChannel clusterChannel : clusterChannels) {
			Assert.assertFalse(clusterChannel.isClosed());

			ClusterReceiver clusterReceiver =
				clusterChannel.getClusterReceiver();

			CountDownLatch countDownLatch = ReflectionTestUtil.getFieldValue(
				clusterReceiver, "_countDownLatch");

			Assert.assertEquals(0, countDownLatch.getCount());
		}
	}

	@Test
	public void testSendMulticastMessage() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(true, 1);

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		Message message = new Message();

		clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

		Assert.assertEquals(1, multicastMessages.size());
		Assert.assertTrue(multicastMessages.contains(message));
		Assert.assertTrue(unicastMessages.isEmpty());
	}

	@Test
	public void testSendUnicastMessage() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(true, 1);

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		Message message = new Message();
		Address address = new TestAddress(-1);

		clusterLinkImpl.sendUnicastMessage(address, message, Priority.LEVEL1);

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertEquals(1, unicastMessages.size());

		ObjectValuePair<Serializable, Address> unicastMessage =
			unicastMessages.get(0);

		Assert.assertSame(message, unicastMessage.getKey());
		Assert.assertSame(address, unicastMessage.getValue());
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		AspectJNewEnvTestRule.INSTANCE;

	protected ClusterLinkImpl getClusterLinkImpl(
		final boolean enabled, int channels) {

		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setProps(
			new Props() {

				@Override
				public boolean contains(String key) {
					return true;
				}

				@Override
				public String get(String key) {
					if (PropsKeys.CLUSTER_LINK_ENABLED.equals(key)) {
						return String.valueOf(enabled);
					}

					return StringPool.BLANK;
				}

				@Override
				public String get(String key, Filter filter) {
					return null;
				}

				@Override
				public String[] getArray(String key) {
					return null;
				}

				@Override
				public String[] getArray(String key, Filter filter) {
					return null;
				}

				@Override
				public Properties getProperties() {
					return new Properties();
				}

				@Override
				public Properties getProperties(
					String prefix, boolean removePrefix) {

					return new Properties();
				}

			});

		clusterLinkImpl.setClusterChannelFactory(
			new TestClusterChannelFactory());

		clusterLinkImpl.setPortalExecutorManager(
			new MockPortalExecutorManager());

		Map<String, Object> properties = new HashMap<>();

		for (int i = 0; i < channels; i++) {
			properties.put(
				ClusterPropsKeys.CHANNEL_NAME_TRANSPORT_PREFIX +
					StringPool.PERIOD + i,
				"test-channel-name-transport-" + i);
			properties.put(
				ClusterPropsKeys.CHANNEL_PROPERTIES_TRANSPORT_PREFIX +
					StringPool.PERIOD + i,
				"test-channel-properties-transport-" + i);
		}

		clusterLinkImpl.activate(properties);

		return clusterLinkImpl;
	}

}