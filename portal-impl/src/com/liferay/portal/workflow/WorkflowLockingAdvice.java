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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowLockingAdvice {

	public static WorkflowDefinitionManager create(
		WorkflowDefinitionManager workflowDefinitionManager) {

		return (WorkflowDefinitionManager)ProxyUtil.newProxyInstance(
			WorkflowLockingAdvice.class.getClassLoader(),
			new Class<?>[] {WorkflowDefinitionManager.class},
			new WorkflowLockingInvocationHandler(workflowDefinitionManager));
	}

	public static WorkflowInstanceManager create(
		WorkflowInstanceManager workflowInstanceManager) {

		return (WorkflowInstanceManager)ProxyUtil.newProxyInstance(
			WorkflowLockingAdvice.class.getClassLoader(),
			new Class<?>[] {WorkflowInstanceManager.class},
			new WorkflowLockingInvocationHandler(workflowInstanceManager));
	}

	private static String _encodeKey(String name, int version) {
		return name.concat(StringPool.POUND).concat(
			StringUtil.toHexString(version));
	}

	private static final Method _START_WORKFLOW_INSTANCE_METHOD;

	private static final Method _UNDEPLOY_WORKFLOW_DEFINITION_METHOD;

	static {
		try {
			_START_WORKFLOW_INSTANCE_METHOD =
				WorkflowInstanceManager.class.getMethod(
					"startWorkflowInstance", long.class, long.class, long.class,
					String.class, Integer.class, String.class, Map.class);

			_UNDEPLOY_WORKFLOW_DEFINITION_METHOD =
				WorkflowDefinitionManager.class.getMethod(
					"undeployWorkflowDefinition", long.class, long.class,
					String.class, int.class);
		}
		catch (NoSuchMethodException nsme) {
			throw new ExceptionInInitializerError(nsme);
		}
	}

	private static class WorkflowLockingInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			if (_START_WORKFLOW_INSTANCE_METHOD.equals(method)) {
				String workflowDefinitionName = (String)arguments[3];
				Integer workflowDefinitionVersion = (Integer)arguments[4];

				String className = WorkflowDefinition.class.getName();
				String key = _encodeKey(
					workflowDefinitionName, workflowDefinitionVersion);

				if (LockManagerUtil.isLocked(className, key)) {
					throw new WorkflowException(
						StringBundler.concat(
							"Workflow definition name ", workflowDefinitionName,
							" and version ",
							String.valueOf(workflowDefinitionVersion),
							" is being undeployed"));
				}

				return _invoke(method, arguments);
			}
			else if (!_UNDEPLOY_WORKFLOW_DEFINITION_METHOD.equals(method)) {
				return _invoke(method, arguments);
			}

			long userId = (Long)arguments[1];
			String name = (String)arguments[2];
			Integer version = (Integer)arguments[3];

			String className = WorkflowDefinition.class.getName();
			String key = _encodeKey(name, version);

			if (LockManagerUtil.isLocked(className, key)) {
				throw new WorkflowException(
					StringBundler.concat(
						"Workflow definition name ", name, " and version ",
						String.valueOf(version), " is being undeployed"));
			}

			try {
				LockManagerUtil.lock(
					userId, className, key, String.valueOf(userId), false,
					Time.HOUR);

				return _invoke(method, arguments);
			}
			finally {
				LockManagerUtil.unlock(className, key);
			}
		}

		private WorkflowLockingInvocationHandler(Object targetObject) {
			_targetObject = targetObject;
		}

		private Object _invoke(Method method, Object[] arguments)
			throws Throwable {

			try {
				return method.invoke(_targetObject, arguments);
			}
			catch (Throwable t) {
				if (t instanceof InvocationTargetException) {
					t = t.getCause();
				}

				throw t;
			}
		}

		private final Object _targetObject;

	}

}