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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;

import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author Iván Zaera
 */
public class SharepointConstants {

	public static final int BATCH_METHOD_ID_DEFAULT = 0;

	public static final String FS_OBJ_TYPE_FILE = "0";

	public static final String FS_OBJ_TYPE_FOLDER = "1";

	public static final String NUMERIC_STATUS_SUCCESS = "0x00000000";

	public static final String PATTERN_MULTI_VALUE_SEPARATOR = Pattern.quote(
		";#");

	public static final String ROW_LIMIT_DEFAULT = StringPool.BLANK;

	public static final String SHAREPOINT_OBJECT_DATE_FORMAT_PATTERN =
		"yyyy-MM-dd HH:mm:ss";

	public static final TimeZone SHAREPOINT_OBJECT_TIME_ZONE =
		TimeZone.getTimeZone("UTC");

	public static final String SYMBOLIC_STATUS_NO_RESULTS_FOUND =
		"ERROR_NO_RESULTS_FOUND";

	public static final String SYMBOLIC_STATUS_SUCCESS = "SUCCESS";

	public static final String URL_SOURCE_NONE = StringPool.SPACE;

	public static final String VIEW_DEFAULT = StringPool.BLANK;

	public static final String WEB_ID_DEFAULT = StringPool.BLANK;

}