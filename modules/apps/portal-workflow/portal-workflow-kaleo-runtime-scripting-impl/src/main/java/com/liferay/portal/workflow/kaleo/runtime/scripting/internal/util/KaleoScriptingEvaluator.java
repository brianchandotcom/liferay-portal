/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
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
@Component(service = KaleoScriptingEvaluator.class)
public class KaleoScriptingEvaluator {

	public Map<String, Object> execute(
			ExecutionContext executionContext, Set<String> outputObjects,
			String scriptLanguage, String script)
		throws PortalException {

		Map<String, Object> inputObjects =
			_scriptingContextBuilder.buildScriptingContext(executionContext);

		Map<String, Object> results = null;

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				KaleoScriptingEvaluator.class.getClassLoader())) {

			results = _scripting.eval(
				null, inputObjects, outputObjects, scriptLanguage, script);
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