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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=checkbox",
	service = {
		CheckboxDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class CheckboxDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"predefinedValue",
			GetterUtil.getBoolean(
				getPredefinedValue(ddmFormField, ddmFormFieldRenderingContext))
		).put(
			"showAsSwitcher",
			GetterUtil.getBoolean(ddmFormField.getProperty("showAsSwitcher"))
		).put(
			"showMaximumRepetitionsInfo",
			GetterUtil.getBoolean(
				ddmFormField.getProperty("showMaximumRepetitionsInfo"))
		).put(
			"systemSettingsURL",
			() -> {
				if (!GetterUtil.getBoolean(
						ddmFormField.getProperty(
							"showMaximumRepetitionsInfo"))) {

					return StringPool.BLANK;
				}

				return _getSystemSettingsURL(
					ddmFormFieldRenderingContext.getHttpServletRequest());
			}
		).put(
			"value",
			GetterUtil.getBoolean(ddmFormFieldRenderingContext.getValue())
		).build();
	}

	protected String getPredefinedValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		if (predefinedValue == null) {
			return null;
		}

		return predefinedValue.getString(
			ddmFormFieldRenderingContext.getLocale());
	}

	private String _getSystemSettingsURL(
		HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL portletURL = requestBackedPortletURLFactory.createActionURL(
			ConfigurationAdminPortletKeys.SYSTEM_SETTINGS);

		portletURL.setParameter(
			"factoryPid",
			"com.liferay.dynamic.data.mapping.form.web.internal." +
				"configuration.DDMFormWebConfiguration");
		portletURL.setParameter(
			"mvcRenderCommandName", "/configuration_admin/edit_configuration");

		return portletURL.toString();
	}

}