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

package com.liferay.documentum.repository.search;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class DQLParameterValueUtil {

	public static String formatParameterValue(String field, String value) {
		return formatParameterValue(field, value, false);
	}

	public static String formatParameterValue(
		String field, String value, boolean wildcard) {

		if (wildcard) {
			value = StringUtil.replace(
				value,
				new String[] {
					StringPool.APOSTROPHE, StringPool.UNDERLINE,
					StringPool.PERCENT
				},
				new String[] {"\\'", "\\_", "\\%"});
		}
		else {
			value = StringUtil.replace(
				value, new String[] {StringPool.APOSTROPHE},
				new String[] {"\\'"});
		}

		if (field.equals(Field.CREATE_DATE) ||
			field.equals(Field.MODIFIED_DATE)) {

			try {
				DateFormat searchSimpleDateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						_INDEX_DATE_FORMAT_PATTERN);

				Date date = searchSimpleDateFormat.parse(value);

				DateFormat dqlSimpleDateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");

				value = dqlSimpleDateFormat.format(date);
			}
			catch (ParseException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to parse date " + value + " for field " +
							field);
				}
			}

			return "DATE('".concat(value).concat("', 'yyyy/mm/dd hh:mi:ss')");
		}
		else {
			if (wildcard) {
				value = StringUtil.replace(
					value, CharPool.STAR, CharPool.PERCENT);
			}
		}

		return value;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static final Log _log = LogFactoryUtil.getLog(
		DQLParameterValueUtil.class);

}