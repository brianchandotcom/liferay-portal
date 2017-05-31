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

package com.liferay.document.library.workflow;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Adolfo Pérez
 */
public class WorkflowHandlerInvocationCounter<T> implements AutoCloseable {

	public WorkflowHandlerInvocationCounter(String className) {
		WorkflowHandler<T> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		_counts = new HashMap<>();

		WorkflowHandler<T> delegateWorkflowHandler =
			_createInvocationCounterWorkflowHandler(workflowHandler);

		_workflowHandlerReplacer = new WorkflowHandlerReplacer<>(
			className, delegateWorkflowHandler);
	}

	@Override
	public void close() throws Exception {
		_workflowHandlerReplacer.close();
	}

	public int getCount(String methodName, Class<?>... parameterTypes)
		throws Exception {

		Method method = WorkflowHandler.class.getMethod(
			methodName, parameterTypes);

		AtomicInteger count = _counts.get(method);

		if (count == null) {
			return 0;
		}

		return count.get();
	}

	private WorkflowHandler<T> _createInvocationCounterWorkflowHandler(
		final WorkflowHandler<T> workflowHandler) {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return (WorkflowHandler<T>)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {WorkflowHandler.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					AtomicInteger count = _counts.get(method);

					if (count == null) {
						count = new AtomicInteger();

						_counts.put(method, count);
					}

					count.incrementAndGet();

					return method.invoke(workflowHandler, args);
				}

			});
	}

	private final Map<Method, AtomicInteger> _counts;
	private final WorkflowHandlerReplacer<T> _workflowHandlerReplacer;

}