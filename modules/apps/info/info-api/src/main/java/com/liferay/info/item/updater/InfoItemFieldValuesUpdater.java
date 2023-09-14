/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.updater;

import com.liferay.info.item.InfoItemFieldValues;

/**
 * @author Alicia García
 */
public interface InfoItemFieldValuesUpdater<T> {

	public T updateFromInfoItemFieldValues(
			T t, InfoItemFieldValues infoItemFieldValues)
		throws Exception;

	public default T updateFromInfoItemFieldValues(
			T t, InfoItemFieldValues infoItemFieldValues, int status)
		throws Exception {

		return updateFromInfoItemFieldValues(t, infoItemFieldValues);
	}

}