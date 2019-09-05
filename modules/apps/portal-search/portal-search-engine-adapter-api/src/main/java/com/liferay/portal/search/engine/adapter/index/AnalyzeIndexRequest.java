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

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class AnalyzeIndexRequest implements IndexRequest<AnalyzeIndexResponse> {

	@Override
	public AnalyzeIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	public void addAttributes(String... attributes) {
		if (_attributes == null) {
			_attributes = new HashSet<>();
		}

		Collections.addAll(_attributes, attributes);
	}

	public void addCharFilters(String... charFilters) {
		if (ArrayUtil.isEmpty(charFilters)) {
			return;
		}

		if (_charFilters == null) {
			_charFilters = new HashSet<>();
		}

		Collections.addAll(_charFilters, charFilters);
	}

	public void addTokenFilter(String... tokenFilters) {
		if (_tokenFilters == null) {
			_tokenFilters = new HashSet<>();
		}

		Collections.addAll(_tokenFilters, tokenFilters);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Set<String> getAttributes() {
		if (_attributes == null) {
			return Collections.emptySet();
		}

		return _attributes;
	}

	public String[] getAttributesArray() {
		if (SetUtil.isEmpty(_attributes)) {
			return StringPool.EMPTY_ARRAY;
		}

		return _attributes.toArray(new String[0]);
	}

	public Set<String> getCharFilters() {
		if (_charFilters == null) {
			return Collections.emptySet();
		}

		return _charFilters;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getIndexName() {
		return _indexName;
	}

	@Override
	public String[] getIndexNames() {
		return new String[] {_indexName};
	}

	/**
	 * @return
	 * @deprecated As of Judson (7.1.x), with no direct replacement. This method
	 *             should not be in the parent interface.  Only certain
	 *             IndexRequests work with mappings.
	 */
	@Deprecated
	@Override
	public String getMappingName() {
		throw new UnsupportedOperationException();
	}

	public String getNormalizer() {
		return _normalizer;
	}

	public String[] getTexts() {
		return _texts;
	}

	public Set<String> getTokenFilters() {
		if (_tokenFilters == null) {
			return Collections.emptySet();
		}

		return _tokenFilters;
	}

	public String getTokenizer() {
		return _tokenizer;
	}

	public boolean isExplain() {
		return _explain;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setAttributes(Set<String> attributes) {
		_attributes = attributes;
	}

	public void setCharFilters(Set<String> charFilters) {
		_charFilters = charFilters;
	}

	public void setExplain(boolean explain) {
		_explain = explain;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setIndexName(String indexName) {
		_indexName = indexName;
	}

	public void setNormalizer(String normalizer) {
		_normalizer = normalizer;
	}

	public void setTexts(String... texts) {
		_texts = texts;
	}

	public void setTokenFilters(Set<String> tokenFilters) {
		_tokenFilters = tokenFilters;
	}

	public void setTokenizer(String tokenizer) {
		_tokenizer = tokenizer;
	}

	private String _analyzer;
	private Set<String> _attributes;
	private Set<String> _charFilters;
	private boolean _explain;
	private String _fieldName;
	private String _indexName;
	private String _normalizer;
	private String[] _texts;
	private Set<String> _tokenFilters;
	private String _tokenizer;

}