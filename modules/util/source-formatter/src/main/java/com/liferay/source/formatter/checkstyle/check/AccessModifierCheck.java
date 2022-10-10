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

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class AccessModifierCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!absolutePath.matches(
				".+-service/.+/service/impl/.+ServiceImpl.java") ||
			absolutePath.contains("/modules/apps/archived")) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
			objBlockDetailAST, false, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			DetailAST modifiersDetailAST =
				methodDefinitionDetailAST.findFirstToken(TokenTypes.MODIFIERS);

			String methodName = getName(methodDefinitionDetailAST);

			if (methodName.startsWith("remove") ||
				methodName.startsWith("un")) {

				continue;
			}

			if (!modifiersDetailAST.branchContains(TokenTypes.ANNOTATION) &&
				modifiersDetailAST.branchContains(
					TokenTypes.LITERAL_PROTECTED)) {

				log(
					methodDefinitionDetailAST, _MSG_INCORRECT_ACCESS_MODIFIER,
					getName(methodDefinitionDetailAST));
			}
		}
	}

	private static final String _MSG_INCORRECT_ACCESS_MODIFIER =
		"access.modifier.incorrect";

}