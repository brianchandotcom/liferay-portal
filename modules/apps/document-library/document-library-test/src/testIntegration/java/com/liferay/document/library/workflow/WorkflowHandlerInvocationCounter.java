/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		WorkflowHandler<T> delegateWorkflowHandler =
			_createInvocationCounterWorkflowHandler(
				WorkflowHandlerRegistryUtil.getWorkflowHandler(className));

		_workflowHandlerReplacer = new WorkflowHandlerReplacer<>(
			className, delegateWorkflowHandler);
	}

	@Override
	public void close() throws Exception {
		_workflowHandlerReplacer.close();
	}

	public int getCount(String methodName, Class<?>... parameterTypes)
		throws Exception {

		AtomicInteger count = _counts.get(
			WorkflowHandler.class.getMethod(methodName, parameterTypes));

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

	private final Map<Method, AtomicInteger> _counts = new HashMap<>();
	private final WorkflowHandlerReplacer<T> _workflowHandlerReplacer;

}