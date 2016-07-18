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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ScreensDDLRecord. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensDDLRecordServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensDDLRecordService
 * @see com.liferay.screens.service.base.ScreensDDLRecordServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensDDLRecordServiceImpl
 * @generated
 */
@ProviderType
public class ScreensDDLRecordServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensDDLRecordServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONArray getDDLRecords(
		long ddlRecordSetId, java.util.Locale locale, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDDLRecords(ddlRecordSetId, locale, start, end, obc);
	}

	public static com.liferay.portal.kernel.json.JSONArray getDDLRecords(
		long ddlRecordSetId, long userId, java.util.Locale locale, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDDLRecords(ddlRecordSetId, userId, locale, start, end,
			obc);
	}

	public static com.liferay.portal.kernel.json.JSONObject getDDLRecord(
		long ddlRecordId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecord(ddlRecordId, locale);
	}

	public static int getDDLRecordsCount(long ddlRecordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordsCount(ddlRecordSetId);
	}

	public static int getDDLRecordsCount(long ddlRecordSetId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordsCount(ddlRecordSetId, userId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ScreensDDLRecordService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ScreensDDLRecordService, ScreensDDLRecordService> _serviceTracker =
		ServiceTrackerFactory.open(ScreensDDLRecordService.class);
}