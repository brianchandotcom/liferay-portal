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

package com.liferay.notification.context;

import com.liferay.notification.model.NotificationTemplate;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class NotificationContextBuilder {

	public NotificationContext build() {
		return _notificationContext;
	}

	public NotificationContextBuilder className(String className) {
		_notificationContext.setClassName(className);

		return this;
	}

	public NotificationContextBuilder classPK(long classPK) {
		_notificationContext.setClassPK(classPK);

		return this;
	}

	public NotificationContextBuilder externalReferenceCode(
		String externalReferenceCode) {

		_notificationContext.setExternalReferenceCode(externalReferenceCode);

		return this;
	}

	public NotificationContextBuilder notificationTemplate(
		NotificationTemplate notificationTemplate) {

		_notificationContext.setNotificationTemplate(notificationTemplate);

		return this;
	}

	public NotificationContextBuilder portletId(String portletId) {
		_notificationContext.setPortletId(portletId);

		return this;
	}

	public NotificationContextBuilder termValues(
		Map<String, Object> termValues) {

		_notificationContext.setTermValues(termValues);

		return this;
	}

	public NotificationContextBuilder userId(long userId) {
		_notificationContext.setUserId(userId);

		return this;
	}

	private final NotificationContext _notificationContext =
		new NotificationContext();

}