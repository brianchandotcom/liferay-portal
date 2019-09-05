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

/**
 * Provides a wrapper for {@link SegmentsExperimentRelService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelService
 * @generated
 */
public class SegmentsExperimentRelServiceWrapper
	implements SegmentsExperimentRelService,
			   ServiceWrapper<SegmentsExperimentRelService> {

	public SegmentsExperimentRelServiceWrapper(
		SegmentsExperimentRelService segmentsExperimentRelService) {

		_segmentsExperimentRelService = segmentsExperimentRelService;
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			addSegmentsExperimentRel(
				long segmentsExperimentId, long segmentsExperienceId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelService.addSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId, serviceContext);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			deleteSegmentsExperimentRel(long segmentsExperimentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelService.deleteSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperimentRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			getSegmentsExperimentRel(
				long segmentsExperimentId, long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelService.getSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperimentRel>
			getSegmentsExperimentRels(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelService.getSegmentsExperimentRels(
			segmentsExperimentId);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				long segmentsExperimentRelId, double split)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelService.updateSegmentsExperimentRel(
			segmentsExperimentRelId, split);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				long segmentsExperimentRelId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelService.updateSegmentsExperimentRel(
			segmentsExperimentRelId, name, serviceContext);
	}

	@Override
	public SegmentsExperimentRelService getWrappedService() {
		return _segmentsExperimentRelService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperimentRelService segmentsExperimentRelService) {

		_segmentsExperimentRelService = segmentsExperimentRelService;
	}

	private SegmentsExperimentRelService _segmentsExperimentRelService;

}