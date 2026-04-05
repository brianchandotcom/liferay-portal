/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.model;

import com.liferay.ai.hub.model.VertexAIEmbeddingModel;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiEmbeddingModel;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(service = VertexAIEmbeddingModel.class)
public class VertexAIEmbeddingModelImpl implements VertexAIEmbeddingModel {

	@Override
	public List<Float> embed(String text) {
		VertexAiEmbeddingModel vertexAiEmbeddingModel =
			VertexAiEmbeddingModel.builder(
			).location(
				"europe-central2"
			).modelName(
				"gemini-embedding-001"
			).project(
				"ai-hub-liferay"
			).publisher(
				"google"
			).build();

		Response<Embedding> response = vertexAiEmbeddingModel.embed(
			TextSegment.from(text));

		Embedding embedding = response.content();

		return embedding.vectorAsList();
	}

}