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