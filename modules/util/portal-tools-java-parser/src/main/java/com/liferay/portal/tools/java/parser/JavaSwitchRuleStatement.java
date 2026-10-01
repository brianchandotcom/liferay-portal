/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class JavaSwitchRuleStatement extends BaseJavaTerm {

	public void addSwitchRuleJavaTerm(JavaTerm switchRuleJavaTerm) {
		_switchRuleJavaTerms.add(switchRuleJavaTerm);
	}

	public void setDefault(boolean isDefault) {
		_isDefault = isDefault;
	}

	public void setLambdaActionJavaExpression(
		JavaExpression lambdaActionJavaExpression) {

		_lambdaActionJavaExpression = lambdaActionJavaExpression;
	}

	public void setLambdaActionJavaTerm(JavaTerm lambdaActionJavaTerm) {
		_lambdaActionJavaTerm = lambdaActionJavaTerm;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		if (_lambdaActionJavaExpression != null) {
			suffix = "";
		}

		suffix = " -> " + StringUtil.trimLeading(suffix);

		if (_isDefault) {
			appendNewLine(
				sb, _switchRuleJavaTerms, indent, prefix + "default", suffix,
				maxLineLength);
		}
		else {
			appendNewLine(
				sb, _switchRuleJavaTerms, indent, prefix + "case ", suffix,
				maxLineLength);
		}

		if (_lambdaActionJavaExpression != null) {
			sb.append(toSingleLineString(_lambdaActionJavaExpression, indent));
			sb.append(";");
		}
		else if (_lambdaActionJavaTerm != null) {
			sb.append(_lambdaActionJavaTerm.toString());
			sb.append(";");
		}

		return sb.toString();
	}

	private boolean _isDefault;
	private JavaExpression _lambdaActionJavaExpression;
	private JavaTerm _lambdaActionJavaTerm;
	private final List<JavaTerm> _switchRuleJavaTerms = new ArrayList<>();

}