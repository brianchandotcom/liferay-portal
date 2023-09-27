/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public String getAnalyzer() {
		return _analyzer;
	}

	@Override
	public Float getCutOffFrequency() {
		return _cutOffFrequency;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Float getFuzziness() {
		return _fuzziness;
	}

	@Override
	public RewriteMethod getFuzzyRewriteMethod() {
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
	public Type getType() {
		return _type;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public ZeroTermsQuery getZeroTermsQuery() {
		return _zeroTermsQuery;
	}

	@Override
	public Boolean isFuzzyTranspositions() {
		return _fuzzyTranspositions;
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
	public void setFuzziness(Float fuzziness) {
		_fuzziness = fuzziness;
	}

	public void setFuzzyRewriteMethod(RewriteMethod fuzzyRewriteMethod) {
		_fuzzyRewriteMethod = fuzzyRewriteMethod;
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