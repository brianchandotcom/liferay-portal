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
public interface ChannelHubManager {

	public void confirmDelivery(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException;

	public void confirmDelivery(
			long companyId, long userId,
			Collection<String> notificationEventUuids, boolean archive)
		throws ChannelException;

	public void confirmDelivery(
			long companyId, long userId, String notificationEventUuid)
		throws ChannelException;

	public void confirmDelivery(
			long companyId, long userId, String notificationEventUuid,
			boolean archive)
		throws ChannelException;

	public Channel createChannel(long companyId, long userId)
		throws ChannelException;

	public ChannelHub createChannelHub(long companyId) throws ChannelException;

	public void deleteUserNotificiationEvent(
			long companyId, long userId, String notificationEventUuid)
		throws ChannelException;

	public void deleteUserNotificiationEvents(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException;

	public void destroyChannel(long companyId, long userId)
		throws ChannelException;

	public void destroyChannelHub(long companyId) throws ChannelException;

	public ChannelHub fetchChannelHub(long companyId) throws ChannelException;

	public ChannelHub fetchChannelHub(long companyId, boolean createIfAbsent)
		throws ChannelException;

	public List<NotificationEvent> fetchNotificationEvents(
			long companyId, long userId, boolean flush)
		throws ChannelException;

	public void flush() throws ChannelException;

	public void flush(long companyId) throws ChannelException;

	public void flush(long companyId, long userId, long timestamp)
		throws ChannelException;

	public Channel getChannel(long companyId, long userId)
		throws ChannelException;

	public Channel getChannel(
			long companyId, long userId, boolean createIfAbsent)
		throws ChannelException;

	public ChannelHub getChannelHub(long companyId) throws ChannelException;

	public ChannelHub getChannelHub(long companyId, boolean createIfAbsent)
		throws ChannelException;

	public List<NotificationEvent> getNotificationEvents(
			long companyId, long userId)
		throws ChannelException;

	public List<NotificationEvent> getNotificationEvents(
			long companyId, long userId, boolean flush)
		throws ChannelException;

	public Collection<Long> getUserIds(long companyId) throws ChannelException;

	public void registerChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException;

	public void removeTransientNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException;

	public void removeTransientNotificationEventsByUuid(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException;

	public void sendNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException;

	public void sendNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException;

	public void storeNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException;

	public void unregisterChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException;

}