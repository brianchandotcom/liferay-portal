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

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public class JavaImport extends BaseJavaTerm {

	public JavaImport(String name, boolean isStatic) {
		_name = new JavaSimpleValue(name);
		_isStatic = isStatic;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		if (_isStatic) {
			append(sb, _name, indent, prefix + "import static ", suffix, -1);
		}
		else {
			append(sb, _name, indent, prefix + "import ", suffix, -1);
		}

		return sb.toString();
	}

	private final boolean _isStatic;
	private final JavaSimpleValue _name;

}