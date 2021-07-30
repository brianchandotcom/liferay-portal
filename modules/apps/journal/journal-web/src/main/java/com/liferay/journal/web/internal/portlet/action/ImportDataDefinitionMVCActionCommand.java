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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.upload.UploadPortletRequestImpl;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/import_data_definition"
	},
	service = MVCActionCommand.class
)
public class ImportDataDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			UploadPortletRequest uploadPortletRequest =
				_getUploadPortletRequest(actionRequest);

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				FileUtil.read(uploadPortletRequest.getFile("jsonFile")));

			DataDefinition dataDefinition = DataDefinition.toDTO(
				jsonObject.getString("dataDefinition"));

			dataDefinition.setDefaultDataLayout(
				DataLayout.toDTO(jsonObject.getString("dataLayout")));
			dataDefinition.setName(
				HashMapBuilder.<String, Object>put(
					String.valueOf(themeDisplay.getSiteDefaultLocale()),
					ParamUtil.getString(actionRequest, "name")
				).build());

			DataDefinitionResource.Builder dataDefinitionResourcedBuilder =
				_dataDefinitionResourceFactory.create();

			DataDefinitionResource dataDefinitionResource =
				dataDefinitionResourcedBuilder.user(
					themeDisplay.getUser()
				).build();

			dataDefinitionResource.postSiteDataDefinitionByContentType(
				themeDisplay.getScopeGroupId(), "journal", dataDefinition);

			SessionMessages.add(
				actionRequest, "importDataDefinitionSuccessMessage");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			SessionErrors.add(
				actionRequest, "importDataDefinitionErrorMessage");

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

	private static final Log _log = LogFactoryUtil.getLog(
		ImportDataDefinitionMVCActionCommand.class);

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}