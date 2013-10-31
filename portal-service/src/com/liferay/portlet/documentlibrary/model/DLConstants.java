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

	public static boolean isValidName(String filename) {
		if (Validator.isNull(filename)) {
			return false;
		}

		for (String blacklistName : DL_NAME_BLACKLIST) {
			String filenamePrefix = filename;

			if (filename.contains(StringPool.PERIOD)) {
				int pos = filename.lastIndexOf(StringPool.PERIOD);

				filenamePrefix = filename.substring(0, pos);
			}

			if (StringUtil.equalsIgnoreCase(
					filenamePrefix, blacklistName)) {

				return false;
			}
		}

		Matcher matcher = DL_CHAR_BLACKLIST_REGEXP.matcher(filename);

		return matcher.matches();
	}

	private static final Pattern DL_CHAR_BLACKLIST_REGEXP =
		Pattern.compile(
			PropsUtil.get(PropsKeys.DL_CHAR_BLACKLIST_REGEXP),
			Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

	private static final String[] DL_NAME_BLACKLIST = PropsUtil.getArray(
		PropsKeys.DL_NAME_BLACKLIST);

}