/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.emailaddress;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldTemplateContextContributorUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueUtil;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nathaly Gomes
 */
@Component(
	property = "ddm.form.field.type.name=" + ObjectDDMFormFieldTypeConstants.EMAIL_ADDRESS,
	service = DDMFormFieldTemplateContextContributor.class
)
public class EmailAddressDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMForm ddmForm = ddmFormField.getDDMForm();
		boolean localizedObjectField = GetterUtil.getBoolean(
			ddmFormField.getProperty("localizedObjectField"));

		return HashMapBuilder.<String, Object>put(
			ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS,
			GetterUtil.getString(
				ddmFormField.getProperty(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_DOMAINS))
		).put(
			ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED,
			GetterUtil.getBoolean(
				ddmFormField.getProperty(
					ObjectFieldSettingConstants.NAME_AUTOCOMPLETE_ENABLED))
		).put(
			ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS,
			GetterUtil.getString(
				ddmFormField.getProperty(
					ObjectFieldSettingConstants.NAME_BLOCKED_DOMAINS))
		).put(
			"localizedObjectField", localizedObjectField
		).put(
			"predefinedValue",
			() -> {
				LocalizedValue predefinedValue =
					(LocalizedValue)ddmFormField.getProperty("predefinedValue");

				if (predefinedValue == null) {
					return StringPool.BLANK;
				}

				return GetterUtil.getString(
					predefinedValue.getString(
						ddmFormFieldRenderingContext.getLocale()));
			}
		).put(
			"value",
			() -> {
				if (localizedObjectField) {
					return DDMFormFieldValueUtil.getValueJSONObject(
						ddmFormFieldRenderingContext);
				}

				return GetterUtil.getString(
					ddmFormFieldRenderingContext.getValue());
			}
		).putAll(
			DDMFormFieldTemplateContextContributorUtil.
				getLocalizationParameters(
					ddmFormField, ddmForm.getDefaultLocale())
		).build();
	}

}