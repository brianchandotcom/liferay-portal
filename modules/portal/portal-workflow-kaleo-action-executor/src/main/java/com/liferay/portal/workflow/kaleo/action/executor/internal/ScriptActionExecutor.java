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

package com.liferay.portal.workflow.kaleo.action.executor.internal;

import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutorException;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.util.ScriptingContextBuilderUtil;
import com.liferay.portal.workflow.kaleo.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portal.workflow.kaleo.action.executor.language=beanshell",
		"com.liferay.portal.workflow.kaleo.action.executor.language=groovy",
		"com.liferay.portal.workflow.kaleo.action.executor.language=javascript",
		"com.liferay.portal.workflow.kaleo.action.executor.language=python",
		"com.liferay.portal.workflow.kaleo.action.executor.language=ruby"
	},
	service = ActionExecutor.class
)
public class ScriptActionExecutor implements ActionExecutor {

	public ScriptActionExecutor() {
		_outputObjects.add(WorkflowContextUtil.WORKFLOW_CONTEXT_NAME);
	}

	@Override
	public void execute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws ActionExecutorException {

		try {
			doExecute(kaleoAction, executionContext);
		}
		catch (Exception e) {
			throw new ActionExecutorException(e);
		}
	}

	public void setOutputObjects(Set<String> outputObjects) {
		_outputObjects.addAll(outputObjects);
	}

	protected void doExecute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws Exception {

		Map<String, Object> inputObjects =
			ScriptingContextBuilderUtil.buildScriptingContext(executionContext);

		String[] scriptRequiredContexts = StringUtil.split(
			kaleoAction.getScriptRequiredContexts());

		Map<String, Object> results = _scripting.eval(
			null, inputObjects, _outputObjects, kaleoAction.getScriptLanguage(),
			kaleoAction.getScript(), scriptRequiredContexts);

		Map<String, Serializable> resultsWorkflowContext =
			(Map<String, Serializable>)results.get(
				WorkflowContextUtil.WORKFLOW_CONTEXT_NAME);

		WorkflowContextUtil.mergeWorkflowContexts(
			executionContext, resultsWorkflowContext);
	}

	@Reference(unbind = "-")
	protected void setScripting(Scripting scripting) {
		_scripting = scripting;
	}

	private final Set<String> _outputObjects = new HashSet<>();
	private volatile Scripting _scripting;

}