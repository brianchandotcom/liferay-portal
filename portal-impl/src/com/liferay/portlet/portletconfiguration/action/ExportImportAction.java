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
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.cal.DateRange;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;

import java.io.File;
import java.io.FileInputStream;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class ExportImportAction extends EditConfigurationAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Validator.isNotNull(cmd)) {
				if (cmd.equals("copy_from_live")) {
					StagingUtil.copyFromLive(actionRequest, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
				else if (cmd.equals(Constants.EXPORT)) {
					exportData(actionRequest, actionResponse, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
				else if (cmd.equals(Constants.IMPORT)) {
					checkExceededSizeLimit(actionRequest);

					importData(actionRequest, actionResponse, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
				else if (cmd.equals("publish_to_live")) {
					StagingUtil.publishToLive(actionRequest, portlet);

					sendRedirect(actionRequest, actionResponse);
				}
			}
			else {
				long plid = ParamUtil.getLong(actionRequest, "plid");

				DateRange dateRange = ExportImportUtil.getDateRange(
					actionRequest, -1, plid, portlet.getPortletId(), false,
					true);

				Date startDate = dateRange.getStartDate();
				Date endDate = dateRange.getEndDate();

				if (startDate != null) {
					actionResponse.setRenderParameter(
						"startDate", String.valueOf(startDate.getTime()));
				}

				if (endDate != null) {
					actionResponse.setRenderParameter(
						"endDate", String.valueOf(endDate.getTime()));
				}
			}
		}
		catch (Exception e) {
			if ((e instanceof LARFileSizeException) ||
				(e instanceof NoSuchLayoutException) ||
				(e instanceof PrincipalException)) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(
					actionRequest, "portlet.portlet_configuration.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(getTitle(portlet, renderRequest));

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.portlet_configuration.export_import"));
	}

	protected void checkExceededSizeLimit(PortletRequest portletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)portletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			if (uploadException.isExceededSizeLimit()) {
				throw new LARFileSizeException(uploadException.getCause());
			}

			throw new PortalException(uploadException.getCause());
		}
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

			DateRange dateRange = ExportImportUtil.getDateRange(
				actionRequest, groupId, plid, portlet.getPortletId(), false,
				true);

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

	protected void importData(
			ActionRequest actionRequest, ActionResponse actionResponse,
			Portlet portlet)
		throws Exception {

		try {
			UploadPortletRequest uploadPortletRequest =
				PortalUtil.getUploadPortletRequest(actionRequest);

			long plid = ParamUtil.getLong(uploadPortletRequest, "plid");
			long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");
			File file = uploadPortletRequest.getFile("importFileName");

			if (!file.exists()) {
				throw new LARFileException("Import file does not exist");
			}

			LayoutServiceUtil.importPortletInfo(
				plid, groupId, portlet.getPortletId(),
				actionRequest.getParameterMap(), file);

			addSuccessMessage(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if ((e instanceof LARFileException) ||
				(e instanceof LARTypeException) ||
				(e instanceof PortletIdException)) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if ((e instanceof LocaleException) ||
					 (e instanceof RecordSetDuplicateRecordSetKeyException) ||
					 (e instanceof StructureDuplicateStructureKeyException)) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				_log.error(e, e);

				SessionErrors.add(
					actionRequest, LayoutImportException.class.getName());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExportImportAction.class);

}