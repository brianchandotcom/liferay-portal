/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Danny Situ
 */
public class CPDefinitionOptionValueRelInfoItemFields {

	public static final InfoField<NumberInfoFieldType>
		cpDefinitionOptionValueRelIdInfoField =
			BuilderHolder._builder.infoFieldType(
				NumberInfoFieldType.INSTANCE
			).name(
				"cpDefinitionOptionValueRelId"
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(
					CPDefinitionOptionValueRelInfoItemFields.class,
					"cpDefinitionOptionValueRelId")
			).build();
	public static final InfoField<TextInfoFieldType> keyInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"key"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionOptionValueRelInfoItemFields.class, "key")
		).build();
	public static final InfoField<TextInfoFieldType> nameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"name"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CPDefinitionOptionValueRelInfoItemFields.class, "name")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(CPDefinitionOptionValueRel.class.getSimpleName());

	}

}