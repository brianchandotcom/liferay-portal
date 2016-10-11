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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SizeIsZeroCheck extends AbstractCheck {

	public static final String MSG_USE_METHOD = "method.use";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "size");

		for (DetailAST methodCallAST : methodCallASTList) {
			_checkMethodCall(detailAST, methodCallAST);
		}
	}

	private void _checkMethodCall(
		DetailAST detailAST, DetailAST methodCallAST) {

		DetailAST nextSibling = methodCallAST.getNextSibling();

		if ((nextSibling == null) ||
			(nextSibling.getType() != TokenTypes.NUM_INT)) {

			return;
		}

		int compareCount = GetterUtil.getInteger(nextSibling.getText());

		DetailAST parentAST = methodCallAST.getParent();

		if (((compareCount != 0) ||
			 ((parentAST.getType() != TokenTypes.EQUAL) &&
			  (parentAST.getType() != TokenTypes.NOT_EQUAL) &&
			  (parentAST.getType() != TokenTypes.GT))) &&
			((compareCount != 1) ||
			 ((parentAST.getType() != TokenTypes.GE) &&
			  (parentAST.getType() != TokenTypes.LT)))) {

			return;
		}

		DetailAST dotAST = methodCallAST.getFirstChild();

		DetailAST nameAST = dotAST.findFirstToken(TokenTypes.IDENT);

		String variableName = nameAST.getText();

		List<DetailAST> definitionASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF);

		for (DetailAST definitionAST : definitionASTList) {
			DetailAST definitionNameAST = definitionAST.findFirstToken(
				TokenTypes.IDENT);

			if (!variableName.equals(definitionNameAST.getText())) {
				continue;
			}

			DetailAST typeAST = definitionAST.findFirstToken(TokenTypes.TYPE);

			DetailAST typeNameAST = typeAST.findFirstToken(TokenTypes.IDENT);

			String typeName = typeNameAST.getText();

			if (typeName.matches(".*(Collection|List|Map|Set)")) {
				log(
					methodCallAST.getLineNo(), MSG_USE_METHOD,
					variableName + ".isEmpty()");
			}
		}
	}

}