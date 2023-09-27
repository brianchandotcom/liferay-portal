/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaSwitchCaseStatement extends BaseJavaTerm {

	public void addDefault() {
		_hasDefault = true;
	}

	public void addSwitchCaseJavaExpression(
		JavaExpression switchCaseJavaExpression) {

		_switchCaseJavaExpressions.add(switchCaseJavaExpression);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		for (JavaExpression switchCaseJavaExpression :
				_switchCaseJavaExpressions) {

			appendNewLine(
				sb, switchCaseJavaExpression, indent, prefix + "case ", suffix,
				maxLineLength);

			prefix = StringPool.BLANK;
		}

		if (_hasDefault) {
			if (sb.index() > 0) {
				sb.append("\n");
			}

			sb.append(prefix);
			sb.append(indent);
			sb.append("default");
			sb.append(suffix);
		}

		return sb.toString();
	}

	private boolean _hasDefault;
	private final List<JavaExpression> _switchCaseJavaExpressions =
		new ArrayList<>();

}