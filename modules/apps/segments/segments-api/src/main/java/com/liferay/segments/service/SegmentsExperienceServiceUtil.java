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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.segments.model.SegmentsExperience
			addSegmentsExperience(
				long segmentsEntryId, long classNameId, long classPK,
				java.util.Map<java.util.Locale, String> nameMap, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			serviceContext);
	}

	public static com.liferay.segments.model.SegmentsExperience
			appendSegmentsExperience(
				long segmentsEntryId, long classNameId, long classPK,
				java.util.Map<java.util.Locale, String> nameMap, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().appendSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			serviceContext);
	}

	public static com.liferay.segments.model.SegmentsExperience
			deleteSegmentsExperience(long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsExperience(segmentsExperienceId);
	}

	public static com.liferay.segments.model.SegmentsExperience
			fetchSegmentsExperience(long groupId, String segmentsExperienceKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchSegmentsExperience(
			groupId, segmentsExperienceKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.segments.model.SegmentsExperience
			getSegmentsExperience(long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperience(segmentsExperienceId);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperience>
			getSegmentsExperiences(
				long groupId, long classNameId, long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK, active);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperience>
			getSegmentsExperiences(
				long groupId, long classNameId, long classPK, boolean active,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsExperience>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	public static int getSegmentsExperiencesCount(
			long groupId, long classNameId, long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperiencesCount(
			groupId, classNameId, classPK, active);
	}

	public static com.liferay.segments.model.SegmentsExperience
			updateSegmentsExperience(
				long segmentsExperienceId, long segmentsEntryId,
				java.util.Map<java.util.Locale, String> nameMap, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active);
	}

	public static void updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateSegmentsExperiencePriority(
			segmentsExperienceId, newPriority);
	}

	public static SegmentsExperienceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsExperienceService, SegmentsExperienceService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsExperienceService.class);

		ServiceTracker<SegmentsExperienceService, SegmentsExperienceService>
			serviceTracker =
				new ServiceTracker
					<SegmentsExperienceService, SegmentsExperienceService>(
						bundle.getBundleContext(),
						SegmentsExperienceService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}