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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationHelper;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 * @author Levente Hudák
 */
public class EditPublishConfigurationAction
	extends EditExportConfigurationAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long exportImportConfigurationId = ParamUtil.getLong(
				actionRequest, "exportImportConfigurationId");

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updatePublishConfiguration(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExportImportConfiguration(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteExportImportConfiguration(actionRequest, true);
			}
			else if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
				StagingUtil.publishLayouts(
					themeDisplay.getUserId(), exportImportConfigurationId);
			}
			else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
				StagingUtil.copyRemoteLayouts(exportImportConfigurationId);
			}
			else if (cmd.equals(Constants.RELAUNCH)) {
				relaunchPublishLayoutConfiguration(
					themeDisplay.getUserId(), actionRequest);
			}
			else if (Validator.isNull(cmd)) {
				addSessionMessages(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass());

			String pagesRedirect = ParamUtil.getString(
				actionRequest, "pagesRedirect");

			sendRedirect(actionRequest, actionResponse, pagesRedirect);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.publish_layouts"));
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/layouts_admin/publish_layouts_processes.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	@Override
	protected void addSessionMessages(ActionRequest actionRequest)
		throws Exception {

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				"exportImportConfigurationId",
			exportImportConfigurationId);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		int exportImportConfigurationType =
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE;

		boolean localPublishing = ParamUtil.getBoolean(
			actionRequest, "localPublishing");

		if (localPublishing) {
			exportImportConfigurationType =
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL;
		}

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				actionRequest, groupId, exportImportConfigurationType);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) + "settingsMap",
			settingsMap);
	}

	protected void relaunchPublishLayoutConfiguration(
			long userId, ActionRequest actionRequest)
		throws PortalException, SystemException {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, "backgroundTaskId");

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(
					MapUtil.getLong(
						taskContextMap, "exportImportConfigurationId"));

		if (exportImportConfiguration.getType() ==
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL) {

			StagingUtil.publishLayouts(userId, exportImportConfiguration);
		}
		else if (exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE) {

			StagingUtil.copyRemoteLayouts(exportImportConfiguration);
		}
	}

	protected ExportImportConfiguration updatePublishConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		boolean localPublishing = ParamUtil.getBoolean(
			actionRequest, "localPublishing");

		if (exportImportConfigurationId > 0) {
			if (localPublishing) {
				return ExportImportConfigurationHelper.
					updatePublishLayoutLocalExportImportConfiguration(
						actionRequest);
			}
			else {
				return ExportImportConfigurationHelper.
					updatePublishLayoutRemoteExportImportConfiguration(
						actionRequest);
			}
		}
		else {
			if (localPublishing) {
				return ExportImportConfigurationHelper.
					addPublishLayoutLocalExportImportConfiguration(
						actionRequest);
			}
			else {
				return ExportImportConfigurationHelper.
					addPublishLayoutRemoteExportImportConfiguration(
						actionRequest);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		EditPublishConfigurationAction.class);

}