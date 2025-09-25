/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.hits;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch7.internal.document.DocumentFieldsTranslator;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.highlight.HighlightFieldBuilderFactory;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.TotalHits;

import org.elasticsearch.common.document.DocumentField;

/**
 * @author Michael C. Han
 */
public class SearchHitsTranslator {

	public SearchHitsTranslator(
		SearchHitBuilderFactory searchHitBuilderFactory,
		SearchHitsBuilderFactory searchHitsBuilderFactory,
		DocumentBuilderFactory documentBuilderFactory,
		HighlightFieldBuilderFactory highlightFieldBuilderFactory,
		GeoBuilders geoBuilders) {

		_searchHitBuilderFactory = searchHitBuilderFactory;
		_searchHitsBuilderFactory = searchHitsBuilderFactory;
		_documentBuilderFactory = documentBuilderFactory;
		_highlightFieldBuilderFactory = highlightFieldBuilderFactory;
		_geoBuilders = geoBuilders;
	}

	public SearchHits translate(
		org.elasticsearch.search.SearchHits elasticsearchSearchHits) {

		return translate(elasticsearchSearchHits, null);
	}

	public SearchHits translate(
		org.elasticsearch.search.SearchHits elasticsearchSearchHits,
		String alternateUidFieldName) {

		SearchHitsBuilder searchHitsBuilder =
			_searchHitsBuilderFactory.getSearchHitsBuilder();

		TotalHits totalHits = elasticsearchSearchHits.getTotalHits();

		org.elasticsearch.search.SearchHit[] elasticsearchSearchHitArray =
			elasticsearchSearchHits.getHits();

		List<SearchHit> searchHits = new ArrayList<>(
			elasticsearchSearchHitArray.length);

		for (org.elasticsearch.search.SearchHit elasticsearchSearchHit :
				elasticsearchSearchHitArray) {

			searchHits.add(
				translate(elasticsearchSearchHit, alternateUidFieldName));
		}

		return searchHitsBuilder.addSearchHits(
			searchHits
		).maxScore(
			elasticsearchSearchHits.getMaxScore()
		).totalHits(
			totalHits.value
		).build();
	}

	protected SearchHit translate(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		SearchHitBuilder searchHitBuilder =
			_searchHitBuilderFactory.getSearchHitBuilder();

		return searchHitBuilder.addHighlightFields(
			_translateHighlightFields(elasticsearchSearchHit)
		).addSources(
			elasticsearchSearchHit.getSourceAsMap()
		).document(
			_translateDocument(elasticsearchSearchHit, alternateUidFieldName)
		).explanation(
			_getExplanationString(elasticsearchSearchHit)
		).id(
			elasticsearchSearchHit.getId()
		).matchedQueries(
			elasticsearchSearchHit.getMatchedQueries()
		).score(
			elasticsearchSearchHit.getScore()
		).sortValues(
			elasticsearchSearchHit.getSortValues()
		).version(
			elasticsearchSearchHit.getVersion()
		).build();
	}

	private String _getExplanationString(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit) {

		Explanation explanation = elasticsearchSearchHit.getExplanation();

		if (explanation != null) {
			return explanation.toString();
		}

		return StringPool.BLANK;
	}

	private Document _translateDocument(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		DocumentFieldsTranslator documentFieldsTranslator =
			new DocumentFieldsTranslator(_geoBuilders);

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		Map<String, Object> documentSourceMap =
			elasticsearchSearchHit.getSourceAsMap();

		documentFieldsTranslator.translate(documentBuilder, documentSourceMap);

		Map<String, DocumentField> documentFieldsMap =
			elasticsearchSearchHit.getFields();

		documentFieldsTranslator.translate(documentFieldsMap, documentBuilder);

		documentFieldsTranslator.populateAlternateUID(
			documentFieldsMap, documentBuilder, alternateUidFieldName);

		return documentBuilder.build();
	}

	private HighlightField _translateHighlightField(
		org.elasticsearch.search.fetch.subphase.highlight.HighlightField
			elasticsearchHighlightField) {

		return _highlightFieldBuilderFactory.builder(
		).fragments(
			TransformUtil.transformToList(
				elasticsearchHighlightField.getFragments(),
				text -> text.toString())
		).name(
			elasticsearchHighlightField.getName()
		).build();
	}

	private List<HighlightField> _translateHighlightFields(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit) {

		Map
			<String,
			 org.elasticsearch.search.fetch.subphase.highlight.HighlightField>
				map = elasticsearchSearchHit.getHighlightFields();

		List<HighlightField> highlightFields = new ArrayList<>();

		for (org.elasticsearch.search.fetch.subphase.highlight.HighlightField
				highlightField : map.values()) {

			highlightFields.add(_translateHighlightField(highlightField));
		}

		return highlightFields;
	}

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final GeoBuilders _geoBuilders;
	private final HighlightFieldBuilderFactory _highlightFieldBuilderFactory;
	private final SearchHitBuilderFactory _searchHitBuilderFactory;
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory;

}