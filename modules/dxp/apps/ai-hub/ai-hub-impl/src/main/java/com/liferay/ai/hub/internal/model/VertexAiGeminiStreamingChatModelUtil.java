/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import dev.langchain4j.model.vertexai.gemini.VertexAiGeminiStreamingChatModel;

/**
 * @author João Victor Alves
 */
public class VertexAiGeminiStreamingChatModelUtil {

	public static VertexAiGeminiStreamingChatModel create() {
		return VertexAiGeminiStreamingChatModel.builder(
		).location(
			"europe-central2"
		).modelName(
			"gemini-2.5-flash-lite"
		).project(
			"ai-hub-liferay"
		).build();
	}

}