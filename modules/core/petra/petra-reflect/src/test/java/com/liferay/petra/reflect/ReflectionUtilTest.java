/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.Permission;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ReflectionUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testClassInitializationFailure() throws Exception {
		SecurityException securityException = new SecurityException();

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkPermission(Permission permission) {
						if (Objects.equals(
								permission.getName(),
								"accessDeclaredMembers")) {

							throw securityException;
						}
					}

				}) {

			swappableSecurityManager.install();

			Class.forName(ReflectionUtil.class.getName());

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(securityException, eiie.getCause());
		}
	}

	@Test
	public void testConstructor() {
		new ReflectionUtil();
	}

	@Test
	public void testGetDeclaredField() throws Exception {
		Field staticField = ReflectionUtil.getDeclaredField(
			TestClass.class, "_privateStaticFinalObject");

		Assert.assertTrue(staticField.isAccessible());
		Assert.assertSame(
			TestClass._privateStaticFinalObject, staticField.get(null));

		TestClass testClass = new TestClass();

		Field field = ReflectionUtil.getDeclaredField(
			TestClass.class, "_privateFinalObject");

		Assert.assertTrue(field.isAccessible());
		Assert.assertTrue(Modifier.isFinal(field.getModifiers()));
		Assert.assertSame(testClass._privateFinalObject, field.get(testClass));
	}

	@Test
	public void testGetDeclaredFields() throws Exception {
		Field[] fields = ReflectionUtil.getDeclaredFields(TestClass.class);

		for (Field field : fields) {
			Assert.assertTrue(field.isAccessible());

			String name = field.getName();

			if (name.equals("_privateStaticFinalObject")) {
				Assert.assertSame(
					TestClass._privateStaticFinalObject, field.get(null));
			}
			else if (name.equals("_privateStaticObject")) {
				Assert.assertSame(
					TestClass._privateStaticObject, field.get(null));
			}
		}
	}

	@Test
	public void testGetDeclaredMethod() throws Exception {
		Method method = ReflectionUtil.getDeclaredMethod(
			TestClass.class, "_getPrivateStaticObject");

		Assert.assertTrue(method.isAccessible());
		Assert.assertSame(TestClass._privateStaticObject, method.invoke(null));
	}

	@Test
	public void testGetImplLookup() {
		Assert.assertNotNull(ReflectionUtil.getImplLookup());
	}

	@Test
	public void testGetInterfaces() {
		Class<?>[] interfaces = ReflectionUtil.getInterfaces(new TestClass());

		Assert.assertEquals(Arrays.toString(interfaces), 1, interfaces.length);
		Assert.assertSame(TestInterface.class, interfaces[0]);

		AtomicReference<ClassNotFoundException> atomicReference =
			new AtomicReference<>();

		interfaces = ReflectionUtil.getInterfaces(
			new TestClass(), new URLClassLoader(new URL[0], null));

		Assert.assertEquals(Arrays.toString(interfaces), 0, interfaces.length);

		interfaces = ReflectionUtil.getInterfaces(
			new TestClass(), new URLClassLoader(new URL[0], null),
			atomicReference::set);

		Assert.assertEquals(Arrays.toString(interfaces), 0, interfaces.length);

		Assert.assertNotNull(atomicReference.get());
	}

	@Test
	public void testThrowException() {
		Exception exception1 = new Exception();

		try {
			ReflectionUtil.throwException(exception1);

			Assert.fail();
		}
		catch (Exception exception2) {
			Assert.assertSame(exception1, exception2);
		}
	}

	private static class TestClass implements TestInterface {

		public static void setPrivateStaticObject(Object privateStaticObject) {
			_privateStaticObject = privateStaticObject;
		}

		public void setPrivateObject(Object privateObject) {
			_privateObject = privateObject;
		}

		@SuppressWarnings("unused")
		private static Object _getPrivateStaticObject() {
			return _privateStaticObject;
		}

		@SuppressWarnings("unused")
		private Object _getPrivateObject() {
			return _privateObject;
		}

		private static final Object _privateStaticFinalObject = new Object();
		private static Object _privateStaticObject = new Object();

		private final Object _privateFinalObject = new Object();
		private Object _privateObject = new Object();

	}

	private interface TestInterface {
	}

}