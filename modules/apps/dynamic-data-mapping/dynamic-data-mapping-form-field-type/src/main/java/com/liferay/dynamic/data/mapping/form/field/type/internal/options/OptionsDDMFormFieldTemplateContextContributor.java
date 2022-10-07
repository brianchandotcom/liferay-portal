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

package com.liferay.dynamic.data.mapping.form.field.type.internal.options;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.options.helper.OptionsDDMFormFieldContextHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.OPTIONS,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		OptionsDDMFormFieldTemplateContextContributor.class
	}
)
public class OptionsDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"allowEmptyOptions",
			GetterUtil.getBoolean(ddmFormField.getProperty("allowEmptyOptions"))
		).put(
			"allowSpecialCharacters",
			() -> {
				if (Objects.equals(
						ddmFormFieldRenderingContext.getPortletNamespace(),
						_portal.getPortletNamespace(
							DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN))) {

					return false;
				}

				return true;
			}
		).put(
			"defaultLanguageId",
			() -> {
				DDMForm ddmForm = ddmFormField.getDDMForm();

				return LocaleUtil.toLanguageId(ddmForm.getDefaultLocale());
			}
		).put(
			"value", _getValue(ddmFormField, ddmFormFieldRenderingContext)
		).build();
	}

	private Map<String, Object> _getValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		OptionsDDMFormFieldContextHelper optionsDDMFormFieldContextHelper =
			new OptionsDDMFormFieldContextHelper(
				_jsonFactory, ddmFormField, ddmFormFieldRenderingContext);

		return optionsDDMFormFieldContextHelper.getValue();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}