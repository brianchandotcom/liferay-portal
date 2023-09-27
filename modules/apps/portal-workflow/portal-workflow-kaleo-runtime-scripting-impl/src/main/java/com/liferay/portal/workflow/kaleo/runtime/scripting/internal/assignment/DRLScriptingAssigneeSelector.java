/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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