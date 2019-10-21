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

package com.liferay.segments.asah.connector.internal.client.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.Instant;

import java.util.Date;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class FilterUtil {

	public static String getFilter(
		String fieldName, String operator, Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			Date date = (Date)value;

			Instant instant = date.toInstant();

			value = instant.toString();
		}
		else {
			String valueString = String.valueOf(value);

			if (Validator.isBlank(valueString)) {
				return null;
			}

			value = StringUtil.quote(valueString, StringPool.APOSTROPHE);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(fieldName);
		sb.append(operator);
		sb.append(value);

		return sb.toString();
	}

	public static String getNullFilter(String fieldName, String operator) {
		return fieldName.concat(
			operator
		).concat(
			StringPool.NULL
		);
	}

	private FilterUtil() {
	}

}