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

package com.liferay.portal.kernel.theme;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeCompanyId implements Serializable {

	public ThemeCompanyId(String value, boolean pattern) {
		_value = value;
		_pattern = pattern;
	}

	public String getValue() {
		return _value;
	}

	public boolean isPattern() {
		return _pattern;
	}

	private final boolean _pattern;
	private final String _value;

}