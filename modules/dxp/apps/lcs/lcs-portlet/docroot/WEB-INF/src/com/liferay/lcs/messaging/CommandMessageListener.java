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

import com.liferay.lcs.command.Command;
import com.liferay.lcs.security.DigitalSignature;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class CommandMessageListener implements MessageListener {

	@Override
	public void receive(Message message) {
		CommandMessage commandMessage = (CommandMessage)message.getPayload();

		String error = null;

		if (_digitalSignature.verifyMessage(
				LCSUtil.getLCSPortletBuildNumber(), commandMessage)) {

			try {
				Command command = _commands.get(
					commandMessage.getCommandType());

				command.execute(commandMessage);
			}
			catch (Exception e) {
				_log.error(e, e);

				error = e.getMessage();
			}
		}
		else {
			error = "Unable to verify digital signature";

			if (_log.isWarnEnabled()) {
				_log.warn(error + ": " + commandMessage);
			}
		}

		if (error != null) {
			ResponseMessage responseMessage =
				ResponseMessageUtil.createResponseMessage(
					commandMessage, null, error);

			try {
				_lcsConnectionManager.sendMessage(responseMessage);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void setCommands(Map<String, Command> commands) {
		_commands = commands;
	}

	public void setDigitalSignature(DigitalSignature digitalSignature) {
		_digitalSignature = digitalSignature;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommandMessageListener.class);

	private Map<String, Command> _commands;
	private DigitalSignature _digitalSignature;
	private LCSConnectionManager _lcsConnectionManager;

}