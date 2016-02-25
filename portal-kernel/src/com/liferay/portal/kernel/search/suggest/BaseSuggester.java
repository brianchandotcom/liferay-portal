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