/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.process.local.LocalProcessLauncher;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log4j.Log4JUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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
	public void testShutdownAndStartupNodes() throws Exception {

		// Assert node 1 and node 2 can see each others

		_assertNodesVisibleToEachOther(_tomcatNode1, _tomcatNode2);

		// Restart node 1, use node 2 as verifier

		_restartAndVerifyNode(_tomcatNode1, _tomcatNode2);

		// Restart node 2, use node 1 as verifier

		_restartAndVerifyNode(_tomcatNode2, _tomcatNode1);
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

	@Test
	public void testTCPControlChannelProperties() throws Exception {
		_testControlChannelProperties(
			Collections.singletonMap(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL, "tcp.xml"));
	}

	@Test
	public void testUDPControlChannelProperties() throws Exception {
		_testControlChannelProperties(
			HashMapBuilder.put(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL, "udp.xml"
			).put(
				"cluster.link.channel.properties.transport.0", "udp.xml"
			).build());
	}

	private void _assertNodesVisibleToEachOther(
			TomcatNode tomcatNode1, TomcatNode tomcatNode2)
		throws Exception {

		// Assert node 1 has a valid cluster node

		ClusterNode clusterNodeOne = tomcatNode1.syncExecute(
			ClusterExecutorUtil::getLocalClusterNode);

		Assert.assertNotNull(clusterNodeOne);

		// Assert node 2 has a valid cluster node

		ClusterNode clusterNodeTwo = tomcatNode2.syncExecute(
			ClusterExecutorUtil::getLocalClusterNode);

		Assert.assertNotNull(clusterNodeTwo);

		Assert.assertTrue(
			tomcatNode1.syncExecute(
				() -> ClusterExecutorUtil.getClusterNodes(
				).contains(
					clusterNodeTwo
				)));

		Assert.assertTrue(
			tomcatNode2.syncExecute(
				() -> ClusterExecutorUtil.getClusterNodes(
				).contains(
					clusterNodeOne
				)));
	}

	private TomcatNode _createAndStartTomcatNodeWithProperties(
			Collection<String> properties)
		throws Exception {

		TomcatCluster.Builder builder = tomcatClusterTestRule.buildTomcatNode();

		builder.configureCopyPropertyKeys(
			copyPropertyKeys -> {
				for (String property : properties) {
					copyPropertyKeys.add(property);
				}
			});

		TomcatNode tomcatNode = builder.build();

		tomcatNode.start(true);

		return tomcatNode;
	}

	private String _getClusterNodeIdByTomcatNode() {
		return ClusterExecutorUtil.getLocalClusterNode(
		).getClusterNodeId();
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

	private void _restartAndVerifyNode(
			TomcatNode restartNode, TomcatNode verifierNode)
		throws Exception {

		// Capture state before stopping the restartNode

		ClusterNode restartClusterNode = restartNode.syncExecute(
			ClusterExecutorUtil::getLocalClusterNode);

		ClusterNode verifierClusterNode = verifierNode.syncExecute(
			ClusterExecutorUtil::getLocalClusterNode);

		Assert.assertNotNull(restartClusterNode);
		Assert.assertNotNull(verifierClusterNode);

		// Stop restart node

		restartNode.stop();

		// Assert verifier node still has the same cluster node id running

		Assert.assertEquals(
			verifierClusterNode.getClusterNodeId(),
			verifierNode.syncExecute(() -> _getClusterNodeIdByTomcatNode()));

		// Assert verifier node cannot see restart node

		Assert.assertTrue(
			verifierNode.syncExecute(
				() -> !ClusterExecutorUtil.getClusterNodes(
				).contains(
					restartClusterNode
				)));

		// Restart restart node

		restartNode.start(true);

		// Assert restart node has a valid cluster node (New ID)

		ClusterNode newRestartClusterNode = restartNode.syncExecute(
			ClusterExecutorUtil::getLocalClusterNode);

		Assert.assertNotNull(newRestartClusterNode);

		// Assert verifier still has the same cluster node id running

		Assert.assertEquals(
			verifierClusterNode.getClusterNodeId(),
			verifierNode.syncExecute(() -> _getClusterNodeIdByTomcatNode()));

		// Assert mutual visibility with the NEW restart node

		_assertNodesVisibleToEachOther(restartNode, verifierNode);
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

	private void _testControlChannelProperties(Map<String, String> properties)
		throws Exception {

		// Set these properties globally

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			PropsUtil.set(entry.getKey(), entry.getValue());
		}

		// Create nodes with properties

		TomcatNode tomcatNode3 = _createAndStartTomcatNodeWithProperties(
			properties.keySet());
		TomcatNode tomcatNode4 = _createAndStartTomcatNodeWithProperties(
			properties.keySet());

		// Assert properties are set correctly on both nodes

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String key = entry.getKey();
			String expectedValue = entry.getValue();

			// Assert Node 3

			Assert.assertEquals(
				expectedValue,
				tomcatNode3.syncExecute(() -> PropsUtil.get(key)));

			// Assert Node 4

			Assert.assertEquals(
				expectedValue,
				tomcatNode4.syncExecute(() -> PropsUtil.get(key)));
		}

		// Assert nodes can get their cluster node IDs successfully

		Assert.assertNotNull(
			tomcatNode3.syncExecute(() -> _getClusterNodeIdByTomcatNode()));

		Assert.assertNotNull(
			tomcatNode4.syncExecute(() -> _getClusterNodeIdByTomcatNode()));
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