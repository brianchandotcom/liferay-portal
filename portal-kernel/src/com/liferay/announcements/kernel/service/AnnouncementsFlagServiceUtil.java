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

package com.liferay.announcements.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

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
	public static void addFlag(long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addFlag(entryId, value);
	}

	public static void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFlag(flagId);
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsFlag
			getFlag(long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException {

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
		if (_service == null) {
			_service = (AnnouncementsFlagService)PortalBeanLocatorUtil.locate(
				AnnouncementsFlagService.class.getName());
		}

		return _service;
	}

	private static AnnouncementsFlagService _service;

}