/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.hits;

import co.elastic.clients.elasticsearch.core.explain.Explanation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch8.internal.document.FieldsTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.highlight.HighlightFieldBuilder;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class HitsMetadataTranslator {

	public HitsMetadataTranslator(
		SearchHitBuilderFactory searchHitBuilderFactory,
		SearchHitsBuilderFactory searchHitsBuilderFactory) {

		_searchHitBuilderFactory = searchHitBuilderFactory;
		_searchHitsBuilderFactory = searchHitsBuilderFactory;
	}

	public SearchHits translate(HitsMetadata<JsonData> hitsMetadata) {
		return translate(null, hitsMetadata);
	}

	public SearchHits translate(
		String alternateUidFieldName, HitsMetadata<JsonData> hitsMetadata) {

		SearchHitsBuilder searchHitsBuilder =
			_searchHitsBuilderFactory.getSearchHitsBuilder();

		List<Hit<JsonData>> hits = hitsMetadata.hits();

		List<SearchHit> searchHits = new ArrayList<>(hits.size());

		for (Hit<JsonData> hit : hits) {
			searchHits.add(translate(alternateUidFieldName, hit));
		}

		TotalHits totalHits = hitsMetadata.total();

		return searchHitsBuilder.addSearchHits(
			searchHits
		).maxScore(
			ConversionUtil.toFloat(hitsMetadata.maxScore(), 0.0F)
		).totalHits(
			totalHits.value()
		).build();
	}

	protected SearchHit translate(
		String alternateUidFieldName, Hit<JsonData> hit) {

		SearchHitBuilder searchHitBuilder =
			_searchHitBuilderFactory.getSearchHitBuilder();

		return searchHitBuilder.addHighlightFields(
			_translateHighlightFields(hit.highlight())
		).addSources(
			_translateSource(hit.source())
		).document(
			_translateDocument(alternateUidFieldName, hit)
		).explanation(
			_getExplanationString(hit)
		).id(
			hit.id()
		).matchedQueries(
			ArrayUtil.toStringArray(hit.matchedQueries())
		).score(
			ConversionUtil.toFloat(hit.score(), 0.0F)
		).sortValues(
			ArrayUtil.toStringArray(hit.sort())
		).version(
			GetterUtil.getLong(hit.version())
		).build();
	}

	private String _getExplanationString(Hit<JsonData> hit) {
		Explanation explanation = hit.explanation();

		if (explanation != null) {
			return JsonpUtil.toString(explanation);
		}

		return StringPool.BLANK;
	}

	private Document _translateDocument(
		String alternateUidFieldName, Hit<JsonData> hit) {

		DocumentBuilder documentBuilder = DocumentBuilderFactory.builder();

		FieldsTranslator fieldsTranslator = new FieldsTranslator();

		fieldsTranslator.translateSource(documentBuilder, hit.source());

		Map<String, JsonData> jsonDatas = hit.fields();

		fieldsTranslator.translateFields(documentBuilder, jsonDatas);

		fieldsTranslator.populateAlternateUID(
			alternateUidFieldName, documentBuilder, jsonDatas);

		return documentBuilder.build();
	}

	private List<HighlightField> _translateHighlightFields(
		Map<String, List<String>> highlight) {

		List<HighlightField> highlightFields = new ArrayList<>();

		for (Map.Entry<String, List<String>> entry : highlight.entrySet()) {
			highlightFields.add(
				new HighlightFieldBuilder(
				).fragments(
					entry.getValue()
				).name(
					entry.getKey()
				).build());
		}

		return highlightFields;
	}

	private Map<String, Object> _translateSource(JsonData jsonData) {
		if (jsonData == null) {
			return Collections.emptyMap();
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			TypeReference<HashMap<String, Object>> typeReference =
				new TypeReference<HashMap<String, Object>>() {
				};

			return objectMapper.readValue(jsonData.toString(), typeReference);
		}
		catch (JsonProcessingException jsonProcessingException) {
			throw new RuntimeException(jsonProcessingException);
		}
	}

	private final SearchHitBuilderFactory _searchHitBuilderFactory;
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory;

}