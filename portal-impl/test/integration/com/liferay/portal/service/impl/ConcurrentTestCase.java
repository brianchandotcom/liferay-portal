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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

/**
 * @author Matthew Tambara
 */
public abstract class ConcurrentTestCase<T> {

	protected abstract void assertResults(
		Set<FutureTask<T>> futureTasks) throws Exception;

	protected abstract Callable<T> createCallable();

	protected void doConcurrentTest() throws Exception {
		SYNCHRONIZE_THREAD_LOCAL.set(true);

		Callable<T> callable = createCallable();

		Set<FutureTask<T>> futureTasks = new HashSet<>();

		for (int i = 0; i < THREAD_COUNT; i++) {
			FutureTask<T> futureTask = new FutureTask<>(callable);

			Thread thread = new Thread(futureTask, "Add Thread " + i);

			thread.start();

			futureTasks.add(futureTask);
		}

		assertResults(futureTasks);

		SYNCHRONIZE_THREAD_LOCAL.set(false);
	}

	protected static final ThreadLocal<Boolean> SYNCHRONIZE_THREAD_LOCAL =
		new InheritableThreadLocal<>();

	protected static final int THREAD_COUNT = 3;

	protected static BaseLocalServiceImpl localServiceBaseImpl;
	protected static BasePersistence<?> originalPersistence;
	protected static String persistenceField;
	protected static Method syncMethod;

	protected static class SynchronousInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (SYNCHRONIZE_THREAD_LOCAL.get() && syncMethod.equals(method)) {
				_cyclicBarrier.await();
			}

			return method.invoke(originalPersistence, args);
		}

		private final CyclicBarrier _cyclicBarrier = new CyclicBarrier(
			THREAD_COUNT, new Runnable() {

				@Override
				public void run() {
					ReflectionTestUtil.setFieldValue(
						localServiceBaseImpl, persistenceField,
						originalPersistence);
				}

			});

	}

}