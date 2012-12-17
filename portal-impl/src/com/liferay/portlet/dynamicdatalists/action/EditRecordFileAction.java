/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.DDLUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Basto
 */
public class EditRecordFileAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.DELETE)) {
			deleteRecordFieldFile(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			jsonObject = updateRecordFieldFile(resourceRequest);
		}
		catch (Exception e) {
			if (e instanceof FileSizeException) {
				jsonObject.put("exception", e.toString());
			}
			else {
				throw e;
			}
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void deleteRecordFieldFile(PortletRequest portletRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordId = ParamUtil.getLong(portletRequest, "recordId");

		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(recordId);

		Fields fields = record.getFields();

		String fieldName = ParamUtil.getString(portletRequest, "fieldName");

		Field field = fields.get(fieldName);

		List<Serializable> values = field.getValues(themeDisplay.getLocale());

		int valueIndex = ParamUtil.getInteger(portletRequest, "valueIndex");

		values.remove(valueIndex);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), portletRequest);

		DDLRecordLocalServiceUtil.updateRecord(
			themeDisplay.getUserId(), recordId, false, record.getDisplayIndex(),
			fields, true, serviceContext);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected JSONObject updateRecordFieldFile(PortletRequest request)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(request);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), uploadPortletRequest);

		long recordId = ParamUtil.getLong(serviceContext, "recordId");

		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(recordId);

		String fieldName = ParamUtil.getString(serviceContext, "fieldName");

		DDLUtil.uploadRecordFieldFile(record, fieldName, serviceContext);

		String fieldValue = String.valueOf(record.getFieldValue(fieldName));

		if (Validator.isNull(fieldValue)) {
			fieldValue = JSONFactoryUtil.getNullJSON();
		}

		return JSONFactoryUtil.createJSONObject(fieldValue);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}