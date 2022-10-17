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

package com.liferay.notification.rest.internal.dto.v1_0.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.model.NotificationTemplateRecipient;
import com.liferay.notification.service.NotificationTemplateLocalServiceUtil;
import com.liferay.notification.service.NotificationTemplateRecipientLocalServiceUtil;
import com.liferay.notification.type.NotificationContext;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.util.LocalizedMapUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;

/**
 * @author Feliphe Marinho
 */
public class NotificationTemplateUtil {

	public static NotificationContext toNotificationContext(
			long userId,
			com.liferay.notification.rest.dto.v1_0.NotificationTemplate
				notificationTemplate,
			NotificationType notificationType)
		throws Exception {

		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setAttachmentObjectFieldIds(
			ListUtil.fromArray(
				notificationTemplate.getAttachmentObjectFieldIds()));

		NotificationTemplate serviceBuilderNotificationTemplate =
			NotificationTemplateLocalServiceUtil.createNotificationTemplate(
				CounterLocalServiceUtil.increment());

		User user = UserLocalServiceUtil.getUser(userId);

		serviceBuilderNotificationTemplate.setCompanyId(user.getCompanyId());
		serviceBuilderNotificationTemplate.setUserId(user.getUserId());
		serviceBuilderNotificationTemplate.setUserName(user.getFullName());

		serviceBuilderNotificationTemplate.setObjectDefinitionId(
			notificationTemplate.getObjectDefinitionId());
		serviceBuilderNotificationTemplate.setBodyMap(
			LocalizedMapUtil.getLocalizedMap(notificationTemplate.getBody()));
		serviceBuilderNotificationTemplate.setDescription(
			notificationTemplate.getDescription());
		serviceBuilderNotificationTemplate.setName(
			notificationTemplate.getName());
		serviceBuilderNotificationTemplate.setRecipientType(
			notificationTemplate.getRecipientType());
		serviceBuilderNotificationTemplate.setType(
			notificationTemplate.getType());

		NotificationTemplateRecipient notificationTemplateRecipient =
			NotificationTemplateRecipientLocalServiceUtil.
				createNotificationTemplateRecipient(
					CounterLocalServiceUtil.increment());

		notificationTemplateRecipient.setCompanyId(user.getCompanyId());
		notificationTemplateRecipient.setUserId(user.getUserId());
		notificationTemplateRecipient.setUserName(user.getFullName());

		notificationTemplateRecipient.setNotificationTemplateId(
			serviceBuilderNotificationTemplate.getNotificationTemplateId());
		notificationTemplateRecipient.setNotificationTemplateRecipientSettings(
			notificationType.createNotificationTemplateRecipientSettings(
				notificationTemplate.getRecipients(), user));

		serviceBuilderNotificationTemplate.setNotificationTemplateRecipient(
			notificationTemplateRecipient);

		notificationContext.setNotificationTemplate(
			serviceBuilderNotificationTemplate);

		notificationContext.setType(notificationTemplate.getType());

		return notificationContext;
	}

}