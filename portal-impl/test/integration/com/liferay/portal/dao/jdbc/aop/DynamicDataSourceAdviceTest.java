/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.impl.ClassNameLocalServiceImpl;
import com.liferay.portal.service.impl.GroupLocalServiceImpl;
import com.liferay.portal.service.impl.LockLocalServiceImpl;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;
import com.liferay.portal.spring.transaction.AnnotationTransactionAttributeSource;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.powermock.reflect.Whitebox;

import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author László Csontos
 */
public class DynamicDataSourceAdviceTest {

	@BeforeClass
	public static void setUpClass() {
		_dynamicDataSourceAdvice = new MockDynamicDataSourceAdvice();

		_dynamicDataSourceTargetSource = new DynamicDataSourceTargetSource();

		_dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
			_dynamicDataSourceTargetSource);

		ServiceBeanAopCacheManager serviceBeanAopCacheManager =
			new ServiceBeanAopCacheManager();

		Whitebox.setInternalState(
			_dynamicDataSourceAdvice, "serviceBeanAopCacheManager",
			serviceBeanAopCacheManager);

		TransactionAttributeSource transactionAttributeSource =
			new AnnotationTransactionAttributeSource();

		_dynamicDataSourceAdvice.setTransactionAttributeSource(
			transactionAttributeSource);
	}

	@Before
	public void setUp() {
		_dynamicDataSourceAdvice.reset();
	}

	@Test
	public void testDoNotSplit() throws Throwable {
		MethodInvocation methodInvocation = new MockMethodInvocation(
			new LockLocalServiceImpl(), "lock", String.class, String.class,
			String.class);

		doTest(methodInvocation, 0, 1);
	}

	@Test
	public void testRead() throws Throwable {
		MethodInvocation methodInvocation = new MockMethodInvocation(
			new GroupLocalServiceImpl(), "getGroup", long.class);

		doTest(methodInvocation, 1, 0);
	}

	@Test
	public void testWrite() throws Throwable {
		MethodInvocation methodInvocation = new MockMethodInvocation(
			new ClassNameLocalServiceImpl(), "addClassName", ClassName.class);

		doTest(methodInvocation, 0, 1);
	}

	protected void doTest(
		MethodInvocation methodInvocation, int expectedReadCount,
		int expectedWriteCount) throws Throwable {

		_dynamicDataSourceAdvice.invoke(methodInvocation);

		Assert.assertEquals(
			expectedReadCount, _dynamicDataSourceAdvice.getReadCount());
		Assert.assertEquals(
			expectedWriteCount, _dynamicDataSourceAdvice.getWriteCount());
	}

	private static MockDynamicDataSourceAdvice _dynamicDataSourceAdvice;
	private static DynamicDataSourceTargetSource
		_dynamicDataSourceTargetSource;

	private static class MockDynamicDataSourceAdvice
		extends DynamicDataSourceAdvice {

		@Override
		public void afterReturning(
				MethodInvocation methodInvocation, Object result)
			throws Throwable {

			DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
				Whitebox.getInternalState(
					this, "_dynamicDataSourceTargetSource");

			Operation operation = dynamicDataSourceTargetSource.getOperation();

			if (operation == Operation.READ) {
				_readCount++;
			}

			if (operation == Operation.WRITE) {
				_writeCount++;
			}
		}

		public int getReadCount() {
			return _readCount;
		}

		public int getWriteCount() {
			return _writeCount;
		}

		public void reset() {
			_readCount = 0;
			_writeCount = 0;
		}

		private int _readCount;
		private int _writeCount;
	}

	private static class MockMethodInvocation implements MethodInvocation {

		public MockMethodInvocation(
			Object thisObject, String methodName, Class<?> ... parameterTypes) {

			_methodName = methodName;
			_parameterTypes = parameterTypes;
			_thisObject = thisObject;
		}

		@Override
		public Object[] getArguments() {
			return null;
		}

		@Override
		public Method getMethod() {
			return Whitebox.getMethod(
				_thisObject.getClass(), _methodName, _parameterTypes);
		}

		@Override
		public AccessibleObject getStaticPart() {
			return null;
		}

		@Override
		public Object getThis() {
			return _thisObject;
		}

		@Override
		public Object proceed() throws Throwable {
			return null;
		}

		private String _methodName;
		private Class<?>[] _parameterTypes;
		private Object _thisObject;
	}

}