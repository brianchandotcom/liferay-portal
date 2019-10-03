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

package com.liferay.portal.bean;

import com.liferay.petra.process.ClassPathUtil;
import com.liferay.portal.kernel.test.FinalizeManagerUtil;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.aspects.ReflectionUtilAdvice;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConstantsBeanFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@AdviseWith(adviceClasses = ReflectionUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testClassInitializationFailure() throws Exception {
		Throwable throwable = new Throwable();

		ReflectionUtilAdvice.setDeclaredMethodThrowable(throwable);

		try {
			Class.forName(ConstantsBeanFactoryImpl.class.getName());

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(throwable, eiie.getCause());
		}
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testCreateConstantsBean() throws Exception {

		// Exception on create

		Method defineClassMethod = ReflectionTestUtil.getAndSetFieldValue(
			ConstantsBeanFactoryImpl.class, "_defineClassMethod", null);

		try {
			ConstantsBeanFactoryImpl.createConstantsBean(Constants.class);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				ConstantsBeanFactoryImpl.class, "_defineClassMethod",
				defineClassMethod);
		}

		// Normal create

		ReflectionUtilAdvice.setDeclaredMethodThrowable(null);

		Object constantsBean = ConstantsBeanFactoryImpl.createConstantsBean(
			Constants.class);

		Class<?> constantsBeanClass = constantsBean.getClass();

		Assert.assertEquals(Modifier.PUBLIC, constantsBeanClass.getModifiers());
		Assert.assertEquals(
			Constants.class.getName() + "Bean", constantsBeanClass.getName());
		Assert.assertSame(Object.class, constantsBeanClass.getSuperclass());

		Method[] methods = constantsBeanClass.getDeclaredMethods();

		Assert.assertEquals(Arrays.toString(methods), 12, methods.length);

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

		// public boolean getBOOLEAN_VALUE();

		Method method = methods[0];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Boolean.TYPE, method.getReturnType());
		Assert.assertEquals("getBOOLEAN_VALUE", method.getName());

		Class<?>[] parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public byte getBYTE_VALUE();

		method = methods[1];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Byte.TYPE, method.getReturnType());
		Assert.assertEquals("getBYTE_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public char getCHAR_VALUE();

		method = methods[2];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Character.TYPE, method.getReturnType());
		Assert.assertEquals("getCHAR_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public double getDOUBLE_VALUE();

		method = methods[3];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Double.TYPE, method.getReturnType());
		Assert.assertEquals("getDOUBLE_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public float getFLOAT_VALUE();

		method = methods[4];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Float.TYPE, method.getReturnType());
		Assert.assertEquals("getFLOAT_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public int getINT_VALUE();

		method = methods[5];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Integer.TYPE, method.getReturnType());
		Assert.assertEquals("getINT_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public long getLONG_VALUE();

		method = methods[6];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Long.TYPE, method.getReturnType());
		Assert.assertEquals("getLONG_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public Object getOBJECT_VALUE();

		method = methods[7];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Object.class, method.getReturnType());
		Assert.assertEquals("getOBJECT_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public short getSHORT_VALUE();

		method = methods[8];

		Assert.assertEquals(Modifier.PUBLIC, method.getModifiers());
		Assert.assertSame(Short.TYPE, method.getReturnType());
		Assert.assertEquals("getSHORT_VALUE", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// public int get_Int(int)

		method = methods[9];

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC, method.getModifiers());
		Assert.assertSame(Integer.TYPE, method.getReturnType());
		Assert.assertEquals("get_Int", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 1, parameterTypes.length);
		Assert.assertSame(int.class, parameterTypes[0]);

		Assert.assertEquals(10, method.invoke(null, 10));

		// public Object get_Object(Object)

		method = methods[10];

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC, method.getModifiers());
		Assert.assertSame(Object.class, method.getReturnType());
		Assert.assertEquals("get_Object", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 1, parameterTypes.length);
		Assert.assertSame(Object.class, parameterTypes[0]);

		Object obj = new Object();

		Assert.assertSame(obj, method.invoke(null, obj));

		// public void get_Void()

		method = methods[11];

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC, method.getModifiers());
		Assert.assertSame(Void.TYPE, method.getReturnType());
		Assert.assertEquals("get_Void", method.getName());

		parameterTypes = method.getParameterTypes();

		Assert.assertEquals(
			Arrays.toString(parameterTypes), 0, parameterTypes.length);

		// Ensure reuse of cached generated class

		Object testConstantsBean2 =
			ConstantsBeanFactoryImpl.createConstantsBean(Constants.class);

		Assert.assertSame(constantsBeanClass, testConstantsBean2.getClass());
	}

	@Test
	public void testSynchronizedConstantsUpdate() throws Exception {
		Object constantsBean = ConstantsBeanFactoryImpl.createConstantsBean(
			Constants.class);

		Class<?> constantsBeanClass = constantsBean.getClass();

		Method getOBJECT_VALUEMethod = constantsBeanClass.getMethod(
			"getOBJECT_VALUE");

		Assert.assertSame(
			Constants.OBJECT_VALUE,
			getOBJECT_VALUEMethod.invoke(constantsBean));

		Object newObject = new Object();

		Constants.OBJECT_VALUE = newObject;

		Assert.assertSame(
			newObject, getOBJECT_VALUEMethod.invoke(constantsBean));
	}

	@Test
	public void testToConstantsBean() throws Exception {

		// First create

		String jvmClassPath = ClassPathUtil.getJVMClassPath(true);

		URL[] urls = null;

		try {
			urls = ClassPathUtil.getClassPathURLs(jvmClassPath);
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(murle);
		}

		ClassLoader classLoader1 = new URLClassLoader(urls, null);

		Class<?> constantsClass1 = classLoader1.loadClass(
			Constants.class.getName());

		ConstantsBeanFactoryImpl constantsBeanFactoryImpl =
			new ConstantsBeanFactoryImpl();

		Object constantsBean1 = constantsBeanFactoryImpl.getConstantsBean(
			constantsClass1);

		Class<?> constantsBeanClass1 = constantsBean1.getClass();

		Assert.assertSame(classLoader1, constantsBeanClass1.getClassLoader());

		Map<Class<?>, ?> constantsBeans =
			ConstantsBeanFactoryImpl.constantsBeans;

		Assert.assertEquals(
			constantsBeans.toString(), 1, constantsBeans.size());

		// Hit cache

		Assert.assertSame(
			constantsBean1,
			constantsBeanFactoryImpl.getConstantsBean(constantsClass1));
		Assert.assertEquals(
			constantsBeans.toString(), 1, constantsBeans.size());

		// Second create

		ClassLoader classLoader2 = new URLClassLoader(urls, null);

		Class<?> constantsClass2 = classLoader2.loadClass(
			Constants.class.getName());

		Object constantsBean2 = constantsBeanFactoryImpl.getConstantsBean(
			constantsClass2);

		Assert.assertNotSame(constantsBean1, constantsBean2);
		Assert.assertNotSame(constantsBeanClass1, constantsBean2.getClass());

		Assert.assertEquals(
			constantsBeans.toString(), 2, constantsBeans.size());

		// Hit cache

		Assert.assertSame(
			constantsBean2,
			constantsBeanFactoryImpl.getConstantsBean(constantsClass2));
		Assert.assertEquals(
			constantsBeans.toString(), 2, constantsBeans.size());

		// Weak reference release key

		classLoader1 = null;
		constantsClass1 = null;
		constantsBean1 = null;
		constantsBeanClass1 = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertSame(
			constantsBean2,
			constantsBeanFactoryImpl.getConstantsBean(constantsClass2));
		Assert.assertEquals(
			constantsBeans.toString(), 1, constantsBeans.size());

		// Weak reference release value

		constantsBean2 = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertTrue(constantsBeans.toString(), constantsBeans.isEmpty());
	}

	public static class Constants {

		public static boolean BOOLEAN_VALUE = false;

		public static byte BYTE_VALUE = 0;

		public static char CHAR_VALUE = 'a';

		public static double DOUBLE_VALUE = 0;

		public static float FLOAT_VALUE = 0;

		public static int INT_VALUE = 0;

		public static long LONG_VALUE = 0;

		public static Object OBJECT_VALUE = new Object();

		public static short SHORT_VALUE = 0;

		public static int get_Int(int i) {
			return i;
		}

		public static Object get_Object(Object obj) {
			return obj;
		}

		public static void get_Void() {
		}

		public Object NON_STATIC_VALUE = new Object();

		protected static Object NON_PUBLIC_VALUE = new Object();

	}

}