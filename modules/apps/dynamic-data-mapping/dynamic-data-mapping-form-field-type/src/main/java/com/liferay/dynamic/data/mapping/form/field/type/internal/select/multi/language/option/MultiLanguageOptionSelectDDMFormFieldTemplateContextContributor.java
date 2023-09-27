/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select.multi.language.option;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.MULTI_LANGUAGE_OPTION_SELECT,
	service = DDMFormFieldTemplateContextContributor.class
)
public class MultiLanguageOptionSelectDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Set<Locale> availableLocales = _language.getAvailableLocales();

		Map<String, Object> parameters =
			_selectDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		for (Map<String, Object> option :
				(List<Map<String, Object>>)parameters.get("options")) {

			HashMap<String, String> labelsMap = new HashMap<>();

			option.put("label", labelsMap);

			for (Locale availableLocale : availableLocales) {
				labelsMap.put(
					_language.getLanguageId(availableLocale),
					_language.get(
						ResourceBundleUtil.getModuleAndPortalResourceBundle(
							availableLocale, getClass()),
						(String)option.get("value")));
			}
		}

		return parameters;
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SELECT + ")"
	)
	private DDMFormFieldTemplateContextContributor
		_selectDDMFormFieldTemplateContextContributor;

}