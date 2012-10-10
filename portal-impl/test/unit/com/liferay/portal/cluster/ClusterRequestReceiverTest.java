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
public class ClusterRequestReceiverTest extends BaseClusterExecutorImplTest {

	@AdviseWith(adviceClasses = {EnableClusterLinkAdvice.class})
	@Test
	public void testInvoke1() throws Exception {
		ClusterExecutorImpl clusterExecutorImpl1 = null;
		ClusterExecutorImpl clusterExecutorImpl2 = null;

		try {
			clusterExecutorImpl1 = getClusterExecutorImpl(false, false);
			clusterExecutorImpl2 = getClusterExecutorImpl(false, false);

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, "");

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(), null,
				destAddress);
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			String timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, destAddress);
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(_TEST_METHOD_KEY_2);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			String timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_3, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			JDKLoggerTestUtil.configureJDKLogger(
				ClusterRequestReceiver.class.getName(), Level.SEVERE);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				null, destAddress);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(_TEST_METHOD_KEY_4);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			clusterRequest.setBeanIdentifier(_BEAN_IDENTIFIER);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				SERIALIZABLE_RETRUN_VALUE, destAddress);
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
			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(_TEST_METHOD_KEY_4);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			clusterRequest.setBeanIdentifier(_BEAN_IDENTIFIER);
			clusterRequest.setServletContextName(_SERVLET_CONTEXT_NAME);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				SERIALIZABLE_RETRUN_VALUE, destAddress);
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			String timestamp = Long.toString(System.currentTimeMillis());

			MethodHandler methodHandler = new MethodHandler(
				_TEST_METHOD_KEY_1, timestamp);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			clusterRequest.setServletContextName(_SERVLET_CONTEXT_NAME);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithoutException(
				futureClusterResponses.get(), clusterRequest.getUuid(),
				timestamp, destAddress);
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

			Address destAddress =
				clusterExecutorImpl2.getLocalClusterNodeAddress();

			MethodHandler methodHandler = new MethodHandler(
				new MethodKey(TestBean.class.getName(), "nonExisitedMethod"));

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, destAddress);

			clusterRequest.setServletContextName(_SERVLET_CONTEXT_NAME);

			List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
				ClusterRequestReceiver.class.getName(), Level.SEVERE);

			FutureClusterResponses futureClusterResponses =
				clusterExecutorImpl1.execute(clusterRequest);

			assertFutureClusterResponsesWithException(
				futureClusterResponses, clusterRequest.getUuid(), destAddress,
				null);

			assertLogger(
				logRecords, "Failed to invoke method " + methodHandler,
				Exception.class);
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