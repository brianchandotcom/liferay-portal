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

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.lang.reflect.InvocationHandler;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ProxyUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.clear();

				for (Class<?> clazz : ProxyUtil.class.getDeclaredClasses()) {
					if (InvocationHandler.class.isAssignableFrom(clazz)) {
						assertClasses.add(clazz);

						break;
					}
				}
			}

		};

	@Test
	public void testNewDelegateProxyInstance() {
		TestInterface testInterface = ProxyUtil.newDelegateProxyInstance(
			TestInterface.class.getClassLoader(), TestInterface.class,
			new Object() {

				public String method1() {
					return "delegateMethod1";
				}

			},
			new TestInterface() {

				@Override
				public String method1() {
					return "defaultMethod1";
				}

				@Override
				public String method2() {
					return "defaultMethod2";
				}

			});

		Assert.assertEquals("delegateMethod1", testInterface.method1());
		Assert.assertEquals("defaultMethod2", testInterface.method2());
	}

	private interface TestInterface {

		public String method1();

		public String method2();

	}

}