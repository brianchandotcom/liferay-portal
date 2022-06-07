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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ProxyUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.clear();

					for (Class<?> clazz :
							ProxyUtil.class.getDeclaredClasses()) {

						if (InvocationHandler.class.isAssignableFrom(clazz)) {
							assertClasses.add(clazz);

							break;
						}
					}
				}

			},
			NewEnvTestRule.INSTANCE);

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testDelegateInvocationHandlerInitializationFailure()
		throws ClassNotFoundException {

		NoSuchMethodException noSuchMethodException =
			new NoSuchMethodException();

		AtomicInteger counter = new AtomicInteger();

		try (SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkPackageAccess(String pkg) {
						if (pkg.equals("java.lang") &&
							(counter.getAndIncrement() == 18)) {

							ReflectionUtil.throwException(
								noSuchMethodException);
						}
					}

				}) {

			swappableSecurityManager.install();

			String name = ProxyUtil.class.getName();

			Class.forName(name.concat("$DelegateInvocationHandler"));
		}
		catch (ExceptionInInitializerError exceptionInInitializerError) {
			Assert.assertSame(
				noSuchMethodException, exceptionInInitializerError.getCause());
		}
	}

	@Test
	public void testNewDelegateProxyInstance() {
		TestInterface defaultTestInterface = new TestInterface() {

			@Override
			public String method1() {
				return "defaultMethod1";
			}

			@Override
			public String method2() {
				return "defaultMethod2";
			}

			@Override
			public String toString() {
				return "Default Object";
			}

		};

		Object identityObject = new Object();

		TestInterface testInterface = ProxyUtil.newDelegateProxyInstance(
			TestInterface.class.getClassLoader(), TestInterface.class,
			new DelegateClass(identityObject), defaultTestInterface);

		Assert.assertEquals("delegateMethod1", testInterface.method1());
		Assert.assertEquals("defaultMethod2", testInterface.method2());
		Assert.assertEquals("Delegate Object", testInterface.toString());
		Assert.assertEquals(testInterface, identityObject);
		Assert.assertNotEquals(testInterface, defaultTestInterface);
		Assert.assertEquals(
			testInterface.hashCode(), identityObject.hashCode());
	}

	private static class DelegateClass {

		public static void irrelevantMethod4() {
		}

		public void equals(Long number) {
		}

		public void equals(Long number1, Long number2) {
		}

		@Override
		public boolean equals(Object object) {
			if (object == _identityObject) {
				return true;
			}

			return false;
		}

		public void equals(Object object1, Object object2) {
		}

		@Override
		public int hashCode() {
			return System.identityHashCode(_identityObject);
		}

		public int hashCode(Object object) {
			return object.hashCode();
		}

		public void irrelevantMethod1() {
		}

		public String method1() {
			return "delegateMethod1";
		}

		@Override
		public String toString() {
			return "Delegate Object";
		}

		public String toString(Object object) {
			return object.toString();
		}

		protected void irrelevantMethod2() {
		}

		private DelegateClass(Object identityObject) {
			_identityObject = identityObject;

			_irrelevantMethod3();
		}

		private void _irrelevantMethod3() {
		}

		private final Object _identityObject;

	}

	private interface TestInterface {

		public String method1();

		public String method2();

	}

}