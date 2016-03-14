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
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;

/**
 * Represents a utility class used by DDL applications.
 *
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDL {

	public static final String[] SELECTED_FIELD_NAMES =
		{Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.UID};

	/**
	 * Returns the JSON Object representation of the given record. The latest approved record version will be used to transform.
	 *
	 * The Theme display locale will be used as default locale.
	 *
	 * @param  record the record to transform
	 * @throws Exception if an unexpected exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #getRecordJSONObject(DDLRecord,boolean,locale)}
	 */
	@Deprecated
	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception;

	/**
	 * Returns the JSON Object representation of the given record.
	 *
	 * @param  record the record to transform
	 * @param  latestRecordVersion whether the lastest record version will be used for the transformation regardless if the latest record versions is a workflow pending or draft state.
	 * @param  locale the locale used to retrieve the localized values of the record
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * Returns the JSON Array representation of the given record set.
	 *
	 * @param  recorSet the record set to transform
	 * @param  locale the locale used to retrieve the localized values of the record set
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONArray getRecordSetJSONArray(
			DDLRecordSet recordSet, Locale locale)
		throws Exception;

	/**
	 * Returns the JSON Array representation of the records a given record set.
	 * The JSON array will contain a list of JSON Objects.
	 *
	 * The Theme display locale will be used as default locale and the latestRecordVersion will be considered <code>false</code>.
	 *
	 * @param  recorSet the record set containing the records to transform
	 * @throws Exception if an unexpected exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #getRecordsJSONArray(List,boolean,locale)}
	 */
	@Deprecated
	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception;

	/**
	 * Returns the JSON Array representation of the records. The JSON array will contain a list of JSON Objects.
	 *
	 * The Theme display locale will be used as default locale and the latestRecordVersion will be considered <code>false</code>.
	 *
	 * @param  records the list of records to transform
	 * @throws Exception if an unexpected exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #getRecordsJSONArray(List,boolean,locale)}
	 */
	@Deprecated
	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception;

	/**
	 * Returns the JSON Array representation of the records. The JSON array will contain a list of record JSON Objects.
	 *
	 * @see #getRecordJSONObject(DDLRecord, boolean, Locale)
	 *
	 * @param  records the list of records to transform
	 * @param  latestRecordVersion whether the lastest record version will be used for the transformation regardless if the latest record versions is a workflow pending or draft state.
	 * @param  locale the locale used to retrieve the localized values of the record
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * Updates the record values according to the form parameters passed in the the request. The request parameters are wrapped in the ServiceContext parameter.
	 * If the passed record ID do not exists. A new record will be added. Otherwise the existing recod will be updated.
	 *
	 * @param  recordId the record ID to update
	 * @param  recordSetId the record set ID of the record
	 * @param  mergeFields whether the merge operation will be performed for the existing record. If <code>true</code>, the missing localized record values are updated for the existing record.
	 * @param  checkPermission whether the permission checker will be used to validate credentials.
	 * @param  serviceContext the service context to be applied
	 * @throws Exception if an unexpected exception occurred
	 */
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception;

}