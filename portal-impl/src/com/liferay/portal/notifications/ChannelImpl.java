/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.BaseChannelImpl;
import com.liferay.portal.kernel.notifications.Channel;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventComparator;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 * @author Jonathan Lee
 */
public class ChannelImpl extends BaseChannelImpl {

	public ChannelImpl() {
		this(CompanyConstants.SYSTEM, 0);
	}

	public ChannelImpl(long companyId, long usedId) {
		super(companyId, usedId);
	}

	@Override
	public Channel clone(long companyId, long userId) {
		return new ChannelImpl(companyId, userId);
	}

	@Override
	public void confirmDelivery(Collection<String> notificationEventUuids)
		throws ChannelException {

		confirmDelivery(notificationEventUuids, false);
	}

	@Override
	public void confirmDelivery(
			Collection<String> notificationEventUuids, boolean archive)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED) {
				if (archive) {
					UserNotificationEventLocalServiceUtil.
						updateUserNotificationEvents(
							notificationEventUuids, getCompanyId(), archive);
				}
				else {
					UserNotificationEventLocalServiceUtil.
						deleteUserNotificationEvents(
							notificationEventUuids, getCompanyId());
				}
			}

			for (String notificationEventUuid : notificationEventUuids) {
				Map<String, NotificationEvent> unconfirmedNotificationEvents =
					_getUnconfirmedNotificationEvents();

				unconfirmedNotificationEvents.remove(notificationEventUuid);
			}
		}
		catch (Exception e) {
			throw new ChannelException(
				"Unable to confirm delivery for user " + getUserId() , e);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void confirmDelivery(String notificationEventUuid)
		throws ChannelException {

		confirmDelivery(notificationEventUuid, false);
	}

	@Override
	public void confirmDelivery(String notificationEventUuid, boolean archive)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED) {
				if (archive) {
					UserNotificationEventLocalServiceUtil.
						updateUserNotificationEvent(
							notificationEventUuid, getCompanyId(), archive);
				}
				else {
					UserNotificationEventLocalServiceUtil.
						deleteUserNotificationEvent(
							notificationEventUuid, getCompanyId());
				}
			}

			Map<String, NotificationEvent> unconfirmedNotificationEvents =
				_getUnconfirmedNotificationEvents();

			unconfirmedNotificationEvents.remove(notificationEventUuid);
		}
		catch (Exception e) {
			throw new ChannelException(
				"Unable to confirm delivery for " + notificationEventUuid , e);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void deleteUserNotificiationEvent(String notificationEventUuid)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				notificationEventUuid, getCompanyId());

			Map<String, NotificationEvent> unconfirmedNotificationEvents =
				_getUnconfirmedNotificationEvents();

			unconfirmedNotificationEvents.remove(notificationEventUuid);
		}
		catch (Exception e) {
			throw new ChannelException(
				"Unable to delete event " + notificationEventUuid , e);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void deleteUserNotificiationEvents(
			Collection<String> notificationEventUuids)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvents(
				notificationEventUuids, getCompanyId());

			for (String notificationEventUuid : notificationEventUuids) {
				Map<String, NotificationEvent> unconfirmedNotificationEvents =
					_getUnconfirmedNotificationEvents();

				unconfirmedNotificationEvents.remove(notificationEventUuid);
			}
		}
		catch (Exception e) {
			throw new ChannelException(
				"Unable to delete events for user " + getUserId() , e);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void flush() {
		_reentrantLock.lock();

		try {
			if (_notificationEvents != null) {
				_notificationEvents.clear();
			}
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void flush(long timestamp) {
		_reentrantLock.lock();

		try {
			if (_notificationEvents == null) {
				return;
			}

			Iterator<NotificationEvent> itr = _notificationEvents.iterator();

			while (itr.hasNext()) {
				NotificationEvent notificationEvent = itr.next();

				if (notificationEvent.getTimestamp() < timestamp) {
					itr.remove();
				}
			}
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public List<NotificationEvent> getNotificationEvents(boolean flush)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			return doGetNotificationEvents(flush);
		}
		catch (ChannelException ce) {
			throw ce;
		}
		catch (Exception e) {
			throw new ChannelException(e);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void init() throws ChannelException {
		_reentrantLock.lock();

		try {
			doInit();
		}
		catch (SystemException se) {
			throw new ChannelException(
				"Unable to init channel " + getUserId(), se);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void removeTransientNotificationEvents(
		Collection<NotificationEvent> notificationEvents) {

		_reentrantLock.lock();

		try {
			if (_notificationEvents != null) {
				_notificationEvents.removeAll(notificationEvents);
			}
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void removeTransientNotificationEventsByUuid(
		Collection<String> notificationEventUuids) {

		Set<String> notificationEventUuidsSet = new HashSet<String>(
			notificationEventUuids);

		_reentrantLock.lock();

		try {
			if (_notificationEvents == null) {
				return;
			}

			Iterator<NotificationEvent> itr = _notificationEvents.iterator();

			while (itr.hasNext()) {
				NotificationEvent notificationEvent = itr.next();

				if (notificationEventUuidsSet.contains(
						notificationEvent.getUuid())) {

					itr.remove();
				}
			}
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	@Override
	public void sendNotificationEvent(NotificationEvent notificationEvent)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			long currentTime = System.currentTimeMillis();

			storeNotificationEvent(notificationEvent, currentTime);

			if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED &&
				notificationEvent.isDeliveryRequired()) {

				UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
					getUserId(), notificationEvent);
			}
		}
		catch (Exception e) {
			throw new ChannelException("Unable to send event", e);
		}
		finally {
			_reentrantLock.unlock();
		}

		notifyChannelListeners();
	}

	@Override
	public void sendNotificationEvents(
			Collection<NotificationEvent> notificationEvents)
		throws ChannelException {

		_reentrantLock.lock();

		try {
			long currentTime = System.currentTimeMillis();

			List<NotificationEvent> persistedNotificationEvents =
				new ArrayList<NotificationEvent>(notificationEvents.size());

			for (NotificationEvent notificationEvent : notificationEvents) {
				storeNotificationEvent(notificationEvent, currentTime);

				if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED &&
					notificationEvent.isDeliveryRequired()) {

					persistedNotificationEvents.add(notificationEvent);
				}
			}

			if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED &&
				!persistedNotificationEvents.isEmpty()) {

				UserNotificationEventLocalServiceUtil.addUserNotificationEvents(
					getUserId(), persistedNotificationEvents);
			}
		}
		catch (Exception e) {
			throw new ChannelException("Unable to send event", e);
		}
		finally {
			_reentrantLock.unlock();
		}

		notifyChannelListeners();
	}

	@Override
	protected void doCleanUp() throws Exception {
		_reentrantLock.lock();

		try {
			long currentTime = System.currentTimeMillis();

			TreeSet<NotificationEvent> notificationEvents =
				_getNotificationEvents();

			Iterator<NotificationEvent> itr1 = notificationEvents.iterator();

			while (itr1.hasNext()) {
				NotificationEvent notificationEvent = itr1.next();

				if (isRemoveNotificationEvent(notificationEvent, currentTime)) {
					itr1.remove();
				}
			}

			Map<String, NotificationEvent> unconfirmedNotificationEvents =
				_getUnconfirmedNotificationEvents();

			List<String> invalidNotificationEventUuids = new ArrayList<String>(
				unconfirmedNotificationEvents.size());

			Set<Map.Entry<String, NotificationEvent>>
				unconfirmedNotificationEventsSet =
					unconfirmedNotificationEvents.entrySet();

			Iterator<Map.Entry<String, NotificationEvent>> itr2 =
				unconfirmedNotificationEventsSet.iterator();

			while (itr2.hasNext()) {
				Map.Entry<String, NotificationEvent> entry = itr2.next();

				NotificationEvent notificationEvent = entry.getValue();

				if (isRemoveNotificationEvent(notificationEvent, currentTime)) {
					invalidNotificationEventUuids.add(entry.getKey());

					itr2.remove();
				}
			}

			if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED &&
				!invalidNotificationEventUuids.isEmpty()) {

				UserNotificationEventLocalServiceUtil.
					deleteUserNotificationEvents(
						invalidNotificationEventUuids, getCompanyId());
			}
		}
		catch (Exception e) {
			throw new ChannelException(
				"Unable to clean up channel " + getUserId(), e);
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	protected List<NotificationEvent> doGetNotificationEvents(boolean flush)
		throws Exception {

		long currentTime = System.currentTimeMillis();

		TreeSet<NotificationEvent> notificationEventsSet =
			_getNotificationEvents();

		Map<String, NotificationEvent> unconfirmedNotificationEvents =
			_getUnconfirmedNotificationEvents();

		List<NotificationEvent> notificationEvents =
			new ArrayList<NotificationEvent>(
				notificationEventsSet.size() +
					unconfirmedNotificationEvents.size());

		for (NotificationEvent notificationEvent : notificationEventsSet) {
			if (isRemoveNotificationEvent(notificationEvent, currentTime)) {
				break;
			}
			else {
				notificationEvents.add(notificationEvent);
			}
		}

		if (flush) {
			notificationEventsSet.clear();
		}
		else if (notificationEventsSet.size() != notificationEvents.size()) {
			notificationEventsSet.retainAll(notificationEvents);
		}

		List<String> invalidNotificationEventUuids = new ArrayList<String>(
			unconfirmedNotificationEvents.size());

		Set<Map.Entry<String, NotificationEvent>>
			unconfirmedNotificationEventsSet =
				unconfirmedNotificationEvents.entrySet();

		Iterator<Map.Entry<String, NotificationEvent>> itr =
			unconfirmedNotificationEventsSet.iterator();

		while (itr.hasNext()) {
			Map.Entry<String, NotificationEvent> entry = itr.next();

			NotificationEvent notificationEvent = entry.getValue();

			if (isRemoveNotificationEvent(notificationEvent, currentTime) &&
				!notificationEvent.isArchived()) {

				invalidNotificationEventUuids.add(notificationEvent.getUuid());

				itr.remove();
			}
			else {
				notificationEvents.add(entry.getValue());
			}
		}

		if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED &&
			!invalidNotificationEventUuids.isEmpty()) {

			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvents(
				invalidNotificationEventUuids, getCompanyId());
		}

		return notificationEvents;
	}

	protected void doInit() throws SystemException {
		if (!PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED) {
			return;
		}

		List<UserNotificationEvent> userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				getUserId(), false);

		Map<String, NotificationEvent> unconfirmedNotificationEvents =
			_getUnconfirmedNotificationEvents();

		List<String> invalidNotificationEventUuids = new ArrayList<String>(
			unconfirmedNotificationEvents.size());

		long currentTime = System.currentTimeMillis();

		for (UserNotificationEvent persistedNotificationEvent :
				userNotificationEvents) {

			try {
				JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject(
					persistedNotificationEvent.getPayload());

				NotificationEvent notificationEvent =
					NotificationEventFactoryUtil.createNotificationEvent(
						persistedNotificationEvent.getTimestamp(),
						persistedNotificationEvent.getType(),
						payloadJSONObject);

				notificationEvent.setDeliveryRequired(
					persistedNotificationEvent.getDeliverBy());

				notificationEvent.setUuid(persistedNotificationEvent.getUuid());

				if (isRemoveNotificationEvent(notificationEvent, currentTime)) {
					invalidNotificationEventUuids.add(
						notificationEvent.getUuid());
				}
				else {
					unconfirmedNotificationEvents.put(
						notificationEvent.getUuid(), notificationEvent);
				}
			}
			catch (JSONException jsone) {
				_log.error(jsone, jsone);

				invalidNotificationEventUuids.add(
					persistedNotificationEvent.getUuid());
			}
		}

		if (!invalidNotificationEventUuids.isEmpty()) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvents(
				invalidNotificationEventUuids, getCompanyId());
		}
	}

	protected boolean isRemoveNotificationEvent(
		NotificationEvent notificationEvent, long currentTime) {

		if ((notificationEvent.getDeliverBy() != 0) &&
			(notificationEvent.getDeliverBy() <= currentTime)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void storeNotificationEvent(
		NotificationEvent notificationEvent, long currentTime) {

		if (isRemoveNotificationEvent(notificationEvent, currentTime)) {
			return;
		}

		if (PropsValues.USER_NOTIFICATION_EVENT_CONFIRMATION_ENABLED &&
			notificationEvent.isDeliveryRequired()) {

			Map<String, NotificationEvent> unconfirmedNotificationEvents =
				_getUnconfirmedNotificationEvents();

			unconfirmedNotificationEvents.put(
				notificationEvent.getUuid(), notificationEvent);
		}
		else {
			TreeSet<NotificationEvent> notificationEvents =
				_getNotificationEvents();

			notificationEvents.add(notificationEvent);

			if (notificationEvents.size() >
					PropsValues.NOTIFICATIONS_MAX_EVENTS) {

				NotificationEvent firstNotificationEvent =
					notificationEvents.first();

				notificationEvents.remove(firstNotificationEvent);
			}
		}
	}

	private TreeSet<NotificationEvent> _getNotificationEvents() {
		if (_notificationEvents == null) {
			_notificationEvents = new TreeSet<NotificationEvent>(_comparator);
		}

		return _notificationEvents;
	}

	private Map<String, NotificationEvent> _getUnconfirmedNotificationEvents() {
		if (_unconfirmedNotificationEvents == null) {
			_unconfirmedNotificationEvents =
				new HashMap<String, NotificationEvent>();
		}

		return _unconfirmedNotificationEvents;
	}

	private static Log _log = LogFactoryUtil.getLog(ChannelImpl.class);

	private static Comparator<NotificationEvent> _comparator =
		new NotificationEventComparator();

	private TreeSet<NotificationEvent> _notificationEvents;
	private ReentrantLock _reentrantLock = new ReentrantLock();
	private Map<String, NotificationEvent> _unconfirmedNotificationEvents;

}