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

import com.liferay.portal.kernel.util.ListUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Kevin Lee
 */
public class ServiceComponentRuntimeCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!(absolutePath.contains("/test/") ||
			  absolutePath.contains("/testIntegration/") ||
			  absolutePath.endsWith("Test.java"))) {

			return;
		}

		List<String> importNames = getImportNames(detailAST);

		if (!importNames.contains(
				"org.osgi.service.component.runtime.ServiceComponentRuntime")) {

			return;
		}

		String serviceComponentRuntimeVariableName =
			_getServiceComponentRuntimeVariableName(detailAST);

		if (serviceComponentRuntimeVariableName == null) {
			return;
		}

		for (DetailAST methodDefDetailAST :
				getAllChildTokens(detailAST, true, TokenTypes.METHOD_DEF)) {

			for (DetailAST methodCallDetailAST :
					getMethodCalls(
						methodDefDetailAST, serviceComponentRuntimeVariableName,
						_METHOD_NAMES)) {

				DetailAST assignDetailAST = getParentWithTokenType(
					methodCallDetailAST, TokenTypes.ASSIGN);

				if (assignDetailAST == null) {
					log(
						methodCallDetailAST, _MSG_PROMISE_GET_VALUE,
						getMethodName(methodCallDetailAST));

					continue;
				}

				String promiseVariableName = getName(assignDetailAST);

				if (promiseVariableName == null) {
					promiseVariableName = getName(assignDetailAST.getParent());
				}

				DetailAST slistDetailAST = methodDefDetailAST.findFirstToken(
					TokenTypes.SLIST);

				if (ListUtil.isEmpty(
						getMethodCalls(
							slistDetailAST, promiseVariableName, "getValue"))) {

					log(
						assignDetailAST, _MSG_PROMISE_GET_VALUE,
						getMethodName(methodCallDetailAST));
				}
			}
		}
	}

	private String _getServiceComponentRuntimeVariableName(
		DetailAST classDefDetailAST) {

		DetailAST objBlockDetailAST = classDefDetailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		for (DetailAST variableDefDetailAST :
				getAllChildTokens(
					objBlockDetailAST, false, TokenTypes.VARIABLE_DEF)) {

			String variableName = getName(variableDefDetailAST);

			String variableTypeName = getVariableTypeName(
				variableDefDetailAST, variableName, false);

			if (variableTypeName.equals("ServiceComponentRuntime")) {
				return variableName;
			}
		}

		return null;
	}

	private static final String[] _METHOD_NAMES = {
		"disableComponent", "enableComponent"
	};

	private static final String _MSG_PROMISE_GET_VALUE = "promise.get.value";

}