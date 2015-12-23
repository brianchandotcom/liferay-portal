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

import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import java.io.IOException;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Bruno Basto
 */
@Component(immediate = true)
public class DDMDataProviderServlet extends HttpServlet {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Hashtable<String, String> properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/dynamic-data-mapping-data-provider");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_NAME,
			"AuthVerifierFilter");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN,
			"/dynamic-data-mapping-data-provider/*");

		bundleContext.registerService(
			Filter.class, new AuthVerifierFilter(), properties);

		properties = new Hashtable<>();

		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
			"/dynamic-data-mapping-data-provider");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
			"DDMDataProviderServlet");
		properties.put(
			HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
			"/dynamic-data-mapping-data-provider/*");
		properties.put("servlet.init.httpMethods", "GET,POST,HEAD");

		bundleContext.registerService(Servlet.class, this, properties);
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String fieldName = ParamUtil.getString(request, "fieldName");
		String serializedDDMForm = ParamUtil.getString(
			request, "serializedDDMForm");

		String data = doGetData(fieldName, serializedDDMForm);

		if (data == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, data);
	}

	protected String doGetData(String fieldName, String serializedDDMForm) {
		try {
			DDMForm ddmForm = _ddmFormJSONDeserializer.deserialize(
				serializedDDMForm);

			Map<String, DDMFormField> ddmFormFieldsMap =
				ddmForm.getDDMFormFieldsMap(true);

			DDMFormField ddmFormField = ddmFormFieldsMap.get(fieldName);

			long ddmDataProviderInstanceId = GetterUtil.getLong(
				ddmFormField.getProperty("ddmDataProviderInstanceId"));

			DDMDataProviderInstance ddmDataProviderInstance =
				_ddmDataProviderInstanceService.getDataProviderInstance(
					ddmDataProviderInstanceId);

			JSONArray jsonArray = toJSONArray(
				ddmDataProviderInstance.getData());

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
	protected void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected JSONArray toJSONArray(List<KeyValuePair> keyValuePairs) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (KeyValuePair keyValuePair : keyValuePairs) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			JSONObject labelJSONObject = _jsonFactory.createJSONObject();

			labelJSONObject.put(
				LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
				keyValuePair.getValue());

			jsonObject.put("label", labelJSONObject);
			jsonObject.put("value", keyValuePair.getKey());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderServlet.class);

	private static final long serialVersionUID = 1L;

	private volatile DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private volatile DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private volatile JSONFactory _jsonFactory;

}