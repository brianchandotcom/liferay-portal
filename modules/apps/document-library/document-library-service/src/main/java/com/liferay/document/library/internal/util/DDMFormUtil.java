/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class DDMFormUtil {

	public static DDMForm buildDDMForm(Set<String> fieldNames, Locale locale) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(Collections.singleton(locale));
		ddmForm.setDefaultLocale(locale);

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (String name : fieldNames) {
			DDMFormField ddmFormField = new DDMFormField(name, "text");

			ddmFormField.setDataType("string");
			ddmFormField.setIndexType("text");
			ddmFormField.setLocalizable(false);
			ddmFormField.setMultiple(false);
			ddmFormField.setReadOnly(false);
			ddmFormField.setRepeatable(false);
			ddmFormField.setRequired(false);
			ddmFormField.setShowLabel(true);

			LocalizedValue label = ddmFormField.getLabel();

			label.addString(
				locale,
				"metadata.".concat(
					StringUtil.replaceFirst(
						name, CharPool.UNDERLINE, CharPool.PERIOD)));
			label.setDefaultLocale(locale);

			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			predefinedValue.addString(locale, StringPool.BLANK);
			predefinedValue.setDefaultLocale(locale);

			LocalizedValue style = ddmFormField.getStyle();

			style.setDefaultLocale(locale);

			LocalizedValue tip = ddmFormField.getTip();

			tip.setDefaultLocale(locale);

			DDMFormFieldOptions ddmFormFieldOptions =
				ddmFormField.getDDMFormFieldOptions();

			ddmFormFieldOptions.setDefaultLocale(locale);

			ddmFormFields.add(ddmFormField);
		}

		ddmForm.setDDMFormFields(ddmFormFields);

		return ddmForm;
	}

}