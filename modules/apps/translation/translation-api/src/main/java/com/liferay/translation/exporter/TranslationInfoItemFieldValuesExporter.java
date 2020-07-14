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