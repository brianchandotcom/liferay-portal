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
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ArquillianCheck extends AbstractCheck {

	public static final String MSG_INVALID_IMPORT = "import.invalid";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PACKAGE_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		int pos = fileName.indexOf("/testIntegration/");

		if (pos == -1) {
			return;
		}

		List<String> importNames = _getImportNames(detailAST);

		if (!importNames.contains("org.jboss.arquillian.junit.Arquillian") ||
			importNames.contains(
				"org.jboss.arquillian.container.test.api.RunAsClient")) {

			return;
		}

		File xmlFile = new File(
			fileName.substring(0, pos) +
				"/testIntegration/resources/arquillian.xml");

		if (!xmlFile.exists()) {
			log(detailAST.getLineNo(), MSG_INVALID_IMPORT);
		}
	}

	private List<String> _getImportNames(DetailAST detailAST) {
		List<String> importASTList = new ArrayList<>();

		DetailAST sibling = detailAST.getNextSibling();

		while (true) {
			if (sibling.getType() == TokenTypes.IMPORT) {
				FullIdent importIdent = FullIdent.createFullIdentBelow(sibling);

				importASTList.add(importIdent.getText());
			}
			else {
				break;
			}

			sibling = sibling.getNextSibling();
		}

		return importASTList;
	}

}