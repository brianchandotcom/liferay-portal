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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AuthorizationProperty {

	public String getKey() {
		return _key;
	}

	public String getValue() {
		return _value;
	}

	public String[] getValues() {
		return StringUtil.split(getValue());
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setValue(String value) {
		_value = value;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(_key);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	private String _key;
	private String _value;

}