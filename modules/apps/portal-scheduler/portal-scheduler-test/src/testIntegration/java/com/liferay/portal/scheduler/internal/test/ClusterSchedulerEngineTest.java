/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

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

}