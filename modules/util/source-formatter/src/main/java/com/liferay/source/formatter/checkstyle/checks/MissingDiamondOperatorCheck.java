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

import com.liferay.petra.string.CharPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class MissingDiamondOperatorCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST typeDetailAST = detailAST.findFirstToken(TokenTypes.TYPE);

		DetailAST typeArgumentsDetailAST = typeDetailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsDetailAST == null) {
			return;
		}

		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST literalNewDetailAST = firstChildDetailAST.getFirstChild();

		if (literalNewDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		firstChildDetailAST = literalNewDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		List<String> enforceDiamondOperatorClassNames = getAttributeValues(
			_ENFORCE_DIAMOND_OPERATOR_CLASS_NAMES_KEY);

		String className = firstChildDetailAST.getText();

		if (!enforceDiamondOperatorClassNames.contains(className)) {
			return;
		}

		DetailAST siblingDetailAST = firstChildDetailAST.getNextSibling();

		if (siblingDetailAST.getType() == TokenTypes.TYPE_ARGUMENTS) {
			return;
		}

		if (literalNewDetailAST.findFirstToken(TokenTypes.OBJBLOCK) == null) {
			log(detailAST, _MSG_MISSING_DIAMOND_OPERATOR, className);
		}
		else {
			String typeName = DetailASTUtil.getTypeName(typeDetailAST, true);

			log(
				detailAST, _MSG_MISSING_GENERIC_TYPES,
				typeName.substring(typeName.indexOf(CharPool.LESS_THAN)),
				className);
		}
	}

	private static final String _ENFORCE_DIAMOND_OPERATOR_CLASS_NAMES_KEY =
		"enforceDiamondOperatorClassNames";

	private static final String _MSG_MISSING_DIAMOND_OPERATOR =
		"diamond.operator.missing";

	private static final String _MSG_MISSING_GENERIC_TYPES =
		"generic.types.missing";

}