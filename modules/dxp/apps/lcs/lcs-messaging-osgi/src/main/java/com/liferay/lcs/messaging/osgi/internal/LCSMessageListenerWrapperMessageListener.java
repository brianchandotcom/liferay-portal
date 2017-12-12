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

package com.liferay.lcs.messaging.osgi.internal;

import com.liferay.lcs.messaging.bus.LCSMessageListener;
import com.liferay.lcs.messaging.bus.LCSMessageListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class LCSMessageListenerWrapperMessageListener
	implements MessageListener {

	public LCSMessageListenerWrapperMessageListener(
		LCSMessageListener lcsMessageListener) {

		_lcsMessageListener = lcsMessageListener;
	}

	public LCSMessageListener getLCSMessageListener() {
		return _lcsMessageListener;
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		try {
			Map<String, String> metadata = new HashMap<>();

			Map<String, Object> values = message.getValues();

			for (String key : values.keySet()) {
				String stringValue = String.valueOf(values.get(key));

				metadata.put(key, stringValue);
			}

			_lcsMessageListener.receive(
				message.getDestinationName(), metadata,
				String.valueOf(message.getPayload()),
				message.getResponseDestinationName());
		}
		catch (LCSMessageListenerException lcsmle) {
			throw new MessageListenerException(lcsmle);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSMessageListenerWrapperMessageListener.class);

	private final LCSMessageListener _lcsMessageListener;

}