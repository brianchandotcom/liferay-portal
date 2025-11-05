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
public class MakeShorterTaskNodeExecutorAIDelegate
	extends BaseTaskNodeExecutorAIDelegate {

	public static final String KEY = "makeShorter";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	protected String getSystemMessage(
		Map<String, Serializable> workflowContext) {

		return StringBundler.concat(
			"You are an expert linguistic editor. Your sole task is to reduce ",
			"the length of the provided text while preserving all essential ",
			"information, key points, and original intent. Remove redundancy, ",
			"filler, and unnecessary detail without changing meaning, tone, ",
			"or clarity. If the text is already concise and cannot be ",
			"shortened without losing important content, return it unchanged. ",
			"Output only the shortened text, with no explanations or ",
			"commentary.");
	}

	@Override
	protected String getUserMessage(Map<String, Serializable> workflowContext) {
		return "This is the text to be shortened: " +
			GetterUtil.getString(workflowContext.get("text"));
	}

}