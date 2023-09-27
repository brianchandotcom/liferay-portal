/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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