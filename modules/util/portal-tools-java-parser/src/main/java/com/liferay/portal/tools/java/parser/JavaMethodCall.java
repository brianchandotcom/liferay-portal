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
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaMethodCall extends BaseJavaExpression {

	public JavaMethodCall(String methodName) {
		_methodName = new JavaSimpleValue(methodName);
	}

	public int getChainSize() {
		JavaExpression chainedJavaExpression = getChainedJavaExpression();

		if (!(chainedJavaExpression instanceof JavaMethodCall)) {
			return 0;
		}

		JavaMethodCall javaMethodCall = (JavaMethodCall)chainedJavaExpression;

		return javaMethodCall.getChainSize() + 1;
	}

	public List<JavaExpression> getParameterValueJavaExpressions() {
		return _parameterValueJavaExpressions;
	}

	public void setGenericJavaTypes(List<JavaType> genericJavaTypes) {
		_genericJavaTypes = genericJavaTypes;
	}

	public void setInsideConstructorCall(boolean insideConstructorCall) {
		_insideConstructorCall = insideConstructorCall;
	}

	public void setMethodCallWithinClass(boolean methodCallWithinClass) {
		_methodCallWithinClass = methodCallWithinClass;
	}

	public void setParameterValueJavaExpressions(
		List<JavaExpression> parameterValueJavaExpressions) {

		_parameterValueJavaExpressions = parameterValueJavaExpressions;
	}

	public void setStatementCondition(boolean statementCondition) {
		_statementCondition = statementCondition;
	}

	public void setUseChainStyle(boolean useChainStyle) {
		_useChainStyle = useChainStyle;

		JavaExpression chainedJavaExpression = getChainedJavaExpression();

		if (chainedJavaExpression instanceof JavaMethodCall) {
			JavaMethodCall javaMethodCall =
				(JavaMethodCall)chainedJavaExpression;

			javaMethodCall.setUseChainStyle(useChainStyle);
		}
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		String originalIndent = indent;

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_genericJavaTypes != null) {
			append(
				sb, _genericJavaTypes, indent, prefix + "<", ">",
				maxLineLength);

			prefix = StringPool.BLANK;
		}

		if (_parameterValueJavaExpressions.isEmpty()) {
			if (_isUseChainStyle() && (getChainedJavaExpression() != null)) {
				append(
					sb, _methodName, indent, prefix, "(", maxLineLength, false);

				sb.append("\n");
				sb.append(originalIndent);
				sb.append(")");
				sb.append(suffix);
			}
			else {
				append(
					sb, _methodName, indent, prefix, "()" + suffix,
					maxLineLength, false);
			}
		}
		else {
			indent = append(
				sb, _methodName, indent, prefix, "(", maxLineLength, false);

			if (_isUseChainStyle()) {
				appendNewLine(
					sb, _parameterValueJavaExpressions, indent, maxLineLength);

				sb.append("\n");
				sb.append(originalIndent);
				sb.append(")");
				sb.append(suffix);
			}
			else if (forceLineBreak) {
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

		return sb.toString();
	}

	private boolean _isUseChainStyle() {
		if (_statementCondition) {
			return false;
		}

		if (_useChainStyle) {
			return _useChainStyle;
		}

		int chainSize = getChainSize();

		if (chainSize == 0) {
			return false;
		}

		if ((chainSize == 1) &&
			(_insideConstructorCall ||
			 (_parameterValueJavaExpressions.isEmpty() &&
			  _methodCallWithinClass))) {

			return false;
		}

		setUseChainStyle(true);

		return true;
	}

	private List<JavaType> _genericJavaTypes;
	private boolean _insideConstructorCall;
	private boolean _methodCallWithinClass;
	private final JavaSimpleValue _methodName;
	private List<JavaExpression> _parameterValueJavaExpressions;
	private boolean _statementCondition;
	private boolean _useChainStyle;

}