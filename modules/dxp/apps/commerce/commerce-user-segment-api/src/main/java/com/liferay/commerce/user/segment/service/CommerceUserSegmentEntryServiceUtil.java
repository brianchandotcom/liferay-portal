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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceUserSegmentEntry. This utility wraps
 * {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryService
 * @see com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryServiceBaseImpl
 * @see com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		java.util.Map<java.util.Locale, String> nameMap, String key,
		boolean active, boolean system, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceUserSegmentEntry(nameMap, key, active, system,
			priority, serviceContext);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentEntries(groupId, start, end,
			orderByComparator);
	}

	public static int getCommerceUserSegmentEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceUserSegmentEntriesCount(groupId);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry getCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommerceUserSegmentEntries(companyId, groupId,
			keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCommerceUserSegmentEntries(searchContext);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		boolean active, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceUserSegmentEntry(commerceUserSegmentEntryId,
			nameMap, key, active, priority, serviceContext);
	}

	public static CommerceUserSegmentEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserSegmentEntryService, CommerceUserSegmentEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceUserSegmentEntryService.class);

		ServiceTracker<CommerceUserSegmentEntryService, CommerceUserSegmentEntryService> serviceTracker =
			new ServiceTracker<CommerceUserSegmentEntryService, CommerceUserSegmentEntryService>(bundle.getBundleContext(),
				CommerceUserSegmentEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}