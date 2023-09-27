/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.provider;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemFieldValuesProvider<T> {

	public default InfoFieldValue<Object> getInfoFieldValue(
		T t, String fieldName) {

		InfoItemFieldValues infoItemFieldValues = getInfoItemFieldValues(t);

		return infoItemFieldValues.getInfoFieldValue(fieldName);
	}

	public InfoItemFieldValues getInfoItemFieldValues(T t);

}