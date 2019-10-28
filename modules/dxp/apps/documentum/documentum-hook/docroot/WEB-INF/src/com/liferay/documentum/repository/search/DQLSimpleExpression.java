/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.documentum.repository.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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