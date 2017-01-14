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
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.DataProviderInstanceNameComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

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
		"mvc.command.name=getDataProviders"
	},
	service = MVCResourceCommand.class
)
public class GetDataProvidersMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();
		long[] groupIds = _portal.getCurrentAndAncestorSiteGroupIds(
			themeDisplay.getScopeGroupId());

		int start = ParamUtil.getInteger(
			resourceRequest, "start", QueryUtil.ALL_POS);
		int end = ParamUtil.getInteger(
			resourceRequest, "end", QueryUtil.ALL_POS);

		DataProviderInstanceNameComparator dataProviderInstanceNameComparator =
			new DataProviderInstanceNameComparator(true);

		List<DDMDataProviderInstance> ddmDataProviderInstances =
			_ddmDataProviderInstanceLocalService.getDataProviderInstances(
				groupIds, start, end, dataProviderInstanceNameComparator);

		JSONArray dataProvidersJSONArray = _jsonFactory.createJSONArray();

		for (DDMDataProviderInstance ddmDataProviderInstance :
				ddmDataProviderInstances) {
			JSONObject dataProviderJSONObject = _jsonFactory.createJSONObject();

			long ddmDataProviderInstanceId =
				ddmDataProviderInstance.getDataProviderInstanceId();

			String ddmDataProviderInstanceUUID =
				ddmDataProviderInstance.getUuid();

			String ddmDataProviderInstanceName =
				ddmDataProviderInstance.getName(locale);

			dataProviderJSONObject.put("id", ddmDataProviderInstanceId);
			dataProviderJSONObject.put("name", ddmDataProviderInstanceName);
			dataProviderJSONObject.put("uuid", ddmDataProviderInstanceUUID);

			dataProvidersJSONArray.put(dataProviderJSONObject);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, dataProvidersJSONArray);
	}

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}