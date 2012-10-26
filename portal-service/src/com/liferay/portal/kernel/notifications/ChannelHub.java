/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
public interface ChannelHub {

	public void cleanUp() throws ChannelException;

	public void cleanUp(long userId) throws ChannelException;

	public ChannelHub clone(long companyId);

	public void confirmDelivery(
			long userId, Collection<String> notificationEventUuids)
		throws ChannelException;

	public void confirmDelivery(
			long userId, Collection<String> notificationEventUuids,
			boolean archive)
		throws ChannelException;

	public void confirmDelivery(long userId, String notificationEventUuid)
		throws ChannelException;

	public void confirmDelivery(
			long userId, String notificationEventUuid, boolean archive)
		throws ChannelException;

	public Channel createChannel(long userId) throws ChannelException;

	public void deleteUserNotificiationEvent(
			long userId, String notificationEventUuid)
		throws ChannelException;

	public void deleteUserNotificiationEvents(
			long userId, Collection<String> notificationEventUuids)
		throws ChannelException;

	public void destroy() throws ChannelException;

	public Channel destroyChannel(long userId) throws ChannelException;

	public Channel fetchChannel(long userId) throws ChannelException;

	public Channel fetchChannel(long userId, boolean createIfAbsent)
		throws ChannelException;

	public List<NotificationEvent> fetchNotificationEvents(long userId)
		throws ChannelException;

	public List<NotificationEvent> fetchNotificationEvents(
			long userId, boolean flush)
		throws ChannelException;

	public void flush() throws ChannelException;

	public void flush(long userId) throws ChannelException;

	public void flush(long userId, long timestamp) throws ChannelException;

	public Channel getChannel(long userId) throws ChannelException;

	public Channel getChannel(long userId, boolean createIfAbsent)
		throws ChannelException;

	public List<NotificationEvent> getNotificationEvents(long userId)
		throws ChannelException;

	public List<NotificationEvent> getNotificationEvents(
			long userId, boolean flush)
		throws ChannelException;

	public Collection<Long> getUserIds();

	public void registerChannelListener(
			long userId, ChannelListener channelListener)
		throws ChannelException;

	public void removeTransientNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvents)
		throws ChannelException;

	public void removeTransientNotificationEventsByUuid(
			long userId, Collection<String> notificationEventUuids)
		throws ChannelException;

	public void sendNotificationEvent(
			long userId, NotificationEvent notificationEvent)
		throws ChannelException;

	public void sendNotificationEvents(
			long userId, Collection<NotificationEvent> notificationEvents)
		throws ChannelException;

	public void unregisterChannelListener(
			long userId, ChannelListener channelListener)
		throws ChannelException;

}