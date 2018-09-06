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

import com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base.BaseDDMFormBuilderServlet;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.DataProviderInstanceNameComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

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
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-data-provider-instances",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMDataProviderInstancesServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-data-provider-instances/*"
	},
	service = Servlet.class
)
public class DDMDataProviderInstancesServlet extends BaseDDMFormBuilderServlet {

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		JSONArray dataProviderInstancesJSONArray =
			getDataProviderInstancesJSONArray(request);

		if (dataProviderInstancesJSONArray == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			response, dataProviderInstancesJSONArray.toJSONString());
	}

	protected JSONArray getDataProviderInstancesJSONArray(
		HttpServletRequest request) {

		try {
			String languageId = ParamUtil.getString(request, "languageId");

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			long scopeGroupId = ParamUtil.getLong(request, "scopeGroupId");

			long[] groupIds = _portal.getCurrentAndAncestorSiteGroupIds(
				scopeGroupId);

			int start = ParamUtil.getInteger(
				request, "start", QueryUtil.ALL_POS);
			int end = ParamUtil.getInteger(request, "end", QueryUtil.ALL_POS);

			DataProviderInstanceNameComparator
				dataProviderInstanceNameComparator =
					new DataProviderInstanceNameComparator(true);

			List<DDMDataProviderInstance> ddmDataProviderInstances =
				_ddmDataProviderInstanceLocalService.getDataProviderInstances(
					groupIds, start, end, dataProviderInstanceNameComparator);

			JSONArray dataProviderInstancesJSONArray =
				_jsonFactory.createJSONArray();

			for (DDMDataProviderInstance ddmDataProviderInstance :
					ddmDataProviderInstances) {

				JSONObject dataProviderInstanceJSONObject =
					_jsonFactory.createJSONObject();

				dataProviderInstanceJSONObject.put(
					"id", ddmDataProviderInstance.getDataProviderInstanceId());
				dataProviderInstanceJSONObject.put(
					"name", ddmDataProviderInstance.getName(locale));
				dataProviderInstanceJSONObject.put(
					"uuid", ddmDataProviderInstance.getUuid());

				dataProviderInstancesJSONArray.put(
					dataProviderInstanceJSONObject);
			}

			return dataProviderInstancesJSONArray;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstancesServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}