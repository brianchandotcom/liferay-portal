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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderParameterSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=getDataProviderParametersSettings"
	},
	service = MVCResourceCommand.class
)
public class GetDataProviderParametersSettingsMVCResourceCommand
	extends BaseMVCResourceCommand {

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
			getInputParametersJSONObject(
				ddmDataProviderParameterSetting.inputParameters()));
		parametersJSONObject.put(
			"outputs",
			getOutputParametersJSONObject(
				ddmDataProviderParameterSetting.outputParameters()));

		return parametersJSONObject;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance =
			getDDMDataProviderInstance(resourceRequest);

		DDMDataProvider ddmDataProvider =
			_ddmDataProviderTracker.getDDMDataProvider(
				ddmDataProviderInstance.getType());

		DDMFormValues ddmFormValues = getDataProviderFormValues(
			ddmDataProvider, ddmDataProviderInstance);

		JSONObject parametersJSONObject = createParametersJSONObject(
			ddmDataProvider, ddmFormValues);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, parametersJSONObject);
	}

	protected DDMFormValues getDataProviderFormValues(
			DDMDataProvider ddmDataProvider,
			DDMDataProviderInstance ddmDataProviderInstance)
		throws PortalException {

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		return _ddmFormValuesJSONDeserializer.deserialize(
			ddmForm, ddmDataProviderInstance.getDefinition());
	}

	protected DDMDataProviderInstance getDDMDataProviderInstance(
			ResourceRequest resourceRequest)
		throws PortalException {

		long ddmDataProviderInstanceId = ParamUtil.getLong(
			resourceRequest, "ddmDataProviderInstanceId");

		return _ddmDataProviderInstanceService.getDataProviderInstance(
			ddmDataProviderInstanceId);
	}

	protected JSONArray getInputParametersJSONObject(
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

			inputJSONObject.put("name", name);

			if (Validator.isNotNull(label)) {
				inputJSONObject.put("label", label);
			}
			else {
				inputJSONObject.put("label", name);
			}

			inputJSONObject.put(
				"required",
				ddmDataProviderInputParameterSetting.inputParameterRequired());

			inputJSONObject.put("type", type);

			inputsJSONArray.put(inputJSONObject);
		}

		return inputsJSONArray;
	}

	protected JSONArray getOutputParametersJSONObject(
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

	protected String getType(String type) throws Exception {
		JSONArray typeJSONArray = _jsonFactory.createJSONArray(type);

		return typeJSONArray.getString(0);
	}

	@Reference
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Reference
	private JSONFactory _jsonFactory;

}