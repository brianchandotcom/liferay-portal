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

package com.liferay.info.item.provider.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Jorge Ferrer
 */
public class PropertyInfoItemServiceFilter implements InfoItemServiceFilter {

	public PropertyInfoItemServiceFilter(
		String propertyName, String propertyValue) {

		_propertyName = propertyName;
		_propertyValue = propertyValue;
	}

	public String getFilterString() {
		return StringBundler.concat(
			"(", _propertyName, StringPool.EQUAL, _propertyValue, ")");
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public String getPropertyValue() {
		return _propertyValue;
	}

	private final String _propertyName;
	private final String _propertyValue;

}