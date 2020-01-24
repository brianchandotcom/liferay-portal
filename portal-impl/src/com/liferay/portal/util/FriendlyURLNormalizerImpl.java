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

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Normalizer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@OSGiBeanProperties(property = "service.ranking:Integer=100")
public class FriendlyURLNormalizerImpl implements FriendlyURLNormalizer {

	@Override
	public String normalize(String friendlyURL) {
		return normalize(friendlyURL, false);
	}

	@Override
	public String normalizeWithEncoding(String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		String decodedFriendlyURL = HttpUtil.decodePath(friendlyURL);

		if (Validator.isNull(decodedFriendlyURL)) {
			decodedFriendlyURL = HttpUtil.decodePath(
				StringUtil.replace(
					friendlyURL, CharPool.PERCENT, CharPool.DASH));
		}

		StringBuilder sb = new StringBuilder(decodedFriendlyURL.length());

		boolean modified = false;

		ByteBuffer byteBuffer = null;
		CharBuffer charBuffer = null;

		CharsetEncoder charsetEncoder = null;

		for (int i = 0; i < decodedFriendlyURL.length(); i++) {
			char c = decodedFriendlyURL.charAt(i);

			if ((CharPool.UPPER_CASE_A <= c) && (c <= CharPool.UPPER_CASE_Z)) {
				sb.append((char)(c + 32));

				modified = true;
			}
			else if (((CharPool.LOWER_CASE_A <= c) &&
					  (c <= CharPool.LOWER_CASE_Z)) ||
					 ((CharPool.NUMBER_0 <= c) && (c <= CharPool.NUMBER_9)) ||
					 (c == CharPool.PERIOD) || (c == CharPool.SLASH) ||
					 (c == CharPool.STAR) || (c == CharPool.UNDERLINE)) {

				sb.append(c);
			}
			else if (Arrays.binarySearch(_REPLACE_CHARS, c) >= 0) {
				if ((i == 0) || (CharPool.DASH != sb.charAt(sb.length() - 1))) {
					sb.append(CharPool.DASH);

					if (c != CharPool.DASH) {
						modified = true;
					}
				}
				else {
					modified = true;
				}
			}
			else {
				if (charsetEncoder == null) {
					charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
						StringPool.UTF8);

					byteBuffer = ByteBuffer.allocate(4);
					charBuffer = CharBuffer.allocate(2);
				}
				else {
					byteBuffer.clear();
					charBuffer.clear();
				}

				charBuffer.put(c);

				boolean endOfInput = false;

				if ((decodedFriendlyURL.length() - 1) == i) {
					endOfInput = true;
				}

				if (Character.isHighSurrogate(c) &&
					((i + 1) < decodedFriendlyURL.length())) {

					c = decodedFriendlyURL.charAt(i + 1);

					if (Character.isLowSurrogate(c)) {
						charBuffer.put(c);

						i++;
					}
					else {
						endOfInput = true;
					}
				}

				charBuffer.flip();

				charsetEncoder.encode(charBuffer, byteBuffer, endOfInput);

				byteBuffer.flip();

				while (byteBuffer.hasRemaining()) {
					byte b = byteBuffer.get();

					sb.append(CharPool.PERCENT);
					sb.append(_HEX_DIGITS[(b >> 4) & 0x0F]);
					sb.append(_HEX_DIGITS[b & 0x0F]);
				}

				if (endOfInput) {
					charsetEncoder.flush(byteBuffer);

					charsetEncoder.reset();
				}

				modified = true;
			}
		}

		if (modified) {
			return sb.toString();
		}

		return friendlyURL;
	}

	@Override
	public String normalizeWithPeriodsAndSlashes(String friendlyURL) {
		return normalize(friendlyURL, true);
	}

	protected String normalize(String friendlyURL, boolean periodsAndSlashes) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = Normalizer.normalizeToAscii(friendlyURL);

		StringBuilder sb = new StringBuilder(friendlyURL.length());

		boolean modified = false;

		for (int i = 0; i < friendlyURL.length(); i++) {
			char c = friendlyURL.charAt(i);

			if ((CharPool.UPPER_CASE_A <= c) && (c <= CharPool.UPPER_CASE_Z)) {
				sb.append((char)(c + 32));

				modified = true;
			}
			else if (((CharPool.LOWER_CASE_A <= c) &&
					  (c <= CharPool.LOWER_CASE_Z)) ||
					 ((CharPool.NUMBER_0 <= c) && (c <= CharPool.NUMBER_9)) ||
					 (c == CharPool.UNDERLINE) ||
					 (!periodsAndSlashes &&
					  ((c == CharPool.SLASH) || (c == CharPool.PERIOD)))) {

				sb.append(c);
			}
			else {
				if ((i == 0) || (CharPool.DASH != sb.charAt(sb.length() - 1))) {
					sb.append(CharPool.DASH);

					if (c != CharPool.DASH) {
						modified = true;
					}
				}
				else {
					modified = true;
				}
			}
		}

		if (modified) {
			return sb.toString();
		}

		return friendlyURL;
	}

	private static final char[] _HEX_DIGITS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
		'E', 'F'
	};

	private static final char[] _REPLACE_CHARS;

	static {
		char[] replaceChars = {
			'-', ' ', ',', '\\', '\'', '\"', '(', ')', '[', ']', '{', '}', '?',
			'#', '@', '+', '~', ';', '$', '!', '=', ':', '&', '\u00a3',
			'\u2013', '\u2014', '\u2018', '\u2019', '\u201c', '\u201d'
		};

		Arrays.sort(replaceChars);

		_REPLACE_CHARS = replaceChars;
	}

}