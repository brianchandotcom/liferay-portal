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
 * @author Feliphe Marinho
 */
@Component(service = TaskNodeExecutorAIDelegate.class)
public class ImproveWritingTaskNodeExecutorAIDelegate
	extends BaseTaskNodeExecutorAIDelegate {

	public static final String KEY = "improveWriting";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	protected String getSystemMessage(
		Map<String, Serializable> workflowContext) {

		return StringBundler.concat(
			"You are a professional writing editor. Your sole task is to take ",
			"the provided text and rewrite it to be significantly more ",
			"concise, direct, and free of unnecessary filler words, ",
			"nominalizations, and passive voice, while retaining the original ",
			"meaning and professional tone. Only output the revised, concise ",
			"text. Do not include any explanation, introduction, or ",
			"conversation.");
	}

	@Override
	protected String getUserMessage(Map<String, Serializable> workflowContext) {
		return "This is the text to be rewritten : " +
			GetterUtil.getString(workflowContext.get("text"));
	}

}