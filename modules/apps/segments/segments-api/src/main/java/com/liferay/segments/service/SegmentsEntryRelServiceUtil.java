/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static void setService(SegmentsEntryRelService service) {
		_service = service;
	}

	private static volatile SegmentsEntryRelService _service;

}