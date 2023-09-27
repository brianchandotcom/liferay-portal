/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class InstanceofOrderCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_INSTANCEOF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.LAND) &&
			(parentDetailAST.getType() != TokenTypes.LOR)) {

			return;
		}

		DetailAST nextConditionDetailAST = _getNextConditionDetailAST(
			detailAST);

		if ((nextConditionDetailAST == null) ||
			(nextConditionDetailAST.getType() !=
				TokenTypes.LITERAL_INSTANCEOF)) {

			return;
		}

		String variableName1 = _getVariableName(detailAST);
		String variableName2 = _getVariableName(nextConditionDetailAST);

		if ((variableName1 == null) || !variableName1.equals(variableName2)) {
			return;
		}

		NaturalOrderStringComparator comparator =
			new NaturalOrderStringComparator();

		String typeName1 = getTypeName(detailAST, false);
		String typeName2 = getTypeName(nextConditionDetailAST, false);

		if (comparator.compare(typeName1, typeName2) > 0) {
			log(
				nextConditionDetailAST, _MSG_ORDER_INSTANCEOF, typeName2,
				typeName1);
		}
	}

	private DetailAST _getNextConditionDetailAST(DetailAST detailAST) {
		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if (nextSiblingDetailAST != null) {
			return nextSiblingDetailAST;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		return parentDetailAST.getNextSibling();
	}

	private String _getVariableName(DetailAST literalInstanceofDetailAST) {
		DetailAST nameDetailAST = literalInstanceofDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (nameDetailAST == null) {
			return null;
		}

		return nameDetailAST.getText();
	}

	private static final String _MSG_ORDER_INSTANCEOF = "instanceof.order";

}