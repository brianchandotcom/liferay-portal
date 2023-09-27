/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 * @author Máté Thurzó
 */
public class PortalSimpleDateFormat extends SimpleDateFormat {

	public PortalSimpleDateFormat(String pattern, Locale locale) {
		super(pattern, locale);

		if (pattern.equals(DateUtil.ISO_8601_PATTERN)) {
			_iso8601Pattern = true;
		}
		else {
			_iso8601Pattern = false;
		}
	}

	@Override
	public StringBuffer format(
		Date date, StringBuffer toAppendToSB, FieldPosition fieldPosition) {

		StringBuffer originalSB = super.format(
			date, toAppendToSB, fieldPosition);

		if (!_iso8601Pattern) {
			return originalSB;
		}

		StringBuffer modifiedSB = new StringBuffer();

		modifiedSB.append(originalSB.substring(0, 22));
		modifiedSB.append(StringPool.COLON);
		modifiedSB.append(originalSB.substring(22));

		return modifiedSB;
	}

	private final boolean _iso8601Pattern;

}