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

package com.liferay.portal.search.web.internal.result.display.context;

import java.io.Serializable;

/**
 * @author André de Oliveira
 */
public class SearchResultFieldDisplayContext implements Serializable {

	public float getBoost() {
		return _boost;
	}

	public String getName() {
		return _name;
	}

	public String getValuesToString() {
		return _valuesToString;
	}

	public boolean isArray() {
		return _isArray;
	}

	public boolean isNumeric() {
		return _isNumeric;
	}

	public boolean isTokenized() {
		return _isTokenized;
	}

	public void setArray(boolean isArray) {
		_isArray = isArray;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNumeric(boolean isNumeric) {
		_isNumeric = isNumeric;
	}

	public void setTokenized(boolean isTokenized) {
		_isTokenized = isTokenized;
	}

	public void setValuesToString(String valuesToString) {
		_valuesToString = valuesToString;
	}

	private float _boost;
	private boolean _isArray;
	private boolean _isNumeric;
	private boolean _isTokenized;
	private String _name;
	private String _valuesToString;

}