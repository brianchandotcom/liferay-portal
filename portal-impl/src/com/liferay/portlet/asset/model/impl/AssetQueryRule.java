/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

/**
 * @author Roberto Díaz
 */
public class AssetQueryRule {

	public AssetQueryRule(
		boolean andOperator, boolean contains, String name, String[] values) {

		_andOperator = andOperator;
		_contains = contains;
		_name = name;
		_values = values;
	}

	public boolean equals(AssetQueryRule assetQueryRule) {
		if (!_name.equals(assetQueryRule.getName())) {
			return false;
		}

		if (_contains != assetQueryRule.getContains()) {
			return false;
		}

		if (_andOperator != assetQueryRule.getAndOperator()) {
			return false;
		}

		return true;
	}

	public boolean getAndOperator() {
		return _andOperator;
	}

	public boolean getContains() {
		return _contains;
	}

	public String getName() {
		return _name;
	}

	public String[] getValues() {
		return _values;
	}

	public void setAndOperator(boolean andOperator) {
		_andOperator = andOperator;
	}

	public void setContains(boolean contains) {
		_contains = contains;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValues(String[] values) {
		_values = values;
	}

	private boolean _andOperator;
	private boolean _contains;
	private String _name;
	private String[] _values;

}