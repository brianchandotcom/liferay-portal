/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class SetOperationType {

	public static final SetOperationType UNION = new SetOperationType("union");

	public static final SetOperationType UNION_ALL = new SetOperationType(
		"union all");

	public SetOperationType(String value) {
		_value = Objects.requireNonNull(value);

		_valueWithSpaces = " ".concat(_value.concat(" "));
	}

	public String getStringWithSpaces() {
		return _valueWithSpaces;
	}

	@Override
	public String toString() {
		return _value;
	}

	private final String _value;
	private final String _valueWithSpaces;

}