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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ServiceContextAdviceTest {

	@Before
	public void setUp() {
		_testServiceBeanAopCacheManager = new TestServiceBeanAopCacheManager();

		_serviceContextAdvice = new ServiceContextAdvice();

		ReflectionTestUtil.setFieldValue(
			_serviceContextAdvice, "serviceBeanAopCacheManager",
			_testServiceBeanAopCacheManager);

		_serviceContext = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testThreadLocalValue() throws Throwable {
		MethodInvocation methodInvocation = createMethodInvocation(
			new Object[] {new ServiceContext()},
			new Class<?>[] {ServiceContext.class}, false);

		_serviceContextAdvice.invoke(methodInvocation);

		Assert.assertFalse(
			_testServiceBeanAopCacheManager.isRemovedMethodInterceptor());
	}

	@Test
	public void testWithNoArguments() throws Throwable {
		MethodInvocation methodInvocation = createMethodInvocation(
			new Object[0], new Class<?>[0], true);

		_serviceContextAdvice.invoke(methodInvocation);

		Assert.assertTrue(
			_testServiceBeanAopCacheManager.isRemovedMethodInterceptor());
	}

	@Test
	public void testWithNullServiceContextArgument() throws Throwable {
		MethodInvocation methodInvocation = createMethodInvocation(
			new Object[0], new Class<?>[] {ServiceContext.class}, true);

		_serviceContextAdvice.invoke(methodInvocation);

		Assert.assertFalse(
			_testServiceBeanAopCacheManager.isRemovedMethodInterceptor());
	}

	@Test
	public void testWithoutServiceContextParameter() throws Throwable {
		MethodInvocation methodInvocation = createMethodInvocation(
			new Object[] {null}, new Class<?>[] {Object.class}, true);

		_serviceContextAdvice.invoke(methodInvocation);

		Assert.assertTrue(
			_testServiceBeanAopCacheManager.isRemovedMethodInterceptor());
	}

	@Test
	public void testWithServiceContextWrapper() throws Throwable {
		MethodInvocation methodInvocation = createMethodInvocation(
			new Object[] {new TestServiceContextWrapper()},
			new Class<?>[] {TestServiceContextWrapper.class}, false);

		_serviceContextAdvice.invoke(methodInvocation);

		Assert.assertFalse(
			_testServiceBeanAopCacheManager.isRemovedMethodInterceptor());
	}

	protected MethodInvocation createMethodInvocation(
			final Object[] arguments, Class<?>[] parameterTypes,
			final boolean expectedOriginalServiceContext)
		throws Throwable {

		final Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", parameterTypes);

		return new MethodInvocation() {

			@Override
			public Method getMethod() {
				return method;
			}

			@Override
			public Object[] getArguments() {
				return arguments;
			}

			@Override
			public Object proceed() throws Throwable {
				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				Assert.assertEquals(
					expectedOriginalServiceContext,
					_serviceContext == serviceContext);

				return null;
			}

			@Override
			public Object getThis() {
				throw new UnsupportedOperationException();
			}

			@Override
			public AccessibleObject getStaticPart() {
				throw new UnsupportedOperationException();
			}

		};
	}

	private ServiceContext _serviceContext;
	private ServiceContextAdvice _serviceContextAdvice;
	private TestServiceBeanAopCacheManager _testServiceBeanAopCacheManager;

	private static class TestInterceptedClass {

		@SuppressWarnings("unused")
		public void method() {
		}

		@SuppressWarnings("unused")
		public void method(Object obj) {
		}

		@SuppressWarnings("unused")
		public void method(ServiceContext serviceContext) {
		}

		@SuppressWarnings("unused")
		public void method(TestServiceContextWrapper serviceContextWrapper) {
		}

	}

	private static class TestServiceBeanAopCacheManager
		extends ServiceBeanAopCacheManager {

		public boolean isRemovedMethodInterceptor() {
			return _removedMethodInterceptor;
		}

		@Override
		public void removeMethodInterceptor(
			MethodInvocation methodInvocation,
			MethodInterceptor methodInterceptor) {

			_removedMethodInterceptor = true;
		}

		private boolean _removedMethodInterceptor;

	}

	private static class TestServiceContextWrapper extends ServiceContext {
	}

}