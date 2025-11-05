/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.kaleo.runtime.node.TaskNodeExecutorAIDelegate;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(service = TaskNodeExecutorAIDelegate.class)
public class MakeLongerTaskNodeExecutorAIDelegate
	extends BaseTaskNodeExecutorAIDelegate {

	@Override
	public String getKey() {
		return "makeLonger";
	}

	@Override
	protected String getSystemMessage(
		Map<String, Serializable> workflowContext) {

		return StringBundler.concat(
			"You are an expert linguistic enhancer. Expand the provided text ",
			"by adding relevant and natural details that clarify or enrich ",
			"its meaning. Keep the original tone, intent, and structure. ",
			"Avoid unnecessary embellishment, repetition, or creative ",
			"exaggeration. Output only the expanded text.");
	}

	@Override
	protected String getUserMessage(Map<String, Serializable> workflowContext) {
		return "This is the text to be detailed: " +
			GetterUtil.getString(workflowContext.get("text"));
	}

}