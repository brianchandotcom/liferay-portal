/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

/**
 * @author Marko Cikos
 */
public class SelectionFDSFilterItem {

	public SelectionFDSFilterItem(String label, Object value) {
		_label = label;
		_value = value;
	}

	public String getLabel() {
		return _label;
	}

	public Object getValue() {
		return _value;
	}

	private final String _label;
	private final Object _value;

}