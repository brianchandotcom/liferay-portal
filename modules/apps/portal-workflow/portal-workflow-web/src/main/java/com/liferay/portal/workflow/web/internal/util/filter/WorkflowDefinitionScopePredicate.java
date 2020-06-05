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