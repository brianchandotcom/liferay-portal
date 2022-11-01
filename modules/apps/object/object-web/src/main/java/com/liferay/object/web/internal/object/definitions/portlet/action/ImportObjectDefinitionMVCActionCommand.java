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
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectViewColumnFieldNameException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.util.PropsUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
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
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			JSONObject jsonObject = null;

			if (exception instanceof ObjectDefinitionNameException) {
				Class<?> clazz = exception.getClass();

				jsonObject = JSONUtil.put(
					"type",
					"ObjectDefinitionNameException." + clazz.getSimpleName());
			}
			else if (exception instanceof ObjectViewColumnFieldNameException) {
				jsonObject = JSONUtil.put(
					"title",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"the-structure-was-imported-without-a-custom-view"));
			}
			else {
				jsonObject = JSONUtil.put(
					"title",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"the-structure-was-not-successfully-imported"));
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}

		hideDefaultSuccessMessage(actionRequest);
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

		JSONObject objectDefinitionJSONObject = _jsonFactory.createJSONObject(
			objectDefinitionJSON);

		objectDefinitionJSONObject.remove(
			"accountEntryRestrictedObjectFieldId");

		ObjectDefinition objectDefinition = ObjectDefinition.toDTO(
			objectDefinitionJSONObject.toString());

		objectDefinition.setActive(false);
		objectDefinition.setName(ParamUtil.getString(actionRequest, "name"));

		ObjectDefinition putObjectDefinition =
			objectDefinitionResource.putObjectDefinitionByExternalReferenceCode(
				objectDefinition.getExternalReferenceCode(), objectDefinition);

		putObjectDefinition.setPortlet(objectDefinition.getPortlet());

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-135430"))) {
			putObjectDefinition.setStorageType(StringPool.BLANK);
		}

		objectDefinitionResource.putObjectDefinition(
			putObjectDefinition.getId(), putObjectDefinition);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportObjectDefinitionMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Reference
	private Portal _portal;

}