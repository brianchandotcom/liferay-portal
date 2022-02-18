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

import com.liferay.portal.kernel.util.GetterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SizeIsZeroCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, "size");

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			_checkMethodCall(detailAST, methodCallDetailAST);
		}
	}

	private void _checkMethodCall(
		DetailAST detailAST, DetailAST methodCallDetailAST) {

		DetailAST nextSiblingDetailAST = methodCallDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.NUM_INT)) {

			return;
		}

		int compareCount = GetterUtil.getInteger(
			nextSiblingDetailAST.getText());

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		if (((compareCount != 0) ||
			 ((parentDetailAST.getType() != TokenTypes.EQUAL) &&
			  (parentDetailAST.getType() != TokenTypes.NOT_EQUAL) &&
			  (parentDetailAST.getType() != TokenTypes.GT))) &&
			((compareCount != 1) ||
			 ((parentDetailAST.getType() != TokenTypes.GE) &&
			  (parentDetailAST.getType() != TokenTypes.LT)))) {

			return;
		}

		String variableName = getName(methodCallDetailAST.getFirstChild());

		List<DetailAST> definitionDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF);

		for (DetailAST definitionDetailAST : definitionDetailASTList) {
			if (!variableName.equals(getName(definitionDetailAST))) {
				continue;
			}

			DetailAST typeDetailAST = definitionDetailAST.findFirstToken(
				TokenTypes.TYPE);

			String typeName = getName(typeDetailAST);

			if (typeName.matches(".*(Collection|List|Map|Set)")) {
				log(
					methodCallDetailAST, _MSG_USE_METHOD,
					variableName + ".isEmpty()");
			}
		}
	}

	private static final String _MSG_USE_METHOD = "method.use";

}