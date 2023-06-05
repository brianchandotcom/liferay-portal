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

import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutorException;
import com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util.RulesEngineExecutor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(enabled = false, service = ActionExecutor.class)
public class DRLActionExecutor implements ActionExecutor {

	@Override
	public void execute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws ActionExecutorException {

		try {
			_rulesEngineExecutor.executeAndMergeWorkflowContexts(
				executionContext, kaleoAction.getScript());
		}
		catch (Exception exception) {
			throw new ActionExecutorException(exception);
		}
	}

	@Override
	public String getActionExecutorKey() {
		return "drl";
	}

	@Reference
	private RulesEngineExecutor _rulesEngineExecutor;

}