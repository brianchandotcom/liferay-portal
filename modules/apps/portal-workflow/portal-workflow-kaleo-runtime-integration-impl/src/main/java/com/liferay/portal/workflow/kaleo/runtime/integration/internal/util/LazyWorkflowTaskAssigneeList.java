/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

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
			WorkflowTaskAssignee workflowTaskAssignee =
				_firstWorkflowTaskAssigneeSupplier.get();

			if (workflowTaskAssignee == null) {
				throw new IndexOutOfBoundsException();
			}

			return workflowTaskAssignee;
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