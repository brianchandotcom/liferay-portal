/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.text.FieldPosition;

import java.util.Date;
import java.util.Locale;

/**
 * @author Mate Thurzo
 */
public class SimpleDateFormat extends java.text.SimpleDateFormat {

	public SimpleDateFormat(String pattern, Locale locale) {
		super(pattern, locale);

		if (pattern.equals(DateUtil.ISO_8601_PATTERN)) {
			_iso8601Format = true;
		}
	}

	@Override
	public StringBuffer format(
		Date date, StringBuffer toAppendTo, FieldPosition pos) {

		StringBuffer originalFormat = super.format(date, toAppendTo, pos);

		StringBuffer sb = new StringBuffer();

		sb.append(originalFormat.substring(0, 22));
		sb.append(StringPool.COLON);
		sb.append(originalFormat.substring(22));

		return sb;
	}

	private boolean _iso8601Format = false;

}