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

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.util.ScriptingContextBuilder;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = KaleoScriptingEvaluator.class)
public class KaleoScriptingEvaluator {

	public Map<String, Object> execute(
			ExecutionContext executionContext, Set<String> outputObjects,
			String scriptLanguage, String script)
		throws PortalException {

		Map<String, Object> inputObjects =
			_scriptingContextBuilder.buildScriptingContext(executionContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Map<String, Object> results = null;

		try {
			currentThread.setContextClassLoader(classLoader);

			results = _scripting.eval(
				null, inputObjects, outputObjects, scriptLanguage, script);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		Map<String, Serializable> resultsWorkflowContext =
			(Map<String, Serializable>)results.get(
				WorkflowContextUtil.WORKFLOW_CONTEXT_NAME);

		WorkflowContextUtil.mergeWorkflowContexts(
			executionContext, resultsWorkflowContext);

		return results;
	}

	@Reference
	private Scripting _scripting;

	@Reference
	private ScriptingContextBuilder _scriptingContextBuilder;

}