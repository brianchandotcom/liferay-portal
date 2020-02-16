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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ConcatCheck extends BaseStringConcatenationCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, "StringBundler", "concat");

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			_checkConcatMethodCall(methodCallDetailAST);
		}
	}

	private void _checkConcatMethodCall(DetailAST methodCallDetailAST) {
		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST previousLiteralStringDetailAST = null;

		DetailAST childDetailAST = elistDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				break;
			}

			if (childDetailAST.getType() == TokenTypes.EXPR) {
				DetailAST grandChildDetailAST = childDetailAST.getFirstChild();

				if (grandChildDetailAST.getType() !=
						TokenTypes.STRING_LITERAL) {

					previousLiteralStringDetailAST = null;
				}
				else {
					if (previousLiteralStringDetailAST != null) {
						_checkConcatMethodCallLiteralStrings(
							previousLiteralStringDetailAST,
							grandChildDetailAST);
					}

					previousLiteralStringDetailAST = grandChildDetailAST;
				}

				if (grandChildDetailAST.getType() == TokenTypes.PLUS) {
					DetailAST literalStringDetailAST =
						grandChildDetailAST.findFirstToken(
							TokenTypes.STRING_LITERAL);

					if (literalStringDetailAST != null) {
						log(grandChildDetailAST, MSG_INCORRECT_PLUS);
					}
				}
			}

			childDetailAST = childDetailAST.getNextSibling();
		}
	}

	private void _checkConcatMethodCallLiteralStrings(
		DetailAST literalStringDetailAST1, DetailAST literalStringDetailAST2) {

		String literalStringValue1 = literalStringDetailAST1.getText();

		literalStringValue1 = literalStringValue1.substring(
			1, literalStringValue1.length() - 1);

		String literalStringValue2 = literalStringDetailAST2.getText();

		literalStringValue2 = literalStringValue2.substring(
			1, literalStringValue2.length() - 1);

		if (literalStringDetailAST1.getLineNo() ==
				literalStringDetailAST2.getLineNo()) {

			if (!literalStringValue1.endsWith("\\n") ||
				literalStringValue2.equals("\\n")) {

				log(
					literalStringDetailAST1, MSG_COMBINE_LITERAL_STRINGS,
					literalStringValue1, literalStringValue2);
			}

			return;
		}

		checkLiteralStringStartAndEndCharacter(
			literalStringValue1, literalStringValue2,
			literalStringDetailAST1.getLineNo());

		String line = getLine(literalStringDetailAST1.getLineNo() - 1);

		int lineLength = CommonUtil.lengthExpandedTabs(
			line, line.length(), getTabWidth());

		int pos = getStringBreakPos(
			literalStringValue1, literalStringValue2,
			getMaxLineLength() - lineLength);

		if (pos != -1) {
			log(
				literalStringDetailAST2, MSG_MOVE_LITERAL_STRING,
				literalStringValue2.substring(0, pos + 1));
		}
	}

}