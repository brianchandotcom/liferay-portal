/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.util;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents a utility class used by DDL applications.
 *
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDL {

	public static final String[] SELECTED_FIELD_NAMES = {
		Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.UID
	};

	/**
	 * Returns the localized value of the record as a JSON Object. If the latest
	 * version of the record is requested, it is transformed regardless of its
	 * workflow status.
	 *
	 * @param  record the record to transform
	 * @param  latestRecordVersion whether the latest version of the record is
	 *         to be transformed regardless of its workflow status, even if it's
	 *         in a pending or draft state.
	 * @param  locale a locale to use to retrieve the localized values of the
	 *         record
	 * @return the localized value of the record as a JSON Object
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * Returns the localized record set as a JSON Array.
	 *
	 * @param  recordSet the record set to transform
	 * @param  locale a locale to use to retrieve the localized values of the
	 *         record set
	 * @return the localized record set as a JSON Array
	 * @throws Exception if an unexpected exception occurred
	 */
	public JSONArray getRecordSetJSONArray(
			DDLRecordSet recordSet, Locale locale)
		throws Exception;

	/**
	 * Returns the records of the locale as a JSON Array. The JSON array
	 * contains a list of record JSON Objects. If the latest version of the
	 * records is requested, the records are transformed regardless of workflow
	 * status.
	 *
	 * @param  records the records to transform
	 * @param  latestRecordVersion whether the latest version of each record is
	 *         to be transformed regardless of its workflow status, even if the
	 *         record is in a pending or draft state.
	 * @param  locale a locale used to retrieve the localized values of the
	 *         record
	 * @return the records of the locale as a JSON Array
	 * @throws Exception if an unexpected exception occurred
	 * @see    #getRecordJSONObject(DDLRecord, boolean, Locale)
	 */
	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion, Locale locale)
		throws Exception;

	/**
	 * Updates the record according to the form parameters passed in the
	 * request. The request parameters are wrapped in the service context
	 * parameter. If a record matching the record ID doesn't exist, a new record
	 * is added. Otherwise, the existing record is updated.
	 *
	 * @param  recordId the record ID to update
	 * @param  recordSetId the record set ID of the record
	 * @param  mergeFields whether to perform the merge operation for the
	 *         existing record. If <code>true</code>, all missing localized
	 *         record values are updated for the existing record.
	 * @param  checkPermission whether to use the permission checker to validate
	 *         credentials
	 * @param  serviceContext the service context to be applied
	 * @return the record
	 * @throws Exception if an unexpected exception occurred
	 */
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception;

}