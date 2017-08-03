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

package com.liferay.lcs.task;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;

import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class CommandMessageTask implements Task {

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	protected void doRun() throws Exception {
		if (!_lcsConnectionManager.isReady()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Waiting for LCS connection manager");
			}

			return;
		}

		if (_log.isTraceEnabled()) {
			_log.trace("Checking messages for " + _keyGenerator.getKey());
		}

		List<Message> messages = _lcsConnectionManager.getMessages(
			_keyGenerator.getKey());

		for (Message message : messages) {
			if (message instanceof CommandMessage) {
				MessageBusUtil.sendMessage("liferay/lcs_commands", message);

				_lcsConnectionManager.putLCSConnectionMetadata(
					"messageTaskTime",
					String.valueOf(System.currentTimeMillis()));
			}
			else {
				_log.error("Unknown message " + message);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommandMessageTask.class);

	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;

}