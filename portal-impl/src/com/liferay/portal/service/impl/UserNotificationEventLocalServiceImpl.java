/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserNotificationEventLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class UserNotificationEventLocalServiceImpl
	extends UserNotificationEventLocalServiceBaseImpl {

	public UserNotificationEvent addUserNotificationEvent(
			long userId, NotificationEvent notificationEvent)
		throws PortalException, SystemException {

		JSONObject payloadJSONObject = notificationEvent.getPayload();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(notificationEvent.getUuid());

		return addUserNotificationEvent(
			userId, notificationEvent.getType(),
			notificationEvent.getTimestamp(), notificationEvent.getDeliverBy(),
			payloadJSONObject.toString(), notificationEvent.isArchived(),
			serviceContext);
	}

	public UserNotificationEvent addUserNotificationEvent(
			long userId, String type, long timestamp, long deliverBy,
			String payload, boolean archived, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long userNotificationEventId = counterLocalService.increment();

		UserNotificationEvent userNotificationEvent =
			userNotificationEventPersistence.create(userNotificationEventId);

		userNotificationEvent.setUuid(serviceContext.getUuid());
		userNotificationEvent.setCompanyId(user.getCompanyId());
		userNotificationEvent.setUserId(userId);
		userNotificationEvent.setType(type);
		userNotificationEvent.setTimestamp(timestamp);
		userNotificationEvent.setDeliverBy(deliverBy);
		userNotificationEvent.setPayload(payload);
		userNotificationEvent.setArchived(archived);

		userNotificationEventPersistence.update(userNotificationEvent, false);

		return userNotificationEvent;
	}

	public List<UserNotificationEvent> addUserNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvents)
		throws PortalException, SystemException {

		List<UserNotificationEvent> userNotificationEvents =
			new ArrayList<UserNotificationEvent>(notificationEvents.size());

		for (NotificationEvent notificationEvent : notificationEvents) {
			UserNotificationEvent userNotificationEvent =
				addUserNotificationEvent(userId, notificationEvent);

			userNotificationEvents.add(userNotificationEvent);
		}

		return userNotificationEvents;
	}

	public void deleteUserNotificationEvent(String uuid)
		throws SystemException {

		userNotificationEventPersistence.removeByUuid(uuid);
	}

	public void deleteUserNotificationEvents(Collection<String> uuids)
		throws SystemException {

		for (String uuid : uuids) {
			deleteUserNotificationEvent(uuid);
		}
	}

	public List<UserNotificationEvent> getUserNotificationEvents(long userId)
		throws SystemException {

		return userNotificationEventPersistence.findByUserId(userId);
	}

	public List<UserNotificationEvent> getUserNotificationEvents(
			long userId, int start, int end)
		throws SystemException {

		return userNotificationEventPersistence.findByUserId(
			userId, start, end);
	}

	public List<UserNotificationEvent> getUserNotificationEvents(
			long userId, boolean archived)
		throws SystemException {

		return userNotificationEventPersistence.findByU_A(userId, archived);
	}

	public List<UserNotificationEvent> getUserNotificationEvents(
			long userId, boolean archived, int start, int end)
		throws SystemException {

		return userNotificationEventPersistence.findByU_A(
			userId, archived, start, end);
	}

	public UserNotificationEvent updateUserNotificationEvent(
			String uuid, boolean archive)
		throws SystemException {

		List<UserNotificationEvent> userNotificationEvents =
			userNotificationEventPersistence.findByUuid(uuid);

		if (userNotificationEvents.isEmpty()) {
			return null;
		}

		UserNotificationEvent userNotificationEvent =
			userNotificationEvents.get(0);

		userNotificationEvent.setArchived(archive);

		userNotificationEventPersistence.update(userNotificationEvent, false);

		return userNotificationEvent;
	}

	public List<UserNotificationEvent> updateUserNotificationEvents(
			Collection<String> uuids, boolean archive)
		throws SystemException {

		List<UserNotificationEvent> userNotificationEvents =
			new ArrayList<UserNotificationEvent>();

		for (String uuid : uuids) {
			userNotificationEvents.add(
				updateUserNotificationEvent(uuid, archive));
		}

		return userNotificationEvents;
	}

}