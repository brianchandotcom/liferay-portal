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
 * Provides the remote service utility for MSBFragmentCollection. This utility wraps
 * {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionService
 * @see com.liferay.modern.site.building.fragment.service.base.MSBFragmentCollectionServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionServiceImpl
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMSBFragmentCollection(groupId, name, description,
			serviceContext);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentCollection(msbFragmentCollectionId);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> deleteMSBFragmentCollections(
		long[] msbFragmentCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteMSBFragmentCollections(msbFragmentCollectionIds);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchMSBFragmentCollection(msbFragmentCollectionId);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMSBFragmentCollections(groupId, start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentCollections(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentCollections(groupId, name, start, end,
			orderByComparator);
	}

	public static int getMSBFragmentCollectionsCount(long groupId) {
		return getService().getMSBFragmentCollectionsCount(groupId);
	}

	public static int getMSBFragmentCollectionsCount(long groupId,
		java.lang.String name) {
		return getService().getMSBFragmentCollectionsCount(groupId, name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMSBFragmentCollection(msbFragmentCollectionId, name,
			description);
	}

	public static MSBFragmentCollectionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentCollectionService, MSBFragmentCollectionService> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentCollectionService.class);
}