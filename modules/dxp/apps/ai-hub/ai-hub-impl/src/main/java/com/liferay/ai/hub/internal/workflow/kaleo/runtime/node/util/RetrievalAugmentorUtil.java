/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.internal.web.search.LiferayWebSearchEngine;
import com.liferay.ai.hub.model.VertexAIEmbeddingModel;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;

import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class RetrievalAugmentorUtil {

	public static RetrievalAugmentor createRetrievalAugmentor(
		long companyId, DTOConverterRegistry dtoConverterRegistry,
		Map<String, String> kaleoNodeSettingValues, Locale locale,
		ObjectEntryManager objectEntryManager,
		SearchEngineAdapter searchEngineAdapter, long userId,
		VertexAIEmbeddingModel vertexAIEmbeddingModel,
		Map<String, Serializable> workflowContext) {

		List<ContentRetriever> contentRetrievers = new ArrayList<>();

		ContentRetriever contentRetriever =
			_createElasticsearchContentRetriever(
				companyId, dtoConverterRegistry, locale, objectEntryManager,
				searchEngineAdapter, userId, vertexAIEmbeddingModel,
				workflowContext);

		if (contentRetriever != null) {
			contentRetrievers.add(contentRetriever);
		}

		contentRetriever = _createLiferayWebSearchContentRetriever(
			companyId, kaleoNodeSettingValues, workflowContext);

		if (contentRetriever != null) {
			contentRetrievers.add(contentRetriever);
		}

		if (contentRetrievers.isEmpty()) {
			return null;
		}

		return DefaultRetrievalAugmentor.builder(
		).queryRouter(
			new DefaultQueryRouter(contentRetrievers)
		).build();
	}

	private static ContentRetriever _createElasticsearchContentRetriever(
		long companyId, DTOConverterRegistry dtoConverterRegistry,
		Locale locale, ObjectEntryManager objectEntryManager,
		SearchEngineAdapter searchEngineAdapter, long userId,
		VertexAIEmbeddingModel vertexAIEmbeddingModel,
		Map<String, Serializable> workflowContext) {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getAndSetNestedFieldsContext(
				new NestedFieldsContext(
					1, List.of("agentDefinitionsToContentRetrievers")));

		try {
			String agentDefinitionExternalReferenceCode = GetterUtil.getString(
				workflowContext.get("agentDefinitionExternalReferenceCode"));

			if (Validator.isNull(agentDefinitionExternalReferenceCode)) {
				return null;
			}

			ObjectEntry agentDefinitionObjectEntry =
				objectEntryManager.getObjectEntry(
					companyId,
					new DefaultDTOConverterContext(
						false, Map.of(), dtoConverterRegistry, null, locale,
						null, UserLocalServiceUtil.getUserById(userId)),
					agentDefinitionExternalReferenceCode,
					ObjectDefinitionLocalServiceUtil.
						fetchObjectDefinitionByExternalReferenceCode(
							"L_AI_HUB_AGENT_DEFINITION", companyId),
					null);

			ObjectEntry[] contentRetrieversObjectEntries =
				(ObjectEntry[])agentDefinitionObjectEntry.getPropertyValue(
					"agentDefinitionsToContentRetrievers");

			if (ArrayUtil.isEmpty(contentRetrieversObjectEntries)) {
				return null;
			}

			return query -> _search(
				TransformUtil.transform(
					contentRetrieversObjectEntries,
					contentRetriever -> GetterUtil.getString(
						contentRetriever.getPropertyValue("indexName")),
					String.class),
				query, searchEngineAdapter, vertexAIEmbeddingModel);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
		finally {
			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				nestedFieldsContext);
		}
	}

	private static ContentRetriever _createLiferayWebSearchContentRetriever(
		long companyId, Map<String, String> kaleoNodeSettingValues,
		Map<String, Serializable> workflowContext) {

		if (kaleoNodeSettingValues.get("rag") == null) {
			return null;
		}

		try {
			JSONObject ragJSONObject = JSONFactoryUtil.createJSONObject(
				kaleoNodeSettingValues.get("rag"));

			JSONObject contentRetrieverJSONObject = ragJSONObject.getJSONObject(
				"contentRetriever");

			if (Objects.equals(
					contentRetrieverJSONObject.getString("key"), "liferay")) {

				return WebSearchContentRetriever.builder(
				).webSearchEngine(
					new LiferayWebSearchEngine(
						GetterUtil.getString(
							workflowContext.get("accessToken")),
						contentRetrieverJSONObject.getString(
							"blueprintExternalReferenceCode"),
						companyId,
						GetterUtil.getString(workflowContext.get("userToken")))
				).build();
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return null;
	}

	private static List<Content> _search(
		String[] indexNames, Query query,
		SearchEngineAdapter searchEngineAdapter,
		VertexAIEmbeddingModel vertexAIEmbeddingModel) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
		).put(
			"knn",
			JSONFactoryUtil.createJSONObject(
			).put(
				"field", "text_embedding_3072"
			).put(
				"k", 10
			).put(
				"num_candidates", 100
			).put(
				"query_vector",
				() -> JSONFactoryUtil.createJSONArray(
					vertexAIEmbeddingModel.embed(query.text()))
			)
		);

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setFetchSourceIncludes(new String[] {"text"});
		searchSearchRequest.setIndexNames(indexNames);
		searchSearchRequest.setQuery(
			QueriesUtil.wrapper(jsonObject.toString()));
		searchSearchRequest.setSize(10);

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> Content.from(
				MapUtil.getString(searchHit.getSourcesMap(), "text")));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RetrievalAugmentorUtil.class);

}