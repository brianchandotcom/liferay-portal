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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowPermissionAdvice {

	public static WorkflowTaskManager create(
		WorkflowTaskManager workflowTaskManager) {

		return (WorkflowTaskManager)ProxyUtil.newProxyInstance(
			WorkflowPermissionAdvice.class.getClassLoader(),
			new Class<?>[] {WorkflowTaskManager.class},
			new WorkflowPermissionInvocationHandler(workflowTaskManager));
	}

	private static final Method _ASSIGN_WORKFLOW_TASK_TO_USER_METHOD;

	static {
		try {
			_ASSIGN_WORKFLOW_TASK_TO_USER_METHOD =
				WorkflowTaskManager.class.getMethod(
					"assignWorkflowTaskToUser", long.class, long.class,
					long.class, long.class, String.class, Date.class,
					Map.class);
		}
		catch (NoSuchMethodException nsme) {
			throw new ExceptionInInitializerError(nsme);
		}
	}

	private static class WorkflowPermissionInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			if (_ASSIGN_WORKFLOW_TASK_TO_USER_METHOD.equals(method)) {
				long userId = (Long)arguments[1];

				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				if (permissionChecker.getUserId() != userId) {
					throw new PrincipalException();
				}
			}

			try {
				return method.invoke(_workflowTaskManager, arguments);
			}
			catch (Throwable t) {
				if (t instanceof InvocationTargetException) {
					t = t.getCause();
				}

				throw t;
			}
		}

		private WorkflowPermissionInvocationHandler(
			WorkflowTaskManager workflowTaskManager) {

			_workflowTaskManager = workflowTaskManager;
		}

		private final WorkflowTaskManager _workflowTaskManager;

	}

}