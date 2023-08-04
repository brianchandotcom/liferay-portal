/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Víctor Galán
 */
public class DateTimeInfoFieldType implements InfoFieldType {

	public static final DateTimeInfoFieldType INSTANCE =
		new DateTimeInfoFieldType();

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "date-and-time");
	}

	@Override
	public String getName() {
		return "date-time";
	}

	private DateTimeInfoFieldType() {
	}

}