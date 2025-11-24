/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class TestClassDBConnectionCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/")) {

			return;
		}

		String className = getName(detailAST);

		if (!className.matches(".*Test(Case)?")) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		List<DetailAST> variableDefDetailASTs = getAllChildTokens(
			objBlockDetailAST, true, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefDetailAST : variableDefDetailASTs) {
			String variableName = getName(variableDefDetailAST);

			String variableTypeName = getVariableTypeName(
				variableDefDetailAST, variableName, false);

			if (!variableTypeName.equals("Connection")) {
				continue;
			}

			parentDetailAST = variableDefDetailAST.getParent();

			if (!equals(objBlockDetailAST, parentDetailAST)) {
				_checkMissingTryWithResourcesStatement(
					variableDefDetailAST, variableName);

				continue;
			}

			DetailAST modifiersDetailAST = variableDefDetailAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (!modifiersDetailAST.branchContains(
					TokenTypes.LITERAL_PRIVATE) ||
				!modifiersDetailAST.branchContains(TokenTypes.LITERAL_STATIC)) {

				continue;
			}

			List<DetailAST> variableCallerDetailASTs =
				getVariableCallerDetailASTs(variableDefDetailAST);

			_checkConnectionInSetupAndTearDown(
				variableCallerDetailASTs, variableDefDetailAST, variableName);
			_checkIncorrectCloseCall(variableCallerDetailASTs, variableName);
		}

		List<DetailAST> methodCallDetailASTs = getMethodCalls(
			detailAST, "DataAccess", "getConnection");

		for (DetailAST methodCallDetailAST : methodCallDetailASTs) {
			parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if ((parentDetailAST == null) ||
				(parentDetailAST.getType() != TokenTypes.ELIST)) {

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if ((parentDetailAST == null) ||
				((parentDetailAST.getType() != TokenTypes.LITERAL_NEW) &&
				 (parentDetailAST.getType() != TokenTypes.METHOD_CALL))) {

				continue;
			}

			log(methodCallDetailAST, _MSG_INCORRECT_PARAMETER);
		}
	}

	private void _checkConnectionInSetupAndTearDown(
		List<DetailAST> detailASTs, DetailAST variableDefDetailAST,
		String variableName) {

		boolean assignedInSetUp = false;
		boolean assignedInSetUpClass = false;
		boolean closedInTearDown = false;
		boolean closedInTearDownClass = false;

		for (DetailAST detailAST : detailASTs) {
			if (detailAST.getPreviousSibling() != null) {
				continue;
			}

			DetailAST parentDetailAST = getParentWithTokenType(
				detailAST, TokenTypes.METHOD_DEF);

			if (parentDetailAST == null) {
				continue;
			}

			String methodName = getName(parentDetailAST);
			parentDetailAST = detailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.ASSIGN) {
				if (!_isAssignedByDataAccessGetConnectionCall(detailAST)) {
					log(detailAST, _MSG_INCORRECT_CONNECTION_SET, variableName);

					continue;
				}

				if (methodName.equals("setUp")) {
					assignedInSetUp = true;
				}
				else if (methodName.equals("setUpClass")) {
					assignedInSetUpClass = true;
				}
				else {
					log(detailAST, _MSG_INCORRECT_CONNECTION_SET, variableName);
				}
			}
			else if (parentDetailAST.getType() == TokenTypes.EXPR) {
				if (!_isClosedByDataAccessCleanUpCall(parentDetailAST)) {
					continue;
				}

				if (methodName.equals("tearDown")) {
					closedInTearDown = true;
				}
				else if (methodName.equals("tearDownClass")) {
					closedInTearDownClass = true;
				}
				else {
					log(
						detailAST, _MSG_INCORRECT_CONNECTION_CLOSE,
						variableName);
				}
			}
		}

		if (assignedInSetUp && !closedInTearDown) {
			log(
				variableDefDetailAST, _MSG_MISSING_CLOSE_CALL, variableName,
				"tearDown");
		}

		if (assignedInSetUpClass && !closedInTearDownClass) {
			log(
				variableDefDetailAST, _MSG_MISSING_CLOSE_CALL, variableName,
				"tearDownClass");
		}
	}

	private void _checkIncorrectCloseCall(
		List<DetailAST> detailASTs, String variableName) {

		for (DetailAST detailAST : detailASTs) {
			DetailAST parentDetailAST = detailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.METHOD_CALL) {
				continue;
			}

			String methodName = getMethodName(parentDetailAST);

			if (!methodName.equals("close")) {
				continue;
			}

			log(
				detailAST, _MSG_INCORRECT_CLOSE_CALL, variableName,
				variableName);
		}
	}

	private void _checkMissingTryWithResourcesStatement(
		DetailAST variableDefDetailAST, String variableName) {

		DetailAST firstChildDetailAST = variableDefDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (firstChildDetailAST == null) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return;
		}

		FullIdent fullIdent = FullIdent.createFullIdentBelow(
			firstChildDetailAST);

		if (!StringUtil.equals(
				fullIdent.getText(), "DataAccess.getConnection")) {

			return;
		}

		log(variableDefDetailAST, _MSG_USE_TRY_WITH_RESOURCES, variableName);
	}

	private boolean _isAssignedByDataAccessGetConnectionCall(
		DetailAST detailAST) {

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		FullIdent fullIdent = FullIdent.createFullIdentBelow(
			nextSiblingDetailAST);

		return StringUtil.endsWith(
			fullIdent.getText(), "DataAccess.getConnection");
	}

	private boolean _isClosedByDataAccessCleanUpCall(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST == null) ||
			(parentDetailAST.getType() != TokenTypes.ELIST)) {

			return false;
		}

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST == null) ||
			(parentDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		FullIdent fullIdent = FullIdent.createFullIdentBelow(parentDetailAST);

		return StringUtil.equals(fullIdent.getText(), "DataAccess.cleanUp");
	}

	private static final String _MSG_INCORRECT_CLOSE_CALL =
		"close.call.incorrect";

	private static final String _MSG_INCORRECT_CONNECTION_CLOSE =
		"connection.close.incorrect";

	private static final String _MSG_INCORRECT_CONNECTION_SET =
		"connection.set.incorrect";

	private static final String _MSG_INCORRECT_PARAMETER =
		"parameter.incorrect";

	private static final String _MSG_MISSING_CLOSE_CALL = "close.call.missing";

	private static final String _MSG_USE_TRY_WITH_RESOURCES =
		"try.with.resources.use";

}