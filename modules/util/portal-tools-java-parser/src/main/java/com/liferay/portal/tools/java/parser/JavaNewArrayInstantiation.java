/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public class JavaNewArrayInstantiation extends BaseJavaExpression {

	public void setInitialJavaArray(JavaArray initialJavaArray) {
		_initialJavaArray = initialJavaArray;
	}

	public void setJavaArrayDeclarator(
		JavaArrayDeclarator javaArrayDeclarator) {

		_javaArrayDeclarator = javaArrayDeclarator;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);

		if (_initialJavaArray == null) {
			if (forceLineBreak) {
				appendWithLineBreak(
					sb, _javaArrayDeclarator, indent, "new ", suffix,
					maxLineLength);
			}
			else {
				append(
					sb, _javaArrayDeclarator, indent, "new ", suffix,
					maxLineLength);
			}

			return sb.toString();
		}

		append(sb, _javaArrayDeclarator, indent, "new ", "", maxLineLength);

		if (forceLineBreak) {
			appendWithLineBreak(
				sb, _initialJavaArray, indent, " ", suffix, maxLineLength);
		}
		else {
			append(
				sb, _initialJavaArray, indent, " ", suffix, maxLineLength,
				false);
		}

		return sb.toString();
	}

	private JavaArray _initialJavaArray;
	private JavaArrayDeclarator _javaArrayDeclarator;

}