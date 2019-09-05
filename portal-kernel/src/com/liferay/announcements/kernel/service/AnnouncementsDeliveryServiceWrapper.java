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
 * Provides a wrapper for {@link AnnouncementsDeliveryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryService
 * @generated
 */
public class AnnouncementsDeliveryServiceWrapper
	implements AnnouncementsDeliveryService,
			   ServiceWrapper<AnnouncementsDeliveryService> {

	public AnnouncementsDeliveryServiceWrapper(
		AnnouncementsDeliveryService announcementsDeliveryService) {

		_announcementsDeliveryService = announcementsDeliveryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _announcementsDeliveryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsDelivery
			updateDelivery(long userId, String type, boolean email, boolean sms)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsDeliveryService.updateDelivery(
			userId, type, email, sms);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #updateDelivery(long, String, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public com.liferay.announcements.kernel.model.AnnouncementsDelivery
			updateDelivery(
				long userId, String type, boolean email, boolean sms,
				boolean website)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _announcementsDeliveryService.updateDelivery(
			userId, type, email, sms, website);
	}

	@Override
	public AnnouncementsDeliveryService getWrappedService() {
		return _announcementsDeliveryService;
	}

	@Override
	public void setWrappedService(
		AnnouncementsDeliveryService announcementsDeliveryService) {

		_announcementsDeliveryService = announcementsDeliveryService;
	}

	private AnnouncementsDeliveryService _announcementsDeliveryService;

}