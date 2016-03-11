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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Edward Han
 */
public abstract class BaseChannelImpl implements Channel {

	@Override
	public void cleanUp() throws ChannelException {
		lock.lock();

		try {
			long currentTime = System.currentTimeMillis();

			if (currentTime > _nextCleanUpTime) {
				_nextCleanUpTime = currentTime + _cleanUpInterval;

				try {
					doCleanUp();
				}
				catch (ChannelException ce) {
					throw ce;
				}
				catch (Exception e) {
					throw new ChannelException(e);
				}
			}
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void close() throws ChannelException {
		flush();
	}

	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public List<NotificationEvent> getNotificationEvents()
		throws ChannelException {

		return getNotificationEvents(true);
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	public boolean hasNotificationEvents() {
		try {
			List<NotificationEvent> notificationEvents = getNotificationEvents(
				false);

			if (!notificationEvents.isEmpty()) {
				return true;
			}
		}
		catch (ChannelException ce) {
			_log.error("Unable to fetch notifications", ce);
		}

		return false;
	}

	@Override
	public void registerChannelListener(ChannelListener channelListener) {
		lock.lock();

		try {
			List<ChannelListener> channelListeners = _getChannelListeners();

			channelListeners.add(channelListener);

			if (hasNotificationEvents()) {
				notifyChannelListeners();
			}
		}
		finally {
			lock.unlock();
		}
	}

	public void setCleanUpInterval(long cleanUpInterval) {
		_cleanUpInterval = cleanUpInterval;
	}

	@Override
	public void unregisterChannelListener(ChannelListener channelListener) {
		lock.lock();

		try {
			List<ChannelListener> channelListeners = _getChannelListeners();

			channelListeners.remove(channelListener);
		}
		finally {
			lock.unlock();
		}

		channelListener.channelListenerRemoved(_userId);
	}

	protected BaseChannelImpl(long companyId, long usedId) {
		_companyId = companyId;
		_userId = usedId;
	}

	protected abstract void doCleanUp() throws Exception;

	protected void notifyChannelListeners() {
		for (ChannelListener channelListener : _getChannelListeners()) {
			channelListener.notificationEventsAvailable(_userId);
		}
	}

	protected final Lock lock = new ReentrantLock();

	private List<ChannelListener> _getChannelListeners() {
		if (_channelListeners == null) {
			_channelListeners = new ArrayList<>();
		}

		return _channelListeners;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseChannelImpl.class);

	private List<ChannelListener> _channelListeners;
	private long _cleanUpInterval;
	private final long _companyId;
	private long _nextCleanUpTime;
	private final long _userId;

}