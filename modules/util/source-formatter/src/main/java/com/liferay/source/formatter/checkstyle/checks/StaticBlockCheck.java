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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class StaticBlockCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.STATIC_INIT};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<String> classObjectNames = _getClassObjectNames(detailAST);

		if (classObjectNames.isEmpty()) {
			return;
		}

		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.METHOD_CALL);

		if (methodCallDetailASTList.isEmpty()) {
			return;
		}

		Map<String, List<DetailAST>> identDetailASTMap = _getIdentDetailASTMap(
			detailAST);

		Map<String, DetailAST[]> variableDefMap = _getVariableDefMap(
			detailAST, identDetailASTMap);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			_checkMethodCall(
				methodCallDetailAST, classObjectNames, identDetailASTMap,
				variableDefMap);
		}
	}

	private void _checkMethodCall(
		DetailAST methodCallDetailAST, List<String> classObjectNames,
		Map<String, List<DetailAST>> identDetailASTMap,
		Map<String, DetailAST[]> variableDefMap) {

		String variableName = DetailASTUtil.getVariableName(
			methodCallDetailAST);

		if (!classObjectNames.contains(variableName) ||
			variableName.equals("_log")) {

			return;
		}

		DetailAST topLevelDetailAST = _getTopLevelDetailAST(
			methodCallDetailAST);

		int statementEndLineNumber = DetailASTUtil.getEndLineNumber(
			topLevelDetailAST);

		List<DetailAST> variableDetailASTList = identDetailASTMap.get(
			variableName);

		DetailAST firstUseVariableDetailAST = variableDetailASTList.get(0);

		topLevelDetailAST = _getTopLevelDetailAST(firstUseVariableDetailAST);

		int statementStartLineNumber = DetailASTUtil.getStartLineNumber(
			topLevelDetailAST);

		if (!_isRequiredMethodCall(
				variableName, classObjectNames, identDetailASTMap,
				variableDefMap, statementStartLineNumber,
				statementEndLineNumber)) {

			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			FullIdent fullIdent = FullIdent.createFullIdent(dotDetailAST);

			log(
				methodCallDetailAST, _MSG_UNNEEDED_STATIC_BLOCK,
				fullIdent.getText());
		}
	}

	private List<String> _getClassObjectNames(DetailAST staticInitDetailAST) {
		List<String> staticObjectNames = new ArrayList<>();

		List<String> immutableFieldTypes = getAttributeValues(
			_IMMUTABLE_FIELD_TYPES_KEY);

		DetailAST previousSiblingDetailAST =
			staticInitDetailAST.getPreviousSibling();

		while (previousSiblingDetailAST != null) {
			DetailAST modifiersDetailAST =
				previousSiblingDetailAST.findFirstToken(TokenTypes.MODIFIERS);

			if (modifiersDetailAST == null) {
				previousSiblingDetailAST =
					previousSiblingDetailAST.getPreviousSibling();

				continue;
			}

			DetailAST nameDetailAST = previousSiblingDetailAST.findFirstToken(
				TokenTypes.IDENT);

			String name = nameDetailAST.getText();

			if (previousSiblingDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
				staticObjectNames.add(name);
			}
			else {
				String typeName = DetailASTUtil.getTypeName(
					previousSiblingDetailAST, true);

				if (!immutableFieldTypes.contains(typeName)) {
					staticObjectNames.add(name);
				}
			}

			previousSiblingDetailAST =
				previousSiblingDetailAST.getPreviousSibling();
		}

		return staticObjectNames;
	}

	private Map<String, List<DetailAST>> _getIdentDetailASTMap(
		DetailAST staticInitDetailAST) {

		Map<String, List<DetailAST>> identDetailASTMap = new HashMap<>();

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			staticInitDetailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			List<DetailAST> list = identDetailASTMap.get(
				identDetailAST.getText());

			if (list == null) {
				list = new ArrayList<>();
			}

			list.add(identDetailAST);

			identDetailASTMap.put(identDetailAST.getText(), list);
		}

		return identDetailASTMap;
	}

	private DetailAST _getTopLevelDetailAST(DetailAST detailAST) {
		DetailAST topLevelDetailAST = null;

		DetailAST parentDetailAST = detailAST;

		while (true) {
			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST.getType() == TokenTypes.STATIC_INIT) {
				return topLevelDetailAST;
			}

			if (grandParentDetailAST.getType() == TokenTypes.SLIST) {
				topLevelDetailAST = parentDetailAST;
			}

			parentDetailAST = grandParentDetailAST;
		}
	}

	private Map<String, DetailAST[]> _getVariableDefMap(
		DetailAST staticInitDetailAST,
		Map<String, List<DetailAST>> identDetailASTMap) {

		Map<String, DetailAST[]> variableDefMap = new HashMap<>();

		List<DetailAST> variableDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				staticInitDetailAST, true, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			DetailAST nameDetailAST =
				variableDefinitionDetailAST.findFirstToken(TokenTypes.IDENT);

			String name = nameDetailAST.getText();

			List<DetailAST> identDetailASTList = identDetailASTMap.get(name);

			DetailAST firstIdentDetailAST = identDetailASTList.get(0);
			DetailAST lastIdentDetailAST = identDetailASTList.get(
				identDetailASTList.size() - 1);

			variableDefMap.put(
				name,
				new DetailAST[] {firstIdentDetailAST, lastIdentDetailAST});
		}

		return variableDefMap;
	}

	private boolean _isRequiredMethodCall(
		String variableName, List<String> classObjectNames,
		Map<String, List<DetailAST>> identDetailASTMap,
		Map<String, DetailAST[]> variableDefMap, int start, int end) {

		for (Map.Entry<String, List<DetailAST>> entry :
				identDetailASTMap.entrySet()) {

			String name = entry.getKey();

			if (name.equals(variableName)) {
				for (DetailAST detailAST : entry.getValue()) {
					if (detailAST.getLineNo() > end) {
						break;
					}

					DetailAST parentDetailAST = detailAST.getParent();

					if (parentDetailAST.getType() == TokenTypes.DOT) {
						continue;
					}

					if (parentDetailAST.getType() == TokenTypes.ASSIGN) {
						DetailAST literalNewDetailAST =
							parentDetailAST.findFirstToken(
								TokenTypes.LITERAL_NEW);

						if (literalNewDetailAST != null) {
							continue;
						}
					}

					return true;
				}

				continue;
			}

			for (DetailAST detailAST : entry.getValue()) {
				if ((detailAST.getLineNo() < start) ||
					(detailAST.getLineNo() > end)) {

					continue;
				}

				if (classObjectNames.contains(name)) {
					return true;
				}

				DetailAST[] firstAndLastUsedDetailASTArray = variableDefMap.get(
					name);

				if (firstAndLastUsedDetailASTArray == null) {
					continue;
				}

				DetailAST lastUsedDetailAST = firstAndLastUsedDetailASTArray[1];

				if (lastUsedDetailAST.getLineNo() > end) {
					return true;
				}

				DetailAST firstUsedDetailAST =
					firstAndLastUsedDetailASTArray[0];

				if (firstUsedDetailAST.getLineNo() < start) {
					DetailAST topLevelDetailAST = _getTopLevelDetailAST(
						firstUsedDetailAST);

					int statementStartLineNumber =
						DetailASTUtil.getStartLineNumber(topLevelDetailAST);

					return _isRequiredMethodCall(
						variableName, classObjectNames, identDetailASTMap,
						variableDefMap, statementStartLineNumber, end);
				}
			}
		}

		return false;
	}

	private static final String _IMMUTABLE_FIELD_TYPES_KEY =
		"immutableFieldTypes";

	private static final String _MSG_UNNEEDED_STATIC_BLOCK =
		"static.block.unneeded";

}