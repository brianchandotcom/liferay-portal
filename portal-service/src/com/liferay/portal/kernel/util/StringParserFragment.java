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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class StringParserFragment {

	public StringParserFragment(String chunk) {
		chunk = chunk.substring(1, chunk.length() - 1);

		if (Validator.isNull(chunk)) {
			throw new IllegalArgumentException("Fragment is null");
		}

		String[] chunkParts = chunk.split(StringPool.COLON, 2);

		if (chunkParts.length == 2) {
			_name = chunkParts[0];
			String pattern = chunkParts[1];

			if (Validator.isNull(pattern)) {
				throw new IllegalArgumentException("Pattern is null");
			}

			_pattern = Pattern.compile(pattern);
		}
		else {
			_name = chunkParts[0];
			_pattern = _defaultPattern;
		}

		if (Validator.isNull(_name)) {
			throw new IllegalArgumentException("Name is null");
		}

		if (_name.startsWith(StringPool.PERCENT)) {
			_name = _name.substring(1);

			if (Validator.isNull(_name)) {
				throw new IllegalArgumentException("Name is null");
			}

			_raw = true;
		}

		_token = StringPool.OPEN_CURLY_BRACE.concat(_name).concat(
			StringPool.CLOSE_CURLY_BRACE);
	}

	public String getName() {
		return _name;
	}

	public String getPattern() {
		return _pattern.toString();
	}

	public String getToken() {
		return _token;
	}

	public boolean isRaw() {
		return _raw;
	}

	public boolean matches(String parameter) {
		Matcher matcher = _pattern.matcher(parameter);

		return matcher.matches();
	}

	private static final Pattern _defaultPattern = Pattern.compile("[^/\\.]+");

	private final String _name;
	private final Pattern _pattern;
	private final boolean _raw;
	private final String _token;

}