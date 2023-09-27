/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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