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
			_getDateFormat(string));

		try {
			return simpleDateFormat.parse(string);
		}
		catch (ParseException pe) {
			throw new WebApplicationException(pe);
		}
	}

	@Override
	public String toString(Date date) {
		return new SimpleDateFormat(
			_DATE_TIME_FORMAT
		).format(
			date
		);
	}

	private String _getDateFormat(String string) {
		if (string.contains("T")) {
			return _DATE_TIME_FORMAT;
		}

		return _DATE_FORMAT;
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd";

	private static final String _DATE_TIME_FORMAT =
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

}