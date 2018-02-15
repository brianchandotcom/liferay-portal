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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryService
 * @generated
 */
@ProviderType
public class FragmentEntryServiceWrapper implements FragmentEntryService,
	ServiceWrapper<FragmentEntryService> {
	public FragmentEntryServiceWrapper(
		FragmentEntryService fragmentEntryService) {
		_fragmentEntryService = fragmentEntryService;
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.addFragmentEntry(groupId,
			fragmentCollectionId, name, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.addFragmentEntry(groupId,
			fragmentCollectionId, fragmentEntryKey, name, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.addFragmentEntry(groupId,
			fragmentCollectionId, name, css, html, js, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.addFragmentEntry(groupId,
			fragmentCollectionId, fragmentEntryKey, name, css, html, js,
			status, serviceContext);
	}

	@Override
	public void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_fragmentEntryService.deleteFragmentEntries(fragmentEntriesIds);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.deleteFragmentEntry(fragmentEntryId);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.fetchFragmentEntry(fragmentEntryId);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId) {
		return _fragmentEntryService.getFragmentCollectionsCount(groupId,
			fragmentCollectionId);
	}

	@Override
	public int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		return _fragmentEntryService.getFragmentCollectionsCount(groupId,
			fragmentCollectionId, name);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId) {
		return _fragmentEntryService.getFragmentEntries(fragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int status) {
		return _fragmentEntryService.getFragmentEntries(fragmentCollectionId,
			status);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end) {
		return _fragmentEntryService.getFragmentEntries(groupId,
			fragmentCollectionId, start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator) {
		return _fragmentEntryService.getFragmentEntries(groupId,
			fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator) {
		return _fragmentEntryService.getFragmentEntries(groupId,
			fragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _fragmentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String[] getTempFileNames(long groupId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.getTempFileNames(groupId, folderName);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.updateFragmentEntry(fragmentEntryId, name);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryService.updateFragmentEntry(fragmentEntryId, name,
			css, html, js, status, serviceContext);
	}

	@Override
	public FragmentEntryService getWrappedService() {
		return _fragmentEntryService;
	}

	@Override
	public void setWrappedService(FragmentEntryService fragmentEntryService) {
		_fragmentEntryService = fragmentEntryService;
	}

	private FragmentEntryService _fragmentEntryService;
}