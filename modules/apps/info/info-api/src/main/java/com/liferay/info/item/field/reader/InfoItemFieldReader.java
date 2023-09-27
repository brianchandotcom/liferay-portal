/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.field.reader;

import com.liferay.info.field.InfoField;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public interface InfoItemFieldReader<T> {

	public default InfoField getInfoField() {
		return null;
	}

	public default String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	public Object getValue(T model);

}