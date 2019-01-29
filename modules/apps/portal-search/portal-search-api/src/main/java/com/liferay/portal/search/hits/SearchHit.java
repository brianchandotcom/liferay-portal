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

package com.liferay.portal.search.hits;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public class SearchHit {

	public void addHighlightField(HighlightField highlightField) {
		_highlightFields.put(highlightField.getName(), highlightField);
	}

	public void addSource(String name, Object value) {
		_sourceMap.put(name, value);
	}

	public Document getDocument() {
		return _document;
	}

	public String getExplanation() {
		return _explanation;
	}

	public Map<String, HighlightField> getHighlightFields() {
		return Collections.unmodifiableMap(_highlightFields);
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

	public Map<String, Object> getSourceMap() {
		return Collections.unmodifiableMap(_sourceMap);
	}

	public long getVersion() {
		return _version;
	}

	public void setDocument(Document document) {
		_document = document;
	}

	public void setExplanation(String explanation) {
		_explanation = explanation;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setMatchedQueries(String[] matchedQueries) {
		_matchedQueries = matchedQueries;
	}

	public void setScore(float score) {
		_score = score;
	}

	public void setVersion(long version) {
		_version = version;
	}

	private Document _document;
	private String _explanation;
	private final Map<String, HighlightField> _highlightFields =
		new HashMap<>();
	private String _id;
	private String[] _matchedQueries;
	private float _score;
	private final Map<String, Object> _sourceMap = new HashMap<>();
	private long _version;

}