/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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