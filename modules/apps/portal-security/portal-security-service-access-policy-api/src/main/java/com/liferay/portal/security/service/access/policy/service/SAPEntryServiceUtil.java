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

package com.liferay.portal.security.service.access.policy.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for SAPEntry. This utility wraps
 * <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryService
 * @generated
 */
public class SAPEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SAPEntry addSAPEntry(
			String allowedServiceSignatures, boolean defaultSAPEntry,
			boolean enabled, String name,
			Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSAPEntry(
			allowedServiceSignatures, defaultSAPEntry, enabled, name, titleMap,
			serviceContext);
	}

	public static SAPEntry deleteSAPEntry(long sapEntryId)
		throws PortalException {

		return getService().deleteSAPEntry(sapEntryId);
	}

	public static SAPEntry deleteSAPEntry(SAPEntry sapEntry)
		throws PortalException {

		return getService().deleteSAPEntry(sapEntry);
	}

	public static SAPEntry fetchSAPEntry(long companyId, String name)
		throws PortalException {

		return getService().fetchSAPEntry(companyId, name);
	}

	public static List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {

		return getService().getCompanySAPEntries(companyId, start, end);
	}

	public static List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getService().getCompanySAPEntries(
			companyId, start, end, orderByComparator);
	}

	public static int getCompanySAPEntriesCount(long companyId) {
		return getService().getCompanySAPEntriesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SAPEntry getSAPEntry(long sapEntryId) throws PortalException {
		return getService().getSAPEntry(sapEntryId);
	}

	public static SAPEntry getSAPEntry(long companyId, String name)
		throws PortalException {

		return getService().getSAPEntry(companyId, name);
	}

	public static SAPEntry updateSAPEntry(
			long sapEntryId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateSAPEntry(
			sapEntryId, allowedServiceSignatures, defaultSAPEntry, enabled,
			name, titleMap, serviceContext);
	}

	public static SAPEntryService getService() {
		return _service;
	}

	public static void setService(SAPEntryService service) {
		_service = service;
	}

	private static volatile SAPEntryService _service;

}