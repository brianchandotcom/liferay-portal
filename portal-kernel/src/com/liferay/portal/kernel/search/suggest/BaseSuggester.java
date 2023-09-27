/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.suggest;

/**
 * @author Michael C. Han
 */
public abstract class BaseSuggester implements Suggester {

	public BaseSuggester(String name, String field) {
		_name = name;
		_field = field;
	}

	public BaseSuggester(String name, String field, String value) {
		_name = name;
		_field = field;
		_value = value;
	}

	public String getField() {
		return _field;
	}

	@Override
	public String getName() {
		return _name;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private final String _field;
	private final String _name;
	private String _value;

}