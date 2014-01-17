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

package com.liferay.portlet.dynamicdatalists.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDL {

	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception;

	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion)
		throws Exception;

	public List<DDLRecord> getRecords(Hits hits) throws Exception;

	public JSONArray getRecordSetJSONArray(DDLRecordSet recordSet)
		throws Exception;

	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception;

	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception;

	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion)
		throws Exception;

	public String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0
	 */
	public boolean isEditable(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0
	 */
	public boolean isEditable(
			PortletPreferences preferences, String portletId, long groupId)
		throws Exception;

	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception;

	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception;

}