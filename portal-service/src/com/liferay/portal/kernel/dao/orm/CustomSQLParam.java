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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomSQLParam {

	public CustomSQLParam(String sql, Object value) {
		_sql = sql;
		_value = value;
	}

	public String getSQL() {
		return _sql;
	}

	public void process(QueryPos qPos) {
		if (_value instanceof Long) {
			Long valueLong = (Long)_value;

			if (valueLong != null) {
				qPos.add(valueLong);
			}
		}
		else if (_value instanceof Long[]) {
			Long[] valueArray = (Long[])_value;

			for (int i = 0; i < valueArray.length; i++) {
				if (valueArray[i] != null) {
					qPos.add(valueArray[i]);
				}
			}
		}
		else if (_value instanceof String) {
			String valueString = (String)_value;

			if (Validator.isNotNull(valueString)) {
				qPos.add(valueString);
			}
		}
		else if (_value instanceof String[]) {
			String[] valueArray = (String[])_value;

			for (int i = 0; i < valueArray.length; i++) {
				if (Validator.isNotNull(valueArray[i])) {
					qPos.add(valueArray[i]);
				}
			}
		}
	}

	private final String _sql;
	private final Object _value;

}