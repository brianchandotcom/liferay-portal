/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperience;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for SegmentsExperience. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsExperienceServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceService
 * @generated
 */
public class SegmentsExperienceServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperienceServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SegmentsExperience addSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperience(
			groupId, segmentsEntryId, plid, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static SegmentsExperience appendSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().appendSegmentsExperience(
			groupId, segmentsEntryId, plid, nameMap, active, serviceContext);
	}

	public static SegmentsExperience appendSegmentsExperience(
			long groupId, long segmentsEntryId, long plid,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().appendSegmentsExperience(
			groupId, segmentsEntryId, plid, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		return getService().deleteSegmentsExperience(segmentsExperienceId);
	}

	public static SegmentsExperience fetchSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws PortalException {

		return getService().fetchSegmentsExperience(
			groupId, segmentsExperienceKey, plid);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SegmentsExperience getSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		return getService().getSegmentsExperience(segmentsExperienceId);
	}

	public static SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws PortalException {

		return getService().getSegmentsExperience(
			groupId, segmentsExperienceKey, plid);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active)
		throws PortalException {

		return getService().getSegmentsExperiences(groupId, plid, active);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active, int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws PortalException {

		return getService().getSegmentsExperiences(
			groupId, plid, active, start, end, orderByComparator);
	}

	public static int getSegmentsExperiencesCount(
			long groupId, long plid, boolean active)
		throws PortalException {

		return getService().getSegmentsExperiencesCount(groupId, plid, active);
	}

	public static SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<java.util.Locale, String> nameMap, boolean active)
		throws PortalException {

		return getService().updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active);
	}

	public static SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties)
		throws PortalException {

		return getService().updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active,
			typeSettingsUnicodeProperties);
	}

	public static void updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws PortalException {

		getService().updateSegmentsExperiencePriority(
			segmentsExperienceId, newPriority);
	}

	public static SegmentsExperienceService getService() {
		return _service;
	}

	public static void setService(SegmentsExperienceService service) {
		_service = service;
	}

	private static volatile SegmentsExperienceService _service;

}