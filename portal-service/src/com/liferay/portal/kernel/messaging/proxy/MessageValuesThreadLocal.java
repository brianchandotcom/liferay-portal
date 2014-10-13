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

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.Collections;
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

		return messageValues.get(key);
	}

	public static Map<String, Object> getValues() {
		Map<String, Object> messageValues = _messageValuesThreadLocal.get();

		if (messageValues == null) {
			return Collections.emptyMap();
		}

		return messageValues;
	}

	public static void setValue(String key, Object value) {
		Map<String, Object> messageValues = _messageValuesThreadLocal.get();

		if (messageValues == null) {
			messageValues = new HashMap<String, Object>();

			_messageValuesThreadLocal.set(messageValues);
		}

		messageValues.put(key, value);
	}

	private static final ThreadLocal<Map<String, Object>>
		_messageValuesThreadLocal =
			new AutoResetThreadLocal<Map<String, Object>>(
				MessageValuesThreadLocal.class.getName());

}