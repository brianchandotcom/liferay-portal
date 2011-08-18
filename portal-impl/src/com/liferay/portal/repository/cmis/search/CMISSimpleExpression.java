/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis.search;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Mika Koivisto
 */
public class CMISSimpleExpression implements CMISCriterion {

	public CMISSimpleExpression(
		String field, String value, CMISSimpleExpressionOperator operator) {

		_field = field;
		_operator = operator;
		_value = value;
	}

	public String toQueryFragment() {
		StringBundler sb = new StringBundler();

		sb.append(_field);
		sb.append(StringPool.SPACE);
		sb.append(_operator);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.APOSTROPHE);
		sb.append(_value);
		sb.append(StringPool.APOSTROPHE);

		return sb.toString();
	}

	private String _field;
	private CMISSimpleExpressionOperator _operator;
	private String _value;

}