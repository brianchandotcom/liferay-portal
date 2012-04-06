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

package com.liferay.portal.pop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.pop.messaging.POPNotificationsMessageListener;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class POPServerUtil {

	public static void addListener(MessageListener listener) throws Exception {
		_instance._addListener(listener);
	}

	public static void deleteListener(MessageListener listener)
		throws Exception {

		_instance._deleteListener(listener);
	}

	public static List<MessageListener> getListeners() throws Exception {
		return _instance._getListeners();
	}

	public static void start() {
		_instance._start();
	}

	private POPServerUtil() {
	}

	private void _addListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not add null listener");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Add listener " + listener.getClass().getName());
		}

		MessageListenerWrapper messageListenerWrapper =
			new MessageListenerWrapper(listener);

		_deleteListener(messageListenerWrapper);

		_listeners.add(messageListenerWrapper);

		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}
	}

	private void _deleteListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not delete null listener");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delete listener " + listener.getClass().getName());
		}

		MessageListenerWrapper messageListenerWrapper =
			new MessageListenerWrapper(listener);

		_deleteListener(messageListenerWrapper);

		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}
	}

	private void _deleteListener(MessageListenerWrapper listener) {
		Iterator<MessageListener> itr = _listeners.iterator();

		while (itr.hasNext()) {
			MessageListenerWrapper curListener =
				(MessageListenerWrapper)itr.next();

			if (curListener.equals(listener)) {
				itr.remove();
			}
		}
	}

	private List<MessageListener> _getListeners() {
		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}

		return new UnmodifiableList<MessageListener>(_listeners);
	}

	private void _start() {
		if (_log.isDebugEnabled()) {
			_log.debug("Start");
		}

		try {
			SchedulerEntry schedulerEntry = new SchedulerEntryImpl();

			schedulerEntry.setEventListenerClass(
				POPNotificationsMessageListener.class.getName());
			schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
			schedulerEntry.setTriggerType(TriggerType.SIMPLE);
			schedulerEntry.setTriggerValue(
				PropsValues.POP_SERVER_NOTIFICATIONS_INTERVAL);

			SchedulerEngineUtil.schedule(
				schedulerEntry, StorageType.MEMORY_CLUSTERED, null, 0);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(POPServerUtil.class);

	private static POPServerUtil _instance = new POPServerUtil();

	private List<MessageListener> _listeners = new ArrayList<MessageListener>();

}