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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.dao.jdbc.aop.MasterDataSource;
import com.liferay.portal.kernel.dao.jdbc.aop.Operation;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopInvocationHandler;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class DynamicDataSourceAdviceTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() throws Exception {
		DynamicDataSourceAdvice dynamicDataSourceAdvice =
			new DynamicDataSourceAdvice();

		_dynamicDataSourceTargetSource =
			new DefaultDynamicDataSourceTargetSource();

		ClassLoader classLoader =
			DynamicDataSourceAdviceTest.class.getClassLoader();

		InvocationHandler invocationHandler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {

				throw new UnsupportedOperationException();
			}

		};

		_readDataSource = (DataSource)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {DataSource.class}, invocationHandler);

		_dynamicDataSourceTargetSource.setReadDataSource(_readDataSource);

		_writeDataSource = (DataSource)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {DataSource.class}, invocationHandler);

		_dynamicDataSourceTargetSource.setWriteDataSource(_writeDataSource);

		dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
			_dynamicDataSourceTargetSource);

		Constructor<ServiceBeanAopInvocationHandler> constructor =
			ServiceBeanAopInvocationHandler.class.getDeclaredConstructor(
				Object.class, ChainableMethodAdvice[].class);

		constructor.setAccessible(true);

		_serviceBeanAopInvocationHandler = constructor.newInstance(
			_testClass, new ChainableMethodAdvice[] {dynamicDataSourceAdvice});
	}

	@Test
	public void testDynamicDataSourceAdvice() throws Throwable {
		for (int i = 1; i <= 6; i++) {
			ServiceBeanMethodInvocation serviceBeanMethodInvocation =
				createMethodInvocation("method" + i);

			serviceBeanMethodInvocation.proceed(null);
		}

		_testClass.assertExecutions();
	}

	protected ServiceBeanMethodInvocation createMethodInvocation(
			String methodName)
		throws Exception {

		return ReflectionTestUtil.invoke(
			_serviceBeanAopInvocationHandler, "_getServiceBeanMethodInvocation",
			new Class<?>[] {Method.class},
			TestClass.class.getMethod(methodName));
	}

	private DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private DataSource _readDataSource;
	private ServiceBeanAopInvocationHandler _serviceBeanAopInvocationHandler;
	private final TestClass _testClass = new TestClass();
	private DataSource _writeDataSource;

	private class TestClass {

		public void assertExecutions() {
			Assert.assertTrue(_testMethod1);
			Assert.assertTrue(_testMethod2);
			Assert.assertTrue(_testMethod3);
			Assert.assertTrue(_testMethod4);
			Assert.assertTrue(_testMethod5);
			Assert.assertTrue(_testMethod6);
		}

		@SuppressWarnings("unused")
		public void method1() throws Exception {
			Operation operation = _callerOperation.get();

			if (operation == Operation.READ) {
				Assert.assertEquals(
					Operation.READ,
					_dynamicDataSourceTargetSource.getOperation());
				Assert.assertSame(
					_readDataSource,
					_dynamicDataSourceTargetSource.getTarget());
			}
			else {
				Assert.assertEquals(
					Operation.WRITE,
					_dynamicDataSourceTargetSource.getOperation());
				Assert.assertSame(
					_writeDataSource,
					_dynamicDataSourceTargetSource.getTarget());
			}

			_testMethod1 = true;
		}

		@Transactional
		public void method2() throws Exception {
			Assert.assertEquals(
				Operation.WRITE, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_writeDataSource, _dynamicDataSourceTargetSource.getTarget());

			_testMethod2 = true;
		}

		@Transactional(readOnly = true)
		public void method3() throws Exception {
			Assert.assertEquals(
				Operation.READ, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_readDataSource, _dynamicDataSourceTargetSource.getTarget());

			_testMethod3 = true;
		}

		@MasterDataSource
		@Transactional
		public void method4() throws Exception {
			Assert.assertEquals(
				Operation.WRITE, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_writeDataSource, _dynamicDataSourceTargetSource.getTarget());

			_testMethod4 = true;
		}

		@MasterDataSource
		@Transactional(readOnly = true)
		public void method5() throws Exception {
			Assert.assertEquals(
				Operation.WRITE, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_writeDataSource, _dynamicDataSourceTargetSource.getTarget());

			_testMethod5 = true;
		}

		@Transactional(readOnly = true)
		public void method6() throws Throwable {
			ServiceBeanMethodInvocation serviceBeanMethodInvocation =
				createMethodInvocation("method3");

			serviceBeanMethodInvocation.proceed(null);

			Assert.assertEquals(
				Operation.READ, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_readDataSource, _dynamicDataSourceTargetSource.getTarget());

			serviceBeanMethodInvocation = createMethodInvocation("method1");

			_callerOperation.set(Operation.READ);

			serviceBeanMethodInvocation.proceed(null);

			_callerOperation.remove();

			Assert.assertEquals(
				Operation.READ, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_readDataSource, _dynamicDataSourceTargetSource.getTarget());

			serviceBeanMethodInvocation = createMethodInvocation("method2");

			serviceBeanMethodInvocation.proceed(null);

			Assert.assertEquals(
				Operation.READ, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_readDataSource, _dynamicDataSourceTargetSource.getTarget());

			serviceBeanMethodInvocation = createMethodInvocation("method4");

			serviceBeanMethodInvocation.proceed(null);

			Assert.assertEquals(
				Operation.READ, _dynamicDataSourceTargetSource.getOperation());
			Assert.assertSame(
				_readDataSource, _dynamicDataSourceTargetSource.getTarget());

			_testMethod6 = true;
		}

		private final ThreadLocal<Operation> _callerOperation =
			new ThreadLocal<>();
		private boolean _testMethod1;
		private boolean _testMethod2;
		private boolean _testMethod3;
		private boolean _testMethod4;
		private boolean _testMethod5;
		private boolean _testMethod6;

	}

}