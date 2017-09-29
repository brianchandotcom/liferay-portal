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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.messaging.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class MessageValuesThreadLocal {

	public static Object getValue(String key) {
		Map<String, Object> messageValues = _messageValuesThreadLocal.get();

		if (messageValues == null) {
			return null;
		}

		return messageValues.get(_THREAD_LOCAL_KEY_PREFIX.concat(key));
	}

	public static void populateMessageFromThreadLocals(Message message) {
		Map<String, Object> messageValues = _messageValuesThreadLocal.get();

		if (messageValues == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : messageValues.entrySet()) {
			message.put(entry.getKey(), entry.getValue());
		}
	}

	public static void populateThreadLocalsFromMessage(Message message) {
		Map<String, Object> values = message.getValues();

		if (values == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith(_THREAD_LOCAL_KEY_PREFIX)) {
				doSetValue(key, entry.getValue());
			}
		}
	}

	public static void setValue(String key, Object value) {
		doSetValue(_THREAD_LOCAL_KEY_PREFIX.concat(key), value);
	}

	protected static void doSetValue(String key, Object value) {
		Map<String, Object> messageValues = _messageValuesThreadLocal.get();

		if (messageValues == null) {
			messageValues = new HashMap<>();

			_messageValuesThreadLocal.set(messageValues);
		}

		messageValues.put(key, value);
	}

	private static final String _THREAD_LOCAL_KEY_PREFIX =
		"THREAD_LOCAL_KEY_PREFIX#";

	private static final ThreadLocal<Map<String, Object>>
		_messageValuesThreadLocal = new CentralizedThreadLocal<>(
			MessageValuesThreadLocal.class.getName());

}