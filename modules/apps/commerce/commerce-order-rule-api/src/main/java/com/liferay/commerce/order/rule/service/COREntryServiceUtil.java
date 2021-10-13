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

package com.liferay.commerce.order.rule.service;

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for COREntry. This utility wraps
 * <code>com.liferay.commerce.order.rule.service.impl.COREntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see COREntryService
 * @generated
 */
public class COREntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.order.rule.service.impl.COREntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static COREntry addCOREntry(
			String externalReferenceCode, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCOREntry(
			externalReferenceCode, active, description, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, type, typeSettings, serviceContext);
	}

	public static COREntry deleteCOREntry(long corEntryId)
		throws PortalException {

		return getService().deleteCOREntry(corEntryId);
	}

	public static COREntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static COREntry fetchCOREntry(long corEntryId)
		throws PortalException {

		return getService().fetchCOREntry(corEntryId);
	}

	public static List<COREntry> getCOREntries(
			long companyId, boolean active, int start, int end)
		throws PortalException {

		return getService().getCOREntries(companyId, active, start, end);
	}

	public static List<COREntry> getCOREntries(
			long companyId, boolean active, String type, int start, int end)
		throws PortalException {

		return getService().getCOREntries(companyId, active, type, start, end);
	}

	public static List<COREntry> getCOREntries(
			long companyId, String type, int start, int end)
		throws PortalException {

		return getService().getCOREntries(companyId, type, start, end);
	}

	public static COREntry getCOREntry(long corEntryId) throws PortalException {
		return getService().getCOREntry(corEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static COREntry updateCOREntry(
			long corEntryId, boolean active, String description,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String name, int priority, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCOREntry(
			corEntryId, active, description, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, name,
			priority, typeSettings, serviceContext);
	}

	public static COREntry updateCOREntryExternalReferenceCode(
			String externalReferenceCode, long corEntryId)
		throws PortalException {

		return getService().updateCOREntryExternalReferenceCode(
			externalReferenceCode, corEntryId);
	}

	public static COREntryService getService() {
		return _service;
	}

	private static volatile COREntryService _service;

}