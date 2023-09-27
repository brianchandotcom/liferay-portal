/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
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