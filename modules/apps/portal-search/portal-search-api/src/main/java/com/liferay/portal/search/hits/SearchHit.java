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
		highlightFields.forEach(this::addHighlightField);
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

			_searchHit.addHighlightField(highlightField);

			return this;
		}

		@Override
		public SearchHitBuilder addHighlightFields(
			Collection<HighlightField> highlightFields) {

			_searchHit.addHighlightFields(highlightFields);

			return this;
		}

		@Override
		public SearchHitBuilder addSource(String name, Object value) {
			_searchHit.addSource(name, value);

			return this;
		}

		@Override
		public SearchHitBuilder addSources(Map<String, Object> sourcesMap) {
			_searchHit.addSources(sourcesMap);

			return this;
		}

		@Override
		public SearchHit build() {
			return new SearchHit(_searchHit);
		}

		@Override
		public SearchHitBuilder document(Document document) {
			_searchHit._setDocument(document);

			return this;
		}

		@Override
		public SearchHitBuilder explanation(String explanation) {
			_searchHit._setExplanation(explanation);

			return this;
		}

		@Override
		public SearchHitBuilder id(String id) {
			_searchHit._setId(id);

			return this;
		}

		@Override
		public SearchHitBuilder matchedQueries(String... matchedQueries) {
			_searchHit._setMatchedQueries(matchedQueries);

			return this;
		}

		@Override
		public SearchHitBuilder score(float score) {
			_searchHit._setScore(score);

			return this;
		}

		@Override
		public SearchHitBuilder sortValues(Object[] sortValues) {
			_searchHit._setSortValues(sortValues);

			return this;
		}

		@Override
		public SearchHitBuilder version(long version) {
			_searchHit._setVersion(version);

			return this;
		}

		private final SearchHit _searchHit = new SearchHit();

	}

	protected SearchHit(SearchHit searchHit) {
		_document = searchHit._document;
		_explanation = searchHit._explanation;
		_id = searchHit._id;
		_matchedQueries = searchHit._matchedQueries;
		_score = searchHit._score;
		_sortValues = searchHit._sortValues;
		_version = searchHit._version;

		_highlightFieldsMap.putAll(searchHit._highlightFieldsMap);
		_sourcesMap.putAll(searchHit._sourcesMap);
	}

	protected void addHighlightField(HighlightField highlightField) {
		_highlightFieldsMap.put(highlightField.getName(), highlightField);
	}

	protected void addSource(String name, Object value) {
		_sourcesMap.put(name, value);
	}

	private SearchHit() {
	}

	private void _setDocument(Document document) {
		_document = document;
	}

	private void _setExplanation(String explanation) {
		_explanation = explanation;
	}

	private void _setId(String id) {
		_id = id;
	}

	private void _setMatchedQueries(String... matchedQueries) {
		if (matchedQueries != null) {
			_matchedQueries = matchedQueries;
		}
		else {
			_matchedQueries = new String[0];
		}
	}

	private void _setScore(float score) {
		_score = score;
	}

	private void _setSortValues(Object[] sortValues) {
		_sortValues = sortValues;
	}

	private void _setVersion(long version) {
		_version = version;
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