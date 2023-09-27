/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public AnnouncementsDeliveryServiceWrapper() {
		this(null);
	}

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