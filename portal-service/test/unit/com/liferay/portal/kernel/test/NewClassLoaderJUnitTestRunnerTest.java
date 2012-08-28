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

package com.liferay.portal.kernel.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class NewClassLoaderJUnitTestRunnerTest {

	@After
	public void after() {
		assertEquals(2, _stepCounter.getAndIncrement());

		_assertClassLoader();
	}

	@Before
	public void before() {
		assertEquals(0, _stepCounter.getAndIncrement());

		assertNull(_classLoader);

		ClassLoader defineClassLoader = getClass().getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		assertSame(defineClassLoader, contextClassLoader);

		_classLoader = defineClassLoader;
	}

	@Test
	public void testClassInitialization1() {
		assertEquals(1, _stepCounter.getAndIncrement());

		_assertClassLoader();

		String value1 = "value1";

		System.setProperty(PROPERTY_KEY, value1);

		assertEquals(value1, ValueClass.value);
	}

	@Test
	public void testClassInitialization2() {
		assertEquals(1, _stepCounter.getAndIncrement());

		_assertClassLoader();

		String value2 = "value2";

		System.setProperty(PROPERTY_KEY, value2);

		assertEquals(value2, ValueClass.value);
	}

	private void _assertClassLoader() {
		assertNotNull(_classLoader);

		ClassLoader defineClassLoader = getClass().getClassLoader();

		assertSame(_classLoader, defineClassLoader);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		assertSame(_classLoader, contextClassLoader);
	}

	private static final String PROPERTY_KEY = "PROPERTY_KEY";

	private ClassLoader _classLoader;
	private AtomicInteger _stepCounter = new AtomicInteger();

	private static class ValueClass {

		public static String value = System.getProperty(PROPERTY_KEY);

	}

}