/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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