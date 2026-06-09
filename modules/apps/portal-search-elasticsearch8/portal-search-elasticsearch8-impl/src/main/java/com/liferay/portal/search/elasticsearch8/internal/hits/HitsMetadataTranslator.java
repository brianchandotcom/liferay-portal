/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.hits;

import co.elastic.clients.elasticsearch.core.explain.Explanation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.InnerHitsResult;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;

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

	public SearchHits translate(HitsMetadata<JsonData> hitsMetadata) {
		return translate(null, hitsMetadata);
	}

	public SearchHits translate(
		String alternateUidFieldName, HitsMetadata<JsonData> hitsMetadata) {

		SearchHitsBuilder searchHitsBuilder = new SearchHitsBuilder();

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

		SearchHitBuilder searchHitBuilder = new SearchHitBuilder();

		return searchHitBuilder.addHighlightFields(
			_translateHighlightFields(hit)
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
			TransformUtil.transformToArray(
				hit.sort(), fieldValue -> fieldValue._get(), Object.class)
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

	private void _populateHighlightFields(
		Hit<JsonData> hit, List<HighlightField> highlightFields) {

		Map<String, List<String>> highlight = hit.highlight();

		if (highlight != null) {
			for (Map.Entry<String, List<String>> entry : highlight.entrySet()) {
				highlightFields.add(
					new HighlightFieldBuilder(
					).fragments(
						entry.getValue()
					).name(
						entry.getKey()
					).build());
			}
		}

		Map<String, InnerHitsResult> innerHits = hit.innerHits();

		if (MapUtil.isEmpty(innerHits)) {
			return;
		}

		for (InnerHitsResult innerHitsResult : innerHits.values()) {
			HitsMetadata<JsonData> hitsMetadata = innerHitsResult.hits();

			if (hitsMetadata == null) {
				continue;
			}

			List<Hit<JsonData>> hits = hitsMetadata.hits();

			if (hits == null) {
				continue;
			}

			for (Hit<JsonData> innerHit : hits) {
				_populateHighlightFields(innerHit, highlightFields);
			}
		}
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

	private List<HighlightField> _translateHighlightFields(Hit<JsonData> hit) {
		List<HighlightField> highlightFields = new ArrayList<>();

		_populateHighlightFields(hit, highlightFields);

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

}