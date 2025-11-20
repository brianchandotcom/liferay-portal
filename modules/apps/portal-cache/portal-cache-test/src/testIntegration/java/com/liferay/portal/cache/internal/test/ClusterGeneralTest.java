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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
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

		// register a listener for node 1

		_tomcatNode1.syncExecute(
			() -> {
				BundleContext bundleContext =
					SystemBundleUtil.getBundleContext();

				bundleContext.registerService(
					ClusterMasterTokenTransitionListener.class,
					new TestClusterMasterTokenTransitionListener(), null);

				return null;
			});

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

	private static CountDownLatch _getCountDownLatch() {
		LoggerContext loggerContext = LoggerContext.getContext();

		List<TestPropertyChangeListener> listeners =
			ReflectionTestUtil.getFieldValue(
				loggerContext, "propertyChangeListeners");

		TestPropertyChangeListener listener = listeners.get(0);

		return listener.getCountDownLatch();
	}

	private static MVCActionCommand _getEditServerMVCActionCommand()
		throws InvalidSyntaxException {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		List<ServiceReference<MVCActionCommand>> serviceReferences =
			new ArrayList<>(
				bundleContext.getServiceReferences(
					MVCActionCommand.class,
					"(mvc.command.name=/server_admin/edit_server)"));

		ServiceReference<MVCActionCommand>
			editServerMVCActionCommandServiceReference = serviceReferences.get(
				0);

		return bundleContext.getService(
			editServerMVCActionCommandServiceReference);
	}

	private static TestPropertyChangeListener _getTestPropertyChangeListener() {
		LoggerContext loggerContext = LoggerContext.getContext();

		List<TestPropertyChangeListener> listeners =
			ReflectionTestUtil.getFieldValue(
				loggerContext, "propertyChangeListeners");

		return listeners.get(0);
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

		String defaultValue = null;

		try {

			// Get the default value of the property

			defaultValue = updateTomcatNode.syncExecute(
				() -> {
					Map<String, String> priorities = Log4JUtil.getPriorities();

					return priorities.get(
						"com.liferay.portal.servlet.filters.autologin." +
							"AutoLoginFilter");
				});

			// Assert the default value of updateTomcatNode

			Assert.assertEquals("ERROR", defaultValue);

			// Register listener for listenTomcatNode

			listenTomcatNode.syncExecute(
				() -> {
					LoggerContext loggerContext = LoggerContext.getContext();

					int size;

					if (updateTomcatNodeIsMasterNode) {

						// CountDown Log4JUtil.getPriorities().size() times
						// because when master node update,
						// all the properties in slave node need to update

						size = Log4JUtil.getPriorities(
						).size();
					}
					else {

						// CountDown only once because when slave node update,
						// only one property need to update in master node

						size = 1;
					}

					loggerContext.addPropertyChangeListener(
						new TestPropertyChangeListener(size));

					return null;
				});

			// Update properties in updateTomcatNode and assert change

			Assert.assertEquals(
				"DEBUG",
				updateTomcatNode.syncExecute(
					() -> {
						ReflectionTestUtil.invoke(
							_getEditServerMVCActionCommand(),
							"_updateLogLevels", new Class<?>[] {Map.class},
							HashMapBuilder.put(
								"com.liferay.portal.servlet.filters." +
									"autologin.AutoLoginFilter",
								"DEBUG"
							).build());

						return Log4JUtil.getPriorities(
						).get(
							"com.liferay.portal.servlet.filters.autologin." +
								"AutoLoginFilter"
						);
					}));

			// Assert the change in listenTomcatNode

			Assert.assertEquals(
				"DEBUG",
				listenTomcatNode.syncExecute(
					() -> {
						_getCountDownLatch().await();

						return Log4JUtil.getPriorities(
						).get(
							"com.liferay.portal.servlet.filters.autologin." +
								"AutoLoginFilter"
						);
					}));
		}
		finally {

			// Remove the listener in listenTomcatNode

			listenTomcatNode.syncExecute(
				() -> {
					LoggerContext loggerContext = LoggerContext.getContext();

					loggerContext.removePropertyChangeListener(
						_getTestPropertyChangeListener());

					return null;
				});

			Map<String, String> restoreMap = HashMapBuilder.put(
				"com.liferay.portal.servlet.filters.autologin.AutoLoginFilter",
				defaultValue
			).build();

			// Restore the property for updateTomcatNode

			updateTomcatNode.syncExecute(
				() -> {
					ReflectionTestUtil.invoke(
						_getEditServerMVCActionCommand(), "_updateLogLevels",
						new Class<?>[] {Map.class}, restoreMap);

					return null;
				});

			// Restore the property for listenTomcatNode

			listenTomcatNode.syncExecute(
				() -> {
					ReflectionTestUtil.invoke(
						_getEditServerMVCActionCommand(), "_updateLogLevels",
						new Class<?>[] {Map.class}, restoreMap);

					return null;
				});
		}
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
			_stoppedLatch.countDown();
		}

		private final CountDownLatch _stoppedLatch;

	}

	private static class TestPropertyChangeListener
		implements PropertyChangeListener {

		public TestPropertyChangeListener(int countDown) {
			_countDownLatch = new CountDownLatch(countDown);
		}

		public CountDownLatch getCountDownLatch() {
			return _countDownLatch;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			_countDownLatch.countDown();
		}

		private final CountDownLatch _countDownLatch;

	}

}