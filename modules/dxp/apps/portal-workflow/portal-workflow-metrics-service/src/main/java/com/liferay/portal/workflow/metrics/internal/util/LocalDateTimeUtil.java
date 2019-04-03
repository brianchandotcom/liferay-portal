/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.util;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Rafael Praxedes
 */
public class LocalDateTimeUtil {

	public static DateTimeFormatter getDateTimeFormatter() {
		if (_dateTimeFormatter == null) {
			_dateTimeFormatter = DateTimeFormatter.ofPattern(
				_INDEX_DATE_FORMAT_PATTERN);
		}

		return _dateTimeFormatter;
	}

	public static LocalDateTime max(
		LocalDateTime localDateTime1, LocalDateTime localDateTime2) {

		if (localDateTime1 == null) {
			return localDateTime2;
		}

		if (localDateTime2 == null) {
			return localDateTime1;
		}

		if (localDateTime1.isAfter(localDateTime2)) {
			return localDateTime1;
		}

		return localDateTime2;
	}

	public static LocalDateTime min(
		LocalDateTime localDateTime1, LocalDateTime localDateTime2) {

		if (localDateTime1 == null) {
			return localDateTime2;
		}

		if (localDateTime2 == null) {
			return localDateTime1;
		}

		if (localDateTime1.isBefore(localDateTime2)) {
			return localDateTime1;
		}

		return localDateTime2;
	}

	public static LocalDateTime toLocalDateTime(
		Document document, String fieldName) {

		String dateString = document.getString(fieldName);

		if (Validator.isNull(dateString)) {
			return null;
		}

		return LocalDateTime.parse(dateString, getDateTimeFormatter());
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static DateTimeFormatter _dateTimeFormatter;

}