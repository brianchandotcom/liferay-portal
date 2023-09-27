/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class CMISParameterValueUtil {

	public static String formatParameterValue(String field, String value) {
		return formatParameterValue(field, value, false, null);
	}

	public static String formatParameterValue(
		String field, String value, boolean wildcard) {

		return formatParameterValue(field, value, wildcard, null);
	}

	public static String formatParameterValue(
		String field, String value, boolean wildcard, QueryConfig queryConfig) {

		if (field.equals(Field.CREATE_DATE) ||
			field.equals(Field.MODIFIED_DATE)) {

			try {
				DateFormat searchSimpleDateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						_INDEX_DATE_FORMAT_PATTERN);

				Date date = searchSimpleDateFormat.parse(value);

				DateFormat cmisSimpleDateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.000'Z'");

				value = cmisSimpleDateFormat.format(date);
			}
			catch (ParseException parseException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to parse date ", value, " for field ",
							field),
						parseException);
				}
			}
		}
		else {
			String productName = StringPool.BLANK;

			if (queryConfig != null) {
				productName = (String)queryConfig.getAttribute(
					"repositoryProductName");
			}

			if (Validator.isNotNull(productName) &&
				productName.startsWith("Alfresco")) {

				value = StringUtil.replace(value, CharPool.APOSTROPHE, "\\'");
			}
			else {
				value = StringUtil.replace(
					value, new char[] {CharPool.APOSTROPHE, CharPool.UNDERLINE},
					new String[] {"\\'", "\\_"});
			}

			if (wildcard) {
				value = StringUtil.replace(
					value, new char[] {CharPool.PERCENT, CharPool.STAR},
					new String[] {"\\%", StringPool.PERCENT});
			}
		}

		return value;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static final Log _log = LogFactoryUtil.getLog(
		CMISParameterValueUtil.class);

}