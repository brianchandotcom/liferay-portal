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

package com.liferay.portal.kernel.search.generic;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class FuzzyLikeThisQuery extends BaseQueryImpl {

	public FuzzyLikeThisQuery(String likeText) {
		_likeText = likeText;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public void addField(String field) {
		_fields.add(field);
	}

	public void addFields(Collection<String> fields) {
		_fields.addAll(fields);
	}

	public void addFields(String... fields) {
		_fields.addAll(Arrays.asList(fields));
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Set<String> getFields() {
		return Collections.unmodifiableSet(_fields);
	}

	public Float getFuzziness() {
		return _fuzziness;
	}

	public String getLikeText() {
		return _likeText;
	}

	public Integer getMaxQueryTerms() {
		return _maxQueryTerms;
	}

	public Integer getPrefixLength() {
		return _prefixLength;
	}

	public boolean isFieldsEmpty() {
		return _fields.isEmpty();
	}

	public Boolean isIgnoreTermFrequency() {
		return _ignoreTermFrequency;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setFuzziness(Float fuzziness) {
		_fuzziness = fuzziness;
	}

	public void setIgnoreTermFrequency(Boolean ignoreTermFrequency) {
		_ignoreTermFrequency = ignoreTermFrequency;
	}

	public void setMaxQueryTerms(Integer maxQueryTerms) {
		_maxQueryTerms = maxQueryTerms;
	}

	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	private String _analyzer;
	private final Set<String> _fields = new HashSet<>();
	private Float _fuzziness;
	private Boolean _ignoreTermFrequency;
	private final String _likeText;
	private Integer _maxQueryTerms;
	private Integer _prefixLength;

}