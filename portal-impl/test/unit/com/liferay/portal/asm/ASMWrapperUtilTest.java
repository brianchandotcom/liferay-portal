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

package com.liferay.portal.asm;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.aspects.ReflectionUtilAdvice;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ASMWrapperUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, AspectJNewEnvTestRule.INSTANCE);

	@Test
	public void testASMWrapper() throws Exception {
		Object asmWrapper = ASMWrapperUtil.createASMWrapper(
			ASMWrapperUtilTestInterface.class, new ASMWrapperUtilTestWrapper(),
			new ASMWrapperUtilTestDefault());

		Class<?> asmWrapperClass = asmWrapper.getClass();

		Method method = asmWrapperClass.getDeclaredMethod(
			"objectMethod", Object.class);

		Object object = new Object();

		Assert.assertNotSame(object, method.invoke(asmWrapper, object));

		method = asmWrapperClass.getDeclaredMethod("intMethod", Integer.TYPE);

		int randomInt = RandomTestUtil.randomInt();

		Assert.assertEquals(randomInt, method.invoke(asmWrapper, randomInt));
	}

	@Test
	public void testConstructor() throws Exception {
		Class clazz = ASMWrapperUtil.class;

		Constructor<ASMWrapperUtil> constructor =
			clazz.getDeclaredConstructor();

		constructor.setAccessible(true);

		constructor.newInstance();
	}

	@Test
	public void testCreateASMWrapper() throws Exception {
		Object asmWrapper = ASMWrapperUtil.createASMWrapper(
			ASMWrapperUtilTestInterface.class, new ASMWrapperUtilTestWrapper(),
			new ASMWrapperUtilTestDefault());

		Class<?> asmWrapperClass = asmWrapper.getClass();

		Assert.assertEquals(Modifier.PUBLIC, asmWrapperClass.getModifiers());
		Assert.assertEquals(
			ASMWrapperUtilTestInterface.class.getName() + "ASMWrapper",
			asmWrapperClass.getName());
		Assert.assertSame(Object.class, asmWrapperClass.getSuperclass());

		Method[] methods = asmWrapperClass.getDeclaredMethods();

		Assert.assertEquals(10, methods.length);

		Arrays.sort(
			methods,
			new Comparator<Method>() {

				@Override
				public int compare(Method method1, Method method2) {
					String name1 = method1.getName();
					String name2 = method2.getName();

					return name1.compareTo(name2);
				}

			});

		Method method = methods[0];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Boolean.TYPE, method.getReturnType());
		Assert.assertEquals("booleanMethod", method.getName());

		Class<?>[] parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Boolean.TYPE, parameterTypes[0]);

		method = methods[1];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Byte.TYPE, method.getReturnType());
		Assert.assertEquals("byteMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Byte.TYPE, parameterTypes[0]);

		method = methods[2];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Character.TYPE, method.getReturnType());
		Assert.assertEquals("charMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Character.TYPE, parameterTypes[0]);

		method = methods[3];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Double.TYPE, method.getReturnType());
		Assert.assertEquals("doubleMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Double.TYPE, parameterTypes[0]);

		method = methods[4];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Float.TYPE, method.getReturnType());
		Assert.assertEquals("floatMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Float.TYPE, parameterTypes[0]);

		method = methods[5];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Integer.TYPE, method.getReturnType());
		Assert.assertEquals("intMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Integer.TYPE, parameterTypes[0]);

		method = methods[6];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Long.TYPE, method.getReturnType());
		Assert.assertEquals("longMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Long.TYPE, parameterTypes[0]);

		method = methods[7];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Object.class, method.getReturnType());
		Assert.assertEquals("objectMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Object.class, parameterTypes[0]);

		method = methods[8];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Short.TYPE, method.getReturnType());
		Assert.assertEquals("shortMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(1, parameterTypes.length);
		Assert.assertEquals(Short.TYPE, parameterTypes[0]);

		method = methods[9];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Void.TYPE, method.getReturnType());
		Assert.assertEquals("voidWithExceptionMethod", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(0, parameterTypes.length);

		Class<?>[] exceptionTypes = method.getExceptionTypes();

		Assert.assertEquals(1, exceptionTypes.length);
		Assert.assertEquals(Exception.class, exceptionTypes[0]);
	}

	@AdviseWith(adviceClasses = {ReflectionUtilAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testErrorCreateASMWrapper() throws Exception {
		try {
			ASMWrapperUtil.createASMWrapper(
				Object.class, new Object(), Object.class);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				Object.class + " is not an interface", iae.getMessage());
		}

		Throwable throwable = new Throwable();

		ReflectionUtilAdvice.setDeclaredMethodThrowable(throwable);

		try {
			ASMWrapperUtil.createASMWrapper(
				ASMWrapperUtilTestInterface.class,
				new ASMWrapperUtilTestWrapper(),
				new ASMWrapperUtilTestDefault());

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertSame(throwable, re.getCause());
		}

		ReflectionUtilAdvice.setDeclaredMethodThrowable(null);
	}

	public static class ASMWrapperUtilTestDefault
		implements ASMWrapperUtilTestInterface {

		@Override
		public boolean booleanMethod(boolean booleanArg) {
			return booleanArg;
		}

		@Override
		public byte byteMethod(byte byteArg) {
			return byteArg;
		}

		@Override
		public char charMethod(char charArg) {
			return charArg;
		}

		@Override
		public double doubleMethod(double doubleArg) {
			return doubleArg;
		}

		@Override
		public float floatMethod(float floatArg) {
			return floatArg;
		}

		@Override
		public int intMethod(int intArg) {
			return intArg;
		}

		@Override
		public long longMethod(long longArg) {
			return longArg;
		}

		@Override
		public Object objectMethod(Object objectArg) {
			return objectArg;
		}

		@Override
		public short shortMethod(short shortArg) {
			return shortArg;
		}

		@Override
		public void voidWithExceptionMethod() throws Exception {
		}

	}

	public static class ASMWrapperUtilTestWrapper {

		public Object objectMethod(Object object) {
			return new Object();
		}

	}

	public interface ASMWrapperUtilTestInterface {

		public boolean booleanMethod(boolean booleanArg);

		public byte byteMethod(byte byteArg);

		public char charMethod(char charArg);

		public double doubleMethod(double doubleArg);

		public float floatMethod(float floatArg);

		public int intMethod(int intArg);

		public long longMethod(long longArg);

		public Object objectMethod(Object objectArg);

		public short shortMethod(short shortArg);

		public void voidWithExceptionMethod() throws Exception;

	}

}