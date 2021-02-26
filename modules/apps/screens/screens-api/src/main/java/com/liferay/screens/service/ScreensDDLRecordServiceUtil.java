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

package com.liferay.screens.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * Provides the remote service utility for ScreensDDLRecord. This utility wraps
 * <code>com.liferay.screens.service.impl.ScreensDDLRecordServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensDDLRecordService
 * @generated
 */
public class ScreensDDLRecordServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.screens.service.impl.ScreensDDLRecordServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONObject getDDLRecord(
			long ddlRecordId, java.util.Locale locale)
		throws PortalException {

		return getService().getDDLRecord(ddlRecordId, locale);
	}

	public static com.liferay.portal.kernel.json.JSONArray getDDLRecords(
			long ddlRecordSetId, java.util.Locale locale, int start, int end,
			OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord>
				orderByComparator)
		throws PortalException {

		return getService().getDDLRecords(
			ddlRecordSetId, locale, start, end, orderByComparator);
	}

	public static com.liferay.portal.kernel.json.JSONArray getDDLRecords(
			long ddlRecordSetId, long userId, java.util.Locale locale,
			int start, int end,
			OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord>
				orderByComparator)
		throws PortalException {

		return getService().getDDLRecords(
			ddlRecordSetId, userId, locale, start, end, orderByComparator);
	}

	public static int getDDLRecordsCount(long ddlRecordSetId)
		throws PortalException {

		return getService().getDDLRecordsCount(ddlRecordSetId);
	}

	public static int getDDLRecordsCount(long ddlRecordSetId, long userId)
		throws PortalException {

		return getService().getDDLRecordsCount(ddlRecordSetId, userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ScreensDDLRecordService getService() {
		return _service;
	}

	private static volatile ScreensDDLRecordService _service;

}