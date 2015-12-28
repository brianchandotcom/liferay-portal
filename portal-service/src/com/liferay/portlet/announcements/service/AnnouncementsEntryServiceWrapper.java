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

package com.liferay.portlet.announcements.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnnouncementsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryService
 * @generated
 */
@ProviderType
public class AnnouncementsEntryServiceWrapper
	implements AnnouncementsEntryService,
		ServiceWrapper<AnnouncementsEntryService> {
	public AnnouncementsEntryServiceWrapper(
		AnnouncementsEntryService announcementsEntryService) {
		_announcementsEntryService = announcementsEntryService;
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long plid, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean displayImmediately,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority,
		boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryService.addEntry(plid, classNameId, classPK,
			title, content, url, type, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			displayImmediately, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority, alert);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEntry(long, long, long,
	String, String, String, String, int, int, int, int, int,
	boolean, int, int, int, int, int, int, boolean)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long plid, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryService.addEntry(plid, classNameId, classPK,
			title, content, url, type, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, priority, alert);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_announcementsEntryService.deleteEntry(entryId);
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _announcementsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean displayImmediately,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryService.updateEntry(entryId, title, content,
			url, type, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, displayImmediately,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, priority);
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