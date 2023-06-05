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

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.condition;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.condition.ConditionEvaluator;
import com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util.RulesEngineExecutor;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false, property = "scripting.language=drl",
	service = ConditionEvaluator.class
)
public class DRLConditionEvaluator implements ConditionEvaluator {

	@Override
	public String evaluate(
			KaleoCondition kaleoCondition, ExecutionContext executionContext)
		throws PortalException {

		Map<String, ?> results =
			_rulesEngineExecutor.executeAndMergeWorkflowContexts(
				executionContext, kaleoCondition.getScript());

		String returnValue = (String)results.get(_RETURN_VALUE);

		if (returnValue != null) {
			return returnValue;
		}

		throw new IllegalStateException(
			"Conditional did not return value for script " +
				kaleoCondition.getScript());
	}

	private static final String _RETURN_VALUE = "returnValue";

	@Reference
	private RulesEngineExecutor _rulesEngineExecutor;

}