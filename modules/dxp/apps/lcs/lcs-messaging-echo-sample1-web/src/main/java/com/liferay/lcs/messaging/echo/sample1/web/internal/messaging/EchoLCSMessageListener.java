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

package com.liferay.lcs.messaging.echo.sample1.web.internal.messaging;

import com.liferay.lcs.messaging.bus.LCSMessageBusService;
import com.liferay.lcs.messaging.bus.LCSMessageListener;
import com.liferay.lcs.messaging.bus.LCSMessageListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class EchoLCSMessageListener implements LCSMessageListener {

	public EchoLCSMessageListener(LCSMessageBusService lcsMessageBusService) {
		_lcsMessageBusService = lcsMessageBusService;
	}

	@Override
	public void receive(
			String destinationName, Map<String, String> metadata,
			String payload, String responseDestinationName)
		throws LCSMessageListenerException {

		if (_log.isInfoEnabled()) {
			_log.info("Received message for destination:" + destinationName);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Message payload: " + payload);
		}

		if (Validator.isNull(responseDestinationName)) {
			return;
		}

		StringBuilder sb = new StringBuilder(payload);

		sb.reverse();

		_lcsMessageBusService.sendMessage(
			responseDestinationName, sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EchoLCSMessageListener.class);

	private final LCSMessageBusService _lcsMessageBusService;

}