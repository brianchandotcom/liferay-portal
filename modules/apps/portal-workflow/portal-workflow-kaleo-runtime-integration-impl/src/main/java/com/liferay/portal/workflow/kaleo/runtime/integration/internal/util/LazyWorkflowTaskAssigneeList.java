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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author Inácio Nery
 */
public class LazyWorkflowTaskAssigneeList
	extends AbstractList<WorkflowTaskAssignee> {

	public LazyWorkflowTaskAssigneeList(
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService) {

		_kaleoTaskInstanceToken = kaleoTaskInstanceToken;
		_kaleoTaskAssignmentInstanceLocalService =
			kaleoTaskAssignmentInstanceLocalService;

		_firstWorkflowTaskAssigneeSupplier =
			new FirstWorkflowTaskAssigneeSupplier(kaleoTaskInstanceToken);
		_workflowTaskAssigneesSupplier = new WorkflowTaskAssigneesSupplier(
			kaleoTaskInstanceToken);
	}

	@Override
	public WorkflowTaskAssignee get(int index) {
		if (index == 0) {
			Optional<WorkflowTaskAssignee> workflowTaskAssigneeOptional =
				_firstWorkflowTaskAssigneeSupplier.get();

			return workflowTaskAssigneeOptional.orElseThrow(
				IndexOutOfBoundsException::new);
		}

		initWorkflowTaskAssignees();

		return _workflowTaskAssignees.get(index);
	}

	@Override
	public Iterator<WorkflowTaskAssignee> iterator() {
		initWorkflowTaskAssignees();

		return _workflowTaskAssignees.iterator();
	}

	@Override
	public int size() {
		if (_workflowTaskAssignees != null) {
			return _workflowTaskAssignees.size();
		}

		return _kaleoTaskAssignmentInstanceLocalService.
			getKaleoTaskAssignmentInstancesCount(
				_kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	protected void initWorkflowTaskAssignees() {
		if (_workflowTaskAssignees == null) {
			_workflowTaskAssignees = _workflowTaskAssigneesSupplier.get();
		}
	}

	private final FirstWorkflowTaskAssigneeSupplier
		_firstWorkflowTaskAssigneeSupplier;
	private final KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;
	private final KaleoTaskInstanceToken _kaleoTaskInstanceToken;
	private List<WorkflowTaskAssignee> _workflowTaskAssignees;
	private final WorkflowTaskAssigneesSupplier _workflowTaskAssigneesSupplier;

}