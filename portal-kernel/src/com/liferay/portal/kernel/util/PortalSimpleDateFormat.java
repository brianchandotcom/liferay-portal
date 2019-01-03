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