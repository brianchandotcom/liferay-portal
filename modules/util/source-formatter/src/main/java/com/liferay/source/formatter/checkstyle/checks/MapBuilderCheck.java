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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MapBuilderCheck extends ChainedMethodCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (isExcludedPath(_RUN_OUTSIDE_PORTAL_EXCLUDES)) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.EXPR) &&
			(parentDetailAST.getType() != TokenTypes.VARIABLE_DEF)) {

			return;
		}

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		String variableName = getVariableName(detailAST, parentDetailAST);

		if (variableName == null) {
			return;
		}

		_checkMapBuilder(detailAST, parentDetailAST);

		_checkNewInstance(
			detailAST, variableName, parentDetailAST, nextSiblingDetailAST);
	}

	private void _checkMapBuilder(
		DetailAST assignDetailAST, DetailAST parentDetailAST) {

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		DetailAST assignValueDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			assignValueDetailAST = firstChildDetailAST.getNextSibling();
		}
		else {
			assignValueDetailAST = firstChildDetailAST.getFirstChild();
		}

		if ((assignValueDetailAST == null) ||
			(assignValueDetailAST.getType() != TokenTypes.METHOD_CALL)) {

			return;
		}

		firstChildDetailAST = assignValueDetailAST.getFirstChild();

		while (true) {
			if ((firstChildDetailAST.getType() != TokenTypes.DOT) &&
				(firstChildDetailAST.getType() != TokenTypes.METHOD_CALL)) {

				break;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String className = firstChildDetailAST.getText();

		if (!className.endsWith("Builder")) {
			return;
		}

		List<String> mapTypeNames = getAttributeValues(_MAP_TYPE_NAMES_KEY);

		if (!mapTypeNames.contains(
				className.substring(0, className.length() - 7))) {

			return;
		}

		List<String> variableNames = _getVariableNames(parentDetailAST);

		String variableName = getVariableName(assignDetailAST, parentDetailAST);

		variableNames.add(variableName);

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return;
			}

			FullIdent fullIdent = getMethodCallFullIdent(
				nextSiblingDetailAST, variableName, "put");

			if (fullIdent != null) {
				log(
					assignDetailAST, _MSG_INCLUDE_MAP_BUILDER,
					fullIdent.getText(), fullIdent.getLineNo(),
					className + ".put", assignDetailAST.getLineNo());

				return;
			}

			for (String curVariableName : variableNames) {
				if (containsVariableName(
						nextSiblingDetailAST, curVariableName)) {

					return;
				}
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
	}

	private void _checkNewInstance(
		DetailAST detailAST, String variableName, DetailAST parentDetailAST,
		DetailAST nextSiblingDetailAST) {

		String newInstanceTypeName = _getNewInstanceTypeName(
			detailAST, parentDetailAST);

		if (newInstanceTypeName == null) {
			return;
		}

		List<String> mapTypeNames = getAttributeValues(_MAP_TYPE_NAMES_KEY);

		if (!mapTypeNames.contains(newInstanceTypeName)) {
			return;
		}

		while (true) {
			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if (nextSiblingDetailAST == null) {
				return;
			}

			FullIdent fullIdent = getMethodCallFullIdent(
				nextSiblingDetailAST, variableName, "put");

			if (fullIdent != null) {
				log(
					detailAST, _MSG_USE_MAP_BUIDER,
					newInstanceTypeName + "Builder", detailAST.getLineNo(),
					fullIdent.getLineNo());

				return;
			}

			if (containsVariableName(nextSiblingDetailAST, variableName)) {
				return;
			}
		}
	}

	private String _getNewInstanceTypeName(
		DetailAST assignDetailAST, DetailAST parentDetailAST) {

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		DetailAST assignValueDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			assignValueDetailAST = firstChildDetailAST.getNextSibling();
		}
		else {
			assignValueDetailAST = firstChildDetailAST.getFirstChild();
		}

		if ((assignValueDetailAST == null) ||
			(assignValueDetailAST.getType() != TokenTypes.LITERAL_NEW)) {

			return null;
		}

		DetailAST identDetailAST = assignValueDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST == null) {
			return null;
		}

		DetailAST elistDetailAST = assignValueDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if ((elistDetailAST == null) ||
			(elistDetailAST.getFirstChild() != null)) {

			return null;
		}

		return identDetailAST.getText();
	}

	private List<String> _getVariableNames(DetailAST detailAST) {
		List<String> variableNames = new ArrayList<>();

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			String variableName = identDetailAST.getText();

			if (!variableName.matches("[a-z_].*")) {
				continue;
			}

			DetailAST parentDetailAST = identDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.ASSIGN) ||
				(parentDetailAST.getType() == TokenTypes.EXPR) ||
				(parentDetailAST.getType() == TokenTypes.TYPECAST)) {

				variableNames.add(variableName);
			}
			else if (parentDetailAST.getType() == TokenTypes.DOT) {
				DetailAST nextSiblingDetailAST =
					identDetailAST.getNextSibling();

				if (nextSiblingDetailAST != null) {
					if (nextSiblingDetailAST.getType() != TokenTypes.IDENT) {
						variableNames.add(variableName);
					}
					else {
						String s = nextSiblingDetailAST.getText();

						if (!s.startsWith("get")) {
							variableNames.add(variableName);
						}
					}
				}
			}
		}

		return variableNames;
	}

	private static final String _MAP_TYPE_NAMES_KEY = "mapTypeNames";

	private static final String _MSG_INCLUDE_MAP_BUILDER =
		"map.builder.include";

	private static final String _MSG_USE_MAP_BUIDER = "map.builder.use";

	private static final String _RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

}