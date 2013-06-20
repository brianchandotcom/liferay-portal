/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARFileSizeException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.lar.ExportImportHelper;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.layoutsadmin.action.ImportLayoutsAction;

import java.io.File;
import java.io.FileInputStream;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class ExportImportAction extends ImportLayoutsAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Validator.isNotNull(cmd)) {
				if (cmd.equals(Constants.ADD_TEMP)) {
					addTempFileEntry(
						actionRequest, actionResponse,
						ExportImportHelper.TEMP_FOLDER_NAME +
							portlet.getPortletId());

					validateFile(
						actionRequest, actionResponse,
						ExportImportHelper.TEMP_FOLDER_NAME +
							portlet.getPortletId());
				}
				else if (cmd.equals("copy_from_live")) {
					StagingUtil.copyFromLive(actionRequest, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
				else if (cmd.equals(Constants.DELETE_TEMP)) {
					deleteTempFileEntry(
						actionRequest, actionResponse,
						ExportImportHelper.TEMP_FOLDER_NAME +
							portlet.getPortletId());
				}
				else if (cmd.equals(Constants.EXPORT)) {
					exportData(actionRequest, actionResponse, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
				else if (cmd.equals(Constants.IMPORT)) {
					importData(
						actionRequest, actionResponse,
						ExportImportHelper.TEMP_FOLDER_NAME +
							portlet.getPortletId());

					sendRedirect(actionRequest, actionResponse);
				}
				else if (cmd.equals("publish_to_live")) {
					StagingUtil.publishToLive(actionRequest, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
			}
			else {
				long plid = ParamUtil.getLong(actionRequest, "plid");

				DateRange dateRange = ExportImportHelperUtil.getDateRange(
					actionRequest, 0, false, plid, portlet.getPortletId());

				Date startDate = dateRange.getStartDate();

				if (startDate != null) {
					actionResponse.setRenderParameter(
						"startDate", String.valueOf(startDate.getTime()));
				}

				Date endDate = dateRange.getEndDate();

				if (endDate != null) {
					actionResponse.setRenderParameter(
						"endDate", String.valueOf(endDate.getTime()));
				}
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				handleUploadException(
					portletConfig, actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME +
						portlet.getPortletId(),
					e);
			}
			else {
				if ((e instanceof LARFileException) ||
					(e instanceof LARFileSizeException) ||
					(e instanceof LARTypeException) ||
					(e instanceof LocaleException) ||
					(e instanceof NoSuchLayoutException) ||
					(e instanceof PortletIdException) ||
					(e instanceof PrincipalException) ||
					(e instanceof StructureDuplicateStructureKeyException) ||
					(e instanceof RecordSetDuplicateRecordSetKeyException)) {

					SessionErrors.add(actionRequest, e.getClass());
				}
				else {
					_log.error(e, e);

					SessionErrors.add(
						actionRequest, ExportImportAction.class.getName());
				}
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return actionMapping.findForward(
				"portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(ActionUtil.getTitle(portlet, renderRequest));

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.portlet_configuration.export_import"));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/portlet_configuration/" +
					"import_portlet_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	@Override
	protected void doImportData(ActionRequest actionRequest, File file)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "plid");
		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Portlet portlet = ActionUtil.getPortlet(actionRequest);

		LayoutServiceUtil.importPortletInfo(
			plid, groupId, portlet.getPortletId(),
			actionRequest.getParameterMap(), file);
	}

	@Override
	protected MissingReferences doValidateFile(
			ActionRequest actionRequest, File file)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "plid");
		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Portlet portlet = ActionUtil.getPortlet(actionRequest);

		return LayoutServiceUtil.validateImportPortletInfo(
			plid, groupId, portlet.getPortletId(),
			actionRequest.getParameterMap(), file);
	}

	protected void exportData(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Portlet portlet)
		throws Exception {

		File file = null;

		try {
			long plid = ParamUtil.getLong(actionRequest, "plid");
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String fileName = ParamUtil.getString(
				actionRequest, "exportFileName");

			DateRange dateRange = ExportImportHelperUtil.getDateRange(
				actionRequest, groupId, false, plid, portlet.getPortletId());

			file = LayoutServiceUtil.exportPortletInfoAsFile(
				plid, groupId, portlet.getPortletId(),
				actionRequest.getParameterMap(), dateRange.getStartDate(),
				dateRange.getEndDate());

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				request, response, fileName, new FileInputStream(file),
				ContentTypes.APPLICATION_ZIP);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExportImportAction.class);

}