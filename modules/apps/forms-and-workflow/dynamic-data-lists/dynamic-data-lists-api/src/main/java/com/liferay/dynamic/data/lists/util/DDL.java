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
	 * Returns the record's JSON Object representation. The latest approved
	 * record version will be used to transform.
	 *
	 * The Theme display locale will be used as the default locale.
	 *
	 * @param      record the record to transform
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getRecordJSONObject(DDLRecord,boolean,locale)}
	 */
	@Deprecated
	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception;

	/**
	 * Returns the localized value of a record as a JSON Object. If the latest
	 * record version is requested, it will be used for the transformation
	 * regardless of the version's workflow status.
	 *
	 * @param  record the record to transform
	 * @param  latestRecordVersion whether the lastest record version will be
	 *         used for the transformation regardless if the latest record
	 *         versions is a workflow pending or draft state.
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * Returns the localized record set as a JSON Array.
	 *
	 * @param  recordSet the record set to transform
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record set
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONArray getRecordSetJSONArray(
			DDLRecordSet recordSet, Locale locale)
		throws Exception;

	/**
	 * Returns a record set's records as a JSON Array. The JSON array will
	 * contain a list of JSON Objects.
	 *
	 * The Theme display locale will be used as the default locale and the
	 * latest record version will not be used if its workflow status is not
	 * approved.
	 *
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getRecordsJSONArray(List,boolean,locale)}
	 */
	@Deprecated
	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception;

	/**
	 * Returns a list of records as a JSON Array. The JSON array will contain a
	 * list of JSON Objects.
	 *
	 * The Theme display locale will be used as the default locale and the
	 * latest record version will not be used if its workflow status is not
	 * approved.
	 *
	 * @param      records the list of records to transform
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getRecordsJSONArray(List,boolean,locale)}
	 */
	@Deprecated
	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception;

	/**
	 * Transforms a list of records into a JSON Array. The JSON array will
	 * contain a list of record JSON Objects. If the latest record version is
	 * requested, it will be used for the transformation regardless of the
	 * version's workflow status.
	 *
	 * @param  records the list of records to transform
	 * @param  latestRecordVersion whether the lastest record version will be
	 *         used for the transformation regardless if the latest record
	 *         versions is a workflow pending or draft state.
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record
	 * @throws Exception if an unexpected exception occurred
	 * @see    #getRecordJSONObject(DDLRecord, boolean, Locale)
	 */
	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * Updates a record according to the form parameters passed in the request.
	 * The request parameters are wrapped in the ServiceContext parameter. If
	 * the passed record ID doesn't exist, a new record will be added.
	 * Otherwise, the existing record will be updated.
	 *
	 * @param  recordId the record ID to update
	 * @param  recordSetId the record set ID of the record
	 * @param  mergeFields whether the merge operation will be performed for the
	 *         existing record. If <code>true</code>, the missing localized
	 *         record values are updated for the existing record.
	 * @param  checkPermission whether the permission checker will be used to
	 *         validate credentials.
	 * @param  serviceContext the service context to be applied
	 * @throws Exception if an unexpected exception occurred
	 */
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception;

}