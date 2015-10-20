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

package com.liferay.portal.pop.notifications.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.pop.MessageListenerWrapper;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class POPServerUtil {

	public static void addListener(MessageListener listener) {
		_instance._addListener(listener);
	}

	public static void deleteListener(MessageListener listener) {
		_instance._deleteListener(listener);
	}

	public static Collection<MessageListener> getListeners() {
		return _instance._getListeners();
	}

	private void _addListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not add null listener");
			}

			return;
		}

		_listeners.put(listener, new MessageListenerWrapper(listener));
	}

	private void _deleteListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not delete null listener");
			}

			return;
		}

		_listeners.remove(listener);
	}

	@SuppressWarnings("unchecked")
	private Collection<MessageListener> _getListeners() {
		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}

		return (Collection)_listeners.values();
	}

	private static final Log _log = LogFactoryUtil.getLog(POPServerUtil.class);

	private static final POPServerUtil _instance = new POPServerUtil();

	private final Map<MessageListener, MessageListenerWrapper>
		_listeners = new ConcurrentHashMap<>();

}