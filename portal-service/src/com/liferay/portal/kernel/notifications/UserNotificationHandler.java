/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Jonathan Lee
 */
public interface UserNotificationHandler {

	public String getPortletId();

	public String getSelector();

	public UserNotificationFeedEntry interpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException;

	public boolean deliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException, SystemException;

}