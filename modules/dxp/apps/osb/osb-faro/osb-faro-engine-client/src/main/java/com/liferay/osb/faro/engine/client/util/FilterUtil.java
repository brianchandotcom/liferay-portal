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

package com.liferay.osb.faro.engine.client.util;

import com.liferay.osb.faro.engine.client.constants.FilterConstants;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

/**
 * @author Matthew Kong
 */
public class FilterUtil {

	public static String getBlankFilter(String fieldName, String operator) {
		return StringBundler.concat(
			fieldName, operator, StringPool.DOUBLE_APOSTROPHE);
	}

	public static String getFieldName(
		String fieldName, String fieldNameContext) {

		if (Validator.isNull(fieldNameContext)) {
			return fieldName;
		}

		return StringUtil.replace(
			fieldNameContext, CharPool.QUESTION, fieldName);
	}

	public static String getFilter(
		String fieldName, String operator, Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			Date date = (Date)value;

			value = String.valueOf(date.toInstant());
		}
		else if (value instanceof List) {
			value = StringBundler.concat(
				StringPool.OPEN_BRACKET,
				StringUtil.merge(
					TransformUtil.transform((List<?>)value, String::valueOf),
					StringPool.COMMA),
				StringPool.CLOSE_BRACKET);
		}
		else {
			String valueString = String.valueOf(value);

			if (Validator.isBlank(valueString)) {
				return null;
			}

			value = StringUtil.quote(valueString, StringPool.APOSTROPHE);
		}

		if (FilterConstants.isStringFunction(operator)) {
			StringBundler sb = new StringBundler(6);

			sb.append(operator);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(fieldName);
			sb.append(StringPool.COMMA);
			sb.append(value);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(fieldName);
		sb.append(operator);
		sb.append(value);

		return sb.toString();
	}

	public static String getFilter(
		String fieldName, String fieldNameContext, String operator,
		String value) {

		return getFilter(
			getFieldName(fieldName, fieldNameContext), operator, value);
	}

	public static String getInterestFilter(
		String interestName, boolean interested) {

		if (Validator.isNull(interestName)) {
			return null;
		}

		StringBundler sb = new StringBundler(3);

		sb.append("interests.filter(filter='");

		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			"name", FilterConstants.COMPARISON_OPERATOR_EQUALS, interestName);
		filterBuilder.addFilter(
			"score", FilterConstants.COMPARISON_OPERATOR_EQUALS, interested);

		sb.append(filterBuilder.build());

		sb.append("')");

		return sb.toString();
	}

	public static String getNullFilter(String fieldName, String operator) {
		return operator + StringPool.NULL;
	}

	public static String negate(String filterString) {
		return "not " + filterString;
	}

}