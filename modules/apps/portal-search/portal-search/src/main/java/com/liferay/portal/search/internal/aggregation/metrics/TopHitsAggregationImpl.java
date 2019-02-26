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

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.internal.aggregation.BaseAggregation;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.sort.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class TopHitsAggregationImpl
	extends BaseAggregation implements TopHitsAggregation {

	public TopHitsAggregationImpl(String name) {
		super(name);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public void addScriptFields(ScriptField... scriptFields) {
		Collections.addAll(_scriptFields, scriptFields);
	}

	@Override
	public void addSelectedFields(String... fields) {
		Collections.addAll(_selectedFields, fields);
	}

	@Override
	public void addSortFields(Sort... sortFields) {
		Collections.addAll(_sortFields, sortFields);
	}

	@Override
	public Boolean getExplain() {
		return _explain;
	}

	@Override
	public Boolean getFetchSource() {
		return _fetchSource;
	}

	@Override
	public String[] getFetchSourceExclude() {
		return _fetchSourceExclude;
	}

	@Override
	public String[] getFetchSourceInclude() {
		return _fetchSourceInclude;
	}

	@Override
	public Integer getFrom() {
		return _from;
	}

	@Override
	public Highlight getHighlight() {
		return _highlight;
	}

	@Override
	public List<ScriptField> getScriptFields() {
		return _scriptFields;
	}

	@Override
	public List<String> getSelectedFields() {
		return Collections.unmodifiableList(_selectedFields);
	}

	@Override
	public Integer getSize() {
		return _size;
	}

	@Override
	public List<Sort> getSortFields() {
		return Collections.unmodifiableList(_sortFields);
	}

	@Override
	public Boolean getTrackScores() {
		return _trackScores;
	}

	@Override
	public Boolean getVersion() {
		return _version;
	}

	@Override
	public void setExplain(Boolean explain) {
		_explain = explain;
	}

	@Override
	public void setFetchSource(Boolean fetchSource) {
		_fetchSource = fetchSource;
	}

	@Override
	public void setFetchSourceExclude(String[] fetchSourceExclude) {
		_fetchSourceExclude = fetchSourceExclude;
	}

	@Override
	public void setFetchSourceInclude(String[] fetchSourceInclude) {
		_fetchSourceInclude = fetchSourceInclude;
	}

	@Override
	public void setFetchSourceIncludeExclude(
		String[] fetchSourceInclude, String[] fetchSourceExclude) {

		_fetchSourceInclude = fetchSourceInclude;
		_fetchSourceExclude = fetchSourceExclude;

		if ((_fetchSourceExclude != null) || (_fetchSourceInclude != null)) {
			_fetchSource = Boolean.TRUE;
		}
	}

	@Override
	public void setFrom(Integer from) {
		_from = from;
	}

	@Override
	public void setHighlight(Highlight highlight) {
		_highlight = highlight;
	}

	@Override
	public void setScriptFields(List<ScriptField> scriptFields) {
		_scriptFields = scriptFields;
	}

	@Override
	public void setSelectedFields(List<String> selectedFields) {
		_selectedFields = selectedFields;
	}

	@Override
	public void setSize(Integer size) {
		_size = size;
	}

	@Override
	public void setTrackScores(Boolean trackScores) {
		_trackScores = trackScores;
	}

	@Override
	public void setVersion(Boolean version) {
		_version = version;
	}

	private Boolean _explain;
	private Boolean _fetchSource;
	private String[] _fetchSourceExclude;
	private String[] _fetchSourceInclude;
	private Integer _from;
	private Highlight _highlight;
	private List<ScriptField> _scriptFields = new ArrayList<>();
	private List<String> _selectedFields = new ArrayList<>();
	private Integer _size;
	private final List<Sort> _sortFields = new ArrayList<>();
	private Boolean _trackScores;
	private Boolean _version;

}