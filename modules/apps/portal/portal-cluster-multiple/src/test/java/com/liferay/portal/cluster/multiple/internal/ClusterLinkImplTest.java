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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

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
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeactivate() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(1);

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(
			clusterChannels.toString(), 1, clusterChannels.size());

		TestClusterChannel clusterChannel = clusterChannels.get(0);

		ExecutorService executorService = clusterLinkImpl.getExecutorService();

		Assert.assertFalse(clusterChannel.isClosed());
		Assert.assertFalse(executorService.isShutdown());

		clusterLinkImpl.deactivate();

		Assert.assertTrue(clusterChannel.isClosed());
		Assert.assertTrue(executorService.isShutdown());
	}

	@Test
	public void testGetChannel() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(2);

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

		Assert.assertEquals(
			clusterChannels.toString(), 2, clusterChannels.size());

		Assert.assertNotEquals(clusterChannel1, clusterChannel2);
		Assert.assertTrue(
			clusterChannels.toString(),
			clusterChannels.contains(clusterChannel1));
		Assert.assertTrue(
			clusterChannels.toString(),
			clusterChannels.contains(clusterChannel2));
	}

	@Test
	public void testInitChannels() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ClusterLinkImpl.class.getName(), Level.OFF)) {

			// Test 1, create ClusterLinkImpl#MAX_CHANNEL_COUNT channels

			List<LogEntry> logEntries = logCapture.getLogEntries();

			try {
				getClusterLinkImpl(ClusterLinkImpl.MAX_CHANNEL_COUNT + 1);

				Assert.fail();
			}
			catch (IllegalStateException illegalStateException) {
				Assert.assertEquals(
					logEntries.toString(), 0, logEntries.size());
				Assert.assertEquals(
					"java.lang.IllegalArgumentException: Channel count must " +
						"be between 1 and " + ClusterLinkImpl.MAX_CHANNEL_COUNT,
					illegalStateException.getMessage());
			}

			// Test 2, create 0 channels

			logEntries = logCapture.resetPriority(String.valueOf(Level.SEVERE));

			try {
				getClusterLinkImpl(0);

				Assert.fail();
			}
			catch (IllegalStateException illegalStateException) {
				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertEquals(
					"Unable to initialize channels", logEntry.getMessage());

				Assert.assertEquals(
					"java.lang.IllegalArgumentException: Channel count must " +
						"be between 1 and " + ClusterLinkImpl.MAX_CHANNEL_COUNT,
					illegalStateException.getMessage());
			}
		}
	}

	@Test
	public void testInitialize() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(2);

		Assert.assertNotNull(clusterLinkImpl.getExecutorService());

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(
			clusterChannels.toString(), 2, clusterChannels.size());

		for (TestClusterChannel clusterChannel : clusterChannels) {
			Assert.assertFalse(clusterChannel.isClosed());

			CountDownLatch countDownLatch = ReflectionTestUtil.getFieldValue(
				clusterChannel.getClusterReceiver(), "_countDownLatch");

			Assert.assertEquals(0, countDownLatch.getCount());
		}
	}

	@Test
	public void testSendMulticastMessage() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(1);

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Assert.assertTrue(
			multicastMessages.toString(), multicastMessages.isEmpty());
		Assert.assertTrue(
			unicastMessages.toString(), unicastMessages.isEmpty());

		Message message = new Message();

		clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

		Assert.assertEquals(
			multicastMessages.toString(), 1, multicastMessages.size());
		Assert.assertTrue(
			multicastMessages.toString(), multicastMessages.contains(message));
		Assert.assertTrue(
			unicastMessages.toString(), unicastMessages.isEmpty());
	}

	@Test
	public void testSendUnicastMessage() {
		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl(1);

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Assert.assertTrue(
			multicastMessages.toString(), multicastMessages.isEmpty());
		Assert.assertTrue(
			unicastMessages.toString(), unicastMessages.isEmpty());

		Message message = new Message();
		Address address = new TestAddress(-1);

		clusterLinkImpl.sendUnicastMessage(address, message, Priority.LEVEL1);

		Assert.assertTrue(
			multicastMessages.toString(), multicastMessages.isEmpty());
		Assert.assertEquals(
			unicastMessages.toString(), 1, unicastMessages.size());

		ObjectValuePair<Serializable, Address> unicastMessage =
			unicastMessages.get(0);

		Assert.assertSame(message, unicastMessage.getKey());
		Assert.assertSame(address, unicastMessage.getValue());
	}

	protected ClusterLinkImpl getClusterLinkImpl(int channels) {
		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		Properties channelNameProperties = new Properties();
		Properties channelPropertiesProperties = new Properties();

		for (int i = 0; i < channels; i++) {
			channelNameProperties.put(
				StringPool.PERIOD + i, "test-channel-name-transport-" + i);
			channelPropertiesProperties.put(
				StringPool.PERIOD + i,
				"test-channel-properties-transport-" + i);
		}

		clusterLinkImpl.setProps(
			PropsTestUtil.setProps(
				HashMapBuilder.<String, Object>put(
					PropsKeys.CLUSTER_LINK_CHANNEL_LOGIC_NAME_TRANSPORT,
					new Properties()
				).put(
					PropsKeys.CLUSTER_LINK_CHANNEL_NAME_TRANSPORT,
					channelNameProperties
				).put(
					PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_TRANSPORT,
					channelPropertiesProperties
				).build()));

		clusterLinkImpl.setClusterChannelFactory(
			new TestClusterChannelFactory());
		clusterLinkImpl.setPortalExecutorManager(
			new MockPortalExecutorManager());

		clusterLinkImpl.activate();

		return clusterLinkImpl;
	}

}