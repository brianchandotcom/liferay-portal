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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Juan Fernández
 */
public class JournalFolderConstants {

	public static final long DEFAULT_PARENT_FOLDER_ID = 0;

	public static boolean isValidName(String name) {
		Matcher matcher = JOURNAL_FOLDER_VALID_NAME_REGEXP.matcher(name);

		return matcher.matches();
	}

	private static final Pattern JOURNAL_FOLDER_VALID_NAME_REGEXP =
		Pattern.compile(
			PropsUtil.get(PropsKeys.JOURNAL_FOLDER_VALID_NAME_REGEXP),
			Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

}