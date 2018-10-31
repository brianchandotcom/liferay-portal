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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MapIterationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> forEachClauseDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.FOR_EACH_CLAUSE);

		for (DetailAST forEachClauseDetailAST : forEachClauseDetailASTList) {
			_checkKeySetIteration(forEachClauseDetailAST);
		}
	}

	private void _checkKeySetIteration(DetailAST forEachClauseDetailAST) {
		DetailAST variableDefinitionDetailAST =
			forEachClauseDetailAST.findFirstToken(TokenTypes.VARIABLE_DEF);

		DetailAST identDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String keyName = identDetailAST.getText();

		List<DetailAST> keySetMethodCallDetailASTList =
			DetailASTUtil.getMethodCalls(forEachClauseDetailAST, "keySet");

		for (DetailAST keySetMethodCallDetailAST :
				keySetMethodCallDetailASTList) {

			FullIdent fullIdent = FullIdent.createFullIdentBelow(
				keySetMethodCallDetailAST);

			String mapName = StringUtil.replaceLast(
				fullIdent.getText(), ".keySet", StringPool.BLANK);

			if (!_containsGetMethod(
					forEachClauseDetailAST.getParent(), keyName, mapName)) {

				continue;
			}

			DetailAST typeDetailAST = DetailASTUtil.getVariableTypeDetailAST(
				keySetMethodCallDetailAST, mapName);

			if ((typeDetailAST != null) &&
				DetailASTUtil.isCollection(typeDetailAST)) {

				List<DetailAST> wildcardTypeDetailASTList =
					DetailASTUtil.getAllChildTokens(
						typeDetailAST, true, TokenTypes.WILDCARD_TYPE);

				if (wildcardTypeDetailASTList.isEmpty()) {
					log(forEachClauseDetailAST, _MSG_USE_ENTRY_SET);
				}
			}
		}
	}

	private boolean _containsGetMethod(
		DetailAST forDetailAST, String keyName, String mapName) {

		List<DetailAST> getMethodCallDetailASTList =
			DetailASTUtil.getMethodCalls(forDetailAST, mapName, "get");

		for (DetailAST getMethodCallDetailAST : getMethodCallDetailASTList) {
			DetailAST eListDetailAST = getMethodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			DetailAST firstChildDetailAST = eListDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				String parameterName = firstChildDetailAST.getText();

				if (parameterName.equals(keyName)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final String _MSG_USE_ENTRY_SET = "entry.set.use";

}