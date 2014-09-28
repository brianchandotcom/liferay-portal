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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class AsyncBrokerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@After
	public void tearDown() {
		System.clearProperty(_THREAD_ENABLED_KEY);
	}

	@Test
	public void testGetOpenBids() {
		AsyncBroker<String, String> asyncBroker =
			new AsyncBroker<String, String>();

		Map<String, NoticeableFuture<String>> map = asyncBroker.getOpenBids();

		Assert.assertTrue(map.isEmpty());

		try {
			map.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_key);

		Assert.assertEquals(1, map.size());
		Assert.assertSame(noticeableFuture, map.get(_key));

		noticeableFuture.cancel(true);

		Assert.assertTrue(map.isEmpty());
	}

	@Test
	public void testOrphanCancellationAlreadyDone()
		throws InterruptedException {

		System.setProperty(_THREAD_ENABLED_KEY, StringPool.FALSE);

		AsyncBroker<String, String> asyncBroker =
			new AsyncBroker<String, String>();

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_key);

		noticeableFuture.cancel(true);

		noticeableFuture = null;

		GCUtil.gc();

		ReflectionTestUtil.invoke(
			FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);
	}

	@Test
	public void testOrphanCancellationNotDoneYet() throws InterruptedException {
		System.setProperty(_THREAD_ENABLED_KEY, StringPool.FALSE);

		AsyncBroker<String, String> asyncBroker =
			new AsyncBroker<String, String>();

		// Without log

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			AsyncBroker.class.getName(), Level.OFF);

		try {
			asyncBroker.post(_key);

			GCUtil.gc();

			ReflectionTestUtil.invoke(
				FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();
		}

		// With log

		captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			AsyncBroker.class.getName(), Level.WARNING);

		try {
			NoticeableFuture<String> noticeableFuture = asyncBroker.post(_key);

			String toString = noticeableFuture.toString();

			noticeableFuture = null;

			GCUtil.gc();

			ReflectionTestUtil.invoke(
				FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Cancelled an orphan NoticeableFuture : " + toString +
					" under key : " + _key,
				logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testOrphanCancellationNotSupported() throws Exception {
		System.setProperty(_THREAD_ENABLED_KEY, StringPool.FALSE);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			AsyncBroker.class.getName(), Level.SEVERE);

		try {
			AsyncBroker<String, String> asyncBroker =
				new AsyncBroker<String, String>();

			asyncBroker.post(_key);

			GCUtil.gc();

			Field field = ReflectionTestUtil.getFieldValue(
				AsyncBroker.class, "_REFERENT_FIELD");

			field.setAccessible(false);

			ReflectionTestUtil.invoke(
				FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			String message = logRecord.getMessage();

			Assert.assertTrue(
				message.startsWith("Unable to access referent of "));

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(
				IllegalAccessException.class, throwable.getClass());
		}
		finally {
			captureHandler.close();
		}
	}

	@AdviseWith(adviceClasses = ReflectionUtilAdvice.class)
	@Test
	public void testPhantomReferenceResurrectionNotSupported()
		throws ClassNotFoundException {

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			AsyncBroker.class.getName(), Level.WARNING);

		try {
			Class.forName(
				AsyncBroker.class.getName(), true,
				AsyncBroker.class.getClassLoader());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Current JVM does not support java.lang.ref.PhantomReference " +
					"resurrection, orphan NoticeableFuture cancellation has " +
						"been disabled.", logRecord.getMessage());
			Assert.assertSame(_exception, logRecord.getThrown());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testPost() throws Exception {
		AsyncBroker<String, String> asyncBroker =
			new AsyncBroker<String, String>();

		ReflectionUtilAdvice.setEnabled(false);

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_key);

		Assert.assertEquals(1, defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_key));
		Assert.assertSame(noticeableFuture, asyncBroker.post(_key));
		Assert.assertEquals(1, defaultNoticeableFutures.size());
		Assert.assertTrue(noticeableFuture.cancel(true));
		Assert.assertTrue(defaultNoticeableFutures.isEmpty());
	}

	@AdviseWith(adviceClasses = ReflectionUtilAdvice.class)
	@Test
	public void testPostPhantomReferenceResurrectionNotSupported()
		throws Exception {

		testPost();
	}

	@Test
	public void testTakeWithException() throws Exception {
		AsyncBroker<String, String> asyncBroker =
			new AsyncBroker<String, String>();

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		Exception exception = new Exception();

		Assert.assertFalse(asyncBroker.takeWithException(_key, exception));

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_key);

		Assert.assertEquals(1, defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_key));
		Assert.assertTrue(asyncBroker.takeWithException(_key, exception));

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}
	}

	@Test
	public void testTakeWithResult() throws Exception {
		AsyncBroker<String, String> asyncBroker =
			new AsyncBroker<String, String>();

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		Assert.assertFalse(asyncBroker.takeWithResult(_key, _value));

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_key);

		Assert.assertEquals(1, defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_key));
		Assert.assertTrue(asyncBroker.takeWithResult(_key, _value));
		Assert.assertEquals(_value, noticeableFuture.get());
		Assert.assertTrue(defaultNoticeableFutures.isEmpty());
	}

	@Aspect
	public static class ReflectionUtilAdvice {

		public static void setEnabled(boolean enabled) {
			_enabled = enabled;
		}

		@Around(
			"execution(public static java.lang.reflect.Field " +
				"com.liferay.portal.kernel.util.ReflectionUtil." +
					"getDeclaredField(Class, String))")
		public Object getDeclaredField(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			if (!_enabled) {
				return proceedingJoinPoint.proceed();
			}

			throw _exception;
		}

		private static boolean _enabled = true;

	}

	private static final String _THREAD_ENABLED_KEY =
		FinalizeManager.class.getName() + ".thread.enabled";

	private static final Exception _exception = new Exception(
		"Forced Exception");

	private final String _key = "testKey";
	private final String _value = "testValue";

}