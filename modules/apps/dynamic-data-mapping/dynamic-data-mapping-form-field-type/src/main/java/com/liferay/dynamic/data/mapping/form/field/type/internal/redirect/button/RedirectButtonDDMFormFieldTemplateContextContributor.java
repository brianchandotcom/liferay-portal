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

package com.liferay.dynamic.data.mapping.form.field.type.internal.redirect.button;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.REDIRECT_BUTTON,
	service = DDMFormFieldTemplateContextContributor.class
)
public class RedirectButtonDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"buttonLabel",
			DDMFormFieldTypeUtil.getPropertyValues(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"buttonLabel")[0]
		).put(
			"message",
			() -> {
				String message = GetterUtil.getString(
					ddmFormField.getProperty("message"));

				if (!message.isEmpty()) {
					return message;
				}

				return _language.format(
					ddmFormFieldRenderingContext.getLocale(),
					GetterUtil.getString(
						((Object[])ddmFormField.getProperty("messageKey"))[0]),
					(Object[])ddmFormField.getProperty("messageArguments"));
			}
		).put(
			"redirectURL",
			() -> {
				String redirectURL = GetterUtil.getString(
					ddmFormField.getProperty("redirectURL"));

				if (!redirectURL.isEmpty()) {
					return redirectURL;
				}

				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(
						ddmFormFieldRenderingContext.getHttpServletRequest());

				return PortletURLBuilder.create(
					requestBackedPortletURLFactory.createActionURL(
						GetterUtil.getString(
							((Object[])ddmFormField.getProperty("portletId"))
								[0]))
				).setParameters(
					HashMapBuilder.put(
						"mvcRenderCommandName",
						new String[] {
							GetterUtil.getString(
								((Object[])ddmFormField.getProperty(
									"mvcRenderCommandName"))[0])
						}
					).putAll(
						Stream.of(
							(Object[])ddmFormField.getProperty("parameters")
						).map(
							String.class::cast
						).map(
							parameter -> parameter.split(StringPool.EQUAL)
						).collect(
							Collectors.toMap(
								keyValue -> keyValue[0],
								keyValue -> new String[] {keyValue[1]})
						)
					).build()
				).buildString();
			}
		).put(
			"title",
			DDMFormFieldTypeUtil.getPropertyValues(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(), "title")
				[0]
		).build();
	}

	@Reference
	private Language _language;

}