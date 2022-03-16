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
import com.liferay.segments.model.SegmentsEntryRel;

import java.util.List;

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

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<SegmentsEntryRel> getSegmentsEntryRels(
			long segmentsEntryId)
		throws PortalException {

		return getService().getSegmentsEntryRels(segmentsEntryId);
	}

	public static List<SegmentsEntryRel> getSegmentsEntryRels(
			long segmentsEntryId, int start, int end,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws PortalException {

		return getService().getSegmentsEntryRels(
			segmentsEntryId, start, end, orderByComparator);
	}

	public static List<SegmentsEntryRel> getSegmentsEntryRels(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		return getService().getSegmentsEntryRels(groupId, classNameId, classPK);
	}

	public static int getSegmentsEntryRelsCount(long segmentsEntryId)
		throws PortalException {

		return getService().getSegmentsEntryRelsCount(segmentsEntryId);
	}

	public static int getSegmentsEntryRelsCount(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		return getService().getSegmentsEntryRelsCount(
			groupId, classNameId, classPK);
	}

	public static boolean hasSegmentsEntryRel(
		long segmentsEntryId, long classNameId, long classPK) {

		return getService().hasSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	public static SegmentsEntryRelService getService() {
		return _service;
	}

	private static volatile SegmentsEntryRelService _service;

}