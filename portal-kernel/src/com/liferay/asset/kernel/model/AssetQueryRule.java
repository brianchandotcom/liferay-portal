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

package com.liferay.asset.kernel.model;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author     Roberto Díaz
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.asset.publisher.web.util.AssetQueryRule}
 */
@Deprecated
public class AssetQueryRule {

	public AssetQueryRule(
		boolean contains, boolean andOperator, String name, String[] values) {

		_contains = contains;
		_andOperator = andOperator;
		_name = name;
		_values = values;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetQueryRule)) {
			return false;
		}

		AssetQueryRule assetQueryRule = (AssetQueryRule)obj;

		if ((_contains == assetQueryRule._contains) &&
			(_andOperator == assetQueryRule._andOperator) &&
			Objects.equals(_name, assetQueryRule._name)) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public String[] getValues() {
		return _values;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _contains);

		hash = HashUtil.hash(hash, _andOperator);

		return HashUtil.hash(hash, _name);
	}

	public boolean isAndOperator() {
		return _andOperator;
	}

	public boolean isContains() {
		return _contains;
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