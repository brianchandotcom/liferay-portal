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
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Qi Zhang
 */
public class UsePassedInVarCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> parameterDefDetailASTs = getParameterDefs(detailAST);

		if (ListUtil.isEmpty(parameterDefDetailASTs)) {
			return;
		}

		List<DetailAST> assignDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.ASSIGN);

		for (DetailAST assignDetailAST : assignDetailASTs) {
			DetailAST parentDetailAST = assignDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			DetailAST firstDetailAST = assignDetailAST.getFirstChild();
			DetailAST lastDetailAST = assignDetailAST.getLastChild();

			if ((firstDetailAST.getType() != TokenTypes.IDENT) ||
				(lastDetailAST.getType() != TokenTypes.IDENT)) {

				continue;
			}

			String typeName = getTypeName(
				getVariableDefinitionDetailAST(
					firstDetailAST, firstDetailAST.getText()),
				true);

			for (DetailAST parameterDefDetailAST : parameterDefDetailASTs) {
				String variableName = lastDetailAST.getText();
				DetailAST parameterDetailAST =
					parameterDefDetailAST.findFirstToken(TokenTypes.IDENT);

				if (!StringUtil.equals(
						variableName, parameterDetailAST.getText())) {

					continue;
				}

				String parameterTypeName = getTypeName(
					parameterDefDetailAST, true);

				if (!StringUtil.equals(typeName, parameterTypeName)) {
					continue;
				}

				_checkUsePassedInParameter(
					parentDetailAST, firstDetailAST.getText(), variableName);
			}
		}
	}

	private boolean _checkLocationMethod(
		DetailAST detailAST, DetailAST identDetailAST, String name,
		String defineVariableName) {

		int tokenType = detailAST.getType();

		if (tokenType != TokenTypes.EXPR) {
			return false;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		tokenType = parentDetailAST.getType();

		if (tokenType != TokenTypes.ELIST) {
			return false;
		}

		parentDetailAST = parentDetailAST.getParent();

		tokenType = parentDetailAST.getType();

		if (tokenType == TokenTypes.METHOD_CALL) {
			String methodName = getMethodName(parentDetailAST);

			if (methodName.startsWith("get") || methodName.startsWith("_get")) {
				if (StringUtil.equals(name, defineVariableName)) {
					log(identDetailAST, _MSG_USE_PASSED_IN_VAR, name);
				}

				return false;
			}

			return true;
		}

		return false;
	}

	private void _checkUsePassedInParameter(
		DetailAST detailAST, String defineVariableName,
		String parameterVariableName) {

		DetailAST nextDetailAST = detailAST.getNextSibling();

		while (nextDetailAST != null) {
			List<DetailAST> identDetailASTs = getAllChildTokens(
				nextDetailAST, true, TokenTypes.IDENT);

			for (DetailAST identDetailAST : identDetailASTs) {
				String name = identDetailAST.getText();

				if (!StringUtil.equals(name, defineVariableName) &&
					!StringUtil.equals(name, parameterVariableName)) {

					continue;
				}

				DetailAST parentDetailAST = identDetailAST.getParent();

				if (_checkLocationMethod(
						parentDetailAST, identDetailAST, name,
						defineVariableName)) {

					return;
				}

				int tokenType = parentDetailAST.getType();

				if ((tokenType == TokenTypes.ASSIGN) &&
					(identDetailAST.getNextSibling() != null)) {

					return;
				}

				if ((tokenType == TokenTypes.DOT) &&
					(identDetailAST.getNextSibling() != null)) {

					DetailAST dotParentDetailAST = parentDetailAST.getParent();

					if (dotParentDetailAST.getType() !=
							TokenTypes.METHOD_CALL) {

						continue;
					}

					DetailAST methodNameDetailAST =
						identDetailAST.getNextSibling();

					String methodName = methodNameDetailAST.getText();

					if (!methodName.startsWith("get")) {
						return;
					}

					if (StringUtil.equals(name, defineVariableName)) {
						log(identDetailAST, _MSG_USE_PASSED_IN_VAR, name);
					}
				}
			}

			nextDetailAST = nextDetailAST.getNextSibling();
		}
	}

	private static final String _MSG_USE_PASSED_IN_VAR = "use.passed.in.var";

}