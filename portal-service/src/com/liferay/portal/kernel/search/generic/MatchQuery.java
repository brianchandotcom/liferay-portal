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

/**
 * @author Michael C. Han
 */
public class MatchQuery extends BaseQueryImpl {

	public MatchQuery(String field, String value) {
		_field = field;
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Float getCutOffFrequency() {
		return _cutOffFrequency;
	}

	public String getField() {
		return _field;
	}

	public Float getFuzziness() {
		return _fuzziness;
	}

	public RewriteMethod getFuzzyRewriteMethod() {
		return _fuzzyRewriteMethod;
	}

	public Integer getMaxExpansions() {
		return _maxExpansions;
	}

	public String getMinShouldMatch() {
		return _minShouldMatch;
	}

	public Operator getOperator() {
		return _operator;
	}

	public Integer getPrefixLength() {
		return _prefixLength;
	}

	public Integer getSlop() {
		return _slop;
	}

	public Type getType() {
		return _type;
	}

	public String getValue() {
		return _value;
	}

	public ZeroTermsQuery getZeroTermsQuery() {
		return _zeroTermsQuery;
	}

	public Boolean isFuzzyTranspositions() {
		return _fuzzyTranspositions;
	}

	public Boolean isLenient() {
		return _lenient;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setCutOffFrequency(Float cutOffFrequency) {
		_cutOffFrequency = cutOffFrequency;
	}

	public void setFuzziness(Float fuzziness) {
		_fuzziness = fuzziness;
	}

	public void setFuzzyRewriteMethod(RewriteMethod fuzzyRewriteMethod) {
		_fuzzyRewriteMethod = fuzzyRewriteMethod;
	}

	public void setFuzzyTranspositions(Boolean fuzzyTranspositions) {
		_fuzzyTranspositions = fuzzyTranspositions;
	}

	public void setLenient(Boolean lenient) {
		_lenient = lenient;
	}

	public void setMaxExpansions(Integer maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	public void setMinShouldMatch(String minShouldMatch) {
		_minShouldMatch = minShouldMatch;
	}

	public void setOperator(Operator operator) {
		_operator = operator;
	}

	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	public void setSlop(Integer slop) {
		_slop = slop;
	}

	public void setType(Type type) {
		_type = type;
	}

	public void setZeroTermsQuery(ZeroTermsQuery zeroTermsQuery) {
		_zeroTermsQuery = zeroTermsQuery;
	}

	public enum Operator {

		AND, OR

	}

	public enum RewriteMethod {

		CONSTANT_SCORE_AUTO, CONSTANT_SCORE_BOOLEAN, CONSTANT_SCORE_FILTER,
		SCORING_BOOLEAN, TOP_TERMS_N, TOP_TERMS_BOOST_N

	}

	public enum Type {

		BOOLEAN, PHRASE, PHRASE_PREFIX

	}

	public enum ZeroTermsQuery {

		ALL, NONE

	}

	private String _analyzer;
	private Float _cutOffFrequency;
	private final String _field;
	private Float _fuzziness;
	private RewriteMethod _fuzzyRewriteMethod;
	private Boolean _fuzzyTranspositions;
	private Boolean _lenient;
	private Integer _maxExpansions;
	private String _minShouldMatch;
	private Operator _operator;
	private Integer _prefixLength;
	private Integer _slop;
	private Type _type;
	private final String _value;
	private ZeroTermsQuery _zeroTermsQuery;

}