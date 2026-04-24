/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class QueryTerm implements Serializable {

	public QueryTerm(String field, String value) {
		_field = field;
		_value = value;
	}

	public String getField() {
		return _field;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{field=", _field, ", value=", _value, "}");
	}

	private final String _field;
	private final String _value;

}