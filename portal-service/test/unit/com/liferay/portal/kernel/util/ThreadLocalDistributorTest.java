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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalDistributorTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	public ThreadLocalDistributorTest() {
		_keyValuePairs.add(
			new KeyValuePair(TestClass.class.getName(), "_threadLocal"));
		_keyValuePairs.add(
			new KeyValuePair(TestClass.class.getName(), "_nonStatic"));
		_keyValuePairs.add(
			new KeyValuePair(TestClass.class.getName(), "_nullValue"));
		_keyValuePairs.add(
			new KeyValuePair(TestClass.class.getName(), "_object"));
	}

	@Test
	public void testAfterPropertiesSet() throws Exception {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		try {
			threadLocalDistributor.afterPropertiesSet();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Thread local sources is null", iae.getMessage());
		}

		threadLocalDistributor.setClassLoader(getClassLoader());
		threadLocalDistributor.setThreadLocalSources(_keyValuePairs);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ThreadLocalDistributor.class.getName(), Level.WARNING);

		try {

			// With log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			threadLocalDistributor.afterPropertiesSet();

			Assert.assertEquals(3, logRecords.size());

			LogRecord logRecord1 = logRecords.get(0);

			Assert.assertEquals(
				"_nonStatic is not a static ThreadLocal",
				logRecord1.getMessage());

			LogRecord logRecord2 = logRecords.get(1);

			Assert.assertEquals(
				"_nullValue is not initialized", logRecord2.getMessage());

			LogRecord logRecord3 = logRecords.get(2);

			Assert.assertEquals(
				"_object is not of type ThreadLocal", logRecord3.getMessage());

			List<ThreadLocal<Serializable>> threadLocals =
				ReflectionTestUtil.getFieldValue(
					threadLocalDistributor, "_threadLocals");

			Assert.assertEquals(1, threadLocals.size());
			Assert.assertSame(TestClass._threadLocal, threadLocals.get(0));

			// Without log

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			threadLocalDistributor = new ThreadLocalDistributor();

			threadLocalDistributor.setClassLoader(getClassLoader());
			threadLocalDistributor.setThreadLocalSources(_keyValuePairs);

			threadLocalDistributor.afterPropertiesSet();

			Assert.assertTrue(logRecords.isEmpty());

			threadLocals = ReflectionTestUtil.getFieldValue(
				threadLocalDistributor, "_threadLocals");

			Assert.assertEquals(1, threadLocals.size());
			Assert.assertSame(TestClass._threadLocal, threadLocals.get(0));
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testGetClassLoader() {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Assert.assertSame(
			contextClassLoader, threadLocalDistributor.getClassLoader());
	}

	@Test
	public void testSerialization() throws Exception {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setClassLoader(getClassLoader());
		threadLocalDistributor.setThreadLocalSources(_keyValuePairs);

		threadLocalDistributor.afterPropertiesSet();

		String testValue = "testValue";

		TestClass._threadLocal.set(testValue);

		threadLocalDistributor.capture();

		Serializable[] threadLocalValues = ReflectionTestUtil.getFieldValue(
			threadLocalDistributor, "_threadLocalValues");

		Assert.assertEquals(1, threadLocalValues.length);
		Assert.assertSame(testValue, threadLocalValues[0]);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			threadLocalDistributor.writeExternal(objectOutputStream);
		}

		byte[] data = unsyncByteArrayOutputStream.toByteArray();

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(data);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			unsyncByteArrayInputStream);

		Assert.assertEquals(0, objectInputStream.readInt());
		Assert.assertArrayEquals(
			threadLocalValues, (Serializable[])objectInputStream.readObject());

		TestClass._threadLocal.remove();

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(data);

		objectInputStream = new ObjectInputStream(unsyncByteArrayInputStream);

		ThreadLocalDistributor newThreadLocalDistributor =
			new ThreadLocalDistributor();

		newThreadLocalDistributor.readExternal(objectInputStream);

		newThreadLocalDistributor.restore();

		Assert.assertEquals(testValue, TestClass._threadLocal.get());
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private final List<KeyValuePair> _keyValuePairs =
		new ArrayList<KeyValuePair>();

	private static class TestClass {

		@SuppressWarnings("unused")
		private static ThreadLocal<?> _nullValue;

		private static final ThreadLocal<String> _threadLocal =
			new ThreadLocal<String>();

		@SuppressWarnings("unused")
		private ThreadLocal<?> _nonStatic;

		@SuppressWarnings("unused")
		private Object _object;

	}

}