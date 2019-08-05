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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.lang.reflect.Field;

import java.net.InetAddress;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ClusterMasterExecutorImplTest extends BaseClusterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Test
	public void testClusterMasterTokenClusterEventListener() throws Exception {

		// Test 1, test when coordiator is not changed

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		mockClusterExecutor.addClusterNode(
			_TEST_ADDRESS,
			new ClusterNode(_TEST_CLUSTER_NODE_ID, InetAddress.getLocalHost()));

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		List<ClusterEventListener> clusterEventListeners =
			mockClusterExecutor.getClusterEventListeners();

		ClusterEventListener clusterEventListener = clusterEventListeners.get(
			0);

		clusterEventListener.processClusterEvent(
			new ClusterEvent(ClusterEventType.COORDINATOR_ADDRESS_UPDATE));

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 2, test JOIN event when coordiator is changed

		mockClusterExecutor.setCoordinatorAddress(_TEST_ADDRESS);

		clusterEventListener.processClusterEvent(
			new ClusterEvent(ClusterEventType.JOIN));

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 3, test DEPART event when coordiator is changed

		mockClusterExecutor.setCoordinatorAddress(_TEST_ADDRESS);

		clusterEventListener.processClusterEvent(
			new ClusterEvent(ClusterEventType.DEPART));

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 4, test COORDINATOR_ADDRESS_UPDATE event when coordiator is
		// changed

		mockClusterExecutor.setCoordinatorAddress(_TEST_ADDRESS);

		clusterEventListener.processClusterEvent(
			new ClusterEvent(ClusterEventType.COORDINATOR_ADDRESS_UPDATE));

		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testClusterMasterTokenTransitionListeners() {

		// Test 1, register cluster master token transition listener

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListeners =
				ReflectionTestUtil.getFieldValue(
					clusterMasterExecutorImpl,
					"_clusterMasterTokenTransitionListeners");

		Assert.assertTrue(clusterMasterTokenTransitionListeners.isEmpty());

		ClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		Assert.assertEquals(
			clusterMasterTokenTransitionListeners.toString(), 1,
			clusterMasterTokenTransitionListeners.size());

		// Test 2, unregister cluster master token transition listener

		clusterMasterExecutorImpl.removeClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		Assert.assertTrue(clusterMasterTokenTransitionListeners.isEmpty());

		// Test 3, set cluster master token transition listeners

		clusterMasterExecutorImpl.setClusterMasterTokenTransitionListeners(
			Collections.singleton(mockClusterMasterTokenTransitionListener));

		Assert.assertEquals(
			clusterMasterTokenTransitionListeners.toString(), 1,
			clusterMasterTokenTransitionListeners.size());
	}

	@Test
	public void testDeactivate() {

		// Test 1, destroy when cluster link is enabled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		List<ClusterEventListener> clusterEventListeners =
			mockClusterExecutor.getClusterEventListeners();

		Assert.assertEquals(
			clusterEventListeners.toString(), 1, clusterEventListeners.size());

		clusterMasterExecutorImpl.deactivate();

		Assert.assertTrue(
			clusterEventListeners.toString(), clusterEventListeners.isEmpty());

		// Test 2, destory when cluster link is disabled

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutorImpl(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		clusterMasterExecutorImpl.deactivate();
	}

	@Test
	public void testExecuteOnMasterDisabled() throws Exception {

		// Test 1, execute without exception when log is eanbled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutorImpl(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(System.currentTimeMillis());

		MethodHandler methodHandler = new MethodHandler(
			_TEST_METHOD, timeString);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Executing on the local node because the cluster master " +
					"executor is disabled",
				logRecord.getMessage());
		}

		// Test 2, execute without exception when log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.toString(), logRecords.isEmpty());
		}

		// Test 3, execute with exception

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			try {
				clusterMasterExecutorImpl.executeOnMaster(null);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable throwable = se.getCause();

				Assert.assertSame(
					NullPointerException.class, throwable.getClass());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertEquals(
					logRecords.toString(), 1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"Executing on the local node because the cluster master " +
						"executor is disabled",
					logRecord.getMessage());
			}
		}
	}

	@Test
	public void testExecuteOnMasterEnabled() throws Exception {

		// Test 1, execute without exception

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(System.currentTimeMillis());

		NoticeableFuture<String> noticeableFuture =
			clusterMasterExecutorImpl.executeOnMaster(
				new MethodHandler(_TEST_METHOD, timeString));

		Assert.assertSame(timeString, noticeableFuture.get());

		// Test 2, execute with exception

		try {
			clusterMasterExecutorImpl.executeOnMaster(_BAD_METHOD_HANDLER);

			Assert.fail();
		}
		catch (SystemException se) {
			Assert.assertEquals(
				"Unable to execute on master " +
					mockClusterExecutor.getLocalClusterNodeId(),
				se.getMessage());
		}
	}

	@Test
	public void testGetMasterClusterNodeId() throws Exception {

		// Test 1, master to slave

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Assert.assertEquals(
			mockClusterExecutor.getLocalClusterNodeId(),
			clusterMasterExecutorImpl.getMasterClusterNodeId(true));
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		mockClusterExecutor.addClusterNode(
			_TEST_ADDRESS,
			new ClusterNode(_TEST_CLUSTER_NODE_ID, InetAddress.getLocalHost()));

		MockClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		Address oldCoordinatorAddress =
			mockClusterExecutor.getCoordinatorAddress();

		mockClusterExecutor.setCoordinatorAddress(_TEST_ADDRESS);

		Assert.assertEquals(
			_TEST_CLUSTER_NODE_ID,
			clusterMasterExecutorImpl.getMasterClusterNodeId(true));
		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());

		// Test 2, slave to master

		mockClusterExecutor.setCoordinatorAddress(oldCoordinatorAddress);

		Assert.assertEquals(
			mockClusterExecutor.getLocalClusterNodeId(),
			clusterMasterExecutorImpl.getMasterClusterNodeId(true));
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
	}

	@AdviseWith(adviceClasses = ClusterExecutorAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGetMasterClusterNodeIdRetry() throws Exception {

		// Test 1, retry to get cluster node when log is enabled

		final ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		mockClusterExecutor.setCoordinatorAddress(_TEST_ADDRESS);

		ClusterExecutorAdvice.block();

		Thread thread = new Thread() {

			@Override
			public void run() {
				try (CaptureHandler captureHandler =
						JDKLoggerTestUtil.configureJDKLogger(
							ClusterMasterExecutorImpl.class.getName(),
							Level.INFO)) {

					Assert.assertEquals(
						_TEST_CLUSTER_NODE_ID,
						clusterMasterExecutorImpl.getMasterClusterNodeId(
							false));

					List<LogRecord> logRecords = captureHandler.getLogRecords();

					Assert.assertEquals(
						logRecords.toString(), 1, logRecords.size());

					LogRecord logRecord = logRecords.get(0);

					Assert.assertEquals(
						StringBundler.concat(
							"Unable to get cluster node information for ",
							"coordinator address ", _TEST_ADDRESS,
							". Trying again."),
						logRecord.getMessage());
				}
			}

		};

		thread.start();

		ClusterExecutorAdvice.waitUntilBlock(1);

		ClusterExecutorAdvice.unblock(1);

		Assert.assertNull(ClusterExecutorAdvice.waitClusterNodeId());

		ClusterExecutorAdvice.waitUntilBlock(1);

		ClusterNode clusterNode = new ClusterNode(
			_TEST_CLUSTER_NODE_ID, InetAddress.getLocalHost());

		mockClusterExecutor.addClusterNode(_TEST_ADDRESS, clusterNode);

		ClusterExecutorAdvice.unblock(1);

		Assert.assertSame(
			_TEST_CLUSTER_NODE_ID, ClusterExecutorAdvice.waitClusterNodeId());

		thread.join();

		// Test 2, retry to get cluster node when log is disabled

		mockClusterExecutor.removeClusterNode(_TEST_ADDRESS);

		ClusterExecutorAdvice.block();

		thread = new Thread() {

			@Override
			public void run() {
				try (CaptureHandler captureHandler =
						JDKLoggerTestUtil.configureJDKLogger(
							ClusterMasterExecutorImpl.class.getName(),
							Level.OFF)) {

					Assert.assertEquals(
						_TEST_CLUSTER_NODE_ID,
						clusterMasterExecutorImpl.getMasterClusterNodeId(
							false));

					List<LogRecord> logRecords = captureHandler.getLogRecords();

					Assert.assertTrue(
						logRecords.toString(), logRecords.isEmpty());
				}
			}

		};

		thread.start();

		ClusterExecutorAdvice.waitUntilBlock(1);

		ClusterExecutorAdvice.unblock(1);

		Assert.assertNull(ClusterExecutorAdvice.waitClusterNodeId());

		ClusterExecutorAdvice.waitUntilBlock(1);

		mockClusterExecutor.addClusterNode(_TEST_ADDRESS, clusterNode);

		ClusterExecutorAdvice.unblock(1);

		Assert.assertSame(
			_TEST_CLUSTER_NODE_ID, ClusterExecutorAdvice.waitClusterNodeId());

		thread.join();
	}

	@Test
	public void testInitialize() throws Exception {

		// Test 1, initialize when cluster link is disabled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutorImpl(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 2, initialize when cluster link is enabled and master is exist

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutorImpl(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 3, initialize when cluster link is enabled and master is not
		// exist

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		mockClusterExecutor.addClusterNode(
			_TEST_ADDRESS,
			new ClusterNode(_TEST_CLUSTER_NODE_ID, InetAddress.getLocalHost()));

		mockClusterExecutor.setCoordinatorAddress(_TEST_ADDRESS);

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutorImpl(mockClusterExecutor);

		clusterMasterExecutorImpl.activate();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@AdviseWith(adviceClasses = SPIUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testMisc() {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutorImpl(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.activate();

		clusterMasterExecutorImpl.setClusterExecutorImpl(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.activate();
	}

	@Test
	public void testNotifyMasterTokenTransitionListeners() {

		// Test 1, notify when master is required

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(true);

		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
		Assert.assertFalse(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());

		// Test 2, notify when master is released

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		mockClusterMasterTokenTransitionListener =
			new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.addClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(false);

		Assert.assertFalse(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());
	}

	@Aspect
	public static class ClusterExecutorAdvice {

		public static void block() {
			_semaphore = new Semaphore(0);
		}

		public static void unblock(int permits) {
			_semaphore.release(permits);
		}

		public static String waitClusterNodeId() throws Exception {
			try {
				return _clusterNodeIdExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return "null";
			}
		}

		public static void waitUntilBlock(int threadCount) {
			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				while (semaphore.getQueueLength() < threadCount);
			}
		}

		@Around(
			"execution(protected * com.liferay.portal.cluster.multiple." +
				"internal.ClusterExecutorImpl.getClusterNodeId(..))"
		)
		public Object getClusterNodeId(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			Object result = proceedingJoinPoint.proceed();

			_clusterNodeIdExchanger.exchange((String)result);

			return result;
		}

		private static final Exchanger<String> _clusterNodeIdExchanger =
			new Exchanger<>();
		private static volatile Semaphore _semaphore;

	}

	@Aspect
	public static class SPIUtilAdvice {

		@Around(
			"execution(public static boolean com.liferay.portal.kernel." +
				"resiliency.spi.SPIUtil.isSPI())"
		)
		public boolean isSPI(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return true;
		}

	}

	private static final MethodHandler _BAD_METHOD_HANDLER = new MethodHandler(
		new MethodKey());

	private static final Address _TEST_ADDRESS = new TestAddress(-1);

	private static final String _TEST_CLUSTER_NODE_ID = "test.cluster.node.id";

	private static final MethodKey _TEST_METHOD = new MethodKey(
		TestBean.class, "testMethod1", String.class);

	private static class MockClusterExecutor extends ClusterExecutorImpl {

		public void addClusterNode(Address address, ClusterNode clusterNode) {
			if (!_enabled) {
				return;
			}

			_clusterNodes.put(address, clusterNode);
		}

		@Override
		public FutureClusterResponses execute(ClusterRequest clusterRequest) {
			if (!_enabled) {
				return null;
			}

			if (clusterRequest.getPayload() == _BAD_METHOD_HANDLER) {
				throw new RuntimeException();
			}

			return super.execute(clusterRequest);
		}

		@Override
		public List<ClusterNode> getClusterNodes() {
			if (!isEnabled()) {
				return Collections.emptyList();
			}

			return super.getClusterNodes();
		}

		public Address getCoordinatorAddress() {
			ClusterChannel clusterChannel = getClusterChannel();

			ClusterReceiver clusterReceiver =
				clusterChannel.getClusterReceiver();

			return clusterReceiver.getCoordinatorAddress();
		}

		@Override
		public ClusterNode getLocalClusterNode() {
			if (!_enabled) {
				return null;
			}

			return super.getLocalClusterNode();
		}

		public String getLocalClusterNodeId() {
			ClusterNode clusterNode = getLocalClusterNode();

			return clusterNode.getClusterNodeId();
		}

		@Override
		public boolean isClusterNodeAlive(String clusterNodeId) {
			if (Validator.isNull(clusterNodeId)) {
				throw new NullPointerException();
			}

			if (!_enabled) {
				return false;
			}

			return super.isClusterNodeAlive(clusterNodeId);
		}

		@Override
		public boolean isEnabled() {
			return _enabled;
		}

		public void removeClusterNode(Address address) {
			if (!_enabled) {
				return;
			}

			_clusterNodes.remove(address);
		}

		public void setCoordinatorAddress(Address address) throws Exception {
			ClusterChannel clusterChannel = getClusterChannel();

			ClusterReceiver clusterReceiver =
				clusterChannel.getClusterReceiver();

			Field field = ReflectionUtil.getDeclaredField(
				BaseClusterReceiver.class, "_coordinatorAddress");

			field.set(clusterReceiver, address);
		}

		@Override
		protected String getClusterNodeId(Address address) {
			ClusterNode clusterNode = _clusterNodes.get(address);

			if (clusterNode == null) {
				return null;
			}

			return clusterNode.getClusterNodeId();
		}

		private MockClusterExecutor(boolean enabled) {
			_enabled = enabled;

			setClusterChannelFactory(new TestClusterChannelFactory());

			clusterExecutorConfiguration = new ClusterExecutorConfiguration() {

				@Override
				public long clusterNodeAddressTimeout() {
					return 100;
				}

				@Override
				public boolean debugEnabled() {
					return false;
				}

				@Override
				public String[] excludedPropertyKeys() {
					return new String[] {
						"access_key", "connection_password",
						"connection_username", "secret_access_key"
					};
				}

			};

			setPortalExecutorManager(new MockPortalExecutorManager());

			setProps(PropsTestUtil.setProps(Collections.emptyMap()));

			_clusterNodes = new ConcurrentHashMap<>();

			if (enabled) {
				initialize(
					"test-channel-logic-name-mock",
					"test-channel-properties-mock", "test-channel-name-mock");

				ClusterChannel clusterChannel = getClusterChannel();

				_clusterNodes.put(
					clusterChannel.getLocalAddress(), getLocalClusterNode());
			}
		}

		private final Map<Address, ClusterNode> _clusterNodes;
		private final boolean _enabled;

	}

	private static class MockClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public boolean isMasterTokenAcquiredNotified() {
			return _masterTokenAcquiredNotified;
		}

		public boolean isMasterTokenReleasedNotified() {
			return _masterTokenReleasedNotified;
		}

		@Override
		public void masterTokenAcquired() {
			_masterTokenAcquiredNotified = true;
		}

		@Override
		public void masterTokenReleased() {
			_masterTokenReleasedNotified = true;
		}

		private boolean _masterTokenAcquiredNotified;
		private boolean _masterTokenReleasedNotified;

	}

}