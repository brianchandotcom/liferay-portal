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
import com.liferay.portal.search.query.MultiMatchQuery;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class MultiMatchQueryImpl
	extends BaseQueryImpl implements MultiMatchQuery {

	public MultiMatchQueryImpl(Object value, Map<String, Float> fieldsBoosts) {
		_value = value;
		_fieldsBoosts = fieldsBoosts;
	}

	public MultiMatchQueryImpl(Object value, Set<String> fields) {
		_value = value;

		for (String field : fields) {
			_fieldsBoosts.put(field, null);
		}
	}

	public MultiMatchQueryImpl(Object value, String... fields) {
		_value = value;

		for (String field : fields) {
			_fieldsBoosts.put(field, null);
		}
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public String getAnalyzer() {
		return _analyzer;
	}

	@Override
	public Float getCutOffFrequency() {
		return _cutOffFrequency;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFieldsBoosts()}
	 */
	@Deprecated
	@Override
	public Set<String> getFields() {
		return _fieldsBoosts.keySet();
	}

	@Override
	public Map<String, Float> getFieldsBoosts() {
		return _fieldsBoosts;
	}

	@Override
	public String getFuzziness() {
		return _fuzziness;
	}

	@Override
	public MatchQuery.RewriteMethod getFuzzyRewriteMethod() {
		return _fuzzyRewriteMethod;
	}

	@Override
	public Integer getMaxExpansions() {
		return _maxExpansions;
	}

	@Override
	public String getMinShouldMatch() {
		return _minShouldMatch;
	}

	@Override
	public Operator getOperator() {
		return _operator;
	}

	@Override
	public Integer getPrefixLength() {
		return _prefixLength;
	}

	@Override
	public Integer getSlop() {
		return _slop;
	}

	@Override
	public Float getTieBreaker() {
		return _tieBreaker;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public MatchQuery.ZeroTermsQuery getZeroTermsQuery() {
		return _zeroTermsQuery;
	}

	@Override
	public boolean isFieldBoostsEmpty() {
		return _fieldsBoosts.isEmpty();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #isFieldBoostsEmpty()}
	 */
	@Deprecated
	@Override
	public boolean isFieldsEmpty() {
		return _fieldsBoosts.isEmpty();
	}

	@Override
	public Boolean isLenient() {
		return _lenient;
	}

	@Override
	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	@Override
	public void setCutOffFrequency(Float cutOffFrequency) {
		_cutOffFrequency = cutOffFrequency;
	}

	@Override
	public void setFuzziness(String fuzziness) {
		_fuzziness = fuzziness;
	}

	@Override
	public void setFuzzyRewriteMethod(
		MatchQuery.RewriteMethod fuzzyRewriteMethod) {

		_fuzzyRewriteMethod = fuzzyRewriteMethod;
	}

	@Override
	public void setLenient(Boolean lenient) {
		_lenient = lenient;
	}

	@Override
	public void setMaxExpansions(Integer maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	@Override
	public void setMinShouldMatch(String minShouldMatch) {
		_minShouldMatch = minShouldMatch;
	}

	@Override
	public void setOperator(Operator operator) {
		_operator = operator;
	}

	@Override
	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	@Override
	public void setSlop(Integer slop) {
		_slop = slop;
	}

	@Override
	public void setTieBreaker(Float tieBreaker) {
		_tieBreaker = tieBreaker;
	}

	public void setType(Type type) {
		_type = type;
	}

	@Override
	public void setZeroTermsQuery(MatchQuery.ZeroTermsQuery zeroTermsQuery) {
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
		sb.append(", _fieldsBoosts=");
		sb.append(_fieldsBoosts);
		sb.append(", fuzziness=");
		sb.append(_fuzziness);
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
		sb.append(", tieBreaker=");
		sb.append(_tieBreaker);
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
	private Map<String, Float> _fieldsBoosts = new HashMap<>();
	private String _fuzziness;
	private MatchQuery.RewriteMethod _fuzzyRewriteMethod;
	private Boolean _lenient;
	private Integer _maxExpansions;
	private String _minShouldMatch;
	private Operator _operator;
	private Integer _prefixLength;
	private Integer _slop;
	private Float _tieBreaker;
	private Type _type;
	private final Object _value;
	private MatchQuery.ZeroTermsQuery _zeroTermsQuery;

}