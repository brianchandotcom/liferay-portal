/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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