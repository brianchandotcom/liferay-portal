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

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.assignment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.ScriptingAssigneeSelector;
import com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util.RulesEngineExecutor;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false, property = "scripting.language=drl",
	service = ScriptingAssigneeSelector.class
)
public class DRLScriptingAssigneeSelector implements ScriptingAssigneeSelector {

	@Override
	public Map<String, ?> getAssignees(
			ExecutionContext executionContext,
			KaleoTaskAssignment kaleoTaskAssignment)
		throws PortalException {

		return _rulesEngineExecutor.executeAndMergeWorkflowContexts(
			executionContext, kaleoTaskAssignment.getAssigneeScript());
	}

	@Reference
	private RulesEngineExecutor _rulesEngineExecutor;

}