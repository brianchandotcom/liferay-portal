/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.ReflectionUtil;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(AspectJMockingNewJVMJUnitTestRunner.class)
public class ClusterExecutorImplTest extends BaseClusterExecutorImplTest {

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

			//Test add
			ClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(
				mockClusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(3, clusterEventListeners.size());

			//Test remove
			clusterExecutorImpl.removeClusterEventListener(
				mockClusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());

			//Test set
			clusterEventListeners = new ArrayList<ClusterEventListener>();

			clusterEventListeners.add(mockClusterEventListener);

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

			CopyOnWriteArrayList<ClusterEventListener> listeners =
				(CopyOnWriteArrayList<ClusterEventListener>)
					field.get(clusterExecutorImpl);

			MockClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			listeners.add(mockClusterEventListener);

			Assert.assertEquals(1, listeners.size());

			//Test add
			clusterExecutorImpl.addClusterEventListener(
				new MockClusterEventListener());

			Assert.assertEquals(1, listeners.size());

			//Test remove
			clusterExecutorImpl.removeClusterEventListener(
				mockClusterEventListener);

			Assert.assertEquals(1, listeners.size());

			//Test get
			List<ClusterEventListener> clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertTrue(clusterEventListeners.isEmpty());

			//Test set
			clusterEventListeners = new ArrayList<ClusterEventListener>();

			clusterEventListeners.add(new MockClusterEventListener());

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			Assert.assertEquals(1, listeners.size());
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

			//Disconnected network
			updateView(clusterExecutorImpl1);

			clusterEvent = mockClusterEventListener.waitDepartMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.DEPART, clusterNode2);

			//Reconnected network
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
	public void testErrorlogAndExceptions() throws Exception {
		JDKLoggerTestUtil.configureJDKLogger(
			ClusterBase.class.getName(), Level.FINE);

		ClusterExecutorImpl clusterExecutorImpl = null;

		try {
			com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

			PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

			portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

			PortalUtil portalUtil = new PortalUtil();

			portalUtil.setPortal(new PortalImpl());

			PortalExecutorManagerUtil portalExecutorManagerUtil =
				new PortalExecutorManagerUtil();

			portalExecutorManagerUtil.setPortalExecutorManager(
				new ClusterExecutorImplTest.MockPortalExecutorManager());

			List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
				ClusterExecutorImpl.class.getName(), Level.SEVERE);

			clusterExecutorImpl = new ClusterExecutorImpl();

			clusterExecutorImpl.initSystemProperties();
			clusterExecutorImpl.initChannels();

			//Initialization without bind address, failed.
			clusterExecutorImpl.initialize();

			assertLogger(
				logRecords, "Unable to determine local network address",
				Exception.class);

			//Initialization with bind address, successed.
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

			//Test when fireAndForget flag is false

			timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, timestamp);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(methodHandler);

			clusterRequest.setFireAndForget(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, clusterExecutorImpl1.getClusterNodeAddresses());

			//Test when fireAndForget flag is true

			timestamp = Long.toString(System.currentTimeMillis());

			methodHandler = new MethodHandler(_TEST_METHOD_KEY_1, timestamp);

			clusterRequest = ClusterRequest.createMulticastRequest(
				methodHandler);

			clusterRequest.setFireAndForget(true);

			futureClusterResponses = clusterExecutorImpl1.execute(
				clusterRequest);

			futureClusterResponses.get(1000, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (TimeoutException e) {
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

			String destClusterId =
				clusterExecutorImpl.getLocalClusterNode().getClusterNodeId();
			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, "");

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destClusterId);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				destAddress);
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

			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(_TEST_METHOD_KEY_2);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
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

			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			String timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_3, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
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

			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				null, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
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

			MockClusterRequestReceiver mockClusterRequestReceiver =
				(MockClusterRequestReceiver)
					clusterExecutorImpl.getControlChannel().getReceiver();

			ClusterRequest localClusterRequest =
				mockClusterRequestReceiver.waitLocalRequestMessage();

			Assert.assertEquals(
				ClusterMessageType.NOTIFY,
				localClusterRequest.getClusterMessageType());

			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			//Test when shortcutLocalMethod is false

			String timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

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
				timestamp, destAddress);

			//Test when shortcutLocalMethod is true

			timestamp = Long.toString(System.currentTimeMillis());

			methodHandler = new MethodHandler(_TEST_METHOD_KEY_1, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			clusterExecutorImpl.setShortcutLocalMethod(true);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			localClusterRequest =
				mockClusterRequestReceiver.waitLocalRequestMessage();

			Assert.assertNull(localClusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, destAddress);
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

			//Test when skipLocal is false

			String timestamp = Long.toString(System.currentTimeMillis());

			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			clusterRequest.setSkipLocal(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, destAddress);

			//Test when skipLocal is true

			timestamp = Long.toString(System.currentTimeMillis());

			methodHandler = new MethodHandler(_TEST_METHOD_KEY_1, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

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

			Address destAddress =
				clusterExecutorImpl.getLocalClusterNodeAddress();

			String timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			MockClusterResponseCallback mockClusterResponseCallback =
				new MockClusterResponseCallback();

			clusterExecutorImpl.execute(
				clusterRequest, mockClusterResponseCallback);

			ClusterNodeResponses clusterNodeResponses =
				mockClusterResponseCallback.waitMessage();

			assertFutureClusterResponsesWithoutException(
				clusterNodeResponses, clusterRequest.getUuid(), timestamp,
				destAddress);
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

			//Test when TimeoutException comes up

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

			//Test when InterruptedException comes up

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
			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();
			Address address1 =
				clusterExecutorImpl1.getLocalClusterNodeAddress();
			Address address2 =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			Assert.assertNotNull(clusterNode1);
			Assert.assertNotNull(clusterNode2);
			Assert.assertNotNull(address1);
			Assert.assertNotNull(address2);

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);

			//Test getClusterNodeAddresses
			List<Address> addresses =
				clusterExecutorImpl1.getClusterNodeAddresses();

			Assert.assertEquals(2, addresses.size());
			Assert.assertTrue(addresses.contains(address1));
			Assert.assertTrue(addresses.contains(address2));

			//Test getClusterNodes
			List<ClusterNode> clusterNodes =
				clusterExecutorImpl1.getClusterNodes();

			Assert.assertEquals(2, clusterNodes.size());
			Assert.assertTrue(clusterNodes.contains(clusterNode1));
			Assert.assertTrue(clusterNodes.contains(clusterNode2));

			//Test isClusterNodeAlive1
			boolean clusterNodeAlive = clusterExecutorImpl1.isClusterNodeAlive(
				clusterNode2.getClusterNodeId());

			Assert.assertTrue(clusterNodeAlive);

			//Test isClusterNodeAlive2
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

			int newPort = 80;

			clusterExecutorImpl2.portalPortConfigured(newPort);

			Assert.assertEquals(newPort, clusterNode2.getPort());

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

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testPortalPortConfigured3() throws Exception {
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