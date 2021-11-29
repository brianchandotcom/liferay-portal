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

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.web.internal.object.definitions.portlet.action.util.ExportImportObjectDefinitiontUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
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

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ObjectDefinitionResource.Builder objectDefinitionResourcedBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourcedBuilder.user(
				themeDisplay.getUser()
			).build();

		long objectDefinitionId = ParamUtil.getLong(
			resourceRequest, "objectDefinitionId");

		ObjectDefinition objectDefinition =
			objectDefinitionResource.getObjectDefinition(objectDefinitionId);

		List<ObjectField> objectFields = ListUtil.fromArray(
			objectDefinition.getObjectFields());

		objectFields.removeIf(
			objectField -> Validator.isNotNull(
				objectField.getRelationshipType()));

		objectDefinition.setObjectFields(
			objectFields.toArray(new ObjectField[0]));

		JSONObject objectDefinitionJSONObject =
			JSONFactoryUtil.createJSONObject(String.valueOf(objectDefinition));

		ExportImportObjectDefinitiontUtil.applyObjectLayoutColumnJSONObject(
			objectDefinitionJSONObject,
			objectLayoutColumnJSONObject -> {
				Stream<ObjectField> stream = objectFields.stream();

				ObjectField objectField = stream.filter(
					filter -> Objects.equals(
						filter.getId(),
						Long.valueOf(
							(Integer)objectLayoutColumnJSONObject.get(
								"objectFieldId")))
				).findFirst(
				).orElse(
					null
				);

				if ((objectField == null) ||
					Validator.isNotNull(objectField.getRelationshipType())) {

					return null;
				}

				objectLayoutColumnJSONObject.put(
					"objectFieldName", objectField.getName());

				return objectLayoutColumnJSONObject;
			});

		_sanitizeJSON(
			objectDefinitionJSONObject,
			new String[] {
				"dateCreated", "dateModified", "id", "listTypeDefinitionId",
				"objectDefinitionId", "objectFieldId", "objectRelationshipId"
			});

		String dataDefinitionString = objectDefinitionJSONObject.toString();

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse,
			StringBundler.concat(
				"Object_", objectDefinition.getName(), StringPool.UNDERLINE,
				String.valueOf(objectDefinitionId), StringPool.UNDERLINE,
				Time.getTimestamp(), ".json"),
			dataDefinitionString.getBytes(), ContentTypes.APPLICATION_JSON);
	}

	private void _sanitizeJSON(Object object, String[] keys) {
		if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			for (int i = 0; i < jsonArray.length(); ++i) {
				_sanitizeJSON(jsonArray.get(i), keys);
			}
		}
		else if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			if (jsonObject.length() == 0) {
				return;
			}

			JSONArray jsonArray = jsonObject.names();

			for (int i = 0; i < jsonArray.length(); ++i) {
				String key = jsonArray.getString(i);

				if (ArrayUtil.contains(keys, key)) {
					jsonObject.remove(key);
				}
				else {
					_sanitizeJSON(jsonObject.get(key), keys);
				}
			}
		}
	}

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

}