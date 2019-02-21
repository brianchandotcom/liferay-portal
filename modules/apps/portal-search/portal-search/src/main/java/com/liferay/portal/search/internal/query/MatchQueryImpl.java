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
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class MatchQueryImpl extends BaseQueryImpl implements MatchQuery {

	public MatchQueryImpl(String field, Object value) {
		_field = field;
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
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

	public Object getValue() {
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

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{analyzer=");
		sb.append(_analyzer);
		sb.append(", className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", cutOffFrequency=");
		sb.append(_cutOffFrequency);
		sb.append(", field=");
		sb.append(_field);
		sb.append(", fuzziness=");
		sb.append(_fuzziness);
		sb.append(", fuzzyTranspositions=");
		sb.append(_fuzzyTranspositions);
		sb.append(", lenient=");
		sb.append(_lenient);
		sb.append(", maxExpansions=");
		sb.append(_maxExpansions);
		sb.append(", minShouldMatch=");
		sb.append(_minShouldMatch);
		sb.append(", operator=");
		sb.append(_operator);
		sb.append(", prefixLength=");
		sb.append(_prefixLength);
		sb.append(", slop=");
		sb.append(_slop);
		sb.append(", type=");
		sb.append(_type);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

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
	private final Object _value;
	private ZeroTermsQuery _zeroTermsQuery;

}