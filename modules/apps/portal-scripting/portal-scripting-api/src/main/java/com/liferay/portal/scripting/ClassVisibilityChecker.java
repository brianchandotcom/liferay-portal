/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public class ClassVisibilityChecker {

	public static final String ALL_CLASSES = "all_classes";

	public ClassVisibilityChecker(
		Set<String> allowedClassNames, Set<String> forbiddenClassNames) {

		_forbiddenClassNames = forbiddenClassNames;

		if ((allowedClassNames != null) &&
			allowedClassNames.contains(ALL_CLASSES)) {

			_allowAll = true;
		}
		else {
			_allowAll = false;
		}

		if (_forbiddenClassNames.contains(ALL_CLASSES)) {
			_denyAll = true;
		}
		else {
			_denyAll = false;
		}

		if (!_allowAll && !_denyAll) {
			_allowedPatterns = new HashSet<>();

			for (String allowedClassName : allowedClassNames) {
				Pattern allowedPattern = Pattern.compile(allowedClassName);

				_allowedPatterns.add(allowedPattern);
			}
		}
		else {
			_allowedPatterns = null;
		}
	}

	public boolean isVisible(String className) {
		if (_denyAll || _forbiddenClassNames.contains(className)) {
			return false;
		}

		if (_allowAll) {
			return true;
		}

		for (Pattern allowedPattern : _allowedPatterns) {
			Matcher matcher = allowedPattern.matcher(className);

			if (matcher.find()) {
				return true;
			}
		}

		return false;
	}

	private final boolean _allowAll;
	private final Set<Pattern> _allowedPatterns;
	private final boolean _denyAll;
	private final Set<String> _forbiddenClassNames;

}