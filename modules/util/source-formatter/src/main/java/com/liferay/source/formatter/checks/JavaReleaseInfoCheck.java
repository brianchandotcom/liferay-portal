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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

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
			javaTermContent = _formatBuild(fileName, javaTerm);
		}
		else if (variableName.equals("_VERSION")) {
			_checkMissingReleaseBuildNumber(fileName, javaTerm);
		}
		else if (variableName.equals("_VERSION_DISPLAY_NAME")) {
			javaTermContent = _formatVersionDisplayName(javaTerm);
		}
		else {
			Matcher matcher = _releaseBuildNumberPattern.matcher(variableName);

			if (matcher.find()) {
				return _formatReleaseBuildNumber(
					javaTerm,
					new ReleaseVersion(matcher.group(1), StringPool.UNDERLINE));
			}
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_VARIABLE};
	}

	private void _checkMissingReleaseBuildNumber(
		String fileName, JavaTerm javaTerm) {

		ReleaseVersion releaseVersion = new ReleaseVersion(
			_getVariableValue(javaTerm.getContent()));

		String releaseBuildNumberVariableName = StringBundler.concat(
			"RELEASE_", releaseVersion.toString("_"), "_BUILD_NUMBER");

		JavaVariable releaseBuildNumberJavaVariable = _getJavaVariable(
			javaTerm, releaseBuildNumberVariableName);

		if (releaseBuildNumberJavaVariable == null) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Missing variable '", releaseBuildNumberVariableName, "'"));
		}
	}

	private String _formatBuild(String fileName, JavaTerm javaTerm) {
		ReleaseVersion latestReleaseVersion = _getLatestReleaseVersion(
			javaTerm);

		if (latestReleaseVersion == null) {
			addMessage(fileName, "Missing variable '_VERSION'");

			return javaTerm.getContent();
		}

		String buildValue = _getVariableValue(javaTerm.getContent());

		String latestReleaseVersionBuild = latestReleaseVersion.toString();

		if (!buildValue.equals(latestReleaseVersionBuild)) {
			return StringUtil.replaceLast(
				javaTerm.getContent(), buildValue, latestReleaseVersionBuild);
		}

		return javaTerm.getContent();
	}

	private String _formatReleaseBuildNumber(
		JavaTerm javaTerm, ReleaseVersion releaseVersion) {

		String actualReleaseBuildNumberValue = _getVariableValue(
			javaTerm.getContent());

		String expectedReleaseBuildNumberValue = releaseVersion.toString();

		if (!actualReleaseBuildNumberValue.equals(
				expectedReleaseBuildNumberValue)) {

			return StringUtil.replaceLast(
				javaTerm.getContent(), actualReleaseBuildNumberValue,
				expectedReleaseBuildNumberValue);
		}

		return javaTerm.getContent();
	}

	private String _formatVersionDisplayName(JavaTerm javaTerm) {
		String versionDisplayNameValue = _getVariableValue(
			javaTerm.getContent());

		String[] versionDisplayNameParts = StringUtil.split(
			versionDisplayNameValue, CharPool.SPACE);

		if (!ArrayUtil.contains(versionDisplayNameParts, "CE")) {
			return javaTerm.getContent();
		}

		String actualVersionNumber = versionDisplayNameParts[0];

		ReleaseVersion latestReleaseVersion = _getLatestReleaseVersion(
			javaTerm);

		String expectedVersionNumber = latestReleaseVersion.toString(
			StringPool.PERIOD);

		String newVersionDisplayNameValue = StringUtil.replaceFirst(
			versionDisplayNameValue, actualVersionNumber,
			expectedVersionNumber);

		String actualReleaseType =
			versionDisplayNameParts[versionDisplayNameParts.length - 1];

		int microVersion = GetterUtil.getInteger(
			latestReleaseVersion.getMicroVersion());

		if ((microVersion > 0) || actualReleaseType.startsWith("GA")) {
			String expectedReleaseType = "GA" + (microVersion + 1);

			newVersionDisplayNameValue = StringUtil.replaceLast(
				newVersionDisplayNameValue, actualReleaseType,
				expectedReleaseType);
		}

		if (versionDisplayNameValue.equals(newVersionDisplayNameValue)) {
			return javaTerm.getContent();
		}

		return StringUtil.replaceLast(
			javaTerm.getContent(), versionDisplayNameValue,
			newVersionDisplayNameValue);
	}

	private JavaVariable _getJavaVariable(JavaTerm javaTerm, String name) {
		JavaClass javaClass = javaTerm.getParentJavaClass();

		for (JavaTerm curJavaTerm : javaClass.getChildJavaTerms()) {
			if ((curJavaTerm instanceof JavaVariable) &&
				name.equals(curJavaTerm.getName())) {

				return (JavaVariable)curJavaTerm;
			}
		}

		return null;
	}

	private ReleaseVersion _getLatestReleaseVersion(JavaTerm javaTerm) {
		JavaVariable versionJavaVariable = _getJavaVariable(
			javaTerm, "_VERSION");

		if (versionJavaVariable == null) {
			return null;
		}

		String versionValue = _getVariableValue(
			versionJavaVariable.getContent());

		return new ReleaseVersion(versionValue);
	}

	private String _getVariableValue(String variableContent) {
		variableContent = StringUtil.trim(variableContent);

		int x = variableContent.length() + 1;

		while (true) {
			x = variableContent.lastIndexOf("=", x - 1);

			if (x == -1) {
				return null;
			}

			if (ToolsUtil.isInsideQuotes(variableContent, x)) {
				continue;
			}

			String variableValue = StringUtil.trim(
				variableContent.substring(x + 1, variableContent.length() - 1));

			if (variableValue.startsWith(StringPool.QUOTE) &&
				variableValue.endsWith(StringPool.QUOTE)) {

				return variableValue.substring(1, variableValue.length() - 1);
			}

			return variableValue;
		}
	}

	private static final Pattern _releaseBuildNumberPattern = Pattern.compile(
		"^RELEASE_([0-9_]+)_BUILD_NUMBER$");

	private class ReleaseVersion {

		public ReleaseVersion(String version) {
			this(version, StringPool.PERIOD);
		}

		public ReleaseVersion(String version, String delimeter) {
			int x = version.indexOf(delimeter);

			if (x == -1) {
				return;
			}

			_majorVersion = version.substring(0, x);

			int y = version.indexOf(delimeter, x + 1);

			if (y == -1) {
				return;
			}

			_microVersion = version.substring(y + 1);
			_minorVersion = version.substring(x + 1, y);
		}

		public String getMicroVersion() {
			return _microVersion;
		}

		@Override
		public String toString() {
			return toString(null);
		}

		public String toString(String delimeter) {
			if (delimeter == null) {
				return StringBundler.concat(
					_majorVersion, _minorVersion,
					String.format(
						"%02d", GetterUtil.getInteger(_microVersion)));
			}

			return StringBundler.concat(
				_majorVersion, delimeter, _minorVersion, delimeter,
				_microVersion);
		}

		private String _majorVersion;
		private String _microVersion;
		private String _minorVersion;

	}

}