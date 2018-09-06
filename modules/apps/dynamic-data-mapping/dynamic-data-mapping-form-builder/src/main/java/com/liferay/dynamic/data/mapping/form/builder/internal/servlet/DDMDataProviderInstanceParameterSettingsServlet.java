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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderParameterSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base.BaseDDMFormBuilderServlet;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-provider-instance-parameter-settings",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMDataProviderInstanceParameterSettingsServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-provider-instance-parameter-settings/*"
	},
	service = Servlet.class
)
public class DDMDataProviderInstanceParameterSettingsServlet
	extends BaseDDMFormBuilderServlet {

	protected JSONObject createParametersJSONObject(
			DDMDataProvider ddmDataProvider, DDMFormValues ddmFormValues)
		throws Exception {

		JSONObject parametersJSONObject = _jsonFactory.createJSONObject();

		if (!ClassUtil.isSubclass(
				ddmDataProvider.getSettings(),
				DDMDataProviderParameterSettings.class)) {

			return parametersJSONObject;
		}

		DDMDataProviderParameterSettings ddmDataProviderParameterSetting =
			(DDMDataProviderParameterSettings)
				DDMFormInstanceFactory.create(
					ddmDataProvider.getSettings(), ddmFormValues);

		parametersJSONObject.put(
			"inputs",
			getInputParametersJSONArray(
				ddmDataProviderParameterSetting.inputParameters()));
		parametersJSONObject.put(
			"outputs",
			getOutputParametersJSONArray(
				ddmDataProviderParameterSetting.outputParameters()));

		return parametersJSONObject;
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			_ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSONObject parametersJSONObject = getParameterSettingsJSONObject(
			request);

		if (parametersJSONObject == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			response, parametersJSONObject.toJSONString());
	}

	protected DDMFormValues getDataProviderFormValues(
		DDMDataProvider ddmDataProvider,
		DDMDataProviderInstance ddmDataProviderInstance) {

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		return deserialize(ddmDataProviderInstance.getDefinition(), ddmForm);
	}

	protected DDMDataProviderInstance getDDMDataProviderInstance(
			HttpServletRequest request)
		throws PortalException {

		long ddmDataProviderInstanceId = ParamUtil.getLong(
			request, "ddmDataProviderInstanceId");

		return _ddmDataProviderInstanceService.getDataProviderInstance(
			ddmDataProviderInstanceId);
	}

	protected JSONArray getInputParametersJSONArray(
			DDMDataProviderInputParametersSettings[]
				ddmDataProviderInputParametersSettings)
		throws Exception {

		JSONArray inputsJSONArray = _jsonFactory.createJSONArray();

		for (DDMDataProviderInputParametersSettings
				ddmDataProviderInputParameterSetting :
					ddmDataProviderInputParametersSettings) {

			String label =
				ddmDataProviderInputParameterSetting.inputParameterLabel();
			String name =
				ddmDataProviderInputParameterSetting.inputParameterName();
			String type = getType(
				ddmDataProviderInputParameterSetting.inputParameterType());

			if (Validator.isNull(name) || Validator.isNull(type)) {
				continue;
			}

			JSONObject inputJSONObject = _jsonFactory.createJSONObject();

			if (Validator.isNotNull(label)) {
				inputJSONObject.put("label", label);
			}
			else {
				inputJSONObject.put("label", name);
			}

			inputJSONObject.put("name", name);
			inputJSONObject.put(
				"required",
				ddmDataProviderInputParameterSetting.inputParameterRequired());
			inputJSONObject.put("type", type);

			inputsJSONArray.put(inputJSONObject);
		}

		return inputsJSONArray;
	}

	protected JSONArray getOutputParametersJSONArray(
			DDMDataProviderOutputParametersSettings[]
				ddmDataProviderOutputParametersSettings)
		throws Exception {

		JSONArray outputsJSONArray = _jsonFactory.createJSONArray();

		for (DDMDataProviderOutputParametersSettings
				ddmDataProviderOutputParameterSetting :
					ddmDataProviderOutputParametersSettings) {

			String name =
				ddmDataProviderOutputParameterSetting.outputParameterName();
			String path =
				ddmDataProviderOutputParameterSetting.outputParameterPath();
			String type = getType(
				ddmDataProviderOutputParameterSetting.outputParameterType());

			if (Validator.isNull(path) || Validator.isNull(type)) {
				continue;
			}

			JSONObject outputJSONObject = _jsonFactory.createJSONObject();

			if (Validator.isNotNull(name)) {
				outputJSONObject.put("name", name);
			}
			else {
				outputJSONObject.put("name", path);
			}

			outputJSONObject.put("type", type);

			outputsJSONArray.put(outputJSONObject);
		}

		return outputsJSONArray;
	}

	protected JSONObject getParameterSettingsJSONObject(
		HttpServletRequest request) {

		try {
			DDMDataProviderInstance ddmDataProviderInstance =
				getDDMDataProviderInstance(request);

			DDMDataProvider ddmDataProvider =
				_ddmDataProviderTracker.getDDMDataProvider(
					ddmDataProviderInstance.getType());

			DDMFormValues ddmFormValues = getDataProviderFormValues(
				ddmDataProvider, ddmDataProviderInstance);

			JSONObject parametersJSONObject = createParametersJSONObject(
				ddmDataProvider, ddmFormValues);

			return parametersJSONObject;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	protected String getType(String type) {
		try {
			JSONArray typeJSONArray = _jsonFactory.createJSONArray(type);

			return typeJSONArray.getString(0);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return type;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstanceParameterSettingsServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;

	@Reference
	private JSONFactory _jsonFactory;

}