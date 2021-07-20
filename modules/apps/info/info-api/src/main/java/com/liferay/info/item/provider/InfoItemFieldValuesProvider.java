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

	/**
	 *   @deprecated As the method name does not match the interface class
	 *   that is returned , replaced by {@link
	 *          #getInfoFieldValue(Object, String)} ()}
	 */
	@Deprecated
	public default InfoFieldValue<Object> getInfoItemFieldValue(
		T t, String fieldName) {

		return getInfoFieldValue(t, fieldName);
	}

	public InfoItemFieldValues getInfoItemFieldValues(T t);

}