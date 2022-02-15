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
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.web.internal.object.definitions.portlet.action.util.ExportImportObjectDefinitiontUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.upload.UploadPortletRequestImpl;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		"mvc.command.name=/object_definitions/import_object_definition"
	},
	service = MVCActionCommand.class
)
public class ImportObjectDefinitionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_importObjectDefinition(actionRequest);

			SessionMessages.add(
				actionRequest, "importObjectDefinitionSuccessMessage");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (exception instanceof
					ObjectDefinitionNameException.
						MustBeginWithUpperCaseLetter ||
				exception instanceof
					ObjectDefinitionNameException.MustNotBeDuplicate ||
				exception instanceof
					ObjectDefinitionNameException.
						MustOnlyContainLettersAndDigits) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				SessionErrors.add(
					actionRequest, "importObjectDefinitionErrorMessage");
			}

			hideDefaultErrorMessage(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private UploadPortletRequest _getUploadPortletRequest(
		ActionRequest actionRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(actionRequest);

		return new UploadPortletRequestImpl(
			_portal.getUploadServletRequest(
				liferayPortletRequest.getHttpServletRequest()),
			liferayPortletRequest,
			_portal.getPortletNamespace(
				liferayPortletRequest.getPortletName()));
	}

	private void _importObjectActions(
			ObjectDefinition objectDefinition,
			ObjectDefinition postObjectDefinition, ThemeDisplay themeDisplay)
		throws Exception {

		ObjectActionResource.Builder objectActionResourcedBuilder =
			_objectActionResourceFactory.create();

		ObjectActionResource objectActionResource =
			objectActionResourcedBuilder.user(
				themeDisplay.getUser()
			).build();

		for (ObjectAction objectAction : objectDefinition.getObjectActions()) {
			objectActionResource.postObjectDefinitionObjectAction(
				postObjectDefinition.getId(), objectAction);
		}
	}

	private void _importObjectDefinition(ActionRequest actionRequest)
		throws Exception {

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			themeDisplay.getUser()
		).build();

		UploadPortletRequest uploadPortletRequest = _getUploadPortletRequest(
			actionRequest);

		String objectDefinitionJSON = FileUtil.read(
			uploadPortletRequest.getFile("objectDefinitionJSON"));

		JSONObject objectDefinitionJSONObject =
			JSONFactoryUtil.createJSONObject(objectDefinitionJSON);

		ExportImportObjectDefinitiontUtil.apply(
			objectDefinitionJSONObject,
			objectLayoutColumnJSONObject -> {
				objectLayoutColumnJSONObject.remove("objectFieldName");

				return objectLayoutColumnJSONObject;
			});

		String titleObjectFieldName = (String)objectDefinitionJSONObject.get(
			"titleObjectFieldName");

		objectDefinitionJSONObject.remove("titleObjectFieldName");

		ObjectDefinition objectDefinition = ObjectDefinition.toDTO(
			objectDefinitionJSONObject.toString());

		objectDefinition.setName(ParamUtil.getString(actionRequest, "name"));

		ObjectDefinition postObjectDefinition =
			objectDefinitionResource.postObjectDefinition(objectDefinition);

		for (ObjectField objectField : postObjectDefinition.getObjectFields()) {
			if (Objects.equals(objectField.getName(), titleObjectFieldName)) {
				postObjectDefinition.setTitleObjectFieldId(objectField.getId());

				break;
			}
		}

		postObjectDefinition.setPortlet(objectDefinition.getPortlet());

		objectDefinitionResource.putObjectDefinition(
			postObjectDefinition.getId(), postObjectDefinition);

		_importObjectActions(
			objectDefinition, postObjectDefinition, themeDisplay);

		objectDefinitionJSONObject = JSONFactoryUtil.createJSONObject(
			objectDefinitionJSON);

		_importObjectLayouts(
			objectDefinitionJSONObject, postObjectDefinition, themeDisplay);

		_importObjectViews(
			objectDefinition, postObjectDefinition, themeDisplay);
	}

	private void _importObjectLayouts(
			JSONObject objectDefinitionJSONObject,
			ObjectDefinition postObjectDefinition, ThemeDisplay themeDisplay)
		throws Exception {

		ObjectLayoutResource.Builder builder =
			_objectLayoutResourceFactory.create();

		ObjectLayoutResource objectLayoutResource = builder.user(
			themeDisplay.getUser()
		).build();

		ExportImportObjectDefinitiontUtil.apply(
			objectDefinitionJSONObject,
			objectLayoutColumnJSONObject -> {
				for (ObjectField objectField :
						postObjectDefinition.getObjectFields()) {

					if (StringUtil.equals(
							objectField.getName(),
							objectLayoutColumnJSONObject.getString(
								"objectFieldName"))) {

						objectLayoutColumnJSONObject.put(
							"objectFieldId", objectField.getId());

						break;
					}
				}

				objectLayoutColumnJSONObject.remove("objectFieldName");

				return objectLayoutColumnJSONObject;
			});

		JSONArray objectLayoutsJSONArray =
			(JSONArray)objectDefinitionJSONObject.get("objectLayouts");

		for (int i = 0; i < objectLayoutsJSONArray.length(); i++) {
			JSONObject objectLayoutJSONObject =
				(JSONObject)objectLayoutsJSONArray.get(i);

			objectLayoutResource.postObjectDefinitionObjectLayout(
				postObjectDefinition.getId(),
				ObjectLayout.toDTO(objectLayoutJSONObject.toString()));
		}
	}

	private void _importObjectViews(
			ObjectDefinition objectDefinition,
			ObjectDefinition postObjectDefinition, ThemeDisplay themeDisplay)
		throws Exception {

		ObjectViewResource.Builder objectViewResourcedBuilder =
			_objectViewResourceFactory.create();

		ObjectViewResource objectViewResource = objectViewResourcedBuilder.user(
			themeDisplay.getUser()
		).build();

		for (ObjectView objectView : objectDefinition.getObjectViews()) {
			objectViewResource.postObjectDefinitionObjectView(
				postObjectDefinition.getId(), objectView);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportObjectDefinitionMVCActionCommand.class);

	@Reference
	private ObjectActionResource.Factory _objectActionResourceFactory;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private ObjectLayoutResource.Factory _objectLayoutResourceFactory;

	@Reference
	private ObjectViewResource.Factory _objectViewResourceFactory;

	@Reference
	private Portal _portal;

}