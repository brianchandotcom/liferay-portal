/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Alicia García
 */
public class DepotEntryConstants {

	public static final String NAME_INVALID_CHARACTERS = StringPool.STAR;

	public static final String NAME_LABEL = "asset-library-name";

	public static final String NAME_RESERVED_WORDS = StringPool.NULL;

	public static String getNameGeneralRestrictions(Locale locale) {
		return StringUtil.toLowerCase(LanguageUtil.get(locale, "blank")) +
			StringPool.COMMA_AND_SPACE +
				StringUtil.toLowerCase(LanguageUtil.get(locale, "numeric"));
	}

}