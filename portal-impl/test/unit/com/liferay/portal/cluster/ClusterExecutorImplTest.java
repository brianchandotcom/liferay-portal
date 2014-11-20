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
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.jgroups.Channel;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.JVM)
public class ClusterExecutorImplTest extends BaseClusterExecutorImplTestCase {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			EnableClusterExecutorDebugAdvice.class, EnableLiveUsersAdvice.class
		})
	@Test
	public void testClusterEventListener() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			List<ClusterEventListener> clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());

			// Test 1, add cluster event listener

			ClusterEventListener clusterEventListener =
				new MockClusterEventListener();

			clusterExecutorImpl.addClusterEventListener(clusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(3, clusterEventListeners.size());

			// Test 2, remove cluster event listener

			clusterExecutorImpl.removeClusterEventListener(
				clusterEventListener);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(2, clusterEventListeners.size());

			// Test 3, set cluster event listener

			clusterEventListeners = new ArrayList<ClusterEventListener>();

			clusterEventListeners.add(clusterEventListener);

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			clusterEventListeners =
				clusterExecutorImpl.getClusterEventListeners();

			Assert.assertEquals(3, clusterEventListeners.size());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		})
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
		})
	@Test
	public void testErrorLogAndExceptions() throws UnknownHostException {
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

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterExecutorImpl.class.getName(), Level.SEVERE);

		ClusterExecutorImpl clusterExecutorImpl = new ClusterExecutorImpl();

		try {

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

			LogRecord logRecord1 = logRecords.get(0);
			LogRecord logRecord2 = logRecords.get(1);

			Assert.assertEquals(2, logRecords.size());
			Assert.assertEquals(
				"Unable to parse portal InetSocketAddress from bad " +
					"address:8080",
				logRecord1.getMessage());
			Assert.assertEquals(
				"Unable to send notify message", logRecord2.getMessage());

			// Test 4, configure internet socket address

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			clusterExecutorImpl.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(InetAddress.getLocalHost(), 80));

			assertLogger(
				logRecords, "Unable to determine configure node port",
				Exception.class);

			// Test 5, execute multicast request

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

			// Test 6, execute unicast request

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
			captureHandler.close();

			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		})
	@Test
	public void testExecuteByFireAndForget() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();
		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();
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

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, clusterExecutorImpl1.getClusterNodeAddresses());

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
		})
	@Test
	public void testExecuteByLocalMethod() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, execute when return value is null

			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod1MethodKey, StringPool.BLANK),
				clusterNode.getClusterNodeId());

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				clusterExecutorImpl.getLocalClusterNodeAddress());

			// Test 2, execute when return value is not serializable

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod2MethodKey),
				clusterExecutorImpl.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(),
				clusterExecutorImpl.getLocalClusterNodeAddress(),
				"Return value is not serializable");

			// Test 3, execute when exception is thrown

			String timestamp = String.valueOf(System.currentTimeMillis());

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod3MethodKey, timestamp),
				clusterExecutorImpl.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(),
				clusterExecutorImpl.getLocalClusterNodeAddress(), timestamp);

			// Test 4, execute when method handler is null

			clusterRequest = ClusterRequest.createUnicastRequest(
				null, clusterExecutorImpl.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(),
				clusterExecutorImpl.getLocalClusterNodeAddress(),
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
		})
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

			// Test 2, execute with shortcut local method disabled

			BaseReceiverAdvice.reset(1);

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler,
				clusterExecutorImpl.getLocalClusterNodeAddress());

			clusterExecutorImpl.setShortcutLocalMethod(false);

			clusterExecutorImpl.execute(clusterRequest);

			object = BaseReceiverAdvice.getJGroupsMessagePayload(
				channel.getReceiver(), channel.getAddress());

			clusterRequest = (ClusterRequest)object;
			MethodHandler newMethodHandler = clusterRequest.getMethodHandler();

			Assert.assertEquals(
				ClusterMessageType.EXECUTE,
				clusterRequest.getClusterMessageType());
			Assert.assertEquals(
				methodHandler.toString(), newMethodHandler.toString());

			// Test 3, execute with shortcut local method enabled

			timestamp = String.valueOf(System.currentTimeMillis());

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod1MethodKey, timestamp),
				clusterExecutorImpl.getLocalClusterNodeAddress());

			clusterExecutorImpl.setShortcutLocalMethod(true);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, clusterExecutorImpl.getLocalClusterNodeAddress());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		})
	@Test
	public void testExecuteBySkipLocal() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {

			// Test 1, execute with skip local disabled

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

			// Test 2, execute with skip local enabled

			timestamp = String.valueOf(System.currentTimeMillis());

			methodHandler = new MethodHandler(testMethod1MethodKey, timestamp);

			clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

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
		})
	@Test
	public void testExecuteWithCallBack() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			Address address = clusterExecutorImpl.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			MockClusterResponseCallback mockClusterResponseCallback =
				new MockClusterResponseCallback();

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl.execute(
					clusterRequest, mockClusterResponseCallback);

			BlockingQueue<ClusterNodeResponse> blockingQueue =
				mockClusterResponseCallback.waitMessage();

			Assert.assertSame(
				futureClusterResponses.getPartialResults(), blockingQueue);
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		})
	@Test
	public void testGetMethods() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();

		MockClusterEventListener mockClusterEventListener =
			new MockClusterEventListener();

		clusterExecutorImpl1.addClusterEventListener(mockClusterEventListener);

		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();

		try {

			// Test 1, get local cluster node

			ClusterNode clusterNode1 =
				clusterExecutorImpl1.getLocalClusterNode();

			Assert.assertNotNull(clusterNode1);

			ClusterNode clusterNode2 =
				clusterExecutorImpl2.getLocalClusterNode();

			Assert.assertNotNull(clusterNode2);

			// Test 2, get address of local cluster node

			Address address1 =
				clusterExecutorImpl1.getLocalClusterNodeAddress();

			Assert.assertNotNull(address1);

			Address address2 =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			Assert.assertNotNull(address2);

			// Test 3, get addresses of all cluster nodes

			ClusterEvent clusterEvent =
				mockClusterEventListener.waitJoinMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.JOIN, clusterNode2);

			List<Address> addresses =
				clusterExecutorImpl1.getClusterNodeAddresses();

			Assert.assertEquals(2, addresses.size());
			Assert.assertTrue(addresses.contains(address1));
			Assert.assertTrue(addresses.contains(address2));

			// Test 4, get all cluster nodes

			List<ClusterNode> clusterNodes =
				clusterExecutorImpl1.getClusterNodes();

			Assert.assertEquals(2, clusterNodes.size());
			Assert.assertTrue(clusterNodes.contains(clusterNode1));
			Assert.assertTrue(clusterNodes.contains(clusterNode2));

			// Test 5, if cluster node is alive using cluster node ID

			Assert.assertTrue(
				clusterExecutorImpl1.isClusterNodeAlive(
					clusterNode2.getClusterNodeId()));

			// Test 6, if cluster node is alive using address

			Assert.assertTrue(
				clusterExecutorImpl1.isClusterNodeAlive(address2));
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
		})
	@Test
	public void testMemberRemoved() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
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
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		})
	@Test
	public void testPortalConfigured1() throws Exception {
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

			updateView(clusterExecutorImpl1);

			clusterEvent = mockClusterEventListener.waitDepartMessage();

			assertClusterEvent(
				clusterEvent, ClusterEventType.DEPART, clusterNode2);

			Assert.assertNull(clusterNode2.getPortalInetSocketAddress());

			InetAddress inetAddress = InetAddress.getLocalHost();
			int port = 8080;

			clusterExecutorImpl2.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(inetAddress, port));

			Assert.assertEquals(
				new InetSocketAddress(inetAddress, port),
				clusterNode2.getPortalInetSocketAddress());

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
			EnableClusterLinkAdvice.class,
			SetPortalInetSocketAddressAdvice.class
		})
	@Test
	public void testPortalConfigured2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			Assert.assertEquals(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.PORTAL_PORT),
				clusterNode.getPortalInetSocketAddress());

			clusterExecutorImpl.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT));

			Assert.assertEquals(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.PORTAL_PORT),
				clusterNode.getPortalInetSocketAddress());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			SetPortalInetSocketAddressAdvice.class,
			SetWebServerProtocolAdvice.class
		})
	@Test
	public void testPortalConfigured3() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			Assert.assertEquals(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT),
				clusterNode.getPortalInetSocketAddress());

			clusterExecutorImpl.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.PORTAL_PORT));

			Assert.assertEquals(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT),
				clusterNode.getPortalInetSocketAddress());
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			SetBadPortalInetSocketAddressAdvice.class,
			SetWebServerProtocolAdvice.class
		})
	@Test
	public void testPortalConfigured4() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl = getClusterExecutorImpl();

		try {
			ClusterNode clusterNode = clusterExecutorImpl.getLocalClusterNode();

			Assert.assertNull(clusterNode.getPortalInetSocketAddress());

			clusterExecutorImpl.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT));

			Assert.assertEquals(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT),
				clusterNode.getPortalInetSocketAddress());

			clusterExecutorImpl.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT));

			Assert.assertEquals(
				new InetSocketAddress(
					InetAddress.getByName(
						SetPortalInetSocketAddressAdvice.SECURE_PORTAL_ADDRESS),
					SetPortalInetSocketAddressAdvice.SECURE_PORTAL_PORT),
				clusterNode.getPortalInetSocketAddress());
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

			clusterEventListeners = new ArrayList<ClusterEventListener>();

			clusterEventListeners.add(new MockClusterEventListener());

			clusterExecutorImpl.setClusterEventListeners(clusterEventListeners);

			Assert.assertTrue(fieldClusterEventListeners.isEmpty());

			// Test 5, get address of cluster node

			List<Address> addresses =
				clusterExecutorImpl.getClusterNodeAddresses();

			Assert.assertTrue(addresses.isEmpty());

			// Test 6, get cluster node

			List<ClusterNode> clusterNodes =
				clusterExecutorImpl.getClusterNodes();

			Assert.assertTrue(clusterNodes.isEmpty());

			// Test 7, get local cluster node

			Assert.assertNull(clusterExecutorImpl.getLocalClusterNode());

			// Test 8, get address of local cluster node

			Assert.assertNull(clusterExecutorImpl.getLocalClusterNodeAddress());

			// Test 9, if cluster node is alive using cluster node ID

			Assert.assertFalse(
				clusterExecutorImpl.isClusterNodeAlive("WrongClusterNodeId"));

			// Test 10, if cluster node is alive using address

			Assert.assertFalse(
				clusterExecutorImpl.isClusterNodeAlive(
					new AddressImpl(new MockAddress())));

			// Test 11, execute cluster request

			Assert.assertNull(
				clusterExecutorImpl.execute(
					ClusterRequest.createMulticastRequest(null)));

			// Test 12, configure remote internet socket address

			clusterExecutorImpl.portalServerInetSocketAddressConfigured(
				new InetSocketAddress(80));

			// Test 13, configure local internet socket address

			clusterExecutorImpl.portalLocalInetSockAddressConfigured(
				new InetSocketAddress(80));
		}
		finally {
			clusterExecutorImpl.destroy();
		}
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		new AspectJNewEnvTestRule();

}