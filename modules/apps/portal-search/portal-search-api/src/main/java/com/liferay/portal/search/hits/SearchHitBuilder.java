/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.hits;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Wade Cao
 */
public class SearchHitBuilder {

	public SearchHitBuilder addHighlightField(HighlightField highlightField) {
		_highlightFieldsMap.put(highlightField.getName(), highlightField);

		return this;
	}

	public SearchHitBuilder addHighlightFields(
		Collection<HighlightField> highlightFields) {

		for (HighlightField highlightField : highlightFields) {
			_highlightFieldsMap.put(highlightField.getName(), highlightField);
		}

		return this;
	}

	public SearchHitBuilder addSource(String name, Object value) {
		_sourcesMap.put(name, value);

		return this;
	}

	public SearchHitBuilder addSources(Map<String, Object> sourcesMap) {
		if (MapUtil.isNotEmpty(sourcesMap)) {
			_sourcesMap.putAll(sourcesMap);
		}

		return this;
	}

	public SearchHit build() {
		return new SearchHit(
			_document, _explanation, _highlightFieldsMap, _id, _score,
			_sortValues, _sourcesMap, _version);
	}

	public SearchHitBuilder document(Document document) {
		_document = document;

		return this;
	}

	public SearchHitBuilder explanation(String explanation) {
		_explanation = explanation;

		return this;
	}

	public SearchHitBuilder id(String id) {
		_id = id;

		return this;
	}

	public SearchHitBuilder matchedQueries(String... matchedQueries) {
		if (matchedQueries != null) {
			_matchedQueries = matchedQueries;
		}
		else {
			_matchedQueries = new String[0];
		}

		return this;
	}

	public SearchHitBuilder score(float score) {
		_score = score;

		return this;
	}

	public SearchHitBuilder sortValues(Object[] sortValues) {
		_sortValues = sortValues;

		return this;
	}

	public SearchHitBuilder version(long version) {
		_version = version;

		return this;
	}

	private Document _document;
	private String _explanation;
	private final Map<String, HighlightField> _highlightFieldsMap =
		new LinkedHashMap<>();
	private String _id;
	private String[] _matchedQueries = new String[0];
	private float _score;
	private Object[] _sortValues;
	private final Map<String, Object> _sourcesMap = new LinkedHashMap<>();
	private long _version;

}