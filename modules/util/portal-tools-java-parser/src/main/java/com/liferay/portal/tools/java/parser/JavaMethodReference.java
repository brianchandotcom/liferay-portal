/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaMethodReference extends BaseJavaExpression {

	public JavaMethodReference(
		String methodName, JavaExpression referenceJavaExpression,
		List<JavaType> genericJavaTypes) {

		_methodName = new JavaSimpleValue(methodName);
		_referenceJavaExpression = referenceJavaExpression;
		_genericJavaTypes = genericJavaTypes;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		if (_genericJavaTypes != null) {
			append(sb, _referenceJavaExpression, indent, maxLineLength);
			append(sb, _genericJavaTypes, indent, "<", ">::", maxLineLength);
		}
		else {
			append(
				sb, _referenceJavaExpression, indent, "", "::", maxLineLength);
		}

		append(sb, _methodName, indent, "", suffix, maxLineLength);

		return sb.toString();
	}

	private final List<JavaType> _genericJavaTypes;
	private final JavaSimpleValue _methodName;
	private final JavaExpression _referenceJavaExpression;

}