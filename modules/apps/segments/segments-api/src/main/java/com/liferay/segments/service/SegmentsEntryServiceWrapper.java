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
	public com.liferay.segments.model.SegmentsEntry addSegmentsEntry(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, boolean active,
		String criteria, String key, String source, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.addSegmentsEntry(nameMap, descriptionMap,
			active, criteria, key, source, type, serviceContext);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry deleteSegmentsEntry(
		long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.deleteSegmentsEntry(segmentsEntryId);
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
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getSegmentsEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.getSegmentsEntries(groupId);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.segments.model.SegmentsEntry> orderByComparator) {
		return _segmentsEntryService.getSegmentsEntries(groupId,
			includeAncestorSegmentsEntries, start, end, orderByComparator);
	}

	@Override
	public int getSegmentsEntriesCount(long groupId,
		boolean includeAncestorSegmentsEntries) {
		return _segmentsEntryService.getSegmentsEntriesCount(groupId,
			includeAncestorSegmentsEntries);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry getSegmentsEntry(
		long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.getSegmentsEntry(segmentsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
		long companyId, long groupId, String keywords,
		boolean includeAncestorSegmentsEntries, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.searchSegmentsEntries(companyId, groupId,
			keywords, includeAncestorSegmentsEntries, start, end, sort);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
		long segmentsEntryId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, boolean active,
		String criteria, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsEntryService.updateSegmentsEntry(segmentsEntryId,
			nameMap, descriptionMap, active, criteria, key, serviceContext);
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