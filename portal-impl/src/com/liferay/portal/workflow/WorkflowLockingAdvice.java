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

import com.liferay.portal.kernel.lock.LockHelperUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowLockingAdvice {

	public Object invoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Signature signature = proceedingJoinPoint.getSignature();

		String methodName = signature.getName();

		Object[] arguments = proceedingJoinPoint.getArgs();

		if (methodName.equals(_START_WORKFLOW_INSTANCE_METHOD_NAME)) {
			String workflowDefinitionName = (String)arguments[3];
			Integer workflowDefinitionVersion = (Integer)arguments[4];

			String className = WorkflowDefinition.class.getName();
			String key = _encodeKey(
				workflowDefinitionName, workflowDefinitionVersion);

			if (LockHelperUtil.isLocked(className, key)) {
				throw new WorkflowException(
					"Workflow definition name " + workflowDefinitionName +
						" and version " + workflowDefinitionVersion +
							" is being undeployed");
			}

			return proceedingJoinPoint.proceed();
		}
		else if (!methodName.equals(
					_UNDEPLOY_WORKFLOW_DEFINITION_METHOD_NAME)) {

			return proceedingJoinPoint.proceed();
		}

		long userId = (Long)arguments[1];
		String name = (String)arguments[2];
		Integer version = (Integer)arguments[3];

		String className = WorkflowDefinition.class.getName();
		String key = _encodeKey(name, version);

		if (LockHelperUtil.isLocked(className, key)) {
			throw new WorkflowException(
				"Workflow definition name " + name + " and version " + version +
					" is being undeployed");
		}

		try {
			LockHelperUtil.lock(
				userId, className, key, String.valueOf(userId), false,
				Time.HOUR);

			return proceedingJoinPoint.proceed();
		}
		finally {
			LockHelperUtil.unlock(className, key);
		}
	}

	private String _encodeKey(String name, int version) {
		return name.concat(StringPool.POUND).concat(
			StringUtil.toHexString(version));
	}

	private static final String _START_WORKFLOW_INSTANCE_METHOD_NAME =
		"startWorkflowInstance";

	private static final String _UNDEPLOY_WORKFLOW_DEFINITION_METHOD_NAME =
		"undeployWorkflowDefinition";

}