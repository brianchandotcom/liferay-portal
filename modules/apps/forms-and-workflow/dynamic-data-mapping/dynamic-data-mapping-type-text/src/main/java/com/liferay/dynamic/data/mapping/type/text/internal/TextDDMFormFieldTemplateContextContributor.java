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

package com.liferay.dynamic.data.mapping.type.text.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContextContributor;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=text",
	service = {
		TextDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class TextDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		parameters.put(
			"displayStyle",
			GetterUtil.getString(
				ddmFormField.getProperty("displayStyle"), "singleline"));

		LocalizedValue placeholder = (LocalizedValue)ddmFormField.getProperty(
			"placeholder");

		Locale locale = ddmFormFieldRenderingContext.getLocale();

		parameters.put("placeholder", getValueString(placeholder, locale));

		LocalizedValue tooltip = (LocalizedValue)ddmFormField.getProperty(
			"tooltip");

		parameters.put("tooltip", getValueString(tooltip, locale));

		List<Object> options = getOptions(
			ddmFormField, ddmFormFieldRenderingContext);

		parameters.put("options", options);
		parameters.put("autocomplete", !options.isEmpty());

		return parameters;
	}

	protected void addDDMDataProviderContextParameters(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		DDMDataProviderContext ddmDataProviderContext,
		List<DDMDataProviderContextContributor>
			ddmDataProviderContextContributors) {

		HttpServletRequest request =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		for (DDMDataProviderContextContributor
				ddmDataProviderContextContributor :
					ddmDataProviderContextContributors) {

			Map<String, String> parameters =
				ddmDataProviderContextContributor.getParameters(request);

			if (parameters == null) {
				continue;
			}

			ddmDataProviderContext.addParameters(parameters);
		}

		ddmDataProviderContext.addParameter(
			"filterParameterValue",
			String.valueOf(ddmFormFieldRenderingContext.getValue()));
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		String dataSourceType = GetterUtil.getString(
			ddmFormField.getProperty("dataSourceType"), "manual");

		if (Objects.equals(dataSourceType, "data-provider")) {
			ddmFormFieldOptions.setDefaultLocale(
				ddmFormFieldRenderingContext.getLocale());

			try {
				String ddmDataProviderInstanceId = GetterUtil.getString(
					ddmFormField.getProperty("ddmDataProviderInstanceId"));

				DDMDataProvider ddmDataProvider =
					ddmDataProviderTracker.getDDMDataProviderByInstanceId(
						ddmDataProviderInstanceId);

				DDMDataProviderContext ddmDataProviderContext = null;

				if (ddmDataProvider != null) {
					ddmDataProviderContext = new DDMDataProviderContext(null);
				}
				else {
					DDMDataProviderInstance ddmDataProviderInstance =
						ddmDataProviderInstanceService.getDataProviderInstance(
							Long.valueOf(ddmDataProviderInstanceId));

					ddmDataProvider = ddmDataProviderTracker.getDDMDataProvider(
						ddmDataProviderInstance.getType());

					DDMForm ddmForm = DDMFormFactory.create(
						ddmDataProvider.getSettings());

					DDMFormValues ddmFormValues =
						ddmFormValuesJSONDeserializer.deserialize(
							ddmForm, ddmDataProviderInstance.getDefinition());

					ddmDataProviderContext = new DDMDataProviderContext(
						ddmFormValues);

					List<DDMDataProviderContextContributor>
						ddmDataProviderContextContributors =
					ddmDataProviderTracker.
					getDDMDataProviderContextContributors(
						ddmDataProviderInstance.getType());

					addDDMDataProviderContextParameters(
						ddmFormFieldRenderingContext, ddmDataProviderContext,
						ddmDataProviderContextContributors);
				}

				ddmDataProviderContext.setHttpServletRequest(
					ddmFormFieldRenderingContext.getHttpServletRequest());

				DDMDataProviderRequest ddmDataProviderRequest =
					new DDMDataProviderRequest(ddmDataProviderContext);

				DDMDataProviderResponse ddmDataProviderResponse =
					ddmDataProvider.getData(ddmDataProviderRequest);

				for (Map<Object, Object> map :
						ddmDataProviderResponse.getData()) {

					for (Entry<Object, Object> entry : map.entrySet()) {
						ddmFormFieldOptions.addOptionLabel(
							String.valueOf(entry.getValue()),
							ddmFormFieldRenderingContext.getLocale(),
							String.valueOf(entry.getKey()));
					}
				}

				return ddmFormFieldOptions;
			}
			catch (PortalException pe) {
				_log.error("Unable to fetch data provider data", pe);
			}
		}
		else {
			List<Map<String, String>> keyValuePairs =
				(List<Map<String, String>>)
					ddmFormFieldRenderingContext.getProperty("options");

			if (keyValuePairs.isEmpty()) {
				return ddmFormField.getDDMFormFieldOptions();
			}

			for (Map<String, String> keyValuePair : keyValuePairs) {
				ddmFormFieldOptions.addOptionLabel(
					keyValuePair.get("value"),
					ddmFormFieldRenderingContext.getLocale(),
					keyValuePair.get("label"));
			}
		}

		return ddmFormFieldOptions;
	}

	protected List<Object> getOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		TextDDMFormFieldContextHelper selectDDMFormFieldContextHelper =
			new TextDDMFormFieldContextHelper(
				getDDMFormFieldOptions(
					ddmFormField, ddmFormFieldRenderingContext),
				ddmFormFieldRenderingContext.getLocale());

		return selectDDMFormFieldContextHelper.getOptions();
	}

	protected String getValueString(Value value, Locale locale) {
		if (value != null) {
			return value.getString(locale);
		}

		return StringPool.BLANK;
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		TextDDMFormFieldTemplateContextContributor.class);

	@Reference
	private DDMDataProviderTracker ddmDataProviderTracker;

	@Reference
	private DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer;

}