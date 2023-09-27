/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.function.Supplier;

/**
 * @author Marcellus Tavares
 */
public class FirstWorkflowTaskAssigneeSupplier
	implements Supplier<WorkflowTaskAssignee> {

	public FirstWorkflowTaskAssigneeSupplier(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		_kaleoTaskInstanceToken = kaleoTaskInstanceToken;
	}

	@Override
	public WorkflowTaskAssignee get() {
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			_kaleoTaskInstanceToken.getFirstKaleoTaskAssignmentInstance();

		if (kaleoTaskAssignmentInstance == null) {
			return null;
		}

		return new WorkflowTaskAssignee(
			kaleoTaskAssignmentInstance.getAssigneeClassName(),
			kaleoTaskAssignmentInstance.getAssigneeClassPK());
	}

	private final KaleoTaskInstanceToken _kaleoTaskInstanceToken;

}