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
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class MissingAuthorCheck extends AbstractCheck {

	public static final String MSG_MISSING_AUTHOR = "author.missing";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST parentAST = detailAST.getParent();

		if (parentAST != null) {
			return;
		}

		FileContents fileContents = getFileContents();

		TextBlock javadoc = fileContents.getJavadocBefore(
			detailAST.getLineNo());

		if (javadoc == null) {
			log(detailAST.getLineNo(), MSG_MISSING_AUTHOR);

			return;
		}

		String[] javadocLines = javadoc.getText();

		for (String javadocLine : javadocLines) {
			if (javadocLine.contains("@author ")) {
				return;
			}
		}

		log(detailAST.getLineNo(), MSG_MISSING_AUTHOR);
	}

}