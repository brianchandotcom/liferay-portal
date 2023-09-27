/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util.filter;

import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.function.Predicate;

/**
 * @author Adam Brandizzi
 */
public class WorkflowDefinitionActivePredicate
	implements Predicate<WorkflowDefinition> {

	public WorkflowDefinitionActivePredicate(int status) {
		_status = status;
	}

	@Override
	public boolean test(WorkflowDefinition workflowDefinition) {
		if (_status == WorkflowConstants.STATUS_ANY) {
			return true;
		}
		else if (_status == WorkflowConstants.STATUS_APPROVED) {
			return workflowDefinition.isActive();
		}

		return !workflowDefinition.isActive();
	}

	private final int _status;

}