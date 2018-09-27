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
		_assertMethodKey(
			TestClass.class, "testMethod", new Class<?>[0],
			new MethodKey(TestClass.class, "testMethod"));
		_assertMethodKey(
			TestClass.class, "testMethod", new Class<?>[] {String.class},
			new MethodKey(TestClass.class, "testMethod", String.class));
		_assertMethodKey(
			TestClass.class, "testMethod",
			new Class<?>[] {String.class, String.class},
			new MethodKey(
				TestClass.class, "testMethod", String.class, String.class));
		_assertMethodKey(
			TestClass.class, "testMethod", new Class<?>[0],
			new MethodKey(TestClass.class.getMethod("testMethod")));
		_assertMethodKey(
			TestClass.class, "testMethod", new Class<?>[0],
			new MethodKey(TestClass.class.getName(), "testMethod"));

		try {
			new MethodKey("ClassNotFound", "testMethod");

			Assert.fail("No RuntimeException thrown!");
		}
		catch (RuntimeException re) {
			Assert.assertTrue(
				"ClassNotFoundException is expected when creating MethodKey " +
					"with invalid class name",
				re.getCause() instanceof ClassNotFoundException);
		}
	}

	@Test
	public void testEquals() {
		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		Assert.assertEquals(methodKey, methodKey);
		Assert.assertNotEquals(methodKey, new Object());
		Assert.assertEquals(
			methodKey,
			new MethodKey(TestClass.class, "testMethod", String.class));
		Assert.assertNotEquals(
			methodKey,
			new MethodKey(
				TestClass.class, "testMethod", String.class, String.class));
		Assert.assertNotEquals(
			methodKey, new MethodKey(TestClass.class, "testMethod", int.class));
		Assert.assertNotEquals(
			methodKey,
			new MethodKey(TestClass.class, "testMethodAnother", String.class));
		Assert.assertNotEquals(
			methodKey, new MethodKey(Object.class, "testMethod", String.class));
	}

	@Test
	public void testGetMethodAndResetCache() throws NoSuchMethodException {
		Map<MethodKey, Method> methods = ReflectionTestUtil.getFieldValue(
			MethodKey.class, "_methods");

		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		// Test 1, MethodKey.getMethod returns and caches the method

		Method actualMethod1 = methodKey.getMethod();

		Assert.assertEquals(
			TestClass.class.getMethod("testMethod", String.class),
			actualMethod1);
		Assert.assertTrue(
			"The method obtained from MethodKey should be accessible",
			actualMethod1.isAccessible());

		Assert.assertEquals(methods.toString(), 1, methods.size());

		// Test 2, method is retrieved from cache and set accessible

		actualMethod1.setAccessible(false);

		Method actualMethod2 = methodKey.getMethod();

		Assert.assertSame(actualMethod2, actualMethod1);
		Assert.assertTrue(
			"The method obtained from MethodKey should be accessible",
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
		MethodKey methodKey = new MethodKey(TestClass.class, "testMethod");

		Assert.assertEquals(
			TestClass.class.getName() + ".testMethod()", methodKey.toString());

		methodKey = new MethodKey(TestClass.class, "testMethod", String.class);

		Assert.assertEquals(
			TestClass.class.getName() + ".testMethod(java.lang.String)",
			methodKey.toString());

		methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class, String.class);

		Assert.assertEquals(
			TestClass.class.getName() +
				".testMethod(java.lang.String,java.lang.String)",
			methodKey.toString());
		Assert.assertEquals(
			TestClass.class.getName() +
				".testMethod(java.lang.String,java.lang.String)",
			ReflectionTestUtil.getFieldValue(methodKey, "_toString"));

		ReflectionTestUtil.setFieldValue(methodKey, "_toString", "testString");

		Assert.assertEquals("testString", methodKey.toString());
	}

	@Test
	public void testTransform() throws Exception {
		MethodKey methodKey = new MethodKey(
			TestClass.class, "testMethod", String.class);

		MethodKey transformedMethodKey = methodKey.transform(
			new URLClassLoader(
				ClassPathUtil.getClassPathURLs(
					ClassPathUtil.getJVMClassPath(true)),
				null));

		Assert.assertNotEquals(methodKey, transformedMethodKey);

		Class<?> transformedClass = transformedMethodKey.getDeclaringClass();

		Constructor<?> transformedClassConstructor =
			transformedClass.getDeclaredConstructor();

		transformedClassConstructor.setAccessible(true);

		Object transformedClassInstance =
			transformedClassConstructor.newInstance();

		Method method = methodKey.getMethod();

		try {
			method.invoke(transformedClassInstance, "test");

			Assert.fail("No IllegalArgumentException thrown!");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"object is not an instance of declaring class",
				iae.getMessage());
		}

		Method transformedMethod = transformedMethodKey.getMethod();

		Assert.assertEquals(
			"test", transformedMethod.invoke(transformedClassInstance, "test"));
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

	private void _assertMethodKey(
		Class<?> expectedClass, String expectedMethodName,
		Class<?>[] expectedParameters, MethodKey methodKey) {

		Assert.assertSame(expectedClass, methodKey.getDeclaringClass());
		Assert.assertEquals(expectedMethodName, methodKey.getMethodName());
		Assert.assertArrayEquals(
			expectedParameters, methodKey.getParameterTypes());
	}

	private static class TestClass {

		public void testMethod() {
		}

		public void testMethod(int parameter) {
		}

		public String testMethod(String parameter) {
			return parameter;
		}

		public void testMethod(String parameter1, String parameter2) {
		}

		public void testMethodAnother(String parameter) {
		}

	}

}