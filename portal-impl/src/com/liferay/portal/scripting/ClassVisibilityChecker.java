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

package com.liferay.portal.scripting;

import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
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

	public ClassVisibilityChecker(Set<String> allowedClasses) {
		if ((allowedClasses != null) && allowedClasses.contains(ALL_CLASSES)) {
			_allowAll = true;
		}
		else {
			_allowAll = false;
		}

		if (_forbiddenClasses.contains(ALL_CLASSES)) {
			_denyAll = true;
		}
		else {
			_denyAll = false;
		}

		if (!_allowAll && !_denyAll) {
			_allowedPatterns = new HashSet<>();

			for (String allowedClass : allowedClasses) {
				Pattern allowedPattern = Pattern.compile(allowedClass);

				_allowedPatterns.add(allowedPattern);
			}
		}
		else {
			_allowedPatterns = null;
		}
	}

	public boolean isVisible(String className) {
		if (_denyAll || _forbiddenClasses.contains(className)) {
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

	private static final Set<String> _forbiddenClasses = new HashSet<>(
		Arrays.asList(PropsValues.SCRIPTING_FORBIDDEN_CLASSES));

	private final boolean _allowAll;
	private final Set<Pattern> _allowedPatterns;
	private final boolean _denyAll;

}