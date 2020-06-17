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

package com.liferay.dynamic.data.mapping.web.internal.info.field.converter;

import com.liferay.dynamic.data.mapping.info.field.converter.DDMFormFieldInfoFieldConverter;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.IntegerInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = DDMFormFieldInfoFieldConverter.class)
public class DDMFormFieldInfoFieldConverterImpl
	implements DDMFormFieldInfoFieldConverter {

	@Override
	public InfoField convert(DDMFormField ddmFormField) {
		LocalizedValue label = ddmFormField.getLabel();

		InfoLocalizedValue<String> labelInfoLocalizedValue =
			InfoLocalizedValue.builder(
			).addValues(
				label.getValues()
			).defaultLocale(
				label.getDefaultLocale()
			).build();

		return new InfoField(
			_getInfoFieldType(ddmFormField), labelInfoLocalizedValue,
			ddmFormField.isLocalizable(), ddmFormField.getName());
	}

	private InfoFieldType _getInfoFieldType(DDMFormField ddmFormField) {
		String ddmFormFieldType = ddmFormField.getType();

		if (Objects.equals(ddmFormFieldType, "ddm-image") ||
			Objects.equals(ddmFormFieldType, "image")) {

			return ImageInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(ddmFormFieldType, DDMFormFieldType.CHECKBOX)) {
			return BooleanInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(ddmFormFieldType, DDMFormFieldType.INTEGER)) {
			return IntegerInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

}