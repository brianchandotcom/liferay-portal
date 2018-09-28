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
	public SegmentsEntryService getWrappedService() {
		return _segmentsEntryService;
	}

	@Override
	public void setWrappedService(SegmentsEntryService segmentsEntryService) {
		_segmentsEntryService = segmentsEntryService;
	}

	private SegmentsEntryService _segmentsEntryService;
}