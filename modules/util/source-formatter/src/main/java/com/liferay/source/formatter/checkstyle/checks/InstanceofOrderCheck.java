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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class InstanceofOrderCheck extends AbstractCheck {

	public static final String MSG_ORDER_INSTANCEOF = "instanceof.order";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_INSTANCEOF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		if ((parentAST.getType() != TokenTypes.LAND) &&
			(parentAST.getType() != TokenTypes.LOR)) {

			return;
		}

		DetailAST nextConditionAST = _getNextConditionAST(detailAST);

		if ((nextConditionAST == null) ||
			(nextConditionAST.getType() != TokenTypes.LITERAL_INSTANCEOF)) {

			return;
		}

		String variableName1 = _getVariableName(detailAST);
		String variableName2 = _getVariableName(nextConditionAST);

		if ((variableName1 == null) || !variableName1.equals(variableName2)) {
			return;
		}

		NaturalOrderStringComparator comparator =
			new NaturalOrderStringComparator();

		String typeName1 = DetailASTUtil.getTypeName(detailAST);
		String typeName2 = DetailASTUtil.getTypeName(nextConditionAST);

		if (comparator.compare(typeName1, typeName2) > 0) {
			log(
				nextConditionAST.getLineNo(), MSG_ORDER_INSTANCEOF, typeName2,
				typeName1);
		}
	}

	private DetailAST _getNextConditionAST(DetailAST detailAST) {
		DetailAST nextSibling = detailAST.getNextSibling();

		if (nextSibling != null) {
			return nextSibling;
		}

		DetailAST parentAST = detailAST.getParent();

		return parentAST.getNextSibling();
	}

	private String _getVariableName(DetailAST literalInstanceOfAST) {
		DetailAST nameAST = literalInstanceOfAST.findFirstToken(
			TokenTypes.IDENT);

		if (nameAST == null) {
			return null;
		}

		return nameAST.getText();
	}

}