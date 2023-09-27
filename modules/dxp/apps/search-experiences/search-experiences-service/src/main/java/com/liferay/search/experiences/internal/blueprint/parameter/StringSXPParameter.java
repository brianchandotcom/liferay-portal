/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 */
public class StringSXPParameter extends BaseSXPParameter {

	public StringSXPParameter(
		String name, boolean templateVariable, String value) {

		super(name, templateVariable);

		_value = value;
	}

	@Override
	public boolean evaluateContains(Object value) {
		if (value instanceof Object[]) {
			for (Object object : (Object[])value) {
				if (StringUtil.containsIgnoreCase(
						_value, GetterUtil.getString(object),
						StringPool.BLANK)) {

					return true;
				}
			}

			return false;
		}

		return StringUtil.containsIgnoreCase(
			_value, GetterUtil.getString(value), StringPool.BLANK);
	}

	@Override
	public boolean evaluateEquals(Object object) {
		return Objects.equals(_value, GetterUtil.getString(object));
	}

	@Override
	public boolean evaluateIn(Object value) {
		return ArrayUtil.contains(
			GetterUtil.getStringValues(
				ArrayUtil.toStringArray((Object[])value)),
			_value);
	}

	@Override
	public String getValue() {
		return _value;
	}

	private final String _value;

}