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

package com.liferay.document.library.internal.util;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class MimeTypeSizeLimitUtil {

	public static <E extends Throwable> void parseMimeTypeSizeLimit(
			String mimeTypeSizeLimit,
			UnsafeBiConsumer<String, Long, E> unsafeBiConsumer)
		throws E {

		String[] parts = StringUtil.split(mimeTypeSizeLimit, CharPool.COLON);

		if (parts.length != 2) {
			unsafeBiConsumer.accept(null, null);
		}
		else {
			unsafeBiConsumer.accept(
				_parseMimeTypeName(StringUtil.trim(parts[0])),
				_parseSizeLimit(StringUtil.trim(parts[1])));
		}
	}

	private static String _parseMimeTypeName(String mimeType) {
		String[] parts = StringUtil.split(mimeType, CharPool.SLASH);

		if (parts.length != 2) {
			return null;
		}

		Matcher typeMatcher = _pattern.matcher(parts[0]);

		if (!typeMatcher.matches()) {
			return null;
		}

		Matcher subtypeMatcher = _pattern.matcher(parts[1]);

		if (!subtypeMatcher.matches()) {
			return null;
		}

		return mimeType;
	}

	private static Long _parseSizeLimit(String sizeLimit) {
		try {
			long value = Long.parseLong(sizeLimit);

			if (value < 0) {
				return null;
			}

			return value;
		}
		catch (NumberFormatException numberFormatException) {
			return null;
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"[a-zA-Z0-9][a-zA-Z0-9$!#&^_-]*");

}