/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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