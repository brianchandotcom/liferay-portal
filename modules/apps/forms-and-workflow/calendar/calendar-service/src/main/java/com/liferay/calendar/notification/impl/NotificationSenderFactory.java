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

package com.liferay.calendar.notification.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetNotificationSender"
	)
	protected void setNotificationSender(
		NotificationSender notificationSender, Map<String, Object> properties) {

		String notificationType = GetterUtil.getString(
			properties.get("notification.type"));

		if (Validator.isNull(notificationType)) {
			throw new IllegalArgumentException(
				"No notification.type property specified");
		}

		NotificationSender previousNotificationSender =
			_notificationSenders.put(notificationType, notificationSender);

		if (_log.isWarnEnabled()) {
			if (previousNotificationSender != null) {
				_log.warn(
					"Overriding NotificationSender: " +
						previousNotificationSender.getClass());
			}
		}
	}

	protected void unsetNotificationSender(
		NotificationSender notificationSender, Map<String, Object> properties) {

		String notificationType = GetterUtil.getString(
			properties.get("notification.type"));

		if (Validator.isNull(notificationType)) {
			throw new IllegalArgumentException(
				"No notification.type property specified");
		}

		_notificationSenders.remove(notificationType);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationSenderFactory.class);

	private static Map<String, NotificationSender> _notificationSenders =
		new ConcurrentHashMap<>();

}