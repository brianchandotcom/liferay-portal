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

package com.liferay.service.access.policy.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SAPEntry. This utility wraps
 * {@link com.liferay.service.access.policy.service.impl.SAPEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryService
 * @see com.liferay.service.access.policy.service.base.SAPEntryServiceBaseImpl
 * @see com.liferay.service.access.policy.service.impl.SAPEntryServiceImpl
 * @generated
 */
@ProviderType
public class SAPEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.policy.service.impl.SAPEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.service.access.policy.model.SAPEntry addSAPEntry(
		java.lang.String allowedServiceSignatures, boolean defaultSAPEntry,
		boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSAPEntry(allowedServiceSignatures, defaultSAPEntry,
			enabled, name, titleMap, serviceContext);
	}

	public static com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSAPEntry(sapEntry);
	}

	public static com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSAPEntry(sapEntryId);
	}

	public static com.liferay.service.access.policy.model.SAPEntry fetchSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchSAPEntry(companyId, name);
	}

	public static java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {
		return getService().getCompanySAPEntries(companyId, start, end);
	}

	public static java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.SAPEntry> obc) {
		return getService().getCompanySAPEntries(companyId, start, end, obc);
	}

	public static int getCompanySAPEntriesCount(long companyId) {
		return getService().getCompanySAPEntriesCount(companyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSAPEntry(companyId, name);
	}

	public static com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSAPEntry(sapEntryId);
	}

	public static com.liferay.service.access.policy.model.SAPEntry updateSAPEntry(
		long sapEntryId, java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSAPEntry(sapEntryId, allowedServiceSignatures,
			defaultSAPEntry, enabled, name, titleMap, serviceContext);
	}

	public static SAPEntryService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SAPEntryService service) {
	}

	private static ServiceTracker<SAPEntryService, SAPEntryService> _serviceTracker =
		ServiceTrackerFactory.open(SAPEntryService.class);
}