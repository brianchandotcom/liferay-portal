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

package com.liferay.portal.search.highlight;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
@ProviderType
public class Highlight {

	public void addFieldConfig(
		String field, Integer fragmentSize, Integer numFragments,
		Integer fragmentOffset) {

		FieldConfig fieldConfig = new FieldConfig(field);

		fieldConfig.setFragmentOffset(fragmentOffset);
		fieldConfig.setFragmentSize(fragmentSize);
		fieldConfig.setNumFragments(numFragments);

		_fieldConfigs.add(fieldConfig);
	}

	public void addFieldConfigs(String... fields) {
		for (final String field : fields) {
			_fieldConfigs.add(new FieldConfig(field));
		}
	}

	public void addPostTags(String... postTags) {
		Collections.addAll(_postTags, postTags);
	}

	public void addPreTags(String... preTags) {
		Collections.addAll(_preTags, preTags);
	}

	public List<FieldConfig> getFieldConfigs() {
		return Collections.unmodifiableList(_fieldConfigs);
	}

	public Boolean getForceSource() {
		return _forceSource;
	}

	public String getFragmenter() {
		return _fragmenter;
	}

	public Integer getFragmentSize() {
		return _fragmentSize;
	}

	public String getHighlighterType() {
		return _highlighterType;
	}

	public Boolean getHighlightFilter() {
		return _highlightFilter;
	}

	public Query getHighlightQuery() {
		return _highlightQuery;
	}

	public Integer getNumOfFragments() {
		return _numOfFragments;
	}

	public List<String> getPostTags() {
		return Collections.unmodifiableList(_postTags);
	}

	public List<String> getPreTags() {
		return Collections.unmodifiableList(_preTags);
	}

	public Boolean getRequireFieldMatch() {
		return _requireFieldMatch;
	}

	public void setForceSource(Boolean forceSource) {
		_forceSource = forceSource;
	}

	public void setFragmenter(String fragmenter) {
		_fragmenter = fragmenter;
	}

	public void setFragmentSize(Integer fragmentSize) {
		_fragmentSize = fragmentSize;
	}

	public void setHighlighterType(String highlighterType) {
		_highlighterType = highlighterType;
	}

	public void setHighlightFilter(Boolean highlightFilter) {
		_highlightFilter = highlightFilter;
	}

	public void setHighlightQuery(Query highlightQuery) {
		_highlightQuery = highlightQuery;
	}

	public void setNumOfFragments(Integer numOfFragments) {
		_numOfFragments = numOfFragments;
	}

	public void setRequireFieldMatch(Boolean requireFieldMatch) {
		_requireFieldMatch = requireFieldMatch;
	}

	public static class FieldConfig {

		public FieldConfig(String field) {
			_field = field;
		}

		public String getField() {
			return _field;
		}

		public Integer getFragmentOffset() {
			return _fragmentOffset;
		}

		public Integer getFragmentSize() {
			return _fragmentSize;
		}

		public Integer getNumFragments() {
			return _numFragments;
		}

		public void setFragmentOffset(Integer fragmentOffset) {
			_fragmentOffset = fragmentOffset;
		}

		public void setFragmentSize(Integer fragmentSize) {
			_fragmentSize = fragmentSize;
		}

		public void setNumFragments(Integer numFragments) {
			_numFragments = numFragments;
		}

		private final String _field;
		private Integer _fragmentOffset;
		private Integer _fragmentSize;
		private Integer _numFragments;

	}

	private final List<FieldConfig> _fieldConfigs = new ArrayList<>();
	private Boolean _forceSource;
	private String _fragmenter;
	private Integer _fragmentSize;
	private String _highlighterType;
	private Boolean _highlightFilter;
	private Query _highlightQuery;
	private Integer _numOfFragments;
	private final List<String> _postTags = new ArrayList<>();
	private final List<String> _preTags = new ArrayList<>();
	private Boolean _requireFieldMatch;

}