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

package com.liferay.portal.dao.shard.advice;

import com.liferay.portal.NoShardSelectedException;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.spring.aop.ShardSelection;
import com.liferay.portal.kernel.spring.aop.ShardSelectionMethod;
import com.liferay.portal.kernel.spring.aop.ShardSelectorParam;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Vilmos Papp
 */
@PrepareForTest({Shard.class, ShardLocalServiceUtil.class, ShardUtil.class})
@RunWith(PowerMockRunner.class)
public class ShardAnnotationAdviceTest extends PowerMockito {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		Shard defaultShard = mock(Shard.class);
		defaultShard.setName(_DEFAULT_SHARD_NAME);

		Shard oneShard = mock(Shard.class);
		oneShard.setName(_ONE_SHARD_NAME);

		ShardAdvice shardAdvice = mock(ShardAdvice.class);

		_shardAnnotationAdvice = new ShardAnnotationAdvice();

		_shardAnnotationAdvice.setShardAdvice(shardAdvice);

		mockStatic(ShardLocalServiceUtil.class);

		when(
			ShardLocalServiceUtil.getShard(
				Company.class.getName(), _DEFAULT_SHARD_COMPANY_ID)
		).thenReturn(
			defaultShard
		);

		when(
			ShardLocalServiceUtil.getShard(
				Company.class.getName(), _ONE_SHARD_COMPANY_ID)
		).thenReturn(
			oneShard
		);

		ServiceBeanAopCacheManager serviceBeanAopCacheManager =
			new ServiceBeanAopCacheManager();

		_shardAnnotationAdvice.setServiceBeanAopCacheManager(
			serviceBeanAopCacheManager);

		Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			registeredAnnotationChainableMethodAdvices =
				serviceBeanAopCacheManager.
					getRegisteredAnnotationChainableMethodAdvices();

		AnnotationChainableMethodAdvice<?>[] annotationChainableMethodAdvices =
			registeredAnnotationChainableMethodAdvices.get(
				ShardSelection.class);

		Assert.assertEquals(1, annotationChainableMethodAdvices.length);
		Assert.assertNull(annotationChainableMethodAdvices[0]);
		Assert.assertSame(
			annotationChainableMethodAdvices,
			registeredAnnotationChainableMethodAdvices.get(
				ShardSelection.class));
	}

	@Test
	public void testAnnotatedMethodWithFirstParamAndNoneAnnotation()
		throws Throwable {

		setupShardUtil(_DEFAULT_SHARD_COMPANY_ID);

		TestClass testClass = new TestClass();

		Object[] companyIdFirst = new Object[] {_ONE_SHARD_COMPANY_ID};

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass, "methodFirstParamWithNoneAnnotation", companyIdFirst);

		methodInvocation.proceed();
	}

	@Test
	public void testAnnotatedMethodWithFirstParamAndParamAnnotation()
		throws Throwable {

		setupShardUtil(_ONE_SHARD_COMPANY_ID);

		TestClass testClass = new TestClass();

		Object[] companyIdFirst = new Object[] {_ONE_SHARD_COMPANY_ID};

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass, "methodFirstParamWithParamAnnotation", companyIdFirst);

		methodInvocation.proceed();
	}

	@Test
	public void testAnnotatedMethodWithoutMethodParam() throws Throwable {
		TestClass testClass = new TestClass();

		setupShardUtil(_DEFAULT_SHARD_COMPANY_ID);

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass, "methodWithoutParamWithAnnotation", null);

		methodInvocation.proceed();
	}

	@Test(expected = NoShardSelectedException.class)
	public void testAnnotatedMethodWithoutParamAnnotation() throws Throwable {
		TestClass testClass = new TestClass();

		setupShardUtil(_DEFAULT_SHARD_COMPANY_ID);

		Object[] companyIdSecond = new Object[] {
			StringPool.BLANK, _ONE_SHARD_COMPANY_ID
		};

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass,
			"methodSecondParamWithMethodAnnotationWithoutParamAnnotation",
			companyIdSecond);

		methodInvocation.proceed();
	}

	@Test
	public void testAnnotatedMethodWithSecondParamAndParamAnnotation()
		throws Throwable {

		setupShardUtil(_ONE_SHARD_COMPANY_ID);

		TestClass testClass = new TestClass();

		Object[] companyIdSecond = new Object[] {
			StringPool.BLANK, _ONE_SHARD_COMPANY_ID
		};

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass,
			"methodSecondParamWithMethodAnnotationAndParamAnnotation",
			companyIdSecond);

		methodInvocation.proceed();
	}

	@Test
	public void testAnnotatedMethodWithSecondParamWithNoneAnnotation()
		throws Throwable {

		setupShardUtil(_DEFAULT_SHARD_COMPANY_ID);

		TestClass testClass = new TestClass();

		Object[] companyIdSecond = new Object[] {
			StringPool.BLANK, _ONE_SHARD_COMPANY_ID
		};

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass, "methodSecondParamWithNoneAnnotation", companyIdSecond);

		methodInvocation.proceed();
	}

	@Test
	public void testAnnotatedMethodWithSecondParamWithoutParamAnnotation()
		throws Throwable {

		setupShardUtil(_DEFAULT_SHARD_COMPANY_ID);

		TestClass testClass = new TestClass();

		Object[] companyIdSecond = new Object[] {
			StringPool.BLANK, _ONE_SHARD_COMPANY_ID
		};

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass, "methodSecondParamWithNoneAnnotation", companyIdSecond);

		methodInvocation.proceed();
	}

	@Test
	public void testAnnotationType() throws Exception {
		Field shardSelectionField = ReflectionUtil.getDeclaredField(
			ShardAnnotationAdvice.class, "_nullShardSelection");

		ShardSelection shardSelection = (ShardSelection)shardSelectionField.get(
			null);

		Assert.assertSame(
			ShardSelection.class, shardSelection.annotationType());
	}

	@Test
	public void testNotAnnotatedMethodWithoutWithoutParam() throws Throwable {
		TestClass testClass = new TestClass();

		setupShardUtil(_DEFAULT_SHARD_COMPANY_ID);

		MethodInvocation methodInvocation = createMethodInvocation(
			testClass, "methodWithoutParamWithoutAnnotation", null);

		methodInvocation.proceed();
	}

	protected MethodInvocation createMethodInvocation(
			TestClass testClass, String methodName, Object[] arguments)
		throws Exception {

		Method method = null;

		if (arguments == null) {
			method = TestClass.class.getMethod(methodName);
		}
		else if (arguments.length == 1) {
			Class<?> clazz[] = new Class[1];
			clazz[0] = long.class;

			method = TestClass.class.getMethod(methodName, clazz);
		}
		else if (arguments.length == 2) {
			Class<?> clazz[] = new Class[2];
			clazz[0] = String.class;
			clazz[1] = long.class;

			method = TestClass.class.getMethod(methodName, clazz);
		}

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(
				testClass, TestClass.class, method, arguments);

		ShardSelection shardSelection = method.getAnnotation(
			ShardSelection.class);

		Annotation[] annotations = null;

		if (shardSelection == null) {
			annotations = new Annotation[0];
		}
		else {
			annotations = new Annotation[] {shardSelection};
		}

		ServiceBeanAopCacheManager.putAnnotations(
			serviceBeanMethodInvocation, annotations);

		serviceBeanMethodInvocation.setMethodInterceptors(
			Arrays.<MethodInterceptor>asList(_shardAnnotationAdvice));

		return serviceBeanMethodInvocation;
	}

	protected void setupShardUtil(long companyId) {
		mockStatic(ShardUtil.class);

		when(
			ShardUtil.getDefaultShardName()
		).thenReturn(
			_DEFAULT_SHARD_NAME
		);

		if (companyId == _ONE_SHARD_COMPANY_ID) {
			when(
				ShardUtil.getCurrentShardName()
			).thenReturn(
				_ONE_SHARD_NAME
			);
		}
		else {
			when(
				ShardUtil.getCurrentShardName()
			).thenReturn(
				_DEFAULT_SHARD_NAME
			);
		}
	}

	private static final long _DEFAULT_SHARD_COMPANY_ID = 1;

	private static final String _DEFAULT_SHARD_NAME = "default";

	private static final long _ONE_SHARD_COMPANY_ID = 2;

	private static final String _ONE_SHARD_NAME = "one";

	private ShardAnnotationAdvice _shardAnnotationAdvice;

	@SuppressWarnings("unused")
	private class TestClass {

		@ShardSelection(selectionMethod = ShardSelectionMethod.NONE)
		public void methodFirstParamWithNoneAnnotation(long companyId) {
			Assert.assertEquals(
				ShardUtil.getCurrentShardName(),
				ShardUtil.getDefaultShardName());
		}

		@ShardSelection(selectionMethod = ShardSelectionMethod.PARAMETER)
		public void methodFirstParamWithParamAnnotation(long companyId) {
			Assert.assertEquals(
				ShardUtil.getCurrentShardName(), _ONE_SHARD_NAME);
		}

		@ShardSelection(selectionMethod = ShardSelectionMethod.PARAMETER)
		public void methodSecondParamWithMethodAnnotationAndParamAnnotation(
				String dummy, @ShardSelectorParam long companyId) {

			Assert.assertEquals(
				ShardUtil.getCurrentShardName(), _ONE_SHARD_NAME);
		}

		@ShardSelection(selectionMethod = ShardSelectionMethod.NONE)
		public void methodSecondParamWithNoneAnnotation(
				String dummy, long companyId) {

			Assert.assertEquals(
				ShardUtil.getCurrentShardName(),
				ShardUtil.getDefaultShardName());
		}

		@ShardSelection(selectionMethod = ShardSelectionMethod.PARAMETER)
		public void methodSecondParamWithMethodAnnotationWithoutParamAnnotation(
			String dummy, long companyId) {
		}

		@ShardSelection(selectionMethod = ShardSelectionMethod.NONE)
		public void methodWithoutParamWithAnnotation() {
			Assert.assertEquals(
				ShardUtil.getCurrentShardName(),
				ShardUtil.getDefaultShardName());
		}

		public void methodWithoutParamWithoutAnnotation() {
			Assert.assertEquals(
				ShardUtil.getCurrentShardName(),
				ShardUtil.getDefaultShardName());
		}

	}

}