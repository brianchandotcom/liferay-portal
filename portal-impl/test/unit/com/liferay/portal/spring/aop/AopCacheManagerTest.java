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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class AopCacheManagerTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		TestInterfaceImpl testInterfaceImpl = new TestInterfaceImpl();

		_aopInvocationHandler = AopCacheManager.create(testInterfaceImpl, null);

		_testInterfaceProxy = (TestInterface)ProxyUtil.newProxyInstance(
			AopCacheManagerTest.class.getClassLoader(),
			new Class<?>[] {TestInterface.class}, _aopInvocationHandler);

		Runtime runtime = Runtime.getRuntime();

		_executorService = Executors.newFixedThreadPool(
			runtime.availableProcessors());
	}

	@After
	public void tearDown() {
		AopCacheManager.destroy(_aopInvocationHandler);

		_executorService.shutdownNow();
	}

	@Test
	public void testConcurrentRegistrationAndInvocations() throws Exception {
		List<Callable<Void>> callables = new ArrayList<>(1000);

		for (int i = 0; i < 1000; i++) {
			ClassLoader classLoader =
				AopCacheManagerTest.class.getClassLoader();

			Class<?> clazz = classLoader.loadClass(
				TestChainableMethodAdvice.class.getName() + (i % 10));

			TestChainableMethodAdvice testChainableMethodAdvice =
				(TestChainableMethodAdvice)clazz.newInstance();

			callables.add(
				() -> {
					Registry registry = RegistryUtil.getRegistry();

					ServiceRegistration<?> serviceRegistration =
						registry.registerService(
							ChainableMethodAdvice.class,
							testChainableMethodAdvice, new HashMap<>());

					List<Object> advices = new ArrayList<>();

					_testInterfaceProxy.assertAop(advices, null);

					Assert.assertTrue(
						advices.toString(),
						advices.contains(testChainableMethodAdvice));

					serviceRegistration.unregister();

					advices.clear();

					_testInterfaceProxy.assertAop(advices, null);

					Assert.assertFalse(
						advices.toString(),
						advices.contains(testChainableMethodAdvice));

					_aopInvocationHandler.setTarget(new TestInterfaceImpl());

					return null;
				});
		}

		Collections.shuffle(callables);

		List<Future<Void>> futures = _executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}
	}

	public static class TestChainableMethodAdvice
		extends ChainableMethodAdvice {

		@Override
		public Object createMethodContext(
			Class<?> targetClass, Method method,
			Map<Class<? extends Annotation>, Annotation> annotations) {

			Assert.assertSame(TestInterfaceImpl.class, targetClass);

			return nullResult;
		}

		@Override
		protected Object before(
			AopMethodInvocation aopMethodInvocation, Object[] arguments) {

			List<Object> advices = (List<Object>)arguments[0];

			advices.add(this);

			Object target = arguments[1];

			if (target == null) {
				arguments[1] = aopMethodInvocation.getThis();
			}
			else {
				Assert.assertSame(target, aopMethodInvocation.getThis());
			}

			return null;
		}

	}

	public static class TestChainableMethodAdvice0
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice1
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice2
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice3
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice4
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice5
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice6
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice7
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice8
		extends TestChainableMethodAdvice {
	}

	public static class TestChainableMethodAdvice9
		extends TestChainableMethodAdvice {
	}

	private AopInvocationHandler _aopInvocationHandler;
	private ExecutorService _executorService;
	private TestInterface _testInterfaceProxy;

	private static class TestInterfaceImpl implements TestInterface {

		@Override
		public void assertAop(List<Object> advices, Object targetPlaceholder) {
			if (advices.isEmpty()) {
				Assert.assertNull(targetPlaceholder);
			}
			else {
				Assert.assertSame(this, targetPlaceholder);

				List<String> adviceNames = new ArrayList<>(advices.size());

				for (Object advice : advices) {
					Class<?> clazz = advice.getClass();

					adviceNames.add(clazz.getName());
				}

				List<String> expectedAdviceNames = new ArrayList<>(adviceNames);

				expectedAdviceNames.sort(null);

				Assert.assertEquals(
					adviceNames.toString(), expectedAdviceNames, adviceNames);
			}
		}

	}

	private interface TestInterface {

		public void assertAop(List<Object> advices, Object targetPlaceholder);

	}

}