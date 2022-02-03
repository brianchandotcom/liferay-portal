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

import java.io.File;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ArquillianCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PACKAGE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		int pos = absolutePath.indexOf("/testIntegration/");

		if (pos == -1) {
			return;
		}

		List<String> importNames = getImportNames(detailAST);

		if (!importNames.contains("org.jboss.arquillian.junit.Arquillian") ||
			importNames.contains(
				"org.jboss.arquillian.container.test.api.RunAsClient")) {

			return;
		}

		File xmlFile = new File(
			absolutePath.substring(0, pos) +
				"/testIntegration/resources/arquillian.xml");

		if (!xmlFile.exists()) {
			log(detailAST, _MSG_INVALID_IMPORT);
		}
	}

	private static final String _MSG_INVALID_IMPORT = "import.invalid";

}