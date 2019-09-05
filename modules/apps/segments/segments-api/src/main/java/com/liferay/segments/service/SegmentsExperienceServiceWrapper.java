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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link SegmentsExperienceService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceService
 * @generated
 */
@ProviderType
public class SegmentsExperienceServiceWrapper
	implements SegmentsExperienceService,
			   ServiceWrapper<SegmentsExperienceService> {

	public SegmentsExperienceServiceWrapper(
		SegmentsExperienceService segmentsExperienceService) {

		_segmentsExperienceService = segmentsExperienceService;
	}

	@Override
	public com.liferay.segments.model.SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			serviceContext);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperience
			deleteSegmentsExperience(long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.deleteSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperience
			fetchSegmentsExperience(long groupId, String segmentsExperienceKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.fetchSegmentsExperience(
			groupId, segmentsExperienceKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperienceService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.segments.model.SegmentsExperience getSegmentsExperience(
			long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperience>
			getSegmentsExperiences(
				long groupId, long classNameId, long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperiences(
			groupId, classNameId, classPK, active);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperience>
			getSegmentsExperiences(
				long groupId, long classNameId, long classPK, boolean active,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsExperience>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperiences(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	@Override
	public int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperiencesCount(
			groupId, classNameId, classPK, active);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperience
			updateSegmentsExperience(
				long segmentsExperienceId, long segmentsEntryId,
				java.util.Map<java.util.Locale, String> nameMap, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active);
	}

	@Override
	public void updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperienceService.updateSegmentsExperiencePriority(
			segmentsExperienceId, newPriority);
	}

	@Override
	public SegmentsExperienceService getWrappedService() {
		return _segmentsExperienceService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperienceService segmentsExperienceService) {

		_segmentsExperienceService = segmentsExperienceService;
	}

	private SegmentsExperienceService _segmentsExperienceService;

}