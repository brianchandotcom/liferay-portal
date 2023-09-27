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
public class Leaf implements ResponseElement {

	public static final String OPEN_LI = "<li>";

	public Leaf(String key, ResponseElement value) {
		this(key, StringPool.NEW_LINE + value.parse(), true, false);
	}

	public Leaf(String key, String value, boolean useEqualSymbol) {
		this(key, value, useEqualSymbol, true);
	}

	public Leaf(
		String key, String value, boolean useEqualSymbol, boolean newLine) {

		_key = key;
		_value = value;
		_useEqualSymbol = useEqualSymbol;
		_newLine = newLine;
	}

	@Override
	public String parse() {
		StringBundler sb = new StringBundler(6);

		if (_useEqualSymbol) {
			sb.append(OPEN_LI);

			sb.append(_key);
			sb.append(StringPool.EQUAL);
			sb.append(_value);
		}
		else {
			sb.append(OPEN_LI);
			sb.append(_key);

			sb.append(StringPool.NEW_LINE);

			sb.append(OPEN_LI);
			sb.append(_value);
		}

		if (_newLine) {
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private final String _key;
	private final boolean _newLine;
	private final boolean _useEqualSymbol;
	private final String _value;

}