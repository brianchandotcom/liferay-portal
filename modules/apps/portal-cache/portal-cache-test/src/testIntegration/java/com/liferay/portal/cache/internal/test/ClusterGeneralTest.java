/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class ClusterGeneralTest {

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
		_updateLogLevelsForAllNodes(_tomcatNode1, _tomcatNode2, true);
	}

	@Test
	public void testCanUpdateLogLevelsForAllNodesFromSlave() throws Exception {
		_updateLogLevelsForAllNodes(_tomcatNode2, _tomcatNode1, false);
	}

	@Test
	public void testSlaveNodeCanBecomeMasterNode() throws Exception {

		// Assert node 1 is master node

		Assert.assertTrue(
			_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Assert node 2 is slave node

		Assert.assertFalse(
			_tomcatNode2.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// register a listener for node 2

		_tomcatNode2.syncExecute(
			() -> {
				BundleContext bundleContext =
					SystemBundleUtil.getBundleContext();

				bundleContext.registerService(
					ClusterMasterTokenTransitionListener.class,
					new TestClusterMasterTokenTransitionListener(), null);

				return null;
			});

		// stop node 1

		_tomcatNode1.stop();

		// As node 1 stop, expect node 2 will become new master
		// wait till node 2 finish swapping from slave to master,
		// confirm it is master now

		Assert.assertTrue(
			_tomcatNode2.syncExecute(
				() -> {
					BundleContext bundleContext =
						SystemBundleUtil.getBundleContext();

					List<ServiceReference<ClusterMasterTokenTransitionListener>>
						serviceReferences = new ArrayList<>(
							bundleContext.getServiceReferences(
								ClusterMasterTokenTransitionListener.class,
								null));

					ServiceReference<ClusterMasterTokenTransitionListener>
						editServerMVCActionCommandServiceReference =
							serviceReferences.get(serviceReferences.size() - 1);

					TestClusterMasterTokenTransitionListener
						testClusterMasterTokenTransitionListener =
							(TestClusterMasterTokenTransitionListener)
								bundleContext.getService(
									editServerMVCActionCommandServiceReference);

					testClusterMasterTokenTransitionListener.getCountDownLatch(
					).await();

					return ClusterMasterExecutorUtil.isMaster();
				}));

		// Restart node 1

		_tomcatNode1.start(true);

		// Assert node 2 is still master node

		Assert.assertTrue(
			_tomcatNode2.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Assert node 1 is still slave node

		Assert.assertFalse(
			_tomcatNode1.syncExecute(ClusterMasterExecutorUtil::isMaster));
	}

	private static void _await() throws Exception {
		LoggerContext loggerContext = LoggerContext.getContext();

		List<TestPropertyChangeListener> listeners =
			ReflectionTestUtil.getFieldValue(
				loggerContext, "propertyChangeListeners");

		TestPropertyChangeListener testPropertyChangeListener = listeners.get(
			0);

		testPropertyChangeListener.await();

		loggerContext.removePropertyChangeListener(testPropertyChangeListener);
	}

	private static MVCActionCommand _getEditServerMVCActionCommand()
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

	private void _updateLogLevelsForAllNodes(
			TomcatNode updateTomcatNode, TomcatNode listenTomcatNode,
			boolean updateTomcatNodeIsMasterNode)
		throws Exception {

		// Assert updateTomcatNode is master node
		// when updateTomcatNodeIsMasterNode is true

		Assert.assertEquals(
			updateTomcatNodeIsMasterNode,
			updateTomcatNode.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Assert listenTomcatNode is master node
		// when updateTomcatNodeIsMasterNode is false

		Assert.assertEquals(
			!updateTomcatNodeIsMasterNode,
			listenTomcatNode.syncExecute(ClusterMasterExecutorUtil::isMaster));

		// Register listener for listenTomcatNode

		listenTomcatNode.syncExecute(
			() -> {
				LoggerContext loggerContext = LoggerContext.getContext();

				loggerContext.addPropertyChangeListener(
					new TestPropertyChangeListener());

				return null;
			});

		// Update properties in updateTomcatNode and assert change

		Assert.assertEquals(
			"DEBUG",
			updateTomcatNode.syncExecute(
				() -> {
					ReflectionTestUtil.invoke(
						_getEditServerMVCActionCommand(), "_updateLogLevels",
						new Class<?>[] {Map.class},
						Collections.singletonMap(
							ClusterGeneralTest.class.getName(), "DEBUG"));

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));

		// Assert the change in listenTomcatNode

		Assert.assertEquals(
			"DEBUG",
			listenTomcatNode.syncExecute(
				() -> {
					_await();

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));

		// Register listener for listenTomcatNode

		listenTomcatNode.syncExecute(
			() -> {
				LoggerContext loggerContext = LoggerContext.getContext();

				loggerContext.addPropertyChangeListener(
					new TestPropertyChangeListener());

				return null;
			});

		// Update properties in updateTomcatNode and assert change

		Assert.assertEquals(
			"ERROR",
			updateTomcatNode.syncExecute(
				() -> {
					ReflectionTestUtil.invoke(
						_getEditServerMVCActionCommand(), "_updateLogLevels",
						new Class<?>[] {Map.class},
						Collections.singletonMap(
							ClusterGeneralTest.class.getName(), "ERROR"));

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));

		// Assert the change in listenTomcatNode

		Assert.assertEquals(
			"ERROR",
			listenTomcatNode.syncExecute(
				() -> {
					_await();

					return Log4JUtil.getPriority(
						ClusterGeneralTest.class.getName());
				}));
	}

	private static TomcatNode _tomcatNode1;
	private static TomcatNode _tomcatNode2;

	private static class TestClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public TestClusterMasterTokenTransitionListener() {
			_stoppedLatch = new CountDownLatch(1);
		}

		public CountDownLatch getCountDownLatch() {
			return _stoppedLatch;
		}

		@Override
		public void masterTokenAcquired() {
			_stoppedLatch.countDown();
		}

		@Override
		public void masterTokenReleased() {
		}

		private final CountDownLatch _stoppedLatch;

	}

	private static class TestPropertyChangeListener
		implements PropertyChangeListener {

		public void await() throws Exception {
			_countDownLatch.await();
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			_countDownLatch.countDown();
		}

		private final CountDownLatch _countDownLatch = new CountDownLatch(1);

	}

}