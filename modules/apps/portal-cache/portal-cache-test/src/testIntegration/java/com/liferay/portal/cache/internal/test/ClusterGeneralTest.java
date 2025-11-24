/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.process.local.LocalProcessLauncher;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.log4j.Log4JUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.core.LoggerContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ClusterGeneralTest implements Serializable {

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
	public void testCanUpdateLogLevelsForAllNodesFromMaster() throws Exception {
		_testCanUpdateLogLevelsForAllNodes(_tomcatNode2, _tomcatNode1, true);
	}

	@Test
	public void testCanUpdateLogLevelsForAllNodesFromSlave() throws Exception {
		_testCanUpdateLogLevelsForAllNodes(_tomcatNode1, _tomcatNode2, false);
	}

	@Test
	public void testSlaveNodeCanBecomeMasterNode() throws Exception {

		// Assert node 1 is the master node

		Assert.assertTrue(
			_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Assert node 2 is a slave node

		Assert.assertFalse(
			_tomcatNode2.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Register a listener for node 2

		_tomcatNode2.syncExecute(
			() -> {
				TestClusterMasterTokenTransitionListener.register();

				return null;
			});

		// Stop node 1

		_tomcatNode1.stop();

		// After node 1 stops, confirm that node 2 is the new master node

		Assert.assertTrue(
			_tomcatNode2.syncExecute(
				() -> {
					TestClusterMasterTokenTransitionListener.await();

					return ClusterMasterExecutorUtil.isMaster();
				}));

		// Restart node 1

		_tomcatNode1.start(true);

		// Assert node 2 is still the master node

		Assert.assertTrue(
			_tomcatNode2.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Assert node 1 is still a slave node

		Assert.assertFalse(
			_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster));
	}

	private MVCActionCommand _getEditServerMVCActionCommand()
		throws InvalidSyntaxException {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Collection<ServiceReference<MVCActionCommand>> serviceReferences =
			bundleContext.getServiceReferences(
				MVCActionCommand.class,
				"(mvc.command.name=/server_admin/edit_server)");

		Iterator<ServiceReference<MVCActionCommand>> iterator =
			serviceReferences.iterator();

		ServiceReference<MVCActionCommand>
			editServerMVCActionCommandServiceReference = iterator.next();

		return bundleContext.getService(
			editServerMVCActionCommandServiceReference);
	}

	private void _testCanUpdateLogLevelsForAllNodes(
			TomcatNode receiverTomcatNode, TomcatNode senderTomcatNode,
			boolean senderTomcatNodeIsMaster)
		throws Exception {

		// Assert senderTomcatNode is the master node when
		// senderTomcatNodeIsMasterNode is true

		Assert.assertEquals(
			senderTomcatNodeIsMaster,
			senderTomcatNode.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Assert receiverTomcatNode is the master node when
		// senderTomcatNodeIsMasterNode is false

		Assert.assertEquals(
			!senderTomcatNodeIsMaster,
			receiverTomcatNode.syncExecute(
				ClusterMasterExecutorUtil::isMaster));

		// Register listener for receiverTomcatNode

		receiverTomcatNode.syncExecute(
			() -> {
				TestPropertyChangeListener.register();

				return null;
			});

		// Update properties in senderTomcatNode and assert change

		Assert.assertEquals(
			"DEBUG",
			senderTomcatNode.syncExecute(
				() -> {
					ReflectionTestUtil.invoke(
						_getEditServerMVCActionCommand(), "_updateLogLevels",
						new Class<?>[] {Map.class},
						Collections.singletonMap(
							ClusterGeneralTest.class.getName(), "DEBUG"));

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));

		// Assert the change in receiverTomcatNode

		Assert.assertEquals(
			"DEBUG",
			receiverTomcatNode.syncExecute(
				() -> {
					TestPropertyChangeListener.await();

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));

		// Register listener for receiverTomcatNode

		receiverTomcatNode.syncExecute(
			() -> {
				TestPropertyChangeListener.register();

				return null;
			});

		// Update properties in senderTomcatNode and assert change

		Assert.assertEquals(
			"ERROR",
			senderTomcatNode.syncExecute(
				() -> {
					ReflectionTestUtil.invoke(
						_getEditServerMVCActionCommand(), "_updateLogLevels",
						new Class<?>[] {Map.class},
						Collections.singletonMap(
							ClusterGeneralTest.class.getName(), "ERROR"));

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));

		// Assert the change in receiverTomcatNode

		Assert.assertEquals(
			"ERROR",
			receiverTomcatNode.syncExecute(
				() -> {
					TestPropertyChangeListener.await();

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));
	}

	private static transient TomcatNode _tomcatNode1;
	private static transient TomcatNode _tomcatNode2;

	private static class TestClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public static void await() throws Exception {
			Map<String, Object> attributes =
				LocalProcessLauncher.ProcessContext.getAttributes();

			TestClusterMasterTokenTransitionListener
				testClusterMasterTokenTransitionListener =
					(TestClusterMasterTokenTransitionListener)attributes.remove(
						TestClusterMasterTokenTransitionListener.class.
							getName());

			CountDownLatch countDownLatch =
				testClusterMasterTokenTransitionListener._countDownLatch;

			countDownLatch.await();

			ServiceRegistration<?> serviceRegistration =
				testClusterMasterTokenTransitionListener._serviceRegistration;

			serviceRegistration.unregister();
		}

		public static void register() {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			TestClusterMasterTokenTransitionListener
				testClusterMasterTokenTransitionListener =
					new TestClusterMasterTokenTransitionListener();

			testClusterMasterTokenTransitionListener._serviceRegistration =
				bundleContext.registerService(
					ClusterMasterTokenTransitionListener.class,
					testClusterMasterTokenTransitionListener, null);

			Map<String, Object> attributes =
				LocalProcessLauncher.ProcessContext.getAttributes();

			attributes.put(
				TestClusterMasterTokenTransitionListener.class.getName(),
				testClusterMasterTokenTransitionListener);
		}

		@Override
		public void masterTokenAcquired() {
			_countDownLatch.countDown();
		}

		@Override
		public void masterTokenReleased() {
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);
		private ServiceRegistration<?> _serviceRegistration;

	}

	private static class TestPropertyChangeListener
		implements PropertyChangeListener {

		public static void await() throws Exception {
			Map<String, Object> attributes =
				LocalProcessLauncher.ProcessContext.getAttributes();

			TestPropertyChangeListener testPropertyChangeListener =
				(TestPropertyChangeListener)attributes.remove(
					TestPropertyChangeListener.class.getName());

			CountDownLatch countDownLatch =
				testPropertyChangeListener._countDownLatch;

			countDownLatch.await();

			LoggerContext loggerContext = LoggerContext.getContext();

			loggerContext.removePropertyChangeListener(
				testPropertyChangeListener);
		}

		public static void register() {
			LoggerContext loggerContext = LoggerContext.getContext();

			TestPropertyChangeListener testPropertyChangeListener =
				new TestPropertyChangeListener();

			loggerContext.addPropertyChangeListener(testPropertyChangeListener);

			Map<String, Object> attributes =
				LocalProcessLauncher.ProcessContext.getAttributes();

			attributes.put(
				TestPropertyChangeListener.class.getName(),
				testPropertyChangeListener);
		}

		@Override
		public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
			_countDownLatch.countDown();
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);

	}

}