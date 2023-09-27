/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.FuzzyQuery;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class FuzzyQueryImpl extends BaseQueryImpl implements FuzzyQuery {

	public FuzzyQueryImpl(String field, String value) {
		_field = field;
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
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
	public Integer getMaxEdits() {
		return _maxEdits;
	}

	@Override
	public Integer getMaxExpansions() {
		return _maxExpansions;
	}

	@Override
	public Integer getPrefixLength() {
		return _prefixLength;
	}

	@Override
	public String getRewrite() {
		return _rewrite;
	}

	@Override
	public Boolean getTranspositions() {
		return _transpositions;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setFuzziness(Float fuzziness) {
		_fuzziness = fuzziness;
	}

	@Override
	public void setMaxEdits(Integer maxEdits) {
		_maxEdits = maxEdits;
	}

	@Override
	public void setMaxExpansions(Integer maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	@Override
	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	@Override
	public void setRewrite(String rewrite) {
		_rewrite = rewrite;
	}

	@Override
	public void setTranspositions(Boolean transpositions) {
		_transpositions = transpositions;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", field=");
		sb.append(_field);
		sb.append(", fuzziness=");
		sb.append(_fuzziness);
		sb.append(", maxEdits=");
		sb.append(_maxEdits);
		sb.append(", maxExpansions=");
		sb.append(_maxExpansions);
		sb.append(", prefixLength=");
		sb.append(_prefixLength);
		sb.append(", transpositions=");
		sb.append(_transpositions);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private Float _fuzziness;
	private Integer _maxEdits;
	private Integer _maxExpansions;
	private Integer _prefixLength;
	private String _rewrite;
	private Boolean _transpositions;
	private final String _value;

}