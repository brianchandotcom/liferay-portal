/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Marcellus Tavares
 */
public class WorkflowTaskAssigneesSupplier
	implements Supplier<List<WorkflowTaskAssignee>> {

	public WorkflowTaskAssigneesSupplier(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		_kaleoTaskInstanceToken = kaleoTaskInstanceToken;
	}

	@Override
	public List<WorkflowTaskAssignee> get() {
		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			_kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances();

		List<WorkflowTaskAssignee> workflowTaskAssignees = new ArrayList<>(
			kaleoTaskAssignmentInstances.size());

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				kaleoTaskAssignmentInstances) {

			WorkflowTaskAssignee workflowTaskAssignee =
				new WorkflowTaskAssignee(
					kaleoTaskAssignmentInstance.getAssigneeClassName(),
					kaleoTaskAssignmentInstance.getAssigneeClassPK());

			workflowTaskAssignees.add(workflowTaskAssignee);
		}

		return workflowTaskAssignees;
	}

	private final KaleoTaskInstanceToken _kaleoTaskInstanceToken;

}