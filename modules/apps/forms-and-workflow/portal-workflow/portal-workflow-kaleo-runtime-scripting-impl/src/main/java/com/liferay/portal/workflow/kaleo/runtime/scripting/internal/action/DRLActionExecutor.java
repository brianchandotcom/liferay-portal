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

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.action;

import com.liferay.portal.kernel.resource.StringResourceRetriever;
import com.liferay.portal.rules.engine.Fact;
import com.liferay.portal.rules.engine.Query;
import com.liferay.portal.rules.engine.RulesEngine;
import com.liferay.portal.rules.engine.RulesResourceRetriever;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutorException;
import com.liferay.portal.workflow.kaleo.runtime.util.RulesContextBuilder;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false, immediate = true,
	property = "com.liferay.portal.workflow.kaleo.runtime.action.executor.language=drl",
	service = ActionExecutor.class
)
public class DRLActionExecutor implements ActionExecutor {

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

	protected void doExecute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws Exception {

		List<Fact<?>> facts = _rulesContextBuilder.buildRulesContext(
			executionContext);

		RulesResourceRetriever rulesResourceRetriever =
			new RulesResourceRetriever(
				new StringResourceRetriever(kaleoAction.getScript()));

		Map<String, ?> results = _rulesEngine.execute(
			rulesResourceRetriever, facts, Query.createStandardQuery());

		Map<String, Serializable> resultsWorkflowContext =
			(Map<String, Serializable>)results.get(
				WorkflowContextUtil.WORKFLOW_CONTEXT_NAME);

		WorkflowContextUtil.mergeWorkflowContexts(
			executionContext, resultsWorkflowContext);
	}

	@Reference
	private RulesContextBuilder _rulesContextBuilder;

	@Reference
	private RulesEngine _rulesEngine;

}