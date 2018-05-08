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
		return _array;
	}

	public boolean isNumeric() {
		return _numeric;
	}

	public boolean isTokenized() {
		return _tokenized;
	}

	public void setArray(boolean array) {
		_array = array;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNumeric(boolean numeric) {
		_numeric = numeric;
	}

	public void setTokenized(boolean tokenized) {
		_tokenized = tokenized;
	}

	public void setValuesToString(String valuesToString) {
		_valuesToString = valuesToString;
	}

	private boolean _array;
	private float _boost;
	private String _name;
	private boolean _numeric;
	private boolean _tokenized;
	private String _valuesToString;

}