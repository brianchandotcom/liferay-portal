/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.hits;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;

import java.io.Serializable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class SearchHit implements Serializable {

	public void addHighlightFields(Collection<HighlightField> highlightFields) {
		for (HighlightField highlightField : highlightFields) {
			_highlightFieldsMap.put(highlightField.getName(), highlightField);
		}
	}

	public void addSources(Map<String, Object> sourcesMap) {
		if (MapUtil.isNotEmpty(sourcesMap)) {
			_sourcesMap.putAll(sourcesMap);
		}
	}

	public Document getDocument() {
		return _document;
	}

	public String getExplanation() {
		return _explanation;
	}

	public Map<String, HighlightField> getHighlightFieldsMap() {
		return _highlightFieldsMap;
	}

	public String getId() {
		return _id;
	}

	public String[] getMatchedQueries() {
		return _matchedQueries;
	}

	public float getScore() {
		return _score;
	}

	public Object[] getSortValues() {
		return _sortValues;
	}

	public Map<String, Object> getSourcesMap() {
		return _sourcesMap;
	}

	public long getVersion() {
		return _version;
	}

	public static class Builder implements SearchHitBuilder {

		@Override
		public SearchHitBuilder addHighlightField(
			HighlightField highlightField) {

			_highlightFieldsMap.put(highlightField.getName(), highlightField);

			return this;
		}

		@Override
		public SearchHitBuilder addHighlightFields(
			Collection<HighlightField> highlightFields) {

			for (HighlightField highlightField : highlightFields) {
				_highlightFieldsMap.put(
					highlightField.getName(), highlightField);
			}

			return this;
		}

		@Override
		public SearchHitBuilder addSource(String name, Object value) {
			_sourcesMap.put(name, value);

			return this;
		}

		@Override
		public SearchHitBuilder addSources(Map<String, Object> sourcesMap) {
			if (MapUtil.isNotEmpty(sourcesMap)) {
				_sourcesMap.putAll(sourcesMap);
			}

			return this;
		}

		@Override
		public SearchHit build() {
			return new SearchHit(
				_document, _explanation, _highlightFieldsMap, _id, _score,
				_sortValues, _sourcesMap, _version);
		}

		@Override
		public SearchHitBuilder document(Document document) {
			_document = document;

			return this;
		}

		@Override
		public SearchHitBuilder explanation(String explanation) {
			_explanation = explanation;

			return this;
		}

		@Override
		public SearchHitBuilder id(String id) {
			_id = id;

			return this;
		}

		@Override
		public SearchHitBuilder matchedQueries(String... matchedQueries) {
			if (matchedQueries != null) {
				_matchedQueries = matchedQueries;
			}
			else {
				_matchedQueries = new String[0];
			}

			return this;
		}

		@Override
		public SearchHitBuilder score(float score) {
			_score = score;

			return this;
		}

		@Override
		public SearchHitBuilder sortValues(Object[] sortValues) {
			_sortValues = sortValues;

			return this;
		}

		@Override
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

	protected SearchHit(
		Document document, String explanation,
		Map<String, HighlightField> highlightFieldsMap, String id, float score,
		Object[] sortValues, Map<String, Object> sourcesMap, long version) {

		_document = document;
		_explanation = explanation;
		_highlightFieldsMap = highlightFieldsMap;
		_id = id;
		_score = score;
		_sortValues = sortValues;
		_sourcesMap = sourcesMap;
		_version = version;
	}

	private final Document _document;
	private final String _explanation;
	private final Map<String, HighlightField> _highlightFieldsMap;
	private final String _id;
	private final String[] _matchedQueries = new String[0];
	private final float _score;
	private final Object[] _sortValues;
	private final Map<String, Object> _sourcesMap;
	private final long _version;

}