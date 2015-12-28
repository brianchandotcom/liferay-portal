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
 * Provides a wrapper for {@link AnnouncementsFlagService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlagService
 * @generated
 */
@ProviderType
public class AnnouncementsFlagServiceWrapper implements AnnouncementsFlagService,
	ServiceWrapper<AnnouncementsFlagService> {
	public AnnouncementsFlagServiceWrapper(
		AnnouncementsFlagService announcementsFlagService) {
		_announcementsFlagService = announcementsFlagService;
	}

	@Override
	public void addFlag(long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException {
		_announcementsFlagService.addFlag(entryId, value);
	}

	@Override
	public void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_announcementsFlagService.deleteFlag(flagId);
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsFlagService.getFlag(entryId, value);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _announcementsFlagService.getOSGiServiceIdentifier();
	}

	@Override
	public AnnouncementsFlagService getWrappedService() {
		return _announcementsFlagService;
	}

	@Override
	public void setWrappedService(
		AnnouncementsFlagService announcementsFlagService) {
		_announcementsFlagService = announcementsFlagService;
	}

	private AnnouncementsFlagService _announcementsFlagService;
}