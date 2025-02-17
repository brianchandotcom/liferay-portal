/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.parser;

/**
 * @author Hugo Huijser
 */
public class JavaVariable extends BaseJavaTerm {

	public JavaVariable(
		String accessModifier, String content, boolean isAbstract,
		boolean isFinal, boolean isStatic, int lineNumber, String name) {

		super(
			accessModifier, content, isAbstract, isFinal, isStatic, lineNumber,
			name);
	}

}