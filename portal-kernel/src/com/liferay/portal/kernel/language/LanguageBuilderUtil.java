/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.language;

/**
 * @author Tina Tian
 */
public class LanguageBuilderUtil {

	public static final String AUTOMATIC_COPY = " (Automatic Copy)";

	public static final String AUTOMATIC_TRANSLATION =
		" (Automatic Translation)";

	public static String fixValue(String value) {
		if (value.endsWith(AUTOMATIC_COPY)) {
			value = value.substring(
				0, value.length() - AUTOMATIC_COPY.length());
		}

		if (value.endsWith(AUTOMATIC_TRANSLATION)) {
			value = value.substring(
				0, value.length() - AUTOMATIC_TRANSLATION.length());
		}

		return value;
	}

}