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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class TransactionalTestRuleCheck extends AbstractCheck {

	public static final String MSG_INVALID_IMPORT = "import.invalid";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.IMPORT};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		String line = getLine(detailAST.getLineNo());

		if (!line.contains(
				"import com.liferay.portal.test.rule.TransactionalTestRule;")) {

			return;
		}

		FileContents fileContents = getFileContents();

		String fileName = fileContents.getFileName();

		if (fileName.endsWith("StagedModelDataHandlerTest.java")) {
			log(detailAST.getLineNo(), MSG_INVALID_IMPORT);
		}
	}

}