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

package com.liferay.portal.search.internal.highlight;

import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.highlight.HighlightBuilder;
import com.liferay.portal.search.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class HighlightImpl implements Highlight {

	public HighlightImpl() {
	}

	public HighlightImpl(HighlightImpl highlightImpl) {
		_fieldConfigs.addAll(highlightImpl._fieldConfigs);
		_forceSource = highlightImpl._forceSource;
		_fragmenter = highlightImpl._fragmenter;
		_fragmentSize = highlightImpl._fragmentSize;
		_highlighterType = highlightImpl._highlighterType;
		_highlightFilter = highlightImpl._highlightFilter;
		_highlightQuery = highlightImpl._highlightQuery;
		_numOfFragments = highlightImpl._numOfFragments;
		_postTags = highlightImpl._postTags;
		_preTags = highlightImpl._preTags;
		_requireFieldMatch = highlightImpl._requireFieldMatch;
	}

	@Override
	public List<FieldConfig> getFieldConfigs() {
		return Collections.unmodifiableList(_fieldConfigs);
	}

	@Override
	public Boolean getForceSource() {
		return _forceSource;
	}

	@Override
	public String getFragmenter() {
		return _fragmenter;
	}

	@Override
	public Integer getFragmentSize() {
		return _fragmentSize;
	}

	@Override
	public String getHighlighterType() {
		return _highlighterType;
	}

	@Override
	public Boolean getHighlightFilter() {
		return _highlightFilter;
	}

	@Override
	public Query getHighlightQuery() {
		return _highlightQuery;
	}

	@Override
	public Integer getNumOfFragments() {
		return _numOfFragments;
	}

	@Override
	public String[] getPostTags() {
		return _postTags;
	}

	@Override
	public String[] getPreTags() {
		return _preTags;
	}

	@Override
	public Boolean getRequireFieldMatch() {
		return _requireFieldMatch;
	}

	public static final class HighlightBuilderImpl implements HighlightBuilder {

		@Override
		public HighlightBuilder addFieldConfig(FieldConfig fieldConfig) {
			_highlightImpl._fieldConfigs.add(fieldConfig);

			return this;
		}

		@Override
		public Highlight build() {
			return new HighlightImpl(_highlightImpl);
		}

		@Override
		public HighlightBuilder fieldConfigs(
			Collection<FieldConfig> fieldConfigs) {

			_highlightImpl._fieldConfigs.clear();

			_highlightImpl._fieldConfigs.addAll(fieldConfigs);

			return this;
		}

		@Override
		public HighlightBuilder forceSource(Boolean forceSource) {
			_highlightImpl._forceSource = forceSource;

			return this;
		}

		@Override
		public HighlightBuilder fragmenter(String fragmenter) {
			_highlightImpl._fragmenter = fragmenter;

			return this;
		}

		@Override
		public HighlightBuilder fragmentSize(Integer fragmentSize) {
			_highlightImpl._fragmentSize = fragmentSize;

			return this;
		}

		@Override
		public HighlightBuilder highlighterType(String highlighterType) {
			_highlightImpl._highlighterType = highlighterType;

			return this;
		}

		@Override
		public HighlightBuilder highlightFilter(Boolean highlightFilter) {
			_highlightImpl._highlightFilter = highlightFilter;

			return this;
		}

		@Override
		public HighlightBuilder highlightQuery(Query highlightQuery) {
			_highlightImpl._highlightQuery = highlightQuery;

			return this;
		}

		@Override
		public HighlightBuilder numOfFragments(Integer numOfFragments) {
			_highlightImpl._numOfFragments = numOfFragments;

			return this;
		}

		@Override
		public HighlightBuilder postTags(String... postTags) {
			_highlightImpl._postTags = postTags;

			return this;
		}

		@Override
		public HighlightBuilder preTags(String... preTags) {
			_highlightImpl._preTags = preTags;

			return this;
		}

		@Override
		public HighlightBuilder requireFieldMatch(Boolean requireFieldMatch) {
			_highlightImpl._requireFieldMatch = requireFieldMatch;

			return this;
		}

		private final HighlightImpl _highlightImpl = new HighlightImpl();

	}

	private final List<FieldConfig> _fieldConfigs = new ArrayList<>();
	private Boolean _forceSource;
	private String _fragmenter;
	private Integer _fragmentSize;
	private String _highlighterType;
	private Boolean _highlightFilter;
	private Query _highlightQuery;
	private Integer _numOfFragments;
	private String[] _postTags = {};
	private String[] _preTags = {};
	private Boolean _requireFieldMatch;

}