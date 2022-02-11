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

package com.liferay.content.dashboard.web.internal.info.item.provider.util;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Cristina González
 */
public class InfoItemFieldValuesProviderUtil {

	public static <T> String getStringValue(
		T infoItem, InfoItemFieldValuesProvider<T> infoItemFieldValuesProvider,
		String infoFieldName) {

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(infoItem);

		return Optional.ofNullable(
			infoItemFieldValues.getInfoFieldValue(infoFieldName)
		).map(
			InfoFieldValue::getValue
		).orElse(
			StringPool.BLANK
		).toString();
	}

	public static <T> String getStringValue(
		T infoItem, InfoItemFieldValuesProvider<T> infoItemFieldValuesProvider,
		String infoFieldName, Locale locale) {

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(infoItem);

		return Optional.ofNullable(
			infoItemFieldValues.getInfoFieldValue(infoFieldName)
		).map(
			infoFieldValue -> infoFieldValue.getValue(locale)
		).orElse(
			StringPool.BLANK
		).toString();
	}

}