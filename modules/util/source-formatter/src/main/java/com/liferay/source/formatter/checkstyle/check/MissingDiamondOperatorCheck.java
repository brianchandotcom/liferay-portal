/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.petra.string.CharPool;

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
			String typeName = getTypeName(typeDetailAST, true);

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