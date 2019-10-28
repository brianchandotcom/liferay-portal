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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class DQLDateExpression extends DQLSimpleExpression {

	public DQLDateExpression(
		String field, Date value,
		DQLSimpleExpressionOperator dqlSimpleExpressionOperator) {

		super(field, _format(value), dqlSimpleExpressionOperator);
	}

	@Override
	public String toQueryFragment() {
		StringBundler sb = new StringBundler(9);

		sb.append(getField());
		sb.append(StringPool.SPACE);
		sb.append(getDQLSimpleExpressionOperator());
		sb.append(StringPool.SPACE);
		sb.append("DATE('");
		sb.append(getValue());
		sb.append("', '");
		sb.append(_DATE_FORMAT_PATTERN);
		sb.append("')");

		return sb.toString();
	}

	private static String _format(Date value) {
		DateFormat dateFormat = new SimpleDateFormat(_DATE_FORMAT_PATTERN);

		return dateFormat.format(value);
	}

	private static final String _DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";

}