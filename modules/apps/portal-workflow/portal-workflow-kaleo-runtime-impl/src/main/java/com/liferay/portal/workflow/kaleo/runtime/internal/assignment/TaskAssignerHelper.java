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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelectorRegistry;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = TaskAssignerHelper.class)
public class TaskAssignerHelper {

	public void reassignKaleoTask(
			List<KaleoTaskAssignment> kaleoTaskAssignments,
			ExecutionContext executionContext)
		throws PortalException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			executionContext.getKaleoTaskInstanceToken();

		List<KaleoTaskAssignmentInstance> previousTaskAssignmentInstances =
			kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances();

		List<KaleoTaskAssignment> reassignedKaleoTaskAssignments =
			new ArrayList<>();

		for (KaleoTaskAssignment kaleoTaskAssignment : kaleoTaskAssignments) {
			KaleoTaskAssignmentSelector kaleoTaskAssignmentSelector =
				_kaleoTaskAssignmentSelectorRegistry.
					getKaleoTaskAssignmentSelector(
						kaleoTaskAssignment.getAssigneeClassName());

			reassignedKaleoTaskAssignments.addAll(
				kaleoTaskAssignmentSelector.getKaleoTaskAssignments(
					kaleoTaskAssignment, executionContext));
		}

		kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.assignKaleoTaskInstanceToken(
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				reassignedKaleoTaskAssignments,
				executionContext.getWorkflowContext(),
				executionContext.getServiceContext());

		_kaleoLogLocalService.addTaskAssignmentKaleoLog(
			previousTaskAssignmentInstances, kaleoTaskInstanceToken, null,
			executionContext.getWorkflowContext(),
			executionContext.getServiceContext());
	}

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoTaskAssignmentSelectorRegistry
		_kaleoTaskAssignmentSelectorRegistry;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

}