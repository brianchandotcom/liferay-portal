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

package com.liferay.modern.site.building.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for MSBFragmentEntry. This utility wraps
 * {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryService
 * @see com.liferay.modern.site.building.fragment.service.base.MSBFragmentEntryServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.impl.MSBFragmentEntryServiceImpl
 * @generated
 */
@ProviderType
public class MSBFragmentEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFragmentEntry(groupId, fragmentCollectionId, name, css,
			html, js, serviceContext);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> deleteFragmentEntries(
		long[] fragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFragmentEntries(fragmentEntriesIds);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFragmentEntry(fragmentEntryId);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchFragmentEntries(
		long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchFragmentEntries(fragmentCollectionId);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchFragmentEntry(
		long fragmentEntryId) {
		return getService().fetchFragmentEntry(fragmentEntryId);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFragmentEntries(fragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFragmentEntries(groupId, fragmentCollectionId, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> obc) {
		return getService()
				   .getFragmentEntries(groupId, fragmentCollectionId, name,
			start, end, obc);
	}

	public static int getGroupFragmentCollectionsCount(
		long fragmentCollectionId) {
		return getService()
				   .getGroupFragmentCollectionsCount(fragmentCollectionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFragmentEntry(fragmentEntryId, name, css, html, js);
	}

	public static MSBFragmentEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentEntryService, MSBFragmentEntryService> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentEntryService.class);
}