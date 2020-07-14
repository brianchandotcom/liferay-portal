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

package com.liferay.petra.lang;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderPoolTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_classLoaders = ReflectionTestUtil.getFieldValue(
			ClassLoaderPool.class, "_classLoaders");

		_contextNames = ReflectionTestUtil.getFieldValue(
			ClassLoaderPool.class, "_contextNames");

		_fallbackClassLoaders = ReflectionTestUtil.getFieldValue(
			ClassLoaderPool.class, "_fallbackClassLoaders");

		Class<?> clazz = Class.forName(
			"com.liferay.petra.lang.ClassLoaderPool$Version");

		_versionConstructor = clazz.getDeclaredConstructor(
			int.class, int.class, int.class, String.class);

		_versionConstructor.setAccessible(true);

		Thread thread = Thread.currentThread();

		_contextClassLoader = thread.getContextClassLoader();
	}

	@Before
	public void setUp() {
		_classLoaders.clear();

		_contextNames.clear();

		_fallbackClassLoaders.clear();
	}

	@Test
	public void testConcurrentRegister() throws Exception {
		ClassLoader classLoader1 = new URLClassLoader(new URL[0]);
		ClassLoader classLoader2 = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader1);

		Assert.assertSame(
			classLoader1, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));
		Assert.assertSame(
			classLoader1, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_2));

		BlockingInvocationHandler blockingInvocationHandler = _block();

		FutureTask<Void> futureTask = new FutureTask<>(
			() -> {
				Assert.assertSame(
					classLoader2,
					ClassLoaderPool.getClassLoader(_CONTEXT_NAME_2));

				return null;
			});

		Thread thread = new Thread(
			futureTask,
			ClassLoaderPoolTest.class.getName() + "-testConcurrentRegister");

		thread.start();

		blockingInvocationHandler.waitUntilBlock();

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader2);

		blockingInvocationHandler.unblock();

		futureTask.get();
	}

	@Test
	public void testConcurrentUnregister() throws Exception {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader);

		Assert.assertSame(
			classLoader, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));
		Assert.assertSame(
			classLoader, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_2));

		BlockingInvocationHandler blockingInvocationHandler = _block();

		FutureTask<Void> futureTask = new FutureTask<>(
			() -> {
				Assert.assertSame(
					_contextClassLoader,
					ClassLoaderPool.getClassLoader(_CONTEXT_NAME_2));

				return null;
			});

		Thread thread = new Thread(
			futureTask,
			ClassLoaderPoolTest.class.getName() + "-testConcurrentUnregister");

		thread.start();

		blockingInvocationHandler.waitUntilBlock();

		ClassLoaderPool.unregister(_CONTEXT_NAME_1);

		blockingInvocationHandler.unblock();

		futureTask.get();
	}

	@Test
	public void testConstructor() {
		new ClassLoaderPool();
	}

	@Test
	public void testGetClassLoader() {
		Assert.assertSame(
			_contextClassLoader,
			ClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));

		ClassLoader classLoader1 = new URLClassLoader(new URL[0]);
		ClassLoader classLoader2 = new URLClassLoader(new URL[0]);
		ClassLoader classLoader3 = new URLClassLoader(new URL[0]);
		ClassLoader classLoader4 = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader1);
		ClassLoaderPool.register(_CONTEXT_NAME_2, classLoader2);
		ClassLoaderPool.register(_SYMBOLIC_NAME + "0_", classLoader3);
		ClassLoaderPool.register(_SYMBOLIC_NAME + "_X", classLoader4);

		Assert.assertSame(
			classLoader1, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_1));
		Assert.assertSame(
			classLoader2, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_2));
		Assert.assertSame(
			classLoader2, ClassLoaderPool.getClassLoader(_CONTEXT_NAME_3));
		Assert.assertSame(
			classLoader3,
			ClassLoaderPool.getClassLoader(_SYMBOLIC_NAME + "0_"));
		Assert.assertSame(
			classLoader4,
			ClassLoaderPool.getClassLoader(_SYMBOLIC_NAME + "_X"));
		Assert.assertSame(
			_contextClassLoader,
			ClassLoaderPool.getClassLoader(_SYMBOLIC_NAME + "1_"));
		Assert.assertSame(
			_contextClassLoader,
			ClassLoaderPool.getClassLoader(_SYMBOLIC_NAME));
		Assert.assertSame(
			_contextClassLoader, ClassLoaderPool.getClassLoader(null));
		Assert.assertSame(
			_contextClassLoader, ClassLoaderPool.getClassLoader("null"));
		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());
	}

	@Test
	public void testGetClassLoaderWithInvalidContextName() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Assert.assertSame(
			contextClassLoader, ClassLoaderPool.getClassLoader("null"));
		Assert.assertSame(
			contextClassLoader, ClassLoaderPool.getClassLoader(null));
	}

	@Test
	public void testGetClassLoaderWithValidContextName() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);

		Assert.assertSame(
			classLoader,
			ClassLoaderPool.getClassLoader(_CONTEXT_NAME_WITHOUT_VERSION));
	}

	@Test
	public void testGetContextName() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_1, classLoader);

		Assert.assertEquals("null", ClassLoaderPool.getContextName(null));
		Assert.assertEquals(
			_CONTEXT_NAME_1, ClassLoaderPool.getContextName(classLoader));
		Assert.assertEquals(
			"null",
			ClassLoaderPool.getContextName(new URLClassLoader(new URL[0])));
	}

	@Test
	public void testGetContextNameWithInvalidClassLoader() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);

		Assert.assertEquals(
			"null",
			ClassLoaderPool.getContextName(new URLClassLoader(new URL[0])));
		Assert.assertEquals("null", ClassLoaderPool.getContextName(null));
	}

	@Test
	public void testGetContextNameWithValidClassLoader() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);

		Assert.assertEquals(
			_CONTEXT_NAME_WITHOUT_VERSION,
			ClassLoaderPool.getContextName(classLoader));
	}

	@Test
	public void testParse() throws Exception {
		Method method = ReflectionTestUtil.getMethod(
			ClassLoaderPool.class, "_parseVersion", String.class);

		_assertEquals("1.0.0", method.invoke(null, "1"));
		_assertEquals("1.0.0", method.invoke(null, "1.0"));
		_assertEquals("1.0.0.0", method.invoke(null, "1.0.0.0"));
		_assertEquals("1.0.0.Aa0_-", method.invoke(null, "1.0.0.Aa0_-"));

		Assert.assertNull(
			"null should be return because 'x' of \"1.x.0\" is not a decimal " +
				"digit",
			method.invoke(null, "1.x.0"));
		Assert.assertNull(
			"null should be return because '-1' of \"-1.0.0\" is less than 0",
			method.invoke(null, "-1.0.0"));
		Assert.assertNull(
			"null should be return because '-1' of \"1.-1.0\" is less than 0",
			method.invoke(null, "1.-1.0"));
		Assert.assertNull(
			"null should be return because '-1' of \"1.0.-1\" is less than 0",
			method.invoke(null, "1.0.-1"));
		Assert.assertNull(
			"null should be return because '~' of \"1.0.0.~\" is not a valid " +
				"qualifier char",
			method.invoke(null, "1.0.0.~"));
		Assert.assertNull(
			"null should be return because \"1.0.\" is not a complete version",
			method.invoke(null, "1.0."));
		Assert.assertNull(
			"null should be return because " + (char)128 + "of 1.0.0." +
				(char)128 + "is not a valid qualifier char",
			method.invoke(null, "1.0.0." + (char)128));
	}

	@Test
	public void testRegister() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);

		Assert.assertEquals(_contextNames.toString(), 1, _contextNames.size());
		Assert.assertEquals(_classLoaders.toString(), 1, _classLoaders.size());
		Assert.assertSame(
			classLoader, _classLoaders.get(_CONTEXT_NAME_WITHOUT_VERSION));
		Assert.assertEquals(
			_CONTEXT_NAME_WITHOUT_VERSION, _contextNames.get(classLoader));
	}

	@Test
	public void testRegisterWithNullClassLoader() {
		try {
			ClassLoaderPool.register("null", null);

			Assert.fail("null classLoader will cause NullPointerException");
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				NullPointerException.class, nullPointerException.getClass());
		}
	}

	@Test
	public void testRegisterWithNullContextName() {
		try {
			ClassLoaderPool.register(null, null);

			Assert.fail("null contextName will cause NullPointerException");
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				NullPointerException.class, nullPointerException.getClass());
		}
	}

	@Test
	public void testUnregisterClassLoader() {
		ClassLoaderPool.register(
			_CONTEXT_NAME_WITHOUT_VERSION + "_X",
			new URLClassLoader(new URL[0]));

		ClassLoaderPool.unregister(_CONTEXT_NAME_WITHOUT_VERSION + "_X");

		_assertEmptyMaps();
	}

	@Test
	public void testUnregisterFallbackClassLoader() {
		ClassLoaderPool.register(
			_CONTEXT_NAME_1, new URLClassLoader(new URL[0]));
		ClassLoaderPool.register(
			_CONTEXT_NAME_2, new URLClassLoader(new URL[0]));
		ClassLoaderPool.register(
			_CONTEXT_NAME_3, new URLClassLoader(new URL[0]));

		ClassLoaderPool.unregister("WRONG_SYMBOLIC_NAME_1.0.0");

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClassLoaderPool.unregister(_CONTEXT_NAME_4);

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClassLoaderPool.unregister(_CONTEXT_NAME_2);

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClassLoaderPool.unregister(_CONTEXT_NAME_1);

		Assert.assertEquals(
			_fallbackClassLoaders.toString(), 1, _fallbackClassLoaders.size());

		ClassLoaderPool.unregister(_CONTEXT_NAME_3);

		Assert.assertTrue(
			_fallbackClassLoaders.toString(), _fallbackClassLoaders.isEmpty());
	}

	@Test
	public void testUnregisterWithInvalidClassLoader() {
		ClassLoaderPool.unregister(new URLClassLoader(new URL[0]));

		_assertEmptyMaps();
	}

	@Test
	public void testUnregisterWithInvalidContextName() {
		ClassLoaderPool.unregister(_CONTEXT_NAME_WITHOUT_VERSION);

		_assertEmptyMaps();
	}

	@Test
	public void testUnregisterWithValidClassLoader() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);

		ClassLoaderPool.unregister(classLoader);

		_assertEmptyMaps();
	}

	@Test
	public void testUnregisterWithValidContextName() {
		ClassLoader classLoader = new URLClassLoader(new URL[0]);

		ClassLoaderPool.register(_CONTEXT_NAME_WITHOUT_VERSION, classLoader);
		ClassLoaderPool.register(
			_CONTEXT_NAME_WITHOUT_VERSION + "_", classLoader);

		ClassLoaderPool.unregister(_CONTEXT_NAME_WITHOUT_VERSION);
		ClassLoaderPool.unregister(_CONTEXT_NAME_WITHOUT_VERSION + "_");

		_assertEmptyMaps();
	}

	@Test
	public void testVersionCompareTo() throws Exception {
		Class<?> clazz = Class.forName(
			"com.liferay.petra.lang.ClassLoaderPool$Version");

		Method method = ReflectionTestUtil.getMethod(clazz, "compareTo", clazz);

		Object version = _versionConstructor.newInstance(2, 1, 1, "");

		Assert.assertEquals(0, method.invoke(version, version));
		Assert.assertEquals(
			1,
			method.invoke(
				version, _versionConstructor.newInstance(1, 0, 0, "")));
		Assert.assertEquals(
			1,
			method.invoke(
				version, _versionConstructor.newInstance(2, 0, 0, "")));
		Assert.assertEquals(
			1,
			method.invoke(
				version, _versionConstructor.newInstance(2, 1, 0, "")));
		Assert.assertEquals(
			0,
			method.invoke(
				version, _versionConstructor.newInstance(2, 1, 1, "")));
	}

	private void _assertEmptyMaps() {
		Assert.assertTrue(_contextNames.toString(), _contextNames.isEmpty());
		Assert.assertTrue(_classLoaders.toString(), _classLoaders.isEmpty());
		Assert.assertTrue(
			_fallbackClassLoaders.toString(), _fallbackClassLoaders.isEmpty());
	}

	private void _assertEquals(String expectedVersion, Object version) {
		int major = ReflectionTestUtil.getFieldValue(version, "_major");
		int minor = ReflectionTestUtil.getFieldValue(version, "_minor");
		int micro = ReflectionTestUtil.getFieldValue(version, "_micro");

		String qualifier = ReflectionTestUtil.getFieldValue(
			version, "_qualifier");

		int length = qualifier.length();

		StringBuilder result = new StringBuilder(20 + length);

		result.append(major);
		result.append(".");
		result.append(minor);
		result.append(".");
		result.append(micro);

		if (length > 0) {
			result.append(".");
			result.append(qualifier);
		}

		Assert.assertEquals(expectedVersion, result.toString());
	}

	private BlockingInvocationHandler _block() {
		BlockingInvocationHandler blockingInvocationHandler =
			new BlockingInvocationHandler(
				_fallbackClassLoaders.get(_SYMBOLIC_NAME));

		_fallbackClassLoaders.put(
			_SYMBOLIC_NAME,
			(ConcurrentNavigableMap<Object, ClassLoader>)Proxy.newProxyInstance(
				ClassLoaderPoolTest.class.getClassLoader(),
				new Class<?>[] {ConcurrentNavigableMap.class},
				blockingInvocationHandler));

		return blockingInvocationHandler;
	}

	private static final String _CONTEXT_NAME_1;

	private static final String _CONTEXT_NAME_2;

	private static final String _CONTEXT_NAME_3;

	private static final String _CONTEXT_NAME_4;

	private static final String _CONTEXT_NAME_WITHOUT_VERSION = "contextName";

	private static final String _SYMBOLIC_NAME = "symbolic.name";

	private static Map<String, ClassLoader> _classLoaders;
	private static ClassLoader _contextClassLoader;
	private static Map<ClassLoader, String> _contextNames;
	private static Map<String, ConcurrentNavigableMap<Object, ClassLoader>>
		_fallbackClassLoaders;
	private static Constructor<?> _versionConstructor;

	static {
		_CONTEXT_NAME_1 = _SYMBOLIC_NAME + "_1.0.0";

		_CONTEXT_NAME_2 = _SYMBOLIC_NAME + "_2.0.0";

		_CONTEXT_NAME_3 = _SYMBOLIC_NAME + "_3.0.0";

		_CONTEXT_NAME_4 = _SYMBOLIC_NAME + "_4.0.0";
	}

	private class BlockingInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("lastEntry")) {
				_waitCountDownLatch.countDown();

				_blockCountDownLatch.await();
			}

			return method.invoke(_concurrentNavigableMap, args);
		}

		public void unblock() {
			_blockCountDownLatch.countDown();
		}

		public void waitUntilBlock() throws InterruptedException {
			_waitCountDownLatch.await();
		}

		private BlockingInvocationHandler(
			ConcurrentNavigableMap<Object, ClassLoader>
				concurrentNavigableMap) {

			_concurrentNavigableMap = concurrentNavigableMap;
		}

		private final CountDownLatch _blockCountDownLatch = new CountDownLatch(
			1);
		private final ConcurrentNavigableMap<Object, ClassLoader>
			_concurrentNavigableMap;
		private final CountDownLatch _waitCountDownLatch = new CountDownLatch(
			1);

	}

}