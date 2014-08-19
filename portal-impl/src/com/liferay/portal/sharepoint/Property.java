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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Bruno Farache
 */
public class Property implements ResponseElement {

	public static final String OPEN_PARAGRAPH = "<p>";

	public Property(String key, ResponseElement value) {
		this(key, StringPool.NEW_LINE + value.parse(), false);
	}

	public Property(String key, String value) {
		this(key, value, true);
	}

	public Property(String key, String value, boolean newLine) {
		_key = key;
		_value = value;
		_newLine = newLine;
	}

	@Override
	public String parse() {
		StringBundler sb = new StringBundler(5);

		sb.append(OPEN_PARAGRAPH);
		sb.append(_key);
		sb.append(StringPool.EQUAL);
		sb.append(_value);

		if (_newLine) {
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private final String _key;
	private final boolean _newLine;
	private final String _value;

}