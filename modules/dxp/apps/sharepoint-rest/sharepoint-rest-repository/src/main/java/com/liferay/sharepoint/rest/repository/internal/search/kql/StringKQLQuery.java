/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class StringKQLQuery implements KQLQuery {

	public StringKQLQuery(String field, String value) {
		_field = field;
		_value = value;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", _field, _value);
	}

	private final String _field;
	private final String _value;

}