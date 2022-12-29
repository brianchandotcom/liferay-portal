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