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

package com.liferay.lcs.messaging;

import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class LCSMessageBusMessageListener extends BaseMessageListener {

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		MessageBusMessage messageBusMessage = new MessageBusMessage();

		messageBusMessage.setDestinationName(
			message.getString("LCS_DESTINATION"));
		messageBusMessage.setKey(_keyGenerator.getKey());
		messageBusMessage.setResponseDestinationName(
			message.getResponseDestinationName());
		messageBusMessage.setResponseId(message.getResponseId());

		Map<String, Object> values = message.getValues();

		for (String key : values.keySet()) {
			Object value = message.get(key);

			Class<? extends Object> clazz = value.getClass();

			if (clazz.isPrimitive() || isWrapper(clazz) ||
				clazz.equals(String.class)) {

				messageBusMessage.put(key, message.get(key));
			}
		}

		messageBusMessage.setPayload(message.getPayload());

		_lcsConnectionManager.sendMessage(messageBusMessage);
	}

	protected boolean isWrapper(Class<? extends Object> clazz) {
		if ((clazz == Byte.class) || (clazz == Boolean.class) ||
			(clazz == Character.class) || (clazz == Double.class) ||
			(clazz == Float.class) || (clazz == Long.class) ||
			(clazz == Integer.class) || (clazz == Short.class)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSMessageBusMessageListener.class);

	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;

}