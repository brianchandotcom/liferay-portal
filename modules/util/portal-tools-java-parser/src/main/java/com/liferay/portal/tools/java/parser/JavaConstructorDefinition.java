/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaConstructorDefinition extends BaseJavaTerm {

	public JavaConstructorDefinition(
		List<JavaAnnotation> javaAnnotations, JavaSignature javaSignature) {

		_javaAnnotations = javaAnnotations;
		_javaSignature = javaSignature;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		for (int i = 0; i < _javaAnnotations.size(); i++) {
			if (i == 0) {
				appendNewLine(
					sb, _javaAnnotations.get(i), indent, prefix, "",
					maxLineLength);

				prefix = StringPool.BLANK;
			}
			else {
				appendNewLine(
					sb, _javaAnnotations.get(i), indent, maxLineLength);
			}
		}

		appendNewLine(
			sb, _javaSignature, indent, prefix, suffix, maxLineLength);

		return sb.toString();
	}

	private final List<JavaAnnotation> _javaAnnotations;
	private final JavaSignature _javaSignature;

}