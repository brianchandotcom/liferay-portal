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
 * Provides the remote service utility for AnnouncementsEntry. This utility wraps
 * <code>com.liferay.portlet.announcements.service.impl.AnnouncementsEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryService
 * @generated
 */
public class AnnouncementsEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.announcements.service.impl.AnnouncementsEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addEntry(long,
	 long, String, String, String, String, Date, Date, int,
	 boolean)}
	 */
	@Deprecated
	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			addEntry(
				long plid, long classNameId, long classPK, String title,
				String content, String url, String type, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, boolean displayImmediately,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			plid, classNameId, classPK, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayImmediately, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority, alert);
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			addEntry(
				long classNameId, long classPK, String title, String content,
				String url, String type, java.util.Date displayDate,
				java.util.Date expirationDate, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			classNameId, classPK, title, content, url, type, displayDate,
			expirationDate, priority, alert);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteEntry(entryId);
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntry(entryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			updateEntry(
				long entryId, String title, String content, String url,
				String type, java.util.Date displayDate,
				java.util.Date expirationDate, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			entryId, title, content, url, type, displayDate, expirationDate,
			priority);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateEntry(long,
	 String, String, String, String, Date, Date, int)}
	 */
	@Deprecated
	public static com.liferay.announcements.kernel.model.AnnouncementsEntry
			updateEntry(
				long entryId, String title, String content, String url,
				String type, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				boolean displayImmediately, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			entryId, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			displayImmediately, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority);
	}

	public static AnnouncementsEntryService getService() {
		if (_service == null) {
			_service = (AnnouncementsEntryService)PortalBeanLocatorUtil.locate(
				AnnouncementsEntryService.class.getName());
		}

		return _service;
	}

	private static AnnouncementsEntryService _service;

}