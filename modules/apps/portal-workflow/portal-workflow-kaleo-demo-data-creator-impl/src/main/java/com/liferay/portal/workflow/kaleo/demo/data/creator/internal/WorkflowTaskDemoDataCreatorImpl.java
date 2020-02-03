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

package com.liferay.portal.workflow.kaleo.demo.data.creator.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowTaskDemoDataCreator;
import com.liferay.portal.workflow.kaleo.demo.data.creator.internal.util.WorkflowDemoDataCreatorUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = WorkflowTaskDemoDataCreator.class)
public class WorkflowTaskDemoDataCreatorImpl
	implements WorkflowTaskDemoDataCreator {

	@Override
	public void completeWorkflowTask(
			long companyId, String transitionName, long userId,
			long workflowTaskId)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(
					_userLocalService.getUser(userId)));

			Map<String, Serializable> workflowContext = _getWorkflowContext(
				companyId, userId, workflowTaskId);

			_workflowTaskManager.assignWorkflowTaskToUser(
				companyId, userId, workflowTaskId, userId, StringPool.BLANK,
				null, workflowContext);

			_workflowTaskManager.completeWorkflowTask(
				companyId, userId, workflowTaskId, transitionName,
				StringPool.BLANK, workflowContext);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Override
	public List<String> getNextTransitionNames(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getNextTransitionNames(
			companyId, 0L, workflowTaskId);
	}

	@Override
	public WorkflowTask getWorkflowTask(long companyId, long workflowInstanceId)
		throws Exception {

		return WorkflowDemoDataCreatorUtil.retry(
			() -> {
				List<WorkflowTask> workflowTasks =
					_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
						companyId, null, workflowInstanceId, false, 0, 1, null);

				if (workflowTasks.isEmpty()) {
					return null;
				}

				return workflowTasks.get(0);
			});
	}

	@Override
	public void updateCompletionDate(long workflowTaskId, Date completionDate)
		throws Exception {

		WorkflowDemoDataCreatorUtil.retry(
			() -> {
				KaleoTaskInstanceToken kaleoTaskInstanceToken =
					_kaleoTaskInstanceTokenLocalService.
						getKaleoTaskInstanceToken(workflowTaskId);

				if (!kaleoTaskInstanceToken.isCompleted()) {
					return null;
				}

				kaleoTaskInstanceToken.setCompletionDate(completionDate);

				_kaleoTaskInstanceTokenLocalService.
					updateKaleoTaskInstanceToken(kaleoTaskInstanceToken);

				return kaleoTaskInstanceToken;
			});
	}

	@Override
	public void updateCreateDate(long workflowTaskId, Date createDate)
		throws PortalException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
				workflowTaskId);

		if (createDate != null) {
			kaleoTaskInstanceToken.setCreateDate(createDate);

			_kaleoTaskInstanceTokenLocalService.updateKaleoTaskInstanceToken(
				kaleoTaskInstanceToken);
		}
	}

	private Map<String, Serializable> _getWorkflowContext(
			long companyId, long userId, long workflowTaskId)
		throws PortalException {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				companyId, workflowTask.getWorkflowInstanceId());

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_ID, String.valueOf(userId));

		return workflowContext;
	}

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}