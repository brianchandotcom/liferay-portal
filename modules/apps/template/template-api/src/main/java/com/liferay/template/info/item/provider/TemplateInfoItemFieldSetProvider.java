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

package com.liferay.template.info.item.provider;

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public interface TemplateInfoItemFieldSetProvider {

	public default InfoFieldSet getInfoFieldSet(String infoItemClassName) {
		return getInfoFieldSet(infoItemClassName, StringPool.BLANK);
	}

	public InfoFieldSet getInfoFieldSet(
		String infoItemClassName, String infoItemFormVariationKey);

	public default List<InfoFieldValue<Object>> getInfoFieldValues(
		String infoItemClassName, Object itemObject) {

		return getInfoFieldValues(
			infoItemClassName, StringPool.BLANK, itemObject);
	}

	public List<InfoFieldValue<Object>> getInfoFieldValues(
		String infoItemClassName, String infoItemFormVariationKey,
		Object itemObject);

}