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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.util.DDLUtil;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.Serializable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class EditRecordAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateRecord(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRecord(actionRequest);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertRecordVersion(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRecordException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.dynamic_data_lists.error");
			}
			else if (e instanceof FileSizeException ||
					 e instanceof StorageFieldRequiredException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
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

		try {
			ActionUtil.getRecord(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchRecordException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.dynamic_data_lists.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest, "portlet.dynamic_data_lists.edit_record"));
	}

	protected void deleteRecord(ActionRequest actionRequest)
		throws Exception {

		long recordId = ParamUtil.getLong(actionRequest, "recordId");

		DDLRecordLocalServiceUtil.deleteRecord(recordId);
	}

	protected void revertRecordVersion(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordId = ParamUtil.getLong(actionRequest, "recordId");

		String version = ParamUtil.getString(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), actionRequest);

		DDLRecordLocalServiceUtil.revertRecordVersion(
			themeDisplay.getUserId(), recordId, version, serviceContext);
	}

	protected DDLRecord updateRecord(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long recordId = ParamUtil.getLong(actionRequest, "recordId");

		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");
		boolean majorVersion = ParamUtil.getBoolean(
			actionRequest, "majorVersion");

		DDLRecord record = DDLRecordLocalServiceUtil.fetchRecord(recordId);

		DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = new Fields();

		for (String fieldName : ddmStructure.getFieldNames()) {
			String fieldDataType = ddmStructure.getFieldDataType(fieldName);
			String fieldValue = ParamUtil.getString(actionRequest, fieldName);

			if (fieldDataType.equals(FieldConstants.DOCUMENT_LIBRARY)) {
				UploadPortletRequest uploadPortletRequest =
					PortalUtil.getUploadPortletRequest(actionRequest);

				File file = uploadPortletRequest.getFile(fieldName);

				if (file != null) {
					JSONObject fileJSONObject = null;

					if (record != null) {
						String oldFieldValue = String.valueOf(
							record.getFieldValue(fieldName));

						fileJSONObject = JSONFactoryUtil.createJSONObject(
							oldFieldValue);
					}

					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(
							DLFileEntry.class.getName(), actionRequest);

					FileEntry fileEntry = DDLUtil.uploadFieldFile(
						ddmStructure, fieldName, fileJSONObject,
						uploadPortletRequest, serviceContext);

					JSONObject recordFileEntryJSONObject =
						DDLUtil.getRecordFileJSONObject(fileEntry);

					fieldValue = recordFileEntryJSONObject.toString();
				}
			}

			Serializable fieldValueSerializable =
				FieldConstants.getSerializable(fieldDataType, fieldValue);

			Field field = new Field(
				ddmStructure.getStructureId(), fieldName,
				fieldValueSerializable);

			fields.put(field);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), actionRequest);

		if (record != null) {
			record = DDLRecordServiceUtil.updateRecord(
				recordId, majorVersion,
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields, false,
				serviceContext);
		}
		else {
			record = DDLRecordServiceUtil.addRecord(
				themeDisplay.getScopeGroupId(), recordSetId,
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
				serviceContext);
		}

		return record;
	}

}