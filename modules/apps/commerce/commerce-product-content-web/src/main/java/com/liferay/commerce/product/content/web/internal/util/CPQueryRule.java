/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Marco Leo
 */
public class CPQueryRule {

	public CPQueryRule(
		boolean contains, boolean andOperator, String name, String[] values) {

		_contains = contains;
		_andOperator = andOperator;
		_name = name;
		_values = values.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPQueryRule)) {
			return false;
		}

		CPQueryRule assetQueryRule = (CPQueryRule)object;

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
		return _values.clone();
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
		_values = values.clone();
	}

	private boolean _andOperator;
	private boolean _contains;
	private String _name;
	private String[] _values;

}