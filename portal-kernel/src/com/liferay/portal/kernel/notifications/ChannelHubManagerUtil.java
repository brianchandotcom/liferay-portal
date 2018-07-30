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

package com.liferay.portal.kernel.notifications;

import java.util.Collection;
import java.util.List;

/**
 * @author Edward Han
 */
public class ChannelHubManagerUtil {

	public static void confirmDelivery(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException {

		getChannelHubManager().confirmDelivery(
			companyId, userId, notificationEventUuids);
	}

	public static void confirmDelivery(
			long companyId, long userId,
			Collection<String> notificationEventUuids, boolean archived)
		throws ChannelException {

		getChannelHubManager().confirmDelivery(
			companyId, userId, notificationEventUuids, archived);
	}

	public static void confirmDelivery(
			long companyId, long userId, String notificationEventUuid)
		throws ChannelException {

		getChannelHubManager().confirmDelivery(
			companyId, userId, notificationEventUuid);
	}

	public static void confirmDelivery(
			long companyId, long userId, String notificationEventUuid,
			boolean archived)
		throws ChannelException {

		getChannelHubManager().confirmDelivery(
			companyId, userId, notificationEventUuid, archived);
	}

	public static Channel createChannel(long companyId, long userId)
		throws ChannelException {

		return getChannelHubManager().createChannel(companyId, userId);
	}

	public static ChannelHub createChannelHub(long companyId)
		throws ChannelException {

		return getChannelHubManager().createChannelHub(companyId);
	}

	public static void deleteUserNotificiationEvent(
			long companyId, long userId, String notificationEventUuid)
		throws ChannelException {

		getChannelHubManager().deleteUserNotificiationEvent(
			companyId, userId, notificationEventUuid);
	}

	public static void deleteUserNotificiationEvents(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException {

		getChannelHubManager().deleteUserNotificiationEvents(
			companyId, userId, notificationEventUuids);
	}

	public static void destroyChannel(long companyId, long userId)
		throws ChannelException {

		getChannelHubManager().destroyChannel(companyId, userId);
	}

	public static void destroyChannelHub(long companyId)
		throws ChannelException {

		getChannelHubManager().destroyChannelHub(companyId);
	}

	public static ChannelHub fetchChannelHub(long companyId)
		throws ChannelException {

		return getChannelHubManager().fetchChannelHub(companyId);
	}

	public static ChannelHub fetchChannelHub(
			long companyId, boolean createIfAbsent)
		throws ChannelException {

		return getChannelHubManager().fetchChannelHub(
			companyId, createIfAbsent);
	}

	public static List<NotificationEvent> fetchNotificationEvents(
			long companyId, long userId, boolean flush)
		throws ChannelException {

		return getChannelHubManager().fetchNotificationEvents(
			companyId, userId, flush);
	}

	public static void flush() throws ChannelException {
		getChannelHubManager().flush();
	}

	public static void flush(long companyId) throws ChannelException {
		getChannelHubManager().flush(companyId);
	}

	public static void flush(long companyId, long userId, long timestamp)
		throws ChannelException {

		getChannelHubManager().flush(companyId, userId, timestamp);
	}

	public static Channel getChannel(long companyId, long userId)
		throws ChannelException {

		return getChannelHubManager().getChannel(companyId, userId);
	}

	public static Channel getChannel(
			long companyId, long userId, boolean createIfAbsent)
		throws ChannelException {

		return getChannelHubManager().getChannel(
			companyId, userId, createIfAbsent);
	}

	public static ChannelHub getChannelHub(long companyId)
		throws ChannelException {

		return getChannelHubManager().getChannelHub(companyId);
	}

	public static ChannelHub getChannelHub(
			long companyId, boolean createIfAbsent)
		throws ChannelException {

		return getChannelHubManager().getChannelHub(companyId, createIfAbsent);
	}

	public static ChannelHubManager getChannelHubManager() {
		return _channelHubManager;
	}

	public static List<NotificationEvent> getNotificationEvents(
			long companyId, long userId)
		throws ChannelException {

		return getChannelHubManager().getNotificationEvents(companyId, userId);
	}

	public static List<NotificationEvent> getNotificationEvents(
			long companyId, long userId, boolean flush)
		throws ChannelException {

		return getChannelHubManager().getNotificationEvents(
			companyId, userId, flush);
	}

	public static Collection<Long> getUserIds(long companyId)
		throws ChannelException {

		return getChannelHubManager().getUserIds(companyId);
	}

	public static void registerChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		getChannelHubManager().registerChannelListener(
			companyId, userId, channelListener);
	}

	public static void removeTransientNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		getChannelHubManager().removeTransientNotificationEvents(
			companyId, userId, notificationEvents);
	}

	public static void removeTransientNotificationEventsByUuid(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException {

		getChannelHubManager().removeTransientNotificationEventsByUuid(
			companyId, userId, notificationEventUuids);
	}

	public static void sendNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		getChannelHubManager().sendNotificationEvent(
			companyId, userId, notificationEvent);
	}

	public static void sendNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		getChannelHubManager().sendNotificationEvents(
			companyId, userId, notificationEvents);
	}

	public static void storeNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		getChannelHubManager().storeNotificationEvent(
			companyId, userId, notificationEvent);
	}

	public static void unregisterChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		getChannelHubManager().unregisterChannelListener(
			companyId, userId, channelListener);
	}

	public void setChannelHubManager(ChannelHubManager channelHubManager) {
		_channelHubManager = channelHubManager;
	}

	private static ChannelHubManager _channelHubManager;

}