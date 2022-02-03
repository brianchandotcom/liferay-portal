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

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class OSGiResourceBuilderCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		if (annotationDetailAST == null) {
			return;
		}

		List<String> importNames = null;

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			String methodName = getMethodName(methodCallDetailAST);

			if (!methodName.equals("builder")) {
				continue;
			}

			String classOrVariableName = getClassOrVariableName(
				methodCallDetailAST);

			if ((classOrVariableName == null) ||
				!classOrVariableName.matches("[A-Z].*Resource")) {

				return;
			}

			if (importNames == null) {
				importNames = getImportNames(methodCallDetailAST);
			}

			String fullyQualifiedClassName = classOrVariableName;

			for (String importName : importNames) {
				if (importName.endsWith("." + classOrVariableName)) {
					fullyQualifiedClassName = importName;

					break;
				}
			}

			if (!fullyQualifiedClassName.contains(".client.")) {
				log(methodCallDetailAST, _MSG_AVOID_METHOD_CALL);
			}
		}
	}

	private static final String _MSG_AVOID_METHOD_CALL = "method.call.avoid";

}