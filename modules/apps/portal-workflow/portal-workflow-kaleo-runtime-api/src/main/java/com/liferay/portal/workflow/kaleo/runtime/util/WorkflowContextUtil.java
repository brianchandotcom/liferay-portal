/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class WorkflowContextUtil {

	public static final String WORKFLOW_CONTEXT_NAME = "workflowContext";

	public static String convert(Map<String, Serializable> workflowContext) {
		if (workflowContext == null) {
			return StringPool.BLANK;
		}

		return JSONFactoryUtil.serialize(workflowContext);
	}

	public static Map<String, Serializable> convert(String json) {
		if (Validator.isNull(json)) {
			return new HashMap<>();
		}

		return (Map<String, Serializable>)JSONFactoryUtil.deserialize(json);
	}

	public static void mergeWorkflowContexts(
		ExecutionContext executionContext,
		Map<String, Serializable> workflowContext) {

		if ((workflowContext != null) && !workflowContext.isEmpty()) {
			Map<String, Serializable> executionContextWorkflowContext =
				executionContext.getWorkflowContext();

			executionContextWorkflowContext.putAll(workflowContext);
		}
	}

}