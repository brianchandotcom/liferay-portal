/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Minhchau Dang
 */
public class JavaStringStartsWithSubstringCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		Matcher matcher = _substringContainsPattern.matcher(
			javaTerm.getContent());

		return matcher.replaceAll("$1.startsWith$2");
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private static final Pattern _substringContainsPattern = Pattern.compile(
		"(\\S*)\\.contains(\\(([^))]*)\\).*?=\\s*(\\1)\\.substring\\(" +
			"(\\3)\\.length\\(\\)\\))",
		Pattern.DOTALL);

}