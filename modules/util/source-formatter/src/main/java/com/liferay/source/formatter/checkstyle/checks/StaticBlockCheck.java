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
import com.liferay.portal.kernel.util.StringUtil;
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

	public void setImmutableFieldTypes(String immutableFieldTypes) {
		_immutableFieldTypes = ArrayUtil.append(
			_immutableFieldTypes, StringUtil.split(immutableFieldTypes));
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<String> classObjectNames = _getClassObjectNames(detailAST);

		if (classObjectNames.isEmpty()) {
			return;
		}

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		if (methodCallASTList.isEmpty()) {
			return;
		}

		Map<String, List<DetailAST>> identASTMap = _getIdentASTMap(detailAST);

		Map<String, DetailAST[]> variableDefMap = _getVariableDefMap(
			detailAST, identASTMap);

		for (DetailAST methodCallAST : methodCallASTList) {
			_checkMethodCall(
				methodCallAST, classObjectNames, identASTMap, variableDefMap);
		}
	}

	private void _checkMethodCall(
		DetailAST methodCallAST, List<String> classObjectNames,
		Map<String, List<DetailAST>> identASTMap,
		Map<String, DetailAST[]> variableDefMap) {

		String variableName = DetailASTUtil.getVariableName(methodCallAST);

		if (!classObjectNames.contains(variableName) ||
			variableName.equals("_log")) {

			return;
		}

		DetailAST topLevelDetailAST = _getTopLevelDetailAST(methodCallAST);

		int statementEndLineNumber = DetailASTUtil.getEndLine(
			topLevelDetailAST);

		List<DetailAST> variableASTList = identASTMap.get(variableName);

		DetailAST firstUseVariableAST = variableASTList.get(0);

		topLevelDetailAST = _getTopLevelDetailAST(firstUseVariableAST);

		int statementStartLineNumber = DetailASTUtil.getStartLine(
			topLevelDetailAST);

		if (!_isRequiredMethodCall(
				variableName, classObjectNames, identASTMap, variableDefMap,
				statementStartLineNumber, statementEndLineNumber)) {

			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

			log(methodCallAST, _MSG_UNNEEDED_STATIC_BLOCK, fullIdent.getText());
		}
	}

	private List<String> _getClassObjectNames(DetailAST staticInitAST) {
		List<String> staticObjectNames = new ArrayList<>();

		DetailAST previousSiblingAST = staticInitAST.getPreviousSibling();

		while (previousSiblingAST != null) {
			DetailAST modifiersAST = previousSiblingAST.findFirstToken(
				TokenTypes.MODIFIERS);

			if (modifiersAST == null) {
				previousSiblingAST = previousSiblingAST.getPreviousSibling();

				continue;
			}

			DetailAST nameAST = previousSiblingAST.findFirstToken(
				TokenTypes.IDENT);

			String name = nameAST.getText();

			if (previousSiblingAST.getType() != TokenTypes.VARIABLE_DEF) {
				staticObjectNames.add(name);
			}
			else {
				String typeName = DetailASTUtil.getTypeName(
					previousSiblingAST, true);

				if (!ArrayUtil.contains(_immutableFieldTypes, typeName)) {
					staticObjectNames.add(name);
				}
			}

			previousSiblingAST = previousSiblingAST.getPreviousSibling();
		}

		return staticObjectNames;
	}

	private Map<String, List<DetailAST>> _getIdentASTMap(
		DetailAST staticInitAST) {

		Map<String, List<DetailAST>> identASTMap = new HashMap<>();

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			staticInitAST, true, TokenTypes.IDENT);

		for (DetailAST identAST : identASTList) {
			List<DetailAST> list = identASTMap.get(identAST.getText());

			if (list == null) {
				list = new ArrayList<>();
			}

			list.add(identAST);

			identASTMap.put(identAST.getText(), list);
		}

		return identASTMap;
	}

	private DetailAST _getTopLevelDetailAST(DetailAST detailAST) {
		DetailAST topLevelDetailAST = null;

		DetailAST parentAST = detailAST;

		while (true) {
			DetailAST grandParentAST = parentAST.getParent();

			if (grandParentAST.getType() == TokenTypes.STATIC_INIT) {
				return topLevelDetailAST;
			}

			if (grandParentAST.getType() == TokenTypes.SLIST) {
				topLevelDetailAST = parentAST;
			}

			parentAST = grandParentAST;
		}
	}

	private Map<String, DetailAST[]> _getVariableDefMap(
		DetailAST staticInitAST, Map<String, List<DetailAST>> identASTMap) {

		Map<String, DetailAST[]> variableDefMap = new HashMap<>();

		List<DetailAST> variableDefASTList = DetailASTUtil.getAllChildTokens(
			staticInitAST, true, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefAST : variableDefASTList) {
			DetailAST nameAST = variableDefAST.findFirstToken(TokenTypes.IDENT);

			String name = nameAST.getText();

			List<DetailAST> identASTList = identASTMap.get(name);

			DetailAST firstIdentAST = identASTList.get(0);
			DetailAST lastIdentAST = identASTList.get(identASTList.size() - 1);

			variableDefMap.put(
				name, new DetailAST[] {firstIdentAST, lastIdentAST});
		}

		return variableDefMap;
	}

	private boolean _isRequiredMethodCall(
		String variableName, List<String> classObjectNames,
		Map<String, List<DetailAST>> identASTMap,
		Map<String, DetailAST[]> variableDefMap, int start, int end) {

		for (Map.Entry<String, List<DetailAST>> entry :
				identASTMap.entrySet()) {

			String name = entry.getKey();

			if (name.equals(variableName)) {
				for (DetailAST detailAST : entry.getValue()) {
					if (detailAST.getLineNo() > end) {
						break;
					}

					DetailAST parentAST = detailAST.getParent();

					if (parentAST.getType() == TokenTypes.DOT) {
						continue;
					}

					if (parentAST.getType() == TokenTypes.ASSIGN) {
						DetailAST literalNewAST = parentAST.findFirstToken(
							TokenTypes.LITERAL_NEW);

						if (literalNewAST != null) {
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

					int statementStartLineNumber = DetailASTUtil.getStartLine(
						topLevelDetailAST);

					return _isRequiredMethodCall(
						variableName, classObjectNames, identASTMap,
						variableDefMap, statementStartLineNumber, end);
				}
			}
		}

		return false;
	}

	private static final String _MSG_UNNEEDED_STATIC_BLOCK =
		"static.block.unneeded";

	private String[] _immutableFieldTypes = new String[0];

}