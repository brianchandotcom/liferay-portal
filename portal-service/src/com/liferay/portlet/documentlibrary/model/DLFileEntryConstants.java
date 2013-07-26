/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Samuel Kong
 */
public class DLFileEntryConstants {

	public static final int DEFAULT_READ_COUNT = 0;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		PropsUtil.get("lock.expiration.time." + getClassName()));

	public static final String PRIVATE_WORKING_COPY_VERSION = "PWC";

	public static final String VERSION_DEFAULT = "1.0";

	public static String getClassName() {
		return DLFileEntry.class.getName();
	}

	public static boolean isValidName(String fileName) {
		Matcher matcher = DL_FILE_VALID_NAME_REGEXP.matcher(fileName);

		return matcher.matches();
	}

	private static final Pattern DL_FILE_VALID_NAME_REGEXP =
		Pattern.compile(
			PropsUtil.get(PropsKeys.DL_FILE_VALID_NAME_REGEXP),
			Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

}