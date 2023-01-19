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

package com.liferay.object.web.internal.list.type.portlet.action;

import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.object.constants.ObjectPortletKeys;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.LIST_TYPE_DEFINITIONS,
		"mvc.command.name=/list_type_definitions/import_list_type_definition"
	},
	service = MVCActionCommand.class
)
public class ImportListTypeDefinitionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_importListTypeDefinition(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"title",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"the-picklist-was-not-successfully-imported")));
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

	private void _importListTypeDefinition(ActionRequest actionRequest)
		throws Exception {

		ListTypeDefinitionResource.Builder builder =
			_listTypeDefinitionResourceFactory.create();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ListTypeDefinitionResource listTypeDefinitionResource = builder.user(
			themeDisplay.getUser()
		).build();

		UploadPortletRequest uploadPortletRequest = _getUploadPortletRequest(
			actionRequest);

		String listTypeDefinitionJSON = FileUtil.read(
			uploadPortletRequest.getFile("listTypeDefinitionJSON"));

		JSONObject listTypeDefinitionJSONObject = _jsonFactory.createJSONObject(
			listTypeDefinitionJSON);

		ListTypeDefinition listTypeDefinition = ListTypeDefinition.toDTO(
			listTypeDefinitionJSONObject.toString());

		listTypeDefinition.setName_i18n(
			LocalizedMapUtil.mergeI18nMap(
				listTypeDefinition.getName_i18n(),
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
				ParamUtil.getString(actionRequest, "name")));

		listTypeDefinitionResource.putListTypeDefinitionByExternalReferenceCode(
			listTypeDefinition.getExternalReferenceCode(), listTypeDefinition);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportListTypeDefinitionMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionResource.Factory
		_listTypeDefinitionResourceFactory;

	@Reference
	private Portal _portal;

}