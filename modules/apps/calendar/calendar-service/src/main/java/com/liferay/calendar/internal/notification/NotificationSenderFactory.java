/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = NotificationSenderFactory.class)
public class NotificationSenderFactory {

	public NotificationSender getNotificationSender(String notificationType)
		throws PortalException {

		NotificationSender notificationSender = _notificationSenders.get(
			notificationType);

		if (notificationSender == null) {
			throw new PortalException(
				"Invalid notification type " + notificationType);
		}

		return notificationSender;
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

	private final Map<String, NotificationSender> _notificationSenders =
		new ConcurrentHashMap<>();

}