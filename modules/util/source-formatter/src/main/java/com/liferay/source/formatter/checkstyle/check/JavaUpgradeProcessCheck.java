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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qi Zhang
 */
public class JavaUpgradeProcessCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) || !_isUpgradeProcess(detailAST)) {
			return;
		}

		boolean continueFlag = true;
		List<DetailAST> ifDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_IF);

		for (DetailAST curDetailAST : ifDetailASTs) {
			if (!_checkUnnecessaryJudgement(curDetailAST)) {
				continueFlag = false;
			}
		}

		if (!continueFlag) {
			return;
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.contains(
				"liferay-portal/portal-impl/src/com/liferay/portal/upgrade" +
					"/v6_") ||
			absolutePath.contains(
				"liferay-portal/portal-impl/src/com/liferay/portal/upgrade" +
					"/v7_0_")) {

			return;
		}

		List<DetailAST> methodDefDetailASTs = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_DEF);

		DetailAST methodDefDetailAST = null;

		for (DetailAST curDetailAST : methodDefDetailASTs) {
			DetailAST iDentDetailAST = curDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if (StringUtil.equals(iDentDetailAST.getText(), "doUpgrade")) {
				methodDefDetailAST = curDetailAST;

				break;
			}
		}

		if ((methodDefDetailAST == null) ||
			((methodDefDetailASTs.size() == 1) &&
			 !_checkIfDeleted(detailAST, methodDefDetailAST))) {

			return;
		}

		DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		if (sListDetailAST.getChildCount() == 1) {
			return;
		}

		DetailAST firstChildDetailAST = sListDetailAST.getFirstChild();

		DetailAST nextDetailAST = firstChildDetailAST;

		if (firstChildDetailAST.getType() == TokenTypes.EXPR) {
			nextDetailAST = _checkMethod(firstChildDetailAST, false);
		}

		if (nextDetailAST == null) {
			return;
		}

		while (nextDetailAST != null) {
			firstChildDetailAST = nextDetailAST;
			nextDetailAST = nextDetailAST.getNextSibling();
		}

		int tokenType = firstChildDetailAST.getType();

		while ((tokenType == TokenTypes.SEMI) ||
			   (tokenType == TokenTypes.RCURLY)) {

			firstChildDetailAST = firstChildDetailAST.getPreviousSibling();

			tokenType = firstChildDetailAST.getType();
		}

		if (tokenType == TokenTypes.EXPR) {
			_checkMethod(firstChildDetailAST, true);
		}
	}

	private boolean _isUpgradeProcess(DetailAST detailAST) {
		DetailAST extendsClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.EXTENDS_CLAUSE);

		if (extendsClauseDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = extendsClauseDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return false;
		}

		String extendsClassName = getName(extendsClauseDetailAST);

		if (!extendsClassName.equals("UpgradeProcess")) {
			return false;
		}

		return true;
	}

	private boolean _checkIfDeleted(
		DetailAST detailAST, DetailAST methodDefDetailAST) {

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		DetailAST childDetailAST = objBlockDetailAST.getFirstChild();

		int objCount = 0;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.LCURLY) &&
				(tokenType != TokenTypes.RCURLY)) {

				objCount++;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		if (objCount != 1) {
			return true;
		}

		DetailAST sListDetailAST = methodDefDetailAST.findFirstToken(
			TokenTypes.SLIST);

		childDetailAST = sListDetailAST.getFirstChild();

		int methodCount = 0;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.SEMI) &&
				(tokenType != TokenTypes.RCURLY)) {

				return true;
			}

			if ((tokenType == TokenTypes.SEMI) ||
				(tokenType == TokenTypes.RCURLY)) {

				childDetailAST = childDetailAST.getNextSibling();

				continue;
			}

			methodCount++;

			DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return true;
			}

			String methodName = getMethodName(firstChildDetailAST);

			if (!StringUtil.equals(methodName, "alterColumnName") &&
				!StringUtil.equals(methodName, "alterColumnType") &&
				!StringUtil.equals(methodName, "alterTableAddColumn") &&
				!StringUtil.equals(methodName, "alterTableDropColumn")) {

				return true;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		if (methodCount != 0) {
			log(detailAST, _MSG_DELETE_CLASS);

			return false;
		}

		return true;
	}

	private DetailAST _checkMethod(DetailAST detailAST, boolean lastLine) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return detailAST;
		}

		String methodName = getMethodName(firstChildDetailAST);

		if ((!StringUtil.equals(methodName, "alterColumnName") &&
			 !StringUtil.equals(methodName, "alterColumnType") &&
			 !StringUtil.equals(methodName, "alterTableAddColumn") &&
			 !StringUtil.equals(methodName, "alterTableDropColumn")) ||
			!_checkParameters(firstChildDetailAST)) {

			return detailAST;
		}

		int startLine = getStartLineNumber(detailAST);
		int endLine = getEndLineNumber(detailAST);

		DetailAST nextDetailAST = _getDetailAST(detailAST, lastLine);

		boolean alertMessage = false;

		while (nextDetailAST != null) {
			int tokenType = nextDetailAST.getType();

			if ((tokenType == TokenTypes.SEMI) ||
				(tokenType == TokenTypes.RCURLY)) {

				nextDetailAST = _getDetailAST(nextDetailAST, lastLine);

				continue;
			}

			if (tokenType != TokenTypes.EXPR) {
				alertMessage = true;

				break;
			}

			DetailAST childDetailAST = nextDetailAST.getFirstChild();

			if (childDetailAST.getType() != TokenTypes.METHOD_CALL) {
				alertMessage = true;

				break;
			}

			String currentMethodName = getMethodName(childDetailAST);

			if ((!StringUtil.equals(currentMethodName, "alterColumnName") &&
				 !StringUtil.equals(currentMethodName, "alterColumnType") &&
				 !StringUtil.equals(currentMethodName, "alterTableAddColumn") &&
				 !StringUtil.equals(
					 currentMethodName, "alterTableDropColumn")) ||
				!_checkParameters(childDetailAST)) {

				alertMessage = true;

				break;
			}

			if (lastLine) {
				startLine = getStartLineNumber(nextDetailAST);
			}
			else {
				endLine = getEndLineNumber(nextDetailAST);
			}

			nextDetailAST = _getDetailAST(nextDetailAST, lastLine);
		}

		String errorMessageMethod = "getPreUpgradeSteps";

		if (lastLine) {
			errorMessageMethod = "getPostUpgradeSteps";
		}

		if (alertMessage) {
			log(
				detailAST, _MSG_REPLACE_METHOD, startLine, endLine,
				errorMessageMethod);
		}

		return nextDetailAST;
	}

	private boolean _checkParameters(DetailAST detailAST) {
		List<String> parameters = _getParameterList(detailAST);

		return ListUtil.isNotEmpty(parameters);
	}

	private boolean _checkUnnecessaryJudgement(DetailAST detailAST) {
		DetailAST exprDetailAST = detailAST.findFirstToken(TokenTypes.EXPR);

		DetailAST childDetailAST = exprDetailAST.getFirstChild();

		boolean unCheck = false;

		if (childDetailAST.getType() == TokenTypes.LNOT) {
			unCheck = true;
			childDetailAST = childDetailAST.getFirstChild();
		}

		String hasMethodName = getMethodName(childDetailAST);

		if (Validator.isNull(hasMethodName)) {
			return true;
		}

		List<String> hasMethodParameters = _getParameters(
			childDetailAST, hasMethodName);

		if (ListUtil.isEmpty(hasMethodParameters)) {
			return true;
		}

		DetailAST sListDetailAST = detailAST.findFirstToken(TokenTypes.SLIST);

		if (sListDetailAST.getNextSibling() != null) {
			return true;
		}

		childDetailAST = sListDetailAST.getFirstChild();

		int count = 0;

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.SEMI) &&
				(tokenType != TokenTypes.RCURLY)) {

				exprDetailAST = childDetailAST;
				count++;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		if (count != 1) {
			return true;
		}

		childDetailAST = exprDetailAST.getFirstChild();

		String alterMethodName = getMethodName(childDetailAST);

		if (Validator.isNull(alterMethodName)) {
			return true;
		}

		List<String> alterMethodParameters = _getParameters(
			childDetailAST, alterMethodName);

		if (ListUtil.isEmpty(alterMethodParameters) ||
			!StringUtil.equals(
				hasMethodParameters.get(0), alterMethodParameters.get(0))) {

			return true;
		}

		if (StringUtil.equals(alterMethodName, "alterColumnName")) {
			String newColumnName = StringUtil.extractFirst(
				alterMethodParameters.get(2), StringPool.SPACE);
			String newColumnType = StringUtil.extractFirst(
				alterMethodParameters.get(2), StringPool.SPACE);

			if ((StringUtil.equals(hasMethodName, "hasColumn") &&
				 ((unCheck &&
				   StringUtil.equals(
					   hasMethodParameters.get(1), newColumnName)) ||
				  (!unCheck &&
				   StringUtil.equals(
					   hasMethodParameters.get(1),
					   alterMethodParameters.get(1))))) ||
				(StringUtil.equals(hasMethodName, "hasColumnType") &&
				 ((unCheck &&
				   StringUtil.equals(
					   hasMethodParameters.get(1), newColumnName) &&
				   StringUtil.equals(
					   hasMethodParameters.get(2), newColumnType)) ||
				  (!unCheck &&
				   StringUtil.equals(
					   hasMethodParameters.get(1),
					   alterMethodParameters.get(1)))))) {

				log(detailAST, _MSG_REMOVE_UNNECESSARY_METHOD);

				return false;
			}
		}
		else {
			if (StringUtil.equals(
					hasMethodParameters.get(1), alterMethodParameters.get(1))) {

				log(detailAST, _MSG_REMOVE_UNNECESSARY_METHOD);

				return false;
			}
		}

		return true;
	}

	private DetailAST _getDetailAST(DetailAST detailAST, boolean lastLine) {
		if (lastLine) {
			return detailAST.getPreviousSibling();
		}

		return detailAST.getNextSibling();
	}

	private List<String> _getParameterList(DetailAST detailAST) {
		DetailAST eListDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		DetailAST firstChildDetailAST = eListDetailAST.getFirstChild();

		List<String> parameters = new ArrayList<>();

		while (firstChildDetailAST != null) {
			int tokenType = firstChildDetailAST.getType();

			if ((tokenType != TokenTypes.EXPR) &&
				(tokenType != TokenTypes.COMMA)) {

				return null;
			}

			if (tokenType == TokenTypes.EXPR) {
				DetailAST childDetailAST = firstChildDetailAST.getFirstChild();

				if (childDetailAST.getType() != TokenTypes.STRING_LITERAL) {
					return null;
				}

				parameters.add(childDetailAST.getText());
			}

			firstChildDetailAST = firstChildDetailAST.getNextSibling();
		}

		return parameters;
	}

	private List<String> _getParameters(
		DetailAST detailAST, String methodName) {

		if (!(StringUtil.equals(methodName, "hasColumn") ||
			  StringUtil.equals(methodName, "hasColumnType") ||
			  StringUtil.equals(methodName, "alterColumnName") ||
			  StringUtil.equals(methodName, "alterColumnType") ||
			  StringUtil.equals(methodName, "alterTableAddColumn") ||
			  StringUtil.equals(methodName, "alterTableDropColumn"))) {

			return null;
		}

		DetailAST eListDetailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		DetailAST childDetailAST = eListDetailAST.getFirstChild();

		List<String> alterMethodParameters = new ArrayList<>();

		while (childDetailAST != null) {
			int tokenType = childDetailAST.getType();

			if ((tokenType != TokenTypes.COMMA) &&
				(tokenType != TokenTypes.EXPR)) {

				return null;
			}

			if (tokenType == TokenTypes.EXPR) {
				DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() !=
						TokenTypes.STRING_LITERAL) {

					return null;
				}

				alterMethodParameters.add(firstChildDetailAST.getText());
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		return alterMethodParameters;
	}

	private static final String _MSG_DELETE_CLASS = "delete.class";

	private static final String _MSG_REMOVE_UNNECESSARY_METHOD =
		"remove.unnecessary.method";

	private static final String _MSG_REPLACE_METHOD = "replace.method";

}