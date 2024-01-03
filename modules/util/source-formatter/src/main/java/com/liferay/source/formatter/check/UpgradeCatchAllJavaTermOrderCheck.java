/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import org.dom4j.DocumentException;

/**
 * @author Nícolas Moura
 */
public class UpgradeCatchAllJavaTermOrderCheck extends JavaTermOrderCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws DocumentException, IOException {

		if (!fileName.endsWith("UpgradeCatchAllCheck.java")) {
			return javaTerm.getContent();
		}

		return super.doProcess(fileName, absolutePath, javaTerm, fileContent);
	}

}