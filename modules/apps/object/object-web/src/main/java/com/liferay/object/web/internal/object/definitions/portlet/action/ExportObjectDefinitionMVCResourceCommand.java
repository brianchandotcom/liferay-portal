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

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.web.internal.util.JSONObjectSanitizerUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/export_object_definition"
	},
	service = MVCResourceCommand.class
)
public class ExportObjectDefinitionMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			themeDisplay.getUser()
		).build();

		long objectDefinitionId = ParamUtil.getLong(
			resourceRequest, "objectDefinitionId");

		ObjectDefinition objectDefinition =
			objectDefinitionResource.getObjectDefinition(objectDefinitionId);

		for (ObjectAction objectAction : objectDefinition.getObjectActions()) {
			Map<String, Object> parameters =
				(Map<String, Object>)objectAction.getParameters();

			Object object = parameters.get("predefinedValues");

			if (object == null) {
				continue;
			}

			parameters.put(
				"predefinedValues",
				ListUtil.toList(
					(ArrayList<LinkedHashMap>)object,
					_jsonFactory::createJSONObject));
		}

		objectDefinition.setObjectFields(
			ArrayUtil.filter(
				objectDefinition.getObjectFields(),
				objectField -> Validator.isNull(
					objectField.getRelationshipType())));

		JSONObject objectDefinitionJSONObject = _jsonFactory.createJSONObject(
			objectDefinition.toString());

		if (!FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
			objectDefinitionJSONObject.remove("modifiable");
		}

		if (!FeatureFlagManagerUtil.isEnabled("LPS-135430")) {
			objectDefinitionJSONObject.remove("storageType");
		}

		JSONObjectSanitizerUtil.sanitize(
			objectDefinitionJSONObject,
			new String[] {
				"dateCreated", "dateModified", "id", "listTypeDefinitionId",
				"objectDefinitionId", "objectDefinitionId1",
				"objectDefinitionId2", "objectFieldId", "objectRelationshipId",
				"titleObjectFieldId"
			});

		String objectDefinitionJSON = objectDefinitionJSONObject.toString();

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse,
			StringBundler.concat(
				"Object_", objectDefinition.getName(), StringPool.UNDERLINE,
				objectDefinitionId, StringPool.UNDERLINE, Time.getTimestamp(),
				".json"),
			objectDefinitionJSON.getBytes(), ContentTypes.APPLICATION_JSON);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

}