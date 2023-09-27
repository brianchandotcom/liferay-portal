/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.service;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for AnnouncementsFlag. This utility wraps
 * <code>com.liferay.portlet.announcements.service.impl.AnnouncementsFlagServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlagService
 * @generated
 */
public class AnnouncementsFlagServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.announcements.service.impl.AnnouncementsFlagServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addFlag(long entryId, int value) throws PortalException {
		getService().addFlag(entryId, value);
	}

	public static void deleteFlag(long flagId) throws PortalException {
		getService().deleteFlag(flagId);
	}

	public static AnnouncementsFlag getFlag(long entryId, int value)
		throws PortalException {

		return getService().getFlag(entryId, value);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AnnouncementsFlagService getService() {
		return _service;
	}

	public static void setService(AnnouncementsFlagService service) {
		_service = service;
	}

	private static volatile AnnouncementsFlagService _service;

}