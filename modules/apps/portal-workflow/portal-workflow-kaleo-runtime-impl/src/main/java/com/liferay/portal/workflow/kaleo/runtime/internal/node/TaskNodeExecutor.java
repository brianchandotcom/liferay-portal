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

package com.liferay.portal.workflow.kaleo.runtime.internal.node;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.AggregateKaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.calendar.DueDateCalculator;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.internal.assignment.helper.TaskAssignerHelper;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "node.type=TASK",
	service = {NodeExecutor.class, TaskNodeExecutor.class}
)
public class TaskNodeExecutor extends BaseNodeExecutor {

	public void executeTimer(ExecutionContext executionContext)
		throws PortalException {

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			executionContext.getKaleoTimerInstanceToken();

		KaleoTimer kaleoTimer = kaleoTimerInstanceToken.getKaleoTimer();

		kaleoActionExecutor.executeKaleoActions(
			KaleoTimer.class.getName(), kaleoTimer.getKaleoTimerId(),
			ExecutionType.ON_TIMER, executionContext);

		List<KaleoTaskAssignment> kaleoTaskReassignments =
			kaleoTimer.getKaleoTaskReassignments();

		if (ListUtil.isNotEmpty(kaleoTaskReassignments)) {
			_taskAssignerHelper.reassignKaleoTask(
				kaleoTaskReassignments, executionContext);
		}

		notificationHelper.sendKaleoNotifications(
			KaleoTimer.class.getName(), kaleoTimer.getKaleoTimerId(),
			ExecutionType.ON_TIMER, executionContext);

		if (!kaleoTimer.isRecurring()) {
			kaleoTimerInstanceTokenLocalService.completeKaleoTimerInstanceToken(
				kaleoTimerInstanceToken.getKaleoTimerInstanceTokenId(),
				executionContext.getServiceContext());
		}
	}

	@Override
	protected boolean doEnter(
			KaleoNode currentKaleoNode, ExecutionContext executionContext)
		throws PortalException {

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();
		ServiceContext serviceContext = executionContext.getServiceContext();

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		KaleoTask kaleoTask = _kaleoTaskLocalService.getKaleoNodeKaleoTask(
			currentKaleoNode.getKaleoNodeId());

		Date dueDate = _calculateDueDate(kaleoTask);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_createTaskInstanceToken(
				executionContext, workflowContext, serviceContext,
				kaleoInstanceToken, kaleoTask, dueDate);

		executionContext.setKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		kaleoActionExecutor.executeKaleoActions(
			KaleoNode.class.getName(), currentKaleoNode.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT, executionContext);

		notificationHelper.sendKaleoNotifications(
			KaleoNode.class.getName(), currentKaleoNode.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT, executionContext);

		List<KaleoTimer> kaleoTimers = kaleoTimerLocalService.getKaleoTimers(
			KaleoNode.class.getName(), currentKaleoNode.getKaleoNodeId());

		kaleoTimerInstanceTokenLocalService.addKaleoTimerInstanceTokens(
			executionContext.getKaleoInstanceToken(),
			executionContext.getKaleoTaskInstanceToken(), kaleoTimers,
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());

		_kaleoLogLocalService.addTaskAssignmentKaleoLogs(
			null, kaleoTaskInstanceToken, "assigned-initial-task",
			workflowContext, serviceContext);

		return true;
	}

	@Override
	protected void doExecute(
		KaleoNode currentKaleoNode, ExecutionContext executionContext,
		List<PathElement> remainingPathElements) {
	}

	@Override
	protected void doExit(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			List<PathElement> remainingPathElements)
		throws PortalException {

		String transitionName = executionContext.getTransitionName();

		KaleoTransition kaleoTransition = null;

		if (Validator.isNull(transitionName)) {
			kaleoTransition = currentKaleoNode.getDefaultKaleoTransition();
		}
		else {
			kaleoTransition = currentKaleoNode.getKaleoTransition(
				transitionName);
		}

		ExecutionContext newExecutionContext = new ExecutionContext(
			executionContext.getKaleoInstanceToken(),
			executionContext.getKaleoTaskInstanceToken(),
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());

		PathElement pathElement = new PathElement(
			null, kaleoTransition.getTargetKaleoNode(), newExecutionContext);

		remainingPathElements.add(pathElement);
	}

	private Date _calculateDueDate(KaleoTask kaleoTask)
		throws KaleoDefinitionValidationException {

		List<KaleoTimer> kaleoTimers = kaleoTimerLocalService.getKaleoTimers(
			KaleoNode.class.getName(), kaleoTask.getKaleoNodeId());

		if (kaleoTimers.isEmpty()) {
			return null;
		}

		TreeSet<Date> sortedDueDates = new TreeSet<>();

		for (KaleoTimer kaleoTimer : kaleoTimers) {
			DelayDuration delayDuration = new DelayDuration(
				kaleoTimer.getDuration(),
				DurationScale.parse(kaleoTimer.getScale()));

			Date dueDate = _dueDateCalculator.getDueDate(
				new Date(), delayDuration);

			sortedDueDates.add(dueDate);
		}

		return sortedDueDates.first();
	}

	private KaleoTaskInstanceToken _createTaskInstanceToken(
			ExecutionContext executionContext,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext,
			KaleoInstanceToken kaleoInstanceToken, KaleoTask kaleoTask,
			Date dueDate)
		throws PortalException {

		return _kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoInstanceToken.getKaleoInstanceTokenId(),
			kaleoTask.getKaleoTaskId(), kaleoTask.getName(),
			_aggregateKaleoTaskAssignmentSelector.getKaleoTaskAssignments(
				kaleoTask.getKaleoTaskAssignments(), executionContext),
			dueDate, workflowContext, serviceContext);
	}

	@Reference
	private AggregateKaleoTaskAssignmentSelector
		_aggregateKaleoTaskAssignmentSelector;

	@Reference
	private DueDateCalculator _dueDateCalculator;

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private TaskAssignerHelper _taskAssignerHelper;

}