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