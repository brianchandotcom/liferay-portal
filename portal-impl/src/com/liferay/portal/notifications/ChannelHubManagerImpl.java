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

package com.liferay.portal.notifications;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHub;
import com.liferay.portal.kernel.notifications.ChannelHubManager;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.ChannelListener;
import com.liferay.portal.kernel.notifications.DuplicateChannelHubException;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.UnknownChannelHubException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Edward Han
 * @author Brian Wing Shun
 * @author Shuyang Zhou
 */
@DoPrivileged
public class ChannelHubManagerImpl implements ChannelHubManager {

	@Override
	public void confirmDelivery(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException {

		confirmDelivery(companyId, userId, notificationEventUuids, false);
	}

	@Override
	public void confirmDelivery(
			long companyId, long userId,
			Collection<String> notificationEventUuids, boolean archive)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.confirmDelivery(userId, notificationEventUuids, archive);
	}

	@Override
	public void confirmDelivery(
			long companyId, long userId, String notificationEventUuid)
		throws ChannelException {

		confirmDelivery(companyId, userId, notificationEventUuid, false);
	}

	@Override
	public void confirmDelivery(
			long companyId, long userId, String notificationEventUuid,
			boolean archive)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.confirmDelivery(userId, notificationEventUuid, archive);
	}

	@Override
	public Channel createChannel(long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.createChannel(userId);
	}

	@Override
	public ChannelHub createChannelHub(long companyId) throws ChannelException {
		ChannelHub channelHub = _channelHub.clone(companyId);

		if (_channelHubs.putIfAbsent(companyId, channelHub) != null) {
			throw new DuplicateChannelHubException(
				"Channel already exists with company id " + companyId);
		}

		return channelHub;
	}

	@Override
	public void deleteUserNotificiationEvent(
			long companyId, long userId, String notificationEventUuid)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.deleteUserNotificiationEvent(userId, notificationEventUuid);
	}

	@Override
	public void deleteUserNotificiationEvents(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.deleteUserNotificiationEvents(
			userId, notificationEventUuids);
	}

	@Override
	public void destroyChannel(long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.destroyChannel(userId);
	}

	@Override
	public void destroyChannelHub(long companyId) throws ChannelException {
		ChannelHub channelHub = _channelHubs.remove(companyId);

		if (channelHub != null) {
			channelHub.destroy();
		}
	}

	@Override
	public ChannelHub fetchChannelHub(long companyId) throws ChannelException {
		return fetchChannelHub(companyId, false);
	}

	@Override
	public ChannelHub fetchChannelHub(long companyId, boolean createIfAbsent)
		throws ChannelException {

		ChannelHub channelHub = _channelHubs.get(companyId);

		if (channelHub == null) {
			synchronized(_channelHubs) {
				channelHub = _channelHubs.get(companyId);

				if (channelHub == null) {
					if (createIfAbsent) {
						channelHub = createChannelHub(companyId);
					}
				}
			}
		}

		return channelHub;
	}

	@Override
	public List<NotificationEvent> fetchNotificationEvents(
			long companyId, long userId, boolean flush)
		throws ChannelException {

		ChannelHub channelHub = fetchChannelHub(companyId);

		if (channelHub == null) {
			return Collections.emptyList();
		}

		return channelHub.fetchNotificationEvents(userId, flush);
	}

	@Override
	public void flush() throws ChannelException {
		for (ChannelHub channelHub : _channelHubs.values()) {
			channelHub.flush();
		}
	}

	@Override
	public void flush(long companyId) throws ChannelException {
		ChannelHub channelHub = fetchChannelHub(companyId);

		if (channelHub != null) {
			channelHub.flush();
		}
	}

	@Override
	public void flush(long companyId, long userId, long timestamp)
		throws ChannelException {

		ChannelHub channelHub = fetchChannelHub(companyId);

		if (channelHub != null) {
			channelHub.flush(userId, timestamp);
		}
	}

	@Override
	public Channel getChannel(long companyId, long userId)
		throws ChannelException {

		return getChannel(companyId, userId, false);
	}

	@Override
	public Channel getChannel(
			long companyId, long userId, boolean createIfAbsent)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId, createIfAbsent);

		return channelHub.getChannel(userId, createIfAbsent);
	}

	@Override
	public ChannelHub getChannelHub(long companyId) throws ChannelException {
		return getChannelHub(companyId, false);
	}

	@Override
	public ChannelHub getChannelHub(long companyId, boolean createIfAbsent)
		throws ChannelException {

		ChannelHub channelHub = fetchChannelHub(companyId, createIfAbsent);

		if (channelHub == null) {
			throw new UnknownChannelHubException(
				"No channel exists with company id " + companyId);
		}

		return channelHub;
	}

	@Override
	public List<NotificationEvent> getNotificationEvents(
			long companyId, long userId)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.getNotificationEvents(userId);
	}

	@Override
	public List<NotificationEvent> getNotificationEvents(
			long companyId, long userId, boolean flush)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.getNotificationEvents(userId, flush);
	}

	@Override
	public Collection<Long> getUserIds(long companyId) throws ChannelException {
		ChannelHub channelHub = getChannelHub(companyId);

		return channelHub.getUserIds();
	}

	@Override
	public void registerChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.registerChannelListener(userId, channelListener);
	}

	@Override
	public void removeTransientNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.removeTransientNotificationEvents(
			userId, notificationEvents);
	}

	@Override
	public void removeTransientNotificationEventsByUuid(
			long companyId, long userId,
			Collection<String> notificationEventUuids)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.removeTransientNotificationEventsByUuid(
			userId, notificationEventUuids);
	}

	@Override
	public void sendClusterNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		sendNotificationEvent(companyId, userId, notificationEvent);

		MethodHandler methodHandler = new MethodHandler(
			_storeNotificationEventMethodKey, companyId, userId,
			notificationEvent);

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		try {
			ClusterExecutorUtil.execute(clusterRequest);
		}
		catch (Exception e) {
			throw new ChannelException("Unable to notify cluster of event", e);
		}
	}

	@Override
	public void sendNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.sendNotificationEvent(userId, notificationEvent);
	}

	@Override
	public void sendNotificationEvents(
			long companyId, long userId,
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.sendNotificationEvents(userId, notificationEvents);
	}

	public void setChannelHubPrototype(ChannelHub channelHub) {
		_channelHub = channelHub;
	}

	@Override
	public void storeNotificationEvent(
			long companyId, long userId, NotificationEvent notificationEvent)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.storeNotificationEvent(userId, notificationEvent);
	}

	@Override
	public void unregisterChannelListener(
			long companyId, long userId, ChannelListener channelListener)
		throws ChannelException {

		ChannelHub channelHub = getChannelHub(companyId);

		channelHub.unregisterChannelListener(userId, channelListener);
	}

	private static final MethodKey _storeNotificationEventMethodKey =
		new MethodKey(
			ChannelHubManagerUtil.class, "storeNotificationEvent", long.class,
			long.class, NotificationEvent.class);

	private ChannelHub _channelHub;
	private final ConcurrentMap<Long, ChannelHub> _channelHubs =
		new ConcurrentHashMap<Long, ChannelHub>();

}