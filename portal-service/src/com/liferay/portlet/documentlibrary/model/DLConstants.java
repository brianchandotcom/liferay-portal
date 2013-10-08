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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Everest Liu
 */
public class DLConstants {

	public static boolean isValidName(String fileName) {

		if (Validator.isNull(fileName)) {
			return false;
		}

		for (String word : DL_NAME_BLACKLIST) {
			String fileNameWithoutExtension = fileName;

			if (fileName.contains(StringPool.PERIOD)) {
				int lastPeriodPos = fileName.lastIndexOf(StringPool.PERIOD);
				fileNameWithoutExtension = fileName.substring(0, lastPeriodPos);
			}

			if (StringUtil.equalsIgnoreCase(fileNameWithoutExtension, word)) {
				return false;
			}
		}

		Matcher matcher = DL_CHAR_BLACKLIST_REGEXP.matcher(fileName);

		return matcher.matches();
	}

	private static final String[] DL_NAME_BLACKLIST = PropsUtil.get(
			PropsKeys.DL_NAME_BLACKLIST).split(",");

	private static final Pattern DL_CHAR_BLACKLIST_REGEXP =
		Pattern.compile(
			PropsUtil.get(PropsKeys.DL_CHAR_BLACKLIST_REGEXP),
			Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
}