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

/**
 * @author Hugo Huijser
 */
public class TestClassCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/")) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		String name = getName(detailAST);

		if (!name.matches(".*Test(Case)?")) {
			return;
		}

		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (name.endsWith("TestCase")) {
			if (!modifiersDetailAST.branchContains(TokenTypes.ABSTRACT)) {
				log(
					detailAST, _MSG_INCORRECT_ABSTRACT_TEST_CASE_CLASS,
					name.substring(0, name.length() - 4));
			}
			else if (name.contains("Base") && !name.startsWith("Base")) {
				log(detailAST, _MSG_INVALID_BASE_CLASS_NAME, name);
			}
		}
		else if (modifiersDetailAST.branchContains(TokenTypes.ABSTRACT)) {
			log(detailAST, _MSG_INCORRECT_ABSTRACT_TEST_CLASS, name);
		}
	}

	private static final String _MSG_INCORRECT_ABSTRACT_TEST_CASE_CLASS =
		"test.case.class.incorrect.abstract";

	private static final String _MSG_INCORRECT_ABSTRACT_TEST_CLASS =
		"test.class.incorrect.abstract";

	private static final String _MSG_INVALID_BASE_CLASS_NAME =
		"test.base.class.invalidName";

}