/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaReleaseInfoCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		String javaTermContent = javaTerm.getContent();

		if (!absolutePath.contains("/kernel/util/ReleaseInfo.java")) {
			return javaTermContent;
		}

		String variableName = javaTerm.getName();

		if (variableName.equals("_BUILD")) {
			return _fixVariable(javaTermContent, "@release.info.build@");
		}

		if (variableName.equals("_DATE")) {
			return _fixVariable(javaTermContent, "@release.info.date@");
		}

		if (variableName.equals("_NAME")) {
			return _fixVariable(javaTermContent, "@release.info.name@");
		}

		if (variableName.startsWith("RELEASE_") &&
			variableName.endsWith("_BUILD_NUMBER")) {

			return _fixReleaseBuildNumber(javaTermContent);
		}

		if (variableName.equals("_VERSION")) {
			return _fixVariable(javaTermContent, "@release.info.version@");
		}

		if (variableName.equals("_VERSION_DISPLAY_NAME")) {
			return _fixVariable(
				javaTermContent, "@release.info.version.display.name@");
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_VARIABLE};
	}

	private String _fixReleaseBuildNumber(String content) {
		Matcher matcher = _releaseBuildNumberVariablePattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replace(
				content, matcher.group(),
				StringBundler.concat(
					matcher.group(1), matcher.group(2), matcher.group(3),
					String.format(
						"%02d", GetterUtil.getInteger(matcher.group(4))),
					";"));
		}

		return content;
	}

	private String _fixVariable(String content, String expectedVariableValue) {
		Matcher matcher = _variablePattern.matcher(content);

		return matcher.replaceAll("$1" + expectedVariableValue + "\";");
	}

	private static final Pattern _releaseBuildNumberVariablePattern =
		Pattern.compile(
			"(RELEASE_([0-9]+)_([0-9]+)_([0-9]+)_BUILD_NUMBER\\s+=\\s+)[0-9]+" +
				";");
	private static final Pattern _variablePattern = Pattern.compile(
		"(=\\s+\").*\";");

}