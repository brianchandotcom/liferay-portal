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
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMSBFragmentEntry(groupId, msbFragmentCollectionId, name,
			css, html, js, serviceContext);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> deleteMSBFragmentEntries(
		long[] msbFragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentEntries(msbFragmentEntriesIds);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentEntry(msbFragmentEntryId);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchMSBFragmentEntries(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchMSBFragmentEntries(msbFragmentCollectionId);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchMSBFragmentEntry(
		long msbFragmentEntryId) {
		return getService().fetchMSBFragmentEntry(msbFragmentEntryId);
	}

	public static int getMSBFragmentCollectionsCount(
		long msbFragmentCollectionId) {
		return getService()
				   .getMSBFragmentCollectionsCount(msbFragmentCollectionId);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long msbFragmentCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentEntries(msbFragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentEntries(groupId, msbFragmentCollectionId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator) {
		return getService()
				   .getMSBFragmentEntries(groupId, msbFragmentCollectionId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		long msbFragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMSBFragmentEntry(msbFragmentEntryId, name, css, html,
			js);
	}

	public static MSBFragmentEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentEntryService, MSBFragmentEntryService> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentEntryService.class);
}