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

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ServiceContextAdviceTest {

	@Before
	public void setUp() {
		_serviceContextAdvice = new ServiceContextAdvice();

		_serviceContext = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testThreadLocalValue() throws Throwable {
		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", ServiceContext.class);

		Assert.assertTrue(
			_serviceContextAdvice.isEnabled(
				TestInterceptedClass.class, method));

		ServiceContext serviceContext = new ServiceContext();

		TestMethodInvocation testMethodInvocation = new TestMethodInvocation(
			method, serviceContext);

		_serviceContextAdvice.invoke(testMethodInvocation);

		Assert.assertSame(
			serviceContext, testMethodInvocation.getServiceContext());
	}

	@Test
	public void testWithNoArguments() throws Throwable {
		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method");

		Assert.assertFalse(
			_serviceContextAdvice.isEnabled(
				TestInterceptedClass.class, method));
	}

	@Test
	public void testWithoutServiceContextParameter() throws Throwable {
		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", Object.class);

		Assert.assertFalse(
			_serviceContextAdvice.isEnabled(
				TestInterceptedClass.class, method));
	}

	@Test
	public void testWithServiceContextWrapper() throws Throwable {
		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method",
			TestServiceContextWrapper.class);

		Assert.assertTrue(
			_serviceContextAdvice.isEnabled(
				TestInterceptedClass.class, method));

		ServiceContext serviceContext = new TestServiceContextWrapper();

		TestMethodInvocation testMethodInvocation = new TestMethodInvocation(
			method, serviceContext);

		_serviceContextAdvice.invoke(testMethodInvocation);

		Assert.assertSame(
			serviceContext, testMethodInvocation.getServiceContext());
	}

	private ServiceContext _serviceContext;
	private ServiceContextAdvice _serviceContextAdvice;

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

	private static class TestMethodInvocation
		extends ServiceBeanMethodInvocation {

		@Override
		public Object[] getArguments() {
			return _arguments;
		}

		@Override
		public Method getMethod() {
			return _method;
		}

		public ServiceContext getServiceContext() {
			return _serviceContext;
		}

		@Override
		public Object getThis() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object proceed() throws Throwable {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			_serviceContext = serviceContext;

			return null;
		}

		private TestMethodInvocation(Method method, Object... arguments) {
			super(null, null);

			_method = method;
			_arguments = arguments;
		}

		private final Object[] _arguments;
		private final Method _method;
		private ServiceContext _serviceContext;

	}

	private static class TestServiceContextWrapper extends ServiceContext {
	}

}