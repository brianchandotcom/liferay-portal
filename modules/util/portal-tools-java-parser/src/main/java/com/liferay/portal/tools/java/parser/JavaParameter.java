/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaParameter extends BaseJavaTerm {

	public JavaParameter(
		String name, List<JavaSimpleValue> modifiers, JavaType javaType) {

		_name = new JavaSimpleValue(name);
		_modifiers = modifiers;
		_javaType = javaType;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		if (ListUtil.isNotEmpty(_modifiers)) {
			indent = append(
				sb, _modifiers, " ", indent, "", " ", maxLineLength);
		}

		indent = append(sb, _javaType, indent, "", " ", maxLineLength, false);

		append(sb, _name, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final JavaType _javaType;
	private final List<JavaSimpleValue> _modifiers;
	private final JavaSimpleValue _name;

}