/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.action;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.DDLExportFormat;
import com.liferay.portlet.dynamicdatalists.util.DDLExporter;
import com.liferay.portlet.dynamicdatalists.util.DDLExporterFactory;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Marcellus Tavares
 */
public class ExportRecordSetAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordSetId = ParamUtil.getLong(resourceRequest, "recordSetId");

		String fileExtension = ParamUtil.getString(
			resourceRequest, "fileExtension");

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(resourceRequest);

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(resourceResponse);

		DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(
			recordSetId);

		String fileName =
			recordSet.getName(themeDisplay.getLocale()) + CharPool.PERIOD +
			fileExtension;

		DDLExportFormat exportFormat = DDLExportFormat.parse(fileExtension);

		DDLExporter exporter = DDLExporterFactory.getDDLExporter(exportFormat);

		byte[] bytes = exporter.export(
			recordSetId, WorkflowConstants.STATUS_APPROVED);

		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			httpServletRequest, httpServletResponse, fileName, bytes,
			contentType);
	}

}