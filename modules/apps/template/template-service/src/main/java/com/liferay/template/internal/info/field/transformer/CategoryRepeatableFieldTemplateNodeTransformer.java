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
import com.liferay.info.type.categorization.Category;
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
	immediate = true,
	property = "info.field.type.class.name=com.liferay.info.field.type.CategoriesInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class CategoryRepeatableFieldTemplateNodeTransformer
	extends BaseRepeatableFieldTemplateNodeTransformer<Category> {

	@Override
	protected UnsafeFunction<Category, TemplateNode, Exception>
		getTransformUnsafeFunction(
			InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField<?> infoField = infoFieldValue.getInfoField();

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		return category -> new TemplateNode(
			themeDisplay, infoField.getName(),
			category.getLabel(themeDisplay.getLocale()),
			infoFieldType.getName(),
			HashMapBuilder.put(
				"key", category.getKey()
			).put(
				"label", category.getLabel(themeDisplay.getLocale())
			).build());
	}

}