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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
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
		PortalRuntimePermission.checkGetBeanProperty(DDLUtil.class);

		return _ddl;
	}

	public static void getRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			DDLRecord record, String fieldName, int valueIndex)
		throws Exception {

		getDDL().sendRecordFileUpload(
			request, response, record, fieldName, valueIndex);
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

	public static List<DDLRecord> getRecords(Hits hits) throws Exception {
		return getDDL().getRecords(hits);
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

	public static boolean isEditable(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception {

		return getDDL().isEditable(request, portletId, groupId);
	}

	public static boolean isEditable(
			PortletPreferences preferences, String portletId, long groupId)
		throws Exception {

		return getDDL().isEditable(preferences, portletId, groupId);
	}

	public static void sendRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			DDLRecord record, String fieldName, int valueIndex)
		throws Exception {

		getDDL().sendRecordFileUpload(
			request, response, record, fieldName, valueIndex);
	}

	public static void sendRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			long recordId, String fieldName, int valueIndex)
		throws Exception {

		getDDL().sendRecordFileUpload(
			request, response, recordId, fieldName, valueIndex);
	}

	public static DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception {

		return getDDL().updateRecord(
			recordId, recordSetId, mergeFields, checkPermission,
			serviceContext);
	}

	public static DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception {

		return getDDL().updateRecord(
			recordId, recordSetId, mergeFields, serviceContext);
	}

	public static void uploadRecordFieldFile(
			DDLRecord record, String fieldName, ServiceContext serviceContext)
		throws Exception {

		getDDL().uploadRecordFieldFile(record, fieldName, serviceContext);
	}

	public void setDDL(DDL ddl) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddl = ddl;
	}

	private static DDL _ddl;

}