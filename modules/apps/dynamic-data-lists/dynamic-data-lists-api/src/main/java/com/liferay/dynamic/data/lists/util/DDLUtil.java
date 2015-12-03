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
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
@ProviderType
public class DDLUtil {

	public static DDL getDDL() {
		PortalRuntimePermission.checkGetBeanProperty(DDLUtil.class);

		return _serviceTracker.getService();
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean isEditable(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception {

		return getDDL().isEditable(request, portletId, groupId);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean isEditable(
			PortletPreferences preferences, String portletId, long groupId)
		throws Exception {

		return getDDL().isEditable(preferences, portletId, groupId);
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

	private static final ServiceTracker<DDL, DDL> _serviceTracker =
		ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DDLUtil.class), DDL.class);

}