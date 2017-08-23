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
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.addFragmentEntry(groupId,
			fragmentCollectionId, name, css, html, js, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> deleteFragmentEntries(
		long[] fragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.deleteFragmentEntries(fragmentEntriesIds);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.deleteFragmentEntry(fragmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchFragmentEntries(
		long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.fetchFragmentEntries(fragmentCollectionId);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchFragmentEntry(
		long fragmentEntryId) {
		return _msbFragmentEntryService.fetchFragmentEntry(fragmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.getFragmentEntries(fragmentCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.getFragmentEntries(groupId,
			fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> obc) {
		return _msbFragmentEntryService.getFragmentEntries(groupId,
			fragmentCollectionId, name, start, end, obc);
	}

	@Override
	public int getGroupFragmentCollectionsCount(long fragmentCollectionId) {
		return _msbFragmentEntryService.getGroupFragmentCollectionsCount(fragmentCollectionId);
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
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryService.updateFragmentEntry(fragmentEntryId,
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