/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.service;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
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

	public AnnouncementsEntryServiceWrapper() {
		this(null);
	}

	public AnnouncementsEntryServiceWrapper(
		AnnouncementsEntryService announcementsEntryService) {

		_announcementsEntryService = announcementsEntryService;
	}

	@Override
	public AnnouncementsEntry addEntry(
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
	public AnnouncementsEntry getEntry(long entryId)
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
	public AnnouncementsEntry updateEntry(
			long entryId, String title, String content, String url, String type,
			java.util.Date displayDate, java.util.Date expirationDate,
			int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsEntryService.updateEntry(
			entryId, title, content, url, type, displayDate, expirationDate,
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