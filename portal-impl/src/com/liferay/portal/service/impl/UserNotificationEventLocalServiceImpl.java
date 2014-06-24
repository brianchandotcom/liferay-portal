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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserNotificationEventLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class UserNotificationEventLocalServiceImpl
	extends UserNotificationEventLocalServiceBaseImpl {

	@Override
	public UserNotificationEvent addUserNotificationEvent(
			long userId, NotificationEvent notificationEvent)
		throws PortalException {

		JSONObject payloadJSONObject = notificationEvent.getPayload();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(notificationEvent.getUuid());

		return addUserNotificationEvent(
			userId, notificationEvent.getType(),
			notificationEvent.getTimestamp(),
			notificationEvent.getDeliveryType(),
			notificationEvent.getDeliverBy(), payloadJSONObject.toString(),
			notificationEvent.isArchived(), serviceContext);
	}

	@Override
	public UserNotificationEvent addUserNotificationEvent(
			long userId, String type, long timestamp, int deliveryType,
			long deliverBy, String payload, boolean archived,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long userNotificationEventId = counterLocalService.increment();

		UserNotificationEvent userNotificationEvent =
			userNotificationEventPersistence.create(userNotificationEventId);

		userNotificationEvent.setUuid(serviceContext.getUuid());
		userNotificationEvent.setCompanyId(user.getCompanyId());
		userNotificationEvent.setUserId(userId);
		userNotificationEvent.setType(type);
		userNotificationEvent.setTimestamp(timestamp);
		userNotificationEvent.setDeliveryType(deliveryType);
		userNotificationEvent.setDeliverBy(deliverBy);
		userNotificationEvent.setDelivered(false);
		userNotificationEvent.setPayload(payload);
		userNotificationEvent.setArchived(archived);

		userNotificationEventPersistence.update(userNotificationEvent);

		return userNotificationEvent;
	}

	/**
	 * @deprecated As of 7.0.0 {@link #addUserNotificationEvent(long, String,
	 *             long, int, long, String, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public UserNotificationEvent addUserNotificationEvent(
			long userId, String type, long timestamp, long deliverBy,
			String payload, boolean archived, ServiceContext serviceContext)
		throws PortalException {

		return addUserNotificationEvent(
			userId, type, timestamp,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, deliverBy, payload,
			archived, serviceContext);
	}

	@Override
	public List<UserNotificationEvent> addUserNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvents)
		throws PortalException {

		List<UserNotificationEvent> userNotificationEvents =
			new ArrayList<UserNotificationEvent>(notificationEvents.size());

		for (NotificationEvent notificationEvent : notificationEvents) {
			UserNotificationEvent userNotificationEvent =
				addUserNotificationEvent(userId, notificationEvent);

			userNotificationEvents.add(userNotificationEvent);
		}

		return userNotificationEvents;
	}

	@Override
	public void deleteUserNotificationEvent(String uuid, long companyId) {
		userNotificationEventPersistence.removeByUuid_C(uuid, companyId);
	}

	@Override
	public void deleteUserNotificationEvents(
		Collection<String> uuids, long companyId) {

		for (String uuid : uuids) {
			deleteUserNotificationEvent(uuid, companyId);
		}
	}

	@Override
	public List<UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean archived) {

		return userNotificationEventPersistence.findByU_A(userId, archived);
	}

	@Override
	public List<UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean actionRequired, boolean archived) {

		return userNotificationEventPersistence.findByU_A_A(
			userId, actionRequired, archived);
	}

	@Override
	public List<UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean actionRequired, boolean archived, int start,
		int end) {

		return userNotificationEventPersistence.findByU_A_A(
			userId, actionRequired, archived, start, end);
	}

	@Override
	public List<UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, boolean archived, int start, int end) {

		return userNotificationEventPersistence.findByU_A(
			userId, archived, start, end);
	}

	@Override
	public List<UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, int deliveryType, boolean archived) {

		return userNotificationEventPersistence.findByU_DT_A(
			userId, deliveryType, archived);
	}

	@Override
	public List<UserNotificationEvent> getArchivedUserNotificationEvents(
		long userId, int deliveryType, boolean archived, int start, int end) {

		return userNotificationEventPersistence.findByU_DT_A(
			userId, deliveryType, archived, start, end);
	}

	@Override
	public int getArchivedUserNotificationEventsCount(
		long userId, boolean archived) {

		return userNotificationEventPersistence.countByU_A(userId, archived);
	}

	@Override
	public int getArchivedUserNotificationEventsCount(
		long userId, boolean actionRequired, boolean archived) {

		return userNotificationEventPersistence.countByU_A_A(
			userId, actionRequired, archived);
	}

	@Override
	public int getArchivedUserNotificationEventsCount(
		long userId, int deliveryType, boolean archived) {

		return userNotificationEventPersistence.countByU_DT_A(
			userId, deliveryType, archived);
	}

	@Override
	public List<UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean delivered) {

		return userNotificationEventPersistence.findByU_D(userId, delivered);
	}

	@Override
	public List<UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean delivered, boolean actionRequired) {

		return userNotificationEventPersistence.findByU_D_A(
			userId, delivered, actionRequired);
	}

	@Override
	public List<UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean delivered, boolean actionRequired, int start,
		int end) {

		return userNotificationEventPersistence.findByU_D_A(
			userId, delivered, actionRequired, start, end);
	}

	@Override
	public List<UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, boolean delivered, int start, int end) {

		return userNotificationEventPersistence.findByU_D(
			userId, delivered, start, end);
	}

	@Override
	public List<UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, int deliveryType, boolean delivered) {

		return userNotificationEventPersistence.findByU_DT_D(
			userId, deliveryType, delivered);
	}

	@Override
	public List<UserNotificationEvent> getDeliveredUserNotificationEvents(
		long userId, int deliveryType, boolean delivered, int start, int end) {

		return userNotificationEventPersistence.findByU_DT_D(
			userId, deliveryType, delivered, start, end);
	}

	@Override
	public int getDeliveredUserNotificationEventsCount(
		long userId, boolean delivered) {

		return userNotificationEventPersistence.countByU_D(userId, delivered);
	}

	@Override
	public int getDeliveredUserNotificationEventsCount(
		long userId, boolean delivered, boolean actionRequired) {

		return userNotificationEventPersistence.countByU_D_A(
			userId, delivered, actionRequired);
	}

	@Override
	public int getDeliveredUserNotificationEventsCount(
		long userId, int deliveryType, boolean delivered) {

		return userNotificationEventPersistence.countByU_DT_D(
			userId, deliveryType, delivered);
	}

	@Override
	public List<UserNotificationEvent> getUserNotificationEvents(long userId) {
		return userNotificationEventPersistence.findByUserId(userId);
	}

	/**
	 * @deprecated As of 6.2.0 {@link #getArchivedUserNotificationEvents(long,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public List<UserNotificationEvent> getUserNotificationEvents(
		long userId, boolean archived) {

		return getArchivedUserNotificationEvents(userId, archived);
	}

	/**
	 * @deprecated As of 6.2.0 {@link #getArchivedUserNotificationEvents(long,
	 *             boolean, int, int)}
	 */
	@Deprecated
	@Override
	public List<UserNotificationEvent> getUserNotificationEvents(
		long userId, boolean archived, int start, int end) {

		return getArchivedUserNotificationEvents(userId, archived, start, end);
	}

	@Override
	public List<UserNotificationEvent> getUserNotificationEvents(
		long userId, int deliveryType) {

		return userNotificationEventPersistence.findByU_DT(
			userId, deliveryType);
	}

	@Override
	public List<UserNotificationEvent> getUserNotificationEvents(
		long userId, int start, int end) {

		return userNotificationEventPersistence.findByUserId(
			userId, start, end);
	}

	@Override
	public List<UserNotificationEvent> getUserNotificationEvents(
		long userId, int deliveryType, int start, int end) {

		return userNotificationEventPersistence.findByU_DT(
			userId, deliveryType, start, end);
	}

	@Override
	public int getUserNotificationEventsCount(long userId) {
		return userNotificationEventPersistence.countByUserId(userId);
	}

	/**
	 * @deprecated As of 6.2.0 {@link
	 *             #getArchivedUserNotificationEventsCount(long, boolean)}
	 */
	@Deprecated
	@Override
	public int getUserNotificationEventsCount(long userId, boolean archived) {
		return getArchivedUserNotificationEventsCount(userId, archived);
	}

	@Override
	public int getUserNotificationEventsCount(long userId, int deliveryType) {
		return userNotificationEventPersistence.countByU_DT(
			userId, deliveryType);
	}

	@Override
	public UserNotificationEvent sendUserNotificationEvents(
			long userId, String portletId, int deliveryType,
			JSONObject notificationEventJSONObject)
		throws PortalException {

		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), portletId,
				notificationEventJSONObject);

		notificationEvent.setDeliveryType(deliveryType);

		UserNotificationEvent userNotificationEvent = addUserNotificationEvent(
			userId, notificationEvent);

		if (deliveryType == UserNotificationDeliveryConstants.TYPE_PUSH) {
			sendPushNotification(notificationEvent);
		}

		return userNotificationEvent;
	}

	@Override
	public UserNotificationEvent updateUserNotificationEvent(
		String uuid, long companyId, boolean archive) {

		List<UserNotificationEvent> userNotificationEvents =
			userNotificationEventPersistence.findByUuid_C(uuid, companyId);

		if (userNotificationEvents.isEmpty()) {
			return null;
		}

		UserNotificationEvent userNotificationEvent =
			userNotificationEvents.get(0);

		userNotificationEvent.setArchived(archive);

		userNotificationEventPersistence.update(userNotificationEvent);

		return userNotificationEvent;
	}

	@Override
	public List<UserNotificationEvent> updateUserNotificationEvents(
		Collection<String> uuids, long companyId, boolean archive) {

		List<UserNotificationEvent> userNotificationEvents =
			new ArrayList<UserNotificationEvent>();

		for (String uuid : uuids) {
			userNotificationEvents.add(
				updateUserNotificationEvent(uuid, companyId, archive));
		}

		return userNotificationEvents;
	}

	protected void sendPushNotification(
		final NotificationEvent notificationEvent) {

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					message.setPayload(notificationEvent.getPayload());

					MessageBusUtil.sendMessage(
						DestinationNames.PUSH_NOTIFICATION, message);

					return null;
				}

			});
	}

}