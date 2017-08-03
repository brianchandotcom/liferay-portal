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