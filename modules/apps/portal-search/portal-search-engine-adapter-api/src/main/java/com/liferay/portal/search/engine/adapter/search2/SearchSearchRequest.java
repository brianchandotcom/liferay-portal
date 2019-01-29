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

package com.liferay.portal.search.engine.adapter.search2;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.sort.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
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

	public void addSorts(Sort... sorts) {
		Collections.addAll(_sorts, sorts);
	}

	public String getAlternateUidFieldName() {
		return _alternateUidFieldName;
	}

	public Map<String, Facet> getFacets() {
		return _facets;
	}

	public Boolean getFetchSource() {
		return _fetchSource;
	}

	public Highlight getHighlight() {
		return _highlight;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getPreference() {
		return _preference;
	}

	public String[] getSelectedFieldNames() {
		return _selectedFieldNames;
	}

	public int getSize() {
		return _size;
	}

	public List<Sort> getSorts() {
		return Collections.unmodifiableList(_sorts);
	}

	public int getStart() {
		return _start;
	}

	public Boolean getVersion() {
		return _version;
	}

	public boolean isScoreEnabled() {
		return _scoreEnabled;
	}

	public void setAlternateUidFieldName(String alternateUidFieldName) {
		_alternateUidFieldName = alternateUidFieldName;
	}

	public void setFetchSource(Boolean fetchSource) {
		_fetchSource = fetchSource;
	}

	public void setHighlight(Highlight highlight) {
		_highlight = highlight;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
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

	public void setStart(int start) {
		_start = start;
	}

	public void setVersion(Boolean version) {
		_version = version;
	}

	private String _alternateUidFieldName;
	private final Map<String, Facet> _facets = new LinkedHashMap<>();
	private Boolean _fetchSource;
	private Highlight _highlight;
	private Locale _locale;
	private String _preference;
	private boolean _scoreEnabled;
	private String[] _selectedFieldNames;
	private int _size;
	private final List<Sort> _sorts = new ArrayList<>();
	private int _start;
	private Boolean _version;

}