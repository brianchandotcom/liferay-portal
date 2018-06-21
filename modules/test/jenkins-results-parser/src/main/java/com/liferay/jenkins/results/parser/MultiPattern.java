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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class MultiPattern {

	public MultiPattern(String... patternStrings) {
		_patterns = new ArrayList<>(patternStrings.length);

		for (String patternString : patternStrings) {
			_patterns.add(Pattern.compile(patternString));
		}
	}

	public Matcher find(String input) {
		for (Pattern pattern : _patterns) {
			Matcher matcher = pattern.matcher(input);

			if (matcher.find()) {
				return matcher;
			}
		}

		return null;
	}

	public int getSize() {
		return _patterns.size();
	}

	public int indexOf(Pattern pattern) {
		return _patterns.indexOf(pattern);
	}

	public Matcher matches(String input) {
		for (Pattern pattern : _patterns) {
			Matcher matcher = pattern.matcher(input);

			if (matcher.matches()) {
				return matcher;
			}
		}

		return null;
	}

	private final List<Pattern> _patterns;

}