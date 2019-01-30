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

package com.liferay.portal.search.engine.adapter.search;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.Stats;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dylan Rebelak
 */
@ProviderType
public class SearchSearchRequest
	extends BaseSearchRequest implements SearchRequest<SearchSearchResponse> {

	@Override
	public SearchSearchResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

	public String getAlternateUidFieldName() {
		return _alternateUidFieldName;
	}

	public GroupBy getGroupBy() {
		return _groupBy;
	}

	public String[] getHighlightFieldNames() {
		return _highlightFieldNames;
	}

	public int getHighlightFragmentSize() {
		return _highlightFragmentSize;
	}

	public int getHighlightSnippetSize() {
		return _highlightSnippetSize;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getPreference() {
		return _preference;
	}

	/**
	 * @return
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public QueryConfig getQueryConfig() {
		throw new UnsupportedOperationException();
	}

	public String[] getSelectedFieldNames() {
		return _selectedFieldNames;
	}

	public int getSize() {
		return _size;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public int getStart() {
		return _start;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by
	 * 		com.liferay.portal.search.stats.StatsRequest
	 */
	@Deprecated
	public Map<String, Stats> getStats() {
		return _stats;
	}

	public boolean isHighlightEnabled() {
		return _highlightEnabled;
	}

	public boolean isHighlightRequireFieldMatch() {
		return _highlightRequireFieldMatch;
	}

	public boolean isLuceneSyntax() {
		return _luceneSyntax;
	}

	public boolean isScoreEnabled() {
		return _scoreEnabled;
	}

	public void putAllStats(Map<String, Stats> stats) {
		if (_stats == null) {
			_stats = new HashMap<>();
		}

		_stats.putAll(stats);
	}

	public void setAlternateUidFieldName(String alternateUidFieldName) {
		_alternateUidFieldName = alternateUidFieldName;
	}

	public void setGroupBy(GroupBy groupBy) {
		_groupBy = groupBy;
	}

	public void setHighlightEnabled(boolean highlightEnabled) {
		_highlightEnabled = highlightEnabled;
	}

	public void setHighlightFieldNames(String... highlightFieldNames) {
		_highlightFieldNames = highlightFieldNames;
	}

	public void setHighlightFragmentSize(int highlightFragmentSize) {
		_highlightFragmentSize = highlightFragmentSize;
	}

	public void setHighlightRequireFieldMatch(
		boolean highlightRequireFieldMatch) {

		_highlightRequireFieldMatch = highlightRequireFieldMatch;
	}

	public void setHighlightSnippetSize(int highlightSnippetSize) {
		_highlightSnippetSize = highlightSnippetSize;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setLuceneSyntax(boolean luceneSyntax) {
		_luceneSyntax = luceneSyntax;
	}

	public void setPreference(String preference) {
		_preference = preference;
	}

	public void setScoreEnabled(boolean scoreEnabled) {
		_scoreEnabled = scoreEnabled;
	}

	public void setSelectedFieldNames(String... selectedFieldNames) {
		_selectedFieldNames = selectedFieldNames;
	}

	public void setSize(int size) {
		_size = size;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setStart(int start) {
		_start = start;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by
	 * 		com.liferay.portal.search.stats.StatsRequest
	 */
	@Deprecated
	public void setStats(Map<String, Stats> stats) {
		_stats = stats;
	}

	private String _alternateUidFieldName;
	private GroupBy _groupBy;
	private boolean _highlightEnabled;
	private String[] _highlightFieldNames;
	private int _highlightFragmentSize = 80;
	private boolean _highlightRequireFieldMatch = true;
	private int _highlightSnippetSize = 3;
	private Locale _locale;
	private boolean _luceneSyntax;
	private String _preference;
	private boolean _scoreEnabled;
	private String[] _selectedFieldNames;
	private int _size;
	private Sort[] _sorts;
	private int _start;
	private Map<String, Stats> _stats;

}