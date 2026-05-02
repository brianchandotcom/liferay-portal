/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import com.liferay.ai.hub.internal.configuration.VertexAIConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiChatModel;
import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class VertexAiGeminiUtil {

	public static VertexAiGeminiChatModel createChatModel(long companyId)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		String location = vertexAIConfiguration.location();

		VertexAiGeminiChatModel.VertexAiGeminiChatModelBuilder builder =
			VertexAiGeminiChatModel.builder();

		if (Objects.equals(location, "global")) {
			builder.apiEndpoint("aiplatform.googleapis.com");
		}

		builder.location(
			location
		).modelName(
			vertexAIConfiguration.modelName()
		).project(
			vertexAIConfiguration.projectId()
		);

		return builder.build();
	}

	public static VertexAiGeminiStreamingChatModel createStreamingChatModel(
			long companyId)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		String location = vertexAIConfiguration.location();

		VertexAiGeminiStreamingChatModel.VertexAiGeminiStreamingChatModelBuilder
			builder = VertexAiGeminiStreamingChatModel.builder();

		if (Objects.equals(location, "global")) {
			builder.apiEndpoint("aiplatform.googleapis.com");
		}

		builder.location(
			location
		).modelName(
			vertexAIConfiguration.modelName()
		).project(
			vertexAIConfiguration.projectId()
		);

		return builder.build();
	}

}