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
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.spring.aop.AopMethod;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;
import com.liferay.portal.spring.aop.ServiceBeanAopInvocationHandler;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.reflect.Method;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ServiceContextAdviceTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_serviceBeanAopInvocationHandler = ServiceBeanAopCacheManager.create(
			_testInterceptedClass,
			new ChainableMethodAdvice[] {new ServiceContextAdvice()});
	}

	@After
	public void tearDown() {
		ServiceBeanAopCacheManager.destroy(_serviceBeanAopInvocationHandler);
	}

	@Test
	public void testThreadLocalValue() throws Throwable {
		ServiceContext serviceContext1 = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext1);

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", ServiceContext.class);

		ServiceContext serviceContext2 = new ServiceContext();

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			_createTestMethodInvocation(method, serviceContext2);

		serviceBeanMethodInvocation.proceed();

		Assert.assertSame(
			serviceContext1, ServiceContextThreadLocal.popServiceContext());
	}

	@Test
	public void testWithNoArguments() {
		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method");

		AopMethod aopMethod = _serviceBeanAopInvocationHandler.getAopMethod(
			method);

		ChainableMethodAdvice[] chainableMethodAdvices =
			ReflectionTestUtil.getFieldValue(
				aopMethod, "_chainableMethodAdvices");

		Assert.assertEquals(
			Arrays.toString(chainableMethodAdvices), 0,
			chainableMethodAdvices.length);
	}

	@Test
	public void testWithNullServiceContext() throws Throwable {
		ServiceContext serviceContext = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", ServiceContext.class);

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			_createTestMethodInvocation(method, new Object[] {null});

		serviceBeanMethodInvocation.proceed();

		Assert.assertSame(
			serviceContext, ServiceContextThreadLocal.popServiceContext());
	}

	@Test
	public void testWithoutServiceContextParameter() {
		ServiceContextThreadLocal.pushServiceContext(new ServiceContext());

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", Object.class);

		AopMethod aopMethod = _serviceBeanAopInvocationHandler.getAopMethod(
			method);

		ChainableMethodAdvice[] chainableMethodAdvices =
			ReflectionTestUtil.getFieldValue(
				aopMethod, "_chainableMethodAdvices");

		Assert.assertEquals(
			Arrays.toString(chainableMethodAdvices), 0,
			chainableMethodAdvices.length);
	}

	@Test
	public void testWithServiceContextWrapper() throws Throwable {
		ServiceContext serviceContext = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method",
			TestServiceContextWrapper.class, Object.class);

		TestServiceContextWrapper testServiceContextWrapper =
			new TestServiceContextWrapper();

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			_createTestMethodInvocation(
				method, testServiceContextWrapper, null);

		serviceBeanMethodInvocation.proceed();

		Assert.assertSame(
			serviceContext, ServiceContextThreadLocal.popServiceContext());
	}

	private ServiceBeanMethodInvocation _createTestMethodInvocation(
		Method method, Object... arguments) {

		return new ServiceBeanMethodInvocation(
			_serviceBeanAopInvocationHandler.getAopMethod(method), arguments);
	}

	private ServiceBeanAopInvocationHandler _serviceBeanAopInvocationHandler;
	private final TestInterceptedClass _testInterceptedClass =
		new TestInterceptedClass();

	private static class TestInterceptedClass {

		@SuppressWarnings("unused")
		public void method() {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unused")
		public void method(Object obj) {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unused")
		public void method(ServiceContext serviceContext) {
			if (serviceContext == null) {
				Assert.assertNotNull(
					ServiceContextThreadLocal.getServiceContext());
			}
			else {
				Assert.assertSame(
					serviceContext,
					ServiceContextThreadLocal.getServiceContext());
			}
		}

		@SuppressWarnings("unused")
		public void method(
			TestServiceContextWrapper serviceContextWrapper, Object obj) {

			Assert.assertSame(
				serviceContextWrapper,
				ServiceContextThreadLocal.getServiceContext());
		}

	}

	private static class TestServiceContextWrapper extends ServiceContext {
	}

}