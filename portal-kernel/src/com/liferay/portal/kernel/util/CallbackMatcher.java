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

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6872.
 * </p>
 *
 * @author Jonathan Potter
 */
public class CallbackMatcher {

	public String replaceMatches(CharSequence charSequence, Callback callback) {
		Matcher matcher = _pattern.matcher(charSequence);

		StringBuilder sb = new StringBuilder(charSequence);

		int offset = 0;

		while (matcher.find()) {
			MatchResult matchResult = matcher.toMatchResult();

			String replacement = callback.foundMatch(matchResult);

			if (replacement == null) {
				continue;
			}

			int matchStart = offset + matchResult.start();
			int matchEnd = offset + matchResult.end();

			sb.replace(matchStart, matchEnd, replacement);

			int matchLength = matchResult.end() - matchResult.start();

			int lengthChange = replacement.length() - matchLength;

			offset += lengthChange;
		}

		return sb.toString();
	}

	public void setRegex(String regex) {
		_pattern = Pattern.compile(regex);
	}

	public interface Callback {

		public String foundMatch(MatchResult matchResult);

	}

	private Pattern _pattern;

}