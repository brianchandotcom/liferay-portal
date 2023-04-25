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

package com.liferay.osb.faro.web.internal.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class FaroPaginationConstants {

	public static final int CUR_DEFAULT = 1;

	public static final String ORDER_BY_TYPE_ASC = "asc";

	public static final String ORDER_BY_TYPE_DEFAULT = ORDER_BY_TYPE_ASC;

	public static final String ORDER_BY_TYPE_DESC = "desc";

	public static Map<String, Object> getConstants() {
		return _constants;
	}

	private static final Map<String, Object> _constants =
		HashMapBuilder.<String, Object>put(
			"cur", CUR_DEFAULT
		).put(
			"delta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
		).put(
			"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
		).put(
			"orderAscending", ORDER_BY_TYPE_ASC
		).put(
			"orderDefault", ORDER_BY_TYPE_DEFAULT
		).put(
			"orderDescending", ORDER_BY_TYPE_DESC
		).build();

}