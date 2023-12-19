/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.hits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.highlight.HighlightFieldBuilderFactory;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.core.explain.Explanation;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch.core.search.HitsMetadata;
import org.opensearch.client.opensearch.core.search.TotalHits;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class HitsMetadataTranslator {

	public HitsMetadataTranslator(
		DocumentBuilderFactory documentBuilderFactory, GeoBuilders geoBuilders,
		HighlightFieldBuilderFactory highlightFieldBuilderFactory,
		SearchHitBuilderFactory searchHitBuilderFactory,
		SearchHitsBuilderFactory searchHitsBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;
		_geoBuilders = geoBuilders;
		_highlightFieldBuilderFactory = highlightFieldBuilderFactory;
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

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		FieldsTranslator fieldsTranslator = new FieldsTranslator(_geoBuilders);

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
				_highlightFieldBuilderFactory.builder(
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

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final GeoBuilders _geoBuilders;
	private final HighlightFieldBuilderFactory _highlightFieldBuilderFactory;
	private final SearchHitBuilderFactory _searchHitBuilderFactory;
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory;

}