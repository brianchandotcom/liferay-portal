/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util.filter;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Inácio Nery
 */
public class WorkflowDefinitionScopePredicate
	implements Predicate<WorkflowDefinition> {

	public WorkflowDefinitionScopePredicate(String scope) {
		_scope = scope;
	}

	@Override
	public boolean test(WorkflowDefinition workflowDefinition) {
		if (Validator.isNull(workflowDefinition.getScope())) {
			return true;
		}

		return Objects.equals(_scope, workflowDefinition.getScope());
	}

	private final String _scope;

}