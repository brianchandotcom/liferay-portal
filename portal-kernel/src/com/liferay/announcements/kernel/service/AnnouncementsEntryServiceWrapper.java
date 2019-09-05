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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnnouncementsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryService
 * @generated
 */
public class AnnouncementsEntryServiceWrapper
	implements AnnouncementsEntryService,
			   ServiceWrapper<AnnouncementsEntryService> {

	public AnnouncementsEntryServiceWrapper(
		AnnouncementsEntryService announcementsEntryService) {

		_announcementsEntryService = announcementsEntryService;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addEntry(long,
	 long, String, String, String, String, Date, Date, int,
	 boolean)}
	 */
	@Deprecated
	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsEntry addEntry(
			long plid, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean displayImmediately,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsEntryService.addEntry(
			plid, classNameId, classPK, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayImmediately, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority, alert);
	}

	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsEntry addEntry(
			long classNameId, long classPK, String title, String content,
			String url, String type, java.util.Date displayDate,
			java.util.Date expirationDate, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsEntryService.addEntry(
			classNameId, classPK, title, content, url, type, displayDate,
			expirationDate, priority, alert);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_announcementsEntryService.deleteEntry(entryId);
	}

	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsEntry getEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsEntryService.getEntry(entryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _announcementsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsEntry
			updateEntry(
				long entryId, String title, String content, String url,
				String type, java.util.Date displayDate,
				java.util.Date expirationDate, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsEntryService.updateEntry(
			entryId, title, content, url, type, displayDate, expirationDate,
			priority);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateEntry(long,
	 String, String, String, String, Date, Date, int)}
	 */
	@Deprecated
	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsEntry
			updateEntry(
				long entryId, String title, String content, String url,
				String type, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				boolean displayImmediately, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsEntryService.updateEntry(
			entryId, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			displayImmediately, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority);
	}

	@Override
	public AnnouncementsEntryService getWrappedService() {
		return _announcementsEntryService;
	}

	@Override
	public void setWrappedService(
		AnnouncementsEntryService announcementsEntryService) {

		_announcementsEntryService = announcementsEntryService;
	}

	private AnnouncementsEntryService _announcementsEntryService;

}