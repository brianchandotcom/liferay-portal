/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.phone.number;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldTemplateContextContributorUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "ddm.form.field.type.name=" + ObjectDDMFormFieldTypeConstants.PHONE_NUMBER,
	service = DDMFormFieldTemplateContextContributor.class
)
public class PhoneNumberDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMForm ddmForm = ddmFormField.getDDMForm();
		boolean localizedObjectField = GetterUtil.getBoolean(
			ddmFormField.getProperty("localizedObjectField"));
		ObjectFieldBusinessType phoneNumberObjectFieldBusinessType =
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_PHONE_NUMBER);

		return HashMapBuilder.<String, Object>put(
			"country", GetterUtil.getString(ddmFormField.getProperty("country"))
		).put(
			"countrySource",
			GetterUtil.getString(ddmFormField.getProperty("countrySource"))
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
		).putAll(
			phoneNumberObjectFieldBusinessType.getRenderingProperties()
		).build();
	}

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

}