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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Assert;

/**
 * @author Miguel Pastor
 */
public class ReflectionUtilTest extends TestCase {

	public void testFindDeclaredAnnotationInClass() {
		Assert.assertTrue(
			ReflectionUtil.isAnnotationDeclared(TestAnnotation.class, A.class));
	}

	public void testFindDeclaredAnnotationInMethod()
		throws NoSuchMethodException {

		B b = new B();

		Method method = b.getClass().getMethod("methodB");

		Assert.assertTrue(
			ReflectionUtil.isAnnotationDeclared(TestAnnotation.class, method));
	}

	public void testFindDerivedAnnotationInClass() {
		Assert.assertTrue(
			ReflectionUtil.isAnnotationDeclared(TestAnnotation.class, B.class));
	}

	public void testNoDerivedAnnotationInMethod() throws NoSuchMethodException {
		C c = new C();

		Method method = c.getClass().getDeclaredMethod("methodA");

		Assert.assertFalse(
			ReflectionUtil.isAnnotationDeclared(TestAnnotation.class, method));
	}

	@Documented
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@interface TestAnnotation {

	}

	@TestAnnotation
	private class A {
		@TestAnnotation
		public void methodA() {
		}

	}

	private class B extends A {
		@TestAnnotation
		public void methodB() {
		}

	}

	private class C extends B {
		@Override
		public void methodA() {
		}

		public void methodC() {
		}

	}

}