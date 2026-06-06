/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import com.liferay.ai.hub.configuration.VertexAIConfiguration;
import com.liferay.ai.hub.internal.langchain4j.model.chat.listener.AIHubChatModelListenerImpl;
import com.liferay.ai.hub.quota.QuotaManager;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import dev.langchain4j.model.vertexai.gemini.HarmCategory;
import dev.langchain4j.model.vertexai.gemini.SafetyThreshold;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Feliphe Marinho
 */
public class VertexAiGeminiUtil {

	public static final String TOOL_CALLING_LOCATION = "europe-west1";

	public static final String TOOL_CALLING_MODEL_NAME = "gemini-2.5-flash";

	public static VertexAiGeminiChatModel createVertexAiGeminiChatModel(
			QuotaManager quotaManager, ServiceContext serviceContext)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, serviceContext.getCompanyId());

		VertexAiGeminiChatModel.VertexAiGeminiChatModelBuilder builder =
			VertexAiGeminiChatModel.builder();

		_setAPIEndpoint(builder::apiEndpoint, vertexAIConfiguration.location());

		return builder.listeners(
			Collections.singletonList(
				new AIHubChatModelListenerImpl(quotaManager, serviceContext))
		).location(
			vertexAIConfiguration.location()
		).modelName(
			vertexAIConfiguration.modelName()
		).project(
			vertexAIConfiguration.projectId()
		).safetySettings(
			_safetyThresholds
		).build();
	}

	public static VertexAiGeminiStreamingChatModel
			createVertexAiGeminiStreamingChatModel(
				QuotaManager quotaManager, ServiceContext serviceContext)
		throws ConfigurationException {

		return createVertexAiGeminiStreamingChatModel(
			null, quotaManager, serviceContext);
	}

	public static VertexAiGeminiStreamingChatModel
			createVertexAiGeminiStreamingChatModel(
				String modelName, QuotaManager quotaManager,
				ServiceContext serviceContext)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, serviceContext.getCompanyId());

		String location = TOOL_CALLING_LOCATION;

		if (Validator.isNull(modelName)) {
			location = vertexAIConfiguration.location();
			modelName = vertexAIConfiguration.modelName();
		}

		VertexAiGeminiStreamingChatModel.VertexAiGeminiStreamingChatModelBuilder
			builder = VertexAiGeminiStreamingChatModel.builder();

		_setAPIEndpoint(builder::apiEndpoint, location);

		return builder.listeners(
			Collections.singletonList(
				new AIHubChatModelListenerImpl(quotaManager, serviceContext))
		).location(
			location
		).modelName(
			modelName
		).project(
			vertexAIConfiguration.projectId()
		).safetySettings(
			_safetyThresholds
		).build();
	}

	private static void _setAPIEndpoint(
		Consumer<String> apiEndpointConsumer, String location) {

		if (Objects.equals(location, "global")) {
			apiEndpointConsumer.accept("aiplatform.googleapis.com");
		}
		else if (Validator.isNotNull(location) &&
				 _multiRegionLocations.contains(location)) {

			apiEndpointConsumer.accept(
				"aiplatform." + location + ".rep.googleapis.com");
		}
	}

	private static final Set<String> _multiRegionLocations = Set.of("eu", "us");
	private static final Map<HarmCategory, SafetyThreshold> _safetyThresholds =
		Map.of(
			HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
			SafetyThreshold.BLOCK_MEDIUM_AND_ABOVE,
			HarmCategory.HARM_CATEGORY_HARASSMENT,
			SafetyThreshold.BLOCK_MEDIUM_AND_ABOVE,
			HarmCategory.HARM_CATEGORY_HATE_SPEECH,
			SafetyThreshold.BLOCK_MEDIUM_AND_ABOVE,
			HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT,
			SafetyThreshold.BLOCK_MEDIUM_AND_ABOVE);

}