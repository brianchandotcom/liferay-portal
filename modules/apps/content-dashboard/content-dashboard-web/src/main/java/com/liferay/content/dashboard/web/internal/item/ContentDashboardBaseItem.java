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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

/**
 * @author Yurena Cabrera
 */
public abstract class ContentDashboardBaseItem<T>
	implements ContentDashboardItem<T> {

	@Override
	public String getDescription(Locale locale) {
		InfoItemFieldValuesProvider infoItemFieldValuesProvider =
			getInfoItemFieldValuesProvider();

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(getInfoItem());

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue("description");

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		Object description = infoFieldValue.getValue();

		return description.toString();
	}

	public abstract T getInfoItem();

	public abstract InfoItemFieldValuesProvider
		getInfoItemFieldValuesProvider();

}