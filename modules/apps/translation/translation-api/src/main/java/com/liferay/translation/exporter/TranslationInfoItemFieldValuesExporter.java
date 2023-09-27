/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.exporter;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface TranslationInfoItemFieldValuesExporter {

	public InputStream exportInfoItemFieldValues(
			InfoItemFieldValues infoItemFieldValues, Locale sourceLocale,
			Locale targetLocale)
		throws IOException;

	public default InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
		return InfoLocalizedValue.localize(getClass(), getMimeType());
	}

	public String getMimeType();

}