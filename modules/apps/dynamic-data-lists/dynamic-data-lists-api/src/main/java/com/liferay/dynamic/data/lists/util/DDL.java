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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

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
	 * Returns the record's JSON Object representation. The latest approved
	 * version of the record is transformed.
	 *
	 * <p>
	 * The Theme display locale is used as the default locale.
	 * </p>
	 *
	 * @param      record the record to transform
	 * @return     the record's JSON Object representation
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getRecordJSONObject(DDLRecord,boolean,locale)}
	 */
	@Deprecated
	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception;

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
	 * Returns the record set's records as a JSON Array. The JSON Array contains
	 * a list of JSON objects.
	 *
	 * <p>
	 * The Theme display locale is used as the default locale and the latest
	 * record version is not used if its workflow status is not approved.
	 * </p>
	 *
	 * @param      recordSet the record set from which to extract records
	 * @return     the record set's records as a JSON Array
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getRecordsJSONArray(List,boolean,locale)}
	 */
	@Deprecated
	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception;

	/**
	 * Returns the records as a JSON Array. The JSON array contains a list of
	 * JSON objects.
	 *
	 * <p>
	 * The Theme display locale is used as the default locale and the latest
	 * version of the record is not used if its workflow status is approved.
	 * </p>
	 *
	 * @param      records the records to transform
	 * @return     the records as a JSON Array
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getRecordsJSONArray(List,boolean,locale)}
	 */
	@Deprecated
	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
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
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public boolean isEditable(
			HttpServletRequest httpServletRequest, String portletId,
			long groupId)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public boolean isEditable(
			PortletPreferences preferences, String portletId, long groupId)
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

	/**
	 * Updates the record according to the form parameters passed in the
	 * request. The request parameters are wrapped in the service context
	 * parameter. If a record matching the record ID doesn't exist, a new record
	 * is added. Otherwise, the existing record is updated. This operation
	 * always check user credentials.
	 *
	 * @param      recordId the record ID to update
	 * @param      recordSetId the record set ID of the record
	 * @param      mergeFields whether to perform the merge operation for the
	 *             existing record. If <code>true</code>, all missing localized
	 *             record values are updated for the existing record.
	 * @param      serviceContext the service context to be applied
	 * @return     the record
	 * @throws     Exception if an unexpected exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #updateRecord(long,long,boolean,boolean,ServiceContext)}
	 */
	@Deprecated
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception;

}