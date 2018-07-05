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

package com.liferay.portal.cluster.multiple.internal.io;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ClassLoaderUtil;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.osgi.framework.Version;

/**
 * @author Lance Ji
 */
public class ClusterClassLoaderPoolTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_classLoaders = ReflectionTestUtil.getFieldValue(
			ClassLoaderPool.class, "_classLoaders");
		_contextNames = ReflectionTestUtil.getFieldValue(
			ClassLoaderPool.class, "_contextNames");

		_fallbackClassLoaders = ReflectionTestUtil.getFieldValue(
			ClusterClassLoaderPool.class, "_fallbackClassLoaders");
	}

	@After
	public void tearDown() {
		_classLoaders.clear();
		_contextNames.clear();
		_fallbackClassLoaders.clear();
	}

	@Test
	public void testConstructor() {
		new ClusterClassLoaderPool();
	}

	@Test
	public void testGetClassLoader() {
		ClassLoader classLoader1 = new URLClassLoader(new URL[0]);
		ClassLoader classLoader2 = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader1);
		ClassLoaderPool.register(_CONTEXT_NAME_2, classLoader2);
		ClusterClassLoaderPool.registerFallback(
			_SYMBOLIC_NAME, new Version("1.0.0"), classLoader1);
		ClusterClassLoaderPool.registerFallback(
			_SYMBOLIC_NAME, new Version("2.0.0"), classLoader2);

		Assert.assertSame(
			classLoader1,
			ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));
		Assert.assertSame(
			classLoader2,
			ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_2));
		Assert.assertSame(
			classLoader2,
			ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_3));
		Assert.assertSame(
			ClassLoaderUtil.getContextClassLoader(),
			ClusterClassLoaderPool.getClassLoader(_SYMBOLIC_NAME));
		Assert.assertSame(
			ClassLoaderUtil.getContextClassLoader(),
			ClusterClassLoaderPool.getClassLoader(null));
		Assert.assertSame(
			ClassLoaderUtil.getContextClassLoader(),
			ClusterClassLoaderPool.getClassLoader("null"));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterClassLoaderPool.class.getName(), Level.WARNING)) {

			// Test 1, log level is WARNING

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertSame(
				classLoader2,
				ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_3));

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to find ClassLoader for " + _CONTEXT_NAME_3 +
					", ClassLoader " + _CONTEXT_NAME_2 + " is provided instead",
				logRecord.getMessage());

			// Test 2, log level is OFF

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			Assert.assertSame(
				classLoader2,
				ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_3));

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());
		}
	}

	@Test
	public void testGetClassLoaderDebug() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterClassLoaderPool.class.getName(), Level.INFO)) {

			// Test 1, log level is INFO

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertSame(
				ClassLoaderUtil.getContextClassLoader(),
				ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());

			// Test 2, log level is FINE

			logRecords = captureHandler.resetLogLevel(Level.FINE);

			Assert.assertSame(
				ClassLoaderUtil.getContextClassLoader(),
				ClusterClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to find ClassLoader for " + _CONTEXT_NAME_1 +
					", fall back to current thread's context classLoader",
				logRecord.getMessage());
		}
	}

	@Test
	public void testGetContextName() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader);
		ClusterClassLoaderPool.registerFallback(
			_SYMBOLIC_NAME, new Version("1.0.0"), classLoader);

		Assert.assertEquals(
			"null", ClusterClassLoaderPool.getContextName(null));
		Assert.assertEquals(
			_CONTEXT_NAME_1,
			ClusterClassLoaderPool.getContextName(classLoader));
		Assert.assertEquals(
			"null",
			ClusterClassLoaderPool.getContextName(
				new URLClassLoader(new URL[0])));
	}

	@Test
	public void testGetContextNameDebug() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterClassLoaderPool.class.getName(), Level.INFO)) {

			// Test 1, log level is INFO

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(
				"null", ClusterClassLoaderPool.getContextName(classLoader));

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());

			// Test 2, log level is FINE

			logRecords = captureHandler.resetLogLevel(Level.FINE);

			Assert.assertEquals(
				"null", ClusterClassLoaderPool.getContextName(classLoader));

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to find contextName for " + classLoader +
					", send 'null' as contextName instead",
				logRecord.getMessage());
		}
	}

	@Test
	public void testUnregisterFallbackClassLoader() {
		ClusterClassLoaderPool.registerFallback(
			_SYMBOLIC_NAME, new Version("1.0.0"),
			new URLClassLoader(new URL[0]));
		ClusterClassLoaderPool.registerFallback(
			_SYMBOLIC_NAME, new Version("2.0.0"),
			new URLClassLoader(new URL[0]));
		ClusterClassLoaderPool.registerFallback(
			_SYMBOLIC_NAME, new Version("3.0.0"),
			new URLClassLoader(new URL[0]));

		ClusterClassLoaderPool.unregisterFallback(
			"WRONG_SYMBOLIC_NAME", new Version("1.0.0"));

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClusterClassLoaderPool.unregisterFallback(
			_SYMBOLIC_NAME, new Version("4.0.0"));

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClusterClassLoaderPool.unregisterFallback(
			_SYMBOLIC_NAME, new Version("2.0.0"));

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClusterClassLoaderPool.unregisterFallback(
			_SYMBOLIC_NAME, new Version("1.0.0"));

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClusterClassLoaderPool.unregisterFallback(
			_SYMBOLIC_NAME, new Version("3.0.0"));

		Assert.assertTrue(
			_fallbackClassLoaders.toString(), _fallbackClassLoaders.isEmpty());
	}

	private static final String _CONTEXT_NAME_1 =
		ClusterClassLoaderPoolTest._SYMBOLIC_NAME + "_1.0.0";

	private static final String _CONTEXT_NAME_2 =
		ClusterClassLoaderPoolTest._SYMBOLIC_NAME + "_2.0.0";

	private static final String _CONTEXT_NAME_3 =
		ClusterClassLoaderPoolTest._SYMBOLIC_NAME + "_3.0.0";

	private static final String _CONTEXT_NAME_4 =
		ClusterClassLoaderPoolTest._SYMBOLIC_NAME + "_4.0.0";

	private static final String _SYMBOLIC_NAME = "symbolic.name";

	private Map<String, ClassLoader> _classLoaders;
	private Map<ClassLoader, String> _contextNames;
	private Map<String, List> _fallbackClassLoaders;

}