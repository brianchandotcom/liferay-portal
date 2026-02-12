/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class MultiMatchQuery extends Query {

	public MultiMatchQuery(Object value, Map<String, Float> fieldsBoosts) {
		_value = value;
		_fieldsBoosts = fieldsBoosts;
	}

	public MultiMatchQuery(Object value, Set<String> fields) {
		_value = value;

		for (String field : fields) {
			_fieldsBoosts.put(field, null);
		}
	}

	public MultiMatchQuery(Object value, String... fields) {
		_value = value;

		for (String field : fields) {
			_fieldsBoosts.put(field, null);
		}
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getFieldsBoosts()}
	 */
	@Deprecated
	public Set<String> getFields() {
		return _fieldsBoosts.keySet();
	}

	public Map<String, Float> getFieldsBoosts() {
		return _fieldsBoosts;
	}

	public String getFuzziness() {
		return _fuzziness;
	}

	public MatchQuery.RewriteMethod getFuzzyRewriteMethod() {
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

	public Float getTieBreaker() {
		return _tieBreaker;
	}

	public Type getType() {
		return _type;
	}

	public Object getValue() {
		return _value;
	}

	public MatchQuery.ZeroTermsQuery getZeroTermsQuery() {
		return _zeroTermsQuery;
	}

	public boolean isFieldBoostsEmpty() {
		return _fieldsBoosts.isEmpty();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #isFieldBoostsEmpty()}
	 */
	@Deprecated
	public boolean isFieldsEmpty() {
		return _fieldsBoosts.isEmpty();
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

	public void setFuzziness(String fuzziness) {
		_fuzziness = fuzziness;
	}

	public void setFuzzyRewriteMethod(
		MatchQuery.RewriteMethod fuzzyRewriteMethod) {

		_fuzzyRewriteMethod = fuzzyRewriteMethod;
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

	public void setTieBreaker(Float tieBreaker) {
		_tieBreaker = tieBreaker;
	}

	public void setType(Type type) {
		_type = type;
	}

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

	public enum Type {

		BEST_FIELDS, BOOL_PREFIX, CROSS_FIELDS, MOST_FIELDS, PHRASE,
		PHRASE_PREFIX

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