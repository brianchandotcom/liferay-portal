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

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.jgroups.Receiver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Tina Tian
 */
public class BaseClusterTestCase {

	@Before
	public void setUp() {
		_clusterBaseCaptureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterBase.class.getName(), Level.OFF);
	}

	@After
	public void tearDown() {
		_clusterBaseCaptureHandler.close();
	}

	@Aspect
	public static class BaseReceiverAdvice {

		public static Object getJGroupsMessagePayload(
				Receiver receiver, org.jgroups.Address sourceAddress)
			throws InterruptedException {

			_countDownLatch.await(10, TimeUnit.MINUTES);

			List<org.jgroups.Message> jGroupsMessageList = _jGroupsMessages.get(
				receiver);

			if ((jGroupsMessageList == null) || jGroupsMessageList.isEmpty()) {
				return null;
			}

			for (org.jgroups.Message jGroupsMessage : jGroupsMessageList) {
				if (sourceAddress.equals(jGroupsMessage.getSrc())) {
					return jGroupsMessage.getObject();
				}
			}

			return null;
		}

		public static void reset(int expectedMessageNumber) {
			_countDownLatch = new CountDownLatch(expectedMessageNumber);

			_jGroupsMessages.clear();
		}

		@Around(
			"execution(* com.liferay.portal.cluster.BaseReceiver." +
				"doReceive(org.jgroups.Message))")
		public void doReceive(ProceedingJoinPoint proceedingJoinPoint) {
			Receiver receiver = (Receiver)proceedingJoinPoint.getThis();
			org.jgroups.Message jGroupsMessage =
				(org.jgroups.Message)proceedingJoinPoint.getArgs()[0];

			List<org.jgroups.Message> jGroupsMessageList = _jGroupsMessages.get(
				receiver);

			if (jGroupsMessageList == null) {
				jGroupsMessageList = new ArrayList<org.jgroups.Message>();

				List<org.jgroups.Message> previousJgroupsMessageList =
					_jGroupsMessages.putIfAbsent(receiver, jGroupsMessageList);

				if (previousJgroupsMessageList != null) {
					jGroupsMessageList = previousJgroupsMessageList;
				}
			}

			jGroupsMessageList.add(jGroupsMessage);

			_countDownLatch.countDown();
		}

		private static CountDownLatch _countDownLatch;
		private static final ConcurrentMap<Receiver, List<org.jgroups.Message>>
			_jGroupsMessages =
				new ConcurrentHashMap<Receiver, List<org.jgroups.Message>>();

	}

	@Aspect
	public static class DisableAutodetectedAddressAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"CLUSTER_LINK_AUTODETECT_ADDRESS)")
		public Object disableAutodetectedAddress(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {""});
		}

	}

	@Aspect
	public static class DisableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object disableClusterLink(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object enableClusterLink(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class JChannelExceptionAdvice {

		public static void setConnectException(Exception exception) {
			_connectException = exception;
		}

		@Around("call(* org.jgroups.JChannel.connect(..))")
		public void connect(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			if (_connectException != null) {
				throw _connectException;
			}

			proceedingJoinPoint.proceed();
		}

		@Around("call(* org.jgroups.JChannel.send(..))")
		public Object send() throws Exception {
			throw new Exception();
		}

		private static Exception _connectException;

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
			Throwable throwable = logRecord.getThrown();

			Assert.assertEquals(exceptionClass, throwable.getClass());
		}

		logRecords.clear();
	}

	protected class MockAddress implements org.jgroups.Address {

		@Override
		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		@Override
		public void readExternal(ObjectInput objectInput) {
		}

		@Override
		public void readFrom(DataInput dataInput) throws Exception {
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput) {
		}

		@Override
		public void writeTo(DataOutput dataOutput) throws Exception {
		}

	}

	private CaptureHandler _clusterBaseCaptureHandler;

}