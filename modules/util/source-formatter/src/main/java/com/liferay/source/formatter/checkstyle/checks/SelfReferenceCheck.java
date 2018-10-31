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

import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class SelfReferenceCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String className = nameDetailAST.getText();

		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST == null) {
				continue;
			}

			DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() != TokenTypes.IDENT) &&
				(firstChildDetailAST.getType() != TokenTypes.LITERAL_THIS)) {

				continue;
			}

			String methodClassName = firstChildDetailAST.getText();

			if ((firstChildDetailAST.getType() == TokenTypes.LITERAL_THIS) ||
				(methodClassName.equals(className) &&
				 !_isInsideAnonymousClass(methodCallDetailAST) &&
				 !_isInsideInnerClass(methodCallDetailAST, className) &&
				 !DetailASTUtil.hasParentWithTokenType(
					 methodCallDetailAST, TokenTypes.INSTANCE_INIT))) {

				DetailAST secondChildDetailAST =
					firstChildDetailAST.getNextSibling();

				if (secondChildDetailAST.getType() == TokenTypes.IDENT) {
					log(
						methodCallDetailAST, _MSG_UNNEEDED_SELF_REFERENCE,
						secondChildDetailAST.getText(),
						firstChildDetailAST.getText() + StringPool.PERIOD);
				}
			}
		}
	}

	private boolean _isInsideAnonymousClass(DetailAST methodCallDetailAST) {
		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while (true) {
			if (parentDetailAST == null) {
				return false;
			}

			if (parentDetailAST.getType() != TokenTypes.METHOD_DEF) {
				parentDetailAST = parentDetailAST.getParent();

				continue;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.OBJBLOCK) {
				return false;
			}

			parentDetailAST = parentDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.CLASS_DEF) {
				return true;
			}

			return false;
		}
	}

	private boolean _isInsideInnerClass(
		DetailAST methodCallDetailAST, String className) {

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		while (true) {
			if ((parentDetailAST.getType() == TokenTypes.CLASS_DEF) ||
				(parentDetailAST.getType() == TokenTypes.ENUM_DEF) ||
				(parentDetailAST.getType() == TokenTypes.INTERFACE_DEF)) {

				DetailAST nameDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.IDENT);

				if (className.equals(nameDetailAST.getText())) {
					return false;
				}

				return true;
			}

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private static final String _MSG_UNNEEDED_SELF_REFERENCE =
		"self.reference.unneeded";

}