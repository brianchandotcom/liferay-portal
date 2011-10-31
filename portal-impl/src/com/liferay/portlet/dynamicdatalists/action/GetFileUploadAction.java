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

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.DDLUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class GetFileUploadAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		long recordId = ParamUtil.getLong(actionRequest, "recordId");
		String fieldName = ParamUtil.getString(actionRequest, "fieldName");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		try {
			getFile(recordId, fieldName, fileName, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		long recordId = ParamUtil.getLong(request, "recordId");
		String fieldName = ParamUtil.getString(request, "fieldName");
		String fileName = ParamUtil.getString(request, "fileName");

		try {
			getFile(recordId, fieldName, fileName, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}

		return null;
	}

	protected void getFile(
			long recordId, String fieldName, String fileName,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(recordId);

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		StringBundler sb = new StringBundler(5);

		sb.append(DDLUtil.getRecordFileUploadPath(record));
		sb.append(StringPool.SLASH);
		sb.append(recordVersion.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(fieldName);

		String path = sb.toString();

		InputStream is = DLStoreUtil.getFileAsStream(
			record.getCompanyId(), CompanyConstants.SYSTEM, path);
		long contentLength = DLStoreUtil.getFileSize(
			record.getCompanyId(), CompanyConstants.SYSTEM, path);
		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}