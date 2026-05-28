/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import com.liferay.ai.hub.configuration.VertexAIConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import dev.langchain4j.model.vertexai.gemini.HarmCategory;
import dev.langchain4j.model.vertexai.gemini.SafetyThreshold;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class VertexAiGeminiUtil {

	public static VertexAiGeminiChatModel createVertexAiGeminiChatModel(
			long companyId)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		VertexAiGeminiChatModel.VertexAiGeminiChatModelBuilder builder =
			VertexAiGeminiChatModel.builder();

		if (Objects.equals(vertexAIConfiguration.location(), "global")) {
			builder.apiEndpoint("aiplatform.googleapis.com");
		}

		return builder.location(
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
			createVertexAiGeminiStreamingChatModel(long companyId)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		VertexAiGeminiStreamingChatModel.VertexAiGeminiStreamingChatModelBuilder
			builder = VertexAiGeminiStreamingChatModel.builder();

		if (Objects.equals(vertexAIConfiguration.location(), "global")) {
			builder.apiEndpoint("aiplatform.googleapis.com");
		}

		return builder.location(
			vertexAIConfiguration.location()
		).modelName(
			vertexAIConfiguration.modelName()
		).project(
			vertexAIConfiguration.projectId()
		).safetySettings(
			_safetyThresholds
		).build();
	}

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