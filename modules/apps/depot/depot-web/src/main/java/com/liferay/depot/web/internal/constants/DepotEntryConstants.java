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