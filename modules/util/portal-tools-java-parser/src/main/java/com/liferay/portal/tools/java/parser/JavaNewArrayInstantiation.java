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