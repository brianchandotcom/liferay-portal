/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;
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

		if (Objects.equals(parts[1], StringPool.STAR)) {
			return mimeType;
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
			if (_log.isDebugEnabled()) {
				_log.debug(numberFormatException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MimeTypeSizeLimitUtil.class);

	private static final Pattern _pattern = Pattern.compile(
		"[a-zA-Z0-9][a-zA-Z0-9$!#&.,;=^_+-]*");

}