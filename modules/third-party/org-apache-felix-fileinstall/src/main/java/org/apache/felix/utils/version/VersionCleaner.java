/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.utils.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionCleaner {

	/**
	 * Clean up version parameters. Other builders use more fuzzy definitions of
	 * the version syntax. This method cleans up such a version to match an OSGi
	 * version.
	 *
	 * @param version The version to clean
	 * @return The cleaned version
	 */
	public static String clean(String version) {
		if ((version == null) || (version.length() == 0)) {
			return "0.0.0";
		}

		String clean = _fastSyntax(version);

		if (clean != null) {
			return clean;
		}

		StringBuffer result = new StringBuffer();

		Matcher matcher = _fuzzyVersion.matcher(version);

		if (matcher.matches()) {
			String major = matcher.group(1);
			String minor = matcher.group(3);
			String micro = matcher.group(5);
			String qualifier = matcher.group(7);

			if (major != null) {
				result.append(major);

				if (minor != null) {
					result.append(".");
					result.append(minor);

					if (micro != null) {
						result.append(".");
						result.append(micro);

						if (qualifier != null) {
							result.append(".");

							_cleanupModifier(result, qualifier);
						}
					}
					else if (qualifier != null) {
						result.append(".0.");

						_cleanupModifier(result, qualifier);
					}
					else {
						result.append(".0");
					}
				}
				else if (qualifier != null) {
					result.append(".0.0.");

					_cleanupModifier(result, qualifier);
				}
				else {
					result.append(".0.0");
				}
			}
		}
		else {
			result.append("0.0.0.");

			_cleanupModifier(result, version);
		}

		return result.toString();
	}

	private static void _cleanupModifier(StringBuffer result, String modifier) {
		for (int i = 0; i < modifier.length(); i++) {
			char c = modifier.charAt(i);

			if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) ||
				((c >= 'A') && (c <= 'Z')) || (c == '_') || (c == '-')) {

				result.append(c);
			}
			else {
				result.append('_');
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
				if (c == '.') {
					state++;
				}
				else if ((c < '0') || (c > '9')) {
					return null;
				}
			}
			else if (state == 6) {
				if (c == '.') {
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
/* @generated */