/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class FuzzyQuery extends Query {

	public FuzzyQuery(String field, String value) {
		_field = field;
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public Float getFuzziness() {
		return _fuzziness;
	}

	public Integer getMaxEdits() {
		return _maxEdits;
	}

	public Integer getMaxExpansions() {
		return _maxExpansions;
	}

	public Integer getPrefixLength() {
		return _prefixLength;
	}

	public String getRewrite() {
		return _rewrite;
	}

	public Boolean getTranspositions() {
		return _transpositions;
	}

	public String getValue() {
		return _value;
	}

	public void setFuzziness(Float fuzziness) {
		_fuzziness = fuzziness;
	}

	public void setMaxEdits(Integer maxEdits) {
		_maxEdits = maxEdits;
	}

	public void setMaxExpansions(Integer maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	public void setRewrite(String rewrite) {
		_rewrite = rewrite;
	}

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