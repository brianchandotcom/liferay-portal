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

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.segments.model.SegmentsEntry;

/**
 * Provides a wrapper for {@link SegmentsEntryService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryService
 * @generated
 */
public class SegmentsEntryServiceWrapper
	implements SegmentsEntryService, ServiceWrapper<SegmentsEntryService> {

	public SegmentsEntryServiceWrapper() {
		this(null);
	}

	public SegmentsEntryServiceWrapper(
		SegmentsEntryService segmentsEntryService) {

		_segmentsEntryService = segmentsEntryService;
	}

	@Override
	public SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryService.addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, type,
			serviceContext);
	}

	@Override
	public SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria, String source, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryService.addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, source,
			type, serviceContext);
	}

	@Override
	public void addSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsEntryService.addSegmentsEntryClassPKs(
			segmentsEntryId, classPKs, serviceContext);
	}

	@Override
	public SegmentsEntry deleteSegmentsEntry(long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryService.deleteSegmentsEntry(segmentsEntryId);
	}

	@Override
	public void deleteSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsEntryService.deleteSegmentsEntryClassPKs(
			segmentsEntryId, classPKs);
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
	public java.util.List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries) {

		return _segmentsEntryService.getSegmentsEntries(
			groupId, includeAncestorSegmentsEntries);
	}

	@Override
	public java.util.List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntry>
			orderByComparator) {

		return _segmentsEntryService.getSegmentsEntries(
			groupId, includeAncestorSegmentsEntries, start, end,
			orderByComparator);
	}

	@Override
	public int getSegmentsEntriesCount(
		long groupId, boolean includeAncestorSegmentsEntries) {

		return _segmentsEntryService.getSegmentsEntriesCount(
			groupId, includeAncestorSegmentsEntries);
	}

	@Override
	public SegmentsEntry getSegmentsEntry(long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryService.getSegmentsEntry(segmentsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<SegmentsEntry>
			searchSegmentsEntries(
				long companyId, long groupId, String keywords,
				boolean includeAncestorSegmentsEntries, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryService.searchSegmentsEntries(
			companyId, groupId, keywords, includeAncestorSegmentsEntries, start,
			end, sort);
	}

	@Override
	public SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryService.updateSegmentsEntry(
			segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap, active,
			criteria, serviceContext);
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