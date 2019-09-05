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

package com.liferay.gradle.plugins.workspace.internal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.GradleException;

/**
 * @author Gregory Amerson
 */
public class VersionUtil {

	public static boolean isDXPVersion(String targetPlatformVersion) {
		Matcher matcher = _dxpVersionPattern.matcher(targetPlatformVersion);

		return matcher.matches();
	}

	public static String normalizeTargetPlatformVersion(
		String targetPlatformVersion) {

		String normalizedTargetPlatformVersion = targetPlatformVersion;

		Matcher matcher = _externalVersionPattern.matcher(
			targetPlatformVersion);

		if (matcher.matches()) {
			StringBuilder sb = new StringBuilder();

			sb.append(matcher.group(1));
			sb.append('.');
			sb.append(matcher.group(2));
			sb.append('.');

			String label = matcher.group(3);

			try {
				int labelNumber = Integer.parseInt(matcher.group(4));

				label = StringUtil.toLowerCase(label);

				if (label.startsWith("fp")) {
					sb.append("10.");
					sb.append(label);
					sb.append(labelNumber);
				}
				else if (label.startsWith("ga")) {
					sb.append(labelNumber - 1);
				}
				else if (label.startsWith("sp")) {
					sb.append("10.");
					sb.append(labelNumber);
				}
			}
			catch (NumberFormatException nfe) {
				throw new GradleException(
					"Invalid version property value", nfe);
			}

			normalizedTargetPlatformVersion = sb.toString();
		}

		return normalizedTargetPlatformVersion;
	}

	private static final Pattern _dxpVersionPattern = Pattern.compile(
		"^[0-9]\\.[0-9]\\.[1-9][0-9](\\.(fp)?[0-9]+(-[0-9]+)?)?$");
	private static final Pattern _externalVersionPattern = Pattern.compile(
		"([0-9]+)\\.([0-9]+)-([A-Za-z]+)([0-9]+)");

}