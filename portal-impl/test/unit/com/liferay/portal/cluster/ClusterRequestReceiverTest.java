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
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewJVMJUnitTestRunner;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(AspectJMockingNewJVMJUnitTestRunner.class)
public class ClusterRequestReceiverTest
	extends BaseClusterExecutorImplTestCase {

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testInvoke1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, StringPool.BLANK);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				address);
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
	public void testInvoke2() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, address);
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
	public void testInvoke3() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			MethodHandler methodHandler = new MethodHandler(
				testMethod2MethodKey);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				"Return value is not serializable");
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
	public void testInvoke4() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod3MethodKey, timestamp);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			JDKLoggerTestUtil.configureJDKLogger(
				ClusterRequestReceiver.class.getName(), Level.SEVERE);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				timestamp);
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
	public void testInvoke5() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				null, address);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				"Payload is not of type " + MethodHandler.class.getName());
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
	public void testInvoke6() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, true);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, true);

			MethodHandler methodHandler = new MethodHandler(
				testMethod4MethodKey);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterRequest.setBeanIdentifier(BEAN_IDENTIFIER);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				SERIALIZABLE_RETRUN_VALUE, address);
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
	public void testInvoke7() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, true);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, true);

			MethodHandler methodHandler = new MethodHandler(
				testMethod4MethodKey);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterRequest.setBeanIdentifier(BEAN_IDENTIFIER);
			clusterRequest.setServletContextName(SERVLET_CONTEXT_NAME);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				SERIALIZABLE_RETRUN_VALUE, address);
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
	public void testInvoke8() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, true);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, true);

			String timestamp = String.valueOf(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				testMethod1MethodKey, timestamp);

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterRequest.setServletContextName(SERVLET_CONTEXT_NAME);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, address);
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
	public void testInvoke9() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, true);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, true);

			MethodHandler methodHandler = new MethodHandler(
				new MethodKey(TestBean.class, "nonexistentMethod"));

			Address address = clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			clusterRequest.setServletContextName(SERVLET_CONTEXT_NAME);

			List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
				ClusterRequestReceiver.class.getName(), Level.SEVERE);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), address,
				null);

			assertLogger(
				logRecords, "Unable to invoke method " + methodHandler,
				NoSuchMethodException.class);
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

}