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

package com.liferay.calendar.internal.notification;

import com.liferay.calendar.notification.NotificationSender;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eduardo Lundgren
 */
@Component(immediate = true, service = NotificationSenderFactory.class)
public class NotificationSenderFactory {

	public static NotificationSender getNotificationSender(
			String notificationType)
		throws PortalException {

		NotificationSender notificationSender = _notificationSenders.get(
			notificationType);

		if (notificationSender == null) {
			throw new PortalException(
				"Invalid notification type " + notificationType);
		}

		return notificationSender;
	}

	public void setNotificationSenders(
		Map<String, NotificationSender> notificationSenders) {

		_notificationSenders = notificationSenders;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setNotificationSender(
		NotificationSender notificationSender, Map<String, Object> properties) {

		String notificationType = (String)properties.get("notification.type");

		if (notificationType == null) {
			throw new IllegalArgumentException(
				"The property \"notification.type\" is null");
		}

		NotificationSender previousNotificationSender =
			_notificationSenders.put(notificationType, notificationSender);

		if (_log.isWarnEnabled() && (previousNotificationSender != null)) {
			Class<?> clazz = previousNotificationSender.getClass();

			_log.warn("Overriding notification sender " + clazz.getName());
		}
	}

	protected void unsetNotificationSender(
		NotificationSender notificationSender, Map<String, Object> properties) {

		String notificationType = (String)properties.get("notification.type");

		if (notificationType == null) {
			throw new IllegalArgumentException(
				"The property \"notification.type\" is null");
		}

		_notificationSenders.remove(notificationType);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationSenderFactory.class);

	private static Map<String, NotificationSender> _notificationSenders =
		new ConcurrentHashMap<>();

}