/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.util.HashMap;
import java.util.Map;

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

				Map<String, String[]> parameters = new HashMap<>();

				for (Object object :
						(Object[])ddmFormField.getProperty("parameters")) {

					String parameter = (String)object;

					String[] parameterPair = parameter.split(StringPool.EQUAL);

					parameters.put(
						parameterPair[0], new String[] {parameterPair[1]});
				}

				parameters.put(
					"mvcRenderCommandName",
					new String[] {
						GetterUtil.getString(
							((Object[])ddmFormField.getProperty(
								"mvcRenderCommandName"))[0])
					});

				return PortletURLBuilder.create(
					requestBackedPortletURLFactory.createActionURL(
						GetterUtil.getString(
							((Object[])ddmFormField.getProperty("portletId"))
								[0]))
				).setParameters(
					parameters
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