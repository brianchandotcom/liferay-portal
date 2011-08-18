/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class CMISParameterValueUtil {

	public static String formatParameterValue(String field, String value) {
		return formatParameterValue(field, value, false);
	}

	public static String formatParameterValue(
		String field, String value, boolean wildcard) {

		if (Field.CREATE_DATE.equals(field)) {
			try {
				Date createDate = _searchFormat.parse(value);

				value = _cmisFormat.format(createDate);
			}
			catch (ParseException e) {
			}

			return value;
		}
		else {
			value = StringUtil.replace(value, StringPool.APOSTROPHE, "\\'");
			value = StringUtil.replace(value, StringPool.UNDERLINE, "\\_");

			if (wildcard) {
				value = StringUtil.replace(value, StringPool.PERCENT, "\\%");
				value = StringPool.PERCENT + value + StringPool.PERCENT;
			}

			return value;
		}
	}

	private static String _CMIS_TIMESTAMP_PATTERN =
		"yyyy-MM-dd'T'HH:mm:ss.sss'Z'";

	private static DateFormat _cmisFormat;
	private static DateFormat _searchFormat;

	static {
		_cmisFormat =
			(DateFormat) DateFormatFactoryUtil.getSimpleDateFormat(
				_CMIS_TIMESTAMP_PATTERN);
		_searchFormat =
			(DateFormat) DateFormatFactoryUtil.getSimpleDateFormat(
				PropsValues.INDEX_DATE_FORMAT_PATTERN);
	}

}