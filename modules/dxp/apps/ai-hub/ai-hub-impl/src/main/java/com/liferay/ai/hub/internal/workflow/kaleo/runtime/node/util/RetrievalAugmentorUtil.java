/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.ai.hub.internal.web.search.LiferayWebSearchEngine;
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
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
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
		FieldConfigBuilderFactory fieldConfigBuilderFactory,
		HighlightBuilderFactory highlightBuilderFactory,
		Map<String, String> kaleoNodeSettingValues, Locale locale,
		ObjectEntryManager objectEntryManager,
		SearchEngineAdapter searchEngineAdapter, long userId,
		Map<String, Serializable> workflowContext) {

		List<ContentRetriever> contentRetrievers = new ArrayList<>();

		ContentRetriever contentRetriever =
			_createElasticsearchContentRetriever(
				companyId, dtoConverterRegistry, fieldConfigBuilderFactory,
				highlightBuilderFactory, locale, objectEntryManager,
				searchEngineAdapter, userId, workflowContext);

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
		).contentInjector(
			DefaultContentInjector.builder(
			).metadataKeysToInclude(
				List.of("url")
			).build()
		).queryRouter(
			new DefaultQueryRouter(contentRetrievers)
		).build();
	}

	private static ContentRetriever _createElasticsearchContentRetriever(
		long companyId, DTOConverterRegistry dtoConverterRegistry,
		FieldConfigBuilderFactory fieldConfigBuilderFactory,
		HighlightBuilderFactory highlightBuilderFactory, Locale locale,
		ObjectEntryManager objectEntryManager,
		SearchEngineAdapter searchEngineAdapter, long userId,
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
				fieldConfigBuilderFactory, highlightBuilderFactory,
				TransformUtil.transform(
					contentRetrieversObjectEntries,
					contentRetriever -> GetterUtil.getString(
						contentRetriever.getPropertyValue("indexName")),
					String.class),
				query, searchEngineAdapter);
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
		FieldConfigBuilderFactory fieldConfigBuilderFactory,
		HighlightBuilderFactory highlightBuilderFactory, String[] indexNames,
		Query query, SearchEngineAdapter searchEngineAdapter) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(false);
		searchSearchRequest.setHighlight(
			highlightBuilderFactory.builder(
			).addFieldConfig(
				fieldConfigBuilderFactory.builder(
					"text_embedding"
				).build()
			).build());
		searchSearchRequest.setIndexNames(indexNames);
		searchSearchRequest.setQuery(
			QueriesUtil.wrapper(
				JSONFactoryUtil.createJSONObject(
				).put(
					"semantic",
					JSONFactoryUtil.createJSONObject(
					).put(
						"field", "text_embedding"
					).put(
						"query", query.text()
					)
				).toString()));
		searchSearchRequest.setStoredFields("text_embedding");

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<Content> contents = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Map<String, HighlightField> highlightFields =
				searchHit.getHighlightFieldsMap();

			HighlightField highlightField = highlightFields.get(
				"text_embedding");

			Metadata metadata = Metadata.from(
				"url", MapUtil.getString(searchHit.getSourcesMap(), "url"));

			for (String fragment : highlightField.getFragments()) {
				contents.add(
					Content.from(TextSegment.from(fragment, metadata)));
			}
		}

		return contents;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RetrievalAugmentorUtil.class);

}