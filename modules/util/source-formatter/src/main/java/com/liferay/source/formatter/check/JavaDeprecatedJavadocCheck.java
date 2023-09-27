/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaDeprecatedJavadocCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws ReflectiveOperationException {

		return _formatDeprecatedJavadoc(content);
	}

	private String _formatDeprecatedJavadoc(String content)
		throws ReflectiveOperationException {

		Matcher matcher = _deprecatedPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.group(2) == null) {
				return StringUtil.insert(
					content, " As of " + _getNextReleaseCodeName(),
					matcher.end(1));
			}

			String releaseCodeName = matcher.group(4);

			if (!_releaseInfoMap.containsKey(releaseCodeName)) {
				String nextReleaseCodeName = _getNextReleaseCodeName();

				if (!releaseCodeName.equals(nextReleaseCodeName)) {
					return StringUtil.replaceFirst(
						content, releaseCodeName, nextReleaseCodeName,
						matcher.start());
				}

				_releaseInfoMap.put(
					nextReleaseCodeName, _getNextReleaseVersion());
			}

			String expectedReleaseVersion = _releaseInfoMap.get(
				releaseCodeName);

			if (matcher.group(5) == null) {
				return StringUtil.insert(
					content,
					StringBundler.concat(" (", expectedReleaseVersion, ")"),
					matcher.end(4));
			}

			if (StringUtil.startsWith(matcher.group(5), ",")) {
				String oldSub = matcher.group(5);

				return StringUtil.replaceFirst(
					content, oldSub, oldSub.substring(1), matcher.start(5));
			}

			String actualReleaseVersion = matcher.group(6);

			if (!actualReleaseVersion.equals(expectedReleaseVersion)) {
				return StringUtil.replaceFirst(
					content, actualReleaseVersion, expectedReleaseVersion,
					matcher.start(5));
			}

			String deprecatedInfo = matcher.group(7);

			if (Validator.isNull(deprecatedInfo)) {
				continue;
			}

			if (!deprecatedInfo.startsWith(StringPool.COMMA)) {
				return StringUtil.insert(
					content, StringPool.COMMA, matcher.end(3));
			}

			if (!deprecatedInfo.startsWith(StringPool.COMMA_AND_SPACE)) {
				return StringUtil.replaceFirst(
					content, StringPool.COMMA, StringPool.COMMA_AND_SPACE,
					matcher.start(7));
			}

			if (deprecatedInfo.matches(", [A-Z].*")) {
				String s = deprecatedInfo.substring(0, 3);

				return StringUtil.replaceFirst(
					content, s, StringUtil.toLowerCase(s), matcher.start(7));
			}

			if (deprecatedInfo.matches(", since [0-9.]+(, [\\S\\s]*)?")) {
				int x = deprecatedInfo.indexOf(CharPool.COMMA, 1);

				if (x == -1) {
					return StringUtil.replaceFirst(
						content, deprecatedInfo, StringPool.BLANK,
						matcher.start(7));
				}

				String s = deprecatedInfo.substring(0, x);

				return StringUtil.replaceFirst(
					content, s, StringPool.BLANK, matcher.start(7));
			}

			if (deprecatedInfo.equals(", unused")) {
				return StringUtil.replaceFirst(
					content, deprecatedInfo, StringPool.BLANK,
					matcher.start(7));
			}

			if (deprecatedInfo.endsWith(StringPool.PERIOD) &&
				!deprecatedInfo.matches("[\\S\\s]*\\.[ \n][\\S\\s]*")) {

				return StringUtil.replaceFirst(
					content, StringPool.PERIOD, StringPool.BLANK,
					matcher.end(7) - 1);
			}
		}

		return content;
	}

	private synchronized String _getNextReleaseCodeName()
		throws ReflectiveOperationException {

		if (_nextReleaseCodeName != null) {
			return _nextReleaseCodeName;
		}

		Field codeNameField = ReleaseInfo.class.getDeclaredField("_CODE_NAME");

		codeNameField.setAccessible(true);

		_nextReleaseCodeName = String.valueOf(codeNameField.get(null));

		return _nextReleaseCodeName;
	}

	private synchronized String _getNextReleaseVersion()
		throws ReflectiveOperationException {

		if (_nextReleaseVersion != null) {
			return _nextReleaseVersion;
		}

		Field versionField = ReleaseInfo.class.getDeclaredField("_VERSION");

		versionField.setAccessible(true);

		_nextReleaseVersion = StringUtil.replaceLast(
			String.valueOf(versionField.get(null)), ".0", ".x");

		return _nextReleaseVersion;
	}

	private static final Pattern _deprecatedPattern = Pattern.compile(
		"(\n\\s*\\* @deprecated)( As of (([\\w.]+)(,? \\(([\\w.]+)\\))?)" +
			"(.*?)\n\\s*\\*( @|/))?",
		Pattern.DOTALL);
	private static final Map<String, String> _releaseInfoMap =
		HashMapBuilder.put(
			"Athanasius", "7.3.x"
		).put(
			"Bunyan", "6.0.x"
		).put(
			"Cavanaugh", "7.4.x"
		).put(
			"Judson", "7.1.x"
		).put(
			"Mueller", "7.2.x"
		).put(
			"Newton", "6.2.x"
		).put(
			"Paton", "6.1.x"
		).put(
			"Wilberforce", "7.0.x"
		).build();

	private String _nextReleaseCodeName;
	private String _nextReleaseVersion;

}