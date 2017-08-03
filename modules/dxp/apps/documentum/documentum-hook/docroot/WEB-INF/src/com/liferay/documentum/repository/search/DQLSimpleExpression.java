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

package com.liferay.documentum.repository.search;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Mika Koivisto
 */
public class DQLSimpleExpression implements DQLCriterion {

	public DQLSimpleExpression(
		String field, String value,
		DQLSimpleExpressionOperator dqlSimpleExpressionOperator) {

		_field = field;
		_value = value;
		_dqlSimpleExpressionOperator = dqlSimpleExpressionOperator;
	}

	public DQLSimpleExpressionOperator getDQLSimpleExpressionOperator() {
		return _dqlSimpleExpressionOperator;
	}

	public String getField() {
		return _field;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toQueryFragment() {
		StringBundler sb = new StringBundler(7);

		sb.append(_field);
		sb.append(StringPool.SPACE);
		sb.append(_dqlSimpleExpressionOperator);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.APOSTROPHE);
		sb.append(_value);
		sb.append(StringPool.APOSTROPHE);

		return sb.toString();
	}

	private final DQLSimpleExpressionOperator _dqlSimpleExpressionOperator;
	private final String _field;
	private final String _value;

}