/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class HitsImpl implements Hits {

	public HitsImpl() {
	}

	public void copy(Hits hits) {
		setDocs(hits.getDocs());

		setLength(hits.getLength());

		setQuery(hits.getQuery());

		setQuerySuggestions(hits.getQuerySuggestions());

		setQueryTerms(hits.getQueryTerms());

		setScores(hits.getScores());

		setSearchTime(hits.getSearchTime());

		setSnippets(hits.getSnippets());

		setSpellCheckResults(hits.getSpellCheckResults());

		setStart(hits.getStart());
	}

	public Document doc(int n) {
		return _docs[n];
	}

	@JSON
	public String getCollatedSpellCheckResult() {
		return _collatedSpellCheckResult;
	}

	@JSON
	public Document[] getDocs() {
		return _docs;
	}

	public int getLength() {
		return _length;
	}

	@JSON(include = false)
	public Query getQuery() {
		return _query;
	}

	@JSON
	public String[] getQuerySuggestions() {
		if (Validator.isNull(_querySuggestions)) {
			return StringPool.BLANK_ARRAY;
		}

		return _querySuggestions;
	}

	@JSON
	public String[] getQueryTerms() {
		return _queryTerms;
	}

	@JSON
	public float[] getScores() {
		return _scores;
	}

	public float getSearchTime() {
		return _searchTime;
	}

	@JSON
	public String[] getSnippets() {
		return _snippets;
	}

	public Map<String, List<String>> getSpellCheckResults() {
		return _spellCheckResults;
	}

	public long getStart() {
		return _start;
	}

	public float score(int n) {
		return _scores[n];
	}

	public void setCollatedSpellCheckResult(String collatedSpellCheckResult) {
		_collatedSpellCheckResult = collatedSpellCheckResult;
	}

	public void setDocs(Document[] docs) {
		_docs = docs;
	}

	public void setLength(int length) {
		_length = length;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setQuerySuggestions(String[] querySuggestions) {
		_querySuggestions = querySuggestions;
	}

	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	public void setScores(float[] scores) {
		_scores = scores;
	}

	public void setScores(Float[] scores) {
		float[] primScores = new float[scores.length];

		for (int i = 0; i < scores.length; i++) {
			primScores[i] = scores[i].floatValue();
		}

		setScores(primScores);
	}

	public void setSearchTime(float time) {
		_searchTime = time;
	}

	public void setSnippets(String[] snippets) {
		_snippets = snippets;
	}

	public void setSpellCheckResults(
		Map<String, List<String>> spellCheckResults) {

		_spellCheckResults = spellCheckResults;
	}

	public void setStart(long start) {
		_start = start;
	}

	public String snippet(int n) {
		return _snippets[n];
	}

	public List<Document> toList() {
		List<Document> subset = new ArrayList<Document>(_docs.length);

		for (Document _doc : _docs) {
			subset.add(_doc);
		}

		return subset;
	}

	private String _collatedSpellCheckResult = null;
	private Document[] _docs;
	private int _length;
	private Query _query;
	private String[] _querySuggestions = null;
	private String[] _queryTerms;
	private float[] _scores = new float[0];
	private float _searchTime;
	private String[] _snippets = {};
	private Map<String, List<String>> _spellCheckResults = null;
	private long _start;

}