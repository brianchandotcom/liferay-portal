/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class SafeProperties extends Properties {

	@Override
	public synchronized Object get(Object key) {
		Object value = super.get(key);

		return _decode((String)value);
	}

	public String getEncodedProperty(String key) {
		return super.getProperty(key);
	}

	@Override
	public String getProperty(String key) {
		return (String)get(key);
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		if (key == null) {
			return null;
		}

		if (value == null) {
			return super.remove(key);
		}

		value = _encode((String)value);

		return super.put(key, value);
	}

	@Override
	public synchronized Object remove(Object key) {
		if (key == null) {
			return null;
		}

		return super.remove(key);
	}

	private String _decode(String value) {
		return StringUtil.replace(
			value, _SAFE_NEWLINE_CHARACTER, StringPool.NEW_LINE);
	}

	private String _encode(String value) {
		return StringUtil.replace(
			value,
			new String[] {
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE,
				StringPool.RETURN
			},
			new String[] {
				_SAFE_NEWLINE_CHARACTER, _SAFE_NEWLINE_CHARACTER,
				_SAFE_NEWLINE_CHARACTER
			});
	}

	private static final String _SAFE_NEWLINE_CHARACTER =
		"_SAFE_NEWLINE_CHARACTER_";

}