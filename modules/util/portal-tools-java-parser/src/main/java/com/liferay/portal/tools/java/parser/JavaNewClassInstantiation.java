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
public class JavaNewClassInstantiation extends BaseJavaExpression {

	public JavaNewClassInstantiation(JavaClassCall javaClassCall) {
		_javaClassCall = javaClassCall;
	}

	public boolean hasBody() {
		return _javaClassCall.hasBody();
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_javaClassCall.hasParameterValueJavaExpressions()) {
			JavaExpression chainedJavaExpression = getChainedJavaExpression();

			if (chainedJavaExpression != null) {
				_javaClassCall.setUseChainStyle(true);

				if (chainedJavaExpression instanceof JavaMethodCall) {
					JavaMethodCall javaMethodCall =
						(JavaMethodCall)chainedJavaExpression;

					javaMethodCall.setUseChainStyle(true);
				}
			}
		}

		if (forceLineBreak) {
			appendWithLineBreak(
				sb, _javaClassCall, indent, prefix + "new ", suffix,
				maxLineLength);
		}
		else {
			append(
				sb, _javaClassCall, indent, prefix + "new ", suffix,
				maxLineLength);
		}

		return sb.toString();
	}

	private final JavaClassCall _javaClassCall;

}