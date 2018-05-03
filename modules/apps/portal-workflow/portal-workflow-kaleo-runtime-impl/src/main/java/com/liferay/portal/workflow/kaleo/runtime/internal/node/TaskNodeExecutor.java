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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.DelayDuration;
import com.liferay.portal.workflow.kaleo.definition.DurationScale;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelectorRegistry;
import com.liferay.portal.workflow.kaleo.runtime.calendar.DueDateCalculator;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.internal.assignment.TaskAssignerUtil;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
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
	immediate = true, property = "node.type=TASK", service = NodeExecutor.class
)
public class TaskNodeExecutor extends BaseNodeExecutor {

	protected Date calculateDueDate(KaleoTask kaleoTask) {
		List<KaleoTimer> kaleoTimers = kaleoTimerLocalService.getKaleoTimers(
			KaleoNode.class.getName(), kaleoTask.getKaleoNodeId());

		if (kaleoTimers.isEmpty()) {
			return null;
		}

		TreeSet<Date> sortedDueDates = new TreeSet<>();

		for (KaleoTimer kaleoTimer : kaleoTimers) {
			DelayDuration delayDuration = new DelayDuration(
				kaleoTimer.getDuration(),
				DurationScale.valueOf(
					StringUtil.toUpperCase(kaleoTimer.getScale())));

			Date dueDate = _dueDateCalculator.getDueDate(
				new Date(), delayDuration);

			sortedDueDates.add(dueDate);
		}

		return sortedDueDates.first();
	}

	protected KaleoTaskInstanceToken createTaskInstanceToken(
			ExecutionContext executionContext,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext,
			KaleoInstanceToken kaleoInstanceToken, KaleoTask kaleoTask,
			Date dueDate)
		throws PortalException {

		Collection<KaleoTaskAssignment> configuredKaleoTaskAssignments =
			kaleoTask.getKaleoTaskAssignments();

		Collection<KaleoTaskAssignment> kaleoTaskAssignments =
			new ArrayList<>();

		for (KaleoTaskAssignment configuredKaleoTaskAssignment :
				configuredKaleoTaskAssignments) {

			String assigneeClassName =
				configuredKaleoTaskAssignment.getAssigneeClassName();

			TaskAssignmentSelector taskAssignmentSelector =
				_taskAssignmentSelectorRegistry.getTaskAssignmentSelector(
					assigneeClassName);

			Collection<KaleoTaskAssignment> calculatedKaleoTaskAssignments =
				taskAssignmentSelector.calculateTaskAssignments(
					configuredKaleoTaskAssignment, executionContext);

			kaleoTaskAssignments.addAll(calculatedKaleoTaskAssignments);
		}

		return _kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoInstanceToken.getKaleoInstanceTokenId(),
			kaleoTask.getKaleoTaskId(), kaleoTask.getName(),
			kaleoTaskAssignments, dueDate, workflowContext, serviceContext);
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

		Date dueDate = calculateDueDate(kaleoTask);

		KaleoTaskInstanceToken kaleoTaskInstanceToken = createTaskInstanceToken(
			executionContext, workflowContext, serviceContext,
			kaleoInstanceToken, kaleoTask, dueDate);

		executionContext.setKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		kaleoActionExecutor.executeKaleoActions(
			KaleoNode.class.getName(), currentKaleoNode.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT, executionContext);

		notificationHelper.sendKaleoNotifications(
			KaleoNode.class.getName(), currentKaleoNode.getKaleoNodeId(),
			ExecutionType.ON_ASSIGNMENT, executionContext);

		_kaleoLogLocalService.addTaskAssignmentKaleoLog(
			null, kaleoTaskInstanceToken, "Assigned initial task.",
			workflowContext, serviceContext);

		return true;
	}

	@Override
	protected void doExecute(
		KaleoNode currentKaleoNode, ExecutionContext executionContext,
		List<PathElement> remainingPathElements) {
	}

	@Override
	protected void doExecuteTimer(
			KaleoNode currentKaleoNode, KaleoTimer kaleoTimer,
			ExecutionContext executionContext)
		throws PortalException {

		List<KaleoTaskAssignment> kaleoTaskReassignments =
			kaleoTimer.getKaleoTaskReassignments();

		if (kaleoTaskReassignments.isEmpty()) {
			return;
		}

		_taskAssignerUtil.reassignKaleoTask(
			kaleoTaskReassignments, executionContext);
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
	private TaskAssignerUtil _taskAssignerUtil;

	@Reference
	private TaskAssignmentSelectorRegistry _taskAssignmentSelectorRegistry;

}