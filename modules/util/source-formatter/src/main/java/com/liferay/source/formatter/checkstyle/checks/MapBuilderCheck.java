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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class MapBuilderCheck extends ChainedMethodCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.ASSIGN, TokenTypes.INSTANCE_INIT, TokenTypes.METHOD_CALL
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (isExcludedPath(_RUN_OUTSIDE_PORTAL_EXCLUDES)) {
			return;
		}

		if (detailAST.getType() == TokenTypes.INSTANCE_INIT) {
			_checkAnonymousClass(detailAST);

			return;
		}

		if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			_checkMapBuilder(detailAST);

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

		_checkNewInstance(
			detailAST, variableName, parentDetailAST, nextSiblingDetailAST);
	}

	private void _checkAnonymousClass(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
			return;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST identDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST == null) {
			return;
		}

		String className = identDetailAST.getText();

		List<String> mapTypeNames = getAttributeValues(_MAP_TYPE_NAMES_KEY);

		if (!mapTypeNames.contains(className)) {
			return;
		}

		List<DetailAST> loopDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.DO_WHILE, TokenTypes.LITERAL_FOR,
			TokenTypes.LITERAL_WHILE);

		if (!loopDetailASTList.isEmpty()) {
			return;
		}

		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.SLIST) {
				continue;
			}

			DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
				!Objects.equals(firstChildDetailAST.getText(), "put")) {

				return;
			}
		}

		log(
			detailAST, _MSG_USE_MAP_BUILDER_INSTEAD, className + "Builder",
			className);
	}

	private void _checkInline(
		DetailAST parentDetailAST, List<DetailAST> methodVariableDetailASTList,
		String className) {

		List<String> followingVariableNames = new ArrayList<>();

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				break;
			}

			followingVariableNames.addAll(
				_getVariableNames(nextSiblingDetailAST));

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}

		List<String> inBetweenVariableNames = new ArrayList<>();

		DetailAST previousSiblingDetailAST =
			parentDetailAST.getPreviousSibling();

		while (true) {
			if (previousSiblingDetailAST == null) {
				return;
			}

			if (previousSiblingDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
				followingVariableNames.addAll(
					_getVariableNames(previousSiblingDetailAST));

				inBetweenVariableNames.addAll(
					_getVariableNames(previousSiblingDetailAST, "get.*"));

				previousSiblingDetailAST =
					previousSiblingDetailAST.getPreviousSibling();

				continue;
			}

			DetailAST identDetailAST = previousSiblingDetailAST.findFirstToken(
				TokenTypes.IDENT);

			String name = identDetailAST.getText();

			DetailAST matchingMethodVariableDetailAST = _getExprDetailAST(
				methodVariableDetailASTList, name);

			if (!followingVariableNames.contains(name) &&
				(matchingMethodVariableDetailAST != null) &&
				!_referencesNonfinalVariable(previousSiblingDetailAST)) {

				List<String> variableNames = _getVariableNames(
					previousSiblingDetailAST);

				boolean contains = false;

				for (String variableName : variableNames) {
					if (inBetweenVariableNames.contains(variableName)) {
						contains = true;

						break;
					}
				}

				if (!contains) {
					log(
						identDetailAST, _MSG_INLINE_MAP_BUILDER, name,
						identDetailAST.getLineNo(), className + ".put",
						parentDetailAST.getLineNo());
				}
			}

			followingVariableNames.addAll(
				_getVariableNames(previousSiblingDetailAST));

			inBetweenVariableNames.addAll(
				_getVariableNames(previousSiblingDetailAST, "get.*"));

			previousSiblingDetailAST =
				previousSiblingDetailAST.getPreviousSibling();
		}
	}

	private void _checkMapBuilder(DetailAST methodCallDetailAST) {
		DetailAST firstChildDetailAST = methodCallDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

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

		List<DetailAST> methodVariableDetailASTList =
			_getMethodVariableDetailASTList(methodCallDetailAST);

		_checkNullValues(methodVariableDetailASTList, className);

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while ((parentDetailAST.getType() == TokenTypes.DOT) ||
			   (parentDetailAST.getType() == TokenTypes.EXPR) ||
			   (parentDetailAST.getType() == TokenTypes.METHOD_CALL)) {

			parentDetailAST = parentDetailAST.getParent();
		}

		if (parentDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
			_checkInline(
				parentDetailAST, methodVariableDetailASTList, className);
		}

		if (parentDetailAST.getType() != TokenTypes.ASSIGN) {
			return;
		}

		DetailAST assignDetailAST = parentDetailAST;

		parentDetailAST = assignDetailAST.getParent();

		DetailAST grandParentDetailAST = parentDetailAST.getParent();

		if (grandParentDetailAST.getType() == TokenTypes.OBJBLOCK) {
			DetailAST greatGrandParentDetailAST =
				grandParentDetailAST.getParent();

			if (greatGrandParentDetailAST.getType() == TokenTypes.CLASS_DEF) {
				return;
			}
		}

		_checkInline(parentDetailAST, methodVariableDetailASTList, className);

		firstChildDetailAST = assignDetailAST.getFirstChild();

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

		List<String> variableNames = _getVariableNames(
			parentDetailAST, "get.*");

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
					detailAST, _MSG_USE_MAP_BUILDER,
					newInstanceTypeName + "Builder", detailAST.getLineNo(),
					fullIdent.getLineNo());

				return;
			}

			if (containsVariableName(nextSiblingDetailAST, variableName)) {
				return;
			}
		}
	}

	private void _checkNullValues(
		List<DetailAST> methodVariableDetailASTList, String className) {

		for (DetailAST methodVariableDetailAST : methodVariableDetailASTList) {
			if (methodVariableDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			DetailAST firstChildDetailAST =
				methodVariableDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NULL) {
				log(firstChildDetailAST, _MSG_CAST_NULL_VALUE, className);
			}
		}
	}

	private DetailAST _getExprDetailAST(
		List<DetailAST> exprDetailASTList, String name) {

		DetailAST exprDetailAST = null;

		for (DetailAST curExprDetailAST : exprDetailASTList) {
			List<String> variableNames = _getVariableNames(curExprDetailAST);

			if (variableNames.contains(name)) {
				if (exprDetailAST != null) {
					return null;
				}

				exprDetailAST = curExprDetailAST;
			}
		}

		return exprDetailAST;
	}

	private List<DetailAST> _getMethodVariableDetailASTList(
		DetailAST methodCallDetailAST) {

		List<DetailAST> exprDetailASTList = new ArrayList<>();

		while (true) {
			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			DetailAST childDetailAST = elistDetailAST.getFirstChild();

			while (true) {
				if (childDetailAST == null) {
					break;
				}

				if (childDetailAST.getType() != TokenTypes.COMMA) {
					exprDetailASTList.add(childDetailAST);
				}

				childDetailAST = childDetailAST.getNextSibling();
			}

			DetailAST parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				return exprDetailASTList;
			}

			methodCallDetailAST = parentDetailAST.getParent();
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
		return _getVariableNames(detailAST, null);
	}

	private List<String> _getVariableNames(
		DetailAST detailAST, String excludeRegex) {

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
				(parentDetailAST.getType() == TokenTypes.TYPECAST) ||
				ArrayUtil.contains(
					ARITHMETIC_OPERATOR_TOKEN_TYPES,
					parentDetailAST.getType()) ||
				ArrayUtil.contains(
					RELATIONAL_OPERATOR_TOKEN_TYPES,
					parentDetailAST.getType())) {

				variableNames.add(variableName);
			}
			else if (parentDetailAST.getType() == TokenTypes.DOT) {
				DetailAST nextSiblingDetailAST =
					identDetailAST.getNextSibling();

				if (nextSiblingDetailAST != null) {
					if ((nextSiblingDetailAST.getType() != TokenTypes.IDENT) ||
						(excludeRegex == null)) {

						variableNames.add(variableName);
					}
					else {
						String s = nextSiblingDetailAST.getText();

						if (!s.matches(excludeRegex)) {
							variableNames.add(variableName);
						}
					}
				}
			}
		}

		return variableNames;
	}

	private boolean _referencesNonfinalVariable(DetailAST detailAST) {
		List<String> variableNames = _getVariableNames(detailAST);

		DetailAST previousDetailAST = detailAST.getPreviousSibling();
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			if (previousDetailAST != null) {
				if (previousDetailAST.getType() == TokenTypes.EXPR) {
					DetailAST firstChildDetailAST =
						previousDetailAST.getFirstChild();

					if (firstChildDetailAST.getType() == TokenTypes.ASSIGN) {
						DetailAST identDetailAST =
							firstChildDetailAST.findFirstToken(
								TokenTypes.IDENT);

						if (variableNames.contains(identDetailAST.getText())) {
							return true;
						}
					}
				}

				previousDetailAST = previousDetailAST.getPreviousSibling();

				continue;
			}

			if (parentDetailAST == null) {
				return false;
			}

			previousDetailAST = parentDetailAST;

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private static final String _MAP_TYPE_NAMES_KEY = "mapTypeNames";

	private static final String _MSG_CAST_NULL_VALUE = "null.value.cast";

	private static final String _MSG_INCLUDE_MAP_BUILDER =
		"map.builder.include";

	private static final String _MSG_INLINE_MAP_BUILDER = "map.builder.inline";

	private static final String _MSG_USE_MAP_BUILDER = "map.builder.use";

	private static final String _MSG_USE_MAP_BUILDER_INSTEAD =
		"map.builder.use.instead";

	private static final String _RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

}