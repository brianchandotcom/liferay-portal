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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 */
public class DDLUtil {

	public static void addAllReservedEls(
		Element rootElement, Map<String, String> tokens,
		DDLRecordSet recordSet) {

		getDDL().addAllReservedEls(rootElement, tokens, recordSet);
	}

	public static DDL getDDL() {
		return _ddl;
	}

	public static Fields getFields(
			UploadPortletRequest uploadPortletRequest, long ddmStructureId)
		throws PortalException, SystemException {

		return getDDL().getFields(uploadPortletRequest, ddmStructureId);
	}

	public static Fields getFields(
			UploadPortletRequest uploadPortletRequest, long ddmStructureId,
			long ddmTemplateId)
		throws PortalException, SystemException {

		return getDDL().getFields(
			uploadPortletRequest, ddmStructureId, ddmTemplateId);
	}

	public static void getRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			DDLRecord record, String fieldName)
		throws Exception {

		getDDL().sendRecordFileUpload(request, response, record, fieldName);
	}

	public static String getRecordFileUploadPath(DDLRecord record) {
		return getDDL().getRecordFileUploadPath(record);
	}

	public static JSONObject getRecordJSONObject(DDLRecord record)
		throws Exception {

		return getDDL().getRecordJSONObject(record);
	}

	public static JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion)
		throws Exception {

		return getDDL().getRecordJSONObject(record, latestRecordVersion);
	}

	public static JSONArray getRecordSetJSONArray(DDLRecordSet recordSet)
		throws Exception {

		return getDDL().getRecordSetJSONArray(recordSet);
	}

	public static JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception {

		return getDDL().getRecordsJSONArray(records);
	}

	public static JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion)
		throws Exception {

		return getDDL().getRecordsJSONArray(records, latestRecordVersion);
	}

	public static String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return getDDL().getTemplateContent(
			ddmTemplateId, recordSet, themeDisplay, renderRequest,
			renderResponse);
	}

	public static void sendRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			long recordId, String fieldName)
		throws Exception {

		getDDL().sendRecordFileUpload(request, response, recordId, fieldName);
	}

	public static String storeRecordFieldFile(
			DDLRecord record, String fieldName, InputStream inputStream)
		throws Exception {

		return getDDL().storeRecordFieldFile(record, fieldName, inputStream);
	}

	public static DDLRecord updateRecord(
			UploadPortletRequest uploadPortletRequest, long recordId,
			long recordSetId, boolean mergeFields)
		throws Exception {

		return getDDL().updateRecord(
			uploadPortletRequest, recordId, recordSetId, mergeFields);
	}

	public static DDLRecord updateRecord(
			UploadPortletRequest uploadPortletRequest, long recordId,
			long recordSetId, boolean mergeFields, boolean checkPermission)
		throws Exception {

		return getDDL().updateRecord(
			uploadPortletRequest, recordId, recordSetId, mergeFields,
			checkPermission);
	}

	public static void uploadRecordFieldFile(
			DDLRecord record, String fieldName,
			UploadPortletRequest uploadPortletRequest,
			ServiceContext serviceContext)
		throws Exception {

		getDDL().uploadRecordFieldFile(
			record, fieldName, uploadPortletRequest, serviceContext);
	}

	public static void uploadRecordFieldFiles(
			DDLRecord record, UploadPortletRequest uploadPortletRequest,
			ServiceContext serviceContext)
		throws Exception {

		getDDL().uploadRecordFieldFiles(
			record, uploadPortletRequest, serviceContext);
	}

	public void setDDL(DDL ddl) {
		_ddl = ddl;
	}

	private static DDL _ddl;

}