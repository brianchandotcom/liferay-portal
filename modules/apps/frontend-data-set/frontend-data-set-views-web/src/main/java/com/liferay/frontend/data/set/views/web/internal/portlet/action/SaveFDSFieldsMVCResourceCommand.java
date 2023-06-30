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

package com.liferay.frontend.data.set.views.web.internal.portlet.action;

import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(
	property = {
		"javax.portlet.name=" + FDSViewsPortletKeys.FDS_VIEWS,
		"mvc.command.name=/frontend_data_set_views/save_fds_fields"
	},
	service = MVCResourceCommand.class
)
public class SaveFDSFieldsMVCResourceCommand
	extends BaseTransactionalMVCResourceCommand {

	@Override
	protected void doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				themeDisplay.getCompanyId(), "FDSField");

		String fdsViewId = ParamUtil.getString(resourceRequest, "fdsViewId");

		String creationData = ParamUtil.getString(
			resourceRequest, "creationData");

		JSONArray creationDataJSONArray = _jsonFactory.createJSONArray(
			creationData);

		for (int i = 0; i < creationDataJSONArray.length(); i++) {
			JSONObject creationDataJSONObject =
				creationDataJSONArray.getJSONObject(i);

			ObjectEntry objectEntry = _objectEntryService.addObjectEntry(
				0, objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"label", String.valueOf(creationDataJSONObject.get("name"))
				).put(
					"name", String.valueOf(creationDataJSONObject.get("name"))
				).put(
					"r_fdsViewFDSFieldRelationship_c_fdsViewId", fdsViewId
				).put(
					"renderer", "default"
				).put(
					"sortable", true
				).put(
					"type", String.valueOf(creationDataJSONObject.get("type"))
				).build(),
				new ServiceContext());

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				objectEntry.getValues());

			jsonObject.put(
				"externalReferenceCode", objectEntry.getExternalReferenceCode()
			).put(
				"id", objectEntry.getObjectEntryId()
			);

			jsonArray.put(jsonObject);
		}

		long[] deletionIds = ParamUtil.getLongValues(
			resourceRequest, "deletionIds");

		for (long id : deletionIds) {
			_objectEntryService.deleteObjectEntry(id);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

}