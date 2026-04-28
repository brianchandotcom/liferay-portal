/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import com.liferay.ai.hub.internal.configuration.VertexAIConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

import java.util.Objects;

/**
 * @author João Victor Alves
 */
public class VertexAiGeminiStreamingChatModelUtil {

	public static VertexAiGeminiStreamingChatModel create(long companyId)
		throws ConfigurationException {

		VertexAIConfiguration vertexAIConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		String location = vertexAIConfiguration.location();

		VertexAiGeminiStreamingChatModel.VertexAiGeminiStreamingChatModelBuilder
			builder = VertexAiGeminiStreamingChatModel.builder(
			).location(
				location
			).modelName(
				vertexAIConfiguration.modelName()
			).project(
				vertexAIConfiguration.projectId()
			);

		if (Objects.equals(location, "global")) {
			builder.apiEndpoint("aiplatform.googleapis.com");
		}

		return builder.build();
	}

}