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

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.spring.transaction.TransactionHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
	public void setUp() throws Exception {
		Constructor<AopInvocationHandler> constructor =
			AopInvocationHandler.class.getDeclaredConstructor(
				Object.class, ChainableMethodAdvice[].class,
				TransactionHandler.class);

		constructor.setAccessible(true);

		_aopInvocationHandler = constructor.newInstance(
			_testInterceptedClass,
			new ChainableMethodAdvice[] {new ServiceContextAdvice()}, null);
	}

	@Test
	public void testThreadLocalValue() throws Throwable {
		ServiceContext serviceContext1 = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext1);

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", ServiceContext.class);

		ServiceContext serviceContext2 = new ServiceContext();

		AopMethodInvocation aopMethodInvocation = _createTestMethodInvocation(
			method);

		aopMethodInvocation.proceed(new Object[] {serviceContext2});

		Assert.assertSame(
			serviceContext1, ServiceContextThreadLocal.popServiceContext());
	}

	@Test
	public void testWithNoArguments() {
		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method");

		AopMethodInvocation aopMethodInvocation = ReflectionTestUtil.invoke(
			_aopInvocationHandler, "_getAopMethodInvocation",
			new Class<?>[] {Method.class}, method);

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				aopMethodInvocation, "_nextAopMethodInvocation"));
	}

	@Test
	public void testWithNullServiceContext() throws Throwable {
		ServiceContext serviceContext = new ServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", ServiceContext.class);

		AopMethodInvocation aopMethodInvocation = _createTestMethodInvocation(
			method);

		aopMethodInvocation.proceed(new Object[] {null});

		Assert.assertSame(
			serviceContext, ServiceContextThreadLocal.popServiceContext());
	}

	@Test
	public void testWithoutServiceContextParameter() {
		ServiceContextThreadLocal.pushServiceContext(new ServiceContext());

		Method method = ReflectionTestUtil.getMethod(
			TestInterceptedClass.class, "method", Object.class);

		AopMethodInvocation aopMethodInvocation = ReflectionTestUtil.invoke(
			_aopInvocationHandler, "_getAopMethodInvocation",
			new Class<?>[] {Method.class}, method);

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				aopMethodInvocation, "_nextAopMethodInvocation"));
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

		AopMethodInvocation aopMethodInvocation = _createTestMethodInvocation(
			method);

		aopMethodInvocation.proceed(
			new Object[] {testServiceContextWrapper, null});

		Assert.assertSame(
			serviceContext, ServiceContextThreadLocal.popServiceContext());
	}

	private AopMethodInvocation _createTestMethodInvocation(Method method) {
		return ReflectionTestUtil.invoke(
			_aopInvocationHandler, "_getAopMethodInvocation",
			new Class<?>[] {Method.class}, method);
	}

	private AopInvocationHandler _aopInvocationHandler;
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