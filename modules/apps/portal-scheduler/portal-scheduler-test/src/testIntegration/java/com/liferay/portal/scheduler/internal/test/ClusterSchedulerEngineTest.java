/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ClusterSchedulerEngineTest implements Serializable {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final TomcatClusterTestRule tomcatClusterTestRule =
		new TomcatClusterTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		TomcatCluster.Builder builder1 =
			tomcatClusterTestRule.buildTomcatNode();

		_tomcatNode1 = builder1.build();

		_tomcatNode1.start(true);

		TomcatCluster.Builder builder2 =
			tomcatClusterTestRule.buildTomcatNode();

		_tomcatNode2 = builder2.build();

		_tomcatNode2.start(true);
	}

	@Test
	public void testDoMasterTokenAcquiredWithJobScheduledOnAllNodes()
		throws Exception {

		String jobName = RandomTestUtil.randomString();

		TomcatNode masterTomcatNode = _tomcatNode2;
		TomcatNode slaveTomcatNode = _tomcatNode1;

		if (_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster)) {
			masterTomcatNode = _tomcatNode1;
			slaveTomcatNode = _tomcatNode2;
		}

		Future<?> slaveJobExecutionFuture = slaveTomcatNode.execute(
			() -> {
				TestSchedulerJobConfiguration.registerAndAwaitExecution(
					jobName);

				return null;
			});

		masterTomcatNode.syncExecute(
			() -> {
				TestSchedulerJobConfiguration.registerAndAwaitExecution(
					jobName);

				return null;
			});

		Assert.assertFalse(slaveJobExecutionFuture.isDone());

		Future<?> slaveMasterTokenFuture = slaveTomcatNode.execute(
			() -> {
				TestClusterMasterTokenTransitionListener.
					registerAndAwaitMasterToken();

				return null;
			});

		masterTomcatNode.stop();

		try {
			slaveMasterTokenFuture.get();

			Assert.assertTrue(
				slaveTomcatNode.syncExecute(
					ClusterMasterExecutorUtil::isMaster));

			slaveJobExecutionFuture.get();

			Assert.assertNotNull(
				slaveTomcatNode.syncExecute(
					() -> SchedulerEngineHelperUtil.getScheduledJob(
						jobName, jobName, StorageType.MEMORY_CLUSTERED)));
		}
		finally {
			masterTomcatNode.start(true);
		}
	}

	@Test
	public void testDoMasterTokenAcquiredWithJobScheduledOnMaster()
		throws Exception {

		String jobName = RandomTestUtil.randomString();

		TomcatNode masterTomcatNode = _tomcatNode2;
		TomcatNode slaveTomcatNode = _tomcatNode1;

		if (_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster)) {
			masterTomcatNode = _tomcatNode1;
			slaveTomcatNode = _tomcatNode2;
		}

		masterTomcatNode.syncExecute(
			() -> {
				TestSchedulerJobConfiguration.registerAndAwaitExecution(
					jobName);

				return null;
			});

		Future<?> future = slaveTomcatNode.execute(
			() -> {
				TestClusterMasterTokenTransitionListener.
					registerAndAwaitMasterToken();

				return null;
			});

		masterTomcatNode.stop();

		try {
			future.get();

			Assert.assertTrue(
				slaveTomcatNode.syncExecute(
					ClusterMasterExecutorUtil::isMaster));

			Assert.assertTrue(
				slaveTomcatNode.syncExecute(
					() -> {
						try (LogCapture logCapture =
								LoggerTestUtil.configureLog4JLogger(
									"com.liferay.portal.scheduler.quartz." +
										"internal.QuartzSchedulerEngine",
									LoggerTestUtil.WARN)) {

							TestSchedulerJobConfiguration.
								registerAndAwaitExecution(jobName);

							for (String message : logCapture.getMessages()) {
								if (message.contains(jobName) &&
									message.contains("already exists")) {

									return true;
								}
							}

							return false;
						}
					}));

			Assert.assertNotNull(
				slaveTomcatNode.syncExecute(
					() -> SchedulerEngineHelperUtil.getScheduledJob(
						jobName, jobName, StorageType.MEMORY_CLUSTERED)));
		}
		finally {
			masterTomcatNode.start(true);
		}
	}

	@Test
	public void testGetScheduledJobs() throws Exception {
		TomcatNode masterTomcatNode = _tomcatNode2;
		TomcatNode slaveTomcatNode = _tomcatNode1;

		if (_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster)) {
			masterTomcatNode = _tomcatNode1;
			slaveTomcatNode = _tomcatNode2;
		}

		_testGetScheduledJobs(
			masterTomcatNode, masterTomcatNode, slaveTomcatNode,
			StorageType.MEMORY);
		_testGetScheduledJobs(
			masterTomcatNode, slaveTomcatNode, slaveTomcatNode,
			StorageType.MEMORY);
		_testGetScheduledJobs(
			masterTomcatNode, masterTomcatNode, slaveTomcatNode,
			StorageType.MEMORY_CLUSTERED);
		_testGetScheduledJobs(
			masterTomcatNode, slaveTomcatNode, slaveTomcatNode,
			StorageType.MEMORY_CLUSTERED);
		_testGetScheduledJobs(
			masterTomcatNode, masterTomcatNode, slaveTomcatNode,
			StorageType.PERSISTED);
		_testGetScheduledJobs(
			masterTomcatNode, slaveTomcatNode, slaveTomcatNode,
			StorageType.PERSISTED);
	}

	private void _testGetScheduledJobs(
			TomcatNode masterTomcatNode, TomcatNode mutatorTomcatNode,
			TomcatNode slaveTomcatNode, StorageType storageType)
		throws Exception {

		String suffix = RandomTestUtil.randomString();

		String destinationName = "liferay/test_fire_" + suffix;
		String groupName = "test.job.group." + suffix;
		String jobName = "test.job.name." + suffix;

		TomcatNode observerTomcatNode = masterTomcatNode;

		if (mutatorTomcatNode == masterTomcatNode) {
			observerTomcatNode = slaveTomcatNode;
		}

		Assert.assertEquals(
			0,
			(int)observerTomcatNode.syncExecute(
				() -> {
					List<SchedulerResponse> schedulerResponses =
						SchedulerEngineHelperUtil.getScheduledJobs(
							groupName, storageType);

					return schedulerResponses.size();
				}));

		slaveTomcatNode.syncExecute(
			() -> {
				TestSchedulerMessageListener.register(destinationName);

				return null;
			});

		Future<?> future = masterTomcatNode.execute(
			() -> {
				TestSchedulerMessageListener.register(destinationName);

				TestSchedulerMessageListener.await(destinationName);

				return null;
			});

		try {
			mutatorTomcatNode.syncExecute(
				() -> {
					SchedulerEngineHelperUtil.schedule(
						TriggerFactoryUtil.createTrigger(
							jobName, groupName, 10, TimeUnit.SECOND),
						storageType, RandomTestUtil.randomString(),
						destinationName, new Message());

					return null;
				});

			future.get();

			SchedulerResponse schedulerResponse =
				observerTomcatNode.syncExecute(
					() -> SchedulerEngineHelperUtil.getScheduledJob(
						jobName, groupName, storageType));

			Assert.assertEquals(jobName, schedulerResponse.getJobName());
			Assert.assertEquals(
				destinationName, schedulerResponse.getDestinationName());
		}
		finally {
			mutatorTomcatNode.syncExecute(
				() -> {
					SchedulerEngineHelperUtil.delete(
						jobName, groupName, storageType);

					return null;
				});
		}
	}

	private static transient TomcatNode _tomcatNode1;
	private static transient TomcatNode _tomcatNode2;

	private static class TestClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public static void registerAndAwaitMasterToken() throws Exception {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			TestClusterMasterTokenTransitionListener
				testClusterMasterTokenTransitionListener =
					new TestClusterMasterTokenTransitionListener();

			ServiceRegistration<?> serviceRegistration =
				bundleContext.registerService(
					ClusterMasterTokenTransitionListener.class,
					testClusterMasterTokenTransitionListener, null);

			CountDownLatch countDownLatch =
				testClusterMasterTokenTransitionListener._countDownLatch;

			countDownLatch.await();

			serviceRegistration.unregister();
		}

		@Override
		public void masterTokenAcquired() {
			_countDownLatch.countDown();
		}

		@Override
		public void masterTokenReleased() {
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);

	}

	private static class TestSchedulerJobConfiguration
		implements SchedulerJobConfiguration {

		public static void registerAndAwaitExecution(String name)
			throws Exception {

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			TestSchedulerJobConfiguration testSchedulerJobConfiguration =
				new TestSchedulerJobConfiguration(name);

			bundleContext.registerService(
				SchedulerJobConfiguration.class, testSchedulerJobConfiguration,
				null);

			CountDownLatch countDownLatch =
				testSchedulerJobConfiguration._countDownLatch;

			countDownLatch.await();
		}

		@Override
		public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
			return _countDownLatch::countDown;
		}

		@Override
		public String getName() {
			return _name;
		}

		@Override
		public TriggerConfiguration getTriggerConfiguration() {
			return TriggerConfiguration.createTriggerConfiguration(
				5, TimeUnit.SECOND);
		}

		private TestSchedulerJobConfiguration(String name) {
			_name = name;
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);
		private final String _name;

	}

	private static class TestSchedulerMessageListener
		implements MessageListener {

		public static void await(String destinationName) throws Exception {
			CountDownLatch countDownLatch = _getCountDownLatch(destinationName);

			countDownLatch.await();
		}

		public static void countDown(String destinationName) {
			CountDownLatch countDownLatch = _getCountDownLatch(destinationName);

			countDownLatch.countDown();
		}

		public static void register(String destinationName) {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			DestinationFactory destinationFactory = bundleContext.getService(
				bundleContext.getServiceReference(DestinationFactory.class));

			bundleContext.registerService(
				Destination.class,
				destinationFactory.createDestination(
					DestinationConfiguration.
						createSerialDestinationConfiguration(destinationName)),
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", destinationName
				).build());

			bundleContext.registerService(
				MessageListener.class,
				new TestSchedulerMessageListener(destinationName),
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name", destinationName
				).build());
		}

		@Override
		public void receive(Message message) {
			ClusterMasterExecutorUtil.executeOnMaster(
				new MethodHandler(
					new MethodKey(
						TestSchedulerMessageListener.class, "countDown",
						String.class),
					_destinationName));
		}

		private static CountDownLatch _getCountDownLatch(
			String destinationName) {

			return _countDownLatches.computeIfAbsent(
				destinationName, key -> new CountDownLatch(1));
		}

		private TestSchedulerMessageListener(String destinationName) {
			_destinationName = destinationName;
		}

		private static final Map<String, CountDownLatch> _countDownLatches =
			new ConcurrentHashMap<>();

		private final String _destinationName;

	}

}