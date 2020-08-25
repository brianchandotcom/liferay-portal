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

package com.liferay.portal.vulcan.internal.param.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;

/**
 * @author Ivica Cardic
 */
public class DateParamConverter implements ParamConverter<Date> {

	@Override
	public Date fromString(String string) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			_getPattern(string));

		try {
			return simpleDateFormat.parse(string);
		}
		catch (ParseException pe) {
			throw new WebApplicationException(pe);
		}
	}

	@Override
	public String toString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			_PATTERN_DATE_TIME);

		return simpleDateFormat.format(date);
	}

	private String _getPattern(String string) {
		if (string.contains("T")) {
			if (string.contains(".")) {
				return _PATTERN_DATE_TIME;
			}

			return _PATTERN_DATE_TIME_WITHOUT_MILLIS;
		}

		return _PATTERN_DATE;
	}

	private static final String _PATTERN_DATE = "yyyy-MM-dd";

	private static final String _PATTERN_DATE_TIME =
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private static final String _PATTERN_DATE_TIME_WITHOUT_MILLIS =
		"yyyy-MM-dd'T'HH:mm:ss'Z'";

}