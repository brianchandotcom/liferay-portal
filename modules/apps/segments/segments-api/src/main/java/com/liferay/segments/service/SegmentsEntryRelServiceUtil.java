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
 * Provides the remote service utility for SegmentsEntryRel. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsEntryRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelService
 * @generated
 */
public class SegmentsEntryRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsEntryRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.segments.model.SegmentsEntryRel
			addSegmentsEntryRel(
				long segmentsEntryId, long classNameId, long classPK,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK, serviceContext);
	}

	public static void deleteSegmentsEntryRel(long segmentsEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteSegmentsEntryRel(segmentsEntryRelId);
	}

	public static void deleteSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntryRel>
			getSegmentsEntryRels(long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntryRels(segmentsEntryId);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntryRel>
			getSegmentsEntryRels(
				long segmentsEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.segments.model.SegmentsEntryRel>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntryRels(
			segmentsEntryId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntryRel>
			getSegmentsEntryRels(long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntryRels(groupId, classNameId, classPK);
	}

	public static int getSegmentsEntryRelsCount(long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntryRelsCount(segmentsEntryId);
	}

	public static int getSegmentsEntryRelsCount(
			long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntryRelsCount(
			groupId, classNameId, classPK);
	}

	public static boolean hasSegmentsEntryRel(
		long segmentsEntryId, long classNameId, long classPK) {

		return getService().hasSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	public static SegmentsEntryRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsEntryRelService, SegmentsEntryRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SegmentsEntryRelService.class);

		ServiceTracker<SegmentsEntryRelService, SegmentsEntryRelService>
			serviceTracker =
				new ServiceTracker
					<SegmentsEntryRelService, SegmentsEntryRelService>(
						bundle.getBundleContext(),
						SegmentsEntryRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}