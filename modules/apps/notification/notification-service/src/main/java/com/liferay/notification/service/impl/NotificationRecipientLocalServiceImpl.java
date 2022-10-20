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

package com.liferay.notification.service.impl;

import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.service.base.NotificationRecipientLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationRecipient",
	service = AopService.class
)
public class NotificationRecipientLocalServiceImpl
	extends NotificationRecipientLocalServiceBaseImpl {

	public NotificationRecipient getNotificationRecipientByClassPK(
		long classPK) {

		return notificationRecipientPersistence.fetchByClassPK(classPK);
	}

}