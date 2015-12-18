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

package com.liferay.dynamic.data.lists.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDL {

	public static final String[] SELECTED_FIELD_NAMES =
		{Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.UID};

	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception;

	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion, Locale locale)
		throws Exception;

	public List<DDLRecord> getRecords(Hits hits) throws Exception;

	public JSONArray getRecordSetJSONArray(
			DDLRecordSet recordSet, Locale locale)
		throws Exception;

	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception;

	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception;

	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public boolean isEditable(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
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