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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Normalizer;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class FriendlyURLNormalizerImpl implements FriendlyURLNormalizer {

	@Override
	public String normalize(String friendlyURL) {
		return normalize(friendlyURL, false, true);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String normalize(String friendlyURL, Pattern friendlyURLPattern) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = StringUtil.toLowerCase(friendlyURL);
		friendlyURL = Normalizer.normalizeToAscii(friendlyURL);

		Matcher matcher = friendlyURLPattern.matcher(friendlyURL);

		friendlyURL = matcher.replaceAll(StringPool.DASH);

		StringBuilder sb = new StringBuilder(friendlyURL.length());

		for (int i = 0; i < friendlyURL.length(); i++) {
			char c = friendlyURL.charAt(i);

			if (c == CharPool.DASH) {
				if ((i == 0) || (CharPool.DASH != sb.charAt(sb.length() - 1))) {
					sb.append(CharPool.DASH);
				}
			}
			else {
				sb.append(c);
			}
		}

		if (sb.length() == friendlyURL.length()) {
			return friendlyURL;
		}

		return sb.toString();
	}

	@Override
	public String normalizeWithEncoding(String friendlyURL) {
		return normalize(friendlyURL, false, false);
	}

	@Override
	public String normalizeWithPeriodsAndSlashes(String friendlyURL) {
		return normalize(friendlyURL, true, true);
	}

	protected String normalize(String friendlyURL, boolean periodsAndSlashes) {
		return normalize(friendlyURL, periodsAndSlashes, true);
	}

	protected String normalize(
		String friendlyURL, boolean periodsAndSlashes,
		boolean normalizeToAscii) {

		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = StringUtil.toLowerCase(friendlyURL);

		if (normalizeToAscii) {
			friendlyURL = Normalizer.normalizeToAscii(friendlyURL);
		}

		StringBuilder sb = new StringBuilder(friendlyURL.length());

		boolean modified = false;

		for (int i = 0; i < friendlyURL.length(); i++) {
			char c = friendlyURL.charAt(i);

			if (((CharPool.LOWER_CASE_A <= c) &&
				 (c <= CharPool.LOWER_CASE_Z)) ||
				((CharPool.NUMBER_0 <= c) && (c <= CharPool.NUMBER_9)) ||
				(c == CharPool.UNDERLINE) ||
				(!normalizeToAscii && (CharPool.PERCENT == c))) {

				sb.append(c);
			}
			else if (!periodsAndSlashes &&
					 ((c == CharPool.SLASH) || (c == CharPool.PERIOD))) {

				sb.append(c);
			}
			else if (normalizeToAscii ||
					 ArrayUtil.contains(_REPLACE_CHARS, c)) {

				if ((i == 0) || (CharPool.DASH != sb.charAt(sb.length() - 1))) {
					sb.append(CharPool.DASH);
				}

				modified = true;
			}
			else {
				try {
					sb.append(URLEncoder.encode(String.valueOf(c), "UTF-8"));

					modified = true;
				}
				catch (UnsupportedEncodingException uee) {
					if (_log.isInfoEnabled()) {
						_log.info(uee, uee);
					}
				}
			}
		}

		if (modified) {
			return sb.toString();
		}

		return friendlyURL;
	}

	private static final char[] _REPLACE_CHARS;

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLNormalizerImpl.class);

	static {
		char[] replaceChars = new char[] {
			' ', ',', '\\', '\'', '\"', '(', ')', '[', ']', '{', '}', '?', '#',
			'@', '+', '~', ';', '$', '!', '=', ':', '&', '\u00a3', '\u2013',
			'\u2014', '\u2018', '\u2019', '\u201c', '\u201d'
		};

		Arrays.sort(replaceChars);

		_REPLACE_CHARS = replaceChars;
	}

}