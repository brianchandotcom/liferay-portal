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
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvTestRule;

import java.util.logging.Level;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.JVM)
public class ClusterRequestReceiverTest
	extends BaseClusterExecutorImplTestCase {

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class
		})
	@Test
	public void testInvoke() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterRequestReceiver.class.getName(), Level.OFF);

		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();
		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();

		try {

			// Test 1, return value is null

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod1MethodKey, StringPool.BLANK),
				clusterExecutorImpl2.getLocalClusterNodeAddress());

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				clusterExecutorImpl2.getLocalClusterNodeAddress());

			// Test 2, return value is not null

			String timestamp = String.valueOf(System.currentTimeMillis());

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod1MethodKey, timestamp),
				clusterExecutorImpl2.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl1.execute(
				clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, clusterExecutorImpl2.getLocalClusterNodeAddress());

			// Test 3, return value is not serializable

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod2MethodKey),
				clusterExecutorImpl2.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl1.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(),
				clusterExecutorImpl2.getLocalClusterNodeAddress(),
				"Return value is not serializable");

			// Test 4, throw exception

			timestamp = String.valueOf(System.currentTimeMillis());

			clusterRequest = ClusterRequest.createUnicastRequest(
				new MethodHandler(testMethod3MethodKey, timestamp),
				clusterExecutorImpl2.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl1.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(),
				clusterExecutorImpl2.getLocalClusterNodeAddress(), timestamp);

			// Test 5, method handler is null

			clusterRequest = ClusterRequest.createUnicastRequest(
				null, clusterExecutorImpl2.getLocalClusterNodeAddress());

			futureClusterResponses = clusterExecutorImpl1.execute(
				clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(),
				clusterExecutorImpl2.getLocalClusterNodeAddress(),
				"Payload is not of type " + MethodHandler.class.getName());
		}
		finally {
			captureHandler.close();

			clusterExecutorImpl1.destroy();
			clusterExecutorImpl2.destroy();
		}
	}

	@AdviseWith(
		adviceClasses = {
			DisableAutodetectedAddressAdvice.class,
			EnableClusterLinkAdvice.class,
			SetJGroupsSingleThreadPoolAdvice.class
		})
	@Test
	public void testInvokeWithSingleThreadPool() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = getClusterExecutorImpl();
		ClusterExecutorImpl clusterExecutorImpl2 = getClusterExecutorImpl();

		String value = "testValue";

		try {
			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest1 =
				ClusterRequest.createUnicastRequest(
					new MethodHandler(_testMethod4MethodKey, value), address);

			FutureClusterResponses futureClusterResponses1 =
				clusterExecutorImpl1.execute(clusterRequest1);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses1.get(), clusterRequest1.getUuid(), value,
				address);

			ClusterRequest clusterRequest2 =
				ClusterRequest.createUnicastRequest(
					new MethodHandler(_testMethod4MethodKey, StringPool.BLANK),
					address);

			FutureClusterResponses futureClusterResponses2 =
				clusterExecutorImpl1.execute(clusterRequest2);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses2.get(), clusterRequest2.getUuid(), null,
				address);
		}
		finally {
			clusterExecutorImpl1.destroy();
			clusterExecutorImpl2.destroy();
		}
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		new AspectJNewEnvTestRule();

	@Aspect
	public static class SetJGroupsSingleThreadPoolAdvice {

		@Around(
			"call(* com.liferay.portal.cluster.ClusterBase.createJChannel(..))")
		public Object setSingleThreadInPool(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Object[] args = proceedingJoinPoint.getArgs();

			args[0] =
				"UDP(thread_pool.min_threads=1;thread_pool.max_threads=1;" +
					"oob_thread_pool.enabled=false;):PING:MERGE3:FD_SOCK:" +
						"FD_ALL:VERIFY_SUSPECT:pbcast.NAKACK2:UNICAST:" +
							"pbcast.STABLE:pbcast.GMS:UFC:MFC:FRAG2:RSVP";

			return proceedingJoinPoint.proceed(args);
		}

	}

	private static final MethodKey _testMethod4MethodKey = new MethodKey(
		TestBean.class, "testMethod4", String.class);

}