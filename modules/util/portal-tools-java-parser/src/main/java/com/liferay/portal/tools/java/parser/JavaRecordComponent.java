/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alan Huang
 */
public class JavaRecordComponent extends BaseJavaTerm {

	public JavaRecordComponent(JavaType javaType, String name) {
		_javaType = javaType;
		_name = new JavaSimpleValue(name);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		indent = append(sb, _javaType, indent, "", " ", maxLineLength, false);

		append(sb, _name, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final JavaType _javaType;
	private final JavaSimpleValue _name;

}