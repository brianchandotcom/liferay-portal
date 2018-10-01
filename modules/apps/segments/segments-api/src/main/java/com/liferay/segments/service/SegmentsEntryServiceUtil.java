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

package com.liferay.segments.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SegmentsEntry. This utility wraps
 * {@link com.liferay.segments.service.impl.SegmentsEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryService
 * @see com.liferay.segments.service.base.SegmentsEntryServiceBaseImpl
 * @see com.liferay.segments.service.impl.SegmentsEntryServiceImpl
 * @generated
 */
@ProviderType
public class SegmentsEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.segments.service.impl.SegmentsEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.segments.model.SegmentsEntry addEntry(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, String key,
		String type, boolean active, String criteria,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(nameMap, descriptionMap, key, type, active,
			criteria, serviceContext);
	}

	public static com.liferay.segments.model.SegmentsEntry deleteEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEntry(entryId);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry> getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.segments.model.SegmentsEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntries(groupId, start, end, orderByComparator);
	}

	public static int getEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntriesCount(groupId);
	}

	public static com.liferay.segments.model.SegmentsEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(entryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.segments.model.SegmentsEntry> searchEntries(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchEntries(companyId, groupId, keywords, start, end, sort);
	}

	public static com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
		long entryId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, String key,
		boolean active, String criteria,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSegmentsEntry(entryId, nameMap, descriptionMap, key,
			active, criteria, serviceContext);
	}

	public static SegmentsEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SegmentsEntryService, SegmentsEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SegmentsEntryService.class);

		ServiceTracker<SegmentsEntryService, SegmentsEntryService> serviceTracker =
			new ServiceTracker<SegmentsEntryService, SegmentsEntryService>(bundle.getBundleContext(),
				SegmentsEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}