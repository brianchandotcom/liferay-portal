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

package com.liferay.portal.kernel.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Alexander Chow
 */
public class UserGroupConstants {

	public static final long DEFAULT_PARENT_USER_GROUP_ID = 0;

	public static final String NAME_INVALID_CHARACTERS =
		StringPool.COMMA + StringPool.SPACE + StringPool.STAR;

	public static final String NAME_LABEL = "user-group-name";

	public static final String NAME_RESERVED_WORDS = StringPool.NULL;

	public static String getNameGeneralRestrictions(
		Locale locale, boolean allowNumeric) {

		String nameGeneralRestrictions = StringUtil.toLowerCase(
			LanguageUtil.get(locale, "blank"));

		if (!allowNumeric) {
			nameGeneralRestrictions +=
				StringPool.COMMA_AND_SPACE +
					StringUtil.toLowerCase(LanguageUtil.get(locale, "numeric"));
		}

		return nameGeneralRestrictions;
	}

}