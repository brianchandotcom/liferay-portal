/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal.jgroups;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration;
import com.liferay.portal.cluster.multiple.internal.ClusterChannel;
import com.liferay.portal.cluster.multiple.internal.ClusterChannelFactory;
import com.liferay.portal.cluster.multiple.internal.ClusterReceiver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.test.performance.PerformanceTimer;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Kevin Lee
 */
public class JGroupsClusterChannelPerformanceTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz = JGroupsClusterChannelPerformanceTest.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/jgroups-cluster-channel-performance.properties"),
			"UTF-8");

		_executorService = Executors.newSingleThreadExecutor();
		_iterations = GetterUtil.getInteger(
			properties.getProperty("jgroups.cluster.channel.iterations"));
		_message = RandomTestUtil.randomString(
			GetterUtil.getInteger(
				properties.getProperty(
					"jgroups.cluster.channel.messages.length")));
		_messagesCount = GetterUtil.getInteger(
			properties.getProperty("jgroups.cluster.channel.messages.count"));
		_messagesTimeout = GetterUtil.getLong(
			properties.getProperty("jgroups.cluster.channel.messages.timeout"));

		String logFile = properties.getProperty(
			"jgroups.cluster.channel.log.file");

		if (Validator.isNotNull(logFile)) {
			_logFilePath = Paths.get(logFile);
		}

		ClusterChannelFactory clusterChannelFactory =
			new JGroupsClusterChannelFactory(
				ConfigurableUtil.createConfigurable(
					ClusterExecutorConfiguration.class,
					Collections.emptyMap()));

		_receiveClusterChannel = clusterChannelFactory.createClusterChannel(
			_executorService, "receive",
			PropsUtil.get(PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL),
			"test", new TestClusterReceiver());

		_sendClusterChannel = clusterChannelFactory.createClusterChannel(
			_executorService, "send",
			PropsUtil.get(PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL),
			"test", new TestClusterReceiver());
	}

	@AfterClass
	public static void tearDownClass() {
		_receiveClusterChannel.close();
		_sendClusterChannel.close();

		_executorService.shutdown();
	}

	@Test
	public void testMulticast() throws Exception {
		_test(true);
	}

	@Test
	public void testUnicast() throws Exception {
		_test(false);
	}

	private void _test(boolean multicast) throws Exception {
		String label;

		if (multicast) {
			label = "multicast";
		}
		else {
			label = "unicast";
		}

		for (int iteration = 1; iteration <= _iterations; iteration++) {
			TestClusterReceiver testClusterReceiver =
				(TestClusterReceiver)
					_receiveClusterChannel.getClusterReceiver();

			testClusterReceiver.reset();

			try (PerformanceTimer performanceTimer = new PerformanceTimer(
					_logFilePath, Long.MAX_VALUE,
					StringBundler.concat(
						label, " Iteration ", iteration, " (", _messagesCount,
						" messages x ", _message.length(), " length)"))) {

				for (int i = 0; i < _messagesCount; i++) {
					if (multicast) {
						_sendClusterChannel.sendMulticastMessage(_message);
					}
					else {
						_sendClusterChannel.sendUnicastMessage(
							_message, _receiveClusterChannel.getLocalAddress());
					}
				}

				Assert.assertTrue(
					testClusterReceiver.await(
						_messagesTimeout, TimeUnit.SECONDS));
			}
		}
	}

	private static ExecutorService _executorService;
	private static int _iterations;
	private static Path _logFilePath;
	private static String _message;
	private static int _messagesCount;
	private static long _messagesTimeout;
	private static ClusterChannel _receiveClusterChannel;
	private static ClusterChannel _sendClusterChannel;

	private static class TestClusterReceiver implements ClusterReceiver {

		@Override
		public void addressesUpdated(List<Address> addresses) {
		}

		public boolean await(long timeout, TimeUnit unit) throws Exception {
			return _countDownLatch.await(timeout, unit);
		}

		@Override
		public void coordinatorAddressUpdated(Address coordinatorAddress) {
		}

		@Override
		public List<Address> getAddresses() {
			return List.of();
		}

		@Override
		public Address getCoordinatorAddress() {
			return null;
		}

		@Override
		public void openLatch() {
		}

		@Override
		public void receive(Object payload, Address srcAddress) {
			if (Objects.equals(payload, _message)) {
				_countDownLatch.countDown();
			}
		}

		public void reset() {
			_countDownLatch = new CountDownLatch(_messagesCount);
		}

		private CountDownLatch _countDownLatch;

	}

}