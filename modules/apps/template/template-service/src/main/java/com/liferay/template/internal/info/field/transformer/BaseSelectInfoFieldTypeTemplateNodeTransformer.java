/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.template.info.field.transformer.BaseTemplateNodeTransformer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
public abstract class BaseSelectInfoFieldTypeTemplateNodeTransformer
	extends BaseTemplateNodeTransformer {

	@Override
	public TemplateNode transform(
		InfoFieldValue<Object> infoFieldValue, ThemeDisplay themeDisplay) {

		InfoField infoField = infoFieldValue.getInfoField();

		List<OptionInfoFieldType> optionInfoFieldTypes =
			getOptionInfoFieldTypes(infoField);

		if (optionInfoFieldTypes == null) {
			optionInfoFieldTypes = Collections.emptyList();
		}

		Map<String, String> optionsMap = new LinkedHashMap<>();

		for (OptionInfoFieldType optionInfoFieldType : optionInfoFieldTypes) {
			optionsMap.put(
				optionInfoFieldType.getValue(),
				optionInfoFieldType.getLabel(themeDisplay.getLocale()));
		}

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		JSONArray selectedOptionValuesJSONArray =
			getSelectedOptionValuesJSONArray(
				infoFieldValue, themeDisplay.getLocale());

		String data = getData(optionsMap, selectedOptionValuesJSONArray);

		TemplateNode templateNode = new TemplateNode(
			themeDisplay, infoField.getName(), data, infoFieldType.getName(),
			getAttributes());

		templateNode.appendOptionsMap(optionsMap);

		templateNode.put("key", getKey(selectedOptionValuesJSONArray));
		templateNode.put(
			"label", getLabel(optionsMap, selectedOptionValuesJSONArray));

		return templateNode;
	}

	protected abstract Map<String, String> getAttributes();

	protected String getData(
		Map<String, String> optionsMap,
		JSONArray selectedOptionValuesJSONArray) {

		return getKey(selectedOptionValuesJSONArray);
	}

	protected abstract String getKey(JSONArray selectedOptionValuesJSONArray);

	protected abstract String getLabel(
		Map<String, String> optionsMap,
		JSONArray selectedOptionValuesJSONArray);

	protected abstract List<OptionInfoFieldType> getOptionInfoFieldTypes(
		InfoField infoField);

	protected JSONArray getSelectedOptionValuesJSONArray(
		InfoFieldValue<Object> infoFieldValue, Locale locale) {

		Object value = infoFieldValue.getValue(locale);

		if (!(value instanceof List)) {
			return jsonFactory.createJSONArray();
		}

		JSONArray selectedOptionValuesJSONArray = jsonFactory.createJSONArray();

		List<KeyLocalizedLabelPair> keyLocalizedLabelPairs =
			(List<KeyLocalizedLabelPair>)value;

		for (KeyLocalizedLabelPair keyLocalizedLabelPair :
				keyLocalizedLabelPairs) {

			selectedOptionValuesJSONArray.put(keyLocalizedLabelPair.getKey());
		}

		return selectedOptionValuesJSONArray;
	}

	@Reference
	protected JSONFactory jsonFactory;

}