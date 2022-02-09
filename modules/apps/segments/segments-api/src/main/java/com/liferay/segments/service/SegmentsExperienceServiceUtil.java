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
			long segmentsEntryId, long classNameId, long classPK,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static SegmentsExperience appendSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().appendSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			serviceContext);
	}

	public static SegmentsExperience appendSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().appendSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		return getService().deleteSegmentsExperience(segmentsExperienceId);
	}

	public static SegmentsExperience fetchSegmentsExperience(
			long groupId, String segmentsExperienceKey, long classNameId,
			long classPK)
		throws PortalException {

		return getService().fetchSegmentsExperience(
			groupId, segmentsExperienceKey, classNameId, classPK);
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

	public static List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active)
		throws PortalException {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK, active);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active,
			int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator)
		throws PortalException {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	public static int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active)
		throws PortalException {

		return getService().getSegmentsExperiencesCount(
			groupId, classNameId, classPK, active);
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

	private static volatile SegmentsExperienceService _service;

}