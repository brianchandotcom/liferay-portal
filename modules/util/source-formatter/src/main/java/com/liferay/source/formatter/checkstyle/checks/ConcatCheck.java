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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ConcatCheck extends StringConcatenationCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "StringBundler", "concat");

		for (DetailAST methodCallAST : methodCallASTList) {
			_checkConcatMethodCall(methodCallAST);
		}
	}

	private void _checkConcatMethodCall(DetailAST methodCallAST) {
		DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

		DetailAST previousLiteralStringAST = null;

		DetailAST childAST = elistAST.getFirstChild();

		while (true) {
			if (childAST == null) {
				break;
			}

			if (childAST.getType() == TokenTypes.EXPR) {
				DetailAST grandChildAST = childAST.getFirstChild();

				if (grandChildAST.getType() != TokenTypes.STRING_LITERAL) {
					previousLiteralStringAST = null;
				}
				else {
					if (previousLiteralStringAST != null) {
						_checkConcatMethodCallLiteralStrings(
							previousLiteralStringAST, grandChildAST);
					}

					previousLiteralStringAST = grandChildAST;
				}
			}

			childAST = childAST.getNextSibling();
		}
	}

	private void _checkConcatMethodCallLiteralStrings(
		DetailAST literalStringAST1, DetailAST literalStringAST2) {

		String literalStringValue1 = literalStringAST1.getText();

		literalStringValue1 = literalStringValue1.substring(
			1, literalStringValue1.length() - 1);

		String literalStringValue2 = literalStringAST2.getText();

		literalStringValue2 = literalStringValue2.substring(
			1, literalStringValue2.length() - 1);

		if (literalStringAST1.getLineNo() == literalStringAST2.getLineNo()) {
			log(
				literalStringAST1.getLineNo(), MSG_COMBINE_LITERAL_STRINGS,
				literalStringValue1, literalStringValue2);

			return;
		}

		checkLiteralStringStartAndEndCharacter(
			literalStringValue1, literalStringValue2,
			literalStringAST1.getLineNo());

		String line = getLine(literalStringAST1.getLineNo() - 1);

		int lineLength = CommonUtils.lengthExpandedTabs(
			line, line.length(), getTabWidth());

		int pos = getStringBreakPos(
			literalStringValue1, literalStringValue2,
			maxLineLength - lineLength);

		if (pos != -1) {
			log(
				literalStringAST2.getLineNo(), MSG_MOVE_LITERAL_STRING,
				literalStringValue2.substring(0, pos + 1));
		}
	}

}