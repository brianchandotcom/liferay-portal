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

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.BaseClusterResponseCallback;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

import org.junit.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Tina Tian
 */
public abstract class BaseClusterExecutorImplTest extends BaseClusterTest {

	public static final String SERIALIZABLE_RETRUN_VALUE =
		"This is test method return value";

	@Aspect
	public static class EnableClusterExecutorDebugAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"CLUSTER_EXECUTOR_DEBUG_ENABLED)")
		public Object enableClusterExecutorDebug(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[]{Boolean.TRUE});
		}

	}

	@Aspect
	public static class EnableLiveUsersAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.LIVE_USERS_ENABLED)")
		public Object enableLiveUsers(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[]{Boolean.TRUE});
		}

	}

	@Aspect
	public static class InetAddressUtilExceptionAdvice {

		@Around(
			"call(* com.liferay.portal.kernel.util.InetAddressUtil." +
				"getLocalInetAddress())")
		public Object throwException(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			throw new Exception();
		}

	}

	@Aspect
	public static class SetPortalPortAdvice {

		public static final int PORTAL_PORT = 80;

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"PORTAL_INSTANCE_HTTP_PORT)")
		public Object enableClusterLink(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[]{PORTAL_PORT});
		}

	}

	protected void assertClusterEvent(
			ClusterEvent clusterEvent,
			ClusterEventType exceptedClusterEventType,
			ClusterNode... expectedClusterNodes)
		throws Exception {

		Assert.assertEquals(
			exceptedClusterEventType, clusterEvent.getClusterEventType());

		List<ClusterNode> clusterNodes = clusterEvent.getClusterNodes();

		Assert.assertEquals(expectedClusterNodes.length, clusterNodes.size());

		for (int i = 0; i < expectedClusterNodes.length; i++) {
			Assert.assertTrue(clusterNodes.contains(expectedClusterNodes[i]));
		}
	}

	protected void assertFutureClusterResponsesWithException(
			FutureClusterResponses futureClusterResponses, String expectedUUID,
			Address expectedAddress, String expectedExceptionMessage)
		throws Exception {

		ClusterNodeResponses clusterNodeResponses =
			futureClusterResponses.get();

		ClusterNodeResponse clusterNodeResponse =
			clusterNodeResponses.getClusterResponse(expectedAddress);

		Exception exception = clusterNodeResponse.getException();

		Assert.assertEquals(1, clusterNodeResponses.size());
		Assert.assertEquals(expectedUUID, clusterNodeResponse.getUuid());
		Assert.assertEquals(expectedAddress, clusterNodeResponse.getAddress());
		Assert.assertTrue(clusterNodeResponse.hasException());

		if (expectedExceptionMessage != null) {
			String exceptionMessage = exception.getMessage();

			if (exception instanceof InvocationTargetException) {
				InvocationTargetException invocationTargetException =
					(InvocationTargetException)exception;

				Throwable throwable =
					invocationTargetException.getTargetException();

				exceptionMessage = throwable.getMessage();
			}

			Assert.assertEquals(expectedExceptionMessage, exceptionMessage);
		}

		try {
			clusterNodeResponse.getResult();
		}
		catch (Exception e) {
			Assert.assertEquals(exception, e);
		}
	}

	protected void assertFutureClusterResponsesWithoutException(
			ClusterNodeResponses clusterNodeResponses, String expectedUUID,
			Object exceptedResult, Address expectedAddress)
		throws Exception {

		List<Address> expectedAddresses = new ArrayList<Address>();

		expectedAddresses.add(expectedAddress);

		assertFutureClusterResponsesWithoutException(
			clusterNodeResponses, expectedUUID, exceptedResult,
			expectedAddresses);
	}

	protected void assertFutureClusterResponsesWithoutException(
			ClusterNodeResponses clusterNodeResponses, String expectedUUID,
			Object exceptedResult, List<Address> expectedAddresses)
		throws Exception {

		Assert.assertEquals(
			expectedAddresses.size(), clusterNodeResponses.size());

		for (Address expectedAddress : expectedAddresses) {
			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(expectedAddress);

			Assert.assertEquals(expectedUUID, clusterNodeResponse.getUuid());
			Assert.assertEquals(
				expectedAddress, clusterNodeResponse.getAddress());
			Assert.assertEquals(
				exceptedResult, clusterNodeResponse.getResult());
			Assert.assertFalse(clusterNodeResponse.hasException());
			Assert.assertNull(clusterNodeResponse.getException());
		}
	}

	protected ClusterExecutorImpl getClusterExecutorImpl(
			boolean useMockReceiver, boolean loadSpringXML)
		throws Exception {

		_initialize(loadSpringXML);

		ClusterExecutorImpl clusterExecutorImpl = new ClusterExecutorImpl();

		clusterExecutorImpl.afterPropertiesSet();
		clusterExecutorImpl.setShortcutLocalMethod(true);

		if (useMockReceiver) {
			clusterExecutorImpl.getControlChannel().setReceiver(
				new MockClusterRequestReceiver(clusterExecutorImpl));
		}

		clusterExecutorImpl.initialize();

		return clusterExecutorImpl;
	}

	protected void updateView(
		ClusterExecutorImpl localClusterExecutorImpl,
		ClusterExecutorImpl... remoteClusterExecutorImpls) {

		final JChannel localJChannel =
			localClusterExecutorImpl.getControlChannel();

		org.jgroups.Address address = localJChannel.getAddress();

		List<org.jgroups.Address> addresses =
			new ArrayList<org.jgroups.Address>();

		addresses.add(address);

		for (ClusterExecutorImpl clusterExecutorImpl :
			remoteClusterExecutorImpls) {

			JChannel jChannel = clusterExecutorImpl.getControlChannel();

			addresses.add(jChannel.getAddress());
		}

		final View view = new View(
			address, System.currentTimeMillis(), addresses);

		new Thread() {
			public void run() {
				Receiver receiver = localJChannel.getReceiver();

				receiver.viewAccepted(view);
			}
		}.start();
	}

	protected static final String _BEAN_IDENTIFIER = "test.bean";

	protected static final String _SERVLET_CONTEXT_NAME =
		"TestServletContextName";

	protected static final MethodKey _TEST_METHOD_KEY_1 = new MethodKey(
		TestBean.class.getName(), "testMethod1", String.class);

	protected static final MethodKey _TEST_METHOD_KEY_2 = new MethodKey(
		TestBean.class.getName(), "testMethod2");

	protected static final MethodKey _TEST_METHOD_KEY_3 = new MethodKey(
		TestBean.class.getName(), "testMethod3", String.class);

	protected static final MethodKey _TEST_METHOD_KEY_4 = new MethodKey(
		TestBean.class.getName(), "testMethod4");

	protected class MockClusterEventListener implements ClusterEventListener {

		public void processClusterEvent(ClusterEvent clusterEvent) {
			try {
				ClusterEventType clusterEventType =
					clusterEvent.getClusterEventType();

				if (clusterEventType.equals(ClusterEventType.DEPART)) {
					_departMessageExchanger.exchange(clusterEvent);
				}
				else if (clusterEventType.equals(ClusterEventType.JOIN)) {
					_joinMessageExchanger.exchange(clusterEvent);
				}
			}
			catch (InterruptedException ex) {
			}
		}

		public ClusterEvent waitDepartMessage() throws Exception {
			try {
				return _departMessageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		public ClusterEvent waitJoinMessage() throws Exception {
			try {
				return _joinMessageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		private Exchanger<ClusterEvent> _departMessageExchanger =
			new Exchanger<ClusterEvent>();
		private Exchanger<ClusterEvent> _joinMessageExchanger =
			new Exchanger<ClusterEvent>();

	}

	protected class MockClusterRequestReceiver extends ClusterRequestReceiver {

		public MockClusterRequestReceiver(
			ClusterExecutorImpl clusterExecutorImpl) {

			super(clusterExecutorImpl);

			_clusterExecutorImpl = clusterExecutorImpl;
		}

		public ClusterRequest waitLocalRequestMessage() throws Exception {
			try {
				return _localRequestExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		@Override
		public void receive(Message message) {
			super.receive(message);

			Object object = message.getObject();

			try {
				if (message.getSrc().equals(
					_clusterExecutorImpl.getLocalClusterNodeAddress().
						getRealAddress())) {

					if (object instanceof ClusterRequest) {
						_localRequestExchanger.exchange((ClusterRequest)object);
					}
				}
			}
			catch (InterruptedException ex) {
			}
		}

		private ClusterExecutorImpl _clusterExecutorImpl;
		private Exchanger<ClusterRequest> _localRequestExchanger =
			new Exchanger<ClusterRequest>();

	}

	protected class MockClusterResponseCallback
		extends BaseClusterResponseCallback {

		public void callback(ClusterNodeResponses clusterNodeResponses) {
			try {
				_messageExchanger.exchange(clusterNodeResponses);
			}
			catch (Exception e) {
			}
		}

		public ClusterNodeResponses waitMessage() throws Exception {
			try {
				return _messageExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		public TimeoutException waitTimeoutException() throws Exception {
			try {
				return _timeoutExceptionExchanger.exchange(
					null, 2000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		public InterruptedException waitInterruptedException()
			throws Exception {

			try {
				return _interruptedExceptionExchanger.exchange(
					null, 1000, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException te) {
				return null;
			}
		}

		public void processInterruptedException(
			InterruptedException interruptedException) {

			try {
				_interruptedExceptionExchanger.exchange(interruptedException);
			}
			catch (Exception e) {
			}
		}

		public void processTimeoutException(TimeoutException timeoutException) {
			try {
				_timeoutExceptionExchanger.exchange(timeoutException);
			}
			catch (Exception e) {
			}
		}

		private Exchanger<ClusterNodeResponses> _messageExchanger =
			new Exchanger<ClusterNodeResponses>();
		private Exchanger<InterruptedException> _interruptedExceptionExchanger =
			new Exchanger<InterruptedException>();
		private Exchanger<TimeoutException> _timeoutExceptionExchanger =
			new Exchanger<TimeoutException>();

	}

	protected class MockPortalExecutorManager implements PortalExecutorManager {

		public <T> Future<T> execute(String name, Callable<T> callable) {
			return _threadPoolExecutor.submit(callable);
		}

		public <T> T execute(
				String name, Callable<T> callable, long timeout,
				TimeUnit timeUnit)
			throws ExecutionException, InterruptedException, TimeoutException {

			Future<T> future = _threadPoolExecutor.submit(callable);

			return future.get(timeout, timeUnit);
		}

		public ThreadPoolExecutor getPortalExecutor(String name) {
			return _threadPoolExecutor;
		}

		public ThreadPoolExecutor getPortalExecutor(
			String name, boolean createIfAbsent) {

			return _threadPoolExecutor;
		}

		public ThreadPoolExecutor registerPortalExecutor(
			String name, ThreadPoolExecutor threadPoolExecutor) {

			return _threadPoolExecutor;
		}

		public void shutdown() {
			shutdown(false);
		}

		public void shutdown(boolean interrupt) {
			if (interrupt) {
				_threadPoolExecutor.shutdownNow();
			}
			else {
				_threadPoolExecutor.shutdown();
			}
		}

		public void shutdown(String name) {
			shutdown(name, false);
		}

		public void shutdown(String name, boolean interrupt) {
			if (interrupt) {
				_threadPoolExecutor.shutdownNow();
			}
			else {
				_threadPoolExecutor.shutdown();
			}
		}

		private ThreadPoolExecutor _threadPoolExecutor =
			new ThreadPoolExecutor(10, 10);

	}

	private void _initialize(boolean loadSpringXML) {
		if (_initialized) {
			return;
		}

		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PortalExecutorManagerUtil portalExecutorManagerUtil =
			new PortalExecutorManagerUtil();

		portalExecutorManagerUtil.setPortalExecutorManager(
			new MockPortalExecutorManager());

		if (loadSpringXML) {
			ClassLoader classLoader = getClass().getClassLoader();

			PortletClassLoaderUtil.setClassLoader(classLoader);

			ApplicationContext applicationContext =
				new FileSystemXmlApplicationContext(
					"portal-impl/test/unit/com/liferay/portal/cluster/" +
						"test-spring.xml");

			BeanLocator beanLocator = new BeanLocatorImpl(
				classLoader, applicationContext);

			PortalBeanLocatorUtil.setBeanLocator(beanLocator);

			PortletBeanLocatorUtil.setBeanLocator(
				_SERVLET_CONTEXT_NAME, beanLocator);
		}

		JDKLoggerTestUtil.configureJDKLogger(
			ClusterBase.class.getName(), Level.FINE);

		_initialized = true;
	}

	private boolean _initialized;

}