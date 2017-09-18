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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MSBFragmentCollectionService}.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionService
 * @generated
 */
@ProviderType
public class MSBFragmentCollectionServiceWrapper
	implements MSBFragmentCollectionService,
		ServiceWrapper<MSBFragmentCollectionService> {
	public MSBFragmentCollectionServiceWrapper(
		MSBFragmentCollectionService msbFragmentCollectionService) {
		_msbFragmentCollectionService = msbFragmentCollectionService;
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection addMSBFragmentCollection(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.addMSBFragmentCollection(groupId,
			name, description, serviceContext);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection deleteMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.deleteMSBFragmentCollection(msbFragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> deleteMSBFragmentCollections(
		long[] msbFragmentCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.deleteMSBFragmentCollections(msbFragmentCollectionIds);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.fetchMSBFragmentCollection(msbFragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.getMSBFragmentCollections(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.getMSBFragmentCollections(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> getMSBFragmentCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.getMSBFragmentCollections(groupId,
			name, start, end, orderByComparator);
	}

	@Override
	public int getMSBFragmentCollectionsCount(long groupId) {
		return _msbFragmentCollectionService.getMSBFragmentCollectionsCount(groupId);
	}

	@Override
	public int getMSBFragmentCollectionsCount(long groupId,
		java.lang.String name) {
		return _msbFragmentCollectionService.getMSBFragmentCollectionsCount(groupId,
			name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _msbFragmentCollectionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentCollection updateMSBFragmentCollection(
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentCollectionService.updateMSBFragmentCollection(msbFragmentCollectionId,
			name, description);
	}

	@Override
	public MSBFragmentCollectionService getWrappedService() {
		return _msbFragmentCollectionService;
	}

	@Override
	public void setWrappedService(
		MSBFragmentCollectionService msbFragmentCollectionService) {
		_msbFragmentCollectionService = msbFragmentCollectionService;
	}

	private MSBFragmentCollectionService _msbFragmentCollectionService;
}