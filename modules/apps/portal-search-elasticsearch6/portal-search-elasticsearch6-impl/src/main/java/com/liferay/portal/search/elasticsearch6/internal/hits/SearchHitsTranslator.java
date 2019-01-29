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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;

import java.util.Map;

import org.apache.lucene.search.Explanation;

import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.text.Text;

/**
 * @author Michael C. Han
 */
public class SearchHitsTranslator {

	public SearchHit translate(
		org.elasticsearch.search.SearchHit elasticsearchSearchHit,
		String alternateUidFieldName) {

		SearchHit searchHit = new SearchHit();

		searchHit.setId(elasticsearchSearchHit.getId());

		if (elasticsearchSearchHit.getExplanation() != null) {
			Explanation explanation = elasticsearchSearchHit.getExplanation();

			searchHit.setExplanation(explanation.toString());
		}

		if (elasticsearchSearchHit.getMatchedQueries() != null) {
			searchHit.setMatchedQueries(
				elasticsearchSearchHit.getMatchedQueries());
		}

		Map<String, DocumentField> documentFieldsMap =
			elasticsearchSearchHit.getFields();

		Document document = new Document();

		searchHit.setDocument(document);

		if (MapUtil.isNotEmpty(documentFieldsMap)) {
			documentFieldsMap.forEach(
				(fieldName, documentField) -> {
					Field field = new Field(documentField.getName());

					String documentFieldName = documentField.getName();

					if (documentFieldName.endsWith(_GEOPOINT_SUFFIX)) {
						String[] values = StringUtil.split(
							documentField.getValue());

						GeoLocationPoint geoLocationPoint = null;

						if (values.length == 2) {
							geoLocationPoint = new GeoLocationPoint(
								Double.valueOf(values[0]),
								Double.valueOf(values[1]));
						}
						else {
							geoLocationPoint = new GeoLocationPoint(values[0]);
						}

						field.addValue(geoLocationPoint);
					}
					else {
						field.addValues(documentField.getValues());
					}

					document.addField(field);
				});
		}

		Map<String, Object> sourceMap = elasticsearchSearchHit.getSourceAsMap();

		if (MapUtil.isNotEmpty(sourceMap)) {
			sourceMap.forEach(searchHit::addSource);
		}

		populateUID(document, alternateUidFieldName);

		Map
			<String,
			 org.elasticsearch.search.fetch.subphase.highlight.HighlightField>
				highlightFieldsMap =
					elasticsearchSearchHit.getHighlightFields();

		if (MapUtil.isNotEmpty(highlightFieldsMap)) {
			highlightFieldsMap.forEach(
				(fieldName, elasticsearchHighlightField) -> {
					HighlightField highlightField = new HighlightField(
						elasticsearchHighlightField.getName());

					Text[] fragments =
						elasticsearchHighlightField.getFragments();

					for (final Text fragment : fragments) {
						highlightField.addFragment(fragment.string());
					}

					searchHit.addHighlightField(highlightField);
				});
		}

		searchHit.setScore(elasticsearchSearchHit.getScore());
		searchHit.setVersion(elasticsearchSearchHit.getVersion());

		return searchHit;
	}

	public SearchHits translate(
		org.elasticsearch.search.SearchHits elasticsearchSearchHits) {

		return translate(elasticsearchSearchHits, null);
	}

	public SearchHits translate(
		org.elasticsearch.search.SearchHits elasticsearchSearchHits,
		String alternateUidFieldName) {

		SearchHits searchHits = new SearchHits();

		searchHits.setTotalHits(elasticsearchSearchHits.totalHits);

		searchHits.setMaxScore(elasticsearchSearchHits.getMaxScore());

		org.elasticsearch.search.SearchHit[] elasticsearchSearchHitArray =
			elasticsearchSearchHits.getHits();

		for (org.elasticsearch.search.SearchHit elasticsearchSearchHit :
				elasticsearchSearchHitArray) {

			SearchHit searchHit = translate(
				elasticsearchSearchHit, alternateUidFieldName);

			searchHits.addSearchHit(searchHit);
		}

		return searchHits;
	}

	protected void populateUID(
		Document document, String alternateUidFieldName) {

		Field uidField = document.getField(_UID_FIELD_NAME);

		if (uidField != null) {
			return;
		}

		if (Validator.isNull(alternateUidFieldName)) {
			return;
		}

		Field field = document.getField(alternateUidFieldName);

		if (field != null) {
			Field newUidField = new Field(_UID_FIELD_NAME);

			newUidField.addValues(field.getValues());

			document.addField(newUidField);
		}
	}

	private static final String _GEOPOINT_SUFFIX = ".geopoint";

	private static final String _UID_FIELD_NAME = "uid";

}