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

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.process.ClassPathUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.net.URLClassLoader;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class MethodKeyTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructors() throws NoSuchMethodException {
		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		Assert.assertEquals(TestClass.class, methodKey.getDeclaringClass());
		Assert.assertEquals("testMethod", methodKey.getMethodName());
		Assert.assertEquals(String.class, methodKey.getParameterTypes()[0]);

		methodKey = new MethodKey();

		Assert.assertNull(methodKey.getDeclaringClass());
		Assert.assertNull(methodKey.getMethodName());
		Assert.assertNull(methodKey.getParameterTypes());

		Method method = TestClass.class.getMethod("testMethod", String.class);

		methodKey = new MethodKey(method);

		Assert.assertEquals(TestClass.class, methodKey.getDeclaringClass());
		Assert.assertEquals("testMethod", methodKey.getMethodName());
		Assert.assertEquals(String.class, methodKey.getParameterTypes()[0]);

		methodKey = new MethodKey(
			TestClass.class.getName(), "testMethod", String.class);

		Assert.assertEquals(TestClass.class, methodKey.getDeclaringClass());
		Assert.assertEquals("testMethod", methodKey.getMethodName());
		Assert.assertEquals(String.class, methodKey.getParameterTypes()[0]);

		try {
			new MethodKey("ClassNotFound", "testMethod", String.class);

			Assert.fail("No RuntimeException thrown!");
		}
		catch (RuntimeException re) {
			Assert.assertTrue(
				"The cause is not ClassNotFoundException",
				re.getCause() instanceof ClassNotFoundException);
		}
	}

	@Test
	public void testEquals() {
		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		Assert.assertEquals(methodKey, methodKey);

		Object object = new Object();

		Assert.assertNotEquals(methodKey, object);

		MethodKey anotherMethodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		Assert.assertEquals(methodKey, anotherMethodKey);

		anotherMethodKey = new MethodKey(
			TestClass.class, "testMethod", int.class);

		Assert.assertNotEquals(methodKey, anotherMethodKey);

		anotherMethodKey = new MethodKey(
			TestClass.class, "anotherTestMethod", String.class);

		Assert.assertNotEquals(methodKey, anotherMethodKey);

		anotherMethodKey = new MethodKey(
			Object.class, "testMethod", String.class);

		Assert.assertNotEquals(methodKey, anotherMethodKey);
	}

	@Test
	public void testGetMethodAndResetCache() throws NoSuchMethodException {
		Map<MethodKey, Method> methods = ReflectionTestUtil.getFieldValue(
			MethodKey.class, "_methods");

		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		// Test 1, MethodKey.getMethod returns and caches the method

		Method expectedMethod = TestClass.class.getMethod(
			"testMethod", String.class);

		Method actualMethod1 = methodKey.getMethod();

		Assert.assertEquals(expectedMethod, actualMethod1);
		Assert.assertTrue(
			"The method obtained from MethodKey is not accessible",
			actualMethod1.isAccessible());

		Assert.assertEquals(methods.toString(), 1, methods.size());

		// Test 2, method is retrieved from cache and set accessible

		actualMethod1.setAccessible(false);

		Method actualMethod2 = methodKey.getMethod();

		Assert.assertSame(actualMethod2, actualMethod1);
		Assert.assertTrue(
			"The method obtained from MethodKey is not accessible",
			actualMethod2.isAccessible());

		Assert.assertEquals(methods.toString(), 1, methods.size());

		// Test 3, resetCache() clears the method cache

		MethodKey.resetCache();

		Assert.assertEquals(methods.toString(), 0, methods.size());
	}

	@Test
	public void testHashCode() throws NoSuchMethodException {
		Method method = TestClass.class.getMethod("testMethod", String.class);

		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		Assert.assertEquals(method.hashCode(), methodKey.hashCode());
	}

	@Test
	public void testToString() {
		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		String expectedToString = StringBundler.concat(
			TestClass.class.getName(), ".testMethod(", String.class.getName(),
			")");

		Assert.assertEquals(expectedToString, methodKey.toString());
		Assert.assertEquals(
			expectedToString,
			ReflectionTestUtil.getFieldValue(methodKey, "_toString"));

		ReflectionTestUtil.setFieldValue(methodKey, "_toString", "testString");

		Assert.assertEquals("testString", methodKey.toString());
	}

	@Test
	public void testTransform() throws Exception {
		ClassLoader newClassLoader = new URLClassLoader(
			ClassPathUtil.getClassPathURLs(ClassPathUtil.getJVMClassPath(true)),
			null);

		Class<?> methodKeyTestClazz = newClassLoader.loadClass(
			"com.liferay.portal.kernel.util.MethodKeyTest");
		Class<?> testClassClazz = newClassLoader.loadClass(
			"com.liferay.portal.kernel.util.MethodKeyTest$TestClass");

		Constructor constructor = testClassClazz.getDeclaredConstructor(
			methodKeyTestClazz);

		constructor.setAccessible(true);

		Object testClassInstance = constructor.newInstance(
			methodKeyTestClazz.newInstance());

		MethodKey originalMethodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		Method method = originalMethodKey.getMethod();

		try {
			method.invoke(testClassInstance, "test");

			Assert.fail("No IllegalArgumentException thrown!");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"object is not an instance of declaring class",
				iae.getMessage());
		}

		MethodKey transformedMethodKey = originalMethodKey.transform(
			newClassLoader);

		method = transformedMethodKey.getMethod();

		Assert.assertEquals("test", method.invoke(testClassInstance, "test"));
	}

	@Test
	public void testWriteAndReadExternal()
		throws ClassNotFoundException, IOException {

		MethodKey originalMethodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		MethodKey deserializedMethodKey = new MethodKey();

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					unsyncByteArrayOutputStream)) {

				originalMethodKey.writeExternal(objectOutputStream);
			}

			try (UnsyncByteArrayInputStream unsyncByteArrayInputStream =
					new UnsyncByteArrayInputStream(
						unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
						unsyncByteArrayOutputStream.size());
				ObjectInputStream objectInputStream = new ObjectInputStream(
					unsyncByteArrayInputStream)) {

				deserializedMethodKey.readExternal(objectInputStream);
			}
		}

		Assert.assertEquals(originalMethodKey, deserializedMethodKey);
	}

	private class TestClass {

		public void anotherTestMethod(String parameter) {
		}

		public void testMethod(int parameter) {
		}

		public String testMethod(String parameter) {
			return parameter;
		}

	}

}