/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public boolean matchesAll(String... inputs) {
		for (String input : inputs) {
			if (matches(input) == null) {
				return false;
			}
		}

		return true;
	}

	private final List<Pattern> _patterns;

}