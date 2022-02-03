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
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public abstract class BaseUnnecessaryStatementCheck extends BaseCheck {

	protected void checkUnnecessaryListVariableBeforeReturn(
		DetailAST detailAST, DetailAST semiDetailAST, String variableName,
		String messageKey) {

		String variableTypeName = getVariableTypeName(
			detailAST, variableName, false);

		if (!variableTypeName.equals("List")) {
			return;
		}

		if ((detailAST.getType() == TokenTypes.ASSIGN) &&
			!isAssignNewArrayList(detailAST.getParent())) {

			return;
		}
		else if ((detailAST.getType() == TokenTypes.VARIABLE_DEF) &&
				 !isAssignNewArrayList(detailAST)) {

			return;
		}

		DetailAST nextSiblingDetailAST = semiDetailAST.getNextSibling();

		if (nextSiblingDetailAST == null) {
			return;
		}

		while (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST =
				nextSiblingDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.DOT) {
				return;
			}

			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			String fullyQualifiedName = fullIdent.getText();

			if (!fullyQualifiedName.equals(variableName + ".add")) {
				return;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if ((nextSiblingDetailAST != null) &&
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if (nextSiblingDetailAST == null) {
				return;
			}

			if (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
				continue;
			}

			if (nextSiblingDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
				firstChildDetailAST = nextSiblingDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
					return;
				}

				firstChildDetailAST = firstChildDetailAST.getFirstChild();

				if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
					variableName.equals(firstChildDetailAST.getText())) {

					log(detailAST, messageKey, variableName);
				}
			}

			return;
		}
	}

	protected void checkUnnecessaryStatementBeforeReassign(
		DetailAST detailAST, DetailAST firstNextVariableCallerDetailAST,
		DetailAST secondNextVariableCallerDetailAST, DetailAST slistDetailAST,
		String variableName, String messageKey) {

		if (firstNextVariableCallerDetailAST.getPreviousSibling() != null) {
			return;
		}

		DetailAST parentDetailAST =
			firstNextVariableCallerDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.ASSIGN) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.EXPR) ||
			!equals(parentDetailAST.getParent(), slistDetailAST)) {

			return;
		}

		if ((secondNextVariableCallerDetailAST == null) ||
			(secondNextVariableCallerDetailAST.getLineNo() > getEndLineNumber(
				parentDetailAST))) {

			log(detailAST, messageKey, variableName);
		}
	}

	protected void checkUnnecessaryStatementBeforeReturn(
		DetailAST detailAST, DetailAST semiDetailAST, String variableName,
		String messageKey) {

		DetailAST nextSiblingDetailAST = semiDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.LITERAL_RETURN) ||
			(getHiddenBefore(nextSiblingDetailAST) != null)) {

			return;
		}

		DetailAST firstChildDetailAST = nextSiblingDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
			variableName.equals(firstChildDetailAST.getText())) {

			log(detailAST, messageKey, variableName);
		}
	}

	protected void checkUnnecessaryToString(
		DetailAST assignDetailAST, String messageKey) {

		if ((assignDetailAST == null) ||
			(assignDetailAST.getType() != TokenTypes.ASSIGN)) {

			return;
		}

		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			assignDetailAST, "toString");

		if (methodCallDetailASTList.size() != 1) {
			return;
		}

		DetailAST methodCallDetailAST = methodCallDetailASTList.get(0);

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.ASSIGN) &&
			(parentDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		String variableName = getVariableName(methodCallDetailAST);

		DetailAST typeDetailAST = getVariableTypeDetailAST(
			methodCallDetailAST, variableName);

		if (typeDetailAST == null) {
			return;
		}

		String methodName = getMethodName(methodCallDetailAST);

		if (!methodName.equals("toString") ||
			(getParameterDetailAST(methodCallDetailAST) != null)) {

			return;
		}

		parentDetailAST = typeDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(parentDetailAST, variableName);

		if (variableCallerDetailASTList.size() != 1) {
			return;
		}

		log(
			assignDetailAST, messageKey, variableName,
			getStartLineNumber(parentDetailAST),
			getStartLineNumber(assignDetailAST));
	}

}