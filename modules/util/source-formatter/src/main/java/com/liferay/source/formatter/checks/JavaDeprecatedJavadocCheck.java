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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * @author Hugo Huijser
 */
public class JavaDeprecatedJavadocCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatDeprecatedJavadoc(fileName, absolutePath, content);

		return content;
	}

	private String _formatDeprecatedJavadoc(
			String fileName, String absolutePath, String content)
		throws Exception {

		ComparableVersion mainReleaseComparableVersion =
			_getMainReleaseComparableVersion(fileName, absolutePath);

		if (mainReleaseComparableVersion == null) {
			return content;
		}

		Matcher matcher = _deprecatedPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.group(2) == null) {
				return StringUtil.insert(
					content,
					" As of " + mainReleaseComparableVersion.toString(),
					matcher.end(1));
			}

			String version = matcher.group(3);

			ComparableVersion comparableVersion = new ComparableVersion(
				version);

			if (comparableVersion.compareTo(mainReleaseComparableVersion) > 0) {
				return StringUtil.replaceFirst(
					content, version, mainReleaseComparableVersion.toString(),
					matcher.start());
			}

			if (StringUtil.count(version, CharPool.PERIOD) == 1) {
				return StringUtil.insert(content, ".0", matcher.end(3));
			}

			String deprecatedInfo = matcher.group(4);

			if (Validator.isNull(deprecatedInfo)) {
				return content;
			}

			if (!deprecatedInfo.startsWith(StringPool.COMMA)) {
				return StringUtil.insert(
					content, StringPool.COMMA, matcher.end(3));
			}

			if (deprecatedInfo.endsWith(StringPool.PERIOD) &&
				!deprecatedInfo.matches("[\\S\\s]*\\.[ \n][\\S\\s]*")) {

				return StringUtil.replaceFirst(
					content, StringPool.PERIOD, StringPool.BLANK,
					matcher.end(4) - 1);
			}
		}

		return content;
	}

	private ComparableVersion _getMainReleaseComparableVersion(
			String fileName, String absolutePath)
		throws Exception {

		boolean usePortalReleaseVersion = false;

		if (isPortalSource() && !isModulesFile(absolutePath)) {
			usePortalReleaseVersion = true;
		}

		String releaseVersion = StringPool.BLANK;

		if (usePortalReleaseVersion) {
			if (_mainReleaseComparableVersion != null) {
				return _mainReleaseComparableVersion;
			}

			releaseVersion = ReleaseInfo.getVersion();
		}
		else {
			BNDSettings bndSettings = getBNDSettings(fileName);

			if (bndSettings == null) {
				return null;
			}

			releaseVersion = bndSettings.getReleaseVersion();

			if (releaseVersion == null) {
				return null;
			}

			putBNDSettings(bndSettings);
		}

		int pos = releaseVersion.lastIndexOf(CharPool.PERIOD);

		String mainReleaseVersion = releaseVersion.substring(0, pos) + ".0";

		ComparableVersion mainReleaseComparableVersion = new ComparableVersion(
			mainReleaseVersion);

		if (usePortalReleaseVersion) {
			_mainReleaseComparableVersion = mainReleaseComparableVersion;
		}

		return mainReleaseComparableVersion;
	}

	private final Pattern _deprecatedPattern = Pattern.compile(
		"(\n\\s*\\* @deprecated)( As of ([0-9\\.]+)(.*?)\n\\s*\\*( @|/))?",
		Pattern.DOTALL);
	private ComparableVersion _mainReleaseComparableVersion;

}