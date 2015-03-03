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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.lang.reflect.InvocationTargetException;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.jgroups.Channel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.JVM)
public class ClusterExecutorImplTest extends BaseClusterExecutorImplTestCase {

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			EnableClusterExecutorDebugAdvice.class
		}
	)
	@Test
	public void testClusterEventListener() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			List<ClusterEventListener> clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(1, clusterEventListeners.size());

			// Test 1, add cluster event listener

			ClusterEventListener clusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(clusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());

			// Test 2, remove cluster event listener

			clusterExecutorImpl.removeClusterEventListener(
				clusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(1, clusterEventListeners.size());

			// Test 3, set cluster event listener

			clusterEventListeners = new ArrayList<>();

			clusterEventListeners.add(clusterEventListener);

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testClusterTopology() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();

		MockClusterEventListener mockClusterEventListener =
			new MockClusterEventListener();

		clusterExecutorImpl1.addClusterEventListener(mockClusterEventListener);

		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();

		try {
			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);

			// Test 1, disconnect network

			updateView(clusterExecutorImpl1);

			clusterEvent = mockClusterEventListener.waitDepartMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.DEPART, clusterNode2);

			// Test 2, reconnect network

			updateView(clusterExecutorImpl1, clusterExecutorImpl2);

			clusterEvent = mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);
		}
		finally {
			clusterExecutorImpl1.destroy();
			clusterExecutorImpl2.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class, JChannelExceptionAdvice.class,
			SetBadPortalInetSocketAddressAdvice.class
		}
	)
	@Test
	public void testErrorLogAndExceptions() {
		SetBadPortalInetSocketAddressAdvice.setPort(8080);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		PropsUtil.setProps(new PropsImpl());

		PortalExecutorManagerUtil portalExecutorManagerUtil =
			new PortalExecutorManagerUtil();

		portalExecutorManagerUtil.setPortalExecutorManager(
			new MockPortalExecutorManager());

		ClusterExecutorImpl clusterExecutorImpl = new ClusterExecutorImpl();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterExecutorImpl.class.getName(), Level.SEVERE)) {

			// Test 1, connect channel with log enabled

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			clusterExecutorImpl.afterPropertiesSet();

			JChannelExceptionAdvice.setConnectException(new Exception());

			try {
				clusterExecutorImpl.initialize();

				Assert.fail();
			}
			catch (IllegalStateException ise) {
				assertLogger(
					logRecords, "Unable to initialize", Exception.class);
			}

			// Test 2, connect channel with log disabled

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			clusterExecutorImpl = new ClusterExecutorImpl();

			clusterExecutorImpl.afterPropertiesSet();

			JChannelExceptionAdvice.setConnectException(new Exception());

			try {
				clusterExecutorImpl.initialize();

				Assert.fail();
			}
			catch (IllegalStateException ise) {
				Assert.assertTrue(logRecords.isEmpty());
			}

			// Test 3, send notify message

			JChannelExceptionAdvice.setConnectException(null);

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			clusterExecutorImpl.initialize();

			assertLogger(
				logRecords, "Unable to send notify message", Exception.class);

			// Test 4, execute multicast request

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

			// Test 5, execute unicast request

			String clusterNodeId = PortalUUIDUtil.generate();

			clusterRequest = ClusterRequest.createUnicastRequest(
				null, clusterNodeId);

			try {
				clusterExecutorImpl.memberJoined(
					new AddressImpl(new MockAddress()),
					new ClusterNode(clusterNodeId, InetAddress.getLocalHost()));

				clusterExecutorImpl.execute(clusterRequest);

				Assert.fail();
			}
			catch (Exception e) {
				Assert.assertEquals(
					"Unable to send unicast request", e.getMessage());
			}
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testExecuteByFireAndForget() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();

		MockClusterEventListener mockClusterEventListener =
			new MockClusterEventListener();

		clusterExecutorImpl1.addClusterEventListener(mockClusterEventListener);

		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();

		assertClusterEvent(
			mockClusterEventListener.waitJoinMessage(), ClusterEventType.JOIN,
			clusterExecutorImpl2.getLocalClusterNode());

		String timestamp = null;

		try {

			// Test 1, execute with fire and forget disabled

			timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(methodHandler);

			clusterRequest.setFireAndForget(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			List<String> clusterNodeIds = new ArrayList<>();

			for (ClusterNode clusterNode :
					clusterExecutorImpl1.getClusterNodes()) {

				clusterNodeIds.add(clusterNode.getClusterNodeId());
			}

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, clusterNodeIds);

			// Test 2, execute with fire and forget enabled

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
			clusterExecutorImpl1.destroy();
			clusterExecutorImpl2.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testExecuteByLocalMethod() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, execute when return value is null

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			String clusterNodeId = clusterNode.getClusterNodeId();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod1MethodKey, StringPool.BLANK),
				clusterNodeId);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				Collections.singletonList(clusterNodeId));

			// Test 2, execute when return value is not serializable

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod2MethodKey), clusterNodeId);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), clusterNodeId,
				"Return value is not serializable");

			// Test 3, execute when exception is thrown

			String timestamp = String.valueOf(System.currentTimeMillis());

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod3MethodKey, timestamp),
				clusterNodeId);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), clusterNodeId,
				timestamp);

			// Test 4, execute when method handler is null

			clusterRequest = ClusterRequest.createUnicastRequest(
				null, clusterNodeId);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), clusterNodeId,
				"Payload is not of type " + MethodHandler.class.getName());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			BaseReceiverAdvice.class, DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testExecuteByShortcutMethod() throws Exception {
		BaseReceiverAdvice.reset(1);

		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, send notify message

			Channel channel = clusterExecutorImpl.getControlChannel();

			Object object = BaseReceiverAdvice.getJGroupsMessagePayload(
				channel.getReceiver(), channel.getAddress());

			ClusterRequest clusterRequest = (ClusterRequest)object;

			Assert.assertEquals(
				ClusterMessageType.NOTIFY,
				clusterRequest.getClusterMessageType());

			// Test 2, execute

			String timestamp = String.valueOf(System.currentTimeMillis());

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			String clusterNodeId = clusterNode.getClusterNodeId();

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod1MethodKey, timestamp),
				clusterNodeId);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, Collections.singletonList(clusterNodeId));
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testExecuteBySkipLocal() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, execute with skip local disabled

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			String clusterNodeId = clusterNode.getClusterNodeId();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, clusterNodeId);

			clusterRequest.setSkipLocal(false);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, Collections.singletonList(clusterNodeId));

			// Test 2, execute with skip local enabled

			timestamp = String.valueOf(System.currentTimeMillis());

			methodHandler = new MethodHandler(testMethod1MethodKey, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, clusterNodeId);

			clusterRequest.setSkipLocal(true);

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get();

			Assert.assertEquals(0, clusterNodeResponses.size());
			Assert.assertNotEquals(TestBean.TIMESTAMP, timestamp);
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testExecuteClusterRequest() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, method handler is null

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(null);

			ClusterNodeResponse clusterNodeResponse =
				clusterExecutorImpl.executeClusterRequest(clusterRequest);

			Exception exception = clusterNodeResponse.getException();

			Assert.assertEquals(
				"Payload is not of type " + MethodHandler.class.getName(),
				exception.getMessage());

			// Test 2, invoke with exception

			String timeStamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod3MethodKey, timeStamp);

			clusterRequest = ClusterRequest.createMulticastRequest(
				methodHandler);

			clusterNodeResponse = clusterExecutorImpl.executeClusterRequest(
				clusterRequest);

			try {
				clusterNodeResponse.getResult();

				Assert.fail();
			}
			catch (Exception e) {
				InvocationTargetException invocationTargetException =
					(InvocationTargetException)e;

				Throwable throwable =
					invocationTargetException.getTargetException();

				Assert.assertEquals(timeStamp, throwable.getMessage());
			}

			// Test 3, invoke without exception

			timeStamp = String.valueOf(System.currentTimeMillis());

			methodHandler = new MethodHandler(testMethod1MethodKey, timeStamp);

			clusterRequest = ClusterRequest.createMulticastRequest(
				methodHandler);

			clusterNodeResponse = clusterExecutorImpl.executeClusterRequest(
				clusterRequest);

			Assert.assertEquals(
				timeStamp, (String)clusterNodeResponse.getResult());

			// Test 4, test threadlocal

			MethodKey testMethod5MethodKey = new MethodKey(
				TestBean.class, "testMethod5");

			methodHandler = new MethodHandler(testMethod5MethodKey);

			clusterRequest = ClusterRequest.createMulticastRequest(
				methodHandler);

			Assert.assertTrue(ClusterInvokeThreadLocal.isEnabled());

			clusterNodeResponse = clusterExecutorImpl.executeClusterRequest(
				clusterRequest);

			Assert.assertFalse((Boolean)clusterNodeResponse.getResult());
			Assert.assertTrue(ClusterInvokeThreadLocal.isEnabled());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testGetMethods() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();

		MockClusterEventListener mockClusterEventListener =
			new MockClusterEventListener();

		clusterExecutorImpl1.addClusterEventListener(mockClusterEventListener);

		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();

		ClusterNode clusterNode = clusterExecutorImpl2.getLocalClusterNode();

		assertClusterEvent(
			mockClusterEventListener.waitJoinMessage(), ClusterEventType.JOIN,
			clusterNode);

		try {

			// Test 1, get local cluster node

			ClusterNode clusterNode1 =
				clusterExecutorImpl1.getLocalClusterNode();

			Assert.assertNotNull(clusterNode1);

			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();

			Assert.assertNotNull(clusterNode2);

			// Test 2, get all cluster nodes

			List<ClusterNode> clusterNodes =
				clusterExecutorImpl1.getClusterNodes();

			Assert.assertEquals(2, clusterNodes.size());
			Assert.assertTrue(clusterNodes.contains(clusterNode1));
			Assert.assertTrue(clusterNodes.contains(clusterNode2));

			// Test 3, if cluster node is alive using cluster node ID

			Assert.assertTrue(
				clusterExecutorImpl1.isClusterNodeAlive(
					clusterNode2.getClusterNodeId()));
		}
		finally {
			clusterExecutorImpl1.destroy();
			clusterExecutorImpl2.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		}
	)
	@Test
	public void testMemberRemoved() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			MockClusterEventListener mockClusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(
				mockClusterEventListener);

			List<Address> addresses = new ArrayList<>();

			addresses.add(new AddressImpl(new MockAddress()));

			clusterExecutorImpl.memberRemoved(addresses);

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitDepartMessage();

			Assert.assertNull(clusterEvent);
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testWithClusterDisabled() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, add cluster event listener

			List<ClusterEventListener> fieldClusterEventListeners =
				ReflectionTestUtil.getFieldValue(
					clusterExecutorImpl, "_clusterEventListeners");

			ClusterEventListener clusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(clusterEventListener);

			Assert.assertTrue(fieldClusterEventListeners.isEmpty());

			// Test 2, remove cluster event listener

			clusterExecutorImpl.removeClusterEventListener(
				clusterEventListener);

			Assert.assertTrue(fieldClusterEventListeners.isEmpty());

			// Test 3, get cluster event listener

			List<ClusterEventListener> clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertTrue(clusterEventListeners.isEmpty());

			// Test 4, set cluster event listener

			clusterEventListeners = new ArrayList<>();

			clusterEventListeners.add(new MockClusterEventListener());

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			Assert.assertTrue(fieldClusterEventListeners.isEmpty());

			// Test 5, get cluster node

			List<ClusterNode> clusterNodes =
				clusterExecutorImpl.getClusterNodes();

			Assert.assertTrue(clusterNodes.isEmpty());

			// Test 6, get local cluster node

			Assert.assertNull(clusterExecutorImpl.getLocalClusterNode());

			// Test 7, if cluster node is alive using cluster node ID

			Assert.assertFalse(
				clusterExecutorImpl.isClusterNodeAlive("WrongClusterNodeId"));

			// Test 8, execute cluster request

			Assert.assertNull(
				clusterExecutorImpl.execute(
					ClusterRequest.createMulticastRequest(null)));
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		AspectJNewEnvTestRule.INSTANCE;

}