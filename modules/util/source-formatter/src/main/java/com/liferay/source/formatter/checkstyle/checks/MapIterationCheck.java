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
		List<DetailAST> forEachClauseASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.FOR_EACH_CLAUSE);

		for (DetailAST forEachClauseAST : forEachClauseASTList) {
			_checkKeySetIteration(forEachClauseAST);
		}
	}

	private void _checkKeySetIteration(DetailAST forEachClauseAST) {
		DetailAST variableDefAST = forEachClauseAST.findFirstToken(
			TokenTypes.VARIABLE_DEF);

		DetailAST identAST = variableDefAST.findFirstToken(TokenTypes.IDENT);

		String keyName = identAST.getText();

		List<DetailAST> keySetMethodCallASTList = DetailASTUtil.getMethodCalls(
			forEachClauseAST, "keySet");

		for (DetailAST keySetMethodCallAST : keySetMethodCallASTList) {
			FullIdent fullIdent = FullIdent.createFullIdentBelow(
				keySetMethodCallAST);

			String mapName = StringUtil.replaceLast(
				fullIdent.getText(), ".keySet", StringPool.BLANK);

			if (!_containsGetMethod(
					forEachClauseAST.getParent(), keyName, mapName)) {

				continue;
			}

			DetailAST typeAST = DetailASTUtil.getVariableTypeAST(
				keySetMethodCallAST, mapName);

			if ((typeAST != null) && DetailASTUtil.isCollection(typeAST)) {
				List<DetailAST> wildcardTypeASTList =
					DetailASTUtil.getAllChildTokens(
						typeAST, true, TokenTypes.WILDCARD_TYPE);

				if (wildcardTypeASTList.isEmpty()) {
					log(forEachClauseAST.getLineNo(), _MSG_USE_ENTRY_SET);
				}
			}
		}
	}

	private boolean _containsGetMethod(
		DetailAST forAST, String keyName, String mapName) {

		List<DetailAST> getMethodCallASTList = DetailASTUtil.getMethodCalls(
			forAST, mapName, "get");

		for (DetailAST getMethodCallAST : getMethodCallASTList) {
			DetailAST eListAST = getMethodCallAST.findFirstToken(
				TokenTypes.ELIST);

			DetailAST firstChild = eListAST.getFirstChild();

			if (firstChild.getType() != TokenTypes.EXPR) {
				continue;
			}

			firstChild = firstChild.getFirstChild();

			if (firstChild.getType() == TokenTypes.IDENT) {
				String parameterName = firstChild.getText();

				if (parameterName.equals(keyName)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final String _MSG_USE_ENTRY_SET = "entry.set.use";

}