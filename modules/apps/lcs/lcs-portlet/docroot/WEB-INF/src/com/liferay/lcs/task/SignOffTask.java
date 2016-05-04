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

import com.liferay.lcs.messaging.HandshakeMessage;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.messaging.scheduler.MessageListenerSchedulerService;
import com.liferay.lcs.task.scheduler.TaskSchedulerService;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ivica Cardic
 */
public class SignOffTask implements Task {

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_lcsConnectionManager.setPending(false);

			_log.error(e, e);
		}
	}

	public void setDeregister(boolean deregister) {
		_deregister = deregister;
	}

	public void setHeartbeatInterval(long heartbeatInterval) {
		_heartbeatInterval = heartbeatInterval;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setMessageListenerSchedulerService(
		MessageListenerSchedulerService messageListenerSchedulerService) {

		_messageListenerSchedulerService = messageListenerSchedulerService;
	}

	public void setServerManuallyShutdown(boolean serverManuallyShutdown) {
		_serverManuallyShutdown = serverManuallyShutdown;
	}

	public void setTaskSchedulerService(
		TaskSchedulerService taskSchedulerService) {

		_taskSchedulerService = taskSchedulerService;
	}

	protected void doRun() throws PortalException {
		if (_log.isInfoEnabled()) {
			_log.info("Initiate sign off");
		}

		_lcsConnectionManager.onSignOff();

		_messageListenerSchedulerService.unscheduleAllMessageListeners();

		_taskSchedulerService.unscheduleAllTasks();

		if (!_deregister) {
			String key = _keyGenerator.getKey();

			HandshakeMessage handshakeMessage = new HandshakeMessage();

			handshakeMessage.put(
				Message.KEY_SERVER_MANUALLY_SHUTDOWN, _serverManuallyShutdown);
			handshakeMessage.put(
				Message.KEY_SIGN_OFF, String.valueOf(_heartbeatInterval));
			handshakeMessage.setKey(key);

			_lcsConnectionManager.sendMessage(handshakeMessage);

			LCSUtil.sendServiceAvailabilityNotification(
				LCSPortletState.NO_CONNECTION);
		}
		else {
			LCSUtil.sendServiceAvailabilityNotification(
				LCSPortletState.NOT_REGISTERED);
		}

		_lcsConnectionManager.setPending(false);

		if (_log.isInfoEnabled()) {
			_log.info("Terminated connection");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SignOffTask.class);

	private boolean _deregister;
	private long _heartbeatInterval;
	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;
	private MessageListenerSchedulerService _messageListenerSchedulerService;
	private boolean _serverManuallyShutdown;
	private TaskSchedulerService _taskSchedulerService;

}