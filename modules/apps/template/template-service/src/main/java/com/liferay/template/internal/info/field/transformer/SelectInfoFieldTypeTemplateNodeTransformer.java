/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.field.transformer;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
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

		JSONArray selectedOptionValuesJSONArray =
			_getSelectedOptionValuesJSONArray(
				infoFieldValue, themeDisplay.getLocale());

		if (!JSONUtil.isEmpty(selectedOptionValuesJSONArray)) {
			stringValue = selectedOptionValuesJSONArray.getString(0);
		}

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		TemplateNode templateNode = new TemplateNode(
			themeDisplay, infoField.getName(), stringValue,
			infoFieldType.getName(),
			HashMapBuilder.put(
				"multiple", Boolean.FALSE.toString()
			).build());

		List<OptionInfoFieldType> optionInfoFieldTypes =
			(List<OptionInfoFieldType>)infoField.getAttribute(
				SelectInfoFieldType.OPTIONS);

		if (optionInfoFieldTypes == null) {
			optionInfoFieldTypes = Collections.emptyList();
		}

		for (OptionInfoFieldType optionInfoFieldType : optionInfoFieldTypes) {
			templateNode.appendOptionMap(
				optionInfoFieldType.getValue(),
				optionInfoFieldType.getLabel(themeDisplay.getLocale()));
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