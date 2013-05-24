/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

	@Override
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

	@Override
	public Document doc(int n) {
		return _docs[n];
	}

	@JSON
	@Override
	public String getCollatedSpellCheckResult() {
		return _collatedSpellCheckResult;
	}

	@JSON
	@Override
	public Document[] getDocs() {
		return _docs;
	}

	@Override
	public int getLength() {
		return _length;
	}

	@JSON(include = false)
	@Override
	public Query getQuery() {
		return _query;
	}

	@JSON
	@Override
	public String[] getQuerySuggestions() {
		if ((_querySuggestions == null) || (_querySuggestions.length == 0)) {
			return StringPool.EMPTY_ARRAY;
		}

		return _querySuggestions;
	}

	@JSON
	@Override
	public String[] getQueryTerms() {
		return _queryTerms;
	}

	@JSON
	@Override
	public float[] getScores() {
		return _scores;
	}

	@Override
	public float getSearchTime() {
		return _searchTime;
	}

	@JSON
	@Override
	public String[] getSnippets() {
		return _snippets;
	}

	@Override
	public Map<String, List<String>> getSpellCheckResults() {
		return _spellCheckResults;
	}

	@Override
	public long getStart() {
		return _start;
	}

	@Override
	public float score(int n) {
		return _scores[n];
	}

	@Override
	public void setCollatedSpellCheckResult(String collatedSpellCheckResult) {
		_collatedSpellCheckResult = collatedSpellCheckResult;
	}

	@Override
	public void setDocs(Document[] docs) {
		_docs = docs;
	}

	@Override
	public void setLength(int length) {
		_length = length;
	}

	@Override
	public void setQuery(Query query) {
		_query = query;
	}

	@Override
	public void setQuerySuggestions(String[] querySuggestions) {
		_querySuggestions = querySuggestions;
	}

	@Override
	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	@Override
	public void setScores(float[] scores) {
		_scores = scores;
	}

	@Override
	public void setScores(Float[] scores) {
		float[] primScores = new float[scores.length];

		for (int i = 0; i < scores.length; i++) {
			primScores[i] = scores[i].floatValue();
		}

		setScores(primScores);
	}

	@Override
	public void setSearchTime(float time) {
		_searchTime = time;
	}

	@Override
	public void setSnippets(String[] snippets) {
		_snippets = snippets;
	}

	@Override
	public void setSpellCheckResults(
		Map<String, List<String>> spellCheckResults) {

		_spellCheckResults = spellCheckResults;
	}

	@Override
	public void setStart(long start) {
		_start = start;
	}

	@Override
	public String snippet(int n) {
		return _snippets[n];
	}

	@Override
	public List<Document> toList() {
		List<Document> subset = new ArrayList<Document>(_docs.length);

		for (Document _doc : _docs) {
			subset.add(_doc);
		}

		return subset;
	}

	private String _collatedSpellCheckResult;
	private Document[] _docs;
	private int _length;
	private Query _query;
	private String[] _querySuggestions;
	private String[] _queryTerms;
	private float[] _scores = new float[0];
	private float _searchTime;
	private String[] _snippets = {};
	private Map<String, List<String>> _spellCheckResults;
	private long _start;

}