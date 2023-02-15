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

package com.liferay.segments.asah.connector.internal.expression.parser.test.util;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Cristina González
 */
public class IndividualSegmentsExpressionUtil {

	public static String getFilter(
		String activityKey, String operator, String value) {

		return String.format(_FILTER, activityKey, operator, value);
	}

	public static String getFilterByCount(
		String filter, String operator, int value) {

		return String.format(
			_FILTER_BY_COUNT, StringUtil.replace(filter, '\'', "''"), operator,
			value);
	}

	private static final String _FILTER = "activityKey eq '%s' and day %s '%s'";

	private static final String _FILTER_BY_COUNT =
		"activities.filterByCount(filter='(%s)',operator='%s',value=%d)";

}