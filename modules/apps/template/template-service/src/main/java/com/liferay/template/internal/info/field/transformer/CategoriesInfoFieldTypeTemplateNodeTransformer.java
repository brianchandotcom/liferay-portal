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

package com.liferay.template.internal.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.template.info.field.transformer.BaseRepeatableFieldTemplateNodeTransformer;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "info.field.type.class.name=com.liferay.info.field.type.CategoriesInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class CategoriesInfoFieldTypeTemplateNodeTransformer
	extends BaseRepeatableFieldTemplateNodeTransformer<KeyLocalizedLabelPair> {

	@Override
	protected UnsafeFunction<KeyLocalizedLabelPair, TemplateNode, Exception>
		getTransformUnsafeFunction(
			InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		return keyLocalizedLabelPair -> new TemplateNode(
			themeDisplay, infoField.getName(),
			keyLocalizedLabelPair.getLabel(themeDisplay.getLocale()),
			infoFieldType.getName(),
			HashMapBuilder.put(
				"key", keyLocalizedLabelPair.getKey()
			).put(
				"label",
				keyLocalizedLabelPair.getLabel(themeDisplay.getLocale())
			).build());
	}

}