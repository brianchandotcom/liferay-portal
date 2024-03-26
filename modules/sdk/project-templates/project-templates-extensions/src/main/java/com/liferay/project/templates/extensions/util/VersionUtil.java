/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.extensions.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author Gregory Amerson
 * @author Lawrence Lee
 */
public class VersionUtil {

	public static int getMajorVersion(String liferayVersion) {
		Matcher quarterlyVersionMatcher =
			_liferayQuarterlyVersionPattern.matcher(liferayVersion);

		if (quarterlyVersionMatcher.matches()) {
			return _parseInt(quarterlyVersionMatcher.group("major"));
		}

		Matcher matcher = _liferayVersionPattern.matcher(liferayVersion);

		if (matcher.matches()) {
			return _parseInt(matcher.group(1));
		}

		return 0;
	}

	public static int getMicroVersion(String liferayVersion) throws Exception {
		Matcher quarterlyVersionMatcher =
			_liferayQuarterlyVersionPattern.matcher(liferayVersion);

		if (quarterlyVersionMatcher.matches()) {
			return _parseInt(quarterlyVersionMatcher.group("micro"));
		}

		String normalizedLiferayVersionString = normalizeLiferayVersion(
			liferayVersion);

		Matcher matcher = _liferayVersionPattern.matcher(
			normalizedLiferayVersionString);

		if (matcher.matches()) {
			return _parseInt(matcher.group(5));
		}

		return 0;
	}

	public static int getMinorVersion(String liferayVersion) {
		Matcher quarterlyVersionMatcher =
			_liferayQuarterlyVersionPattern.matcher(liferayVersion);

		if (quarterlyVersionMatcher.matches()) {
			return _parseInt(quarterlyVersionMatcher.group("minor"));
		}

		Matcher matcher = _liferayVersionPattern.matcher(liferayVersion);

		if (matcher.matches()) {
			return _parseInt(matcher.group(2));
		}

		return 0;
	}

	public static boolean isLiferayQuarterlyVersion(String liferayVersion) {
		Matcher quarterlyVersionMatcher =
			_liferayQuarterlyVersionPattern.matcher(liferayVersion);

		return quarterlyVersionMatcher.matches();
	}

	public static boolean isLiferayVersion(String liferayVersion) {
		if (isLiferayQuarterlyVersion(liferayVersion)) {
			return true;
		}

		Matcher matcher = _liferayVersionPattern.matcher(liferayVersion);

		return matcher.matches();
	}

	public static String normalizeLiferayVersion(String liferayVersion)
		throws Exception {

		String normalizedVersion = liferayVersion.replaceAll("-", ".");

		IntStream intStream = normalizedVersion.chars();

		long componentCount = intStream.filter(
			components -> components == '.'
		).count();

		if (componentCount > 3) {
			normalizedVersion = normalizedVersion.substring(
				0, normalizedVersion.lastIndexOf("."));
		}

		return normalizedVersion;
	}

	private static int _parseInt(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException numberFormatException) {
			return 0;
		}
	}

	private static final Pattern _liferayQuarterlyVersionPattern =
		Pattern.compile(
			"^(?<major>2\\d{3})\\.q(?<minor>[1234])\\.(?<micro>\\d+)$");
	private static final Pattern _liferayVersionPattern = Pattern.compile(
		"^([7-9]|[1-9]\\d{1}|[1-9]\\d{2})\\.(\\d+)((\\.)(\\d+|\\d+-[1-9])" +
			"(-\\d+|(\\.(((e|f)p)|u)?[0-9]+(-[0-9]+)?))?)?$");

}