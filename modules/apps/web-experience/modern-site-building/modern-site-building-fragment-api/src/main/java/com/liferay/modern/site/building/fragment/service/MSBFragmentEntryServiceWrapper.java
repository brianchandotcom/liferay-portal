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
 * Provides a wrapper for {@link MSBFragmentEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryService
 * @generated
 */
@ProviderType
public class MSBFragmentEntryServiceWrapper implements MSBFragmentEntryService,
	ServiceWrapper<MSBFragmentEntryService> {
	public MSBFragmentEntryServiceWrapper(
		MSBFragmentEntryService msbFragmentEntryService) {
		_msbFragmentEntryService = msbFragmentEntryService;
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.addMSBFragmentEntry(groupId,
			msbFragmentCollectionId, name, css, html, js, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> deleteMSBFragmentEntries(
		long[] msbFragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.deleteMSBFragmentEntries(msbFragmentEntriesIds);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.deleteMSBFragmentEntry(msbFragmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchMSBFragmentEntries(
		long msbFragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.fetchMSBFragmentEntries(msbFragmentCollectionId);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchMSBFragmentEntry(
		long msbFragmentEntryId) {
		return _msbFragmentEntryService.fetchMSBFragmentEntry(msbFragmentEntryId);
	}

	@Override
	public int getMSBFragmentCollectionsCount(long msbFragmentCollectionId) {
		return _msbFragmentEntryService.getMSBFragmentCollectionsCount(msbFragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long msbFragmentCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.getMSBFragmentEntries(msbFragmentCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.getMSBFragmentEntries(groupId,
			msbFragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator) {
		return _msbFragmentEntryService.getMSBFragmentEntries(groupId,
			msbFragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _msbFragmentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		long msbFragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.updateMSBFragmentEntry(msbFragmentEntryId,
			name, css, html, js);
	}

	@Override
	public MSBFragmentEntryService getWrappedService() {
		return _msbFragmentEntryService;
	}

	@Override
	public void setWrappedService(
		MSBFragmentEntryService msbFragmentEntryService) {
		_msbFragmentEntryService = msbFragmentEntryService;
	}

	private MSBFragmentEntryService _msbFragmentEntryService;
}