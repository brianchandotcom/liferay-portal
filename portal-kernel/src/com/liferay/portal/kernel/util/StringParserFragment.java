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

package com.liferay.portal.kernel.util;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class StringParserFragment {

	public static StringParserFragment create(String chunk) {
		StringParserFragment stringParserFragment = _stringParserFragments.get(
			chunk);

		if (stringParserFragment == null) {
			stringParserFragment = new StringParserFragment(chunk);

			_stringParserFragments.put(chunk, stringParserFragment);
		}

		return stringParserFragment;
	}

	public String getName() {
		return _name;
	}

	public String getPattern() {
		return _matcher.toString();
	}

	public String getToken() {
		return _token;
	}

	public boolean isRaw() {
		return _raw;
	}

	public boolean matches(String parameter) {
		return _matcher.matches(parameter);
	}

	protected StringParserFragment(String fragment) {
		if ((fragment == null) || (fragment.length() < 3)) {
			throw new IllegalArgumentException(
				"Fragment is invalid: " + fragment);
		}

		int index = fragment.indexOf(CharPool.COLON);

		String name = null;

		if (index < 0) {
			name = fragment.substring(1, fragment.length() - 1);

			_matcher = _defaultMatcher;
		}
		else {
			name = fragment.substring(1, index);

			String pattern = fragment.substring(
				index + 1, fragment.length() - 1);

			if (Validator.isNull(pattern)) {
				throw new IllegalArgumentException(
					"Pattern is null: " + fragment);
			}

			_matcher = _getMatcher(pattern);
		}

		if (name.isEmpty()) {
			throw new IllegalArgumentException("Name is null: " + fragment);
		}

		if (name.charAt(0) == CharPool.PERCENT) {
			name = name.substring(1);

			if (name.isEmpty()) {
				throw new IllegalArgumentException(
					"Name is invalid: " + fragment);
			}

			_raw = true;
		}
		else {
			_raw = false;
		}

		_name = name;

		_token = StringPool.OPEN_CURLY_BRACE.concat(
			_name
		).concat(
			StringPool.CLOSE_CURLY_BRACE
		);
	}

	private Matcher _getMatcher(String pattern) {
		if (pattern.equals("\\d+")) {
			return new DigitMatcher();
		}

		if (pattern.equals("[^/]+")) {
			return new NotSlashMatcher();
		}

		if (pattern.equals("(?!id$)[^/]+") || pattern.equals("(?!id/)[^/]+")) {
			return new NotIdMatcher(pattern);
		}

		return new PatternMatcher(pattern);
	}

	private static final Matcher _defaultMatcher = new DefaultMatcher();
	private static final Map<String, StringParserFragment>
		_stringParserFragments = new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.SOFT_REFERENCE_FACTORY);

	private final Matcher _matcher;
	private final String _name;
	private final boolean _raw;
	private final String _token;

	private static class DefaultMatcher implements Matcher {

		@Override
		public boolean matches(String s) {
			if (s.isEmpty()) {
				return false;
			}

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);

				if ((c == CharPool.SLASH) || (c == CharPool.PERIOD)) {
					return false;
				}
			}

			return true;
		}

		@Override
		public String toString() {
			return "[^/\\.]+";
		}

	}

	private static class DigitMatcher implements Matcher {

		@Override
		public boolean matches(String s) {
			if (s.isEmpty()) {
				return false;
			}

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);

				if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
					return false;
				}
			}

			return true;
		}

		@Override
		public String toString() {
			return "\\d+";
		}

	}

	private static class NotIdMatcher implements Matcher {

		@Override
		public boolean matches(String s) {
			if (s.equals("id") || (s.indexOf(CharPool.SLASH) != -1)) {
				return false;
			}

			return true;
		}

		@Override
		public String toString() {
			return _pattern;
		}

		private NotIdMatcher(String pattern) {
			_pattern = pattern;
		}

		private final String _pattern;

	}

	private static class NotSlashMatcher implements Matcher {

		@Override
		public boolean matches(String s) {
			if (s.isEmpty()) {
				return false;
			}

			if (s.indexOf(CharPool.SLASH) == -1) {
				return true;
			}

			return false;
		}

		@Override
		public String toString() {
			return "[^/]+";
		}

	}

	private static class PatternMatcher implements Matcher {

		@Override
		public boolean matches(String s) {
			java.util.regex.Matcher matcher = _pattern.matcher(s);

			return matcher.matches();
		}

		@Override
		public String toString() {
			return _pattern.toString();
		}

		private PatternMatcher(String pattern) {
			_pattern = Pattern.compile(pattern);
		}

		private final Pattern _pattern;

	}

	private interface Matcher {

		public boolean matches(String s);

	}

}