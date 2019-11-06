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

package com.liferay.portal.workflow.kaleo.runtime.internal.model.listener;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.KaleoActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelectorRegistry;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationHelper;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_kaleoTaskAssignmentInstanceLocalService.
					getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property assigneeClassNameProperty =
						PropertyFactoryUtil.forName("assigneeClassName");

					dynamicQuery.add(
						assigneeClassNameProperty.like(User.class.getName()));

					Property assigneeClassPKProperty =
						PropertyFactoryUtil.forName("assigneeClassPK");

					dynamicQuery.add(
						assigneeClassPKProperty.eq(user.getUserId()));
				});
			actionableDynamicQuery.setPerformActionMethod(
				(KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) ->
					_reassignKaleoTaskInstance(kaleoTaskAssignmentInstance));

			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	private void _reassignKaleoTaskInstance(
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance)
		throws PortalException {

		KaleoTask kaleoTask = _kaleoTaskLocalService.getKaleoTask(
			kaleoTaskAssignmentInstance.getKaleoTaskId());

		KaleoInstanceToken kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.getKaleoInstanceToken(
				kaleoTaskAssignmentInstance.getKaleoInstanceTokenId());

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
				kaleoTaskAssignmentInstance.getKaleoTaskInstanceTokenId());

		ExecutionContext executionContext = new ExecutionContext(
			kaleoInstanceToken, kaleoTaskInstanceToken,
			WorkflowContextUtil.convert(
				kaleoTaskInstanceToken.getWorkflowContext()),
			new ServiceContext() {
				{
					setCompanyId(kaleoInstanceToken.getCompanyId());
					setScopeGroupId(kaleoInstanceToken.getGroupId());
					setUserId(kaleoInstanceToken.getUserId());
				}
			});

		List<KaleoTaskAssignment> kaleoTaskAssignments = new ArrayList<>();

		for (KaleoTaskAssignment kaleoTaskAssignment :
				kaleoTask.getKaleoTaskAssignments()) {

			TaskAssignmentSelector taskAssignmentSelector =
				_taskAssignmentSelectorRegistry.getTaskAssignmentSelector(
					kaleoTaskAssignment.getAssigneeClassName());

			kaleoTaskAssignments.addAll(
				taskAssignmentSelector.calculateTaskAssignments(
					kaleoTaskAssignment, executionContext));
		}

		List<KaleoTaskAssignmentInstance> previousTaskAssignmentInstances =
			kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances();

		kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.deleteKaleoTaskInstanceToken(
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(),
				kaleoTask.getKaleoTaskId(), kaleoTask.getName(),
				kaleoTaskAssignments, kaleoTaskInstanceToken.getDueDate(),
				executionContext.getWorkflowContext(),
				executionContext.getServiceContext());

		executionContext.setKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		_kaleoActionExecutor.executeKaleoActions(
			KaleoNode.class.getName(), kaleoTask.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT, executionContext);

		_notificationHelper.sendKaleoNotifications(
			KaleoNode.class.getName(), kaleoTask.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT, executionContext);

		_kaleoLogLocalService.addTaskAssignmentKaleoLog(
			previousTaskAssignmentInstances, kaleoTaskInstanceToken, null,
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());
	}

	@Reference
	private KaleoActionExecutor _kaleoActionExecutor;

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private NotificationHelper _notificationHelper;

	@Reference
	private TaskAssignmentSelectorRegistry _taskAssignmentSelectorRegistry;

}