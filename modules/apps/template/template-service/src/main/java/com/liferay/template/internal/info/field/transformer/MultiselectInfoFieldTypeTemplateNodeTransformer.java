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
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.type.KeyLocalizedLabelPair;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "info.field.type.class.name=com.liferay.info.field.type.MultiselectInfoFieldType",
	service = TemplateNodeTransformer.class
)
public class MultiselectInfoFieldTypeTemplateNodeTransformer
	extends BaseTemplateNodeTransformer {

	@Override
	public TemplateNode transform(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField infoField = infoFieldValue.getInfoField();

		JSONArray selectedOptionValuesJSONArray =
			_getSelectedOptionValuesJSONArray(
				infoFieldValue, themeDisplay.getLocale());

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		TemplateNode templateNode = new TemplateNode(
			themeDisplay, infoField.getName(),
			JSONUtil.toString(selectedOptionValuesJSONArray),
			infoFieldType.getName(),
			HashMapBuilder.put(
				"multiple", Boolean.TRUE.toString()
			).build());

		List<MultiselectInfoFieldType.Option> options =
			(List<MultiselectInfoFieldType.Option>)infoField.getAttribute(
				MultiselectInfoFieldType.OPTIONS);

		if (options == null) {
			options = Collections.emptyList();
		}

		for (MultiselectInfoFieldType.Option option : options) {
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