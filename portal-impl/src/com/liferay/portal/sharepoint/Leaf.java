/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
		StringBundler sb = new StringBundler(7);

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

	private String _key;
	private boolean _newLine;
	private boolean _useEqualSymbol;
	private String _value;

}