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
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Michael C. Han
 */
public class FuzzyQuery extends BaseQueryImpl {

	public FuzzyQuery(String field, String value) {
		_field = field;
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
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

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

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
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private final String _field;
	private Float _fuzziness;
	private Integer _maxEdits;
	private Integer _maxExpansions;
	private Integer _prefixLength;
	private final String _value;

}