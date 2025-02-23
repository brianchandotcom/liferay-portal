/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public class JavaImport extends BaseJavaTerm {

	public JavaImport(boolean isStatic, String name) {
		_isStatic = isStatic;
		_name = new JavaSimpleValue(name);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		if (_isStatic) {
			append(
				sb, _name, indent, prefix + "import static ", suffix,
				NO_MAX_LINE_LENGTH);
		}
		else {
			append(
				sb, _name, indent, prefix + "import ", suffix,
				NO_MAX_LINE_LENGTH);
		}

		return sb.toString();
	}

	private final boolean _isStatic;
	private final JavaSimpleValue _name;

}