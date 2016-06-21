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

package com.liferay.dynamic.data.mapping.data.provider.internal.servlet;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContextContributor;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.data.provider.internal.servlet.DDMDataProviderServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-data-provider/*",
		"servlet.init.httpMethods=GET,POST,HEAD"
	},
	service = Servlet.class
)
public class DDMDataProviderServlet extends HttpServlet {

	protected void addDDMDataProviderContextParameters(
		HttpServletRequest request,
		DDMDataProviderContext ddmDataProviderContext,
		List<DDMDataProviderContextContributor>
			ddmDataProviderContextContributors) {

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
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String data = doGetData(request);

		if (data == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, data);
	}

	protected String doGetData(HttpServletRequest request) {
		try {
			String ddmDataProviderInstanceId = ParamUtil.getString(
				request, "ddmDataProviderInstanceId");

			DDMDataProvider ddmDataProvider =
				_ddmDataProviderTracker.getDDMDataProviderById(
					ddmDataProviderInstanceId);

			DDMDataProviderContext ddmDataProviderContext = null;

			if (ddmDataProvider != null) {
				ddmDataProviderContext = new DDMDataProviderContext(null);
			}
			else {
				DDMDataProviderInstance ddmDataProviderInstance =
					_ddmDataProviderInstanceService.getDataProviderInstance(
						Long.valueOf(ddmDataProviderInstanceId));

				ddmDataProvider = _ddmDataProviderTracker.getDDMDataProvider(
					ddmDataProviderInstance.getType());

				DDMForm ddmForm = DDMFormFactory.create(
					ddmDataProvider.getSettings());

				DDMFormValues ddmFormValues =
					_ddmFormValuesJSONDeserializer.deserialize(
						ddmForm, ddmDataProviderInstance.getDefinition());

				ddmDataProviderContext = new DDMDataProviderContext(
					ddmFormValues);

				List<DDMDataProviderContextContributor>
					ddmDataProviderContextContributors =
						_ddmDataProviderTracker.
							getDDMDataProviderContextContributors(
								ddmDataProviderInstance.getType());

				addDDMDataProviderContextParameters(
					request, ddmDataProviderContext,
					ddmDataProviderContextContributors);
			}

			ddmDataProviderContext.setHttpServletRequest(request);

			DDMDataProviderRequest ddmDataProviderRequest =
				new DDMDataProviderRequest(ddmDataProviderContext);

			DDMDataProviderResponse ddmDataProviderResponse =
				ddmDataProvider.getData(ddmDataProviderRequest);

			JSONArray jsonArray = toJSONArray(ddmDataProviderResponse);

			return jsonArray.toString();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderInstanceService(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderTracker(
		DDMDataProviderTracker ddmDataProviderTracker) {

		_ddmDataProviderTracker = ddmDataProviderTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected JSONArray toJSONArray(
		DDMDataProviderResponse ddmDataProviderResponse) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Map<Object, Object> map : ddmDataProviderResponse.getData()) {
			for (Entry<Object, Object> entry : map.entrySet()) {
				JSONObject jsonObject = _jsonFactory.createJSONObject();

				JSONObject labelJSONObject = _jsonFactory.createJSONObject();

				labelJSONObject.put(
					LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
					entry.getKey());

				jsonObject.put("label", labelJSONObject);

				jsonObject.put("value", entry.getValue());

				jsonArray.put(jsonObject);
			}
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderServlet.class);

	private static final long serialVersionUID = 1L;

	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;
	private DDMDataProviderTracker _ddmDataProviderTracker;
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private JSONFactory _jsonFactory;

}