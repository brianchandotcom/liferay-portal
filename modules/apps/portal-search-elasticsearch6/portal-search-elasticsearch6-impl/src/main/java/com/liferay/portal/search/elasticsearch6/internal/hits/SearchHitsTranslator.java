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

package com.liferay.portal.search.elasticsearch6.internal.hits;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch6.internal.document.DocumentFieldsTranslator;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.highlight.HighlightFieldBuilderFactory;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.lucene.search.Explanation;

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

		return translate(elasticsearchSearchHits, null);
	}

	public SearchHits translate(
		org.elasticsearch.search.SearchHits elasticsearchSearchHits,
		String alternateUidFieldName) {

		SearchHitsBuilder searchHitsBuilder =
			_searchHitsBuilderFactory.getSearchHitsBuilder();

		return searchHitsBuilder.addSearchHits(
			Stream.of(
				elasticsearchSearchHits.getHits()
			).map(
				elasticsearchSearchHit -> translate(
					elasticsearchSearchHit, alternateUidFieldName)
			)
		).maxScore(
			elasticsearchSearchHits.getMaxScore()
		).totalHits(
			elasticsearchSearchHits.totalHits
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
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		SearchHitBuilder searchHitBuilder =
			_searchHitBuilderFactory.getSearchHitBuilder();

		return searchHitBuilder.addHighlightFields(
			translateHighlightFields(elasticsearchSearchHit)
		).addSources(
			elasticsearchSearchHit.getSourceAsMap()
		).document(
			translateDocument(elasticsearchSearchHit, alternateUidFieldName)
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
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		DocumentFieldsTranslator documentFieldsTranslator =
			new DocumentFieldsTranslator(_geoBuilders);

		Map<String, DocumentField> documentFieldsMap =
			elasticsearchSearchHit.getFields();

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentFieldsTranslator.translate(documentFieldsMap, documentBuilder);

		documentFieldsTranslator.populateAlternateUID(
			documentFieldsMap, documentBuilder, alternateUidFieldName);

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

	protected Stream<HighlightField> translateHighlightFields(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit) {

		Map
			<String,
			 org.elasticsearch.search.fetch.subphase.highlight.HighlightField>
				map = elasticsearchSearchHit.getHighlightFields();

		Collection
			<org.elasticsearch.search.fetch.subphase.highlight.HighlightField>
				values = map.values();

		Stream<org.elasticsearch.search.fetch.subphase.highlight.HighlightField>
			stream = values.stream();

		return stream.map(this::translateHighlightField);
	}

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final GeoBuilders _geoBuilders;
	private final HighlightFieldBuilderFactory _highlightFieldBuilderFactory;
	private final SearchHitBuilderFactory _searchHitBuilderFactory;
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory;

}