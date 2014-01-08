/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewJVMJUnitTestRunner;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.jgroups.Channel;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(AspectJMockingNewJVMJUnitTestRunner.class)
public class ClusterExecutorImplTest extends BaseClusterExecutorImplTestCase {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			EnableClusterExecutorDebugAdvice.class, EnableLiveUsersAdvice.class
		}
	)
	@Test
	public void testClusterEventListener1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			List<ClusterEventListener> clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());

			// Add

			ClusterEventListener clusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(clusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(3, clusterEventListeners.size());

			// Remove

			clusterExecutorImpl.removeClusterEventListener(
				clusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());

			// Set

			clusterEventListeners = new ArrayList<ClusterEventListener>();

			clusterEventListeners.add(clusterEventListener);

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(3, clusterEventListeners.size());
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testClusterEventListener2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			Field field = ReflectionUtil.getDeclaredField(
				ClusterExecutorImpl.class, "_clusterEventListeners");

			List<ClusterEventListener> fieldClusterEventListeners =
				(List<ClusterEventListener>)field.get(clusterExecutorImpl);

			ClusterEventListener clusterEventListener =
				new MockClusterEventListener();

			fieldClusterEventListeners.add(clusterEventListener);

			Assert.assertEquals(1, fieldClusterEventListeners.size());

			// Add

			clusterExecutorImpl.addClusterEventListener(
				new MockClusterEventListener());

			Assert.assertEquals(1, fieldClusterEventListeners.size());

			// Remove

			clusterExecutorImpl.removeClusterEventListener(
				clusterEventListener);

			Assert.assertEquals(1, fieldClusterEventListeners.size());

			// Get

			List<ClusterEventListener> clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertTrue(clusterEventListeners.isEmpty());

			// Set

			clusterEventListeners = new ArrayList<ClusterEventListener>();

			clusterEventListeners.add(new MockClusterEventListener());

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			Assert.assertEquals(1, fieldClusterEventListeners.size());
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testClusterTopology() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);

			MockClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl1.addClusterEventListener(
				mockClusterEventListener);

			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);

			// Disconnected network

			updateView(clusterExecutorImpl1);

			clusterEvent = mockClusterEventListener.waitDepartMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.DEPART, clusterNode2);

			// Reconnected network

			updateView(clusterExecutorImpl1, clusterExecutorImpl2);

			clusterEvent = mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);
		}
		finally {
			if (clusterExecutorImpl1 != null) {
				clusterExecutorImpl1.destroy();
			}

			if (clusterExecutorImpl2 != null) {
				clusterExecutorImpl2.destroy();
			}
		}
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class, InetAddressUtilExceptionAdvice.class,
			JChannelExceptionAdvice.class
		}

	)
	@Test
	public void testErrorLogAndExceptions() throws Exception {
		JDKLoggerTestUtil.configureJDKLogger(
			ClusterBase.class.getName(), Level.FINE);

		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			PortalUtil portalUtil = new PortalUtil();

			portalUtil.setPortal(new PortalImpl());

			PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

			portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

			PropsUtil.setProps(new PropsImpl());

			PortalExecutorManagerUtil portalExecutorManagerUtil =
				new PortalExecutorManagerUtil();

			portalExecutorManagerUtil.setPortalExecutorManager(
				new ClusterExecutorImplTest.MockPortalExecutorManager());

			List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
				ClusterExecutorImpl.class.getName(), Level.SEVERE);

			clusterExecutorImpl = new ClusterExecutorImpl();

			clusterExecutorImpl.initChannels();
			clusterExecutorImpl.initSystemProperties();

			clusterExecutorImpl.initialize();

			assertLogger(
				logRecords, "Unable to determine local network address",
				Exception.class);

			clusterExecutorImpl.initBindAddress();

			clusterExecutorImpl.initialize();

			assertLogger(
				logRecords, "Unable to send notify message", Exception.class);

			clusterExecutorImpl.portalPortConfigured(80);

			assertLogger(
				logRecords, "Unable to determine configure node port",
				Exception.class);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(null);

			try {
				clusterExecutorImpl.execute(clusterRequest);

				Assert.fail();
			}
			catch (Exception e) {
				Assert.assertEquals(
					"Unable to send multicast request", e.getMessage());
			}

			clusterRequest = ClusterRequest.createUnicastRequest(
				null, new AddressImpl(new MockAddress()));

			try {
				clusterExecutorImpl.execute(clusterRequest);

				Assert.fail();
			}
			catch (Exception e) {
				Assert.assertEquals(
					"Unable to send unicast request", e.getMessage());
			}
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteByFireAndForget() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;
		String timestamp = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			// fireAndForget is false

			timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(methodHandler);

			clusterRequest.setFireAndForget(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, clusterExecutorImpl1.getClusterNodeAddresses());

			// fireAndForget is true

			timestamp = String.valueOf(System.currentTimeMillis());

			methodHandler = new MethodHandler(testMethod1MethodKey, timestamp);

			clusterRequest = ClusterRequest.createMulticastRequest(
				methodHandler);

			clusterRequest.setFireAndForget(true);

			futureClusterResponses = clusterExecutorImpl1.execute(
				clusterRequest);

			futureClusterResponses.get(1000, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
			Assert.assertEquals(TestBean.TIMESTAMP, timestamp);
		}
		finally {
			if (clusterExecutorImpl1 != null) {
				clusterExecutorImpl1.destroy();
			}

			if (clusterExecutorImpl2 != null) {
				clusterExecutorImpl2.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteByLocalMethod1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, StringPool.BLANK);

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, clusterNode.getClusterNodeId());

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				address);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteByLocalMethod2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			MethodHandler methodHandler = new MethodHandler(
				testMethod2MethodKey);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				"Return value is not serializable");
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteByLocalMethod3() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod3MethodKey, timestamp);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				timestamp);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteByLocalMethod4() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				null, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				"Payload is not of type " + MethodHandler.class.getName());
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteByShortcutMethod() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(true, false);

			Channel channel = clusterExecutorImpl.getControlChannel();

			MockClusterRequestReceiver mockClusterRequestReceiver =
				(MockClusterRequestReceiver)channel.getReceiver();

			ClusterRequest localClusterRequest =
				mockClusterRequestReceiver.waitLocalRequestMessage();

			Assert.assertEquals(
				ClusterMessageType.NOTIFY,
				localClusterRequest.getClusterMessageType());

			// shortcutLocalMethod is false

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterExecutorImpl.setShortcutLocalMethod(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			localClusterRequest =
				mockClusterRequestReceiver.waitLocalRequestMessage();

			Assert.assertEquals(
				ClusterMessageType.EXECUTE,
				localClusterRequest.getClusterMessageType());

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, address);

			// shortcutLocalMethod is true

			timestamp = String.valueOf(System.currentTimeMillis());

			methodHandler = new MethodHandler(testMethod1MethodKey, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterExecutorImpl.setShortcutLocalMethod(true);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			localClusterRequest =
				mockClusterRequestReceiver.waitLocalRequestMessage();

			Assert.assertNull(localClusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, address);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteBySkipLocal() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			// skipLocal is false

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterRequest.setSkipLocal(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, address);

			// skipLocal is true

			timestamp = String.valueOf(System.currentTimeMillis());

			methodHandler = new MethodHandler(testMethod1MethodKey, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterRequest.setSkipLocal(true);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			Assert.assertEquals(0, futureClusterResponses.get().size());
			Assert.assertFalse(TestBean.TIMESTAMP.equals(timestamp));
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testExecuteWhenDisableCluster() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(null);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			Assert.assertNull(futureClusterResponses);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteWithCallBack1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			MockClusterResponseCallback mockClusterResponseCallback =
				new MockClusterResponseCallback();

			clusterExecutorImpl.execute(
				clusterRequest, mockClusterResponseCallback);

			ClusterNodeResponses clusterNodeResponses =
				mockClusterResponseCallback.waitMessage();

			assertFutureClusterResponsesWithoutException(
				clusterNodeResponses, clusterRequest.getUuid(), timestamp,
				address);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testExecuteWithCallBack2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			// TimeoutException

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				null, new AddressImpl(new MockAddress()));

			MockClusterResponseCallback mockClusterResponseCallback =
				new MockClusterResponseCallback();

			clusterExecutorImpl.execute(
				clusterRequest, mockClusterResponseCallback, 1000,
				TimeUnit.MILLISECONDS);

			TimeoutException timeoutException =
				mockClusterResponseCallback.waitTimeoutException();

			Assert.assertNotNull(timeoutException);

			// InterruptedException

			clusterExecutorImpl.execute(
				clusterRequest, mockClusterResponseCallback);

			Field field = ReflectionUtil.getDeclaredField(
				ClusterExecutorImpl.class, "_executorService");

			ExecutorService executorService = (ExecutorService)field.get(
				clusterExecutorImpl);

			executorService.shutdownNow();

			InterruptedException interruptedException =
				mockClusterResponseCallback.waitInterruptedException();

			Assert.assertNotNull(interruptedException);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testGetMethods1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			List<Address> addresses =
				clusterExecutorImpl.getClusterNodeAddresses();

			Assert.assertTrue(addresses.isEmpty());

			List<ClusterNode> clusterNodes =
				clusterExecutorImpl.getClusterNodes();

			Assert.assertTrue(clusterNodes.isEmpty());

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			Assert.assertNull(clusterNode);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			Assert.assertNull(address);

			boolean clusterNodeAlive = clusterExecutorImpl.isClusterNodeAlive(
				new AddressImpl(new MockAddress()));

			Assert.assertFalse(clusterNodeAlive);

			clusterNodeAlive = clusterExecutorImpl.isClusterNodeAlive(
				"WrongClusterNodeId");

			Assert.assertFalse(clusterNodeAlive);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testGetMethods2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);

			MockClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl1.addClusterEventListener(
				mockClusterEventListener);

			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			ClusterNode clusterNode1 =
				clusterExecutorImpl1.getLocalClusterNode();

			Assert.assertNotNull(clusterNode1);

			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();

			Assert.assertNotNull(clusterNode2);

			Address address1 =
				clusterExecutorImpl1.getLocalClusterNodeAddress();

			Assert.assertNotNull(address1);

			Address address2 =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			Assert.assertNotNull(address2);

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);

			List<Address> addresses =
				clusterExecutorImpl1.getClusterNodeAddresses();

			Assert.assertEquals(2, addresses.size());
			Assert.assertTrue(addresses.contains(address1));
			Assert.assertTrue(addresses.contains(address2));

			List<ClusterNode> clusterNodes =
				clusterExecutorImpl1.getClusterNodes();

			Assert.assertEquals(2, clusterNodes.size());
			Assert.assertTrue(clusterNodes.contains(clusterNode1));
			Assert.assertTrue(clusterNodes.contains(clusterNode2));

			boolean clusterNodeAlive = clusterExecutorImpl1.isClusterNodeAlive(
				clusterNode2.getClusterNodeId());

			Assert.assertTrue(
				clusterExecutorImpl1.isClusterNodeAlive(
					clusterNode2.getClusterNodeId()));

			clusterNodeAlive = clusterExecutorImpl1.isClusterNodeAlive(
				address2);

			Assert.assertTrue(clusterNodeAlive);
		}
		finally {
			if (clusterExecutorImpl1 != null) {
				clusterExecutorImpl1.destroy();
			}

			if (clusterExecutorImpl2 != null) {
				clusterExecutorImpl2.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testMemberRemoved() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			MockClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(
				mockClusterEventListener);

			List<Address> addresses = new ArrayList<Address>();

			addresses.add(new AddressImpl(new MockAddress()));

			clusterExecutorImpl.memberRemoved(addresses);

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitDepartMessage();

			Assert.assertNull(clusterEvent);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testPortalPortConfigured1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);

			MockClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl1.addClusterEventListener(
				mockClusterEventListener);

			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);

			updateView(clusterExecutorImpl1);

			clusterEvent = mockClusterEventListener.waitDepartMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.DEPART, clusterNode2);

			Assert.assertEquals(-1, clusterNode2.getPort());

			clusterExecutorImpl2.portalPortConfigured(80);

			Assert.assertEquals(80, clusterNode2.getPort());

			clusterEvent = mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);
		}
		finally {
			if (clusterExecutorImpl1 != null) {
				clusterExecutorImpl1.destroy();
			}

			if (clusterExecutorImpl2 != null) {
				clusterExecutorImpl2.destroy();
			}
		}
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class, SetPortalPortAdvice.class
		}

	)
	@Test
	public void testPortalPortConfigured2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			Assert.assertEquals(
				SetPortalPortAdvice.PORTAL_PORT, clusterNode.getPort());

			clusterExecutorImpl.portalPortConfigured(81);

			Assert.assertEquals(
				SetPortalPortAdvice.PORTAL_PORT, clusterNode.getPort());
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class, SetPortalPortAdvice.class,
			SetWebServerProtocolAdvice.class
		}

	)
	@Test
	public void testPortalPortConfigured3() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			Assert.assertEquals(
				SetPortalPortAdvice.SECURE_PORTAL_PORT, clusterNode.getPort());

			clusterExecutorImpl.portalPortConfigured(80);

			Assert.assertEquals(
				SetPortalPortAdvice.SECURE_PORTAL_PORT, clusterNode.getPort());
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testPortalPortConfigured4() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			clusterExecutorImpl = getClusterExecutorImpl(false, false);

			clusterExecutorImpl.portalPortConfigured(80);
		}
		finally {
			if (clusterExecutorImpl != null) {
				clusterExecutorImpl.destroy();
			}
		}
	}

}