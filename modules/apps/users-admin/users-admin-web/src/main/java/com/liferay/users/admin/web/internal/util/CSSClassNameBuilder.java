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

package com.liferay.users.admin.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Drew Brokke
 */
public class CSSClassNameBuilder {

	public void add(String cssClassName) {
		_add(cssClassName, true);
	}

	public void add(String cssClassName, boolean condition) {
		_add(cssClassName, condition);
	}

	public String build() {
		return _sb.toString();
	}

	private void _add(String cssClassName, boolean condition) {
		if (condition) {
			_sb.append(cssClassName);
			_sb.append(StringPool.SPACE);
		}
	}

	private final StringBundler _sb = new StringBundler();

}