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

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringPool;

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