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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SelfReferenceCheck extends AbstractCheck {

	public static final String MSG_UNNEEDED_SELF_REFERENCE =
		"self.reference.unneeded";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String className = nameAST.getText();

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			if (dotAST == null) {
				continue;
			}

			DetailAST firstChild = dotAST.getFirstChild();

			if ((firstChild.getType() != TokenTypes.IDENT) &&
				(firstChild.getType() != TokenTypes.LITERAL_THIS)) {

				continue;
			}

			String methodClassName = firstChild.getText();

			if ((firstChild.getType() == TokenTypes.LITERAL_THIS) ||
				(methodClassName.equals(className) &&
				 !_isInsideAnonymousClass(methodCallAST) &&
				 !_isInsideInnerClass(methodCallAST, className))) {

				DetailAST secondChild = firstChild.getNextSibling();

				if (secondChild.getType() == TokenTypes.IDENT) {
					log(
						methodCallAST.getLineNo(), MSG_UNNEEDED_SELF_REFERENCE,
						secondChild.getText(),
						firstChild.getText() + StringPool.PERIOD);
				}
			}
		}
	}

	private boolean _isInsideAnonymousClass(DetailAST methodCallAST) {
		DetailAST parentAST = methodCallAST.getParent();

		while (true) {
			if (parentAST == null) {
				return false;
			}

			if (parentAST.getType() != TokenTypes.METHOD_DEF) {
				parentAST = parentAST.getParent();

				continue;
			}

			parentAST = parentAST.getParent();

			if (parentAST.getType() != TokenTypes.OBJBLOCK) {
				return false;
			}

			parentAST = parentAST.getParent();

			if (parentAST.getType() != TokenTypes.CLASS_DEF) {
				return true;
			}

			return false;
		}
	}

	private boolean _isInsideInnerClass(
		DetailAST methodCallAST, String className) {

		DetailAST parentAST = methodCallAST.getParent();

		while (true) {
			if (parentAST.getType() == TokenTypes.CLASS_DEF) {
				DetailAST nameAST = parentAST.findFirstToken(TokenTypes.IDENT);

				if (className.equals(nameAST.getText())) {
					return false;
				}

				return true;
			}

			parentAST = parentAST.getParent();
		}
	}

}