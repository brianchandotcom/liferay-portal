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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.List;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;

/**
 * @author Tina Tian
 */
public class BaseClusterTest {

	@Aspect
	public static class DisableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object disableClusterLink(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[]{Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object enableClusterLink(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[]{Boolean.TRUE});
		}

	}

	@Aspect
	public static class JChannelExceptionAdvice {

		@Around("call(* org.jgroups.JChannel.send(..))")
		public Object throwException(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			throw new Exception();
		}

	}

	protected void assertLogger(
		List<LogRecord> logRecords, String message, Class<?> exceptionClass) {

		if (message == null) {
			Assert.assertTrue(logRecords.isEmpty());

			return;
		}

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(message, logRecord.getMessage());

		if (exceptionClass == null) {
			Assert.assertNull(logRecord.getThrown());
		}
		else {
			Assert.assertNotNull(logRecord.getThrown());
		}

		logRecords.clear();
	}

	protected class MockAddress implements org.jgroups.Address {

		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		public void readExternal(ObjectInput objectInput) {
		}

		public void readFrom(DataInput dataInput) throws Exception {
		}

		public int size() {
			return 0;
		}

		public void writeExternal(ObjectOutput objectOutput) {
		}

		public void writeTo(DataOutput dataOutput) throws Exception {
		}

	}

}