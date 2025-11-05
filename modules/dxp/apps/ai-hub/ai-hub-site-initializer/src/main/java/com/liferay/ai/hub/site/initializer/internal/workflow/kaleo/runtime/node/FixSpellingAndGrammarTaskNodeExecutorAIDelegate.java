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
public class FixSpellingAndGrammarTaskNodeExecutorAIDelegate
	extends BaseTaskNodeExecutorAIDelegate {

	public static final String KEY = "fixSpellingAndGrammar";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	protected String getSystemMessage(
		Map<String, Serializable> workflowContext) {

		return StringBundler.concat(
			"You are an expert linguistic editor. Your sole task is to ",
			"correct all grammatical, spelling, and punctuation errors in the ",
			"provided text while preserving its meaning, tone, and style. Do ",
			"not alter structure or wording beyond what is necessary for ",
			"grammatical precision and natural fluency. Output only the ",
			"corrected text, with no explanations or commentary. If the text ",
			"is already correct, return it unchanged.");
	}

	@Override
	protected String getUserMessage(Map<String, Serializable> workflowContext) {
		return "This is the text to be fixed: " + workflowContext.get("text");
	}

}