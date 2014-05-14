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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ExportImportConfigurationServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.sites.action.ActionUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
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
 * @author Levente Hudák
 */
public class EditExportConfigurationAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateExportConfiguration(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExportImportConfiguration(actionRequest, false);
			}
			else if (cmd.equals(Constants.EXPORT)) {
				long exportImportConfigurationId = ParamUtil.getLong(
					actionRequest, "exportImportConfigurationId");

				LayoutServiceUtil.exportLayoutsAsFileInBackground(
					exportImportConfigurationId);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteExportImportConfiguration(actionRequest, true);
			}
			else if (cmd.equals(Constants.RELAUNCH)) {
				relaunchExportLayoutConfiguration(actionRequest);
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
			getForward(renderRequest, "portlet.layouts_admin.export_layouts"));
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
				"/html/portlet/layouts_admin/export_layouts_processes.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

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

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				actionRequest, groupId,
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) + "settingsMap",
			settingsMap);
	}

	protected void deleteExportImportConfiguration(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException, SystemException {

		long[] deleteExportImportConfigurationIds = null;

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		if (exportImportConfigurationId > 0) {
			deleteExportImportConfigurationIds =
				new long[] {exportImportConfigurationId};
		}
		else {
			deleteExportImportConfigurationIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteExportImportConfigurationIds"), 0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<TrashedModel>();

		for (long deleteExportImportConfigurationId :
				deleteExportImportConfigurationIds) {

			if (moveToTrash) {
				ExportImportConfiguration exportImportConfiguration =
					ExportImportConfigurationServiceUtil.
						moveExportImportConfigurationToTrash(
							deleteExportImportConfigurationId);

				trashedModels.add(exportImportConfiguration);
			}
			else {
				ExportImportConfigurationServiceUtil.
					deleteExportImportConfiguration(
						deleteExportImportConfigurationId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected void relaunchExportLayoutConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, "backgroundTaskId");

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId");

		LayoutServiceUtil.exportLayoutsAsFileInBackground(
			exportImportConfigurationId);
	}

	protected ExportImportConfiguration updateExportConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		if (exportImportConfigurationId > 0) {
			return ExportImportConfigurationHelper.
				updateExportLayoutExportImportConfiguration(actionRequest);
		}
		else {
			return ExportImportConfigurationHelper.
				addExportLayoutExportImportConfiguration(actionRequest);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		EditExportConfigurationAction.class);

}