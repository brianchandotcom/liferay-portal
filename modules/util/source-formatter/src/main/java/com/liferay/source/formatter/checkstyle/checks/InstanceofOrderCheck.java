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

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

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

		String typeName1 = DetailASTUtil.getTypeName(detailAST, false);
		String typeName2 = DetailASTUtil.getTypeName(
			nextConditionDetailAST, false);

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