/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link UserNotificationEventService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventService
 * @generated
 */
public class UserNotificationEventServiceWrapper
	implements ServiceWrapper<UserNotificationEventService>,
			   UserNotificationEventService {

	public UserNotificationEventServiceWrapper() {
		this(null);
	}

	public UserNotificationEventServiceWrapper(
		UserNotificationEventService userNotificationEventService) {

		_userNotificationEventService = userNotificationEventService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _userNotificationEventService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.UserNotificationEvent
			getUserNotificationEvent(long userNotificationEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userNotificationEventService.getUserNotificationEvent(
			userNotificationEventId);
	}

	@Override
	public com.liferay.portal.kernel.model.UserNotificationEvent
			updateUserNotificationEvent(
				java.lang.String uuid, long companyId, boolean archive)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _userNotificationEventService.updateUserNotificationEvent(
			uuid, companyId, archive);
	}

	@Override
	public UserNotificationEventService getWrappedService() {
		return _userNotificationEventService;
	}

	@Override
	public void setWrappedService(
		UserNotificationEventService userNotificationEventService) {

		_userNotificationEventService = userNotificationEventService;
	}

	private UserNotificationEventService _userNotificationEventService;

}