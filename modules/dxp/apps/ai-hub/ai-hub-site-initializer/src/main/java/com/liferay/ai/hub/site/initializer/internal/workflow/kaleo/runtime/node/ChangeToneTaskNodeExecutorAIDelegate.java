/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.workflow.kaleo.runtime.node;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.workflow.kaleo.runtime.node.TaskNodeExecutorAIDelegate;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(service = TaskNodeExecutorAIDelegate.class)
public class ChangeToneTaskNodeExecutorAIDelegate
	extends BaseTaskNodeExecutorAIDelegate {

	public static final String KEY = "changeTone";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	protected String getSystemMessage(
		Map<String, Serializable> workflowContext) {

		return StringBundler.concat(
			"You are an expert linguistic editor. Your sole task is to adjust ",
			"the tone of the provided text to be more ",
			workflowContext.get("tone"), ". Modify vocabulary, phrasing, and ",
			"sentence structure as needed while preserving the original ",
			"meaning, intent, and clarity. If the text already matches this ",
			"tone, return it unchanged. Output only the rewritten text, with ",
			"no explanations or commentary.");
	}

	@Override
	protected String getUserMessage(Map<String, Serializable> workflowContext) {
		return StringBundler.concat(
			"This is the text whose tone was changed to be ",
			workflowContext.get("tone"), ": ", workflowContext.get("text"));
	}

}