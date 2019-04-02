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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.SimpleStringQuery;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides support for parsing raw, human readable query syntax. No
 * transformation is made on user input.
 *
 * <p>
 * The actual query syntax and any further processing are dependent on your
 * search engine's implementation details. Consult your search provider's
 * documentation for more information.
 * </p>
 *
 * @author Michael C. Han
 */
public class SimpleStringQueryImpl
	extends BaseQueryImpl implements SimpleStringQuery {

	public SimpleStringQueryImpl(String query) {
		_query = query;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public void addField(String field, float boost) {
		_fieldBoostMap.put(field, boost);
	}

	@Override
	public void addFields(String... fields) {
		for (String field : fields) {
			_fieldBoostMap.put(field, null);
		}
	}

	@Override
	public String getAnalyzer() {
		return _analyzer;
	}

	@Override
	public Boolean getAnalyzeWildcard() {
		return _analyzeWildcard;
	}

	@Override
	public Boolean getAutoGenerateSynonymsPhraseQuery() {
		return _autoGenerateSynonymsPhraseQuery;
	}

	@Override
	public Operator getDefaultOperator() {
		return _defaultOperator;
	}

	@Override
	public Map<String, Float> getFieldBoostMap() {
		return Collections.unmodifiableMap(_fieldBoostMap);
	}

	@Override
	public Integer getFuzzyMaxExpansions() {
		return _fuzzyMaxExpansions;
	}

	@Override
	public Integer getFuzzyPrefixLength() {
		return _fuzzyPrefixLength;
	}

	@Override
	public Boolean getFuzzyTranspositions() {
		return _fuzzyTranspositions;
	}

	@Override
	public Boolean getLenient() {
		return _lenient;
	}

	@Override
	public String getQuery() {
		return _query;
	}

	@Override
	public String getQuoteFieldSuffix() {
		return _quoteFieldSuffix;
	}

	@Override
	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	@Override
	public void setAnalyzeWildcard(Boolean analyzeWildcard) {
		_analyzeWildcard = analyzeWildcard;
	}

	@Override
	public void setAutoGenerateSynonymsPhraseQuery(
		Boolean autoGenerateSynonymsPhraseQuery) {

		_autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
	}

	@Override
	public void setDefaultOperator(Operator defaultOperator) {
		_defaultOperator = defaultOperator;
	}

	@Override
	public void setFuzzyMaxExpansions(Integer fuzzyMaxExpansions) {
		_fuzzyMaxExpansions = fuzzyMaxExpansions;
	}

	@Override
	public void setFuzzyPrefixLength(Integer fuzzyPrefixLength) {
		_fuzzyPrefixLength = fuzzyPrefixLength;
	}

	@Override
	public void setFuzzyTranspositions(Boolean fuzzyTranspositions) {
		_fuzzyTranspositions = fuzzyTranspositions;
	}

	@Override
	public void setLenient(Boolean lenient) {
		_lenient = lenient;
	}

	@Override
	public void setQuoteFieldSuffix(String quoteFieldSuffix) {
		_quoteFieldSuffix = quoteFieldSuffix;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", query=");
		sb.append(_query);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private String _analyzer;
	private Boolean _analyzeWildcard;
	private Boolean _autoGenerateSynonymsPhraseQuery;
	private Operator _defaultOperator;
	private final Map<String, Float> _fieldBoostMap = new HashMap<>();
	private Integer _fuzzyMaxExpansions;
	private Integer _fuzzyPrefixLength;
	private Boolean _fuzzyTranspositions;
	private Boolean _lenient;
	private final String _query;
	private String _quoteFieldSuffix;

}