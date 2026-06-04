/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.internal.audit.constants.AIHubEventTypes;
import com.liferay.ai.hub.internal.constants.AIHubDestinationNames;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.ChatResponseMetadata;
import dev.langchain4j.model.output.TokenUsage;

import java.util.Date;

/**
 * @author João Victor Alves
 */
public class MessageUtil {

	public static void sendMessage(
		ChatResponse chatResponse, KaleoInstanceToken kaleoInstanceToken,
		String prompt, ServiceContext serviceContext, String userMessage) {

		Message message = new Message();

		ChatResponseMetadata chatResponseMetadata = chatResponse.metadata();

		TokenUsage tokenUsage = chatResponseMetadata.tokenUsage();

		message.put(
			"additionalInformation",
			JSONUtil.put(
				"inputTokenCount", String.valueOf(tokenUsage.inputTokenCount())
			).put(
				"outputTokenCount",
				String.valueOf(tokenUsage.outputTokenCount())
			).put(
				"promptInput", prompt
			).put(
				"thoughtsTokenCount",
				() ->
					tokenUsage.totalTokenCount() -
						tokenUsage.inputTokenCount() -
							tokenUsage.outputTokenCount()
			).put(
				"totalTokenCount", String.valueOf(tokenUsage.totalTokenCount())
			).put(
				"userMessageInput", userMessage
			));

		message.put("createDate", new Date());
		message.put(
			"eventType",
			AIHubEventTypes.AI_HUB_AGENT_INSTANCE_COMPLETE_PARTIALLY);
		message.put("kaleoInstanceToken", kaleoInstanceToken);
		message.put("serviceContext", serviceContext);
		message.put("userId", serviceContext.getUserId());
		message.put(
			"workflowInstanceId", kaleoInstanceToken.getKaleoInstanceId());

		MessageBusUtil.sendMessage(
			AIHubDestinationNames.AI_HUB_AGENT_INSTANCE, message);
	}

}