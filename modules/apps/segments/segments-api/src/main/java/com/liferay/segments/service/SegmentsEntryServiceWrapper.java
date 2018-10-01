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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SegmentsEntryService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryService
 * @generated
 */
@ProviderType
public class SegmentsEntryServiceWrapper implements SegmentsEntryService,
	ServiceWrapper<SegmentsEntryService> {
	public SegmentsEntryServiceWrapper(
		SegmentsEntryService segmentsEntryService) {
		_segmentsEntryService = segmentsEntryService;
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry addEntry(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, String key,
		String type, boolean active, String criteria,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.addEntry(nameMap, descriptionMap, key,
			type, active, criteria, serviceContext);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.deleteEntry(entryId);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.segments.model.SegmentsEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.getEntries(groupId, start, end,
			orderByComparator);
	}

	@Override
	public int getEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.getEntriesCount(groupId);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.getEntry(entryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.segments.model.SegmentsEntry> searchEntries(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.searchEntries(companyId, groupId,
			keywords, start, end, sort);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
		long entryId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, String key,
		boolean active, String criteria,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.updateSegmentsEntry(entryId, nameMap,
			descriptionMap, key, active, criteria, serviceContext);
	}

	@Override
	public SegmentsEntryService getWrappedService() {
		return _segmentsEntryService;
	}

	@Override
	public void setWrappedService(SegmentsEntryService segmentsEntryService) {
		_segmentsEntryService = segmentsEntryService;
	}

	private SegmentsEntryService _segmentsEntryService;
}