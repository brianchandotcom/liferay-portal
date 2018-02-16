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

package com.liferay.exportimport.changeset.web.internal.portlet.action;

import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.changeset.exception.ExportImportEntityException;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ChangesetPortletKeys.CHANGESET,
		"mvc.command.name=exportImportChangeset"
	},
	service = MVCActionCommand.class
)
public class ExportImportChangesetMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, "cmd");

		if (cmd.equals(Constants.EXPORT) || cmd.equals(Constants.PUBLISH)) {
			_processExportAndPublishAction(actionRequest, actionResponse);
		}
		else {
			SessionErrors.add(
				actionRequest, ExportImportEntityException.class,
				new ExportImportEntityException(
					ExportImportEntityException.TYPE_INVALID_COMMAND));
		}
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			long backgroundTaskId)
		throws IOException {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL renderURL = liferayPortletResponse.createRenderURL(
			ExportImportPortletKeys.EXPORT_IMPORT);

		renderURL.setParameter("mvcPath", "/view_export_import.jsp");
		renderURL.setParameter(
			"backURL", actionRequest.getParameter("backURL"));
		renderURL.setParameter(
			"backgroundTaskId", String.valueOf(backgroundTaskId));

		actionRequest.setAttribute(WebKeys.REDIRECT, renderURL.toString());

		hideDefaultSuccessMessage(actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	private void _processExportAndPublishAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		String changesetUuid = StringPool.BLANK;

		if (Validator.isNotNull(actionRequest.getParameter("changesetUuid"))) {
			changesetUuid = ParamUtil.getString(actionRequest, "changesetUuid");
		}
		else {
			SessionErrors.add(
				actionRequest, ExportImportEntityException.class,
				new ExportImportEntityException(
					ExportImportEntityException.TYPE_NO_DATA_FOUND));

			return;
		}

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap();

		parameterMap.put("changesetUuid", new String[] {changesetUuid});

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, "cmd");

		String portletId = MapUtil.getString(
			actionRequest.getParameterMap(), "portletId");

		Portlet portlet = _portletLocalService.getPortletById(portletId);

		long backgroundTaskId = 0;

		if (cmd.equals(Constants.EXPORT)) {
			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactory.
					buildExportPortletSettingsMap(
						themeDisplay.getUser(), themeDisplay.getPlid(),
						themeDisplay.getScopeGroupId(),
						ChangesetPortletKeys.CHANGESET, parameterMap,
						_exportImportHelper.getPortletExportFileName(portlet));

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						themeDisplay.getUserId(), portletId,
						ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
						settingsMap);

			backgroundTaskId =
				_exportImportLocalService.exportPortletInfoAsFileInBackground(
					themeDisplay.getUserId(), exportImportConfiguration);
		}
		else if (cmd.equals(Constants.PUBLISH)) {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (!scopeGroup.isStagingGroup() &&
				!scopeGroup.isStagedRemotely()) {

				SessionErrors.add(
					actionRequest, ExportImportEntityException.class,
					new ExportImportEntityException(
						ExportImportEntityException.TYPE_GROUP_NOT_STAGED));

				return;
			}

			if (!scopeGroup.isStagedPortlet(portletId)) {
				SessionErrors.add(
					actionRequest, ExportImportEntityException.class,
					new ExportImportEntityException(
						ExportImportEntityException.TYPE_PORTLET_NOT_STAGED));

				return;
			}

			long liveGroupId = 0;

			if (scopeGroup.isStagingGroup()) {
				liveGroupId = scopeGroup.getLiveGroupId();
			}
			else if (scopeGroup.isStagedRemotely()) {
				liveGroupId = scopeGroup.getRemoteLiveGroupId();
			}

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactory.
					buildPublishPortletSettingsMap(
						themeDisplay.getUser(), themeDisplay.getScopeGroupId(),
						themeDisplay.getPlid(), liveGroupId,
						themeDisplay.getPlid(), ChangesetPortletKeys.CHANGESET,
						parameterMap);

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						themeDisplay.getUserId(), portletId,
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_PORTLET_LOCAL,
						settingsMap);

			backgroundTaskId = _staging.publishPortlet(
				themeDisplay.getUserId(), exportImportConfiguration);
		}

		sendRedirect(actionRequest, actionResponse, backgroundTaskId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportChangesetMVCActionCommand.class);

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private Staging _staging;

}