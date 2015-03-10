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
import com.liferay.portal.kernel.cluster.ClusterChannel;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class ClusterLinkImplTest extends BaseClusterTestCase {

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testDestroy() {
		TransportationConfigurationAdvice.setChannelCount(1);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(1, clusterChannels.size());

		TestClusterChannel clusterChannel = clusterChannels.get(0);

		ExecutorService executorService = clusterLinkImpl.getExecutorService();

		Assert.assertFalse(clusterChannel.isClosed());
		Assert.assertFalse(executorService.isShutdown());

		clusterLinkImpl.destroy();

		Assert.assertTrue(clusterChannel.isClosed());
		Assert.assertTrue(executorService.isShutdown());
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testDisabledClusterLink() {

		// Test 1, initialize

		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterChannelFactory(
			new TestClusterChannelFactory());

		clusterLinkImpl.initialize();

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertTrue(clusterChannels.isEmpty());
		Assert.assertNull(clusterLinkImpl.getExecutorService());

		// Test 2, send unitcast message

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Message message = new Message();
		Address address = new TestAddress("test.address");

		clusterLinkImpl.sendUnicastMessage(address, message, Priority.LEVEL1);

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		// Test 3, send multicast message

		clusterLinkImpl.sendMulticastMessage(message, Priority.LEVEL1);

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		// Test 4, destroy

		clusterLinkImpl.destroy();
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testGetChannel() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

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
		Assert.assertTrue(
			clusterChannels.contains((TestClusterChannel)clusterChannel1));
		Assert.assertTrue(
			clusterChannels.contains((TestClusterChannel)clusterChannel2));
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testInitChannels() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterLinkImpl.class.getName(), Level.OFF)) {

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
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testInitialize() {
		TransportationConfigurationAdvice.setChannelCount(2);

		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterChannelFactory(
			new TestClusterChannelFactory());

		clusterLinkImpl.initialize();

		Assert.assertNotNull(clusterLinkImpl.getExecutorService());

		List<TestClusterChannel> clusterChannels =
			TestClusterChannel.getClusterChannels();

		Assert.assertEquals(2, clusterChannels.size());

		for (TestClusterChannel clusterChannel : clusterChannels) {
			Assert.assertFalse(clusterChannel.isClosed());

			ClusterReceiver clusterReceiver = clusterChannel.getReceiver();

			CountDownLatch countDownLatch = ReflectionTestUtil.getFieldValue(
				clusterReceiver, "_countDownLatch");

			Assert.assertEquals(0, countDownLatch.getCount());
		}
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testSendMulticastMessage() {
		TransportationConfigurationAdvice.setChannelCount(1);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

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

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			TransportationConfigurationAdvice.class
		}
	)
	@Test
	public void testSendUnicastMessage() {
		TransportationConfigurationAdvice.setChannelCount(1);

		ClusterLinkImpl clusterLinkImpl = getClusterLinkImpl();

		List<Serializable> multicastMessages =
			TestClusterChannel.getMulticastMessages();
		List<ObjectValuePair<Serializable, Address>> unicastMessages =
			TestClusterChannel.getUnicastMessages();

		Assert.assertTrue(multicastMessages.isEmpty());
		Assert.assertTrue(unicastMessages.isEmpty());

		Message message = new Message();
		Address address = new TestAddress("test.address");

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

	@Aspect
	public static class TransportationConfigurationAdvice {

		public static void setChannelCount(int channelCount) {
			_CHANNEL_COUNT = channelCount;
		}

		@Around(
			"execution(* com.liferay.portal.util.PropsUtil.getProperties(" +
				"String, boolean))"
		)
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
							CharPool.POUND + i, "channel properties");
				}

				return properties;
			}

			return proceedingJoinPoint.proceed();
		}

		private static int _CHANNEL_COUNT = 0;

	}

	protected ClusterLinkImpl getClusterLinkImpl() {
		ClusterLinkImpl clusterLinkImpl = new ClusterLinkImpl();

		clusterLinkImpl.setClusterChannelFactory(
			new TestClusterChannelFactory());

		clusterLinkImpl.initialize();

		return clusterLinkImpl;
	}

}