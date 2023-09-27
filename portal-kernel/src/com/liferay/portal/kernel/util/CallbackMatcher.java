/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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