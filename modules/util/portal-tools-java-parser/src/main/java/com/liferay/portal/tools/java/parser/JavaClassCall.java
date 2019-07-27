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

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaClassCall extends JavaExpression {

	public JavaClassCall(
		String className, List<JavaType> genericJavaTypes,
		List<JavaExpression> parameterValueJavaExpressions) {

		_className = new JavaSimpleValue(className);
		_genericJavaTypes = genericJavaTypes;
		_parameterValueJavaExpressions = parameterValueJavaExpressions;
	}

	public boolean hasBody() {
		return _hasBody;
	}

	public boolean hasParameterValueJavaExpressions() {
		return !_parameterValueJavaExpressions.isEmpty();
	}

	public void setEmptyBody(boolean emptyBody) {
		_emptyBody = emptyBody;
	}

	public void setHasBody(boolean hasBody) {
		_hasBody = hasBody;
	}

	public void setStatementCondition(boolean statementCondition) {
		_statementCondition = statementCondition;
	}

	public void setUseChainStyle(boolean useChainStyle) {
		_useChainStyle = useChainStyle;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		String originalIndent = indent;
		String originalSuffix = suffix;

		if (_hasBody) {
			suffix = " {";
		}

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_genericJavaTypes == null) {
			if (_parameterValueJavaExpressions.isEmpty()) {
				append(
					sb, _className, indent, prefix, "()" + suffix,
					maxLineLength);
			}
			else {
				indent = append(
					sb, _className, indent, prefix, "(", maxLineLength);
			}
		}
		else {
			indent = append(sb, _className, indent, prefix, "", maxLineLength);

			if (_parameterValueJavaExpressions.isEmpty()) {
				append(
					sb, _genericJavaTypes, indent, "<", ">()" + suffix,
					maxLineLength);
			}
			else {
				indent = append(
					sb, _genericJavaTypes, indent, "<", ">(", maxLineLength);
			}
		}

		if (!_parameterValueJavaExpressions.isEmpty()) {
			if (!_statementCondition && _useChainStyle) {
				appendNewLine(
					sb, _parameterValueJavaExpressions, indent, maxLineLength);

				sb.append("\n");

				if (prefix.startsWith("try (")) {
					sb.append("\t");
				}

				sb.append(originalIndent);
				sb.append(")");
				sb.append(suffix);
			}
			else if (forceLineBreak && !_hasBody) {
				appendNewLine(
					sb, _parameterValueJavaExpressions, indent, "",
					")" + suffix, maxLineLength);
			}
			else {
				append(
					sb, _parameterValueJavaExpressions, indent, "",
					")" + suffix, maxLineLength);
			}
		}

		if (_hasBody) {
			sb.append("\n");

			if (!_emptyBody) {
				sb.append(NESTED_CODE_BLOCK);
				sb.append("\n");
			}

			sb.append(originalIndent);

			if (prefix.startsWith("try (")) {
				sb.append("\t");
			}

			sb.append("}");
			sb.append(originalSuffix);
		}

		return sb.toString();
	}

	protected static final String NESTED_CODE_BLOCK =
		"${JAVA_CLASS_CALL_NESTED_CODE_BLOCK}";

	private final JavaSimpleValue _className;
	private boolean _emptyBody;
	private final List<JavaType> _genericJavaTypes;
	private boolean _hasBody;
	private final List<JavaExpression> _parameterValueJavaExpressions;
	private boolean _statementCondition;
	private boolean _useChainStyle;

}