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

import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionConstants;

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
		if (_status == WorkflowDefinitionConstants.STATUS_ALL) {
			return true;
		}
		else if (_status == WorkflowDefinitionConstants.STATUS_PUBLISHED) {
			return workflowDefinition.isActive();
		}

		return !workflowDefinition.isActive();
	}

	private final int _status;

}