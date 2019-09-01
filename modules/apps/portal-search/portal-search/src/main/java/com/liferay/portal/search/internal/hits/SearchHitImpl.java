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

package com.liferay.portal.search.internal.hits;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;

import java.io.Serializable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class SearchHitImpl implements SearchHit, Serializable {

	public void addHighlightFields(Collection<HighlightField> highlightFields) {
		highlightFields.forEach(this::addHighlightField);
	}

	public void addSources(Map<String, Object> sourcesMap) {
		if (!MapUtil.isEmpty(sourcesMap)) {
			_sourcesMap.putAll(sourcesMap);
		}
	}

	@Override
	public Document getDocument() {
		return _document;
	}

	@Override
	public String getExplanation() {
		return _explanation;
	}

	@Override
	public Map<String, HighlightField> getHighlightFieldsMap() {
		return _highlightFieldsMap;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String[] getMatchedQueries() {
		return _matchedQueries;
	}

	@Override
	public float getScore() {
		return _score;
	}

	@Override
	public Map<String, Object> getSourcesMap() {
		return _sourcesMap;
	}

	@Override
	public long getVersion() {
		return _version;
	}

	protected SearchHitImpl(SearchHitImpl searchHitImpl) {
		_document = searchHitImpl._document;
		_explanation = searchHitImpl._explanation;
		_id = searchHitImpl._id;
		_matchedQueries = searchHitImpl._matchedQueries;
		_score = searchHitImpl._score;
		_version = searchHitImpl._version;

		_highlightFieldsMap.putAll(searchHitImpl._highlightFieldsMap);
		_sourcesMap.putAll(searchHitImpl._sourcesMap);
	}

	protected void addHighlightField(HighlightField highlightField) {
		_highlightFieldsMap.put(highlightField.getName(), highlightField);
	}

	protected void addSource(String name, Object value) {
		_sourcesMap.put(name, value);
	}

	protected void setDocument(Document document) {
		_document = document;
	}

	protected void setExplanation(String explanation) {
		_explanation = explanation;
	}

	protected void setId(String id) {
		_id = id;
	}

	protected void setMatchedQueries(String... matchedQueries) {
		if (matchedQueries != null) {
			_matchedQueries = matchedQueries;
		}
		else {
			_matchedQueries = new String[0];
		}
	}

	protected void setScore(float score) {
		_score = score;
	}

	protected void setVersion(long version) {
		_version = version;
	}

	protected static class Builder implements SearchHitBuilder {

		@Override
		public SearchHitBuilder addHighlightField(
			HighlightField highlightField) {

			_searchHitImpl.addHighlightField(highlightField);

			return this;
		}

		@Override
		public SearchHitBuilder addHighlightFields(
			Collection<HighlightField> highlightFields) {

			_searchHitImpl.addHighlightFields(highlightFields);

			return this;
		}

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by {@link
		 *             #addHighlightFields(Collection))}
		 */
		@Deprecated
		@Override
		public SearchHitBuilder addHighlightFields(
			Stream<HighlightField> highlightFieldStream) {

			_searchHitImpl.addHighlightFields(
				highlightFieldStream.collect(Collectors.toList()));

			return this;
		}

		@Override
		public SearchHitBuilder addSource(String name, Object value) {
			_searchHitImpl.addSource(name, value);

			return this;
		}

		@Override
		public SearchHitBuilder addSources(Map<String, Object> sourcesMap) {
			_searchHitImpl.addSources(sourcesMap);

			return this;
		}

		@Override
		public SearchHit build() {
			return new SearchHitImpl(_searchHitImpl);
		}

		@Override
		public SearchHitBuilder document(Document document) {
			_searchHitImpl.setDocument(document);

			return this;
		}

		@Override
		public SearchHitBuilder explanation(String explanation) {
			_searchHitImpl.setExplanation(explanation);

			return this;
		}

		@Override
		public SearchHitBuilder id(String id) {
			_searchHitImpl.setId(id);

			return this;
		}

		@Override
		public SearchHitBuilder matchedQueries(String... matchedQueries) {
			_searchHitImpl.setMatchedQueries(matchedQueries);

			return this;
		}

		@Override
		public SearchHitBuilder score(float score) {
			_searchHitImpl.setScore(score);

			return this;
		}

		@Override
		public SearchHitBuilder version(long version) {
			_searchHitImpl.setVersion(version);

			return this;
		}

		private final SearchHitImpl _searchHitImpl = new SearchHitImpl();

	}

	private SearchHitImpl() {
	}

	private Document _document;
	private String _explanation;
	private final Map<String, HighlightField> _highlightFieldsMap =
		new LinkedHashMap<>();
	private String _id;
	private String[] _matchedQueries = new String[0];
	private float _score;
	private final Map<String, Object> _sourcesMap = new LinkedHashMap<>();
	private long _version;

}