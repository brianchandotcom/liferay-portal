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

package com.liferay.apio.architect.date;

import com.liferay.apio.architect.functional.Try;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 * Provides functions for transforming dates to/from ISO-8061 date strings.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class DateTransformer {

	/**
	 * Tries to convert an ISO-8061 date string to a {@code Date} object.
	 *
	 * @param  string an ISO-8061 date string
	 * @return a {@code Success} containing {@code Date} if the string was a
	 *         valid ISO-8061 date string; a {@code Failure} otherwise
	 * @review
	 */
	public static Try<Date> asDate(String string) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

		dateFormat.setTimeZone(timeZone);

		return Try.fromFallible(() -> dateFormat.parse(string));
	}

	/**
	 * Converts an {@code Date} object to an ISO-8061 date string string.
	 *
	 * @param  date a date
	 * @return the {@code Date} in the form of an ISO-8061 date string
	 * @review
	 */
	public static String asString(Date date) {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

		dateFormat.setTimeZone(timeZone);

		return dateFormat.format(date);
	}

	private DateTransformer() {
		throw new UnsupportedOperationException();
	}

}