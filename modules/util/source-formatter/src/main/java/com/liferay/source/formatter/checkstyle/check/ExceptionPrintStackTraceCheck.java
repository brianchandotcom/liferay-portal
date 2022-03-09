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

import java.util.List;

/**
 * @author Alan Huang
 */
public class ExceptionPrintStackTraceCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_CATCH};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/modules/sdk/") ||
			absolutePath.contains("/modules/util/") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/") ||
			absolutePath.endsWith("Jdk14LogFactoryImpl.java")) {

			return;
		}

		DetailAST parameterDefinitionDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETER_DEF);

		String exceptionVariableName = getName(parameterDefinitionDetailAST);

		String variableTypeName = getVariableTypeName(
			detailAST, exceptionVariableName, false);

		if ((variableTypeName == null) ||
			!variableTypeName.endsWith("Exception")) {

			return;
		}

		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, exceptionVariableName, "printStackTrace");

		if (methodCallDetailASTList.isEmpty()) {
			return;
		}

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST parameterDetailAST = getParameterDetailAST(
				methodCallDetailAST);

			if (parameterDetailAST != null) {
				continue;
			}

			log(methodCallDetailAST, _MSG_AVOID_METHOD_CALL);
		}
	}

	private static final String _MSG_AVOID_METHOD_CALL = "method.call.avoid";

}