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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class TestClassCheck extends AbstractCheck {

	public static final String MSG_INCORRECT_ABSTRACT_TEST_CASE_CLASS =
		"test.case.class.incorrect.abstract";

	public static final String MSG_INCORRECT_ABSTRACT_TEST_CLASS =
		"test.class.incorrect.abstract";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		if (!fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/")) {

			return;
		}

		DetailAST parentAST = detailAST.getParent();

		if (parentAST != null) {
			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameAST.getText();

		if (!name.matches(".*Test(Case)?")) {
			return;
		}

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (name.endsWith("TestCase")) {
			if (!modifiersAST.branchContains(TokenTypes.ABSTRACT)) {
				log(
					detailAST.getLineNo(),
					MSG_INCORRECT_ABSTRACT_TEST_CASE_CLASS,
					name.substring(0, name.length() - 4));
			}
		}
		else if (modifiersAST.branchContains(TokenTypes.ABSTRACT)) {
			log(detailAST.getLineNo(), MSG_INCORRECT_ABSTRACT_TEST_CLASS, name);
		}
	}

}