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
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.template.info.field.transformer.BaseTemplateNodeTransformer;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "info.field.type.class.name=com.liferay.info.field.type.SelectInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class SelectInfoFieldTypeTemplateNodeTransformer
	extends BaseTemplateNodeTransformer {

	@Override
	public TemplateNode transform(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField infoField = infoFieldValue.getInfoField();

		String stringValue = StringPool.BLANK;

		Optional<Boolean> multipleOptional = infoField.getAttributeOptional(
			SelectInfoFieldType.MULTIPLE);

		Boolean multiple = multipleOptional.orElse(false);

		JSONArray selectedOptionValuesJSONArray =
			_getSelectedOptionValuesJSONArray(
				infoFieldValue, themeDisplay.getLocale());

		if (multiple) {
			stringValue = JSONUtil.toString(selectedOptionValuesJSONArray);
		}
		else if (!JSONUtil.isEmpty(selectedOptionValuesJSONArray)) {
			stringValue = selectedOptionValuesJSONArray.getString(0);
		}

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		TemplateNode templateNode = new TemplateNode(
			themeDisplay, infoField.getName(), stringValue,
			infoFieldType.getName(),
			HashMapBuilder.put(
				"multiple", String.valueOf(multiple)
			).build());

		Optional<List<SelectInfoFieldType.Option>> optionsOptional =
			infoField.getAttributeOptional(SelectInfoFieldType.OPTIONS);

		for (SelectInfoFieldType.Option option :
				optionsOptional.orElse(Collections.emptyList())) {

			templateNode.appendOptionMap(
				option.getValue(), option.getLabel(themeDisplay.getLocale()));
		}

		return templateNode;
	}

	private JSONArray _getSelectedOptionValuesJSONArray(
		InfoFieldValue<Object> infoFieldValue, Locale locale) {

		Object value = infoFieldValue.getValue(locale);

		if (!(value instanceof List)) {
			return _jsonFactory.createJSONArray();
		}

		JSONArray selectedOptionValuesJSONArray =
			_jsonFactory.createJSONArray();

		List<KeyLocalizedLabelPair> keyLocalizedLabelPairs =
			(List<KeyLocalizedLabelPair>)value;

		for (KeyLocalizedLabelPair keyLocalizedLabelPair :
				keyLocalizedLabelPairs) {

			selectedOptionValuesJSONArray.put(keyLocalizedLabelPair.getKey());
		}

		return selectedOptionValuesJSONArray;
	}

	@Reference
	private JSONFactory _jsonFactory;

}