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

package com.liferay.portal.file.install.internal.version;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 */
public final class VersionCleaner {

	public static String clean(String version) {
		if ((version == null) || (version.length() == 0)) {
			return "0.0.0";
		}

		String clean = _fastSyntax(version);

		if (clean != null) {
			return clean;
		}

		StringBundler sb = new StringBundler();

		Matcher matcher = _fuzzyVersion.matcher(version);

		if (matcher.matches()) {
			String major = matcher.group(1);
			String minor = matcher.group(3);
			String micro = matcher.group(5);
			String qualifier = matcher.group(7);

			if (major != null) {
				sb.append(major);

				if (minor != null) {
					sb.append(StringPool.PERIOD);
					sb.append(minor);

					if (micro != null) {
						sb.append(StringPool.PERIOD);
						sb.append(micro);

						if (qualifier != null) {
							sb.append(StringPool.PERIOD);

							_cleanupModifier(sb, qualifier);
						}
					}
					else if (qualifier != null) {
						sb.append(".0.");

						_cleanupModifier(sb, qualifier);
					}
					else {
						sb.append(".0");
					}
				}
				else if (qualifier != null) {
					sb.append(".0.0.");

					_cleanupModifier(sb, qualifier);
				}
				else {
					sb.append(".0.0");
				}
			}
		}
		else {
			sb.append("0.0.0.");

			_cleanupModifier(sb, version);
		}

		return sb.toString();
	}

	private static void _cleanupModifier(StringBundler sb, String modifier) {
		for (int i = 0; i < modifier.length(); i++) {
			char c = modifier.charAt(i);

			if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) ||
				((c >= 'A') && (c <= 'Z')) || (c == CharPool.UNDERLINE) ||
				(c == CharPool.DASH)) {

				sb.append(c);
			}
			else {
				sb.append('_');
			}
		}
	}

	private static String _fastSyntax(String version) {
		int state = 0;

		for (int i = 0, l = version.length(); i < l; i++) {
			char c = version.charAt(i);

			if ((state == 0) || (state == 2) || (state == 4)) {
				if ((c < '0') || (c > '9')) {
					return null;
				}

				state++;
			}
			else if ((state == 1) || (state == 3) || (state == 5)) {
				if (c == CharPool.PERIOD) {
					state++;
				}
				else if ((c < '0') || (c > '9')) {
					return null;
				}
			}
			else if (state == 6) {
				if (c == CharPool.PERIOD) {
					return null;
				}
			}
		}

		if ((state == 0) || (state == 1)) {
			return version + ".0.0";
		}
		else if ((state == 2) || (state == 3)) {
			return version + ".0";
		}
		else {
			return version;
		}
	}

	private static final Pattern _fuzzyVersion = Pattern.compile(
		"(\\d+)(\\.(\\d+)(\\.(\\d+))?)?([^a-zA-Z0-9](.*))?", Pattern.DOTALL);

}