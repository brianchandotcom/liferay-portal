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

package com.liferay.frontend.data.set.filter;

/**
 * @author Marco Leo
 */
public class RadioFDSFilterItem {

	public RadioFDSFilterItem(String label, Object value) {
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