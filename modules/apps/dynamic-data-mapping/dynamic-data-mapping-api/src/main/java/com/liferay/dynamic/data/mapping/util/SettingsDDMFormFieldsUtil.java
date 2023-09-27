/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;

import java.util.Map;

/**
 * @author Marcos Martins
 */
public class SettingsDDMFormFieldsUtil {

	public static Map<String, DDMFormField> getSettingsDDMFormFields(
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		String type) {

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldType(type);

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		DDMForm ddmForm = DDMFormFactory.create(ddmFormFieldTypeSettings);

		return ddmForm.getDDMFormFieldsMap(true);
	}

}