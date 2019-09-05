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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryService
 * @generated
 */
public class FragmentEntryServiceWrapper
	implements FragmentEntryService, ServiceWrapper<FragmentEntryService> {

	public FragmentEntryServiceWrapper(
		FragmentEntryService fragmentEntryService) {

		_fragmentEntryService = fragmentEntryService;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, long, int, int,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, int type,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, name, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, long, int, int,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, name, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, long, int, int,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, fragmentEntryKey, name, type, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, long, int, int,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, fragmentEntryKey, name, status,
			serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, fragmentEntryKey, name,
			previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, String, String,
	 String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, String css,
			String html, String js, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, name, css, html, js, type, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, String, String,
	 String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, String css,
			String html, String js, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, name, css, html, js, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, String, String,
	 String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, int type,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, fragmentEntryKey, name, css, html,
			js, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, String, String, String, String,
	 String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, fragmentEntryKey, name, css, html,
			js, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js,
			String configuration, long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.addFragmentEntry(
			groupId, fragmentCollectionId, fragmentEntryKey, name, css, html,
			js, configuration, previewFileEntryId, type, status,
			serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry copyFragmentEntry(
			long groupId, long fragmentEntryId, long fragmentCollectionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.copyFragmentEntry(
			groupId, fragmentEntryId, fragmentCollectionId, serviceContext);
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

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesCount(Long, Long)}
	 */
	@Deprecated
	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId) {

		return _fragmentEntryService.getFragmentCollectionsCount(
			groupId, fragmentCollectionId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesCount(Long, Long, Integer)}
	 */
	@Deprecated
	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, int status) {

		return _fragmentEntryService.getFragmentCollectionsCount(
			groupId, fragmentCollectionId, status);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesCount(Long, Long, String)}
	 */
	@Deprecated
	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, String name) {

		return _fragmentEntryService.getFragmentCollectionsCount(
			groupId, fragmentCollectionId, name);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesCount(Long, Long, String, Integer)}
	 */
	@Deprecated
	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, String name, int status) {

		return _fragmentEntryService.getFragmentCollectionsCount(
			groupId, fragmentCollectionId, name, status);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesCount(Long, Long, Integer)}
	 */
	@Deprecated
	@Override
	public int getFragmentCollectionsCountByType(
		long groupId, long fragmentCollectionId, int type) {

		return _fragmentEntryService.getFragmentCollectionsCountByType(
			groupId, fragmentCollectionId, type);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(long fragmentCollectionId) {

		return _fragmentEntryService.getFragmentEntries(fragmentCollectionId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesByStatus(Long, Long, Integer)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int status) {

		return _fragmentEntryService.getFragmentEntries(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end) {

		return _fragmentEntryService.getFragmentEntries(
			groupId, fragmentCollectionId, start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesByStatus(Long, Long, Integer, Integer, Integer, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntries(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntries(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesByNameAndStatus(Long, Long, String, Integer, Integer, Integer, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, String name, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntries(
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesByName(Long, Long, String, Integer, Integer, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntries(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByName(
			long groupId, long fragmentCollectionId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntriesByName(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByNameAndStatus(
			long groupId, long fragmentCollectionId, String name, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntriesByNameAndStatus(
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByStatus(
			long groupId, long fragmentCollectionId, int status) {

		return _fragmentEntryService.getFragmentEntriesByStatus(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByStatus(
			long groupId, long fragmentCollectionId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntriesByStatus(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getFragmentEntriesByTypeAndStatus(Long, Long, Integer, Integer)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByType(
			long groupId, long fragmentCollectionId, int type, int status) {

		return _fragmentEntryService.getFragmentEntriesByType(
			groupId, fragmentCollectionId, type, status);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByType(
			long groupId, long fragmentCollectionId, int type, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntriesByType(
			groupId, fragmentCollectionId, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByTypeAndStatus(
			long groupId, long fragmentCollectionId, int type, int status) {

		return _fragmentEntryService.getFragmentEntriesByTypeAndStatus(
			groupId, fragmentCollectionId, type, status);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByTypeAndStatus(
			long groupId, long fragmentCollectionId, int type, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return _fragmentEntryService.getFragmentEntriesByTypeAndStatus(
			groupId, fragmentCollectionId, type, status, start, end,
			orderByComparator);
	}

	@Override
	public int getFragmentEntriesCount(
		long groupId, long fragmentCollectionId) {

		return _fragmentEntryService.getFragmentEntriesCount(
			groupId, fragmentCollectionId);
	}

	@Override
	public int getFragmentEntriesCountByName(
		long groupId, long fragmentCollectionId, String name) {

		return _fragmentEntryService.getFragmentEntriesCountByName(
			groupId, fragmentCollectionId, name);
	}

	@Override
	public int getFragmentEntriesCountByNameAndStatus(
		long groupId, long fragmentCollectionId, String name, int status) {

		return _fragmentEntryService.getFragmentEntriesCountByNameAndStatus(
			groupId, fragmentCollectionId, name, status);
	}

	@Override
	public int getFragmentEntriesCountByStatus(
		long groupId, long fragmentCollectionId, int status) {

		return _fragmentEntryService.getFragmentEntriesCountByStatus(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public int getFragmentEntriesCountByType(
		long groupId, long fragmentCollectionId, int type) {

		return _fragmentEntryService.getFragmentEntriesCountByType(
			groupId, fragmentCollectionId, type);
	}

	@Override
	public int getFragmentEntriesCountByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status) {

		return _fragmentEntryService.getFragmentEntriesCountByTypeAndStatus(
			groupId, fragmentCollectionId, type, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fragmentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.getTempFileNames(groupId, folderName);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.moveFragmentEntry(
			fragmentEntryId, fragmentCollectionId);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.updateFragmentEntry(
			fragmentEntryId, previewFileEntryId);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.updateFragmentEntry(fragmentEntryId, name);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateFragmentEntry(long, String, String, String,
	 String, String, int)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js, status);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateFragmentEntry(long, String, String, String,
	 String, String, long, int)}
	 */
	@Deprecated
	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js, previewFileEntryId, status);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, String configuration, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js, configuration, status);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, String configuration, long previewFileEntryId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fragmentEntryService.updateFragmentEntry(
			fragmentEntryId, name, css, html, js, configuration,
			previewFileEntryId, status);
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