/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.hits;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch7.internal.document.DocumentFieldsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
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
import java.util.stream.Stream;

import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.TotalHits;

import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.text.Text;

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

		return translate(null, elasticsearchSearchHits, null);
	}

	public SearchHits translate(
		SearchSearchRequest searchSearchRequest,
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
				translate(
					searchSearchRequest, elasticsearchSearchHit,
					alternateUidFieldName));
		}

		return searchHitsBuilder.addSearchHits(
			searchHits
		).maxScore(
			elasticsearchSearchHits.getMaxScore()
		).totalHits(
			totalHits.value
		).build();
	}

	protected String getExplanationString(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit) {

		Explanation explanation = elasticsearchSearchHit.getExplanation();

		if (explanation != null) {
			return explanation.toString();
		}

		return StringPool.BLANK;
	}

	protected SearchHit translate(
		SearchSearchRequest searchSearchRequest,
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		SearchHitBuilder searchHitBuilder =
			_searchHitBuilderFactory.getSearchHitBuilder();

		return searchHitBuilder.addHighlightFields(
			translateHighlightFields(elasticsearchSearchHit)
		).addSources(
			elasticsearchSearchHit.getSourceAsMap()
		).document(
			translateDocument(
				searchSearchRequest, elasticsearchSearchHit,
				alternateUidFieldName)
		).explanation(
			getExplanationString(elasticsearchSearchHit)
		).id(
			elasticsearchSearchHit.getId()
		).matchedQueries(
			elasticsearchSearchHit.getMatchedQueries()
		).score(
			elasticsearchSearchHit.getScore()
		).version(
			elasticsearchSearchHit.getVersion()
		).build();
	}

	protected Document translateDocument(
		SearchSearchRequest searchSearchRequest,
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		DocumentFieldsTranslator documentFieldsTranslator =
			new DocumentFieldsTranslator(_geoBuilders);

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		boolean useSource = false;

		if (searchSearchRequest != null) {
			Boolean fetchSource = searchSearchRequest.getFetchSource();

			if (fetchSource != null) {
				useSource = fetchSource.booleanValue();
			}
			else {
				String[] fetchSourceExcludes =
					searchSearchRequest.getFetchSourceExcludes();
				String[] fetchSourceIncludes =
					searchSearchRequest.getFetchSourceIncludes();

				if ((fetchSourceExcludes != null) ||
					(fetchSourceIncludes != null)) {

					useSource = true;
				}
			}
		}

		if (useSource) {
			Map<String, Object> documentSourceMap =
				elasticsearchSearchHit.getSourceAsMap();

			documentFieldsTranslator.translate(
				documentBuilder, documentSourceMap);
		}
		else {
			Map<String, DocumentField> documentFieldsMap =
				elasticsearchSearchHit.getFields();

			documentFieldsTranslator.translate(
				documentFieldsMap, documentBuilder);

			documentFieldsTranslator.populateAlternateUID(
				documentFieldsMap, documentBuilder, alternateUidFieldName);
		}

		return documentBuilder.build();
	}

	protected HighlightField translateHighlightField(
		org.elasticsearch.search.fetch.subphase.highlight.HighlightField
			elasticsearchHighlightField) {

		return _highlightFieldBuilderFactory.builder(
		).fragments(
			Stream.of(
				elasticsearchHighlightField.getFragments()
			).map(
				Text::string
			)
		).name(
			elasticsearchHighlightField.getName()
		).build();
	}

	protected List<HighlightField> translateHighlightFields(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit) {

		Map
			<String,
			 org.elasticsearch.search.fetch.subphase.highlight.HighlightField>
				map = elasticsearchSearchHit.getHighlightFields();

		List<HighlightField> highlightFields = new ArrayList<>();

		for (org.elasticsearch.search.fetch.subphase.highlight.HighlightField
				highlightField : map.values()) {

			highlightFields.add(translateHighlightField(highlightField));
		}

		return highlightFields;
	}

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final GeoBuilders _geoBuilders;
	private final HighlightFieldBuilderFactory _highlightFieldBuilderFactory;
	private final SearchHitBuilderFactory _searchHitBuilderFactory;
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory;

}