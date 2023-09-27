/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.scripting.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.util.ScriptingContextBuilder;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = ScriptingContextBuilder.class)
public class ScriptingContextBuilderImpl implements ScriptingContextBuilder {

	@Override
	public Map<String, Object> buildScriptingContext(
			ExecutionContext executionContext)
		throws PortalException {

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		if (workflowContext == null) {
			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

			KaleoInstance kaleoInstance = kaleoInstanceToken.getKaleoInstance();

			workflowContext = WorkflowContextUtil.convert(
				kaleoInstance.getWorkflowContext());
		}

		Map<String, Object> inputObjects =
			HashMapBuilder.<String, Object>putAll(
				workflowContext
			).put(
				"kaleoInstanceToken", executionContext.getKaleoInstanceToken()
			).put(
				"workflowContext", workflowContext
			).build();

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			executionContext.getKaleoTaskInstanceToken();

		if (kaleoTaskInstanceToken != null) {
			inputObjects.put("kaleoTaskInstanceToken", kaleoTaskInstanceToken);

			KaleoTask kaleoTask = kaleoTaskInstanceToken.getKaleoTask();

			inputObjects.put("taskName", kaleoTask.getName());

			if (kaleoTaskInstanceToken.getCompletionUserId() != 0) {
				inputObjects.put(
					"userId", kaleoTaskInstanceToken.getCompletionUserId());
			}
			else {
				inputObjects.put("userId", kaleoTaskInstanceToken.getUserId());
			}

			inputObjects.put(
				"workflowTaskAssignees",
				_kaleoWorkflowModelConverter.getWorkflowTaskAssignees(
					kaleoTaskInstanceToken));
		}
		else {
			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

			inputObjects.put("userId", kaleoInstanceToken.getUserId());
		}

		if (executionContext.getKaleoTimerInstanceToken() != null) {
			inputObjects.put(
				"kaleoTimerInstanceToken",
				executionContext.getKaleoTimerInstanceToken());
		}

		return inputObjects;
	}

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

}