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

package com.liferay.portal.kernel.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class NewClassLoaderJUnitTestRunnerTest {

	@Before
	public void setUp() {
		Assert.assertEquals(0, _counter.getAndIncrement());
		Assert.assertNull(_classLoader);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Assert.assertSame(classLoader, contextClassLoader);

		_classLoader = classLoader;
	}

	@After
	public void tearDown() {
		Assert.assertEquals(2, _counter.getAndIncrement());

		assertClassLoader();
	}

	@Test
	public void testClassInitialization1() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertClassLoader();

		String value1 = "value1";

		System.setProperty(_PROPERTY_KEY, value1);

		Assert.assertEquals(value1, ValueClass.value);
	}

	@Test
	public void testClassInitialization2() {
		Assert.assertEquals(1, _counter.getAndIncrement());

		assertClassLoader();

		String value2 = "value2";

		System.setProperty(_PROPERTY_KEY, value2);

		Assert.assertEquals(value2, ValueClass.value);
	}

	private void assertClassLoader() {
		Assert.assertNotNull(_classLoader);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Assert.assertSame(_classLoader, classLoader);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Assert.assertSame(_classLoader, contextClassLoader);
	}

	private static final String _PROPERTY_KEY = "PROPERTY_KEY";

	private ClassLoader _classLoader;
	private final AtomicInteger _counter = new AtomicInteger();

	private static class ValueClass {

		public static String value = System.getProperty(_PROPERTY_KEY);

	}

}