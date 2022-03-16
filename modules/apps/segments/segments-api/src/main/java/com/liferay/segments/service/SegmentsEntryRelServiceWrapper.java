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
import com.liferay.segments.model.SegmentsEntryRel;

/**
 * Provides a wrapper for {@link SegmentsEntryRelService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelService
 * @generated
 */
public class SegmentsEntryRelServiceWrapper
	implements SegmentsEntryRelService,
			   ServiceWrapper<SegmentsEntryRelService> {

	public SegmentsEntryRelServiceWrapper() {
		this(null);
	}

	public SegmentsEntryRelServiceWrapper(
		SegmentsEntryRelService segmentsEntryRelService) {

		_segmentsEntryRelService = segmentsEntryRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<SegmentsEntryRel> getSegmentsEntryRels(
			long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelService.getSegmentsEntryRels(segmentsEntryId);
	}

	@Override
	public java.util.List<SegmentsEntryRel> getSegmentsEntryRels(
			long segmentsEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsEntryRel>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelService.getSegmentsEntryRels(
			segmentsEntryId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<SegmentsEntryRel> getSegmentsEntryRels(
			long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelService.getSegmentsEntryRels(
			groupId, classNameId, classPK);
	}

	@Override
	public int getSegmentsEntryRelsCount(long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelService.getSegmentsEntryRelsCount(
			segmentsEntryId);
	}

	@Override
	public int getSegmentsEntryRelsCount(
			long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelService.getSegmentsEntryRelsCount(
			groupId, classNameId, classPK);
	}

	@Override
	public boolean hasSegmentsEntryRel(
		long segmentsEntryId, long classNameId, long classPK) {

		return _segmentsEntryRelService.hasSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	@Override
	public SegmentsEntryRelService getWrappedService() {
		return _segmentsEntryRelService;
	}

	@Override
	public void setWrappedService(
		SegmentsEntryRelService segmentsEntryRelService) {

		_segmentsEntryRelService = segmentsEntryRelService;
	}

	private SegmentsEntryRelService _segmentsEntryRelService;

}