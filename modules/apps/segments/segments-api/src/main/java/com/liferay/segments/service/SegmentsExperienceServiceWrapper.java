/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.segments.model.SegmentsExperience;

/**
 * Provides a wrapper for {@link SegmentsExperienceService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceService
 * @generated
 */
public class SegmentsExperienceServiceWrapper
	implements SegmentsExperienceService,
			   ServiceWrapper<SegmentsExperienceService> {

	public SegmentsExperienceServiceWrapper() {
		this(null);
	}

	public SegmentsExperienceServiceWrapper(
		SegmentsExperienceService segmentsExperienceService) {

		_segmentsExperienceService = segmentsExperienceService;
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.addSegmentsExperience(
			groupId, segmentsEntryId, plid, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.appendSegmentsExperience(
			groupId, segmentsEntryId, plid, nameMap, active, serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.appendSegmentsExperience(
			groupId, segmentsEntryId, plid, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.deleteSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.fetchSegmentsExperience(
			groupId, segmentsExperienceKey, plid);
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
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperience(
			groupId, segmentsExperienceKey, plid);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperiences(
			groupId, plid, active);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperiences(
			groupId, plid, active, start, end, orderByComparator);
	}

	@Override
	public int getSegmentsExperiencesCount(
			long groupId, long plid, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.getSegmentsExperiencesCount(
			groupId, plid, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			java.util.Map<java.util.Locale, String> nameMap, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceService.updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active,
			typeSettingsUnicodeProperties);
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