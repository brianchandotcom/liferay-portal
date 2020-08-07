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
import com.liferay.portal.search.query.StringQuery;

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
 * @author Bruno Farache
 * @author Petteri Karttunen
 */
public class StringQueryImpl extends BaseQueryImpl implements StringQuery {

	public StringQueryImpl(String query) {
		_query = query;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public void addField(String field) {
		_fields.put(field, null);
	}

	@Override
	public void addField(String field, Float boost) {
		_fields.put(field, boost);
	}

	@Override
	public Boolean getAllowLeadingWildcard() {
		return _allowLeadingWildcard;
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
	public String getDefaultField() {
		return _defaultField;
	}

	@Override
	public Operator getDefaultOperator() {
		return _defaultOperator;
	}

	@Override
	public Boolean getEnablePositionIncrements() {
		return _enablePositionIncrements;
	}

	@Override
	public Boolean getEscape() {
		return _escape;
	}

	@Override
	public Map<String, Float> getFields() {
		return _fields;
	}

	@Override
	public Float getFuzziness() {
		return _fuzziness;
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
	public String getFuzzyRewrite() {
		return _fuzzyRewrite;
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
	public Integer getMaxDeterminedStates() {
		return _maxDeterminedStates;
	}

	@Override
	public String getMinimumShouldMatch() {
		return _minimumShouldMatch;
	}

	@Override
	public Integer getPhraseSlop() {
		return _phraseSlop;
	}

	@Override
	public String getQuery() {
		return _query;
	}

	@Override
	public String getQuoteAnalyzer() {
		return _quoteAnalyzer;
	}

	@Override
	public String getQuoteFieldSuffix() {
		return _quoteFieldSuffix;
	}

	@Override
	public String getRewrite() {
		return _rewrite;
	}

	@Override
	public Float getTieBreaker() {
		return _tieBreaker;
	}

	@Override
	public String getTimeZone() {
		return _timeZone;
	}

	@Override
	public void setAllowLeadingWildcard(Boolean allowLeadingWildcard) {
		_allowLeadingWildcard = allowLeadingWildcard;
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
	public void setDefaultField(String defaultField) {
		_defaultField = defaultField;
	}

	@Override
	public void setDefaultOperator(Operator defaultOperator) {
		_defaultOperator = defaultOperator;
	}

	@Override
	public void setEnablePositionIncrements(Boolean enablePositionIncrements) {
		_enablePositionIncrements = enablePositionIncrements;
	}

	@Override
	public void setEscape(boolean escape) {
		_escape = escape;
	}

	@Override
	public void setFuzziness(Float fuzziness) {
		_fuzziness = fuzziness;
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
	public void setFuzzyRewrite(String fuzzyRewrite) {
		_fuzzyRewrite = fuzzyRewrite;
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
	public void setMaxDeterminedStates(Integer maxDeterminedStates) {
		_maxDeterminedStates = maxDeterminedStates;
	}

	@Override
	public void setMinimumShouldMatch(String minimumShouldMatch) {
		_minimumShouldMatch = minimumShouldMatch;
	}

	@Override
	public void setPhraseSlop(Integer phraseSlop) {
		_phraseSlop = phraseSlop;
	}

	@Override
	public void setQuoteAnalyzer(String quoteAnalyzer) {
		_quoteAnalyzer = quoteAnalyzer;
	}

	@Override
	public void setQuoteFieldSuffix(String quoteFieldSuffix) {
		_quoteFieldSuffix = quoteFieldSuffix;
	}

	@Override
	public void setRewrite(String rewrite) {
		_rewrite = rewrite;
	}

	@Override
	public void setTieBreaker(float tieBreaker) {
		_tieBreaker = tieBreaker;
	}

	@Override
	public void setTimeZone(String timeZone) {
		_timeZone = timeZone;
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

	private Boolean _allowLeadingWildcard;
	private String _analyzer;
	private Boolean _analyzeWildcard;
	private Boolean _autoGenerateSynonymsPhraseQuery;
	private String _defaultField;
	private Operator _defaultOperator;
	private Boolean _enablePositionIncrements;
	private Boolean _escape;
	private Map<String, Float> _fields = new HashMap<>();
	private Float _fuzziness;
	private Integer _fuzzyMaxExpansions;
	private Integer _fuzzyPrefixLength;
	private String _fuzzyRewrite;
	private Boolean _fuzzyTranspositions;
	private Boolean _lenient;
	private Integer _maxDeterminedStates;
	private String _minimumShouldMatch;
	private Integer _phraseSlop;
	private final String _query;
	private String _quoteAnalyzer;
	private String _quoteFieldSuffix;
	private String _rewrite;
	private Float _tieBreaker;
	private String _timeZone;

}