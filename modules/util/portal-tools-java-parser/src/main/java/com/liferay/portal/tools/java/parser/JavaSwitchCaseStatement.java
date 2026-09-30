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

	public void addSwitchCaseJavaTerms(List<JavaTerm> switchCaseJavaTerms) {
		_switchCaseJavaTermsList.add(switchCaseJavaTerms);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		for (List<JavaTerm> switchCaseJavaTerms : _switchCaseJavaTermsList) {
			if (switchCaseJavaTerms.size() == 1) {
				appendNewLine(
					sb, switchCaseJavaTerms.get(0), indent, prefix + "case ",
					suffix, maxLineLength);
			}
			else {
				appendNewLine(
					sb, switchCaseJavaTerms, indent, prefix + "case ", suffix,
					maxLineLength);
			}

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
	private final List<List<JavaTerm>> _switchCaseJavaTermsList =
		new ArrayList<>();

}