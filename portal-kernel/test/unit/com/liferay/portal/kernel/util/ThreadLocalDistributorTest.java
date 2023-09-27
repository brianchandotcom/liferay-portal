/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalDistributorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	public ThreadLocalDistributorTest() {
		_keyValuePairs.add(
			new KeyValuePair(TestClass.class.getName(), "_threadLocal"));
		_keyValuePairs.add(
			new KeyValuePair(TestClass.class.getName(), "_nonstatic"));
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
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Thread local sources is null",
				illegalArgumentException.getMessage());
		}

		threadLocalDistributor.setClassLoader(getClassLoader());
		threadLocalDistributor.setThreadLocalSources(_keyValuePairs);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ThreadLocalDistributor.class.getName(), Level.WARNING)) {

			// With log

			List<LogEntry> logEntries = logCapture.getLogEntries();

			threadLocalDistributor.afterPropertiesSet();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			LogEntry logEntry1 = logEntries.get(0);

			Assert.assertEquals(
				"_nonstatic is not a static ThreadLocal",
				logEntry1.getMessage());

			LogEntry logEntry2 = logEntries.get(1);

			Assert.assertEquals(
				"_nullValue is not initialized", logEntry2.getMessage());

			LogEntry logEntry3 = logEntries.get(2);

			Assert.assertEquals(
				"_object is not of type ThreadLocal", logEntry3.getMessage());

			List<ThreadLocal<Serializable>> threadLocals =
				ReflectionTestUtil.getFieldValue(
					threadLocalDistributor, "_threadLocals");

			Assert.assertEquals(
				threadLocals.toString(), 1, threadLocals.size());
			Assert.assertSame(TestClass._threadLocal, threadLocals.get(0));

			// Without log

			logEntries = logCapture.resetPriority(String.valueOf(Level.OFF));

			threadLocalDistributor = new ThreadLocalDistributor();

			threadLocalDistributor.setClassLoader(getClassLoader());
			threadLocalDistributor.setThreadLocalSources(_keyValuePairs);

			threadLocalDistributor.afterPropertiesSet();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());

			threadLocals = ReflectionTestUtil.getFieldValue(
				threadLocalDistributor, "_threadLocals");

			Assert.assertEquals(
				threadLocals.toString(), 1, threadLocals.size());
			Assert.assertSame(TestClass._threadLocal, threadLocals.get(0));
		}
	}

	@Test
	public void testGetClassLoader() {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		Thread currentThread = Thread.currentThread();

		Assert.assertSame(
			currentThread.getContextClassLoader(),
			threadLocalDistributor.getClassLoader());
	}

	@Test
	public void testSerialization() throws Exception {
		ThreadLocalDistributor threadLocalDistributor =
			new ThreadLocalDistributor();

		threadLocalDistributor.setClassLoader(getClassLoader());
		threadLocalDistributor.setThreadLocalSources(_keyValuePairs);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ThreadLocalDistributor.class.getName(), Level.WARNING)) {

			threadLocalDistributor.afterPropertiesSet();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"_nonstatic is not a static ThreadLocal",
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"_nullValue is not initialized", logEntry.getMessage());

			logEntry = logEntries.get(2);

			Assert.assertEquals(
				"_object is not of type ThreadLocal", logEntry.getMessage());
		}

		String testValue = "testValue";

		TestClass._threadLocal.set(testValue);

		threadLocalDistributor.capture();

		Serializable[] threadLocalValues = ReflectionTestUtil.getFieldValue(
			threadLocalDistributor, "_threadLocalValues");

		Assert.assertEquals(
			Arrays.toString(threadLocalValues), 1, threadLocalValues.length);
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

	private final List<KeyValuePair> _keyValuePairs = new ArrayList<>();

	private static class TestClass {

		@SuppressWarnings("unused")
		private static ThreadLocal<?> _nullValue;

		private static final ThreadLocal<String> _threadLocal =
			new ThreadLocal<>();

		@SuppressWarnings("unused")
		private ThreadLocal<?> _nonstatic;

		@SuppressWarnings("unused")
		private Object _object;

	}

}