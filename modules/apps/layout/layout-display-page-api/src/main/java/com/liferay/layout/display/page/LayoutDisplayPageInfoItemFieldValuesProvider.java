/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import com.liferay.info.item.InfoItemFieldValues;

/**
 * @author Lourdes Fernández Besada
 */
public interface LayoutDisplayPageInfoItemFieldValuesProvider<T> {

	public String getClassName();

	public InfoItemFieldValues getInfoItemFieldValues(long classPK);

	public InfoItemFieldValues getInfoItemFieldValues(T t);

}