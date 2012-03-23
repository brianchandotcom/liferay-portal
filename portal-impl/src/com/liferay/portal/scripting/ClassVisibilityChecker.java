/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

		if (_forbiddenClasses.contains(ALL_CLASSES)) {
			_denyAll = true;
		}

		if (!_allowAll && !_denyAll) {
			_allowedPatterns = new HashSet<Pattern>();

			for (String allowedClass : allowedClasses) {
				Pattern allowedPattern = Pattern.compile(allowedClass);

				_allowedPatterns.add(allowedPattern);
			}
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

	private static Set<String> _forbiddenClasses = new HashSet<String>(
		Arrays.asList(PropsValues.SCRIPTING_FORBIDDEN_CLASSES));

	private boolean _allowAll;
	private Set<Pattern> _allowedPatterns;
	private boolean _denyAll;

}